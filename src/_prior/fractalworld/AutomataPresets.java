package _prior.fractalworld;

public class AutomataPresets {

    //Extremely cursed comments + code not shown, only relevant stuff here

    public static boolean nextState;

    public static void conwaysLifeDRAW2D(/**ServerWorld world,**/ int x, int z) {

        int liveNeighbors = AutomataControl.countLiveNeighbors(/**world,**/ x, z);
        boolean isCurrentlyAlive = AutomataControl.isAlive(/**world,**/ x, z);

        if (isCurrentlyAlive) {
            nextState = (liveNeighbors == 2 || liveNeighbors == 3); // Survival: 2-3 neighbors
        } else {
            nextState = (liveNeighbors == 3); // Birth: exactly 3 neighbors
        }

        AutomataControl.addToQueue(new AutomataControl.AutomataTask(/**world, **/x, z, nextState));
    }
}
