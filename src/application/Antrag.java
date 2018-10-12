package application;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

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
	
	
	//Attribute, die darÃ¼berhinaus noch benÃ¶tigt werden
	private static int idZaehler = 10000;
	private static int antragzaehler = 1;
	
	
	
	/** ***************************************************************************************************************************************************
	 * *********************************************************Implementierung der Methoden***************************************************************
	 ******************************************************************************************************************************************************/
	
	public Antrag()
	{
		
	}
	
	public Antrag(int pID) throws SQLException
	{		this.getAntragById(pID);	}
	
	
	/**Methode, um einen neuen Antrag in die DB zu schreiben.
	 * Im ersten Schritt wird die Datenbankverbindung hergestellt.
	 * Danach werden die Parameter fÃ¼r das SQL-Statement mit Get-Methoden Ã¼bergeben und das gesamte SQL-Statement in einem String "ps" gespeichert.
	 * Im letzten Schritt wird der String an die Instanzmethode "insert" aus der Klasse "MysqlCon" Ã¼bergeben und damit ein neuer Datensatz in der DB erzeugt.
	 *  
	 * @throws SQLException
	 */
	public static void insertAntrag(String name, String ersteller, LocalDate erstelldatum, LocalDate zieldatum, String text, String erstGruppe) throws SQLException
	{
		
		Main.get_DBConnection().ExecuteTransact(String.format("INSERT INTO antrag (titel, beschreibung, fertigstellungsdatum, antragsdatum, "
				+ "status, ablehnungsgrund, anmerkung, ersteller_fk, bearbeiter_fk, ag_ersteller_fk, ag_bearbeiter_fk) "
				+ "VALUES('%s', '%s', '%s', '%s', 'erstellt', '', '', '%s', NULL, '%s', '%s');"
				, name, text, zieldatum, erstelldatum, ersteller, erstGruppe, erstGruppe));

	}
	
	/**Methode, um einen Antrag in der DB zu Ã¼berschreiben bzw. zu korrigieren. Der Ãœbergabewert "id" stellt die AntragsID des zu korrigierenden Antrags dar.
	 * Im ersten Schritt wird die Datenbankverbindung hergestellt.
	 * Danach werden die Parameter fÃ¼r das SQL-Statement mit Get-Methoden Ã¼bergeben und das gesamte SQL-Statement in einem String "ps" gespeichert.
	 * Im letzten Schritt wird der String an die Instanzmethode "update" aus der Klasse "MysqlCon" Ã¼bergeben und damit der entsprechende Datensatz Ã¼berschrieben.
	 *  
	 * @throws SQLException
	 */
