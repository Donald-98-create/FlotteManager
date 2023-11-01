package de.hs_kl.oopr_22.fleet_manager;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

@SuppressWarnings("serial")
public class GUI extends JFrame {

	private GUIActions guiActions = new GuiActionsImpl(this); // HIERFÜR MUESSEN SIE EINE KLASSE BAUEN UND ALS INSTANZ
																// ZUWEISEN

	private JTable tblVehicles;
	private JTextField txtSearchZipcode;
	private JTextField txtSearchLicensePlateNumber;
	private JTextField txtDetailsLicensePlateNumber;
	private JTextField txtLocationStreet;
	private JTextField txtLocationName;
	private JTextField txtLocationZipcode;
	private JTextField txtLocationCity;
	private JTextField txtDetailsPurchaseValue;
	private JTextField txtDetailsConsumption;
	private JComboBox<?> cmbDetailsVehiclesType;
	private JComboBox<?> cmbSearchVehicleType;
	private JComboBox<String> cmbDetailsLocation;
	private JCheckBox checkboxDetailsTrailer;
	private DefaultTableModel tableData;

	private JPanel contentPanel;
	private JLabel lblSearchLicensePlateNumber;
	private JPanel pnlDetailsVehicles;
	private JLabel lblVehicleDetails;
	private JLabel lblDetailsVehicleType;
	private JLabel lblDetailsLicensePlateNumber;
	private JLabel lblDetailsLocation;
	private JLabel lblDetailsPurchaseValue;
	private JLabel lblDetailsConsumption;
	private JLabel lblSearchEmptyCell;
	private JSeparator hlnLocation;
	private JLabel lblLocation;
	private JPanel pnlLocation;
	private JLabel lblLocationDisplayName;
	private JLabel lblLocationStreet;
	private JLabel lblLocationZipcode;
	private JLabel lblLocationCity;
	private JLabel lblDetailsTrailer;
	private JButton btnAddLocation;
	private JButton btnSave;
	private JButton btnNew;
	private JButton btnSearch;
	private JButton btnReset;
	private JButton btnExport;
	private JLabel lblImage;
	JLabel lblAverageLKWConsumption;
	JLabel lblAverageOthersConsumption;
	JLabel lblTotalVehicleValue;

	private Location kaiserslautern = new Location(1, "Kaiserslautern", "Foostrasse 1", "66123", "Kaiserslautern");
	private Location zweibruecken = new Location(2, "Zweibrücken", "FooAllee 2", "66666", "Zweibrücken");

	private String empty = "";
	private String typeLKW = "LKW";
	private String typePKW = "PKW";
	private String typeTransporter = "Transporter";
	private final String[] cmbDetailsContentVehicles = { typeLKW, typePKW, typeTransporter };
	private Object[] cmbDetailsContentLocations = { kaiserslautern.getDisplayedName(),
			zweibruecken.getDisplayedName() };

	VehicleDatabase vehicleDatabase1 = new VehicleDatabase();
	LocationDatabase locationDatabase1 = new LocationDatabase();

	private Vehicle vehicle1 = new Vehicle("KL-123", typeLKW, kaiserslautern, "20000", "8", true, "1");
	private Vehicle vehicle2 = new Vehicle("ZW-22", typePKW, zweibruecken, "70000", "15", false, "2");
	private Vehicle vehicle3 = new Vehicle("KL-133", typePKW, kaiserslautern, "50000", "10", false, "5");
	private Vehicle vehicle4 = new Vehicle("ZW-990", typeLKW, zweibruecken, "50000", "10", false, "4");
	private Vehicle vehicle5 = new Vehicle("KL-133", typeTransporter, kaiserslautern, "50000", "10", false, "6");
	private Vehicle vehicle6 = new Vehicle("KL-133", typeLKW, kaiserslautern, "50000", "10", false, "10");
	private Vehicle vehicle7 = new Vehicle("ZW-453", typeTransporter, zweibruecken, "50000", "10", false, "12");

	boolean isLKW = false;

