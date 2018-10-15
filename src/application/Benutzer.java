package application;

import java.sql.*;
import java.util.Observable;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Benutzer {
	
	//Initialisierung der Attribute nach den Attributen in der DB
	private int id;
	private String benutzername;
	private String passwort;
	private int berechtigung;
	private String gruppe;
	
	private static String bnPrüfen;
	private static int bPrüfen;
	private static String gPrüfen;

	private static boolean anmelden;
	
	
	

	/** ***************************************************************************************************************************************************
	 * *********************************************************Implementierung der Methoden***************************************************************
	 ******************************************************************************************************************************************************/
	
	public Benutzer()
	{
		
	}
	
	public Benutzer(String name) throws SQLException
	{
		this.getBenutzerByName(name);
	}


	

/*	public Benutzer(String int1) {
		this.benutzername="admin";
		this.passwort="pw123";
	}
*/


	/*public BenutzerMethode(String pBenutzer, String pPasswort) throws SQLException
	{
		this.getBenutzerByBenutzerAndPassword(pBenutzer,pPasswort);
	}
	*/
	public static boolean isRightBenutzer(String pBenutzer, String pPasswort) throws SQLException
	{
		Main.get_DBConnection().Execute(String.format("SELECT * FROM benutzer WHERE benutzer = '%s' AND passwort = '%s';", pBenutzer, pPasswort));
		ResultSet rs = Main.get_DBConnection().get_last_resultset();
		
		if(rs.next())
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	 public static boolean anmelden(String benN, String p) throws SQLException{
	        String eingabeName = benN;
	        String eingabePasswort = p;
	        String vergleichsPasswort = null;
	        try {
	        	
	        	Main.get_DBConnection().Execute(String.format("SELECT passwort FROM benutzer WHERE benutzername = '%s';", benN));
				ResultSet rsP = Main.get_DBConnection().get_last_resultset();
	        	
	        	while(rsP.next())
	        	{
		        	vergleichsPasswort = rsP.getString("passwort");
		        	
		        	if(BCrypt.checkpw(p, vergleichsPasswort))   {
			            anmelden = true;
			        } else {
			            anmelden = false;
			        }
	        	}
	        	
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        
			return anmelden;
	    }
	 
	 
	 	public static int berechtigungPrüfen(String benN) throws SQLException{
	        
	 		bnPrüfen = benN;
       
	 		Main.get_DBConnection().Execute(String.format("SELECT blevel FROM benutzer WHERE benutzername = '%s';", benN));
			ResultSet rs = Main.get_DBConnection().get_last_resultset();

	        if(rs.first()){
	        	bPrüfen = (Integer) rs.getInt("blevel");
	        }
	         if(anmelden==true){
		            return bPrüfen;
		     } else {
		            return 0;
		     }    
	    }

	/**Methode, um einen neuen Benutzer in die DB zu schreiben.
	 * Im ersten Schritt wird die Datenbankverbindung hergestellt.
	 * Danach werden die Parameter für das SQL-Statement mit Get-Methoden übergeben und das gesamte SQL-Statement in einem String "ps" gespeichert.
	 * Im letzten Schritt wird der String an die Instanzmethode "insert" aus der Klasse "MysqlCon" übergeben und damit ein neuer Datensatz in der DB erzeugt.
	 *  
	 * @throws SQLException
	 */
	public static void insertBenutzer(String benutzer, String passwort, int berechtigung, String gruppe) throws SQLException
	{
		passwort = BCrypt.hashpw(passwort, BCrypt.gensalt());
		Main.get_DBConnection().ExecuteTransact(String.format("INSERT INTO benutzer (benutzername, passwort, blevel, ag_fk) "
				+ "VALUES ('%s', '%s', '%d', '%s')", benutzer, passwort, berechtigung, gruppe));
	}
	
	/**Methode, um einen Benutzer in der DB zu bearbeiten. Der Übergabewert "name" stellt den Benutzernamen des zu bearbeitenden Benutzers dar.
	 * Im ersten Schritt wird die Datenbankverbindung hergestellt.
	 * Danach werden die Parameter für das SQL-Statement mit Get-Methoden übergeben und das gesamte SQL-Statement in einem String "ps" gespeichert.
	 * Im letzten Schritt wird der String an die Instanzmethode "update" aus der Klasse "MysqlCon" übergeben und damit der entsprechende Datensatz geändert.
	 *  
	 * @throws SQLException
	 */
	public static void updateBenutzerPw(String name, String passwort, String gruppe, int berechtigung) throws SQLException
	{
		passwort = BCrypt.hashpw(passwort, BCrypt.gensalt());
		Main.get_DBConnection().ExecuteTransact(String.format("UPDATE benutzer SET passwort = '%s',"
				+ " blevel = '%d', ag_fk = '%s' WHERE benutzername = '%s';", passwort, berechtigung, gruppe, name));
	}
	
	/**Methode, um einen Benutzer in der DB zu bearbeiten. Der Übergabewert "name" stellt den Benutzernamen des zu bearbeitenden Benutzers dar.
	 * Im ersten Schritt wird die Datenbankverbindung hergestellt.
	 * Danach werden die Parameter für das SQL-Statement übergeben und das gesamte SQL-Statement in einem String gespeichert.
	 *  
	 * @throws SQLException
	 */
	public static void updateBenutzer(String name, String gruppe, int berechtigung) throws SQLException
	{
		Main.get_DBConnection().ExecuteTransact(String.format("UPDATE benutzer SET blevel = '%d', ag_fk = '%s' WHERE benutzername = '%s';", berechtigung, gruppe, name));
	}
	
	/**Methode, um einen Benutzer aus der DB zu löschen. Der Übergabewert "name" stellt den Benutzernamen des zu löschenden Benutzers dar.
	 * Im ersten Schritt wird die Datenbankverbindung hergestellt.
	 * Danach werden die Parameter für das SQL-Statement mit Get-Methoden übergeben und das gesamte SQL-Statement in einem String "ps" gespeichert.
	 * Im letzten Schritt wird der String an die Instanzmethode "update" aus der Klasse "MysqlCon" übergeben und damit der entsprechende Benutzer gelöscht.
	 *  
	 * @throws SQLException
	 */
	public static void deleteBenutzer(String name) throws SQLException
	{	
		Main.get_DBConnection().ExecuteTransact(String.format("DELETE FROM benutzer WHERE benutzername = '%s';", name));
	}
	
	
	public Benutzer getBenutzerByBenutzerAndPassword(String pBenutzer, String pPasswort) throws SQLException
	{
		Main.get_DBConnection().Execute(String.format("select * from benutzer WHERE benutzer='%s' AND passwort = '%s';", pBenutzer, pPasswort));
		ResultSet rs = Main.get_DBConnection().get_last_resultset();

		while(rs.next())
		{	
			this.benutzername = rs.getString("benutzername");
			this.passwort = rs.getString("passwort");
			this.gruppe = (String)rs.getObject("gruppe");
		}
		return this;
	}
	
	// Methode um TableView Benutzerverwaltung zu befüllen
	public static ObservableList getBenutzerverwaltung() throws SQLException {
		
	    ObservableList<Benutzer> data = FXCollections.observableArrayList();

	    Main.get_DBConnection().Execute(String.format("SELECT * FROM benutzer"));
		ResultSet rs = Main.get_DBConnection().get_last_resultset();

		while(rs.next())
		{
			data.add(new Benutzer(rs.getString("benutzername")));
		}
		return data;
	}
	
	public Benutzer getBenutzerByName(String name) throws SQLException
	{
	    Main.get_DBConnection().Execute(String.format("SELECT * FROM benutzer WHERE benutzername = '%s'", name));
		ResultSet rs = Main.get_DBConnection().get_last_resultset();

		if(rs.next())
		{
			this.id = rs.getInt("benutzerid");
			this.benutzername = rs.getString("benutzername");
			this.passwort = rs.getString("passwort");
			this.gruppe = rs.getString("ag_fk");
			this.berechtigung = rs.getInt("blevel");		    
		}
		return this;
	}
	
	
	public static ObservableList getBenutzerByGruppe(int id) throws SQLException {
		
	    ObservableList<Benutzer> data = FXCollections.observableArrayList();

	    Main.get_DBConnection().Execute(String.format("SELECT benutzername FROM benutzer WHERE ag_fk = '%s'", id));
		ResultSet rs = Main.get_DBConnection().get_last_resultset();

		while(rs.next())
		{
			data.add(new Benutzer(rs.getString("benutzername")));
		}
		return data;
	}
	
	
	public static String getBearGruppeByUser(String benN) throws SQLException
	{
 		bnPrüfen = benN;
   
 		Main.get_DBConnection().Execute(String.format("SELECT ag_fk FROM benutzer WHERE benutzername = '%s'", benN));
 		ResultSet rs = Main.get_DBConnection().get_last_resultset();
 		
        if(rs.first()){
        	gPrüfen = (String) rs.getString("ag_fk");
        }
         if(anmelden==true){
	            return gPrüfen;
	     } else {
	            return null;
	     }    
    }
	
	public static Boolean selberName(String benutzer) {
		
	    Main.get_DBConnection().Execute(String.format("SELECT * FROM benutzer WHERE benutzername = '%s'", benutzer));
		ResultSet rs = Main.get_DBConnection().get_last_resultset();
		if (rs == null) {
			return false;
		}else {
			return true;
		}
	}	

	
	
	/*****************************************************************************************************************************************************
	 *******************************************************Implementierung der Getter und Setter*********************************************************
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
	public String getGruppe() {
		return gruppe;
	}
	public void setGruppe(String gruppe) {
		this.gruppe = gruppe;
	}

}