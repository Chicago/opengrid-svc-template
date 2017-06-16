package org.opengrid.data.meta;

public class DotSizer {
	//string for calculating size based on value of field
	//default is the value itself with no calculation
	//@v represents the value
	private String calculator = "@v";

	public String getCalculator() {
		return calculator;
	}


	public void setCalculator(String calculator) {
		this.calculator = calculator;
	}
	
	
}
