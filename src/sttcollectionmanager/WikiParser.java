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

/**
 *
 * August Enzelberger <augustenz+github@gmail.com> (https://github.com/augustenz)
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WikiParser {
    private URL url;
    
    public WikiParser() {
        
    }
    
    public WikiParser(URL url) {
        this.url = url;
    }
    public String parsePage() {
        try {
            url = new URL(this.url.toString());
        } catch (MalformedURLException ex) {
            Logger.getLogger(WikiParser.class.getName()).log(Level.SEVERE, null, ex);
        }

        String text = "";
            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(url.openConnection().getInputStream()));
                String line = br.readLine();
                while (line != null) {
                    line = line.trim();
                    if (line.startsWith("| CrewName") || line.startsWith("| CharName") || line.startsWith("| Stars") || line.startsWith("| CMD") || line.startsWith("| DIP") || line.startsWith("| ENG") || line.startsWith("| MED") || line.startsWith("| SCI") || line.startsWith("| SEC") || line.startsWith("| Race") || line.startsWith("<!--Traits")) {
                        text += line;
                    }
                    line = br.readLine();
                }
            } catch (IOException ex) {
                Logger.getLogger(WikiParser.class.getName()).log(Level.SEVERE, null, ex);
            }
        return text;
    }
    
    public LinkedList<CrewMember> getCrewMembers() {
        LinkedList<CrewMember> crewList = new LinkedList<CrewMember>();
        URL url=null;
        
        try {
            url = new URL ("http://startrektimelineswiki.com/wiki/Crew?action=raw");
            //url = new URL ("https://en.wikipedia.org/wiki/Main_Page?action=raw");
        } catch (MalformedURLException ex) {
            Logger.getLogger(STTCollectionManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        WikiParser parser = new WikiParser(url);
        String text = parser.parsePage();
        
        String delims = "[|,=,<,>]";
        String[] tokens = text.split(delims);

        for (int i = 0; i < tokens.length; i++) {
           tokens[i] = tokens[i].trim(); 
        }
        
        for (int i = 0; i < tokens.length; i++) {
            //System.out.println(i+": "+tokens[i]);
            CrewMember crewMember = new CrewMember();
            crewMember.setCmd("0");
            crewMember.setDip("0");
            crewMember.setEng("0");
            crewMember.setMed("0");
            crewMember.setSci("0");
            crewMember.setSec("0");
            crewMember.setRace("");          
            String[] traits = new String[30];
            
            if (tokens[i].equals("CrewName")) {
                String crewName = "";
                int k=i;
                while (!tokens[k+1].equals("CharName")) { 
                    crewName = crewName.concat(tokens[k+1].trim()+" ");
                    k++;
                }
                crewMember.setCrewName(crewName.trim());
                //String[] splitCharName = tokens[i+3].split("[{,(]");
                //while (!tokens[j].equals("CharName") && j+5 < tokens.length) {
                String[] splitCharName = tokens[k+2].split("[{,(]");
                crewMember.setCharName(splitCharName[0].trim());
                //crewMember.setStars(tokens[i+5]);
                crewMember.setStars(tokens[k+4]);
                int j=i+1;
                //while (!tokens[j].equals("Race")) {
                while (!tokens[j].equals("CrewName") && j+5 < tokens.length) {
                    if (tokens[j+5].equals("CMD")) {
                        crewMember.setCmd(tokens[j+6]);
                    }
                    if (tokens[j+5].equals("DIP")) {
                        crewMember.setDip(tokens[j+6]);
                    }
                    if (tokens[j+5].equals("ENG")) {
                        crewMember.setEng(tokens[j+6]);
                    }
                    if (tokens[j+5].equals("MED")) {
                        crewMember.setMed(tokens[j+6]);
                    }
                    if (tokens[j+5].equals("SCI")) {
                        crewMember.setSci(tokens[j+6]);
                    }
                    if (tokens[j+5].equals("SEC")) {
                        crewMember.setSec(tokens[j+6]);
                    }
                    if (tokens[j+5].equals("Race")) {
                        crewMember.setRace(tokens[j+6]);
                    }
                    if (tokens[j+5].startsWith("!")) {
                        int h=j+7;
                        int t=0;
                        
                        do {
                            if(!tokens[h].equals(" "))
                                traits[t] = tokens[h];
                            if(tokens[h].endsWith("}"))
                                traits[t] = traits[t].substring(0, traits[t].length()-2);
                            
                            t++;
                            h++;
                        } while(!tokens[h-1].endsWith("}"));
                        crewMember.setTraits(traits);
                    }
                    j++;
                }
                crewList.add(crewMember);
            }
            
        }
        return crewList;
    }
}

