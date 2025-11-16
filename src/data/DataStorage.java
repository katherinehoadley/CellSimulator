package data;

import model.GamePreset;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class DataStorage {

    private final GenericFromHell presetManager;
    private final Path dataPath;
    private int [][] loadedGrid;
    private GamePreset automata;

    public DataStorage() {
        this.presetManager = new GenericFromHell();

        //this data path should be outside of src, in the directory above that, in a folder called data.
        this.dataPath = Paths.get(System.getProperty("user.dir"), "data");
        File dataDir = dataPath.toFile();
        if (!dataDir.exists()) dataDir.mkdirs();
    }

    public void saveData(int[][] grid, String preset, String fileName) {
        try {
//            System.out.print("Enter filename to save (without extension): ");
//            String filename = scanner.nextLine().trim();

            Path filePath = Paths.get(String.valueOf(dataPath), fileName + ".txt");

            try (BufferedWriter writer = Files.newBufferedWriter(filePath)) {
                writer.write("PRESET:" + preset);
                writer.newLine();

                //erm all I can think of while writing this line is some instagram reel diego sent me of squidward
                for (int[] row : grid) {
                    for (int j = 0; j < row.length; j++) {
                        writer.write(row[j] + (j == row.length - 1 ? "" : " "));
                    }
                    writer.newLine();
                }

                System.out.println("Saved to: " + filePath.toAbsolutePath());
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loadData(String fileName) {
        try {
//            Scanner scanner = new Scanner(System.in);
//
//            File dataDir = new File(String.valueOf(dataPath));
//            String[] files = dataDir.list((dir, name) -> name.endsWith(".txt"));
//            if (files == null || files.length == 0) {
//                System.out.println("No data files found in " + dataPath);
//                return;
//            }
//
//            System.out.println("Available files:");
//            for (String f : files) {
//                System.out.println("- " + f);
//            }
//
//            System.out.print("Enter the file name you want to load: ");
//            String chosen = scanner.nextLine().trim();
//            if (!chosen.endsWith(".txt")) chosen += ".txt";

            Path filePath = Paths.get(String.valueOf(dataPath), fileName);
            if (!Files.exists(filePath)) {
                System.out.println("File not found: " + filePath.getFileName());
                return;
            }

            List<String> lines = Files.readAllLines(filePath);

            if (lines.isEmpty()) {
                System.out.println("File is empty!");
                return;
            }

            // line 1 is preset
            String preset = lines.get(0).replace("PRESET:", "").trim();

            List<int[]> rows = new ArrayList<>();

            for (int i = 1; i < lines.size(); i++) {

                String line = lines.get(i).trim();
                if (line.isEmpty()) continue;

                String[] parts = line.split(" ");
                int[] row = new int[parts.length];

                for (int j = 0; j < parts.length; j++) {
                    row[j] = Integer.parseInt(parts[j]);
                }

                rows.add(row);
            }

            //somehow works?
            int[][] newGrid = rows.toArray(new int[0][]);

            automata = presetManager.getWhatClassThePresetIsFromUsingGenericMagic(preset);

            loadedGrid = newGrid;

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public int[][] getLoadedGrid() {
    	return loadedGrid;
    }
    
    public GamePreset getAutomata() {
    	return automata;
    }
}

