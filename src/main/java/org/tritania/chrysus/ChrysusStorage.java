/*
 * Copyright 2012-2013 Erik Wilson <erikwilson@magnorum.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.tritania.chrysus;

/*Start Imports*/
import java.io.File;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import org.tritania.chrysus.util.Log;
import org.tritania.chrysus.Chrysus;
/*End Imports*/

public class ChrysusStorage 
{
	public Chrysus chrysus;

    public ChrysusStorage(Chrysus chrysus)
    {
        this.chrysus = chrysus;
    }
	
	private  Connection conn = null;
    private  ResultSet results = null;

    private final  String WALLET = "CREATE TABLE `WALLET` ("
	+ "`id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,"
	+ "`player` VARCHAR(36) NOT NULL,"
	+ "`active` BOOLEAN NOT NULL,"
	+ "`value` INTEGER(11) NOT NULL,"
    + "`OrderID1` INTEGER(11) DEFAULT 0,"
    + "`OrderID2` INTEGER(11) DEFAULT 0,"
    + "`OrderID3` INTEGER(11) DEFAULT 0,"
    + "`OrderID4` INTEGER(11) DEFAULT 0,"
    + "`OrderID5` INTEGER(11) DEFAULT 0"
	+ ");"; 
    
    private final  String ORDERS = "CREATE TABLE `ORDERS` ("
	+ "`id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,"
	+ "`item` VARCHAR(16) NOT NULL,"
	+ "`price` INTEGER(11) NOT NULL,"
	+ "`type` INTEGER(11) NOT NULL," //0 for sell 1 for buy
	+ "`status` INTEGER(11) NOT NULL," //0 for still on market 1 for sold/bought
	+ "`OrderID` INTEGER(11) NOT NULL"
	+ ");"; 


	private final  String PRICES = "CREATE TABLE `PRICES` ("
	+ "`id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,"
	+ "`item` VARCHAR(30) NOT NULL UNIQUE KEY,"
	+ "`alias` VARCHAR(30) NOT NULL,"
	+ "`price` INTEGER(11) NOT NULL"
	+ ");"; 
	
	public  Connection initialize()
	{
		try
		{
				Class.forName("com.mysql.jdbc.Driver");
				conn = DriverManager.getConnection(chrysus.config.SQLurl, chrysus.config.SQLuser, chrysus.config.SQLpass);
				conn.setAutoCommit(false);
		}

			
		catch (SQLException ex) 
		{ 
			Log.severe("SQL exception on initialize :");
        	Log.severe("  " + ex.getMessage());
		}
        catch (ClassNotFoundException ex) 
        { 
			Log.severe("You need the MySQL library. :");
        	Log.severe("  " + ex.getMessage());
		}
        
        if (!TableExists()) 
        {
            CreateTable();
        }
        
        return conn;
	}
    
    private  boolean TableExists()
    {
        ResultSet rs = null;
        try 
        {
            Connection conn = getConnection();

            DatabaseMetaData dbm = conn.getMetaData();
            rs = dbm.getTables(null, null, "WALLET", null);
            if (!rs.next())
                return false;
            return true;
        } 
        catch (SQLException ex) 
        {
            Log.severe(" Table Check Exception :");
        	Log.severe("  " + ex.getMessage());
            return false;
        } 
        finally 
        {
            try 
            {
                if (rs != null)
                    rs.close();
            } catch (SQLException ex) 
            { 
					Log.severe(" Table Check SQL Exception (on closing) :");
					Log.severe("  " + ex.getMessage());
			}
        }
    }
      
