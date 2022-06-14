package ajbc.channel.chat_ex;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ChatClient {

	private static int counter = 0;
	private final String HOST = "localhost";
	private final int PORT = 9090, BUFFER_SIZE = 256;
	private final String STOP = "STOP";
	private final int CLIENT_NUM;

	public ChatClient() {
		CLIENT_NUM = counter++;
	}

	public void startClient() {

		Scanner sc = new Scanner(System.in);
		try {
			Selector selector = Selector.open();
			SocketChannel clientSocket = SocketChannel.open();
			clientSocket.connect(new InetSocketAddress("localhost", PORT));
			clientSocket.configureBlocking(false);

			clientSocket.register(selector, SelectionKey.OP_WRITE);
			ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);

			mainLoop: while (true) {
				selector.select();// this blocks until a ready channel registers
				Set<SelectionKey> selectedKeys = selector.selectedKeys();
				Iterator<SelectionKey> iter = selectedKeys.iterator();
				while (iter.hasNext()) {
					selectedKeys.stream().forEach(System.out::println);
					SelectionKey key = iter.next();

					if (key.isWritable()) {
						System.out.println("Client " + CLIENT_NUM + " say something");
						String txt = sc.nextLine();
						if (txt.equals(STOP))
							break mainLoop;
						write(buffer, txt, key);
					}

					else if (key.isReadable()) {
						read(buffer, key);
					}

					iter.remove();
				}
			}
			sc.close();
			clientSocket.close();
			selector.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void write(ByteBuffer buffer, String txt, SelectionKey key) throws IOException {

		SocketChannel client = (SocketChannel) key.channel();
		buffer.clear();
		buffer.put(txt.getBytes());
		buffer.flip();
		client.write(buffer);
		buffer.clear();

		client.register(key.selector(), SelectionKey.OP_READ);
	}

	private void read(ByteBuffer buffer, SelectionKey key) throws IOException {

		SocketChannel client = (SocketChannel) key.channel();
		client.read(buffer);
		buffer.flip();
		String txt = "";

		while (buffer.hasRemaining())
			txt += (char) buffer.get();

		buffer.clear();
		System.out.println("Server replied for client " + CLIENT_NUM + " :");
		System.out.println(txt.trim());
		client.register(key.selector(), SelectionKey.OP_WRITE);
	}

	public static void main(String[] args) {

		List<ChatClient> clients = new ArrayList<ChatClient>();
		clients.add(new ChatClient());
//		clients.add(new ChatClient());

		final int THREADS_NUM = clients.size();
		ExecutorService executor = Executors.newFixedThreadPool(THREADS_NUM);

		clients.forEach(client -> executor.execute(() -> {
			client.startClient();
		}));
	}

}
