package application;

import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Gruppe {
	
	//Initialisierung der Attribute nach den Attributen in der DB
	private String agid;
	private String gruppenname;
	private String gruppenbeschr;
	private String agstatus;
	

	
	
	
	
	/** ***************************************************************************************************************************************************
	 * *********************************************************Implementierung der Methoden***************************************************************
	 ******************************************************************************************************************************************************/
	
	public Gruppe() 
	{
		
	}
	
	public Gruppe(int agid) throws SQLException
	{
		this.getGruppe(agid);
	}
	
	public Gruppe(String name) throws SQLException
	{
		this.getGruppeByName(name);
	}
	
	/**Methode, um eine neue Gruppe in die DB zu schreiben.
	 * Im ersten Schritt wird die Datenbankverbindung hergestellt.
	 * Danach werden die Parameter für das SQL-Statement mit Get-Methoden übergeben und das gesamte SQL-Statement in einem String "ps" gespeichert.
	 * Im letzten Schritt wird der String an die Instanzmethode "insert" aus der Klasse "MysqlCon" übergeben und damit ein neuer Datensatz in der DB erzeugt.
	 *  
	 * @throws SQLException
	 */
	public static void insertGruppe(String gruppe, String beschreibung) throws SQLException
	{
		Main.get_DBConnection().ExecuteTransact(String.format("INSERT INTO ag (gruppenname, gruppenbeschreibung, agstatus) "
				+ "VALUES ('%s', '%s', 'aktiv')", gruppe, beschreibung));
	}
	
	/**Methode, um eine Gruppe in der DB zu ändern. Der Übergabewert "name" stellt den Gruppennamen der zu bearbeitenden Gruppe dar.
	 * Im ersten Schritt wird die Datenbankverbindung hergestellt.
	 * Danach werden die Parameter für das SQL-Statement mit Get-Methoden übergeben und das gesamte SQL-Statement in einem String "ps" gespeichert.
	 * Im letzten Schritt wird der String an die Instanzmethode "update" aus der Klasse "MysqlCon" übergeben und damit der entsprechende Datensatz geändert.
	 *  
	 * @throws SQLException
	 */
	public void updateGruppeById(int id, String gruppe, String beschreibung) throws SQLException
	{
		Main.get_DBConnection().ExecuteTransact(String.format("UPDATE ag SET gruppe = '%d', ag_fk = '%s' WHERE agid = '%s';", gruppe, beschreibung, id));
	}
	
	/**Methode, um eine Gruppe aus der DB zu löschen. Der Übergabewert "name" stellt den Gruppennamen der zu löschenden Gruppe dar.
	 * Im ersten Schritt wird die Datenbankverbindung hergestellt.
	 * Danach werden die Parameter für das SQL-Statement mit Get-Methoden übergeben und das gesamte SQL-Statement in einem String "ps" gespeichert.
	 * Im letzten Schritt wird der String an die Instanzmethode "update" aus der Klasse "MysqlCon" übergeben und damit die entsprechende Gruppe gelöscht.
	 *  
	 * @throws SQLException
	 */
	public static void deleteGruppe(String id) throws SQLException
	{
		Main.get_DBConnection().ExecuteTransact(String.format("UPDATE ag SET agstatus = 'inaktiv' WHERE gruppenname = '%s'", id));
	}
	
	// Methode um TableView Benutzerverwaltung zu befüllen
	public static ObservableList getGruppenverwaltung() throws SQLException {
		
	    ObservableList<Gruppe> data = FXCollections.observableArrayList();

	    Main.get_DBConnection().Execute("SELECT * FROM ag WHERE agstatus <> 'inaktiv'");
		ResultSet rs = Main.get_DBConnection().get_last_resultset();

		while(rs.next())
		{
			data.add(new Gruppe(rs.getString("gruppenname")));
		}
		return data;
	}
	
	public Gruppe getGruppe(int agid) throws SQLException
	{
		Main.get_DBConnection().Execute(String.format("SELECT gruppenname FROM ag WHERE agid = '%s'", agid));
		ResultSet rs = Main.get_DBConnection().get_last_resultset();

		if(rs.next())
		{
			this.gruppenname = rs.getString("gruppenname");	    
		}
		return this;
	}
	
	public Gruppe getGruppeByName(String name) throws SQLException
	{
	    Main.get_DBConnection().Execute(String.format("SELECT * FROM ag WHERE gruppenname = '%s'", name));
		ResultSet rs = Main.get_DBConnection().get_last_resultset();

		if(rs.next())
		{
			this.agid = rs.getString("agid");
			this.gruppenname = rs.getString("gruppenname");
			this.gruppenbeschr = rs.getString("gruppenbeschreibung");	    
		}
		return this;
	}
	
	public static ObservableList getGruppennamen() throws SQLException {
		
		ObservableList<String> data = FXCollections.observableArrayList();
	    Main.get_DBConnection().Execute("SELECT * FROM ag WHERE agstatus <> 'inaktiv'");
		ResultSet rs = Main.get_DBConnection().get_last_resultset();

		while(rs.next())
		{
			data.add(rs.getString("gruppenname"));
		}
		return data;
	}
	
	
	/** ***************************************************************************************************************************************************
	 * ******************************************************Implementierung der Getter und Setter*********************************************************
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
