package com.sabsari.dolphin.core.task;

import org.junit.Test;

public class TaskExecutorTest {

	@Test
	public void _쓰레드풀익스큐터테스트() {
		int poolSize = 10;
        int taskSize = 30;
        
        TaskExecutor taskExecutor = new TaskExecutor(poolSize);
        
        for (int i = 0; i < taskSize; i++) {
            Task task = new TaskThread("" + i);
            taskExecutor.doTask(task);
        }
        
        try {
			Thread.sleep(20000);
		} catch (InterruptedException e) {
			// Auto-generated catch block
			e.printStackTrace();
		}
        
        taskExecutor.shutdown(); 
	}
	
	public static class TaskThread implements Task {
	     
	    private String taskId;
	     
	    public TaskThread(String id){
	        this.taskId = id;
	    }
	    
	    @Override
		public String getTaskId() {
			return this.taskId;
		}
	 
	    @Override
	    public void run() {
	        System.out.println(Thread.currentThread().getName()+" Start. id = " + this.taskId);
	        processCommand();
	        System.out.println(Thread.currentThread().getName()+" End. id = " + this.taskId);
	    }
	 
	    private void processCommand() {
	        try {
	            Thread.sleep(500);
	        } catch (InterruptedException e) {
	            e.printStackTrace();
	        }
	    }	
	}
}
