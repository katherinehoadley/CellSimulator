// Author: Anna Rakes

package Tester;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import model.GamePreset;
import model.Initialization;
import model.InitializationFactory;
import model.PresetFactory;
import model.TestInitializationClass;
import model.TestPresetClass;

class FactoryTesters {
	
	   @Test // test that InitializationFactory can create a valid class of the correct type.
	    void testInitializationFactoryValidClass() {
	        InitializationFactory factory = new InitializationFactory();
	        Initialization initialization = factory.getInitialization("TestInitializationClass");
	        assertNotNull(initialization);
	        assertTrue(initialization instanceof TestInitializationClass);
	    }

	    @Test // test that InitializationFactory returns null for initialization classes that don't exist
	    void testInitializationFactoryInvalidClass() {
	        InitializationFactory factory = new InitializationFactory();
	        Initialization initialization = factory.getInitialization("NonExistentClass");
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
}
