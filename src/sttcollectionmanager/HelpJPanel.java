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

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

/**
 *
 * @author August Enzelberger <augustenz+github@gmail.com>
 * (https://github.com/augustenz)
 */
public class HelpJPanel extends javax.swing.JPanel {

    /**
     * Creates new form HelpJPanel
     */
    public HelpJPanel() {
        initComponents();
        
        String text="STT Collection Manager\n" +
                    "Author: August Enzelberger\n" +
                    "Project Homepage: https://github.com/augustenz/STT-Collection-Manager\n" +
                    "Date: 5/8/2016\n" +
                    "\n" +
                    "STT Collection Manager is a Java application, developed to help manage a Crew collection for Disruptor Beam's Star Trek Timelines game.\n" +
                    "\n" +
                    "=====================================================================\n" +
                    "Important Info\n" +
                    "\n" +
                    "This application needs the latest JRE (Java Runtime Environment) to run, it is available for various systems and can be obtained\n" +
                    "from www.oracle.com (or https://java.com/en/download/).\n" +
                    "\n" +
                    "JavaFX is used by this application to display charts, it is included in Oracle's JRE so nothing else is needed if that JRE is installed.\n" +
                    "Other JREs (like OpenJDK in Linux) need extra packages (like OpenJFX in Linux) to run Java applications that contain JavaFX elements.\n" +
                    "In that case either the extra packages can be installed, or Oracle's JRE.\n" +
                    "JavaFX is required to run this application.\n" +
                    "\n" +
                    "The database is saved in a folder with the name \"STTCollectionManager\" in the user's home directory, or in the directory containing the application.\n" +
                    "\n" +
                    "To run the application, double-click on the STTCollectionManager.jar file, or right click and choose \n" +
                    "open with and your JRE (java.exe on Windows, OpenJDK or Oracle Java on Linux).\n" +
                    "\n" +
                    "To run the project from the command line, go to the application folder and \n" +
                    "type the following:\n" +
                    "\n" +
                    "\n" +
                    "java -jar \"STTCollectionManager.jar\"\n" +
                    "\n" +
                    "The Shortcut icons folder contains icon images of various sizes if you wish to make a shortcut\n" +
                    "for the application.\n" +
                    "\n" +
                    "=====================================================================\n" +
                    "STT Collection Manager consists of multiple tabs:\n" +
                    "\n" +
                    "1) The Collection tab\n" +
                    "\n" +
                    "This tab contains the Crew Collection table. Here all crew that is currently in the collection can be edited. The editable fields are all the skill fields,\n" +
                    "the fused stars field, the level field, the quantity field (in case of multiples) and a checkbox that removes crew from the collection.\n" +
                    "Clicking on a column header will sort the table according to that column.\n" +
                    "Clicking on the Crew Name field, Race field or any of the Traits in the traits field will open the corresponding wiki page.\n" +
                    "Right-clicking on crew members brings up a menu that allows to make a copy of the selected crew member (multiples but with different stats).\n" +
                    "\n" +
                    "2) The Crew tab\n" +
                    "\n" +
                    "This tab contains the Crew table. Here all crew in the game is listed, no fields are editable here except a checkbox that only allows adding crew to the\n" +
                    "collection.\n" +
                    "Clicking on a column header will sort the table according to that column.\n" +
                    "Clicking on the Crew Name field, Race field or any of the Traits in the traits field will open the corresponding wiki page.\n" +
                    "\n" +
                    "3) The Log tab\n" +
                    "\n" +
                    "This tab contains a textarea where messages regarding the execution of commands can be seen.\n" +
                    "\n" +
                    "4) The Charts tab\n" +
                    "\n" +
                    "This tab contains four charts:\n" +
                    "\n" +
                    " - All Crew pie chart: A pie chart where the balance (quantity of crew/rarity) of the crew in the game can be seen. Each slice represents the number\n" +
                    "of crew in game of a certain rarity.\n" +
                    " \n" +
                    " - Crew Collection pie chart: A pie chart where the balance (quantity of crew/rarity) of the collection can be seen. Each slice represents the number\n" +
                    "of crew in the collection of a certain rarity.\n" +
                    " \n" +
                    " - Bar chart: A bar chart where on the left the bars represent the number of crew in the game of a certain rarity and on the right the number of crew\n" +
                    "in the collection of a certain rarity.\n" +
                    " \n" +
                    " - Stacked bar chart: Similar to the bar chart, only that the bars are stacked instead of being side-by-side.\n" +
                    "\n" +
                    "Every chart will show the number that each graphic element represents as a mouse tooltip.\n" +
                    "If a chart is clicked it is opened in a new window where it is enlarged and can be maximized.\n" +
                    "\n" +
                    "5) The Stats tab\n" +
                    "\n" +
                    "This tab has a text field where the number of crew slots can be entered. It has some information about the collection, such as the number of crew/crew\n" +
                    "slots, the number of crew in the collection per rarity/the number of crew in the game per rarity and the average crew level.\n" +
                    "\n" +
                    "=====================================================================\n" +
                    "The Menu Bar\n" +
                    "\n" +
                    "The menu bar consists of five menus: File, Search, Export, Skin, Font and Help.\n" +
                    "\n" +
                    "1) File\n" +
                    "\n" +
                    " - Download Crew: Drops the crew table (the collection is kept intact) and re fetches all the crew data from the wiki. This is useful when new crew is\n" +
                    "added to the wiki.\n" +
                    "\n" +
                    " - Refresh Tables: Refreshes the data shown in the tables, affects only the view, not the data.\n" +
                    "\n" +
                    " - Exit: Saves various settings like the window size, window position etc, shuts down the database and closes the application.\n" +
                    "\n" +
                    "2) Search\n" +
                    "\n" +
                    "Using the search function, both tables can be filtered according to certain criteria. Typing in any text field will immediately query the database and\n" +
                    "populate the tables accordingly.\n" +
                    "Any info can be entered only partly and the matches (if any) will be shown.\n" +
                    "Multiple traits can be entered (they can be separated by a space or a comma) but there will be matches only if they are entered in the same order that they\n" +
                    "appear in.\n" +
                    "Single traits of course will be matched regardless of their position in the field.\n" +
                    "\n" +
                    "For the search window to open, this menu item has to be pressed when either the Collection tab or the Crew tab are open. It will filter the table that is\n" +
                    "open.\n" +
                    "\n" +
                    "3) Export\n" +
                    "\n" +
                    " - Export All Crew: Exports all crew in the game to a HTML (crew in collection is shown in bold), text, or csv (good for Spreadsheets) file.\n" +
                    "\n" +
                    " - Export Crew Collection: Exports the crew collection to a HTML, text, or csv (good for Spreadsheets) file.\n" +
                    "\n" +
                    "4) Skin\n" +
                    "\n" +
                    "Lists skins available on the system and allows changing the current skin. This setting is saved.\n" +
                    "\n" +
                    "5) Font\n" +
                    "\n" +
                    " - Increase Font Size: Increases the font size.\n" +
                    " - Decrease Font Size: Decreases the font size.\n" +
                    " - Reset Font Size: Resets the font size (12).\n" +
                    " - Change Font: Lists fonts found on the system and allows to change the font being used in the tables.\n" +
                    "\n" +
                    "These settings are saved.\n" +
                    "\n" +
                    "6) Help\n" +
                    "\n" +
                    " - Help: Probably this readme file :)\n" +
                    " - Info: Info about the system.\n" +
                    " - About: The application credits.";
        
        helpEditorPane.setText(text);
        helpEditorPane.setCaretPosition(0);
        helpEditorPane.addHyperlinkListener(new HyperlinkListener() {
            public void hyperlinkUpdate(HyperlinkEvent e) {
                if(e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
                    try {
                        open(e.getURL().toURI());
                    } catch (URISyntaxException ex) {
                        Logger.getLogger(AboutJFrame.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        helpScrollPane = new javax.swing.JScrollPane();
        helpEditorPane = new javax.swing.JEditorPane();

        setPreferredSize(new java.awt.Dimension(960, 400));

        helpScrollPane.setViewportView(helpEditorPane);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(helpScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(helpScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JEditorPane helpEditorPane;
    private javax.swing.JScrollPane helpScrollPane;
    // End of variables declaration//GEN-END:variables
 private void open(URI uri) {
        if (Desktop.isDesktopSupported()) {
          Desktop desktop = Desktop.getDesktop();
          try {
            desktop.browse(uri);
          } catch (IOException e) {
            JOptionPane.showMessageDialog(this,
                "Failed to launch the link, your computer is likely misconfigured.",
                "Cannot Launch Link",JOptionPane.WARNING_MESSAGE);
          }
        } else {
          JOptionPane.showMessageDialog(this,
              "Java is not able to launch links on your computer.",
              "Cannot Launch Link", JOptionPane.WARNING_MESSAGE);
        }
    } 
}