//	public static void updateAntragById(int id) throws SQLException
//	{
//		MysqlCon db = new MysqlCon();
//		db.getDbCon();
//		String ps = "UPDATE antrag SET idantrag = '" + id + "', titel = '" + this.getName() + "', beschreibung = '" + this.getBeschreibung() 
//				+ "', fertigstellungsdatum = '" + this.getFertigstellungsdatum() + "', antragsdatum = '" + this.getAntragsdatum() + "', status = '" + this.getStatus()
//				+ "', ablehnungsgrund = '" + this.getAblehnungsgrund() + "', anmerkung = '" + this.getAnmerkung() + "', ersteller_fk = '" + this.getErsteller() 
//				+ "', bearbeiter_fk = '" + this.getBearbeiter() + "', ag_ersteller_fk = '" + this.getErstGruppe() + "', ag_bearbeiter_fk = '" + this.getBearbeiterGruppe()
//				+ "WHERE idantrag = " + id;
//		db.executeSt(ps);
//		
//	}
	
	/**Methode, um einen Antrag aus der DB zu lÃ¶schen. Der Ãœbergabewert "id" stellt die AntragsID des zu lÃ¶schenden Antrags dar.
	 * Im ersten Schritt wird die Datenbankverbindung hergestellt.
	 * Danach werden die Parameter fÃ¼r das SQL-Statement mit Get-Methoden Ã¼bergeben und das gesamte SQL-Statement in einem String "ps" gespeichert.
	 * Im letzten Schritt wird der String an die Instanzmethode "update" aus der Klasse "MysqlCon" Ã¼bergeben und damit der entsprechende Datensatz gelÃ¶scht.
	 *  
	 * @throws SQLException
	 */
	public static void deleteAntragById(int id) throws SQLException
	{
		Main.get_DBConnection().ExecuteTransact(String.format("DELETE FROM antrag WHERE idantrag = '%d';", id));
	}
	
	/**Methode zieht sich Antrag mit im Parameter angegebener ID.
	 *Zuerst wird die Datenbankverbindung hergestellt.
	 *Im ResultSet wird der ausgeführte SELECT gespeichert. 
	 *in der IF-Abfrage werden die Daten in die jeweiligen Variablen gespeichert und zurückgegeben. 
	 * @param pID
	 * @return
	 * @throws SQLException
	 * #Robin
	 */
	public Antrag getAntragById(int pID) throws SQLException
	{
		Main.get_DBConnection().Execute(String.format("SELECT * FROM antrag WHERE idantrag = '%d';", pID));
		ResultSet rs = Main.get_DBConnection().get_last_resultset();

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
			this.ersteller = new Benutzer(rs.getString("ersteller_fk"));
			this.bearbeiter = (Benutzer) rs.getObject("bearbeiter_fk");
		}
		return this;
	}
	
		
	/**Methode, um einen Antrag aus der DB auszugeben. Der Eingabewert "ersteller" stellt den Antragsersteller des auszugebenden Antrags dar.
	 * Im ersten Schritt wird die Datenbankverbindung hergestellt.
	 * Danach werden die Parameter fuer das SQL-Statement mit Get-Methoden uebergeben und das gesamte SQL-Statement in einem String "ps" gespeichert.
	 * 
	 * @author 
	 * @param benutzer
	 * @throws SQLException
	 */
	public static ObservableList<Antrag> getGruppenAntraege(String benutzer) throws SQLException
	{
	    ObservableList data = FXCollections.observableArrayList();
	    try {
	    	Main.get_DBConnection().Execute(String.format("SELECT * FROM antrag WHERE ersteller_fk = '%s';", benutzer));
	    	ResultSet rs = Main.get_DBConnection().get_last_resultset();
	    	while(rs.next()) {
	    		
	    		data.add(new Antrag(rs.getInt("idantrag")));
	    		
	    	}
	    } catch(SQLException e) {
	    	System.out.println(e);
	    }
	    	return data;
	}
	
	/**Methode um alle bestehenden Anräge unabhängig der Gruppe abzufragen.
	 *Im ersten Schritt eine Liste erstellt, in welcher die Daten gespeichert werden.
	 *Anschließend wird die Datenbankverbindung hergestellt.
	 *Danach wird der Parameter "idantrag" abgeholt, in welchem alle weiteren Daten gespeichert sind.
	 *Diese Daten sind in ObservableList data gespeichert.
	 * @author Robin
	 * @return data
	 * @throws SQLException 
	 */
	public static ObservableList<Antrag> getAlleAntraege() throws SQLException {
	    
		ObservableList<Antrag> data = FXCollections.observableArrayList();
	    
	    try {
	    	Main.get_DBConnection().Execute("SELECT * FROM antrag;");
	    	ResultSet rs = Main.get_DBConnection().get_last_resultset();	
	    	
	        while(rs.next())
	        {
	            data.add(new Antrag(rs.getInt("idantrag")));
	        }
	    }
	    catch(SQLException e)
	    {	System.out.println(e);	}
	    
	    return data;
	}
	
	
	
	/**Methode, um einen Antrag aus der DB auszugeben. Der Eingabewert "status" stellt den Antragsersteller des auszugebenden Antrags dar.
	 * Im ersten Schritt wird die Datenbankverbindung hergestellt.
	 * Danach werden die Parameter fuer das SQL-Statement mit Get-Methoden uebergeben und das gesamte SQL-Statement in einem String "ps" gespeichert.
	 *  
	 * @throws SQLException
	 * 
	 * Ich vermute, dass die Methode nicht mehr benötigt wird, da die Methode getAntraegeByStatus diese ersetzt #Robin
	 */
