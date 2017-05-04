package com.prefengine.util;

import java.util.Comparator;

import com.prefengine.domain.Itinerary;

public class MilageComarator implements Comparator<Itinerary>{

	@Override
	public int compare(Itinerary i1, Itinerary i2) {
		return (int) (i1.getTotalMiles()-i2.getTotalMiles());
	}

}
