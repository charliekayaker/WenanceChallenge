package com.wenance.digitalcurrencies.threads;

public class Task {
	
	//Inyectar la tarea con lambda para que corra cada 10 segs.
	
	private final long frecuency;
	private final Schedulable schedulable;
	
	public Task(Schedulable schedulable, long frecuency) {
		this.frecuency = frecuency;
		this.schedulable = schedulable;
	}
	
	
	public final void execute() {
		
		new Thread(()->{
			while(true) {				
				schedulable.executeTask();
				try {				
					Thread.sleep(this.frecuency);
				} catch (InterruptedException e) {
				
					e.printStackTrace();
				}
			}
		});
	}

}
