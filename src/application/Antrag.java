package application;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;


import java.sql.*;
import java.util.Date;

public class Antrag {
	
	//Initialisierung der Attribute nach den Attributen in der DB
	private int antragid;
	private String name;
	private String beschreibung;
	private Date antragsdatum;
	private Date fertigstellungsdatum;
	private String status;
	private String ablehnungsgrund;
	private String anmerkung;
	private Benutzer ersteller;
	private Benutzer bearbeiter;
	private Gruppe erstGruppe;
	private Gruppe bearGruppe;
	
	
	//Attribute, die darüberhinaus noch benötigt werden
	private int idZaehler = 10000;
	private int antragzaehler = 1;
	
	
	
	/** ***************************************************************************************************************************************************
	 * *********************************************************Implementierung der Methoden***************************************************************
	 ******************************************************************************************************************************************************/
	
	
	
	public Antrag()
	{
		
	}
	
	public Antrag(int pID) throws SQLException
	{
		this.getAntragById(pID);
	}
	
	
	/**Methode, um einen neuen Antrag in die DB zu schreiben.
	 * Im ersten Schritt wird die Datenbankverbindung hergestellt.
	 * Danach werden die Parameter für das SQL-Statement mit Get-Methoden übergeben und das gesamte SQL-Statement in einem String "ps" gespeichert.
	 * Im letzten Schritt wird der String an die Instanzmethode "insert" aus der Klasse "MysqlCon" übergeben und damit ein neuer Datensatz in der DB erzeugt.
	 *  
	 * @throws SQLException
	 */
	public void insertAntrag(String name, String ersteller, LocalDate erstelldatum, LocalDate zieldatum, String text) throws SQLException
	{	
		antragzaehler = antragzaehler + 1;
		MysqlCon db = new MysqlCon();
		db.getDbCon();
		String ps = "INSERT INTO antrag "
				+ "(idantrag, titel, beschreibung, fertigstellungsdatum, antragsdatum, status, ablehnungsgrund, anmerkung, ersteller_fk, bearbeiter_fk, ag_ersteller_fk, ag_bearbeiter_fk) "
				+ "VALUES "+ "('"+this.antragzaehler+"', '"+name+"', '"+text+"','"+zieldatum+"', '"
		 		+erstelldatum+"', 'erstellt', '', '', '"+ersteller+"', 'NULL', '"+this.getErstGruppe()+"', '')";
		db.insert(ps);
	}
	
	/**Methode, um einen Antrag in der DB zu überschreiben bzw. zu korrigieren. Der Übergabewert "id" stellt die AntragsID des zu korrigierenden Antrags dar.
	 * Im ersten Schritt wird die Datenbankverbindung hergestellt.
	 * Danach werden die Parameter für das SQL-Statement mit Get-Methoden übergeben und das gesamte SQL-Statement in einem String "ps" gespeichert.
	 * Im letzten Schritt wird der String an die Instanzmethode "update" aus der Klasse "MysqlCon" übergeben und damit der entsprechende Datensatz überschrieben.
	 *  
	 * @throws SQLException
	 */
	public void updateAntragById(int id) throws SQLException
	{
		MysqlCon db = new MysqlCon();
		db.getDbCon();
		String ps = "UPDATE antrag SET idantrag = '" + this.getAntragid() + "', titel = '" + this.getName() + "', beschreibung = '" + this.getBeschreibung() 
				+ "', fertigstellungsdatum = '" + this.getFertigstellungsdatum() + "', antragsdatum = '" + this.getAntragsdatum() + "', status = '" + this.getStatus()
				+ "', ablehnungsgrund = '" + this.getAblehnungsgrund() + "', anmerkung = '" + this.getAnmerkung() + "', ersteller_fk = '" + this.getErsteller() 
				+ "', bearbeiter_fk = '" + this.getBearbeiter() + "', ag_ersteller_fk = '" + this.getErstGruppe() + "', ag_bearbeiter_fk = '" + this.getBearbeiterGruppe()
				+ "WHERE idantrag = " + id;
		db.update(ps);
		
	}
	
