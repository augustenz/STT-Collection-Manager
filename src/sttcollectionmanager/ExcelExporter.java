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

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JTable;
import javax.swing.table.TableModel;

public class ExcelExporter {
    public void exportTable(JTable table, File file, String tableName) throws IOException {
        TableModel model = table.getModel();
        FileWriter out = new FileWriter(file);
        
        int columns=model.getColumnCount();
        if(tableName.equals("STT Crew Collection"))
            columns-=1;
        
        for(int i=0; i<columns; i++) {
            out.write(model.getColumnName(i) + "\t");
        }
        out.write(System.getProperty("line.separator"));

        for(int i=0; i<model.getRowCount(); i++) {
            for(int j=0; j < columns; j++) {
                try {
                    out.write(model.getValueAt(i,j).toString()+"\t");
                }
                catch (NullPointerException ex) {
                    System.out.println("Cell("+i+":"+j+")=null");
                }
            }
            out.write(System.getProperty("line.separator"));
        }

        out.close();
        System.out.println("write out to: " + file);
    }
}

