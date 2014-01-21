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
	
	private static Connection conn = null;

	private final static String PRICES = "CREATE TABLE `PRICES` ("
	+ "`id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,"
	+ "`items` VARCHAR(16) NOT NULL,"
	+ "`price` INTEGER(11) NOT NULL"
	+ ");"; 

	public static Connection initialize()
	{
		try
		{
				Class.forName("com.mysql.jdbc.Driver");
				conn = DriverManager.getConnection(Configuration.SQLurl, Configuration.SQLuser, Configuration.SQLpass);
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
    
    private static boolean TableExists()
    {
        ResultSet rs = null;
        try 
        {
            Connection conn = getConnection();

            DatabaseMetaData dbm = conn.getMetaData();
            rs = dbm.getTables(null, null, "PRICES", null);
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
      
    public static void CreateTable()
    {
		Statement st = null;
    	try 
    	{
			Log.info(" Populating Database...");
    		Connection conn = getConnection();
    		st = conn.createStatement();
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
    
    public static void closeConnection()
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
    
    public static Connection getConnection()
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
    
    public static void Store(String query)
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
    		Log.severe(" SQL Exception:");
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
    			Log.severe(" SQL Exception:");
    			Log.severe("  " + ex.getMessage());
    		}
    	} 
    }
}


