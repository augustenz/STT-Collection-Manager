/*
 * Copyright (C) 2017 August Enzelberger <augustenz+github@gmail.com> (https://github.com/augustenz)
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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

/**
 *
 * @author August Enzelberger <augustenz+github@gmail.com>
 * (https://github.com/augustenz)
 */
public class FileParser {
    private File crewFile;
    
    public FileParser() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
        FileFilter filter = new FileFilter() {

            @Override
            public boolean accept(File f) {
                if(f.getName().endsWith(".xls"))
                    return true;
                else
                    return false;
            }

            @Override
            public String getDescription() {
                    return "Excel Files (*.xls)";
            }
        };
        fileChooser.addChoosableFileFilter(filter);
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.setDialogTitle("Import table data");
        
        int result = fileChooser.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
            crewFile = fileChooser.getSelectedFile();
            System.out.println("Selected file: " + crewFile.getAbsolutePath());
        }    
    }
    
    public LinkedList<CrewMember> importCrewMembers(String table) {
        if(crewFile!=null) {
            LinkedList<CrewMember> crewList = new LinkedList<CrewMember>();

            String dataFile = crewFile.getAbsolutePath();
            BufferedReader br = null;
            String line = "";
            String delim = "\t";


            try {
                br = new BufferedReader(new FileReader(dataFile));
                line = br.readLine();

                if (table.equals("Crew")) {
                    while ((line = br.readLine()) != null) {
                        String[] crewMemberData = line.split(delim);

                        CrewMember crewMember = new CrewMember();
                        crewMember.setCmd("0");
                        crewMember.setDip("0");
                        crewMember.setEng("0");
                        crewMember.setMed("0");
                        crewMember.setSci("0");
                        crewMember.setSec("0");
                        crewMember.setRace("");          
                        String[] traits = new String[1];

                        crewMember.setCrewName(crewMemberData[1]);
                        crewMember.setCharName(crewMemberData[2]);
                        crewMember.setStars(crewMemberData[3]);
                        crewMember.setCmd(crewMemberData[4]);          
                        crewMember.setDip(crewMemberData[5]);
                        crewMember.setEng(crewMemberData[6]);
                        crewMember.setMed(crewMemberData[7]);
                        crewMember.setSci(crewMemberData[8]);
                        crewMember.setSec(crewMemberData[9]);
                        crewMember.setRace(crewMemberData[10]);
                        traits[0] = crewMemberData[11];
                        crewMember.setTraits(traits);
                        crewList.add(crewMember);
                    }
                } else if (table.equals("Collection")) {
                    while ((line = br.readLine()) != null) {
                        String[] crewMemberData = line.split(delim);

                        CrewMember crewMember = new CrewMember();
                        crewMember.setCmd("0");
                        crewMember.setDip("0");
                        crewMember.setEng("0");
                        crewMember.setMed("0");
                        crewMember.setSci("0");
                        crewMember.setSec("0");
                        crewMember.setStarsFused("0");
                        crewMember.setLevel("0");
                        crewMember.setQuantity("0");

                        crewMember.setCrewName(crewMemberData[1]);
                        crewMember.setStarsFused(crewMemberData[4]);
                        crewMember.setLevel(crewMemberData[5]);

                        if(crewMemberData[6].equals("true")) {
                            crewMember.setFullyEquiped(true);
                        } else {
                            crewMember.setFullyEquiped(false);
                        }

                        if(crewMemberData[7].equals("true")) {
                            crewMember.setInVault(true);
                        } else {
                            crewMember.setInVault(false);
                        }    

                        crewMember.setCmd(crewMemberData[8]);          
                        crewMember.setDip(crewMemberData[9]);
                        crewMember.setEng(crewMemberData[10]);
                        crewMember.setMed(crewMemberData[11]);
                        crewMember.setSci(crewMemberData[12]);
                        crewMember.setSec(crewMemberData[13]);
                        crewMember.setQuantity(crewMemberData[16]);
                        crewList.add(crewMember);
                    }
                }     
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (br != null) {
                    try {
                        br.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            return crewList;
        }
        else
            return null;
    }
}