//	public ResultSet getAntragByStatus(String status) throws SQLException
//	{
//		MysqlCon db = new MysqlCon();
//		db.getDbCon();
//		ResultSet rs = db.query("select * from antrag WHERE status='"+ status+"'");
//		while(rs.next())
//		{
//			this.antragid = rs.getInt("idantrag");
//			this.name = rs.getString("titel");
//			this.fertigstellungsdatum = rs.getDate("fertigstellungsdatum");
//			this.ersteller = (Benutzer) rs.getObject("ersteller_fk");
//			this.bearbeiter = (Benutzer) rs.getObject("bearbeiter_fk");
//		}
//		return rs;
//	}
	
	/**Methode um alle bestehenden Anräge abhängig vom Status abzufragen.
	 *Der Eingabewert "status" stellt den Status  der auszugebenden Anträge dar.
	 *erstellt = neues Ticket angelegt -> verschieben in Tickets prüfen,
	 *geprüft = Ticket geprüft -> verschieben in Tickets genehmigen,
	 *genehmigt = Ticket genehmigt -> verschieben in Gruppentickets,
	 *erledigt = Ticket abschließend bearbeitet -> verschieben in abgeschlossene Tickets, 
	 *abgelehnt = Ticket wurde vom genehmiger/prüfer abgelehnt -> anzeige in eigene Tickets, aber nicht in abgesch. Tickets
	 *
	 *Im ersten Schritt eine Liste erstellt, in welcher die Daten gespeichert werden.
	 *Anschließend wird die Datenbankverbindung hergestellt.
	 *Danach wird der Parameter "idantrag" abgeholt, in welchem alle weiteren Daten gespeichert sind.
	 *Diese Daten sind in ObservableList data gespeichert. 
	 *
	 *
	 * @author Robin
	 * @param status, benutzername
	 * @return data
	 * @throws SQLException 
	 */
	public static ObservableList<Antrag> getAntraegebyStatus(String status, String benutzername) throws SQLException
	{
        ObservableList<Antrag> data = FXCollections.observableArrayList();
        try
        {
            Main.get_DBConnection().Execute(String.format("SELECT * FROM antrag " + 
                		 						"INNER JOIN benutzer ON antrag.ag_ersteller_fk = benutzer.ag_fk " + 
                		 						"WHERE benutzername = '%s' AND status = '%s';", benutzername, status));
                 
            ResultSet rs = Main.get_DBConnection().get_last_resultset();
                 while(rs.next())
                 {
                     data.add(new Antrag(rs.getInt("idantrag")));
                 }                 
        }
        catch(SQLException e)
        {	System.out.println(e);	}
        
        return data;
    }
	
	/**
	 * Methode, um die Anträge mit Status 'erstellt' abzufragen.
	 * Die Ergebnisse erscheinen in der Tabelle 'Tickets prüfen'.
	 * 
	 * Im ersten Schritt wird eine lokale ObservableList erzeugt.
	 * Im Anschluss wird die DBConnection abgefragt u. ggfs. neu verbunden.
	 * Dann wird das SQL Statement abgefragt und das Ergebnis in einem ResultSet gespeichert.
	 * Dieses wird durch die Schleife in die ObservableList eingeftragen.
	 * Zum Schluss wird die Liste zurückgegeben.
	 * 
	 * @author Robin
	 * @param status, benutzername
	 * @return data
	 * @throws SQLException
	 */
	public static ObservableList<Antrag> getAntraegezuPruefen (String benutzername) throws SQLException
	{
        ObservableList<Antrag> data = FXCollections.observableArrayList();
        
        try
        {
		Main.get_DBConnection().Execute(String.format("SELECT * FROM antrag " + 
					"INNER JOIN benutzer ON antrag.ag_ersteller_fk = benutzer.ag_fk " + 
					"WHERE benutzername = '%s' AND status = 'erstellt' and ersteller_fk <> '%s';", benutzername, benutzername));

		 ResultSet rs = Main.get_DBConnection().get_last_resultset();
		 	while(rs.next())
		 	{
		 		data.add(new Antrag(rs.getInt("idantrag")));
		 	}                 
		}
		catch(SQLException e)
		{	System.out.println(e);	}

		return data;
	}
	
	    
	/**Methode, um einen Antrag aus der DB auszugeben. Der Eingabewert "arbeitsgruppe" stellt den Antragsersteller des auszugebenden Antrags dar.
	 * Im ersten Schritt wird die Datenbankverbindung hergestellt.
	 * Danach werden die Parameter fuer das SQL-Statement mit Get-Methoden uebergeben und das gesamte SQL-Statement in einem String "ps" gespeichert.
	 *  
	 * @throws SQLException
	 * Methode wird vermutlich nicht mehr benötigt, da die Methode getAntraegebyStatus dies inne hat #Robin
	 */
