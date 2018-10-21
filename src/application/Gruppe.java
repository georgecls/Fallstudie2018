package application;

import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Gruppe {

	// Initialisierung der Attribute nach den Attributen in der DB
	private String agid;
	private String gruppenname;
	private String gruppenbeschr;
	private String agstatus;

	/**
	 * ***************************************************************************************************************************************************
	 * ******************************************************Implementierung der
	 * Konstruktoren*************************************************************
	 ******************************************************************************************************************************************************/

	public Gruppe() {

	}

	public Gruppe(int agid) throws SQLException {
		this.getGruppe(agid);
	}

	public Gruppe(String name) throws SQLException {
		this.getGruppeByName(name);
	}

	/**
	 * ***************************************************************************************************************************************************
	 * *************************************************Implementierung der
	 * statischen
	 * Methoden************************************************************
	 ******************************************************************************************************************************************************/

	/**
	 * Methode, um eine neue Gruppe in die DB zu schreiben. !DB-Connection! ->
	 * Verwendung von Methoden aus der Klasse "DBConnector".
	 * 
	 * @throws SQLException
	 * @return none
	 * @param gruppenname, gruppenbeschreibung
	 */
	public static void insertGruppe(String gruppe, String beschreibung) throws SQLException {
		Main.get_DBConnection()
				.ExecuteTransact(String.format(
						"INSERT INTO ag (gruppenname, gruppenbeschreibung, agstatus) " + "VALUES ('%s', '%s', 'aktiv')",
						gruppe, beschreibung));
	}

	/**
	 * Methode, um eine Gruppe anhand der GruppenID in der DB zu ändern.
	 * !DB-Connection! -> Verwendung von Methoden aus der Klasse "DBConnector".
	 * 
	 * @throws SQLException
	 * @return none
	 * @param gruppenID, gruppenname, gruppenbeschreibung
	 */
	public static void updateGruppeById(String id, String gruppe, String beschreibung) throws SQLException {
		Main.get_DBConnection().ExecuteTransact(
				String.format("UPDATE ag SET gruppenname = '%s', gruppenbeschreibung = '%s' WHERE agid = '%s';", gruppe,
						beschreibung, id));
	}

	/**
	 * Methode, um eine Gruppe anhand des Gruppennamen in der DB zu ändern.
	 * !DB-Connection! -> Verwendung von Methoden aus der Klasse "DBConnector".
	 * 
	 * @throws SQLException
	 * @return none
	 * @param gruppenname, gruppenbeschreibung
	 */
	public static void updateGruppeByName(String gruppe, String beschreibung) throws SQLException {
		Main.get_DBConnection()
				.ExecuteTransact(String.format(
						"UPDATE ag SET gruppenbeschreibung = '%s', agstatus = 'aktiv' WHERE gruppenname = '%s';",
						beschreibung, gruppe));
	}

	/**
	 * Methode, um den Status (agstatus) einer Gruppe anhand der GruppenID in der DB
	 * auf "inaktiv" zu setzen. !DB-Connection! -> Verwendung von Methoden aus der
	 * Klasse "DBConnector".
	 * 
	 * @throws SQLException
	 * @return none
	 * @param GruppenID
	 */
	public static void deleteGruppe(String id) throws SQLException {
		Main.get_DBConnection()
				.ExecuteTransact(String.format("UPDATE ag SET agstatus = 'inaktiv' WHERE agid = '%s'", id));
	}

	/**
	 * Methode, um obere TableView in Gruppenverwaltung zu befüllen. Es werden alle
	 * aktiven Gruppen ausgewählt und das Resultset dann in einer ObservableList
	 * gespeichert und zurückgegeben. Die untere TableView in Gruppenverwaltung wird
	 * von der Klasse Benutzer befüllt. !DB-Connection! -> Verwendung von Methoden
	 * aus der Klasse "DBConnector".
	 * 
	 * @throws SQLException
	 * @return ObservableList
	 * @param none
	 */
	public static ObservableList getGruppenverwaltung() throws SQLException {

		ObservableList<Gruppe> data = FXCollections.observableArrayList();

		Main.get_DBConnection().Execute("SELECT * FROM ag WHERE agstatus <> 'inaktiv'");
		ResultSet rs = Main.get_DBConnection().get_last_resultset();

		while (rs.next()) {
			data.add(new Gruppe(rs.getString("gruppenname")));
		}
		return data;
	}

	// Wird nicht aufgerufen -> LÖSCHEN?
	/**
	 * Methode wird im Konstruktor Gruppe(int) aufgerufen, der Konstruktor wird aber
	 * nie verwendet. !DB-Connection! -> Verwendung von Methoden aus der Klasse
	 * "DBConnector".
	 * 
	 * @throws SQLException
	 * @return Gruppe
	 * @param GruppenID als int
	 */
	public Gruppe getGruppe(int agid) throws SQLException {
		Main.get_DBConnection().Execute(String.format("SELECT gruppenname FROM ag WHERE agid = '%s'", agid));
		ResultSet rs = Main.get_DBConnection().get_last_resultset();

		if (rs.next()) {
			this.gruppenname = rs.getString("gruppenname");
		}
		return this;
	}

	/**
	 * Methode wird im Konstruktor Gruppe(String) aufgerufen und holt sich alle
	 * Informationen der entsprechenden Gruppe aus der Datenbank anhand des
	 * angegebenen Gruppennamen und gibt diese Informationen in einem Objekt der
	 * Klasse Gruppe zurück. !DB-Connection! -> Verwendung von Methoden aus der
	 * Klasse "DBConnector".
	 * 
	 * @throws SQLException
	 * @return Gruppe
	 * @param gruppenname
	 */
	public Gruppe getGruppeByName(String name) throws SQLException {
		Main.get_DBConnection().Execute(String.format("SELECT * FROM ag WHERE gruppenname = '%s'", name));
		ResultSet rs = Main.get_DBConnection().get_last_resultset();

		if (rs.next()) {
			this.agid = rs.getString("agid");
			this.gruppenname = rs.getString("gruppenname");
			this.gruppenbeschr = rs.getString("gruppenbeschreibung");
		}
		return this;
	}

	/**
	 * Methode, um die DropDown-Boxen in verschiedenen Fenstern mit den Namen der
	 * aktiven Gruppen zu füllen. !DB-Connection! -> Verwendung von Methoden aus der
	 * Klasse "DBConnector".
	 * 
	 * @throws SQLException
	 * @return ObservableList
	 * @param none
	 */
	public static ObservableList getGruppennamen() throws SQLException {

		ObservableList<String> data = FXCollections.observableArrayList();
		Main.get_DBConnection().Execute("SELECT * FROM ag WHERE agstatus <> 'inaktiv'");
		ResultSet rs = Main.get_DBConnection().get_last_resultset();

		while (rs.next()) {
			data.add(rs.getString("gruppenname"));
		}
		return data;
	}

	/**
	 * Methode, um beim Anlegen einer neuen Gruppe zu prüfen, ob der Gruppenname
	 * schon vergeben ist. TRUE: Gruppenname schon vergeben FALSE: Gruppenname nicht
	 * vergeben !DB-Connection! -> Verwendung von Methoden aus der Klasse
	 * "DBConnector".
	 * 
	 * @throws SQLException
	 * @return boolean
	 * @param gruppenname
	 */
	public static Boolean selberName(String gruppe) throws SQLException {

		int i = 0;
		Main.get_DBConnection().Execute(String.format("SELECT * FROM ag WHERE gruppenname = '%s'", gruppe));
		ResultSet rs = Main.get_DBConnection().get_last_resultset();

		if (rs.next())
			i = rs.getInt(1);

		if (i == 0) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * Methode, um den Status der Gruppe abzufragen. TRUE: Gruppe inaktiv FALSE:
	 * Gruppe aktiv !DB-Connection! -> Verwendung von Methoden aus der Klasse
	 * "DBConnector".
	 * 
	 * @throws SQLException
	 * @return boolean
	 * @param gruppenname
	 */
	public static Boolean inaktiveGruppe(String gruppe) throws SQLException {

		int i = 0;
		Main.get_DBConnection()
				.Execute(String.format("SELECT * FROM ag WHERE gruppenname = '%s' AND  agstatus = 'inaktiv'", gruppe));
		ResultSet rs = Main.get_DBConnection().get_last_resultset();

		if (rs.next())
			i = rs.getInt(1);

		if (i == 0) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * ***************************************************************************************************************************************************
	 * ******************************************************Implementierung der
	 * Getter und Setter*********************************************************
	 ******************************************************************************************************************************************************/
	public String getId() {
		return agid;
	}

	public void setId(String id) {
		this.agid = id;
	}

	public String getGruppenname() {
		return gruppenname;
	}

	public void setGruppenname(String gruppenname) {
		this.gruppenname = gruppenname;
	}

	public String getGruppenbeschr() {
		return gruppenbeschr;
	}

	public void setGruppenbeschr(String gruppenbeschreibung) {
		this.gruppenbeschr = gruppenbeschreibung;
	}
}