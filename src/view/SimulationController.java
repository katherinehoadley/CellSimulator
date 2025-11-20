package view;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class SimulationController {
	
	protected int bufferPeriod = 100; 

	protected static final int MIN_BUFFER_PERIOD = 50;
	
	// Lance: keeps step running continuously.
	protected static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
	protected static ScheduledFuture<?> scheduledTask;
	
	
	public void startSimulation(Runnable step) {
		stopSimulation(); // reset any previous running simulations so there are never two running at once
		scheduledTask = scheduler.scheduleAtFixedRate(step, 0, bufferPeriod, TimeUnit.MILLISECONDS);
	}
	
	public void stopSimulation() {
		if (scheduledTask != null && !scheduledTask.isCancelled()) {
			scheduledTask.cancel(true); 
		}
	}
	
	public void slowDownSimSpeed() {
		bufferPeriod += 100;
	}
	
	public void speedUpSim() {
		bufferPeriod -= 100;
	}
	
	public boolean checkLimitForSimSpeed() {
		return (bufferPeriod - 100 >= MIN_BUFFER_PERIOD);
	}
}
