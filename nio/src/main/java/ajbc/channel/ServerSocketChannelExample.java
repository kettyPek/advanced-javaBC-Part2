package ajbc.channel;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class ServerSocketChannelExample {

	private static final int BUFFER_SIZE = 25;
	private static final int PORT = 9090;

	public static void main(String[] args) {
		blocking();
	}

	private static void blocking() {

		try (ServerSocketChannel serverSocket = ServerSocketChannel.open();) {
			serverSocket.bind(new InetSocketAddress(PORT));

			while (true) {
				SocketChannel socketChannel = serverSocket.accept();
				ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);
				while (buffer.hasRemaining() && socketChannel.read(buffer) != -1) {
					socketChannel.write(buffer);
				}
				buffer.rewind();
				while(buffer.hasRemaining())
					System.out.println((char)buffer.get());
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
