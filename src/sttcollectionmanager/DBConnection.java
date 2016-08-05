/*
 * Copyright (C) 2016 August Enzelberger <augustenz+github@gmail.com> (https://github.com/augustenz)
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
package sttcollectionmanager;

import java.sql.*;

public class DBConnection {
    
    private JdbcUrl jdbcUrl;
    private String username;
    private String password;
    private Connection myConnection;
    private DatabaseMetaData meta;
    private Statement stmt;
    private ResultSet rs;

    DBConnection(String driver) {
        try {
            if(driver.equalsIgnoreCase("derby")) {
                Class.forName("org.apache.derby.jdbc.ClientDriver");
            }
            else if(driver.equalsIgnoreCase("mysql"))
                Class.forName("com.mysql.jdbc.Driver");
        }
        catch (Exception e) {
            return;
        }
        return;
    }
    
    public String connect(JdbcUrl jdbcUrl, String username, String password) {
        try {
            this.jdbcUrl=jdbcUrl;
            this.username=username;
            this.password=password;
            //String url=jdbcUrl.getURL();
            
            /*if(jdbcUrl.getDriver().equalsIgnoreCase("derby")) {
            url+="create=true;";
            }*/

            myConnection = DriverManager.getConnection(jdbcUrl.getEmbeddedURLConnect(),username,password);
            meta = myConnection.getMetaData();
            stmt = myConnection.createStatement();
            return "Ok";
        }
        catch (Exception e) {return e.getMessage();}
    }                 
    
    public Connection getConnection(){
        return myConnection;
    }

    public DatabaseMetaData getMeta(){
        return meta;
    }

    public Statement getStatement(){
        return stmt;
    }
    
    public JdbcUrl getURL(){
        return jdbcUrl;
    }
    
    public void setResultSet(ResultSet rs) {
        this.rs =rs; 
    }
    
    public ResultSet getResultSet() {
        return rs; 
    }
    
    public void close(ResultSet rs){
        if(rs !=null){
            try{
                rs.close();
            }
            catch(Exception e){}
        }
    }
    
     public void close(Statement stmt){
        if(stmt !=null){
            try{
                stmt.close();
            }
            catch(Exception e){}
        }
    }
     
    public void closeConnection(Boolean shutdown){
        if(myConnection != null){
            try{
                if(shutdown) {
                    myConnection = DriverManager.getConnection(jdbcUrl.getEmbeddedURLClose(),username,password);
                    myConnection.close();
                }
                else
                    myConnection.close();
            }
            catch(Exception e){}
        }
    }   
}

