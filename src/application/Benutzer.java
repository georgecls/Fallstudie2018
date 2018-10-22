package application;

import java.sql.*;
import java.util.Observable;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Benutzer {

	// Initialisierung der Attribute nach den Attributen in der DB
	private static int id;
	private String benutzername;
	private String passwort;
	private int berechtigung;
	private String status;
	private int ag_fk;

	// Attribute die darüberhinaus noch benötigt werden
	private String gruppenname;
	private Gruppe agid;
	private static String bnPrüfen;
	private static int bPrüfen;
	private static String gPrüfen;
	private static boolean anmelden = false;

	/** ***************************************************************************************************************************************************
	 * ******************************************************Implementierung der Konstruktoren*************************************************************
	 ******************************************************************************************************************************************************/

	public Benutzer() {
		anmelden = false;
	}

	public Benutzer(String name) throws SQLException {
		this.getBenutzerByName(name);
	}

	/** ***************************************************************************************************************************************************
	 * *************************************************Implementierung der statischen Methoden************************************************************
	 ******************************************************************************************************************************************************/

	// Wird nicht aufgerufen -> LÖSCHEN?
	public static boolean isRightBenutzer(String pBenutzer, String pPasswort) throws SQLException {
		Main.get_DBConnection().Execute(String
				.format("SELECT * FROM benutzer WHERE benutzer = '%s' AND passwort = '%s';", pBenutzer, pPasswort));
		ResultSet rs = Main.get_DBConnection().get_last_resultset();

		if (rs.next()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Methode, um die Anmeldung eines vermeintlichen Benutzers abzuwicklen. 
	 * TRUE: der Benutzer ist in der DB vorhanden, sein Status aktiv und das eingegebene
	 * Passwort stimmt mit dem hinterlegten aus der DB überein. 
	 * FALSE: alle anderen Fälle.
	 * 
	 * Der Status wird in der statischen Klassenvariable "anmelden" gespeichert.
	 * !DB-Connection! -> Verwendung von Methoden aus der Klasse "DBConnector".
	 * 
	 * @throws SQLException
	 * @return boolean
	 * @param benutzername, passwort
	 */
	public static boolean anmelden(String benN, String p) throws SQLException {
		String eingabeName = benN;
		String eingabePasswort = p;
		String vergleichsPasswort = null;
		String aktivität = null;

		Main.get_DBConnection().Execute(
				String.format("SELECT passwort, benutzerid, bstatus FROM benutzer WHERE benutzername = '%s';", benN));
		ResultSet rs = Main.get_DBConnection().get_last_resultset();

		while (rs.next()) {
			id = rs.getInt("benutzerid");
			vergleichsPasswort = rs.getString("passwort");
			aktivität = rs.getString("bstatus");
			if (BCrypt.checkpw(p, vergleichsPasswort) && aktivität.equals("aktiv")) {
				anmelden = true;
			} else {
				anmelden = false;
			}
		}
		return anmelden;
	}

	/**
	 * Methode, um die Berechtigung des aktuell angemeldeten Benutzers abzufragen und zurückzugeben. 
	 * !DB-Connection! -> Verwendung von Methoden aus der Klasse "DBConnector".
	 * 
	 * @throws SQLException
	 * @return int
	 * @param benutzername
	 */
	public static int berechtigungPrüfen(String benN) throws SQLException {
		bnPrüfen = benN;

		Main.get_DBConnection().Execute(String.format("SELECT blevel FROM benutzer WHERE benutzername = '%s';", benN));
		ResultSet rs = Main.get_DBConnection().get_last_resultset();

		if (rs.first()) {
			bPrüfen = (Integer) rs.getInt("blevel");
		}
		if (anmelden == true) {
			return bPrüfen;
		} else {
			return -1;
		}
	}

	/**
	 * Methode, um einen neuen Benutzer in die DB einzutragen. Die ID wird von der DB automatisch erzeugt. 
	 * !DB-Connection! -> Verwendung von Methoden aus der Klasse "DBConnector".
	 * 
	 * @throws SQLException
	 * @return none
	 * @param benutzername, passwort, berechtigung, gruppe
	 */
	public static void insertBenutzer(String benutzer, String passwort, int berechtigung, String gruppe)
			throws SQLException {
		Gruppe g1 = new Gruppe();
		g1.getGruppeByName(gruppe);
		String i = g1.getId();

		passwort = BCrypt.hashpw(passwort, BCrypt.gensalt());
		Main.get_DBConnection()
				.ExecuteTransact(String.format("INSERT INTO benutzer (benutzername, passwort, blevel, bstatus, ag_fk) "
						+ "VALUES ('%s', '%s', '%d', 'aktiv', '%s')", benutzer, passwort, berechtigung, i));
	}

	/**
	 * Methode, um die Berechtigung, die Gruppe und das Passwort eines Benutzers in der DB zu ändern. 
	 * !DB-Connection! -> Verwendung von Methoden aus der Klasse "DBConnector".
	 * 
	 * @throws SQLException
	 * @return none
	 * @param benutzername, passwort, gruppe, berechtigung
	 */
	public static void updateBenutzerPw(String name, String passwort, String gruppe, int berechtigung)
			throws SQLException {
		Gruppe g1 = new Gruppe();
		g1.getGruppeByName(gruppe);
		String i = g1.getId();

		passwort = BCrypt.hashpw(passwort, BCrypt.gensalt());
		Main.get_DBConnection()
				.ExecuteTransact(String.format(
						"UPDATE benutzer SET passwort = '%s',"
								+ " blevel = '%d', ag_fk = '%s' WHERE benutzername = '%s';",
						passwort, berechtigung, i, name));
	}

	/**
	 * Methode, um die Berechtigung und die Gruppe eines Benutzers in der DB zu ändern. 
	 * !DB-Connection! -> Verwendung von Methoden aus der Klasse "DBConnector".
	 * 
	 * @throws SQLException
	 * @return none
	 * @param benutzername, gruppe, berechtigung
	 */
	public static void updateBenutzer(String name, String gruppe, int berechtigung) throws SQLException {
		Gruppe g1 = new Gruppe();
		g1.getGruppeByName(gruppe);
		String i = g1.getId();

		Main.get_DBConnection().ExecuteTransact(
				String.format("UPDATE benutzer SET blevel = '%d', ag_fk = '%s' " + "WHERE benutzername = '%s';",
						berechtigung, i, name));
	}

	/**
	 * Methode, um den Status eines Benutzers auf "aktiv" zu setzen und die Gruppe
	 * eines Benutzers in der DB zu ändern. !DB-Connection! -> Verwendung von
	 * Methoden aus der Klasse "DBConnector".
	 * 
	 * @throws SQLException
	 * @return none
	 * @param benutzername, gruppe
	 */
	public static void updateInaktiverBenutzer(String name, String gruppe) throws SQLException {
		Gruppe g1 = new Gruppe();
		g1.getGruppeByName(gruppe);
		String i = g1.getId();
		Main.get_DBConnection().ExecuteTransact(String.format(
				"UPDATE benutzer SET bstatus = 'aktiv', ag_fk = '%s' " + "WHERE benutzername = '%s';", i, name));
	}

	/**
	 * Methode, um den Status eines Benutzers auf "inaktiv" und seine Gruppe auf
	 * NULL zu setzen. !DB-Connection! -> Verwendung von Methoden aus der Klasse
	 * "DBConnector".
	 * 
	 * @throws SQLException
	 * @return none
	 * @param benutzername
	 */
	public static void deleteBenutzer(String name) throws SQLException {
		Main.get_DBConnection().ExecuteTransact(String
				.format("UPDATE benutzer SET bstatus = 'inaktiv', ag_fk = NULL WHERE benutzername = '%s';", name));
	}

	/**
	 * Methode, um TableView in Benutzerverwaltung zu befüllen. Es werden alle
	 * aktiven Benutzer ausgewählt und das Resultset dann in einer ObservableList
	 * gespeichert und zurückgegeben. !DB-Connection! -> Verwendung von Methoden aus
	 * der Klasse "DBConnector".
	 * 
	 * @throws SQLException
	 * @return ObservableList
	 * @param none
	 */
	public static ObservableList getBenutzerverwaltung() throws SQLException {

		ObservableList<Benutzer> data = FXCollections.observableArrayList();

		Main.get_DBConnection().Execute("SELECT * FROM benutzer WHERE bstatus <> 'inaktiv'");
		ResultSet rs = Main.get_DBConnection().get_last_resultset();

		while (rs.next()) {
			data.add(new Benutzer(rs.getString("benutzername")));
		}
		return data;
	}

	/**
	 * Methode wird im Konstruktor Benutzer(String) aufgerufen und holt sich alle
	 * Informationen des entsprechenden Benutzers aus der Datenbank anhand des
	 * angegebenen Benutzernamen und gibt diese Informationen in einem Objekt der
	 * Klasse Benutzer zurück. !DB-Connection! -> Verwendung von Methoden aus der
	 * Klasse "DBConnector".
	 * 
	 * @throws SQLException
	 * @return Benutzer
	 * @param benutzername
	 */
	public Benutzer getBenutzerByName(String name) throws SQLException {
		Main.get_DBConnection().Execute(String.format(
				"SELECT * FROM benutzer INNER JOIN ag ON benutzer.ag_fk = ag.agid WHERE benutzername = '%s'", name));
		ResultSet rs = Main.get_DBConnection().get_last_resultset();

		if (rs.next()) {
			this.setId(rs.getInt("benutzerid"));
			this.benutzername = rs.getString("benutzername");
			this.passwort = rs.getString("passwort");
			this.berechtigung = rs.getInt("blevel");
			this.ag_fk = rs.getInt("ag_fk");
			this.gruppenname = rs.getString(8);
		}
		return this;
	}

	/**
	 * Methode, um untere TableView in Gruppenverwaltung zu befüllen. Es werden alle
	 * aktiven Benutzer anhand der übergebene Gruppe ausgewählt, das Resultset dann
	 * in einer ObservableList gespeichert und zurückgegeben. Die obere TbaleView in
	 * Gruppenverwaltung wird von der Klasse Gruppe befüllt. !DB-Connection! ->
	 * Verwendung von Methoden aus der Klasse "DBConnector".
	 * 
	 * @throws SQLException
	 * @return ObservableList
	 * @param gruppenID
	 */
	public static ObservableList getBenutzerByGruppe(String id) throws SQLException {
		ObservableList<Benutzer> data = FXCollections.observableArrayList();
		Main.get_DBConnection().Execute(String.format("SELECT benutzername FROM benutzer WHERE ag_fk = '%s'", id));
		ResultSet rs = Main.get_DBConnection().get_last_resultset();

		while (rs.next()) {
			data.add(new Benutzer(rs.getString("benutzername")));
		}
		return data;
	}

	/**
	 * Methode, um zu prüfen, ob Benutzer einer bestimmten Gruppe zugeordnet sind.
	 * Verwendet wird die Methode in ControllerGruppenverwaltung bei der Prüfung, ob
	 * eine Gruppe auf "inaktiv" gesetzt werden kann. TRUE: es sind noch Benutzer in
	 * der Gruppe FALSE: es sind keine Benutzer mehr in der Gruppe !DB-Connection!
	 * -> Verwendung von Methoden aus der Klasse "DBConnector".
	 * 
	 * @throws SQLException
	 * @return boolean
	 * @param gruppenID
	 */
	public static boolean pruefeBenutzer(String id) throws SQLException {
		Main.get_DBConnection().Execute(String.format("SELECT benutzername FROM benutzer WHERE ag_fk = '%s'", id));
		ResultSet rs = Main.get_DBConnection().get_last_resultset();

		if (rs.next()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Methode, um die Gruppe des angemeldeten Benutzers herauszufinden und
	 * zurückzugeben. !DB-Connection! -> Verwendung von Methoden aus der Klasse
	 * "DBConnector".
	 * 
	 * @throws SQLException
	 * @return String
	 * @param benutzername
	 */
	public static String getBearGruppeByUser(String benN) throws SQLException {
		bnPrüfen = benN;
		Main.get_DBConnection().Execute(String.format("SELECT ag_fk FROM benutzer WHERE benutzername = '%s'", benN));
		ResultSet rs = Main.get_DBConnection().get_last_resultset();

		if (rs.first()) {
			gPrüfen = (String) rs.getString("ag_fk");
		}
		if (anmelden == true) {
			return gPrüfen;
		} else {
			return null;
		}
	}

	/**
	 * Methode, um beim Anlegen eines neuen Benutzers zu prüfen, ob der Benutzername
	 * schon vergeben ist. TRUE: Benutzername schon vergeben FALSE: Benutzername
	 * nicht vergeben !DB-Connection! -> Verwendung von Methoden aus der Klasse
	 * "DBConnector".
	 * 
	 * @throws SQLException
	 * @return boolean
	 * @param benutzername
	 */
	public static Boolean selberName(String benutzer) throws SQLException {
		int i = 0;
		Main.get_DBConnection().Execute(String.format("SELECT * FROM benutzer WHERE benutzername = '%s'", benutzer));
		ResultSet rs = Main.get_DBConnection().get_last_resultset();

		if (rs.next()) {
			i = rs.getInt(1);
		}
		if (i == 0) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * Methode, um herauszufinden, ob ein Benutzer aktiv oder inaktiv ist. TRUE:
	 * Benutzername inaktiv FALSE: Benutzername aktiv !DB-Connection! -> Verwendung
	 * von Methoden aus der Klasse "DBConnector".
	 * 
	 * @throws SQLException
	 * @return boolean
	 * @param benutzername
	 */
	public static boolean inaktiverBenutzer(String benutzer) throws SQLException {
		int i = 0;
		Main.get_DBConnection().Execute(
				String.format("SELECT * FROM benutzer WHERE benutzername = '%s' AND  bstatus = 'inaktiv'", benutzer));
		ResultSet rs = Main.get_DBConnection().get_last_resultset();

		if (rs.next()) {
			i = rs.getInt(1);
		
			if (i == 0) {
				return false;
			} else {
				return true;
			}
		}
		else return false;
	}

	/**
	 * Methode, um die benutzerID zu einem Benutzernamen herauszufinden und
	 * zurückzugeben. !DB-Connection! -> Verwendung von Methoden aus der Klasse
	 * "DBConnector".
	 * 
	 * @throws SQLException
	 * @return int
	 * @param benutzername
	 */
	public static int getIdByName(String name) throws SQLException {
		ObservableList<Benutzer> data = FXCollections.observableArrayList();
		int id;
		Main.get_DBConnection()
				.Execute(String.format("SELECT benutzerid FROM benutzer WHERE benutzername = '%s'", name));
		ResultSet rs = Main.get_DBConnection().get_last_resultset();

		if (rs.next()) {
			return id = rs.getInt(1);
		} else {
			return 0;
		}
	}

	/**
	 * Methode, um die Gruppe eines Benutzers herauszufinden und zurückzugeben.
	 * !DB-Connection! -> Verwendung von Methoden aus der Klasse "DBConnector".
	 * 
	 * @throws SQLException
	 * @return String
	 * @param benutzername
	 */
	public static int getGruppeByName(String name) throws SQLException {
		ObservableList<Benutzer> data = FXCollections.observableArrayList();
		int id;
		Main.get_DBConnection().Execute(String.format("SELECT ag_fk FROM benutzer WHERE benutzername = '%s'", name));
		ResultSet rs = Main.get_DBConnection().get_last_resultset();

		if (rs.next()) {
			return id = rs.getInt(1);
		} else {
			return 0;
		}
	}

	/*****************************************************************************************************************************************************
	 ******************************************************* Implementierung der Getter und
	 * Setter*********************************************************
	 ******************************************************************************************************************************************************/

	public String getBenutzername() {
		return benutzername;
	}

	public void setBenutzername(String benutzername) {
		this.benutzername = benutzername;
	}

	public String getPasswort() {
		return passwort;
	}

	public void setPasswort(String passwort) {
		this.passwort = passwort;
	}

	public int getBerechtigung() {
		return berechtigung;
	}

	public void setPasswort(int berechtigung) {
		this.berechtigung = berechtigung;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getAg_fk() {
		return ag_fk;
	}

	public void setAg_fk(int ag_fk) {
		this.ag_fk = ag_fk;
	}

	public String getGruppe() {
		return gruppenname;
	}

	public void setGruppe(String gruppenname) {
		this.gruppenname = gruppenname;
	}
}