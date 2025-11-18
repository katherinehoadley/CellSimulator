package data;

import java.io.File;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

import model.GameOfLife;
import model.GamePreset;

public class GenericFromHell {
    public GenericFromHell() {
        discoverPresets();
    }

    //Cache the presets to a list so it doesnt need to search each call
    private final Map<String, Class<?>> presets = new HashMap<>();

    
    //This generic is needed because I cant use static and I needed a
    //way to reverse lookup the interface's method output for getPreset.
    public GamePreset getWhatClassThePresetIsFromUsingGenericMagic(String name) {
        Class<?> clazz = presets.get(name);
        if (clazz == null) {
            System.out.println("class is null: " + name);
            return null;
        }
        try {
            return (GamePreset) clazz.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    //This searches for every class that implements the GamePreset interface 
    private void discoverPresets() {
        try {
            String basePackage = GameOfLife.class.getPackage().getName();
            String path = basePackage.replace('.', '/');
            
            Enumeration<URL> resources = Thread.currentThread().getContextClassLoader().getResources(path);

            while (resources.hasMoreElements()) {
                URL resource = resources.nextElement();
                File directory = new File(URLDecoder.decode(resource.getFile(), StandardCharsets.UTF_8));

                if (directory.exists()) {
                    scanDirectory(directory, basePackage);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //TODO - since apparently this walker is broken for searching other directories, make sure to call it for
    // each directory. 
    
    private void scanDirectory(File directory, String packageName) {
    	System.out.println("Scan Directory");
        File[] files = directory.listFiles();
        if (files == null) {
        	System.out.println("files is null in this package");
        	return;
        }

        for (File file : files) {
            if (file.isDirectory()) {
                //Abstract IDE's recursion to go into subpackages
                scanDirectory(file, packageName + "." + file.getName());

                //dont check weird $ files
            } else if (file.getName().endsWith(".class") && !file.getName().contains("$")) {
                String className = packageName + "." + file.getName().substring(0, file.getName().length() - 6);
                try {
                    Class<?> clazz = Class.forName(className);
                    if (!Modifier.isAbstract(clazz.getModifiers()) && model.GamePreset.class.isAssignableFrom(clazz)) {

                        model.GamePreset instance = (model.GamePreset) clazz.getDeclaredConstructor().newInstance();
                        String presetName = instance.getPreset();

                        //cache preset so I dont need to call this again until new object of generic is made
                        presets.put(presetName, clazz);
                        System.out.println("FOUND PRESET!!! - " + presetName + " (" + clazz.getSimpleName() + ")");
                    }
                } catch (Throwable ignored) {} //screw error handling
            }
        }
    }
}
