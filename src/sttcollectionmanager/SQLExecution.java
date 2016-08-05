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

import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JTextArea;

/**
 *
 * August Enzelberger <augustenz+github@gmail.com> (https://github.com/augustenz)
 */
public class SQLExecution {
    String SQL;
    DBConnection conn;
    STTCollectionManager view;

    SQLExecution(String SQL, DBConnection conn, STTCollectionManager view) {
        this.SQL=SQL;
        this.conn=conn;
        this.view=view;
    }

    SQLException update() {
        try {
            conn.getStatement().executeUpdate(SQL);
            System.out.println(SQL+"\nExecution completed successfully!\n");
            printToLogTextArea(SQL+"\n\t\tExecution completed successfully!\n");
            return null;
        } catch (SQLException ex) {
            System.out.println("Problems with: "+SQL+"\nError message:\n"+ex.getMessage()+"\n");
            printToLogTextArea("Problems with: "+SQL+"\n\t\tError message:\n\t\t"+ex.getMessage()+"\n");
            return ex;
            
        } catch (NullPointerException e) {
            return new SQLException();
        }
    }

    ResultSet query() {
        try {
            conn.setResultSet(conn.getStatement().executeQuery(SQL));
            System.out.println(SQL+"\nExecution completed successfully!\n");
            printToLogTextArea(SQL+"\n\t\tExecution completed successfully!\n");
            return conn.getResultSet();
        } catch (SQLException ex) {
            System.out.println("Problems with: "+SQL+"\nError message:\n"+ex.getMessage()+"\n");
            printToLogTextArea("Problems with: "+SQL+"\n\t\tError message:\n\t\t"+ex.getMessage()+"\n");
            return null;
        } catch (NullPointerException e) {
            return null;
        } 
    }

    private void printToLogTextArea(String SQL){
        JTextArea t = view.getLogTextArea();
        String s = view.todayNow();
        t.append("\n"+s+"\t"+SQL);
        t.setCaretPosition(t.getText().length());
    }
}
