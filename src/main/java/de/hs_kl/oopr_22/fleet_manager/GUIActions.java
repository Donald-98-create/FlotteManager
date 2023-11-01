package de.hs_kl.oopr_22.fleet_manager;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public interface GUIActions {

	/**
	 * This method collects all vehicles that conform to the given search parameters
	 * and then updates the vehicle-table in the GUI.
	 * 
	 * @param licensePlate The license plate search parameter.
	 */
	void search(String licensePlate);

	/**
	 * This method collects all vehicles that conform to the given search parameters
	 * and then updates the details-components in the GUI.
	 * 
	 * @param licensePlate The license plate search parameter.
	 */
	void showDetails(String licensePlate);

	/*
	 * !Note! -> not yet implemented to GUI This method takes the vehicle parameter
	 * and turns them into an Array and then puts it to the DefaultTableModel which
	 * is given as parameter. Also creates a new vehicle and adds it to the list.
	 * 
	 * @param licensePlate The license plate for the Array parameter.
	 * 
	 * @param vehicleType The vehicle type for the Array parameter.
	 * 
	 * @param location The location for the Array parameter
	 * 
	 * @param tableData The DefaultTableModel where the Array will be displayed
	 */
	// void save(String licensePlate, String vehicleType, Location location,
	// DefaultTableModel tableData,
	// LocationDatabase locationDatabase, VehicleDatabase vehicleDatabase);

	/**
	 * This method takes a vehicle as paramter and takes his attributes to turn it
	 * into an array for the dataTable.
	 * 
	 * @param vehicle
	 */
	Object[] VehicleToArray(Vehicle vehicle);

	/**
	 * This method adds vehicle to the tableData from the VehicleDatabase.
	 * 
	 * @param tableData The DefaultTabelModel where the data will be stored to.
	 * 
	 * @param database  In here are the all vehicles.
	 */
	void createVehicleDataTable(DefaultTableModel tableData, VehicleDatabase vehicleDatabase);

	double calculateCurrentValueTable(Vehicle vehicle);

	double calculateCurrentValueNew(double number, String vehicleType);

	/**
	 * This method adds the vehicle to the database and table.
	 * 
	 * @param vehicle   The vehicle which needs to be added.
	 * 
	 * @param database  The Vehicle Database where the vehicle will be added to.
	 * 
	 * @param tableData The defaultTableModel where the vehicle will be added to.
	 */
	void addPreConfiguredVehicle(Vehicle vehicle, VehicleDatabase database, DefaultTableModel tableData);

	/**
	 * Clears any selections made in the given JTable parameter.
	 * 
	 * @param table add the table that needs to be unselected
	 */
	void clearTblSelection(JTable table);

	/**
	 * computes average fuel consumption of all LKWs.
	 * 
	 * @param database The vehicle Database
	 */
	double computeAverageFuelConsumptionLKW(VehicleDatabase database);

	/**
	 * computes average fuel consumption of all other vehicles.
	 * 
	 * @param database The vehicle Database
	 */
	double computeAverageFuelConsumptionOthers(VehicleDatabase database);

	/**
	 * gets the list size of all LKWs.
	 * 
	 * @param database The vehicle Database
	 */
	int getLKWListSize(VehicleDatabase database);

	/**
	 * gets the list size of all other vehicles.
	 * 
	 * @param database The vehicle Database
	 */
	int getOthersListSize(VehicleDatabase database);

	/**
	 * updates average LKW consumption label and average consumption of other
	 * vehicles.
	 * 
	 * @param database                    The vehicle database
	 * @param lblAverageLKWConsumption
	 * @param lblAverageOthersConsumption
	 */
	void updateConsumptionLabels(VehicleDatabase database, JLabel lblAverageLKWConsumption,
			JLabel lblAverageOthersConsumption);

	/**
	 * computes all current puchase values.
	 * 
	 * @param database The database
	 * 
	 * @param lable    lblTotalVehicleValue
	 */
	void computeAllCurrentPurchaseValues(VehicleDatabase database, JLabel lblTotalVehicleValue);

	void createTxtFile(VehicleDatabase vehicleDatabase, LocationDatabase locDatabase, JLabel lblTotalVehicleValue,
			JLabel lblAverageLKWConsumption, JLabel lblAverageOthersConsumption);

	void consoleOutputTxtFile(VehicleDatabase vehicleDatabase, LocationDatabase locationDatabase,
			JLabel lblTotalVehicleValue, JLabel lblAverageLKWConsumption, JLabel lblAverageOthersConsumption);
}
