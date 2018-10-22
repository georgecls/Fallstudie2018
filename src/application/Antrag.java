package application;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Date;

/**
 * Anträge können folgende Status innehaben: erstellt = neues Ticket angelegt ->
 * verschieben in Tickets prüfen, geprüft = Ticket geprüft -> verschieben in
 * Tickets genehmigen, genehmigt = Ticket genehmigt -> verschieben in
 * Gruppentickets, erledigt = Ticket abschließend bearbeitet -> verschieben in
 * abgeschlossene Tickets, abgelehnt = Ticket wurde vom genehmiger/prüfer
 * abgelehnt -> anzeige in eigene Tickets, aber nicht in abgesch. Tickets
 * gelöscht = Ticket gelöscht
 */
public class Antrag {

	// Initialisierung der Attribute nach den Attributen in der DB
	private int antragid;
	private String name;
	private String beschreibung;
	private Date antragsdatum;
	private Date fertigstellungsdatum;
	private String status;
	private String ablehnungsgrund;
	private String kommentar;
	private Benutzer ersteller;
	private Benutzer bearbeiter;
	private Gruppe erstGruppe;
	private Gruppe bearGruppe;

	/**
	 * ***************************************************************************************************************************************************
	 * ******************************************************Implementierung der Konstruktoren*************************************************************
	 ******************************************************************************************************************************************************/

	public Antrag() {

	}

	public Antrag(int pID) throws SQLException {
		this.getAntragById(pID);
	}

	/**
	 * ***************************************************************************************************************************************************
	 * **************************************************Implementierung der statischen Methoden***********************************************************
	 ******************************************************************************************************************************************************/

	/**
	 * Methode, um einen neuen Antrag in die DB zu schreiben. 
	 * !DB-Connection! -> Verwendung von Methoden aus der Klasse "DBConnector".
	 *
	 * @throws SQLException
	 * @return none
	 * @param name, ersteller, erstelldatum, zieldatum, beschreibung,
	 *        erstellergruppe, bearbeitergruppe
	 */

	public static void insertAntrag(String name, String ersteller, LocalDate erstelldatum, LocalDate zieldatum,
			String text, String erstGruppe, String gruppe) throws SQLException 
	{
		int erst = Benutzer.getIdByName(ersteller);
		Gruppe g1 = new Gruppe();
		g1.getGruppeByName(gruppe);
		String gr = g1.getId();
		Main.get_DBConnection().ExecuteTransact(String.format(
				"INSERT INTO antrag (titel, beschreibung, fertigstellungsdatum, antragsdatum, "
						+ "status, ablehnungsgrund, anmerkung, ersteller_fk, bearbeiter_fk, ag_ersteller_fk, ag_bearbeiter_fk) "
						+ "VALUES('%s', '%s', '%s', '%s', 'erstellt', NULL, NULL, '%d', NULL, '%s', '%s');",
				name, text, zieldatum, erstelldatum, erst, erstGruppe, gr));
	}

	/**
	 * Methode um alle bestehenden Anräge unabhängig der Gruppe abzufragen. Es wird
	 * eine Liste erstellt, in welcher die Daten gespeichert und später ausgegeben
	 * werden. 
	 * !DB-Connection! -> Verwendung von Methoden aus der Klasse "DBConnector".
	 * 
	 * @throws SQLException
	 * @return ObservableList
	 * @param none
	 */
	public static ObservableList<Antrag> getAlleAntraege() throws SQLException {

		ObservableList<Antrag> data = FXCollections.observableArrayList();

		try {
			Main.get_DBConnection().Execute("SELECT * FROM antrag;");
			ResultSet rs = Main.get_DBConnection().get_last_resultset();

			while (rs.next()) {
				data.add(new Antrag(rs.getInt("idantrag")));
			}
		} catch (SQLException e) {
			System.out.println(e);
		}

		return data;
	}

	/**
	 * Methode um alle bestehenden Anräge abhängig vom Status abzufragen.
	 * !DB-Connection! -> Verwendung von Methoden aus der Klasse "DBConnector".
	 *
	 * @throws SQLException
	 * @return data
	 * @param status, benutzername
	 */
	public static ObservableList<Antrag> getAntraegebyStatus(String status, String benutzername) throws SQLException {
		ObservableList<Antrag> data = FXCollections.observableArrayList();
		try {
			Main.get_DBConnection()
					.Execute(String.format(
							"SELECT * FROM antrag " + "INNER JOIN benutzer ON antrag.ag_ersteller_fk = benutzer.ag_fk "
									+ "WHERE benutzername = '%s' AND status = '%s';",
							benutzername, status));

			ResultSet rs = Main.get_DBConnection().get_last_resultset();
			while (rs.next()) {
				data.add(new Antrag(rs.getInt("idantrag")));
			}
		} catch (SQLException e) {
			System.out.println(e);
		}

		return data;
	}

