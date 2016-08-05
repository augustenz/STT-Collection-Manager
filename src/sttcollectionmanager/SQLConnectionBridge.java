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

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author August Enzelberger <augustenz+github@gmail.com>
 * (https://github.com/augustenz)
 */
public class SQLConnectionBridge {
    JdbcUrl jdbcUrl;
    String user;
    String password;
    STTCollectionManager sttCollectionManager;
    
    
    public SQLConnectionBridge(JdbcUrl jdbcUrl, String user, String password, STTCollectionManager sttCollectionManager) {
        this.jdbcUrl=jdbcUrl;
        
        this.user=user;
        this.password=password;
        this.sttCollectionManager=sttCollectionManager;
    }
    
    SQLException update(String SQL) {
        SQLException sqlException;
        DBConnection conn = new DBConnection(jdbcUrl.getDriver());
        String conStatus = conn.connect(jdbcUrl,user,password);
        System.out.println("\nConnection to Database: "+conStatus+"\n");
        sttCollectionManager.getLogTextArea().append("\nConnection to Database: "+conStatus+"\n");
        try {
            Statement statement = conn.getStatement();

            try {
                SQLExecution sqlExecution = new SQLExecution(SQL,conn,sttCollectionManager);
                sqlException = sqlExecution.update();
                try {
                    
                } finally {
                    conn.close(conn.getResultSet());
                }
            } finally {
                conn.close(conn.getStatement());
            }
        } finally {
            conn.closeConnection(false);
        }
        return sqlException;
    }
    
    DefaultTableModel query(String SQL, String[] columnNames) {
        DefaultTableModel model = new DefaultTableModel();
        DBConnection conn = new DBConnection(jdbcUrl.getDriver());
        String conStatus = conn.connect(jdbcUrl,user,password);
        System.out.println("\nConnection to Database: "+conStatus+"\n");
        sttCollectionManager.getLogTextArea().append("\nConnection to Database: "+conStatus+"\n");
        ResultSet rs = conn.getResultSet();
        for(int i=0; i<columnNames.length; i++)
            model.addColumn(columnNames[i]);
        
        try {
            Statement statement = conn.getStatement();

            try {
                SQLExecution sqlExecution = new SQLExecution(SQL,conn,sttCollectionManager);
                rs = sqlExecution.query();
                try {
                    try {
                        if(rs!=null) {
                            Object[] data = new Object[columnNames.length];

                            while (rs.next()) {
                                boolean fullyEquipped=false;
                                boolean inVault=false;
                                boolean inCollection=false;
                                for(int i=0; i<columnNames.length; i++) {
                                    if(rs.getString(i+1)!=null) {
                                        if(columnNames[i].equals("Fully Equipped") && rs.getString(i+1).equals("true"))
                                            fullyEquipped=true;
                                        if(columnNames[i].equals("In Vault") && rs.getString(i+1).equals("true"))
                                            inVault=true;
                                        if(columnNames[i].equals("In Collection") && rs.getString(i+1).equals("true"))
                                            inCollection=true;
                                    }
                                }
                                for(int i=0; i<columnNames.length; i++) {
                                    if(columnNames[i].equals("Fully Equipped"))
                                        data[i]=(Object)fullyEquipped;
                                    else
                                        if(columnNames[i].equals("In Vault"))
                                            data[i]=(Object)inVault;
                                        else
                                            if(columnNames[i].equals("In Collection"))
                                                data[i]=(Object)inCollection;
                                            else
                                                data[i]=rs.getString(i+1);
                                }

                                model.addRow(data);
                            }
                        }
                    }catch (Exception ex) {
                            Logger.getLogger(SQLConnectionBridge.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } finally {
                    conn.close(conn.getResultSet());
                }
            } finally {
                conn.close(conn.getStatement());
            }
        } finally {
            conn.closeConnection(false);
        }
        return model;
    }
    
    DefaultTableModel query(String SQL, String columnName) {
        String[] columnNames = new String[1];
        columnNames[0]=columnName;
        return query(SQL,columnNames);
    }
    
    public DatabaseMetaData getMeta(){
        DBConnection conn = new DBConnection(jdbcUrl.getDriver());
        DatabaseMetaData meta;
        try {
            String conStatus = conn.connect(jdbcUrl,user,password);
            System.out.println("\nConnection to Database: "+conStatus+"\n");
            sttCollectionManager.getLogTextArea().append("\nConnection to Database: "+conStatus+"\n");
        } finally {
            conn.closeConnection(false);
            return conn.getMeta();
        }  
    }
    
    void shutdown() {
        DBConnection conn = new DBConnection(jdbcUrl.getDriver());
        String conStatus = conn.connect(jdbcUrl,user,password);
        System.out.println("\nConnection to Database: "+conStatus+"\n");
        sttCollectionManager.getLogTextArea().append("\nConnection to Database: "+conStatus+"\n");
        conn.closeConnection(true);
    }
}
