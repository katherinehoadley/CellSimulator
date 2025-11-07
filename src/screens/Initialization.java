//Katherine Hoadley
package screens;

import world.Step;
import world.StepGameOfLife;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Initialization {

    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);	//lance says this ok. kat disagrees.


    //Might be moved to HomeUI?

    public void initializeMap(){
        //TODO - get user input and get the size the grid should be and do this better

        new GameUI().setGridDimensions(0, 0);

        // Start tick loop (currently its 10 times per second, this can change)
        Step step = new StepGameOfLife();
        scheduler.scheduleAtFixedRate(step::update, 0, 100, TimeUnit.MILLISECONDS);
    }

}
