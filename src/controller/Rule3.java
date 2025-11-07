package controller;

public class Rule3 extends NeighborFinder implements UpdateRules {
	
//	A location with three neighbors will contain a bacteria in the next generation. 
//	If it was unoccupied before, a new bacteria is born. 
//	If it currently contains a bacteria, it remains. 


	@Override
	public void checkRule() {
		// TODO Auto-generated method stub
		// call method that calculates neighbors and returns int
		// if return == 3, call method to birth bacteria
		
	}

}
