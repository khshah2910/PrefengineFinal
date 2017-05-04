package com.prefengine.util;

import java.util.Comparator;

import com.prefengine.domain.Itinerary;

public class StopsComparator implements Comparator<Itinerary>{

	@Override
	public int compare(Itinerary i1, Itinerary i2) {
		return i1.getNumberOfStops()-i2.getNumberOfStops();
	}

}
