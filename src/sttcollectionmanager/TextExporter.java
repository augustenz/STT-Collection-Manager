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

/**
 *
 * August Enzelberger <augustenz+github@gmail.com> (https://github.com/augustenz)
 */
public class TextExporter {
    public void exportTable(JTable table, File file, String tableName) throws IOException {
        TableModel model = table.getModel();
        FileWriter out = new FileWriter(file);
        
        int columns=model.getColumnCount();
        if(tableName.equals("STT Crew Collection"))
            columns-=1;
        
        int columnLengths[]=findColumnLengths(model,columns);
        int separator=0;

        for(int i=0; i<columns; i++) {
            String tail = "";
            int offset = columnLengths[i]-model.getColumnName(i).length();
            for(int k=0; k<offset; k++)
                tail+=" ";
            String row=model.getColumnName(i)+tail + "\t";
            out.write(row);
        }
        out.write(System.getProperty("line.separator"));
        
        for(int i=0; i<columns; i++) {
            String tail = "";
            int offset = columnLengths[i]-model.getColumnName(i).length();
            for(int k=0; k<offset; k++)
                tail+=" ";
            for(int j=0; j<model.getColumnName(i).length(); j++)
                out.write("-");
            out.write(tail);
            out.write("\t");
        }
        out.write(System.getProperty("line.separator"));

        for(int i=0; i<model.getRowCount(); i++) {
            for(int j=0; j < columns; j++) {
                String cell = null;
                String tail = "";
                try {
                    if(tableName.equals("STT Crew Collection")) {
                        if((j==6 || j==7) && model.getValueAt(i,j).toString().equals("false"))
                            cell = "No";
                        else if((j==6 || j==7) && model.getValueAt(i,j).toString().equals("true"))
                            cell = "Yes";
                        else
                            cell = model.getValueAt(i,j).toString();
                    }
                    else
                        cell = model.getValueAt(i,j).toString();
                }
                catch (NullPointerException ex) {
                    System.out.println("Cell("+i+":"+j+")=null");
                    break;
                }
                
                int offset;

                if(columnLengths[j]<model.getColumnName(j).length())
                    offset = model.getColumnName(j).length()-cell.length();
                else
                    offset = columnLengths[j]-cell.length();

                for(int k=0; k<offset; k++)
                    tail+=" ";
                out.write(cell+tail+"\t");
            }
            out.write(System.getProperty("line.separator"));
        }
        out.close();
        System.out.println("write out to: " + file);
    }
    
    int[] findColumnLengths(TableModel model, int columns) {
        int columnLengths[] = new int[columns];
        for(int j=0; j < columns; j++) {
            for(int i=0; i<model.getRowCount(); i++) {
                try {
                    if(columnLengths[j]<model.getValueAt(i,j).toString().length())
                        columnLengths[j]=model.getValueAt(i,j).toString().length();
                }
                catch (NullPointerException ex) {
                    System.out.println("Cell("+i+":"+j+")=null");
                } 
            }
        }
        return columnLengths;
    }

}
