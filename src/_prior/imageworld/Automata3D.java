package _prior.imageworld;

import java.util.Map;

public class Automata3D {

//    //for multithreading pain (DAEMON stops memory leaks from unused eternal threads)
//    private static final ThreadFactory DAEMON_THREAD_FACTORY = runnable -> {
//        Thread t = new Thread(runnable);
//        t.setDaemon(true);
//        return t;
//    };
//    private static final ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors(), DAEMON_THREAD_FACTORY);
//    private static final ExecutorService postTickExecutor = Executors.newSingleThreadExecutor(DAEMON_THREAD_FACTORY);
//    private static final ThreadLocal<BlockPos.Mutable[]> MUTABLE_POOL = ThreadLocal.withInitial(() -> {
//        int maxSize = 27; // fits all known neighborhoods
//        BlockPos.Mutable[] pool = new BlockPos.Mutable[maxSize];
//        for (int i = 0; i < maxSize; i++) pool[i] = new BlockPos.Mutable();
//        return pool;
//    });

    public static AutomataType currentAutomataType = AutomataType.NORMAL;

    public enum NeighborhoodType {
        MOOSE, VON_NEUMANN
    }
    public enum AutomataType {
        NORMAL, EXPLOSIVE, SKYBLOCK, VANISHING
    }

    private static final Map<NeighborhoodType, int[]> NEIGHBORHOOD_TYPES = Map.of(
            //TODO - for this project, we should probably use VON_NEUMANN, which means just check the 8
            // neighbors around you, both orthogonally and diagonally
            NeighborhoodType.VON_NEUMANN, new int[] {
                    1, 0, 0,  -1, 0, 0,
                    0, 1, 0,   0, -1, 0,
                    0, 0, 1,   0, 0, -1
            },
            NeighborhoodType.MOOSE, new int[] {
                    -1, -1, -1,  0, -1, -1,  1, -1, -1,
                    -1,  0, -1,  0,  0, -1,  1,  0, -1,
                    -1,  1, -1,  0,  1, -1,  1,  1, -1,

                    -1, -1,  0,  0, -1,  0,  1, -1,  0,
                    -1,  0,  0,               1,  0,  0,
                    -1,  1,  0,  0,  1,  0,  1,  1,  0,

                    -1, -1,  1,  0, -1,  1,  1, -1,  1,
                    -1,  0,  1,  0,  0,  1,  1,  0,  1,
                    -1,  1,  1,  0,  1,  1,  1,  1,  1
            }
    );

    //Code below for this is so absurd and half of it is 3D logic so I dont think we will be using this one...

    //This class is 676 lines of code long...

}
