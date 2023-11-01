package de.hs_kl.oopr_22.fleet_manager;

import java.util.ArrayList;

/**
 * Stores all vehicles.
 */
public class VehicleDatabase {

	private final ArrayList<Vehicle> vehicleDbList;

	public VehicleDatabase() {
		vehicleDbList = new ArrayList<>();
	}

	public ArrayList<Vehicle> getVehicleDbList() {
		return vehicleDbList;
	}

}
