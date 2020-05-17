package assignment2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

public class Database_SQL {
	
	

	
	static Connection conn=null;
	static Statement stmt = null;
	
	//The following function connects to SQL:
	public static void getconnection() 
	{
				
		try 
		{
			System.out.println("Connecting to the DataBase:  ");
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection("jdbc:mysql://localhost/pow", "root", "password");
			stmt = DriverManager.getConnection("jdbc:mysql://localhost/pow","root", "password").createStatement();
		}
		catch (Exception exception) 
		{
			exception.printStackTrace();
		}
		
	}
	
	//This method creates a SQL DB:
	public static void create_DB (String Name_database) 
	{
		
		try 
		{
			// Execute a query to create database
			System.out.println("Creating database "+ Name_database);
			stmt = DriverManager.getConnection("jdbc:mysql://localhost/pow","root", "password").createStatement();
			stmt.executeUpdate("create database if not exists " + Name_database);
			System.out.println("Database " + Name_database + " created successfully...");			
			System.out.println("Database used : " + Name_database);
			
		} 
		catch (SQLException exc) 
		{
			exc.printStackTrace();
		}
	}
	
	
	
	
	
}
	


