package example;

import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;


import processing.Processor;
import processing.Status;
import processing.StatusListener;

public class MyClassLoader extends ClassLoader implements Processor {
	
	private String result = null;
	private Path searchPath;
	
	public MyClassLoader(Path path) {
		if (!Files.isDirectory(path)) throw new IllegalArgumentException("Path must be a directory");
		searchPath = path;
	}
	
	public Class<?> findClass(String binName) throws ClassNotFoundException {
		Path classfile = Paths.get(searchPath + FileSystems.getDefault().getSeparator() + binName.replace(".", FileSystems.getDefault().getSeparator()) + ".class");
		//System.out.println(classfile.toString());
		byte [] buf;
		try {
			buf = Files.readAllBytes(classfile);
		} catch (Exception e) {
			throw new ClassNotFoundException("Error in defining" + binName + "in" + searchPath,e  );
		}
		return defineClass(null, buf,  0, buf.length);
	}
	
	@Override
	public boolean submitTask(String task, StatusListener sl) {
		AtomicInteger ai = new AtomicInteger(0);
		
		ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
		//ScheduledFuture<?> scheduleFuture = executorService.scheduleAtFixedRate(
		executorService.scheduleAtFixedRate(
				()->{
					System.out.println("running");
		            ai.incrementAndGet();
		            sl.statusChanged(new Status("Running",ai.get()));		    		
				}, 
				1, 1, TimeUnit.SECONDS);	
		
		ExecutorService executor = Executors.newSingleThreadExecutor();
		// uruchom zadanie, które skoñczy siê, gdy licznik przekroczy wartoœæ 100
		executor.submit(() -> {
		      while (true) {
		          //System.out.println(scheduleFuture.isDone()); will always print false
		          try {
					Thread.sleep(800);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		          if (ai.get() >= 10) { 
		        	  sl.statusChanged(new Status("Finished",10));
		        	  result = task.toUpperCase();
		              System.out.println("finished");
		              //scheduleFuture.cancel(true);
		              executorService.shutdown();
		              executor.shutdown();
		              break;
		          }
		      }			
			});

		return true;
	}

	@Override
	public String getInfo() {
		return "Asynchronous processing";
	}

	@Override
	public String getResult() {
		return result;
	}

}
