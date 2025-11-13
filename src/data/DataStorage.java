package data;

import model.CellularAutomata;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class DataStorage {

    private final GenericFromHell presetManager;
    private final Path dataPath;

    public DataStorage() {
        this.presetManager = new GenericFromHell();

        //this data path should be outside of src, in the directory above that, in a folder called data.
        this.dataPath = Paths.get(System.getProperty("user.dir"), "data");
        File dataDir = dataPath.toFile();
        if (!dataDir.exists()) dataDir.mkdirs(); //ignore useless output of mkdirs because screw yu
    }

    //TODO - i got so fed up with the Generic class that I fully forgot to test load and save data. This is a
    // tommorow problem because im about to collapse.

    public void saveData(int[][] grid, String preset) {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Enter filename to save (without extension): ");
            String filename = scanner.nextLine().trim();

            Path filePath = Paths.get(String.valueOf(dataPath), filename + ".txt");

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

    public void loadData() {
        try {
            Scanner scanner = new Scanner(System.in);

            File dataDir = new File(String.valueOf(dataPath));
            String[] files = dataDir.list((dir, name) -> name.endsWith(".txt"));
            if (files == null || files.length == 0) {
                System.out.println("No data files found in " + dataPath);
                return;
            }

            System.out.println("Available files:");
            for (String f : files) {
                System.out.println("- " + f);
            }

            System.out.print("Enter the file name you want to load: ");
            String chosen = scanner.nextLine().trim();
            if (!chosen.endsWith(".txt")) chosen += ".txt";

            Path filePath = Paths.get(String.valueOf(dataPath), chosen);
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

            //TODO - the code within these lines is AI. This is untested and probably broken in
            // at least one way. I just did not want to deal with printing the object 2D array
            // instead of the data in a digestible manner again.
            ////////////////////////////////////////////
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
            ////////////////////////////////////////////

            //somehow works?
            int[][] newGrid = rows.toArray(new int[0][]);

            //TODO - for future compatibility, this may not extend CellularAutomata, but rather it will extend Object.
            // Some more processing would need to be done to determine which superclass it falls under so casting
            // doesn't become a problem... basically fix this tomorrow
            Object object = presetManager.getWhatClassThePresetIsFromUsingGenericMagic(preset);

            if (object instanceof CellularAutomata){
                object = object;
            }

            System.out.println("Loaded preset: " + preset);
            System.out.println("Grid size: " + newGrid.length + "x" + newGrid[0].length);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

