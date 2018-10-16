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
	private String kommentar;
	private Benutzer ersteller;
	private Benutzer bearbeiter;
	private Gruppe erstGruppe;
	private Gruppe bearGruppe;
	
	
	//Attribute, die dar√ºberhinaus noch ben√∂tigt werden
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
	 * Danach werden die Parameter f√ºr das SQL-Statement mit Get-Methoden √ºbergeben und das gesamte SQL-Statement in einem String "ps" gespeichert.
	 * Im letzten Schritt wird der String an die Instanzmethode "insert" aus der Klasse "MysqlCon" √ºbergeben und damit ein neuer Datensatz in der DB erzeugt.
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
	
	
	/**Methode zieht sich Antrag mit im Parameter angegebener ID.
	 *Zuerst wird die Datenbankverbindung hergestellt.
	 *Im ResultSet wird der ausgef¸hrte SELECT gespeichert. 
	 *in der IF-Abfrage werden die Daten in die jeweiligen Variablen gespeichert und zur¸ckgegeben. 
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
			this.kommentar = rs.getString("anmerkung");
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
	
	/**Methode um alle bestehenden Anr‰ge unabh‰ngig der Gruppe abzufragen.
	 *Im ersten Schritt eine Liste erstellt, in welcher die Daten gespeichert werden.
	 *Anschlieﬂend wird die Datenbankverbindung hergestellt.
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
	
	/**Methode um alle bestehenden Anr‰ge abh‰ngig vom Status abzufragen.
	 *Der Eingabewert "status" stellt den Status  der auszugebenden Antr‰ge dar.
	 *erstellt = neues Ticket angelegt -> verschieben in Tickets pr¸fen,
	 *gepr¸ft = Ticket gepr¸ft -> verschieben in Tickets genehmigen,
	 *genehmigt = Ticket genehmigt -> verschieben in Gruppentickets,
	 *erledigt = Ticket abschlieﬂend bearbeitet -> verschieben in abgeschlossene Tickets, 
	 *abgelehnt = Ticket wurde vom genehmiger/pr¸fer abgelehnt -> anzeige in eigene Tickets, aber nicht in abgesch. Tickets
	 *
	 *Im ersten Schritt eine Liste erstellt, in welcher die Daten gespeichert werden.
	 *Anschlieﬂend wird die Datenbankverbindung hergestellt.
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
	 * Methode, um die Antr‰ge mit Status 'erstellt' abzufragen.
	 * Die Ergebnisse erscheinen in der Tabelle 'Tickets pr¸fen'.
	 * 
	 * Im ersten Schritt wird eine lokale ObservableList erzeugt.
	 * Im Anschluss wird die DBConnection abgefragt u. ggfs. neu verbunden.
	 * Dann wird das SQL Statement abgefragt und das Ergebnis in einem ResultSet gespeichert.
	 * Dieses wird durch die Schleife in die ObservableList eingeftragen.
	 * Zum Schluss wird die Liste zur¸ckgegeben.
	 * 
	 * @author Robin
	 * @param status, benutzername
	 * @return data
	 * @throws SQLException
	 */
	public static ObservableList<Antrag> getAntraegezuPruefen (String benutzername, int benutzerid) throws SQLException
	{
        ObservableList<Antrag> data = FXCollections.observableArrayList();
        
        try
        {
		Main.get_DBConnection().Execute(String.format("SELECT * FROM antrag " + 
					"INNER JOIN benutzer ON antrag.ag_ersteller_fk = benutzer.ag_fk " + 
					"WHERE benutzername = '%s' AND status = 'erstellt' and ersteller_fk <> '%s';", benutzername, benutzerid));

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
	
	
	public static ObservableList<Antrag> getEigeneAntraege (String benutzername) throws SQLException
	{
        ObservableList<Antrag> data = FXCollections.observableArrayList();
        
        try
        {
		Main.get_DBConnection().Execute(String.format("SELECT * FROM antrag " + 
					"INNER JOIN benutzer ON antrag.ag_ersteller_fk = benutzer.ag_fk " + 
					"WHERE benutzername = '%s' AND status = 'erstellt';", benutzername));

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
	
	
	/****************************************************************************************
	 * *************************************Auswertung***************************************
	 ****************************************************************************************/
	
	/**
	 * Methode um die Anzahl der Antr‰ge je nach Status ausgeben zu lassen.
	 * Ergebnis wird in PieChart ausgegeben.
	 * 
	 * @author Robin
	 * @param status
	 * @return i
	 * @throws SQLException
	 */
	public static Double countAntraegeByStatus(String status) throws SQLException {
		double i = 0.00;
		
		Main.get_DBConnection().Execute(String.format("SELECT COUNT(idantrag) FROM antrag where status = '%s';", status));
		ResultSet rs = Main.get_DBConnection().get_last_resultset();
		while(rs.next()) {
			String is = rs.getString(1);
			i = Double.parseDouble(is);
		}
		return i;
	}
	
	/**
	 * Methode um die Anzahl der Antr‰ge je nach Status und Gruppe ausgeben zu lassen.
	 * Ergebnis wird in PieChart ausgegeben.
	 * 
	 * @author Robin
	 * @param benutzername, status
	 * @return i 
	 * @throws SQLException
	 */
	public static Double countAntraegeByGruppe(String benutzername, String status) throws SQLException{
		double i = 0.00;
		
		Main.get_DBConnection().Execute(String.format("SELECT COUNT(idantrag) FROM antrag " + 
				"INNER JOIN benutzer ON antrag.ag_ersteller_fk = benutzer.ag_fk " + 
				"WHERE benutzername = '%s' AND status = '%s';", benutzername, status));
		ResultSet rs = Main.get_DBConnection().get_last_resultset();
		while(rs.next()) {
			String is = rs.getString(1);
			i = Double.parseDouble(is);
		}
		return i;
	}
	
	
	/** ***************************************************************************************************************************************************
	 * *************************************************************Ver‰nderung der Status*****************************************************************
	 ******************************************************************************************************************************************************/
	
	
	/**Methode, um einen Antrag aus der DB zu l√∂schen. Der √úbergabewert "id" stellt die AntragsID des zu l√∂schenden Antrags dar.
	 * Im ersten Schritt wird die Datenbankverbindung hergestellt.
	 * Danach werden die Parameter f√ºr das SQL-Statement mit Get-Methoden √ºbergeben und das gesamte SQL-Statement in einem String "ps" gespeichert.
	 * Im letzten Schritt wird der String an die Instanzmethode "update" aus der Klasse "MysqlCon" √ºbergeben und damit der entsprechende Datensatz gel√∂scht.
	 *  
	 * @throws SQLException
	 */
	public static void deleteAntragById(int id) throws SQLException
	{
		Main.get_DBConnection().ExecuteTransact(String.format("UPDATE antrag SET status = 'geloescht' WHERE idantrag = '%s'", id));
	}
	
	
	public static void antragPruefen(int id) throws SQLException
	{
		
		Main.get_DBConnection().ExecuteTransact(String.format("UPDATE antrag SET status = 'geprueft' WHERE idantrag = '%s'", id));
	}
	
	public static void antragGenehmigen(int id) throws SQLException
	{
		
		Main.get_DBConnection().ExecuteTransact(String.format("UPDATE antrag SET status = 'genehmigt' WHERE idantrag = '%s'", id));
	}
	
	public static void antragBearbeiten(int id) throws SQLException
	{
		
		Main.get_DBConnection().ExecuteTransact(String.format("UPDATE antrag SET status = 'erledigt' WHERE idantrag = '%s'", id));
	}
	
	public static void antragAblehnen(int id) throws SQLException
	{
		
		Main.get_DBConnection().ExecuteTransact(String.format("UPDATE antrag SET status = 'abgelehnt' WHERE idantrag = '%s'", id));
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

    public String getKommentar()
    {	return kommentar;	}
    
    public void setKommentar(String kommentar)
    {	this.kommentar = kommentar;	}
    
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