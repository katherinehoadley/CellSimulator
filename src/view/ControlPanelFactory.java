// Anna Rakes

package view;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;


public class ControlPanelFactory {
	
	/**
	 * method makeControlPanel
	 * @param searchName
	 * creates an ControlPanel object based on the given class name. 
	 * uses reflection to load a class by name and call its no argument constructor.
	 * @return a new ControlPanel object
	 */
	public ControlPanel makeControlPanel(String searchName) {
		try {
			Class<?> theClass = Class.forName("view."+searchName + "Controls");			

			Constructor<?> theConstructor = theClass.getConstructor();

			return (ControlPanel) theConstructor.newInstance();

		} catch (ClassNotFoundException e) {
			System.err.println("The problem is with the class name.");
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			System.err.println("The problem is with the method name and parameter list");
			e.printStackTrace();
		} catch (SecurityException e) {
			System.err.println("You don't have permission to access that method.");
			e.printStackTrace();
		} catch (InstantiationException e) {
			System.err.println("The problem was in invoking the constructor");
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			System.err.println("You don't have permission to access the constructor.");
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			System.err.println("The problem is with the parameter in the method call.");
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			System.err.println("The method or constructor threw an exception.");
			e.printStackTrace();
		}
		System.out.println("I am unable to create the Search.  Returning null.");
		return null;
	}

}
