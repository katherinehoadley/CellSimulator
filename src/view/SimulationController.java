// Author: Anna Rakes

package view;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public class SimulationController {
	
	// Default delay period between each step of the simulation when running continuously.
	private int bufferPeriod = 100; 

	// Minimum delay period (upper limit for simulation speed)
	private static final int MIN_BUFFER_PERIOD = 50;
	
	// Lance: keeps step running continuously.
	private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
	private static ScheduledFuture<?> scheduledTask;
	
	/**
	 * method startSimulation
	 * @param step
	 * starts the simulation loop.
	 * cancels any previous simulations running so they dont overlap
	 * schedules the given step to run repeatedly at the current speed (bufferPeriod).
	 */
	public void startSimulation(Runnable step) {
		stopSimulation(); // reset any previous running simulations so there are never two running at once
		scheduledTask = scheduler.scheduleAtFixedRate(step, 0, bufferPeriod, TimeUnit.MILLISECONDS);
	}
	
	/**
	 * method stopSimulation
	 * Stops the current simulation loop.
     * Cancels the scheduled task so steps no longer run.
	 */
	public void stopSimulation() {
		if (scheduledTask != null && !scheduledTask.isCancelled()) {
			scheduledTask.cancel(true); 
		}
	}
	
	// slow down the simulation speed
	public void slowDownSimSpeed() {
		bufferPeriod += 100;
	}
	
	// speed up simulation speed
	public void speedUpSim() {
		bufferPeriod -= 100;
	}
	
	/**
	 * checks to see if current speed is within the max limit for speed of the simullation
	 * if there is still room for simulation to speed up, return true. 
	 * @return
	 */
	public boolean checkLimitForSimSpeed() {
		return (bufferPeriod - 100 >= MIN_BUFFER_PERIOD);
	}
}