    public  void CreateTable()
    {
		Statement st = null;
    	try 
    	{
			Log.info(" Populating Database...");
    		Connection conn = getConnection();
    		st = conn.createStatement();
    		st.executeUpdate(WALLET);
    		st.executeUpdate(ORDERS);
    		st.executeUpdate(PRICES);
    		conn.commit();
    		
		}
		catch (SQLException ex) 
    	{ 
    		Log.severe(" Create Table Exception :");
    		Log.severe("  " + ex.getMessage());
    	}
    	finally 
    	{
    		try {
    			if (st != null) 
    			{
    				st.close();
    			}
    		} 
    		catch (SQLException ex) 
    		{
    			Log.severe(" Could not create the table (on close) :");
    			Log.severe("  " + ex.getMessage());
    		}
    	} 
	 }
    
    public  void closeConnection()
    {
		if(conn != null) 
		{
			try {
				
					if(conn.isValid(10)) 
					{
						conn.close();
					}
					conn = null;
				} 
			catch (SQLException ex) 
			{ 
					Log.severe("Error on Connection close :");
					Log.severe("  " + ex.getMessage());
			}
		}
    }
    
    public  Connection getConnection()
	{
		if(conn == null) conn = initialize();
		{
			try 
			{
				if(!conn.isValid(10)) conn = initialize();
			} 
			catch (SQLException ex) 
			{
				Log.severe("Failed to check SQL status :");
				Log.severe("  " + ex.getMessage());
		    }
		}
		return conn;
	}
    
    public  void Store(String query)
    {
        Statement st = null;
    	try 
    	{
    		Connection conn = getConnection();
    		st = conn.createStatement();
    		st.executeQuery(query);
    		conn.commit();
    		
		}
		catch (SQLException ex) 
    	{ 
    		Log.severe(" SQL Exception 1:");
    		Log.severe("  " + ex.getMessage());
    	}
    	finally 
    	{
    		try {
    			if (st != null) 
    			{
    				st.close();
    			}
    		} 
    		catch (SQLException ex) 
    		{
    			Log.severe(" SQL Exception 2:");
    			Log.severe("  " + ex.getMessage());
    		}
    	} 
    }
    
    public  void StoreTwo(String query)
    {
        Statement st = null;
    	try 
    	{
    		Connection conn = getConnection();
    		st = conn.createStatement();
    		st.executeUpdate(query);
    		conn.commit();
    		
		}
		catch (SQLException ex) 
    	{ 
    		Log.severe(" SQL Exception 1:");
    		Log.severe("  " + ex.getMessage());
    	}
    	finally 
    	{
    		try {
    			if (st != null) 
    			{
    				st.close();
    			}
    		} 
    		catch (SQLException ex) 
    		{
    			Log.severe(" SQL Exception 2:");
    			Log.severe("  " + ex.getMessage());
    		}
    	} 
    }
    
    public  ArrayList<String> getData(String query)
    {   
        ArrayList<String> resultsout = new ArrayList<String>();
        Statement st = null;
    	try 
    	{
    		Connection conn = getConnection();
            st = conn.createStatement();
            results = st.executeQuery(query);
            resultsout = convertResults(results);
    		
		}
		catch (SQLException ex) 
    	{ 
    		Log.severe(" SQL Exception 3:");
    		Log.severe("  " + ex.getMessage());
    	}
    	finally 
    	{
    		try {
    			if (st != null) 
    			{
    				st.close();
    			}
    		} 
    		catch (SQLException ex) 
    		{
    			Log.severe(" SQL Exception 4:");
    			Log.severe("  " + ex.getMessage());
    		}
    	}
        return resultsout; 
    }
    
  
  
    public  ArrayList<String> convertResults(ResultSet resin)
    {
        ArrayList<String> resultsout = new ArrayList<String>();
        int i=1;
        try 
        {
            while(resin.next())
            {
                try
                {
                    resultsout.add(resin.getString(1));
                    i++;
                }
                catch(SQLException ex)
                {
                    Log.severe(" SQL Exception 5:");
                    Log.severe("  " + ex.getMessage());
                }
            }
            resultsout.add("END_DATA_STREAM");
        }
        catch(SQLException ex)
        {
            Log.severe(" SQL Exception 6:");
            Log.severe("  " + ex.getMessage());
        }
        return resultsout;
    }
    
}


