
import java.io.*;
import java.sql.*;
import java.util.ArrayList;



public class TestBenchmarks {

	public static void main(String[] args)throws ClassNotFoundException, SQLException {
		
		//Establishing connection with mysql via localhost  port number:3306 to the database schema demo and checking the timing zone 
		
		//PLEASE NOTE THAT: database name: Benhmarks  // User: root // Password : admin
		//Feel free to chose the database name of your choice
		//If you need to modify any of these parameters:
			String database_name="BenchMarks";
			String User="root";
			String Password="admin";
			 
		//this string serve to check time of the sql and java console application 
			String check_time="?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
			
			Connection connection=DriverManager.getConnection("jdbc:mysql://localhost:3306/"+check_time,User,Password);
			Statement myStmt=connection.createStatement();
			
			
			
		//Create the database if it doesn't exists
			myStmt.execute("CREATE DATABASE IF NOT EXISTS "+ database_name );
		//use the chosen database
			myStmt.execute("USE "+ database_name);
		
			String if_exists_Benchmarks="DROP TABLE IF EXISTS BENCHMARKS";
			myStmt.executeUpdate(if_exists_Benchmarks);
			
		String CREATE_TABLE_BENCHMARKS="CREATE TABLE BENCHMARKS("
				+ "IdServer INT NOT NULL,"
				+ "IdClient INT NOT NULL,"
				+ "ServerName varchar(50),"
				+ "ClientName varchar(50),"
				+ "GeoMean DOUBLE,"
				+"PRIMARY KEY (IdServer,IdClient))";
		
		myStmt.executeUpdate(CREATE_TABLE_BENCHMARKS);
		
		// TODO Auto-generated method stub
		String s1=System.getProperty("user.dir")+"\\src\\clients";
		File ClientsFolder=new File(s1);
		ArrayList<Client> arayC=new ArrayList<Client>();
		
		//fetching the client's txt files from the folder
		if(ClientsFolder.isDirectory()) {
			//getting the names of the clients txt files
			String t[]=ClientsFolder.list();
			for(int i=0;i<t.length;++i) {
				try {
					// Going through the clients informations and appending them to a list of clients
					String path2=s1+"\\"+t[i];
					
					FileReader fr=new FileReader(path2);
					BufferedReader br = new BufferedReader(fr);
					String line;
					
					String Name=t[i].substring(0,t[i].length()-4);
					//creating a new client and adding it to the list
					Client c1=new Client(i+1,Name);
					while((line=br.readLine()) != null) {
						//getting the result of the line in an array 
						//this array contains 3 itmes that represent:
						//Description , name ,ET
						String Bench[]=line.split(" ");
						double ET=Double.parseDouble(Bench[2]);
						c1.AddBenchmark(Bench[0],Bench[1] , ET);
					}
					br.close();
					arayC.add(c1);
					System.out.println(c1);
				}catch(Exception e) {
					e.printStackTrace();
				}
			}
		}
		//Creating the server module, it's the reference PC.
		Server s=new Server(1,"jean Yves");
		
		s.AddBenchmark("BubbleSortList","bbls", 37.7);
		s.AddBenchmark("ListAppending " , "LsAp", 12.34);
		s.AddBenchmark("WriteTest", "wTest",60.2);
		s.AddBenchmark("FindPrime", "Fpme",10.29);
		s.AddBenchmark("FindVoyelle","Fvol", 25.02);
		s.AddBenchmark("CaesarCypher", "CaCp", 16.58);
		s.AddBenchmark("ICMPrequest", "icmp", 53.44);
		
		for(int i=0;i<arayC.size();++i) {
			insert(connection,s,arayC.get(i));
		}
		
		
		//Importing the results from the database to an local CSV file
		String query="SELECT * FROM BENCHMARKS ORDER BY GeoMean DESC";
		sqlToCSV(connection,query,"results");

		connection.close();
	}

	
	// method to insert the clients benchmarks into a Database
	public static void insert(Connection con,Server s,Client c) throws ClassNotFoundException, SQLException  {
		double geoMean=s.getSPECratio(c);
		System.out.println(c.getName()+" : geoMeans: "+ geoMean);
		String query="INSERT INTO BENCHMARKS (IdServer,IdClient,ServerName,ClientName,GeoMean) Values (?,?,?,?,?)";
		PreparedStatement prepared=con.prepareStatement(query);
		
		prepared.setInt(1,s.getId());
		prepared.setInt(2, c.getId());
		prepared.setString(3, s.getName());
		prepared.setString(4, c.getName());
		prepared.setDouble(5, geoMean);
		
		prepared.execute();
	}
	
	//method for importing the data from the database and store it in a CSV file
	public static void sqlToCSV (Connection conn,String query, String filename){

	       System.out.println("creating csv file: " + filename);
	       String s1=System.getProperty("user.dir")+"\\src"+"\\"+filename + ".csv";

	       try {

	         FileWriter fw = new FileWriter(s1);
	         Statement st = conn.createStatement();
	         ResultSet rs = st.executeQuery(query);

	         int cols = rs.getMetaData().getColumnCount();

	         for(int i = 1; i <= cols; i ++){
	            fw.append(rs.getMetaData().getColumnLabel(i));
	            if(i < cols) fw.append(',');
	            else fw.append('\n');
	         }

	         while (rs.next()) {
	            for(int i = 1; i <= cols; i ++){
	                fw.append(rs.getString(i));
	                if(i < cols) fw.append(',');
	            }
	            fw.append('\n');
	        }
	        fw.flush();
	        fw.close();
	        System.out.println("CSV File is created successfully.");
	        System.out.println("You can find the result file from the path : "+s1);
	        conn.close();
	    } catch (Exception e) {
	    	 System.out.println(e);
	    }
	}
	
}
