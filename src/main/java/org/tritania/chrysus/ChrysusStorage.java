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
import java.util.logging.Logger;

public class ChrysusStorage 
{
	private Logger log;
	private Chrysus plugin;
	
	public ChrysusStorage(Chrysus plugin)
	{
		this.plugin = plugin;
	}
	
	private static Connection conn = null;

	private final static String ITEM_IO = ""; 
	private final static String PLAYER_IO = "CREATE TABLE `PLAYER_IO` ("
	+ "`id` INT NOT NULL AUTO_INCREMENT PRIMARY KEY,"
	+ "`player` INTEGER(11) NOT NULL,"
	+ "`company` VARCHAR(30) NOT NULL,"
	+ "`balance` INTEGER(11) NOT NULL"
	+ ");"; 
	
	private final static String COMPANY_IO = ""; //I think you ge the picture by now
	/*The Above are strings for the table creation querys*/

	public static Connection initialize()
	{
		try
		{
				Class.forName("com.mysql.jdbc.Driver");
				conn = DriverManager.getConnection(Chrysus.SQLurl, Chrysus.SQLuser, Chrysus.SQLpass);
				conn.setAutoCommit(false);
		}

			
		catch (SQLException ex) 
		{ 
			Chrysus.logger.severe("SQL exception on initialize :");
        	Chrysus.logger.severe("  " + ex.getMessage());
		}
        catch (ClassNotFoundException ex) 
        { 
			Chrysus.logger.severe("You need the MySQL library. :");
        	Chrysus.logger.severe("  " + ex.getMessage());
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
            rs = dbm.getTables(null, null, "PLAYER_IO", null);
            if (!rs.next())
                return false;
            return true;
        } 
        catch (SQLException ex) 
        {
            Chrysus.logger.severe(" Table Check Exception :");
        	Chrysus.logger.severe("  " + ex.getMessage());
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
					Chrysus.logger.severe(" Table Check SQL Exception (on closing) :");
					Chrysus.logger.severe("  " + ex.getMessage());
			}
        }
    }
      
    public static void CreateTable()
    {
		Statement st = null;
    	try 
    	{
			Chrysus.logger.info(" Creating Database...");
    		Connection conn = getConnection();
    		st = conn.createStatement();
    		st.executeUpdate(PLAYER_IO);
    		conn.commit();
    		
		}
		catch (SQLException ex) 
    	{ 
    		Chrysus.logger.severe(" Create Table Exception :");
    		Chrysus.logger.severe("  " + ex.getMessage());
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
    			Chrysus.logger.severe(" Could not create the table (on close) :");
    			Chrysus.logger.severe("  " + ex.getMessage());
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
					Chrysus.logger.severe("Error on Connection close :");
					Chrysus.logger.severe("  " + ex.getMessage());
			}
		}
    }
    
    public static Connection getConnection()
	{
		if(conn == null) conn = initialize();
		if(Chrysus.usemySQL) 
		{
			try 
			{
				if(!conn.isValid(10)) conn = initialize();
			} 
			catch (SQLException ex) 
			{
				Chrysus.logger.severe("Failed to check SQL status :");
				Chrysus.logger.severe("  " + ex.getMessage());
		    }
		}
		return conn;
	}
	
	public static void cleanData(String playername)
	{
		
	}
    
    public static void sell(String playername, String items)
	{
		
	}
}