	String averageLKWConsumption = Double.toString(guiActions.computeAverageFuelConsumptionLKW(vehicleDatabase1));
	String averageOthersConsumption = Double.toString(guiActions.computeAverageFuelConsumptionOthers(vehicleDatabase1));

	public GUI() {
		buildGui();
		registerGuiActions();
		addPreConfigLocations();
		guiActions.updateConsumptionLabels(vehicleDatabase1, lblAverageLKWConsumption, lblAverageOthersConsumption);
		guiActions.computeAllCurrentPurchaseValues(vehicleDatabase1, lblTotalVehicleValue);
	}

	/**
	 * Adds actions to the GUI elements
	 */
	private void registerGuiActions() {
		btnSearch.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// HIER KÖNNEN SIE WEITERE PARAMETER FÜR DIE SUCHE HINZUFÜGEN
				guiActions.search(txtSearchLicensePlateNumber.getText());

				filterTable();
				guiActions.clearTblSelection(tblVehicles);
			}

			private void filterTable() {
				tableData.setRowCount(0); // clears the table
				for (Vehicle vehicle : vehicleDatabase1.getVehicleDbList()) {
					if (vehicle.getLicensePlate().startsWith(txtSearchLicensePlateNumber.getText().toUpperCase())
							&& cmbSearchVehicleType.getSelectedItem().equals(vehicle.getVehicleType())
							&& vehicle.getLocation().getZipCode().startsWith(txtSearchZipcode.getText())) {
						tableData.addRow(guiActions.VehicleToArray(vehicle));
					}
				}
			}
		});

		tblVehicles.addMouseListener(new MouseAdapter() {
			/*
			 * Opens the detail area upon a user's click into the table
			 */
			@Override
			public void mouseClicked(MouseEvent e) {
				// Get value of selected row and first column (index 0)
				int row = tblVehicles.getSelectedRow();
				int column = 0;

				String licensePlateNumber = tblVehicles.getValueAt(row, column).toString();
				String ContentVehicle = tblVehicles.getValueAt(row, column + 1).toString();
				String ContentLocation = tblVehicles.getValueAt(row, column + 2).toString();
				String ContentPurchaseValue = tblVehicles.getValueAt(row, column + 3).toString();
				String ContentConsumption = tblVehicles.getValueAt(row, column + 4).toString();

				guiActions.showDetails(licensePlateNumber); // wird für MVP02 nicht benötigt
				txtDetailsLicensePlateNumber.setText(licensePlateNumber);
				txtDetailsPurchaseValue.setText(ContentPurchaseValue);
				txtDetailsConsumption.setText(ContentConsumption);

				for (int i = 0; i < cmbDetailsContentVehicles.length; i++) {
					if (ContentVehicle == cmbDetailsContentVehicles[i]) {
						cmbDetailsVehiclesType.setSelectedIndex(i);
					}
				}

				for (int i = 0; i < locationDatabase1.getAllLocationNames().size(); i++) {
					if (ContentLocation == locationDatabase1.getAllLocationNames().get(i)) {
						cmbDetailsLocation.setSelectedIndex(i);
					}
				}
				for (Vehicle vehicle : vehicleDatabase1.getVehicleDbList()) {
					if (licensePlateNumber == vehicle.getLicensePlate()) {
						updateCheckboxDetailsTrailers(vehicle);
					}
				}

				addCheckboxDetailsTrailer();
			}

		});

		btnSave.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String licensePlate = txtDetailsLicensePlateNumber.getText();
				String vehicleType = (String) cmbDetailsVehiclesType.getSelectedItem();
				String location = (String) cmbDetailsLocation.getSelectedItem();
				String purchaseValue = txtDetailsPurchaseValue.getText();
				String consumption = txtDetailsConsumption.getText();
				if (purchaseValue.equals(empty) || consumption.equals(empty) || licensePlate.equals(empty)
						|| vehicleType.equals(empty) || location.equals(empty)) {
					JOptionPane.showMessageDialog(null, "Sie müssen alle Textfelder ausfüllen!");
				} else {
					boolean hasTrailer = checkboxDetailsTrailer.isSelected();
					double currentValue = guiActions.calculateCurrentValueNew(Double.parseDouble(purchaseValue),
							vehicleType);
					Object[] inputData = { licensePlate.toUpperCase(), vehicleType, location, purchaseValue,
							consumption, currentValue };

					int selectedRow = tblVehicles.getSelectedRow();
					if (tblVehicles.isRowSelected(selectedRow)) { // Checks if a row is selected
						/*
						 * Sets the values in the table to the newly changed
						 */
						tblVehicles.setValueAt(purchaseValue, selectedRow, 3);
						tblVehicles.setValueAt(consumption, selectedRow, 4);
						/*
						 * loop checks for vehicle with the same licensePlate and sets the newly changed
						 * attributes for the vehicle in the list.
						 */
						for (Vehicle vehicle : vehicleDatabase1.getVehicleDbList()) {
							String tempLicensePlate = vehicle.getLicensePlate();
							if (tempLicensePlate.equals(tblVehicles.getValueAt(selectedRow, 0))) {
								vehicle.setPurchaseValue(purchaseValue);
								vehicle.setConsumption(consumption);
								vehicle.setHasTrailer(checkboxDetailsTrailer.isSelected());
								break;
							}
						}
						guiActions.updateConsumptionLabels(vehicleDatabase1, lblAverageLKWConsumption,
								lblAverageOthersConsumption);
						guiActions.computeAllCurrentPurchaseValues(vehicleDatabase1, lblTotalVehicleValue);
						guiActions.clearTblSelection(tblVehicles);
					} else {
						tableData.addRow(inputData);
						/*
						 * This loop finds the location with the same name and assigns it to
						 * tempLocation. tempLocation will be assigned to the new vehicle.
						 */
						Location tempLocation = null;
						for (Location loc : locationDatabase1.getLocationDbList()) {
							if (loc.getDisplayedName().equals(location)) {
								tempLocation = loc;
							}
						}
						String yearOfPurchaseNew = "1";
						Vehicle vehicle = new Vehicle(licensePlate, vehicleType, tempLocation, purchaseValue,
								consumption, hasTrailer, yearOfPurchaseNew);
						vehicleDatabase1.getVehicleDbList().add(vehicle);
						guiActions.updateConsumptionLabels(vehicleDatabase1, lblAverageLKWConsumption,
								lblAverageOthersConsumption);
						guiActions.computeAllCurrentPurchaseValues(vehicleDatabase1, lblTotalVehicleValue);
					}
				}
			}

		});

		btnAddLocation.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				guiActions.clearTblSelection(tblVehicles);
				String displayedName = txtLocationName.getText();
				String street = txtLocationStreet.getText();
				String zipcode = txtLocationZipcode.getText();
				String city = txtLocationCity.getText();

				if (displayedName.equals(empty) || street.equals(empty) || zipcode.equals(empty)
						|| city.equals(empty)) {
					JOptionPane.showMessageDialog(null, "Füllen Sie bitte alle Felder aus!"); // temporarily "input
																								// Verifier", will be
																								// changed in future
				} else {
					int ID = locationDatabase1.getLocationDbList().size() + 1;
					Location location = new Location(ID, displayedName, street, zipcode, city);
					locationDatabase1.getLocationDbList().add(location);

					locationDatabase1.getAllLocationNames().add(location.getDisplayedName());

					String[] tempAr = locationDatabase1.getAllLocationNames().toArray(new String[0]);

					cmbDetailsLocation.setModel(new DefaultComboBoxModel<String>(tempAr));

					cmbDetailsContentLocations = locationDatabase1.getLocationDbList().toArray();
				}
			}
		});

		btnReset.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				tableData.setRowCount(0);
				guiActions.createVehicleDataTable(tableData, vehicleDatabase1);
			}
		});

		btnNew.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				txtDetailsLicensePlateNumber.setText(null);
				cmbDetailsVehiclesType.setSelectedItem(null);
				cmbDetailsLocation.setSelectedItem(null);
				txtDetailsPurchaseValue.setText(null);
				txtDetailsConsumption.setText(null);
				guiActions.clearTblSelection(tblVehicles);
			}
		});

		btnExport.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				guiActions.createTxtFile(vehicleDatabase1, locationDatabase1, lblTotalVehicleValue,
						lblAverageLKWConsumption, lblAverageOthersConsumption);
				guiActions.consoleOutputTxtFile(vehicleDatabase1, locationDatabase1, lblTotalVehicleValue,
						lblAverageLKWConsumption, lblAverageOthersConsumption);
			}
		});

		contentPanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				tblVehicles.getSelectionModel().clearSelection();
			}
		});

		cmbDetailsVehiclesType.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				addCheckboxDetailsTrailer();
			}
		});
	}

	/*
	 * adds preconfigured locations to database
	 */
	private void addPreConfigLocations() {
		locationDatabase1.addLocation(kaiserslautern);
		locationDatabase1.addLocation(zweibruecken);
	}

	private void addCheckboxDetailsTrailer() {
		if (cmbDetailsVehiclesType.getSelectedItem() == "LKW") {

			isLKW = true;
		} else {
			isLKW = false;
		}
		lblDetailsTrailer.setVisible(isLKW);
		checkboxDetailsTrailer.setVisible(isLKW);
	}

	private void updateCheckboxDetailsTrailers(Vehicle vehicle) {
		checkboxDetailsTrailer.setSelected(vehicle.getHasTrailer());
	}

	/**
	 * Constructs the gui elements and puts them together
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private void buildGui() {
		/*
		 * Header
		 */
		setTitle("Flottenmanager");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1024, 535);
		contentPanel = new JPanel();
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPanel);
		contentPanel.setLayout(null);

		JLabel lblApplicationTitle = new JLabel("Flottenmanager");
		lblApplicationTitle.setFont(new Font("Tahoma", Font.PLAIN, 35));
		lblApplicationTitle.setBounds(10, 10, 255, 43);
		contentPanel.add(lblApplicationTitle);

		lblImage = new JLabel("");
		lblImage.setIcon(new ImageIcon(GUI.class.getResource("/main/resources/icon.png")));
		lblImage.setBounds(275, 10, 80, 43);
		contentPanel.add(lblImage);

		btnExport = new JButton("Export");
		btnExport.setBounds(605, 31, 80, 21);
		contentPanel.add(btnExport);

		/*
		 * Search panel
		 */
		JPanel pnlSearchVehicles = new JPanel();
		pnlSearchVehicles.setBorder(null);
		pnlSearchVehicles.setBounds(10, 63, 685, 43);
		contentPanel.add(pnlSearchVehicles);

		txtSearchLicensePlateNumber = new JTextField(empty);

		txtSearchLicensePlateNumber.setColumns(10);

		txtSearchZipcode = new JTextField(empty);
		txtSearchZipcode.setColumns(10);
		pnlSearchVehicles.setLayout(new GridLayout(2, 4, 0, 0));

		lblSearchLicensePlateNumber = new JLabel("Kennzeichen");
		pnlSearchVehicles.add(lblSearchLicensePlateNumber);

		JLabel lblSearchVehicleType = new JLabel("Fahrzeugart");
		pnlSearchVehicles.add(lblSearchVehicleType);

		JLabel lblSearchZipcode = new JLabel("Standort PLZ");
		pnlSearchVehicles.add(lblSearchZipcode);

		lblSearchEmptyCell = new JLabel(" ");
		pnlSearchVehicles.add(lblSearchEmptyCell);
		pnlSearchVehicles.add(txtSearchLicensePlateNumber);

		cmbSearchVehicleType = new JComboBox(cmbDetailsContentVehicles);
		pnlSearchVehicles.add(cmbSearchVehicleType);
		pnlSearchVehicles.add(txtSearchZipcode);

		btnSearch = new JButton("Suchen");
		pnlSearchVehicles.add(btnSearch);

		/*
		 * Vehicle table
		 */
		JScrollPane pnlTable = new JScrollPane();
		pnlTable.setBounds(10, 180, 685, 283);
		contentPanel.add(pnlTable);

		tblVehicles = new JTable();

		tblVehicles.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		pnlTable.setViewportView(tblVehicles);
		tableData = new DefaultTableModel(new Object[][] {}, new Object[] { "Kennzeichen", "Fahrzeugart", "Standort",
				"Anschaffungswert", "Verbrauch/100km", "aktueller Wert" });
		tblVehicles.setModel(tableData);
		tblVehicles.setBorder(new LineBorder(new Color(0, 0, 0)));
		// disables editable cells
		tblVehicles.setDefaultEditor(Object.class, null);

		guiActions.addPreConfiguredVehicle(vehicle1, vehicleDatabase1, tableData);
		guiActions.addPreConfiguredVehicle(vehicle2, vehicleDatabase1, tableData);
		guiActions.addPreConfiguredVehicle(vehicle3, vehicleDatabase1, tableData);
		guiActions.addPreConfiguredVehicle(vehicle4, vehicleDatabase1, tableData);
		guiActions.addPreConfiguredVehicle(vehicle5, vehicleDatabase1, tableData);
		guiActions.addPreConfiguredVehicle(vehicle6, vehicleDatabase1, tableData);
		guiActions.addPreConfiguredVehicle(vehicle7, vehicleDatabase1, tableData);

		/*
		 * Details panel
		 */
		pnlDetailsVehicles = new JPanel();
		pnlDetailsVehicles.setBounds(767, 74, 216, 150);
		contentPanel.add(pnlDetailsVehicles);
		pnlDetailsVehicles.setLayout(new GridLayout(6, 2, 0, 0));

		lblDetailsLicensePlateNumber = new JLabel("Kenzeichen:");
		lblDetailsLicensePlateNumber.setHorizontalAlignment(SwingConstants.LEFT);
		pnlDetailsVehicles.add(lblDetailsLicensePlateNumber);

		txtDetailsLicensePlateNumber = new JTextField();
		pnlDetailsVehicles.add(txtDetailsLicensePlateNumber);
		txtDetailsLicensePlateNumber.setColumns(10);

		lblDetailsVehicleType = new JLabel("Fahrzeugart:");
		lblDetailsVehicleType.setHorizontalAlignment(SwingConstants.LEFT);
		pnlDetailsVehicles.add(lblDetailsVehicleType);

		cmbDetailsVehiclesType = new JComboBox(cmbDetailsContentVehicles);
		pnlDetailsVehicles.add(cmbDetailsVehiclesType);

		lblDetailsLocation = new JLabel("Standort:");
		lblDetailsLocation.setHorizontalAlignment(SwingConstants.LEFT);
		pnlDetailsVehicles.add(lblDetailsLocation);

		cmbDetailsLocation = new JComboBox(cmbDetailsContentLocations);
		pnlDetailsVehicles.add(cmbDetailsLocation);

		lblVehicleDetails = new JLabel("Fahrzeugdetails");
		lblVehicleDetails.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblVehicleDetails.setBounds(767, 35, 126, 18);
		contentPanel.add(lblVehicleDetails);

		lblDetailsPurchaseValue = new JLabel("Anschaffungswert:");
		lblDetailsPurchaseValue.setHorizontalAlignment(SwingConstants.LEFT);
		pnlDetailsVehicles.add(lblDetailsPurchaseValue);

		txtDetailsPurchaseValue = new JTextField();
		pnlDetailsVehicles.add(txtDetailsPurchaseValue);

		lblDetailsConsumption = new JLabel("Verbrauch/100km:");
		lblDetailsConsumption.setHorizontalAlignment(SwingConstants.LEFT);
		pnlDetailsVehicles.add(lblDetailsConsumption);

		txtDetailsConsumption = new JTextField();
		pnlDetailsVehicles.add(txtDetailsConsumption);
		txtDetailsConsumption.setColumns(3);

		btnNew = new JButton("Neu");
		btnNew.setBounds(920, 31, 58, 21);
		contentPanel.add(btnNew);

		btnReset = new JButton("Alle anzeigen");
		btnReset.setBounds(300, 130, 110, 25);
		contentPanel.add(btnReset);

		lblDetailsTrailer = new JLabel("Anhänger:");
		pnlDetailsVehicles.add(lblDetailsTrailer);

		checkboxDetailsTrailer = new JCheckBox();
		pnlDetailsVehicles.add(checkboxDetailsTrailer);

		JSeparator hlnDetails = new JSeparator();
		hlnDetails.setBounds(767, 63, 214, 7);
		contentPanel.add(hlnDetails);

		/*
		 * Standorte
		 */
		hlnLocation = new JSeparator();
		hlnLocation.setBounds(769, 346, 214, 7);
		contentPanel.add(hlnLocation);

		lblLocation = new JLabel("Standort");
		lblLocation.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblLocation.setBounds(767, 318, 126, 18);
		contentPanel.add(lblLocation);

		pnlLocation = new JPanel();
		pnlLocation.setBounds(767, 363, 216, 76);
		contentPanel.add(pnlLocation);
		pnlLocation.setLayout(new GridLayout(4, 2, 0, 0));

		lblLocationDisplayName = new JLabel("Anzeigename:");
		lblLocationDisplayName.setHorizontalAlignment(SwingConstants.LEFT);
		pnlLocation.add(lblLocationDisplayName);

		txtLocationName = new JTextField(empty);
		pnlLocation.add(txtLocationName);
		txtLocationName.setColumns(10);

		lblLocationStreet = new JLabel("Straße:");
		lblLocationStreet.setHorizontalAlignment(SwingConstants.LEFT);
		pnlLocation.add(lblLocationStreet);

		txtLocationStreet = new JTextField(empty);
		txtLocationStreet.setColumns(10);
		pnlLocation.add(txtLocationStreet);

		lblLocationZipcode = new JLabel("PLZ:");
		lblLocationZipcode.setHorizontalAlignment(SwingConstants.LEFT);
		pnlLocation.add(lblLocationZipcode);

		txtLocationZipcode = new JTextField(empty);
		pnlLocation.add(txtLocationZipcode);

		lblLocationCity = new JLabel("Stadt:");
		lblLocationCity.setHorizontalAlignment(SwingConstants.LEFT);
		pnlLocation.add(lblLocationCity);

		txtLocationCity = new JTextField(empty);
		pnlLocation.add(txtLocationCity);

		btnAddLocation = new JButton("Hinzufügen");
		btnAddLocation.setBounds(880, 449, 103, 21);
		contentPanel.add(btnAddLocation);

		btnSave = new JButton("Speichern");
		btnSave.setBounds(880, 274, 103, 21);
		contentPanel.add(btnSave);

		/*
		 * Kennzahlen ("key figures")
		 */

		JPanel pnlKeyFigures = new JPanel();
		pnlKeyFigures.setBounds(10, 468, 650, 20);
		contentPanel.add(pnlKeyFigures);
		pnlKeyFigures.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JLabel lblForTotalValue = new JLabel("Gesamtwert:");
		pnlKeyFigures.add(lblForTotalValue);

		lblTotalVehicleValue = new JLabel("0");
		lblForTotalValue.setLabelFor(lblTotalVehicleValue);
		pnlKeyFigures.add(lblTotalVehicleValue);

		JLabel lblForAverageLKWConsumption = new JLabel("   ⌀ Verbrauch (LKWs):");
		pnlKeyFigures.add(lblForAverageLKWConsumption);

		lblAverageLKWConsumption = new JLabel("0");
		pnlKeyFigures.add(lblAverageLKWConsumption);

		JLabel lblForAverageOthersConsumption = new JLabel("   ⌀ Verbrauch (Sonstige):");
		pnlKeyFigures.add(lblForAverageOthersConsumption);

		lblAverageOthersConsumption = new JLabel("0");
		pnlKeyFigures.add(lblAverageOthersConsumption);

	}
}