	/**
	 * Methode, um einen Anträge der Gruppe des aktuell angemeldeten Benutzers aus der DB auszugeben. 
	 * !DB-Connection! -> Verwendung von Methoden aus der Klasse "DBConnector".
	 * 
	 * @throws SQLException
	 * @return ObservableList
	 * @param status, benutzername
	 */
	public static ObservableList<Antrag> getGruppenantraege(String status, String benutzername) throws SQLException {
		int bGruppe = Benutzer.getGruppeByName(benutzername);

		ObservableList<Antrag> data = FXCollections.observableArrayList();
		try {
			Main.get_DBConnection()
					.Execute(String.format(
							"SELECT * FROM antrag " + "INNER JOIN benutzer ON antrag.ag_ersteller_fk = benutzer.ag_fk "
									+ "WHERE benutzername = '%s' AND status = '%s' AND ag_bearbeiter_fk = '%d';",
							benutzername, status, bGruppe));

			ResultSet rs = Main.get_DBConnection().get_last_resultset();
			while (rs.next()) {
				data.add(new Antrag(rs.getInt("idantrag")));
			}
		} catch (SQLException e) {
			System.out.println(e);
		}

		return data;
	}

	/**
	 * Methode, um die Anträge mit Status 'erstellt' abzufragen. Die Ergebnisse
	 * erscheinen in der Tabelle 'Tickets prüfen'. !DB-Connection! -> Verwendung von
	 * Methoden aus der Klasse "DBConnector".
	 * 
	 * @throws SQLException
	 * @return data
	 * @param benutzername, benutzerID
	 */
	public static ObservableList<Antrag> getAntraegezuPruefen(String benutzername, int benutzerid) throws SQLException {
		ObservableList<Antrag> data = FXCollections.observableArrayList();

		try {
			int grid = Benutzer.getGruppeByName(benutzername);
			Main.get_DBConnection()
					.Execute(String.format(
							"SELECT * FROM antrag "
									+ "WHERE ag_ersteller_fk = '%d' AND status = 'erstellt' AND ersteller_fk <> '%d';",
							grid, benutzerid));

			ResultSet rs = Main.get_DBConnection().get_last_resultset();
			while (rs.next()) {
				data.add(new Antrag(rs.getInt("idantrag")));
			}
		} catch (SQLException e) {
			System.out.println(e);
		}

		return data;
	}

	/**
	 * Methode, um die erstellten Anträge des aktuell angemeldeten Benutzers
	 * abzufragen. Die Ergebnisse erscheinen in der Tabelle 'eigene Tickets'.
	 * !DB-Connection! -> Verwendung von Methoden aus der Klasse "DBConnector".
	 * 
	 * @throws SQLException
	 * @return data
	 * @param benutzerID
	 */
	public static ObservableList<Antrag> getEigeneAntraege(int benutzerid) throws SQLException {
		ObservableList<Antrag> data = FXCollections.observableArrayList();

		try {
			Main.get_DBConnection()
					.Execute(String.format("SELECT * FROM antrag " + "WHERE ersteller_fk = '%d';", benutzerid));

			ResultSet rs = Main.get_DBConnection().get_last_resultset();
			while (rs.next()) {
				data.add(new Antrag(rs.getInt("idantrag")));
			}
		} catch (SQLException e) {
			System.out.println(e);
		}

		return data;
	}

	/****************************************************************************************
	 * *************************************Auswertung***************************************
	 ****************************************************************************************/

	/**
	 * Methode um die Anzahl der Anträge je nach Status ausgeben zu lassen. Ergebnis
	 * wird in PieChart ausgegeben.
	 * 
	 * @param status
	 * @return double
	 * @throws SQLException
	 */
	public static Double countAntraegeByStatus(String status) throws SQLException {
		double i = 0.00;

		Main.get_DBConnection()
				.Execute(String.format("SELECT COUNT(idantrag) FROM antrag where status = '%s';", status));
		ResultSet rs = Main.get_DBConnection().get_last_resultset();
		while (rs.next()) {
			String is = rs.getString(1);
			i = Double.parseDouble(is);
		}
		return i;
	}

	/**
	 * Methode um die Anzahl der Anträge je nach Status und Gruppe ausgeben zu
	 * lassen. Ergebnis wird in PieChart ausgegeben.
	 * 
	 * @param benutzername, status
	 * @return i
	 * @throws SQLException
	 */
	public static Double countAntraegeByGruppe(String benutzername, String status) throws SQLException {
		double i = 0.00;

		Main.get_DBConnection()
				.Execute(String.format("SELECT COUNT(idantrag) FROM antrag "
						+ "INNER JOIN benutzer ON antrag.ag_ersteller_fk = benutzer.ag_fk "
						+ "WHERE benutzername = '%s' AND status = '%s';", benutzername, status));
		ResultSet rs = Main.get_DBConnection().get_last_resultset();
		while (rs.next()) {
			String is = rs.getString(1);
			i = Double.parseDouble(is);
		}
		return i;
	}

