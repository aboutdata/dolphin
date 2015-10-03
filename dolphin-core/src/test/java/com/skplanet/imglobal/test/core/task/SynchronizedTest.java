package com.sabsari.dolphin.test.core.task;

import java.util.LinkedList;
import java.util.Queue;

public class SynchronizedTest {

	// List<? super T> and List<? extends T>
	public static abstract class Worker<T> extends Thread {
	
		private boolean stop = false;
		
		private String workerName = "scv";
		
		private SynchronizedWorkList<T> workList;
		
		public Worker(SynchronizedWorkList<T> workList) {		
			this.workList = workList;		
		}
		
		public Worker(SynchronizedWorkList<T> workList, String workerName) {		
			this.workList = workList;
			this.workerName = workerName;
		}
		
		public String getWorkerName() {
			return this.workerName;
		}
	
		/**
		 * wait until finishing the current work.
		 */
		public void stopWorker() {	
			this.stop = true;	
		}
		
		/**
		 * abort the current thread right away
		 */
		public void forceQuit() {
			this.interrupt();
		}
	
		@Override	
		public void run() {	
			T work = null;	
			while (!this.stop && !this.isInterrupted()) {
				try {
					work = this.workList.deQueue();	
				} catch (InterruptedException e) {
					// 종료 로깅
					break;
				}
			
				doWork(work);
				work = null;
			}
		}
		
		abstract void doWork(T work);	
	}

	public static class SynchronizedWorkList<T> {
	
		private long timeout = 0L;			
		private Queue<T> queue = new LinkedList<T>();
		
		public SynchronizedWorkList() {}
		
		public SynchronizedWorkList(long waitTimeout) {		
			this.timeout = waitTimeout;
		}
		
		public synchronized void enQueue(T e) {		
			this.queue.add(e);		
			notifyAll();		
		}
		
		public synchronized T deQueue() throws InterruptedException {		
			while (this.queue.isEmpty()) {		
				wait(timeout);		
			}			
			return this.queue.poll();		
		}	
	}
}