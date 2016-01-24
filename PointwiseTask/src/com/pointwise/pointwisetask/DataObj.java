package com.pointwise.pointwisetask;




public class DataObj implements Comparable<DataObj>{
	private String value;
	private int weight;

	public DataObj(String value, int weight){
		super();
		this.value = value;
		this.weight = weight;
	} 
	
	@Override
	public int compareTo(DataObj anotherDataObj) {
		if(this.weight - anotherDataObj.getWeight() == 0){
			return 1;
		}
		else{
			return anotherDataObj.getWeight() - this.weight;
		}
	}


	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public int getWeight() {
		return weight;
	}


	public void setWeight(int weight) {
		this.weight = weight;
	}
	

}
