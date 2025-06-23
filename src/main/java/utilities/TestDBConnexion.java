package utilities;

public class TestDBConnexion {

	public static void main(String[] args) throws DataBaseReaderException {
		// TODO Auto-generated method stub
		String url = "jdbc:mysql://localhost:3306/db_users";
		String user = "root";
		String pssw = "Hellow";
		DataBaseReader dbr = new DataBaseReader(url, user, pssw);
		
		

	}

}
