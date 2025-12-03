// Author: Anna Rakes
// Tester class for all the factories.

package Tester;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import controller.Initialization;
import controller.InitializationFactory;
import controller.TestInitializationClass;
import model.GamePreset;
import model.PresetFactory;
import model.TestPresetClass;
import view.ControlPanel;
import view.ControlPanelFactory;
import view.TestControlPanelFactoryControls;

class FactoryTesters {
	
	   @Test // test that InitializationFactory can create a valid class of the correct type.
	    void testInitializationFactoryValidClass() {
	        InitializationFactory factory = new InitializationFactory();
	        Initialization initialization = factory.getInitialization("TestInitializationClass", new double[]{1.0, 2.0, 3.0});
	        assertNotNull(initialization);
	        assertTrue(initialization instanceof TestInitializationClass);
	    }

	    @Test // test that InitializationFactory returns null for initialization classes that don't exist
	    void testInitializationFactoryInvalidClass() {
	        InitializationFactory factory = new InitializationFactory();
	        Initialization initialization = factory.getInitialization("NonExistentClass", new double[]{1.0, 2.0, 3.0});
	        assertNull(initialization);
	    }

	    @Test // test that PresetFactory can create a valid class of the correct type. 
	    void testPresetFactoryValidClass() {
	        PresetFactory factory = new PresetFactory();
	        GamePreset preset = factory.makeSimulation("TestPresetClass");
	        assertNotNull(preset);
	        assertTrue(preset instanceof TestPresetClass);
	    }

	    @Test // test that InitializationFactory returns null for preset classes that don't exist
	    void testPresetFactoryInvalidClass() {
	        PresetFactory factory = new PresetFactory();
	        GamePreset preset = factory.makeSimulation("NonExistentClass");
	        assertNull(preset);
	    }
	    
	    @Test // test that ControlPanelFactory can create a valid class of the correct type.
	    void testControlPanelFactoryValidClass() {
	        ControlPanelFactory factory = new ControlPanelFactory();
	        ControlPanel chosenControlPanel = factory.makeControlPanel("TestControlPanelFactory");
	        assertNotNull(chosenControlPanel);
	        assertTrue(chosenControlPanel instanceof TestControlPanelFactoryControls);
	    }

	    @Test // test that ControlPanelFactory returns null for initialization classes that don't exist
	    void testControlPanelFactoryInvalidClass() {
	        ControlPanelFactory factory = new ControlPanelFactory();
	        ControlPanel chosenControlPanel = factory.makeControlPanel("NonExistentClass");
	        assertNull(chosenControlPanel);
	    }
}
