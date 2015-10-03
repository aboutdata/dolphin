package com.sabsari.dolphin.test.core.task;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

public class TaskExecutor {
	
	private int poolSize;
	private ExecutorService workerPool;
	
	public TaskExecutor(int initialPoolSize) {
		initialize(initialPoolSize, Executors.defaultThreadFactory());
	}
	
	public TaskExecutor(int initialPoolSize, ThreadFactory tFactory) {
		initialize(initialPoolSize, tFactory);
	}
	
	private void initialize(int initialPoolSize, ThreadFactory tFactory) {
		this.poolSize = initialPoolSize;
		this.workerPool = Executors.newFixedThreadPool(initialPoolSize, tFactory);
		
		// logging
		System.out.println("Initialized TaskExecutor with pool size of " + initialPoolSize);			
	}
	
	public int getPoolSize() {
		return this.poolSize;
	}
	
	public void doTask(Task task) {
		// logging
		System.out.println("Register new Task. ID: " + task.getTaskId());	
		this.workerPool.execute(task);
	}
	
	public void shutdown() {
		this.workerPool.shutdown();
		// logging
		System.out.println("Waiting for thread pool shutdown.");
		try {
			while(!this.workerPool.awaitTermination(1, TimeUnit.SECONDS)) {
				// logging
				System.out.print(".");
			}
		} catch (InterruptedException e) {
			this.workerPool.shutdownNow();
		    Thread.currentThread().interrupt();
		}
		// logging
		System.out.println("Thread pool shutdown completed.");
	}
}
