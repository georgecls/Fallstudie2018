package application;

import java.sql.*;
import java.util.Observable;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Benutzer {
	
	//Initialisierung der Attribute nach den Attributen in der DB
	private String benutzername;
	private String passwort;
	private int berechtigung;
	private String gruppe;
	
	private static String bnPr�fen;
	private static int bPr�fen;
	private static String gPr�fen;

	
	private static boolean anmelden;
	
	
	

	/** ***************************************************************************************************************************************************
	 * *********************************************************Implementierung der Methoden***************************************************************
	 ******************************************************************************************************************************************************/
	
	public Benutzer()
	{
		
	}
	
	public Benutzer(String BName) throws SQLException
	{
		this.getAntragByBenutzername(BName);
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
		MysqlCon db = new MysqlCon();
		db.getDbCon();
		ResultSet rs = db.query("select * from benutzer WHERE benutzer='"+ pBenutzer +"' and passwort = '"+ pPasswort +"'");
		if(rs.next())
		{
			return true;
		}
		else
		{
			return false;
		}
		
	}
	
	 public static boolean anmelden(String benN, String p){
	        String eingabeName = benN;
	        String eingabePasswort = p;
	        String vergleichsPasswort = "";
	        try {
	        	MysqlCon db = new MysqlCon();
	        	db.getDbCon();
	        	ResultSet rsN;
	        	ResultSet rsP;
				rsP = db.query("select passwort from benutzer WHERE benutzername ='"+benN+"'");
	        	
	        	while(rsP.next())
	        	{
		        	vergleichsPasswort = rsP.getString("passwort");
		        	
					
					if(vergleichsPasswort.equals(p)) {
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
	 
	 
	 	public static int berechtigungPr�fen(String benN) throws SQLException{
	        
	 		bnPr�fen = benN;
       
	        MysqlCon db = new MysqlCon();
	        db.getDbCon();
	        ResultSet rs = db.query("select blevel from benutzer WHERE benutzername ='"+benN+"'");
	        if(rs.first()){
	        	bPr�fen = (Integer) rs.getInt("blevel");
	        }
	         if(anmelden==true){
		            return bPr�fen;
		     } else {
		            return 0;
		     }    
	    }


	

	/**Methode, um einen neuen Benutzer in die DB zu schreiben.
	 * Im ersten Schritt wird die Datenbankverbindung hergestellt.
	 * Danach werden die Parameter f�r das SQL-Statement mit Get-Methoden �bergeben und das gesamte SQL-Statement in einem String "ps" gespeichert.
	 * Im letzten Schritt wird der String an die Instanzmethode "insert" aus der Klasse "MysqlCon" �bergeben und damit ein neuer Datensatz in der DB erzeugt.
	 *  
	 * @throws SQLException
	 */
	public static void insertBenutzer(String benutzer, String passwort, int berechtigung, String gruppe) throws SQLException
	{
		MysqlCon db = new MysqlCon();
		db.getDbCon();
		String ps = "INSERT INTO benutzer"
				+ "(benutzername, passwort, blevel, ag_fk) VALUES ('" + benutzer 
				+ "', '" + passwort + "', '" + berechtigung + "','" + gruppe +"')";
		db.executeSt(ps);
	}
	
	/**Methode, um einen Benutzer in der DB zu bearbeiten. Der �bergabewert "name" stellt den Benutzernamen des zu bearbeitenden Benutzers dar.
	 * Im ersten Schritt wird die Datenbankverbindung hergestellt.
	 * Danach werden die Parameter f�r das SQL-Statement mit Get-Methoden �bergeben und das gesamte SQL-Statement in einem String "ps" gespeichert.
	 * Im letzten Schritt wird der String an die Instanzmethode "update" aus der Klasse "MysqlCon" �bergeben und damit der entsprechende Datensatz ge�ndert.
	 *  
	 * @throws SQLException
	 */
	public static void updateBenutzer(String name, String passwort, String gruppe, int berechtigung) throws SQLException
	{
		MysqlCon db = new MysqlCon();
		db.getDbCon();
		String ps = "UPDATE benutzer SET benutzername = '" + name + "', passwort = '" + passwort + "', blevel = '" + berechtigung 
				+ "', ag_fk = '" + gruppe + "' WHERE benutzername = '" + name + "'";
		db.executeSt(ps);
		
	}
	
	/**Methode, um einen Benutzer aus der DB zu l�schen. Der �bergabewert "name" stellt den Benutzernamen des zu l�schenden Benutzers dar.
	 * Im ersten Schritt wird die Datenbankverbindung hergestellt.
	 * Danach werden die Parameter f�r das SQL-Statement mit Get-Methoden �bergeben und das gesamte SQL-Statement in einem String "ps" gespeichert.
	 * Im letzten Schritt wird der String an die Instanzmethode "update" aus der Klasse "MysqlCon" �bergeben und damit der entsprechende Benutzer gel�scht.
	 *  
	 * @throws SQLException
	 */
	public static void deleteBenutzer(String name) throws SQLException{
		
		MysqlCon db = new MysqlCon();
		db.getDbCon();
		String ps = "DELETE FROM benutzer WHERE benutzername = '" + name + "'";
		db.executeSt(ps);
		
	}
	
	
	public Benutzer getBenutzerByBenutzerAndPassword(String pBenutzer, String pPasswort) throws SQLException
	{
		MysqlCon db = new MysqlCon();
		db.getDbCon();
		ResultSet rs = db.query("select * from benutzer WHERE benutzer='"+ pBenutzer +"' and passwort='"+ pPasswort +"' ");
		while(rs.next())
		{	
			this.benutzername = rs.getString("benutzername");
			this.passwort = rs.getString("passwort");
			this.gruppe = (String)rs.getObject("gruppe");

		}
		return this;
			
	}
	
	// Methode um TableView Benutzerverwaltung zu bef�llen
	public static ObservableList getBenutzerverwaltung() throws SQLException {
		
	    ObservableList<Benutzer> data = FXCollections.observableArrayList();

		MysqlCon db = new MysqlCon();
		db.getDbCon();
		ResultSet rs = db.query("select * from benutzer");
		while(rs.next())
		{
			data.add(new Benutzer(rs.getString("benutzername")));
		}
		
		return data;
	}
	
	public Benutzer getAntragByBenutzername(String BName) throws SQLException
	{
		MysqlCon db = new MysqlCon();
		db.getDbCon();
		ResultSet rs = db.query("select * from benutzer WHERE benutzername='"+ BName +"'");
		if(rs.next())
		{
			this.benutzername = rs.getString("benutzername");
			this.passwort = rs.getString("passwort");
			this.gruppe = rs.getString("ag_fk");
			this.berechtigung = rs.getInt("blevel");		    
		}
		
		return this;
	}
	
	public static String getBearGruppeByUser(String benN) throws SQLException{
        
 		bnPr�fen = benN;
   
        MysqlCon db = new MysqlCon();
        db.getDbCon();
        ResultSet rs = db.query("select ag_fk from benutzer WHERE benutzername ='"+benN+"'");
        if(rs.first()){
        	gPr�fen = (String) rs.getString("ag_fk");
        }
         if(anmelden==true){
	            return gPr�fen;
	     } else {
	            return null;
	     }    
    }
	
	
	/** ***************************************************************************************************************************************************
	 * ******************************************************Implementierung der Getter und Setter*********************************************************
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
