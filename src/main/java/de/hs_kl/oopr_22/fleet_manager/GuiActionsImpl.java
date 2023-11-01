package de.hs_kl.oopr_22.fleet_manager;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class GuiActionsImpl implements GUIActions {

	public GuiActionsImpl(GUI gui) {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void search(String licensePlate) {
		// TODO Auto-generated method stub

	}

	@Override
	public void showDetails(String licensePlate) {
		// TODO Auto-generated method stub

	}

	/*
	 * @Override public void save(String licensePlate, String vehicleType, Location
	 * location, DefaultTableModel tableData, LocationDatabase locationDatabase,
	 * VehicleDatabase vehicleDatabase) { Object[] inputData = {
	 * licensePlate.toUpperCase(), vehicleType, location };
	 * tableData.addRow(inputData);
	 * 
	 * Location tempLocation = null; for (Location loc :
	 * locationDatabase.getLocationDbList()) { if
	 * (loc.getDisplayedName().equals(location.getDisplayedName())) { tempLocation =
	 * loc; } } Vehicle vehicle = new Vehicle(licensePlate, vehicleType,
	 * tempLocation, "XXX", "XXX", false);
	 * vehicleDatabase.getVehicleDbList().add(vehicle); }
	 */
	@Override
	public Object[] VehicleToArray(Vehicle vehicle) {
		String licensePlate = vehicle.getLicensePlate();
		String vehicleType = vehicle.getVehicleType();
		Location location = vehicle.getLocation();
		String purchaseValue = vehicle.getPurchaseValue();
		String consumption = vehicle.getConsumption();
		Double currentValue = calculateCurrentValueTable(vehicle);

		Object[] Array = { licensePlate.toUpperCase(), vehicleType, location, purchaseValue, consumption,
				currentValue };
		return Array;
	}

	@Override
	public double calculateCurrentValueTable(Vehicle vehicle) {
		double currentValue = Double.parseDouble(vehicle.getPurchaseValue());
		for (int i = 0; i < Integer.valueOf(vehicle.getYearOfPurchase()); i++) {
			if (i < 3 && vehicle.getVehicleType() == "PKW") {
				currentValue = currentValue - currentValue * 0.15;
			} else {
				currentValue = currentValue - currentValue * 0.05;
			}

		}
		return (double) Math.round(100 * currentValue) / 100;
	}

	@Override
	public void createVehicleDataTable(DefaultTableModel tableData, VehicleDatabase database) {
		for (Vehicle vehicle : database.getVehicleDbList()) {
			tableData.addRow(VehicleToArray(vehicle));
		}
	}

	@Override
	public void addPreConfiguredVehicle(Vehicle vehicle, VehicleDatabase database, DefaultTableModel dataTable) {
		database.getVehicleDbList().add(vehicle);
		dataTable.addRow(VehicleToArray(vehicle));
	}

	@Override
	public void clearTblSelection(JTable table) {
		table.getSelectionModel().clearSelection();
	}

	@Override
	public double calculateCurrentValueNew(double purchaseValue, String vehicleType) {
		double currentValue = purchaseValue;
		if (vehicleType == "PKW") {
			currentValue = currentValue - currentValue * 0.15;
		} else {
			currentValue = currentValue - currentValue * 0.05;
		}

		return (double) Math.round(100 * currentValue) / 100;
	}

	@Override
	public double computeAverageFuelConsumptionLKW(VehicleDatabase database) {
		double result = 0;
		for (Vehicle vehicle : database.getVehicleDbList()) {
			if (vehicle.getVehicleType().equals("LKW")) {
				result += Double.parseDouble(vehicle.getConsumption());
			}
		}
		result = result / getLKWListSize(database);
		return Math.round(100 * result) / 100;
	}

	@Override
	public double computeAverageFuelConsumptionOthers(VehicleDatabase database) {
		double result = 0;
		for (Vehicle vehicle : database.getVehicleDbList()) {
			if (vehicle.getVehicleType() != "LKW") {
				result += Double.parseDouble(vehicle.getConsumption());
			}
		}
		result = result / getOthersListSize(database);
		return (double) Math.round(100 * result) / 100;
	}

	@Override
	public int getLKWListSize(VehicleDatabase database) {
		List<Vehicle> tempList = new ArrayList<>();
		for (Vehicle vehicle : database.getVehicleDbList()) {
			if (vehicle.getVehicleType().equals("LKW")) {
				tempList.add(vehicle);
			}
		}
		return tempList.size();
	}

	@Override
	public int getOthersListSize(VehicleDatabase database) {
		List<Vehicle> tempList = new ArrayList<>();
		for (Vehicle vehicle : database.getVehicleDbList()) {
			if (vehicle.getVehicleType() != "LKW") {
				tempList.add(vehicle);
			}
		}
		return tempList.size();
	}

	@Override
	public void updateConsumptionLabels(VehicleDatabase database, JLabel lblAverageLKWConsumption,
			JLabel lblAverageOthersConsumption) {
		String averageLKWConsumption = Double.toString(computeAverageFuelConsumptionLKW(database));
		String averageOthersConsumption = Double.toString(computeAverageFuelConsumptionOthers(database));
		lblAverageLKWConsumption.setText(averageLKWConsumption);
		lblAverageOthersConsumption.setText(averageOthersConsumption);
	}

	@Override
	public void computeAllCurrentPurchaseValues(VehicleDatabase database, JLabel lblTotalVehicleValue) {
		double value = 0d;
		for (Vehicle vehicle : database.getVehicleDbList()) {
			value += calculateCurrentValueTable(vehicle);
		}

		lblTotalVehicleValue.setText(Double.toString(value));
	}

	@Override
	public void createTxtFile(VehicleDatabase vehicleDatabase, LocationDatabase locationDatabase,
			JLabel lblTotalVehicleValue, JLabel lblAverageLKWConsumption, JLabel lblAverageOthersConsumption) {
		int vehicleDatabaseSize = vehicleDatabase.getVehicleDbList().size();
		int locationDatabaseSize = locationDatabase.getLocationDbList().size();
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter("FlottenDaten.txt"));
			writer.write("Daten der Flotte: " + "\n\n");
			writer.write("Gesamtanzahl der Fahrzeuge: " + " " + vehicleDatabaseSize);
			writer.write("\nGesamtwert aller Fahrzeuge: " + lblTotalVehicleValue.getText());
			writer.write("\nDuchschnittsverbrauch aller LKWs: " + lblAverageLKWConsumption.getText());
			writer.write("\nDurchschnittsvebrauch sonstiger Fahrzeuge: " + lblAverageOthersConsumption.getText());
			writer.write("\n\nAlle Fahrzeuge:\n");
			for (int i = 0; i < vehicleDatabaseSize; i++) {
				writer.write(
						i + 1 + "." + "Fahrzeug:\n" + vehicleDatabase.getVehicleDbList().get(i).toString() + "\n\n");
			}
			writer.write("\nGesamtanzahl an Orten: " + locationDatabaseSize);
			writer.write("\n\nAlle Orte: \n");
			for (Location location : locationDatabase.getLocationDbList()) {
				writer.write(location.allToString() + "\n");
			}

			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void consoleOutputTxtFile(VehicleDatabase vehicleDatabase, LocationDatabase locationDatabase,
			JLabel lblTotalVehicleValue, JLabel lblAverageLKWConsumption, JLabel lblAverageOthersConsumption) {
		int vehicleDatabaseSize = vehicleDatabase.getVehicleDbList().size();
		int locationDatabaseSize = locationDatabase.getLocationDbList().size();

		System.out.println(("Daten der Flotte: " + "\n\n"));
		System.out.println(("Gesamtanzahl der Fahrzeuge: " + " " + vehicleDatabaseSize));
		System.out.println(("\nGesamtwert aller Fahrzeuge: " + lblTotalVehicleValue.getText()));
		System.out.println(("\nDuchschnittsverbrauch aller LKWs: " + lblAverageLKWConsumption.getText()));
		System.out.println(("\nDurchschnittsvebrauch sonstiger Fahrzeuge: " + lblAverageOthersConsumption.getText()));
		System.out.println(("\n\nAlle Fahrzeuge:\n"));
		for (int i = 0; i < vehicleDatabaseSize; i++) {
			System.out.println(
					i + 1 + "." + "Fahrzeug:\n" + vehicleDatabase.getVehicleDbList().get(i).toString() + "\n\n");
		}
		System.out.println("\nGesamtanzahl an Orten: " + locationDatabaseSize);
		System.out.println("\n\nAlle Orte: \n");
		for (Location location : locationDatabase.getLocationDbList()) {
			System.out.println(location.allToString() + "\n");
		}

	}
}
