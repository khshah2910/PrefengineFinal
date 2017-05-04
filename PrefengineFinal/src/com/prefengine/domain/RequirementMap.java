package com.prefengine.domain;

public class RequirementMap {
	public static String map(String requirement){
		String mappedOutput = null;
		if(requirement.equals("s_price")){
			mappedOutput = "Price";
		}
		if(requirement.equals("s_duration")){
			mappedOutput= "Duration";
		}
		if(requirement.equals("s_stops")){
			mappedOutput= "Stops";
		}
		if(requirement.equals("s_rank")){
			mappedOutput= "Rank";
		}
		return mappedOutput;
	}
}
