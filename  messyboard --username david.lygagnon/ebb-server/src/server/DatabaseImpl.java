package server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import common.Message;

public class DatabaseImpl {

	private Connection conn;
	
	private Statement stat;
	
	private boolean transaction_success;
	
	public DatabaseImpl() {
		this.transaction_success = true;
	}
	
	public void startConnection() {
		try {
			Class.forName("org.sqlite.JDBC");
			conn = DriverManager.getConnection("jdbc:sqlite:test.db");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void dropTable() {
		try {
			stat = conn.createStatement();
			stat.executeUpdate("drop table if exists postings;");
			stat.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void createTable() {
	      try {
	    	stat = conn.createStatement();
			stat.executeUpdate("create table postings (id integer primary key autoincrement, title, message, owner, postingDate TIMESTAMP);");
			stat.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public List<Message> viewMessages() {
		ResultSet rs;
		List<Message> message_list = new ArrayList<Message>();
		String title;
		String message;
		String owner;
		int id;
		Date date;
		Timestamp timestamp;
		
		try {
			stat = conn.createStatement();
			rs = stat.executeQuery("select * from postings;");
			
			while (rs.next()) {
				id = rs.getInt("id");
				title = rs.getString("title");
				message = rs.getString("message");
				owner = rs.getString("owner");
				timestamp = rs.getTimestamp("postingDate");
				
				long milliseconds = timestamp.getTime() + (timestamp.getNanos() / 1000000);
			    date = new Date(milliseconds);
				
			    Message message_object = new Message();
			    message_object.setId(id);
			    message_object.setTitle(title);
				message_object.setMessage(message);
				message_object.setOwner(owner);
				message_object.setDate(date);
				
				message_list.add(message_object);
				System.out.println("id= " + id + "title= " + rs.getString("title") + "message = " + rs.getString("message")
						+ " owner = " + rs.getString("owner") + "date = "
						+ rs.getString("postingDate"));
			}

			rs.close();
			stat.close();
		} catch (SQLException e) {
			transaction_success = false;
			e.printStackTrace();
		}

		return message_list;

	}
	
	public boolean postMessage(String title, String message, String owner) {
		ResultSet rs;
		
		/*
		ResultSet rs;
        try {
			stat.executeUpdate("insert into postings (message, owner, date) values ('" + message + "', '" + owner + "', '" + date + "');");
			 rs = stat.getGeneratedKeys();
		        System.out.println("RESULT SET: " + rs.getInt(1));
		        rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
       */
		
		Timestamp sqlDate = new java.sql.Timestamp(new java.util.Date().getTime());
		
		PreparedStatement prep;
		try {
			prep = conn
					.prepareStatement("insert into postings(title, message, owner, postingDate) values (?, ?, ?, ?);");
			
			prep.setString(1, title);
			prep.setString(2, message);
			prep.setString(3, owner);
			prep.setTimestamp(4, sqlDate);
			prep.addBatch();
	
			conn.setAutoCommit(false);
			prep.executeBatch();
			conn.setAutoCommit(true);
			rs = prep.getGeneratedKeys();
			System.out.println("RESULT SET: " + rs.getInt(1));
		    rs.close();
		} catch (SQLException e) {
			transaction_success = false;
			e.printStackTrace();
		}
		
		return transaction_success;
	}
	
public boolean updateMessage(int id, String title, String message) {
		Timestamp sqlDate = new java.sql.Timestamp(new java.util.Date().getTime());
		ResultSet rs;
		
		System.out.println("Given values: " + id + " " + title + " " + message);
		try {
			stat = conn.createStatement();
			stat.executeUpdate("update postings set message = '"+ message +"', title = '"+ title +"' where id = " + id +";");
		} catch (SQLException e) {
			transaction_success = false;
			e.printStackTrace();
		}
		
		
		/*	try {
			
			
			
			
			
			
			stat = conn.createStatement();
			stat.executeUpdate("update postings set message = '"+ message +"' where id = " + id +";");
			stat.close();
			stat = conn.createStatement();			
			rs = stat.executeQuery("select * from postings where id= " + id + ";");
			//rs.next();
			System.out.println("RESULT SET IS : " + rs.getString("message"));
			rs.close();
			//stat.executeUpdate("update postings set title = '"+ title +"', message ='"+ message + "', postingDate =" + sqlDate +" where id = " + id +";");
			 * 
			 * 
		} catch (SQLException e) {
			transaction_success = false;
			e.printStackTrace();
		}
		*/
		return transaction_success;
	}
	
	
	public void closeConnection() {
		try {
			conn.close();
		} catch (SQLException e) {
			System.err.println("[DBConnector.closeConnection error.");
			e.printStackTrace();
		}
	}
}
