package ajbc.channel;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class AsyncFilesExample {

	private static final int BUFFER_SIZE = 1024;

	public static void run() throws IOException, InterruptedException, ExecutionException {
		
		// writing using CompletionHandler
		
		Path path = Paths.get("data","test-write.txt");
		
		if(!Files.exists(path))
			Files.createFile(path);
		
		AsynchronousFileChannel fileChannel = AsynchronousFileChannel.open(path, StandardOpenOption.READ);
		ByteBuffer buffer = ByteBuffer.allocate(BUFFER_SIZE);
		
		String text = "Your commendations, madam, get from her tears.\r\n"
				+ "COUNTESS\r\n"
				+ "'Tis the best brine a maiden can season her praise\r\n"
				+ "in. The remembrance of her father never approaches\r\n"
				+ "her heart but the tyranny of her sorrows takes all\r\n"
				+ "livelihood from her cheek. No more of this, Helena;\r\n"
				+ "go to, no more; lest it be rather thought you affect\r\n"
				+ "a sorrow than have it.\r\n"
				+ "HELENA";
		
		buffer.put(text.getBytes());
		buffer.flip();
		
		Double attachment = 4.0;
		
		CompletionHandler<Integer, Double> completionHandler = new CompletionHandler<Integer, Double>(){

			@Override
			public void completed(Integer result, Double attachment) {
				System.out.println(result);
				
			}

			@Override
			public void failed(Throwable exc, Double attachment) {
				// TODO Auto-generated method stub
				
			}};
			
		fileChannel.write(buffer,0, attachment,completionHandler);
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		// reading using Future
//		Path path = Paths.get("data","shakespeare.txt");
//		AsynchronousFileChannel fileChannel = AsynchronousFileChannel.open(path, StandardOpenOption.READ);
//		ByteBuffer buffer = ByteBuffer.allocate((int)fileChannel.size());
//		
//		Future<Integer> numBytes = fileChannel.read(buffer, 0);
////		while(!numBytes.isDone())
////			System.out.println("reading...");
//		
//		numBytes.get();
//		buffer.flip();
//		byte[] data = new byte[buffer.limit()];
//		buffer.get(data);
////		System.out.println(new String(data));
//		buffer.clear();
//		System.out.println("something");
//		
//		fileChannel.close();
	}

}
