package ajbc.channel;

import java.nio.CharBuffer;

public class BufferExample {

	public static void run() {
		final int BUFFER_SIZE = 30;

		CharBuffer buffer = CharBuffer.allocate(BUFFER_SIZE);
		// write into buffer
		buffer.put('o');
		buffer.put('k');
		buffer.append(" Java is the best");
		buffer.put(0, 'k');

		// flip for reading
		buffer.flip();

		while (buffer.hasRemaining()) {
			System.out.print(buffer.get());
		}
		System.out.println();

		// reading again from buffer
		buffer.rewind();
		while (buffer.hasRemaining()) {
			System.out.print(buffer.get());
		}
		System.out.println();

		// writing from the position we finished reading
		buffer.rewind();
		buffer.get();
		buffer.get();
		// we read 2 chars
		buffer.compact();
		buffer.put(" now writing");

		// flip for reading
		buffer.flip();
		while (buffer.hasRemaining()) {
			System.out.print(buffer.get());
		}
		System.out.println();

		//
		buffer.clear();
		buffer.put("this is new text");
		buffer.flip();
		int counter = 0;
		while(buffer.hasRemaining()) {
			System.out.print(buffer.get());
			if(counter == 4)
				buffer.mark();
			counter++;
		}
		System.out.println();
		
		buffer.reset();
		while (buffer.hasRemaining()) {
			System.out.print(buffer.get());
		}
		System.out.println();

	}

}
