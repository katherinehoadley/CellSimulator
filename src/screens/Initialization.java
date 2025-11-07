package screens;

import world.Step;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Initialization {

    public static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);


    //Might be moved to HomeUI?

    public void initializeMap(){
        //TODO - get user input and get the size the grid should be and do this better

        new GameUI().setGridDimensions(0, 0);

        // Start tick loop (currently its 10 times per second, this can change)
        scheduler.scheduleAtFixedRate(Step::update, 0, 100, TimeUnit.MILLISECONDS);
    }

}
