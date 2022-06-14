package ajbc.channel;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class BasicChannel2 {
	
	public static void run() throws IOException {
		
		final int BUFFER_SIZE = 10;
		
		String fileName = "data/nio-data.txt";
		// create a file
		RandomAccessFile file = new RandomAccessFile(fileName,"rw");
		// get file's channel
		FileChannel fileChannel = file.getChannel();
		// create a buffer to contain an manipulate file's data
		ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);
		// read text from channel into buffer
		
		int bytesRead = 0; // we will get the number of bytes we read from a channel
		// firstly we need to read from the buffer and only then write into it
		bytesRead = fileChannel.read(buffer);
		while(bytesRead != -1) {
			buffer.flip(); // changes the position and limit of the buffer
			while(buffer.hasRemaining())
				System.out.print((char)buffer.get());
			buffer.clear(); // clears buffer before writing into it 
			bytesRead = fileChannel.read(buffer);
			System.out.print(" | ");
		}
		
		file.close();
		

	}

}
