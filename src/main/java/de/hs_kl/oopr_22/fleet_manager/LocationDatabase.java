package de.hs_kl.oopr_22.fleet_manager;

import java.util.ArrayList;

/**
 * Stores all locations.
 */
public class LocationDatabase {

	// Tipp: Geben Sie einer Location eine ID!
	private final ArrayList<Location> locationDbList;
	private final ArrayList<String> allLocationNames;

	public LocationDatabase() {
		locationDbList = new ArrayList<>();
		allLocationNames = new ArrayList<>();
	}

	public ArrayList<Location> getLocationDbList() {
		return locationDbList;
	}

	public ArrayList<String> getAllLocationNames() {
		return allLocationNames;
	}

	public void addLocation(Location location) {
		locationDbList.add(location);
		allLocationNames.add(location.getDisplayedName());
	}
}
