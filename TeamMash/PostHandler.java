import java.sql.*;
import java.lang.*;
import java.util.*;
import java.io.*;

import org.json.JSONObject;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.Headers;

import java.net.InetSocketAddress;
import java.net.HttpURLConnection;

class Rating{
	
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost/";

	static final String username = "mithil";
	static final String password = "kv23200266";

	public void createDatabase(){
		Connection connection = null;
		Statement statement = null;
		try{
			Class.forName("com.mysql.jdbc.Driver");

			System.out.println("Connecting to database ");
			connection = DriverManager.getConnection(DB_URL,username,password);

			System.out.println("Creating database ");
			statement = connection.createStatement();

			String sqlcommand = "CREATE DATABASE IF NOT EXISTS RATING";
			statement.executeUpdate(sqlcommand);

			sqlcommand = "USE RATING";
			statement.executeUpdate(sqlcommand);
			sqlcommand = "CREATE TABLE IF NOT EXISTS RATINGTABLE(NAME VARCHAR(50) NOT NULL,RATING INT NOT NULL,PRIMARY KEY(NAME))";
			statement.executeUpdate(sqlcommand); 			
		}
		catch(SQLException se){
			se.printStackTrace();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			try{
				if(statement != null)
					statement.close();
			}
			catch(SQLException se2){

			}
			try{
				if(connection != null)
					connection.close();
			}
			catch(SQLException se){
				se.printStackTrace();
			}
		}
	}

	public void newPlayer(String name){
		Connection connection = null;
		Statement statement = null;
		try{
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(DB_URL,username,password);
			statement = connection.createStatement();

			String sqlcommand = "USE RATING";
			statement.executeUpdate(sqlcommand);
			sqlcommand = "SELECT NAME FROM RATINGTABLE WHERE NAME = '" + name + "'";
			ResultSet resultSet = statement.executeQuery(sqlcommand);
			int count = 0;
			while(resultSet.next()){
				count++;
			}
			if(count == 0){
				sqlcommand = "INSERT INTO RATINGTABLE(NAME,RATING) VALUES('" + name + "',1000)";   			
				statement.executeUpdate(sqlcommand);
			}
		}
		catch(SQLException se){
			se.printStackTrace();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			try{
				if(statement != null)
					statement.close();
			}
			catch(SQLException se2){

			}
			try{
				if(connection != null)
					connection.close();
			}
			catch(SQLException se){
				se.printStackTrace();
			}
		}	
	}

	public int getRatings(String player){
		Connection connection = null;
		Statement statement = null;
		int rating = -1;
		try{
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(DB_URL,username,password);
			statement = connection.createStatement();

			String sqlcommand = "USE TEAMRATING";
			statement.executeUpdate(sqlcommand);
			
			sqlcommand = "SELECT * FROM mytable WHERE Name = '" + player + "'";
			ResultSet resultSet = statement.executeQuery(sqlcommand);

			while(resultSet.next()){
				rating = resultSet.getInt("RATING");
			}
			return rating;
		}
		catch(SQLException se){
			se.printStackTrace();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			try{
				if(statement != null)
					statement.close();
			}
			catch(SQLException se2){

			}
			try{
				if(connection != null)
					connection.close();
			}
			catch(SQLException se){
				se.printStackTrace();
			}
			return rating;
		}
	}

	public void updateIndividualRatings(String player,int rating){
		Connection connection = null;
		Statement statement = null;
		try{
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(DB_URL,username,password);
			statement = connection.createStatement();

			String sqlcommand = "USE TEAMRATING";
			statement.executeUpdate(sqlcommand);
			
			sqlcommand = "UPDATE mytable SET RATING = " + rating + " WHERE Name = '" + player + "'";
			statement.executeUpdate(sqlcommand);
		}
		catch(SQLException se){
			se.printStackTrace();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			try{
				if(statement != null)
					statement.close();
			}
			catch(SQLException se2){

			}
			try{
				if(connection != null)
					connection.close();
			}
			catch(SQLException se){
				se.printStackTrace();
			}
		}	
	}