//	public static ResultSet getAntragByBaergruppe(String arbeitsgruppe) throws SQLException
//	{
//		MysqlCon db = new MysqlCon();
//		db.getDbCon();
//		ResultSet rs = db.query("select * from antrag WHERE ersteller='"+ arbeitsgruppe +"'");
//		return rs;
//	}
//	
	
	
	/**Methode, um einen Antrag aus der DB auszugeben. Der Eingabewert "datum" stellt den Antragsersteller des auszugebenden Antrags dar.
	 * Im ersten Schritt wird die Datenbankverbindung hergestellt.
	 * Danach werden die Parameter fuer das SQL-Statement mit Get-Methoden uebergeben und das gesamte SQL-Statement in einem String "ps" gespeichert.
	 *  
	 * @throws SQLException
	 * Vermutlich sinnfreie Methode, da keine Umsetzung in GUI #Robin
	 */
//	public static Antrag getAntragbyDatum (Date fertigstellungsdatum) throws SQLException
//	{
//		Antrag a1 = null;
//		MysqlCon db = new MysqlCon();
//		db.getDbCon();
//		ResultSet rs = db.query("select * from antrag WHERE fertigstellungsdatum='"+ fertigstellungsdatum +"'");
//		
//		while(rs.next())
//		{
//			a1 = new Antrag();
//			a1.antragid = rs.getInt("idantrag");
//			a1.name = rs.getString("titel");
//			a1.fertigstellungsdatum = rs.getDate("fertigstellungsdatum");
//			a1.status = rs.getString("status");
//			a1.bearbeiter = (Benutzer) rs.getObject("bearbeiter_fk");
//		}
//		return a1;
//	}
	
	public static int countAntraege() throws SQLException {
		Main.get_DBConnection().Execute(String.format("SELECT COUNT(idantrag) FROM antrag"));
		Main.get_DBConnection().get_last_resultset().next();
		int rs = Main.get_DBConnection().get_last_resultset().getInt(0);
		return rs;
	}
	
	/** ***************************************************************************************************************************************************
	 * ******************************************************Implementierung der Getter und Setter*********************************************************
	 ******************************************************************************************************************************************************/
	
	public int getAntragid()
	{	return antragid;	}

	public void setAntragid(int antragid)
	{	this.antragid = antragid;	}

	public String getName()
	{	return name;	}

	public void setName(String name)
	{	this.name = name;	}
	
	public String getBeschreibung()
	{	return beschreibung;	}
	
	public void setBeschreibung(String beschreibung)
	{	this.beschreibung = beschreibung;	}
	
	public Date getAntragsdatum()
	{	return antragsdatum;	}
	
	public void setAntragsdatum(Date aDatum)
	{	this.antragsdatum = aDatum;	}
	
	public Date getFertigstellungsdatum()
	{	return fertigstellungsdatum;	}
	
	public void setFertigstellungsdatum(Date fDatum)
	{	this.fertigstellungsdatum = fDatum;	}
	
	public String getStatus()
	{	return status;	}
	
	public void setStatus(String status)
	{	this.status = status;	}
	
	public String getAblehnungsgrund()
	{	return ablehnungsgrund;	}
	
	public void setAblehnungsgrund(String abGrund)
	{	this.ablehnungsgrund = abGrund;	}

    public String getAnmerkung()
    {	return anmerkung;	}
    
    public void setAnmerkung(String anmerkung)
    {	this.anmerkung = anmerkung;	}
    
    public Benutzer getErsteller()
    {	return ersteller;	}
    
    public void setErsteller(Benutzer ersteller)
    {	this.ersteller = ersteller;	}

    public Gruppe getErstGruppe()
    {	return erstGruppe;	}
    
    public void setErstellerGruppe(Gruppe eGruppe)
    {   this.erstGruppe = eGruppe;	}
    
    public Benutzer getBearbeiter()
    {	return bearbeiter;	}
    
    public void setBearbeiter(Benutzer bearbeiter)
    {   this.bearbeiter = bearbeiter;	}

	public Gruppe getBearbeiterGruppe()
	{   return bearGruppe;	}
    
    public void setBearbeiterGruppe(Gruppe bGruppe)
    {   this.bearGruppe = bGruppe;	}

	public void getAuftragByNr(int pNR)
	{
		
	}
}