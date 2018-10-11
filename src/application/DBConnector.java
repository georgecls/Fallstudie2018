package application;

//-----------------------------------------------------
//		----------- Alle Imports -----------
//(SQL-)Import wird fuer alle SQL-Operationen benoetigt. 
//-----------------------------------------------------
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

/// --------------------------------------------------------
/// Klasse:		DBConnector
/// Parent: 	-
/// Funktion:	Herstellen einer Datenbank-Verbindung, 
///				Ausfuehrung von SQL-Statement und Abfragen 
///				von Werten aus der Datenbank.
/// --------------------------------------------------------
public class DBConnector
{
	//* --------------------------------------------------
	// Temporaer wird der Standard-Username, das Passwort
	// und der Port in Klartext hart im Code hinterlegt.
	// -------------------------------------------------*/
	static String DRIVER_CLASS_INFO = "com.mysql.jdbc.Driver";
	static String DEFAULT_USERNAME = "root";
	static String DEFAULT_PWD = "root";
	static Integer DEFAULT_PORT = 3306;
	static String DEFAULT_DB_VENDOR = "mysql";
	static String DEFAULT_CONNECTION_SETTING = "useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC&autoReconnect=true&useSSL=false";
	//* ---------------------------
	// "Normale" Member-Variablen.
	// --------------------------*/
	private String database_name;
	private String username;
	private String password;
	private Integer port;
	private Connection connection;
	private String last_error;
	private ResultSet last_resultset;
	private Vector<String> last_vector;

	
	//* ------------------------------------
	// Konstruktor I
	// Nur Datenbank-Name, fuer den Username 
	// und das Passwort werden 
	// die Standard-Werte gesetzt. 
	// -----------------------------------*/
	public DBConnector(String in_database_name)
	{
		init();
		database_name = in_database_name;
		username = DEFAULT_USERNAME;
		password = DEFAULT_PWD;
		port = DEFAULT_PORT;
	}
	
	//* ------------------------------------------------
	// Konstruktor II
	// Datenbank-Name und Username werden gesetzt.  
	// Fuer das Passwort wird der Standard-Wert gesetzt. 
	// -----------------------------------------------*/
	public DBConnector(String in_database_name, String in_username)
	{
		init();
		database_name = in_database_name;
		username = in_username;
		password = DEFAULT_PWD;
		port = DEFAULT_PORT;
	}
	
	//* ------------------
	// Konstruktor III 
	// ------------------*/
	public DBConnector(String in_database_name, String in_username, String in_password)
	{
		init();
		database_name = in_database_name;
		username = in_username;
		password = in_password;
		port = DEFAULT_PORT;
	}
	
	//* ------------------
	// Konstruktor IV 
	// -----------------*/
	public DBConnector(String in_database_name, String in_username, String in_password, Integer in_port)
	{
		init();
		database_name = in_database_name;
		username = in_username;
		password = in_password;
		port = in_port;
	}
	
	//* -------------------------------------
	// Initialisierung der Member-Variablen. 
	// ------------------------------------*/
	private void init()
	{
		database_name = username = password = "";
		connection = null;
		port = 0;
		last_resultset = null;
		last_vector = new Vector<String>();
	}
	
	//* -----------------------------------
	// Verbindung zur Datenbank herstellen. 
	// ----------------------------------*/
	public Boolean ConnectToDatabase() throws InstantiationException, IllegalAccessException
	{
		/* -------------
		// Validierung.
		// -----------*/
		if(database_name.isEmpty()) return false;
		if(username.isEmpty()) return false;
		if(port <= 0) return false;
		
		try
		{
			//* -------------------------------------
			// Herstellen der Datenbank-Verbindung. 
			// ------------------------------------*/
			Class.forName(DRIVER_CLASS_INFO).newInstance();
			String connection_info = String.format("jdbc:%s://localhost:%d/%s?%s", DEFAULT_DB_VENDOR, port, database_name, DEFAULT_CONNECTION_SETTING);
			connection = DriverManager.getConnection("jdbc:mysql://sql7.freemysqlhosting.net:3306/sql7258916", username, password);
			//* ---------------------------
			// Validierung der Verbindung. 
			// --------------------------*/
			if(false == is_connected())
			{
				last_error = "Verbindung konnte nicht hergestellt werden";
				return false;
			}
			
			return true;
		}
		catch(SQLException e)
		{
			last_error = e.getMessage();
			return false;
		}
		catch(ClassNotFoundException e)
		{
			last_error = e.getMessage();
			return false;
		}
	}
	
	
	/* ------------------------------------
	// Pruefung ob eine Verbindung besteht.  
	// ----------------------------------*/
	public Boolean is_connected()
	{
		try
		{
			if(null == connection) return false;
			if(connection.isClosed()) return false;
		}
		catch (SQLException e)
		{
			last_error = e.getMessage();
		}
		
		return true;
	}
	
	/* -----------------------------------------
	// Verbindung trennen und Klasse aufraeumen
	// ---------------------------------------*/
	public void close()
	{
		if(connection == null) return;
		
		try
		{
			if(is_connected() || false == connection.isClosed())
			{
				connection.close();
				connection = null;
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	/* -------------------------------------
	// Rueckgabe der letzten, aufgetretenen, 
	// Fehlermeldung, wenn vorhanden. 
	// -----------------------------------*/
	public String get_last_error()
	{
		return last_error;
	}
	
	/* ---------------------------------
	// Rueckgabe des letzten ResultSets. 
	// -------------------------------*/
	public ResultSet get_last_resultset()
	{
		return last_resultset;
	}
	
	/* -------------------------------------------
	// Ausfuehrung des uebergebenen SQL-Statements.
	// Wichtig: Ohne Transaktion!
	// Also nur "normale" SELECTS. Ansonsten 
	// die UPDATE-Funktion aufrufen. 
	// -----------------------------------------*/
	public void Execute(String sql_statement)
	{
		// -------------
		// Validierung.
		// -------------
		if(false == is_connected())
		{
			last_error = "Keine Datenbank-Verbindung";
			return;
		}
		
		try
		{
			Statement statement = connection.createStatement();
			
			last_resultset = statement.executeQuery(sql_statement);
		}
		catch (SQLException e)
		{
			last_error = e.getMessage();
		}
	}
	
	
	/* -------------------------------------------
	// Ausfuehrung eines UPDATE-Statements
	// mit Transaktion. 
	// -----------------------------------------*/
	public Integer ExecuteTransact(String sql_statement) throws SQLException
	{
		// --------------------
		// Der Rueckgabe-Wert.
		// --------------------
		int ret = -1;
		// -------------
		// Validierung.
		// -------------
		if(false == is_connected())
		{
			last_error = "Keine Datenbank-Verbindung";
			return -1;
		}
		
		PreparedStatement prepStatement = null;
		
		try
		{
			connection.setAutoCommit(false);
			prepStatement = connection.prepareStatement(sql_statement);
			ret = prepStatement.executeUpdate();
		}
		catch (SQLException e)
		{
			last_error = e.getMessage();
			if (connection != null)
			{
	            try
	            {
	                System.err.print("Rollback der Transaktion wird ausgefuehrt. ");
	                connection.rollback();
	            }
	            catch(SQLException ex)
	            {
	                last_error = ex.getMessage();
	            }
	        }
	    }
		finally
		{
	        if (prepStatement != null)
	        {
	        	prepStatement.close();
	        }
	        connection.setAutoCommit(true);
	    }
        return ret;
	}
}
	
	