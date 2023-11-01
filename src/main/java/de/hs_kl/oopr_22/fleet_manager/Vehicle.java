package de.hs_kl.oopr_22.fleet_manager;

public class Vehicle {
	private String licensePlate;
	private String vehicleType;
	private Location location;
	private String purchaseValue;
	private String consumption;
	private boolean hasTrailer = false;
	private String yearOfPurchase;

	public Vehicle(String licensePlate, String vehicleType, Location location, String purchaseValue, String consumption,
			boolean hasTrailer, String yearOfPurchase) {
		this.setLicensePlate(licensePlate);
		this.setVehicleType(vehicleType);
		this.setLocation(location);
		this.setPurchaseValue(purchaseValue);
		this.setConsumption(consumption);
		this.setHasTrailer(hasTrailer);
		this.setYearOfPurchase(yearOfPurchase);
	}

	public String toString() {
		return "Kennzeichen: " + licensePlate + " | Fahrzeugart: " + vehicleType + " | Ort: " + location
				+ " | Anschaffungswert: " + purchaseValue + " | Verbrauch: " + consumption + " |";

	}

	public String getLicensePlate() {
		return licensePlate;
	}

	public void setLicensePlate(String licensePlate) {
		this.licensePlate = licensePlate;
	}

	public String getVehicleType() {
		return vehicleType;
	}

	public void setVehicleType(String vehicleType) {
		this.vehicleType = vehicleType;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public String getPurchaseValue() {
		return purchaseValue;
	}

	public void setPurchaseValue(String purchaseValue) {
		this.purchaseValue = purchaseValue;
	}

	public String getConsumption() {
		return consumption;
	}

	public void setConsumption(String consumption) {
		this.consumption = consumption;
	}

	public boolean getHasTrailer() {
		return hasTrailer;
	}

	public void setHasTrailer(boolean hasTrailer) {
		this.hasTrailer = hasTrailer;
	}

	public String getYearOfPurchase() {
		return yearOfPurchase;
	}

	public void setYearOfPurchase(String yearOfPurchase) {
		this.yearOfPurchase = yearOfPurchase;
	}
}
