package ajbc.channel;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.nio.channels.WritableByteChannel;

public class BasicChannel {

	public static void run() throws IOException {

		String fileName = "data/nio-data.txt"; 

		// ----read from file----

		try (FileInputStream fileInputStream = new FileInputStream(fileName);
				FileChannel fileChannel = fileInputStream.getChannel();
				WritableByteChannel outChannel = Channels.newChannel(System.out);) {
			
			MappedByteBuffer buffer = fileChannel.map(MapMode.READ_ONLY, 0, fileChannel.size());
			while (buffer.hasRemaining()) {
				outChannel.write(buffer);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		

		// Option 1
//		String text = "";
//		while(buffer.hasRemaining()) {
//			char ch = (char)buffer.get(); // takes byte and parse it to char
//			text += ch;
//		}
//		System.out.println(text);

	}

}
