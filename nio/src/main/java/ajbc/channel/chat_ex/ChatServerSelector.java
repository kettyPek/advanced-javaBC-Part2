package ajbc.channel.chat_ex;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class ChatServerSelector {

	private static final int PORT = 9090, BUFFER_SIZE = 256;
	private List<SocketChannel> clientsChanneles = new ArrayList<SocketChannel>();

	public void startServer() {

		try {
			Selector selector = Selector.open();

			ServerSocketChannel serverChannel = ServerSocketChannel.open();
			serverChannel.socket().bind(new InetSocketAddress(PORT));
			serverChannel.configureBlocking(false);

			serverChannel.register(selector, SelectionKey.OP_ACCEPT);

			ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);

			while (true) {
				selector.select(); // blocking
				Set<SelectionKey> selectedKeys = selector.selectedKeys();
				Iterator<SelectionKey> iter = selectedKeys.iterator();
				while (iter.hasNext()) {
					System.out.println(selector.toString());
					SelectionKey key = iter.next();
					System.out.println(key);
					if (key.isAcceptable()) {
						registerServer(selector, serverChannel);
					}
					else if (key.isReadable()) {
						read(selector, buffer, key);
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

	private void read(Selector selector, ByteBuffer buffer, SelectionKey key) throws IOException, InterruptedException {
		SocketChannel client = (SocketChannel) key.channel();
		client.read(buffer);
		Thread.sleep(500);
		client.register(selector, SelectionKey.OP_WRITE);
	}

//	private void writeAllMessages(ByteBuffer buffer) {
//		clientsChanneles.forEach(client -> {
//			try {
//				client.read(buffer);
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		});
//	}

//	private void write(Selector selector, ByteBuffer buffer, SelectionKey key)
//			throws IOException, InterruptedException {
//		SocketChannel client = (SocketChannel) key.channel();
//		buffer.flip();
//		client.write(buffer);
//		buffer.clear();
//		client.register(selector, SelectionKey.OP_READ);
//	}

	private void registerServer(Selector selector, ServerSocketChannel serverChannel) throws IOException {
		SocketChannel client = serverChannel.accept();
		client.configureBlocking(false);
		client.register(selector, SelectionKey.OP_READ);
		clientsChanneles.add(client);
	}

	public static void main(String[] args) {

		ChatServerSelector chatServer = new ChatServerSelector();
		chatServer.startServer();
	}

}