	/**Methode, um einen Antrag aus der DB zu löschen. Der Übergabewert "id" stellt die AntragsID des zu löschenden Antrags dar.
	 * Im ersten Schritt wird die Datenbankverbindung hergestellt.
	 * Danach werden die Parameter für das SQL-Statement mit Get-Methoden übergeben und das gesamte SQL-Statement in einem String "ps" gespeichert.
	 * Im letzten Schritt wird der String an die Instanzmethode "update" aus der Klasse "MysqlCon" übergeben und damit der entsprechende Datensatz gelöscht.
	 *  
	 * @throws SQLException
	 */
	public void deleteAntragById(int id) throws SQLException
	{
		MysqlCon db = new MysqlCon();
		db.getDbCon();
		String ps = "DELETE FROM antrag WHERE idantrag = " + id;
		db.delete(ps);
		
	}
	
	
	public Antrag getAntragById(int pID) throws SQLException
	{
		MysqlCon db = new MysqlCon();
		db.getDbCon();
		ResultSet rs = db.query("select * from antrag WHERE idantrag='"+ pID +"'");
		if(rs.first())
		{
			this.antragid = rs.getInt("idantrag");
			this.name = rs.getString("titel");
			this.beschreibung = rs.getString("beschreibung");
			this.fertigstellungsdatum = rs.getDate("fertigstellungsdatum");
			this.antragsdatum = rs.getDate("antragsdatum");
			this.status = rs.getString("status");
			this.ablehnungsgrund = rs.getString("ablehnungsgrund");
			this.anmerkung = rs.getString("anmerkung");
			this.ersteller = (Benutzer) rs.getObject("ersteller_fk");
			this.bearbeiter = (Benutzer) rs.getObject("bearbeiter_fk");
			this.erstGruppe = (Gruppe) rs.getObject("ag_ersteller_fk");
			this.bearGruppe = (Gruppe) rs.getObject("ag_bearbeiter_fk");
		    
		}
		
		return this;
	}
	
		
	/**Methode, um einen Antrag aus der DB auszugeben. Der Eingabewert "ersteller" stellt den Antragsersteller des auszugebenden Antrags dar.
	 * Im ersten Schritt wird die Datenbankverbindung hergestellt.
	 * Danach werden die Parameter fuer das SQL-Statement mit Get-Methoden uebergeben und das gesamte SQL-Statement in einem String "ps" gespeichert.
	 *  
	 * @throws SQLException
	 */
	public static ObservableList getAntraegeByBenutzer(String benutzer) throws SQLException
	{
		//Was macht denn rs.getInt("idantrag")?
	    ObservableList data = FXCollections.observableArrayList();
	    try {
	    	MysqlCon db = new MysqlCon();
	    	db.getDbCon();
	    	ResultSet rs = db.query("select idantrag, titel, fertigstellungsdatum from antrag WHERE ersteller_fk='"+ benutzer +"'");
	    	while(rs.next()) {
	    		
	    		data.add(new Antrag(rs.getInt("idantrag")));
	    	}
	    } catch(SQLException e) {
	    	System.out.println(e);
	    }
	    	return data;
	}
	

	public static ObservableList getAntraege() {
	    ObservableList<Antrag> data = FXCollections.observableArrayList();
	    try {
	    	MysqlCon db = new MysqlCon();
			db.getDbCon();
			ResultSet rs = db.query("SELECT idantrag, titel, fertigstellungsdatum FROM antrag");
	        while(rs.next()) {

	            data.add(new Antrag(rs.getInt("idantrag")));
	        }

	    } catch(SQLException e) {
	        System.out.println(e);
	    }

	    return data;
	}
	
	
	
