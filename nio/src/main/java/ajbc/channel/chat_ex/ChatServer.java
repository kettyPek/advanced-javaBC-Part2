package ajbc.channel.chat_ex;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class ChatServer {

	private final int PORT = 9090;
	private final int BUFFER_SIZE = 256;

	public void startServer() {

		try {
			Selector selector = Selector.open();
			ServerSocketChannel serverChannel = ServerSocketChannel.open();
			serverChannel.socket().bind(new InetSocketAddress(PORT));

			serverChannel.configureBlocking(false);
			serverChannel.register(selector, SelectionKey.OP_ACCEPT);

			ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);

			while (true) {
				while (selector.selectNow() == 0)
					;

				Set<SelectionKey> selectedKeys = selector.selectedKeys();
				Iterator<SelectionKey> iter = selectedKeys.iterator();

				while (iter.hasNext()) {
					SelectionKey key = iter.next();
					if (key.isAcceptable()) {
						System.out.println("client available");
						registerServer(selector, serverChannel);
					}
					else if(key.isReadable()) {
						System.out.println("reading to client");
						read(buffer,key);
					}
					else if (key.isWritable()) {
						System.out.println("echoing to client");
						echo(buffer, key);
					}
					iter.remove();
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private static void registerServer(Selector selector, ServerSocketChannel serverChannel) throws IOException {
		SocketChannel client = serverChannel.accept();
		client.configureBlocking(false);
		client.register(selector, SelectionKey.OP_WRITE);
	}

	private static void read(ByteBuffer buffer, SelectionKey key) throws IOException, InterruptedException {
		SocketChannel client = (SocketChannel) key.channel();
		client.read(buffer);
		buffer.rewind();
	}
	
	private static void echo(ByteBuffer buffer, SelectionKey key) throws IOException, InterruptedException {
		SocketChannel client = (SocketChannel) key.channel();
		client.write(buffer);
		buffer.clear();
		client.close();
	}
	
	public static void main(String[] args) {
		
		ChatServer chatServer = new ChatServer();
		chatServer.startServer();
	}

}
