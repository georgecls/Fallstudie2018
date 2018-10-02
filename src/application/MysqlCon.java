package application;

import java.sql.*;

public class MysqlCon {
    public Connection conn;
    private Statement statement;
    public static MysqlCon db;
    
    public MysqlCon() {
        String url= "jdbc:mysql://sql7.freemysqlhosting.net:3306/";
        String dbName = "sql7258916";
        String driver = "com.mysql.jdbc.Driver";
        String userName = "sql7258916";
        String password = "SfSFVG296y";
        try {
            Class.forName(driver).newInstance();
            this.conn = (Connection)DriverManager.getConnection(url+dbName,userName,password);
        }
        catch (Exception sqle) {
            sqle.printStackTrace();
        }
    }
    /**
     *
     * @return MysqlConnect Database connection object
     */
    public static synchronized MysqlCon getDbCon() {
        if ( db == null ) {
            db = new MysqlCon();
        }
        return db;
 
    }
	
	public ResultSet query(String query) throws SQLException{
        statement = db.conn.createStatement();
        ResultSet res = statement.executeQuery(query);
        return res;
    }
	
    /**
     * @desc Method to insert data to a table
     * @param insertQuery String The Insert query
     * @return boolean
     * @throws SQLException
     */
    public int insert(String insertQuery) throws SQLException {
        statement = db.conn.createStatement();
        int result = statement.executeUpdate(insertQuery);
        return result;
 
    }

    public int select(String selectQuery) throws SQLException {
        statement = db.conn.createStatement();
        int result = statement.executeUpdate(selectQuery);
        return result;
 
    }

    public int update(String updateQuery) throws SQLException {
        statement = db.conn.createStatement();
        int result = statement.executeUpdate(updateQuery);
        return result;
 
    }
    
    public int delete(String deleteQuery) throws SQLException {
        statement = db.conn.createStatement();
        int result = statement.executeUpdate(deleteQuery);
        return result;
 
    }
    
}