	/**Methode, um einen Antrag aus der DB auszugeben. Der Eingabewert "status" stellt den Antragsersteller des auszugebenden Antrags dar.
	 * Im ersten Schritt wird die Datenbankverbindung hergestellt.
	 * Danach werden die Parameter fuer das SQL-Statement mit Get-Methoden uebergeben und das gesamte SQL-Statement in einem String "ps" gespeichert.
	 *  
	 * @throws SQLException
	 */
	public ResultSet getAntragByStatus(String status) throws SQLException
	{
		MysqlCon db = new MysqlCon();
		db.getDbCon();
		ResultSet rs = db.query("select * from antrag WHERE status='"+ status +"'");
		while(rs.next())
		{
			this.antragid = rs.getInt("idantrag");
			this.name = rs.getString("titel");
			this.fertigstellungsdatum = rs.getDate("fertigstellungsdatum");
			this.ersteller = (Benutzer) rs.getObject("ersteller_fk");
			this.bearbeiter = (Benutzer) rs.getObject("bearbeiter_fk");
		}
		
		return rs;
	}
	
	
	/**Methode, um einen Antrag aus der DB auszugeben. Der Eingabewert "arbeitsgruppe" stellt den Antragsersteller des auszugebenden Antrags dar.
	 * Im ersten Schritt wird die Datenbankverbindung hergestellt.
	 * Danach werden die Parameter fuer das SQL-Statement mit Get-Methoden uebergeben und das gesamte SQL-Statement in einem String "ps" gespeichert.
	 *  
	 * @throws SQLException
	 */
	public static ResultSet getAntragByBaergruppe(String arbeitsgruppe) throws SQLException
	{
		MysqlCon db = new MysqlCon();
		db.getDbCon();
		ResultSet rs = db.query("select * from antrag WHERE ersteller='"+ arbeitsgruppe +"'");
		return rs;
	}
	
	
	
	/**Methode, um einen Antrag aus der DB auszugeben. Der Eingabewert "datum" stellt den Antragsersteller des auszugebenden Antrags dar.
	 * Im ersten Schritt wird die Datenbankverbindung hergestellt.
	 * Danach werden die Parameter fuer das SQL-Statement mit Get-Methoden uebergeben und das gesamte SQL-Statement in einem String "ps" gespeichert.
	 *  
	 * @throws SQLException
	 */
	public static Antrag getAntragbyDatum (Date fertigstellungsdatum) throws SQLException
	{
		Antrag a1 = null;
		MysqlCon db = new MysqlCon();
		db.getDbCon();
		ResultSet rs = db.query("select * from antrag WHERE fertigstellungsdatum='"+ fertigstellungsdatum +"'");
		
		while(rs.next())
		{
			a1 = new Antrag();
			a1.antragid = rs.getInt("idantrag");
			a1.name = rs.getString("titel");
			a1.fertigstellungsdatum = rs.getDate("fertigstellungsdatum");
			a1.status = rs.getString("status");
			a1.bearbeiter = (Benutzer) rs.getObject("bearbeiter_fk");
		}
		
		return a1;
	}
	
	/** ***************************************************************************************************************************************************
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
	
	public Date getAntragsdatum(){
	    return antragsdatum;
	}
	
	public void setAntragsdatum(Date aDatum){
	    this.antragsdatum = aDatum;
	}
	
	public Date getFertigstellungsdatum(){
	    return fertigstellungsdatum;
	}
	
	public void setFertigstellungsdatum(Date fDatum){
	    this.fertigstellungsdatum = fDatum;
	}
	
	public String getStatus(){
	    return status;
	}
	
	public void setStatus(String status){
	    this.status = status;
	}
	
	public String getAblehnungsgrund(){
	    return ablehnungsgrund;
	}
	
	public void setAblehnungsgrund(String abGrund){
	    this.ablehnungsgrund = abGrund;
	}

    public String getAnmerkung(){
        return anmerkung;
    }
    
    public void setAnmerkung(String anmerkung){
        this.anmerkung = anmerkung;
    }
    
    public Benutzer getErsteller(){
        return ersteller;
    }
    
    public void setErsteller(Benutzer ersteller){
        this.ersteller = ersteller;
    }

    public Gruppe getErstGruppe(){
        return erstGruppe;
    }
    
    public void setErstellerGruppe(Gruppe eGruppe){
        this.erstGruppe = eGruppe;
    }
    
    public Benutzer getBearbeiter(){
        return bearbeiter;
    }
    
    public void setBearbeiter(Benutzer bearbeiter){
        this.bearbeiter = bearbeiter;
    }

	    public Gruppe getBearbeiterGruppe(){
        return bearGruppe;
    }
    
    public void setBearbeiterGruppe(Gruppe bGruppe){
        this.bearGruppe = bGruppe;
    }

	public void getAuftragByNr(int pNR)
	{
		
	}

}