	public void updateRatings(String playerA,String playerB,int result){
		//result for playerA
		// result = 1 win
		// result = 0 draw
		// result = -1 loss

		int ratingA = this.getRatings(playerA);
		int ratingB = this.getRatings(playerB);

		double tempA,tempB;
		tempA = Math.pow(10,(ratingA-ratingB)/400);
		tempB = Math.pow(10,(ratingB-ratingA)/400);

		double eA = 1.0/(1+tempA);
		double eB = 1.0/(1+tempB);

		double sA,sB;
		sA = sB = 0; 
		if(result == 1){
			sA = 1;
			sB = 0;
		}
		else if(result == -1){
			sA = 0;
			sB = 1;
		}
		else if(result == 0){
			sA = 0.5;
			sB = 0.5;
		}

		double newratingA = ratingA + 32*(sA - eA);
		double newratingB = ratingB + 32*(sB - eB);

		this.updateIndividualRatings(playerA,(int)newratingA);
		this.updateIndividualRatings(playerB,(int)newratingB);

		return ;
	}
}

class Transactions{
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost/";

	static final String username = "mithil";
	static final String password = "kv23200266";

	public void insertNewTransaction(String ip,JSONObject jsonObj){
		Connection connection = null;
		Statement statement = null;

		String winnerTeam = (String)jsonObj.get("WinnerTeam");
		String loserTeam = (String)jsonObj.get("LoserTeam");

		try{
			Class.forName("com.mysql.jdbc.Driver");
			connection = DriverManager.getConnection(DB_URL,username,password);
			statement = connection.createStatement();

			String sqlcommand = "USE TRANSACTIONS";
			statement.executeUpdate(sqlcommand);

			// INSERT INTO TransactionTable(ID,IP,WINNER,LOSER) VALUES(default,'10.22.13.455','TEAM1','TEAM2');

			sqlcommand = "INSERT INTO TransactionTable(ID,IP,WINNER,LOSER) VALUES(null,'" + ip + "','" + winnerTeam + "','" + loserTeam + "')";   			
			System.out.println(sqlcommand);
			statement.executeUpdate(sqlcommand);
		}
		catch(SQLException se){
			se.printStackTrace();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			try{
				if(statement != null)
					statement.close();
			}
			catch(SQLException se2){

			}
			try{
				if(connection != null)
					connection.close();
			}
			catch(SQLException se){
				se.printStackTrace();
			}
		}
	}
}

public class PostHandler{
	public static void main(String[] args) throws Exception{
		int port = 5050;
		HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
		server.createContext("/postrequest", new MyHandler());
		server.setExecutor(null);
		System.out.println("Server starting at port : " + port);
		server.start();
	}

	static class MyHandler implements HttpHandler{
		public void handle(HttpExchange t) throws IOException {
			
			System.out.println("POST Request");
			String ipAddress =  t.getRemoteAddress().toString();
			System.out.println("IP Address: " + ipAddress);


			Map <String, Object> parameters = new HashMap<String, Object>();
			InputStreamReader isr = new InputStreamReader(t.getRequestBody(), "utf-8");
			BufferedReader br = new BufferedReader(isr);
			
			int b;
			StringBuilder buf = new StringBuilder(512);
			while ((b = br.read()) != -1) {
				buf.append((char) b);
			}
			br.close();
			isr.close();

			String requestBodyString = buf.toString();
			System.out.println("Received Request Body : " + requestBodyString);

			JSONObject jsonObj = new JSONObject(requestBodyString);

			String response = "200 OK";
			Headers responseHeaders = t.getResponseHeaders();
			responseHeaders.set("Content-Type", "text/html");
			responseHeaders.set("Connection","Keep-Alive");
	        responseHeaders.set("Access-Control-Allow-Origin", "*");


			t.sendResponseHeaders(HttpURLConnection.HTTP_OK,response.length());

			OutputStream os = t.getResponseBody();
			os.write(response.getBytes());
			os.close();

			this.helperUpdate(jsonObj);
			this.transactionUpdate(ipAddress,jsonObj);
		}

		public void helperUpdate(JSONObject jsonObj){
			String winnerTeam = (String)jsonObj.get("WinnerTeam");
			String loserTeam = (String)jsonObj.get("LoserTeam");

			Rating rating = new Rating();
			rating.updateRatings(winnerTeam,loserTeam,1);
		}

		public void transactionUpdate(String ip,JSONObject jsonObj){
			Transactions t = new Transactions();
			t.insertNewTransaction(ip,jsonObj);
		}
	}
}
