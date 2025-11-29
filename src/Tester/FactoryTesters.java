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
	
	   @Test
	    void testInitializationFactoryValidClass() {
	        InitializationFactory factory = new InitializationFactory();
	        Initialization init = factory.getInitialization("TestInitializationClass");
	        assertNotNull(init);
	        assertTrue(init instanceof TestInitializationClass);
	    }

	    @Test
	    void testInitializationFactoryInvalidClass() {
	        InitializationFactory factory = new InitializationFactory();
	        Initialization init = factory.getInitialization("NonExistentClass");
	        assertNull(init);
	    }

	    @Test
	    void testPresetFactoryValidClass() {
	        PresetFactory factory = new PresetFactory();
	        GamePreset preset = factory.makeSimulation("TestPresetClass");
	        assertNotNull(preset);
	        assertTrue(preset instanceof TestPresetClass);
	    }

	    @Test
	    void testPresetFactoryInvalidClass() {
	        PresetFactory factory = new PresetFactory();
	        GamePreset preset = factory.makeSimulation("NonExistentClass");
	        assertNull(preset);
	    }
	
	
}
