package application;

import java.sql.SQLException;

public class Gruppe {
	
	//Initialisierung der Attribute nach den Attributen in der DB
	public String gruppenname;
	public String gruppenbeschr;
	
	
	
	/** ***************************************************************************************************************************************************
	 * *********************************************************Implementierung der Methoden***************************************************************
	 ******************************************************************************************************************************************************/
	
	public Gruppe() 
	{
		
	}
	
	/**Methode, um eine neue Gruppe in die DB zu schreiben.
	 * Im ersten Schritt wird die Datenbankverbindung hergestellt.
	 * Danach werden die Parameter f�r das SQL-Statement mit Get-Methoden �bergeben und das gesamte SQL-Statement in einem String "ps" gespeichert.
	 * Im letzten Schritt wird der String an die Instanzmethode "insert" aus der Klasse "MysqlCon" �bergeben und damit ein neuer Datensatz in der DB erzeugt.
	 *  
	 * @throws SQLException
	 */
	public void insertGruppe() throws SQLException
	{
		MysqlCon db = new MysqlCon();
		db.getDbCon();
		String ps = "INSERT INTO benutzer "
				+ "(gruppenname, gruppenbeschreibung) " + "VALUES " + "('" + this.getGruppenname() + "', '" + this.getGruppenbeschr() + "')";
		db.executeSt(ps);
	}
	
	/**Methode, um eine Gruppe in der DB zu �ndern. Der �bergabewert "name" stellt den Gruppennamen der zu bearbeitenden Gruppe dar.
	 * Im ersten Schritt wird die Datenbankverbindung hergestellt.
	 * Danach werden die Parameter f�r das SQL-Statement mit Get-Methoden �bergeben und das gesamte SQL-Statement in einem String "ps" gespeichert.
	 * Im letzten Schritt wird der String an die Instanzmethode "update" aus der Klasse "MysqlCon" �bergeben und damit der entsprechende Datensatz ge�ndert.
	 *  
	 * @throws SQLException
	 */
	public void updateGruppeById(int name) throws SQLException
	{
		MysqlCon db = new MysqlCon();
		db.getDbCon();
		String ps = "UPDATE ag SET gruppenname = '" + this.getGruppenname() + "', gruppenbeschreibung = '" + this.getGruppenbeschr() + "WHERE benutzername = " + name;
		db.executeSt(ps);
		
	}
	
	/**Methode, um eine Gruppe aus der DB zu l�schen. Der �bergabewert "name" stellt den Gruppennamen der zu l�schenden Gruppe dar.
	 * Im ersten Schritt wird die Datenbankverbindung hergestellt.
	 * Danach werden die Parameter f�r das SQL-Statement mit Get-Methoden �bergeben und das gesamte SQL-Statement in einem String "ps" gespeichert.
	 * Im letzten Schritt wird der String an die Instanzmethode "update" aus der Klasse "MysqlCon" �bergeben und damit die entsprechende Gruppe gel�scht.
	 *  
	 * @throws SQLException
	 */
	public void deleteGruppeById(int name) throws SQLException
	{
		MysqlCon db = new MysqlCon();
		db.getDbCon();
		String ps = "DELETE FROM ag WHERE gruppenname = " + name;
		db.executeSt(ps);
		
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
