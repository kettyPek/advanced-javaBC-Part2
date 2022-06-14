package ajbc.channel;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;

import java.nio.channels.SocketChannel;

public class ClientSocketChannelExample {
	
	private static final int BUFFER_SIZE = 25;
	private static final int PORT = 9090;
	private static final String HOST = "localhost";

	public static void main(String[] args) {
		blocking();
	}

	private static void blocking() {
		
		String text = "This is the client talking";
		
		try(SocketChannel clieantSocket = SocketChannel.open();) {
			clieantSocket.connect(new InetSocketAddress(HOST,PORT));
			ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);
			
			buffer.put(text.getBytes());
			
			buffer.flip();
			
			while(buffer.hasArray())
				clieantSocket.write(buffer);
			
			System.out.println("client sent to server: " + text);	
			System.out.println("-----------------------------------------");
			System.out.println("client received :");
			
			while(clieantSocket.read(buffer) != -1) {
				buffer.flip();
				while(buffer.hasRemaining()) {
					System.out.print((char)buffer.getChar());
				}
				buffer.clear();
			}
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		

	}

}
