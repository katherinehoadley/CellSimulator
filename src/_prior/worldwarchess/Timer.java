package _prior.worldwarchess;

public class Timer {


    //called every iteration (or in this case tick)
    private static void doHostLogic(){
//        NPC.stopAllNpcMovement();
//        currentTime = maxTimePerTurn;
//        turnCounter++;
//        playerIDMovedList.clear();
//        CommanderAttacks.resolveCommanderAttacks();
//        GamePanel.sidebarEvaluator.updateEvaluation();
//        WorldMap.tickBoards();
//        WorldMap.tickAttacks();
//        WorldMap.resetPieces();
//        Marker.tickMarkers();

        //TODO - SPAWNS NEW ONES THEN KILLS LONELY / OVERCROWDED
        // WorldMap.tickMinionRequests();

//        MoveHistory.saveData();
//        MoveHistory.addNewTurn();

        //TODO - CALCULATES NEW SPOTS TO MOVE TO / FINDS DEAD CELLS AND TURNS THEM TEMP
        // if (turnCounter % 2 == 1) NPC.doAllNpcLogic("Black", "White",true);

//        if (GameMode.currentMode == GameModeType.NPC_WAR) {
//            if (turnCounter % 2 == 0) NPC.doAllNpcLogic("White", "Black", false);
//        }
    }



    //WorldMap.class
//    public static void tickMinionRequests() {
//        for (Piece piece : minionPieceDestroyRequests){
//            erasePiece(piece, true);
//        }
//
//        for (Piece piece : minionPieceSpawnRequests){
//            Board board = piece.getBoard();
//            int posX = piece.getPosX();
//            int posY = piece.getPosY();
//            Piece piece1 = board.getPieceAt(posX, posY, Optional.empty(), pieces);
//            if (piece1 == null){
//                pieces.add(piece);
//
//            } else if (!Objects.equals(piece1.getTeam(), piece.getTeam())){
//                WorldMap.killPiece(piece1, true);
//                pieces.add(piece);
//            }
//        }
//
//        minionPieceSpawnRequests.clear();
//        minionPieceDestroyRequests.clear();
//    }



    //NPC.class
    public static void doAllNpcLogic(String team, String ignoredTeam, boolean doOtherTeams) {
//        //moved NPC logic on a different thread because it can be laggy
//        new Thread(() -> {
//            areNpcsMoving = true;
//            stopMovement = false;
//            for (Piece npc : WorldMap.getAllNPCPieces()) {
//                if (stopMovement){
//                    stopMovement = false;
//                    areNpcsMoving = false;
//                    return;
//                }
//                if (Objects.equals(npc.getTeam(), team)){
//                    npc.move();
//                } else if (doOtherTeams){
//                    if (!Objects.equals(npc.getTeam(), ignoredTeam)){
//                        npc.move(); //TODO - only line here that matters, move each tile / piece
//                    }
//                }
//            }
//            if (!Objects.equals(GameMode.getCurrentGameModeAsString(), "War") || GameMode.isTutorial ||
//                    WorldMap.currentMapPreset.equals(GameModeMapType.SIEGE)) {
//                //end turn if its an NPC gamemode since they all moved
//                Timer.currentTime = 0;
//                areNpcsMoving = false;
//            }
//        }, "NPCMover").start();
    }
}
