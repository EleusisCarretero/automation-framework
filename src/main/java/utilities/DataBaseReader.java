package utilities;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;



public class DataBaseReader {
	private static final Logger LOGGER = Logger.getLogger(DataBaseReader.class.getName());
	String dbUrl;
	String user;
	String password;
	private Connection cn;
	
	DataBaseReader(String dbUrl, String user, String password) throws DataBaseReaderException{
		
		this.dbUrl = dbUrl;
		this.user = user;
		this.password = password;
		this.cn = makeConnection();
		if (this.cn == null) {
			LOGGER.severe("unable to create a connection object");
			throw new DataBaseReaderException("No cn connection");
		}
	}
	
	@SuppressWarnings("finally")
	private Connection makeConnection() {
		Connection cn = null;
		try {
			cn = DriverManager.getConnection(dbUrl, user, password);
			LOGGER.info("Corrention csuccessfully done");
		}
		catch (SQLException e) {
			LOGGER.severe("Unable to make connection to data base");
			LOGGER.throwing(DataBaseReader.class.getName(), "main", e);
		}
		finally {
			return cn;
		}
	}
	public ResultSet exceuteQuery(String query) throws SQLException, DataBaseReaderException {
		Statement stmt = null;
		ResultSet rs = null;
		try {
			stmt = this.cn.createStatement();
			rs = stmt.executeQuery(query);
			return rs;
		}
		catch(SQLException e){
			LOGGER.severe("Unable to make query result");
			LOGGER.throwing(DataBaseReader.class.getName(), "main", e);
		}
		finally {
			rs.close();
            stmt.close();
		}
		throw new DataBaseReaderException("No result manager");
	}

}
