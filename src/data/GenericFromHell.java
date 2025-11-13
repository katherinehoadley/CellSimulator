package data;

import java.io.File;
import java.lang.reflect.Modifier;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

import model.CellularAutomata;

public class GenericFromHell {

    //AhHhH hOw CLeAn hHAHhAHhAHHAhaHaAAAAAAAAAAAAAAAAAAAAAAAAA
    public GenericFromHell() {
        discoverPresets();
    }

    //Cache because if you do this function too often I will be very depressed and also no static and also
    //no side effects to adding a new class that implements GamePreset...
    private final Map<String, Class<?>> presets = new HashMap<>();


    //This used to be Class<? extends CellularAutomata> but that is less scalable so even though this is even
    //more generic and even worse, this can do for now because like it still works the same, just returning
    //Object is somewhat ugly. However this entire class is ugly because its  a generic so like whatever.
    //The amount of work to go through to not use static is insane. This class is like too allergic to static.
    //Like all this could be solved with an enum or static list but like oH nO iF I AdD a NeW fILe I dOnT wAnT
    //To WrItE tHe 2 SecCoNd ChaNgE... LIKE HOW DO I SCALE THIS... Its like why spend 10 minutes writing code
    //when you can spent 20 hours failing to write something that will do it for you the one time in never
    //that you actually need to do it. like also this is even slower because its generic but like atleast
    //its not like WorldWarChess where I needed to call a constructor lookup a few thousand times all at once
    //because thats so slow it makes me want to scream. like at this point why even try to make clean code
    //like if my code is so bad then nobody will hacker it because they dont got time for that its a
    //cyber security thing. coding while sick is like the worst thing ever because like I have no brainpower
    //but I have just enough strength to be at my computer and now that I was stuck to my bed for
    //like 5 hours I have plenty of time and a ruined sleep schedule for the day.
    //Speaking of schedules, how is something as simple as a scheduler as a constant final
    //like hated because it uses static? Like anything even remotely related to
    //static is hated and ostracised. Like the thing running this computer is electricity, static electricity
    //from my cat smudge rubbing against the computer, yet smudge is not generic, hes just a good boy, and
    //sometimes thats all static is, its just a good little guy that does what it must, and gets the job done.
    //Like a launch station for a train station at once place its good and like you dont need to make a new
    //train station at the same spot just because like I guess you can plan for it but like stop being dumb.
    //My brain is pain and my frame is a shame, my car has bars but not like police cars
    //The back is cray and im being taken away, im tryna play with an array but I cant get relay
    //wait wtf am I writing
    //At this rate this will be the comment for the entire commit
    //This generic is needed because I cant use static and I needed a
    //way to reverse lookup the interface's method output for getPreset.
    public Object getWhatClassThePresetIsFromUsingGenericMagic(String name) {
        Class<?> clazz = presets.get(name);
        if (clazz == null) {
            System.out.println("clazzzzzzzzzzzzzzzzzzz null you numskull: " + name);
            return null;
        }
        try {
            return clazz.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    //The amount of googling that went into this one (2) function is ridiculous. This is my File walker from Abstract
    //merged with functionality to exclude bytecode $ classes merged with more functionality from the internet
    //because this just kept having problem after problem and now I somehow ended up with this mess...
    //All of this just to not use static.
    @SuppressWarnings("all") //idk why this header is here but idk if I can remove it so it stays... I think its just a QoL thing
    private void discoverPresets() {
        try {
            //Since apparently after 30 minutes I cant figure out how to start from the src directory since
            //when compiled its compressed, we will just start from the "model" package.
            String basePackage = CellularAutomata.class.getPackage().getName();
            String path = basePackage.replace('.', '/');
            
            System.out.println(path);

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
    // each directory. I could automate this, or I could get a tiny bit of sleep... I think I know what I am
    // doing for now. Also damn this class has more comment than code... I am in the perfect state of mind to
    // yap because its a lot easier than doing code. Like I just type stuff and theres no error other than maybe
    // a spell check, but like screw grammer, only noah would care about that for this

    //this function was broken up into 2 to make this easier cleaner and more readable...
    //this will walk into subpackage directories just to automatically find what implements the interface of
    //GamePreset. It really should not have been this difficult.
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

                //it needs to be a bytecode class not a weird extra compiled one but im losing my mind
                //so I just hope this distinction is right
            } else if (file.getName().endsWith(".class") && !file.getName().contains("$")) {
                String className = packageName + "." + file.getName().substring(0, file.getName().length() - 6);
                try {
                    Class<?> clazz = Class.forName(className);
                    if (!Modifier.isAbstract(clazz.getModifiers()) && GamePreset.class.isAssignableFrom(clazz)) {

                        GamePreset instance = (GamePreset) clazz.getDeclaredConstructor().newInstance();
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