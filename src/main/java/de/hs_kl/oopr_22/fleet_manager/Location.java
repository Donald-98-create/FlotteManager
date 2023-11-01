package de.hs_kl.oopr_22.fleet_manager;

public class Location {

	private int ID;
	private String displayedName;
	private String street;
	private String zipCode;
	private String city;

	public Location(int ID, String displayedName, String street, String zipCode, String city) {
		this.setID(ID);
		this.setDisplayedName(displayedName);
		this.setStreet(street);
		this.setZipCode(zipCode);
		this.setCity(city);
	}

	public String toString() {
		return displayedName;
	}

	public String allToString() {
		return "ID: " + ID + " | Anzeigename: " + displayedName + " | Straße: " + street + " | Postleitzahl: " + zipCode
				+ " | Stadt: " + city + " | ";
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public String getDisplayedName() {
		return displayedName;
	}

	public void setDisplayedName(String displayedName) {
		this.displayedName = displayedName;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

}
