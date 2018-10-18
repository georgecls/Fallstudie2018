package application;

import java.sql.*;
import java.util.Observable;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Benutzer {
	
	//Initialisierung der Attribute nach den Attributen in der DB
	private static int id;
	private String benutzername;
	private String passwort;
	private int berechtigung;
	private String status;
	private int ag_fk;
	
	private String gruppenname;
	
	private Gruppe agid;
	
	private static String bnPrüfen;
	private static int bPrüfen;
	private static String gPrüfen;

	private static boolean anmelden = false;
	
	/** ***************************************************************************************************************************************************
	 * *********************************************************Implementierung der Methoden***************************************************************
	 ******************************************************************************************************************************************************/
	
	public Benutzer()
	{
		anmelden = false;
	}
	
	public Benutzer(String name) throws SQLException
	{
		this.getBenutzerByName(name);
	}


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
	
	public static boolean anmelden(String benN, String p) throws SQLException 
	{
	       String eingabeName = benN;
	       String eingabePasswort = p;
	       String vergleichsPasswort = null;
	       String aktivität = null;
	       
	       try {
	    	   Main.get_DBConnection().Execute(String.format("SELECT passwort, benutzerid, bstatus FROM benutzer WHERE benutzername = '%s';", benN));
	    	   ResultSet rsP = Main.get_DBConnection().get_last_resultset();
	        	
	    	   while(rsP.next())
	    	   {
	    		   id = rsP.getInt("benutzerid");
	    		   vergleichsPasswort = rsP.getString("passwort");
	    		   aktivität = rsP.getString("bstatus");
	    		   if(BCrypt.checkpw(p, vergleichsPasswort) && aktivität.equals("aktiv"))
	    		   {
	    			   anmelden = true;
			       }
	    		   else
	    		   {
	    			   anmelden = false;
			       }
	        	}
	        	
	       }
	       catch (SQLException e)
	       {
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
		    }
	        else
	        {
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
		Gruppe g1 = new Gruppe();
		g1.getGruppeByName(gruppe);
		String i = g1.getId();
		
		passwort = BCrypt.hashpw(passwort, BCrypt.gensalt());
		Main.get_DBConnection().ExecuteTransact(String.format("INSERT INTO benutzer (benutzername, passwort, blevel, bstatus, ag_fk) "
				+ "VALUES ('%s', '%s', '%d', 'aktiv', '%s')", benutzer, passwort, berechtigung, i));
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
		Gruppe g1 = new Gruppe();
		g1.getGruppeByName(gruppe);
		String i = g1.getId();
		
		passwort = BCrypt.hashpw(passwort, BCrypt.gensalt());
		Main.get_DBConnection().ExecuteTransact(String.format("UPDATE benutzer SET passwort = '%s',"
				+ " blevel = '%d', ag_fk = '%s' WHERE benutzername = '%s';", passwort, berechtigung, i, name));
	}
	
	/**Methode, um einen Benutzer in der DB zu bearbeiten. Der Übergabewert "name" stellt den Benutzernamen des zu bearbeitenden Benutzers dar.
	 * Im ersten Schritt wird die Datenbankverbindung hergestellt.
	 * Danach werden die Parameter für das SQL-Statement übergeben und das gesamte SQL-Statement in einem String gespeichert.
	 *  
	 * @throws SQLException
	 */
	public static void updateBenutzer(String name, String gruppe, int berechtigung) throws SQLException
	{
		Gruppe g1 = new Gruppe();
		g1.getGruppeByName(gruppe);
		String i = g1.getId();
	
		Main.get_DBConnection().ExecuteTransact(String.format("UPDATE benutzer SET blevel = '%d', ag_fk = '%s' " 
								+ "WHERE benutzername = '%s';", berechtigung, i, name));
	}
	
	public static void updateInaktiverBenutzer(String name) throws SQLException
	{
			Main.get_DBConnection().ExecuteTransact(String.format("UPDATE benutzer SET bstatus = 'aktiv' WHERE benutzername = '%s';", name));
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
		Main.get_DBConnection().ExecuteTransact(String.format("UPDATE benutzer SET bstatus = 'inaktiv', ag_fk = NULL WHERE benutzername = '%s';", name));
	}
	
	
//	public Benutzer getBenutzerByBenutzerAndPassword(String pBenutzer, String pPasswort) throws SQLException
//	{
//		Main.get_DBConnection().Execute(String.format("select * from benutzer WHERE benutzer='%s' AND passwort = '%s';", pBenutzer, pPasswort));
//		ResultSet rs = Main.get_DBConnection().get_last_resultset();
//
//		while(rs.next())
//		{	
//			this.benutzername = rs.getString("benutzername");
//			this.passwort = rs.getString("passwort");
//			this.gruppenname = (String) rs.getObject("gruppe");
//		}
//		return this;
//	}
	
	// Methode um TableView Benutzerverwaltung zu befüllen
	public static ObservableList getBenutzerverwaltung() throws SQLException {
		
	    ObservableList<Benutzer> data = FXCollections.observableArrayList();

	    Main.get_DBConnection().Execute("SELECT * FROM benutzer WHERE bstatus <> 'inaktiv'");
		ResultSet rs = Main.get_DBConnection().get_last_resultset();

		while(rs.next())
		{
			data.add(new Benutzer(rs.getString("benutzername")));
		}
		return data;
	}
	
	public Benutzer getBenutzerByName(String name) throws SQLException
	{
	    Main.get_DBConnection().Execute(String.format("SELECT * FROM benutzer INNER JOIN ag ON benutzer.ag_fk = ag.agid WHERE benutzername = '%s'", name));
		ResultSet rs = Main.get_DBConnection().get_last_resultset();

		if(rs.next())
		{
			this.setId(rs.getInt("benutzerid"));
			this.benutzername = rs.getString("benutzername");
			this.passwort = rs.getString("passwort");
			this.berechtigung = rs.getInt("blevel");
			this.ag_fk = rs.getInt("ag_fk");
			this.gruppenname = rs.getString(8);	
		}
		return this;
	}
	
	
	public static ObservableList getBenutzerByGruppe(String id) throws SQLException {
		
	    ObservableList<Benutzer> data = FXCollections.observableArrayList();

	    Main.get_DBConnection().Execute(String.format("SELECT benutzername FROM benutzer WHERE ag_fk = '%s'", id));
		ResultSet rs = Main.get_DBConnection().get_last_resultset();

		while(rs.next())
		{
			data.add(new Benutzer(rs.getString("benutzername")));
		}
		return data;
	}
	
	public static boolean pruefeBenutzer(String id) throws SQLException {

	    Main.get_DBConnection().Execute(String.format("SELECT benutzername FROM benutzer WHERE ag_fk = '%s'", id));
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
        }
        else
        {
	        return null;
	    }    
    }
	
	
	public static Boolean selberName(String benutzer) throws SQLException {
		
		int i = 0;
	    Main.get_DBConnection().Execute(String.format("SELECT * FROM benutzer WHERE benutzername = '%s'", benutzer));
		ResultSet rs = Main.get_DBConnection().get_last_resultset();
		
		if(rs.next())
			i = rs.getInt(1);
		
		if (i == 0) {
			return false;
		}
		else
		{
			return true;
		}
	}	
	
	public static Boolean inaktiverBenutzer(String benutzer) throws SQLException {
		
		int i = 0;
	    Main.get_DBConnection().Execute(String.format("SELECT * FROM benutzer WHERE benutzername = '%s' AND  bstatus = 'inaktiv'", benutzer));
		ResultSet rs = Main.get_DBConnection().get_last_resultset();
		
		if(rs.next())
			i = rs.getInt(1);
		
		if (i == 0) {
			return false;
		}
		else
		{
			return true;
		}
	}	

	
	public static int getIdByName(String name) throws SQLException {
        
        ObservableList<Benutzer> data = FXCollections.observableArrayList();
        int id;

        Main.get_DBConnection().Execute(String.format("SELECT benutzerid FROM benutzer WHERE benutzername = '%s'", name));
        ResultSet rs = Main.get_DBConnection().get_last_resultset();

        if(rs.next()) {
          return id = rs.getInt(1);
        }
        else
        {
           return 0;
        }
    }

	public static int getGruppeByName(String name) throws SQLException {
        
        ObservableList<Benutzer> data = FXCollections.observableArrayList();
        int id;

        Main.get_DBConnection().Execute(String.format("SELECT ag_fk FROM benutzer WHERE benutzername = '%s'", name));
        ResultSet rs = Main.get_DBConnection().get_last_resultset();

        if(rs.next()) {
        	return id = rs.getInt(1);
        }
        else
        {
        	return 0;
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