	/**
	 * ****************************************************************************************
	 * *********************************Veränderung der Status**********************************
	 *******************************************************************************************/

	/**
	 * Diese Methoden dienen allesamt der Veränderung des Status eines Antrags.
	 */

	public static void deleteAntragById(String id) throws SQLException {
		Main.get_DBConnection()
				.ExecuteTransact(String.format("UPDATE antrag SET status = 'gelöscht' WHERE idantrag = '%s'", id));
	}

	public static void antragPruefen(String id, String anm) throws SQLException {
		Main.get_DBConnection().ExecuteTransact(
				String.format("UPDATE antrag SET status = 'geprüft', anmerkung = '%s' WHERE idantrag = '%s'", anm, id));
	}

	public static void antragGenehmigen(String id, String text) throws SQLException {
		Main.get_DBConnection().ExecuteTransact(String
				.format("UPDATE antrag SET status = 'genehmigt', anmerkung = '%s' WHERE idantrag = '%s'", text, id));
	}

	public static void antragBearbeiten(String id) throws SQLException {
		Main.get_DBConnection()
				.ExecuteTransact(String.format("UPDATE antrag SET status = 'erledigt' WHERE idantrag = '%s'", id));
	}

	public static void antragAblehnen(String id, String text) throws SQLException {
		Main.get_DBConnection().ExecuteTransact(String.format(
				"UPDATE antrag SET status = 'abgelehnt', ablehnungsgrund = '%s' WHERE idantrag = '%s'", text, id));
	}

	/**
	 * ***************************************************************************************************************************************************
	 * ***************************************************Implementierung der nicht statische Methoden******************************************************
	 ******************************************************************************************************************************************************/

	/**
	 * Methode zieht sich Antrag mit im Parameter angegebener ID.
	 * 
	 * @param pID
	 * @return Objekt von Antrag
	 * @throws SQLException
	 */
	public Antrag getAntragById(int pID) throws SQLException {
		Main.get_DBConnection().Execute(String.format("SELECT * FROM antrag WHERE idantrag = '%d';", pID));
		ResultSet rs = Main.get_DBConnection().get_last_resultset();

		if (rs.first()) {
			this.antragid = rs.getInt("idantrag");
			this.name = rs.getString("titel");
			this.beschreibung = rs.getString("beschreibung");
			this.fertigstellungsdatum = rs.getDate("fertigstellungsdatum");
			this.antragsdatum = rs.getDate("antragsdatum");
			this.status = rs.getString("status");
			this.ablehnungsgrund = rs.getString("ablehnungsgrund");
			this.kommentar = rs.getString("anmerkung");
			this.ersteller = new Benutzer(rs.getString("ersteller_fk"));
			this.bearbeiter = (Benutzer) rs.getObject("bearbeiter_fk");
			this.erstGruppe = new Gruppe(rs.getString("ag_ersteller_fk"));
			this.bearGruppe = new Gruppe(rs.getString("ag_bearbeiter_fk"));
		}
		return this;
	}

	/**
	 * ***************************************************************************************************************************************************
	 * ******************************************************Implementierung der Getter und Setter*********************************************************
	 ******************************************************************************************************************************************************/

	public int getAntragid() {
		return antragid;
	}

	public void setAntragid(int antragid) {
		this.antragid = antragid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBeschreibung() {
		return beschreibung;
	}

	public void setBeschreibung(String beschreibung) {
		this.beschreibung = beschreibung;
	}

	public Date getAntragsdatum() {
		return antragsdatum;
	}

	public void setAntragsdatum(Date aDatum) {
		this.antragsdatum = aDatum;
	}

	public Date getFertigstellungsdatum() {
		return fertigstellungsdatum;
	}

	public void setFertigstellungsdatum(Date fDatum) {
		this.fertigstellungsdatum = fDatum;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getAblehnungsgrund() {
		return ablehnungsgrund;
	}

	public void setAblehnungsgrund(String abGrund) {
		this.ablehnungsgrund = abGrund;
	}

	public String getKommentar() {
		return kommentar;
	}

	public void setKommentar(String kommentar) {
		this.kommentar = kommentar;
	}

	public Benutzer getErsteller() {
		return ersteller;
	}

	public void setErsteller(Benutzer ersteller) {
		this.ersteller = ersteller;
	}

	public Gruppe getErstGruppe() {
		return erstGruppe;
	}

	public void setErstellerGruppe(Gruppe eGruppe) {
		this.erstGruppe = eGruppe;
	}

	public Benutzer getBearbeiter() {
		return bearbeiter;
	}

	public void setBearbeiter(Benutzer bearbeiter) {
		this.bearbeiter = bearbeiter;
	}

	public Gruppe getBearbeiterGruppe() {
		return bearGruppe;
	}

	public void setBearbeiterGruppe(Gruppe bGruppe) {
		this.bearGruppe = bGruppe;
	}

	public void getAuftragByNr(int pNR) {

	}
}