package application;

import java.sql.ResultSet;
import java.sql.SQLException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Gruppe {
	
	//Initialisierung der Attribute nach den Attributen in der DB
	private String gruppenname;
	private String gruppenbeschr;
	
	
	
	/** ***************************************************************************************************************************************************
	 * *********************************************************Implementierung der Methoden***************************************************************
	 ******************************************************************************************************************************************************/
	
	public Gruppe() 
	{
		
	}
	
	public Gruppe(String GName) throws SQLException
	{
		this.getGruppeByBenutzername(GName);
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
		Main.get_DBConnection().ExecuteTransact(String.format("INSERT INTO ag (gruppenname, gruppenbeschreibung) "
				+ "VALUES ('%s', '%s')", gruppe, beschreibung));
	}
	
	/**Methode, um eine Gruppe in der DB zu ändern. Der Übergabewert "name" stellt den Gruppennamen der zu bearbeitenden Gruppe dar.
	 * Im ersten Schritt wird die Datenbankverbindung hergestellt.
	 * Danach werden die Parameter für das SQL-Statement mit Get-Methoden übergeben und das gesamte SQL-Statement in einem String "ps" gespeichert.
	 * Im letzten Schritt wird der String an die Instanzmethode "update" aus der Klasse "MysqlCon" übergeben und damit der entsprechende Datensatz geändert.
	 *  
	 * @throws SQLException
	 */
	public void updateGruppeById(String gruppe, String beschreibung) throws SQLException
	{
		Main.get_DBConnection().ExecuteTransact(String.format("UPDATE ag SET gruppe = '%d', ag_fk = '%s' WHERE benutzername = '%s';", gruppe, beschreibung));
	}
	
	/**Methode, um eine Gruppe aus der DB zu löschen. Der Übergabewert "name" stellt den Gruppennamen der zu löschenden Gruppe dar.
	 * Im ersten Schritt wird die Datenbankverbindung hergestellt.
	 * Danach werden die Parameter für das SQL-Statement mit Get-Methoden übergeben und das gesamte SQL-Statement in einem String "ps" gespeichert.
	 * Im letzten Schritt wird der String an die Instanzmethode "update" aus der Klasse "MysqlCon" übergeben und damit die entsprechende Gruppe gelöscht.
	 *  
	 * @throws SQLException
	 */
	public static void deleteGruppe(String name) throws SQLException
	{
		MysqlCon db = new MysqlCon();
		db.getDbCon();
		String ps = "DELETE FROM ag WHERE gruppenname = " + name;
		db.executeSt(ps);
		
	}
	
	// Methode um TableView Benutzerverwaltung zu befüllen
	public static ObservableList getGruppenverwaltung() throws SQLException {
		
	    ObservableList<Gruppe> data = FXCollections.observableArrayList();

	    Main.get_DBConnection().Execute("SELECT * FROM ag");
		ResultSet rs = Main.get_DBConnection().get_last_resultset();

		while(rs.next())
		{
			data.add(new Gruppe(rs.getString("gruppenname")));
		}
		return data;
	}
	
	
	public Gruppe getGruppeByBenutzername(String GName) throws SQLException
	{
	    Main.get_DBConnection().Execute(String.format("SELECT * FROM ag WHERE gruppenname = '%s'", GName));
		ResultSet rs = Main.get_DBConnection().get_last_resultset();

		if(rs.next())
		{
			this.gruppenname = rs.getString("gruppenname");
			this.gruppenbeschr = rs.getString("gruppenbeschreibung");	    
		}
		return this;
	}
	
	/** ***************************************************************************************************************************************************
	 * ******************************************************Implementierung der Getter und Setter*********************************************************
	 ******************************************************************************************************************************************************/
	
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
