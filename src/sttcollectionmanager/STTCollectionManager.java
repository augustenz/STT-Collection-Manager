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

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.Image;
import java.awt.List;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.DatabaseMetaData;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javafx.embed.swing.JFXPanel;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.RowSorter;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

public class STTCollectionManager extends javax.swing.JFrame {

    /**
     * Creates new form MainJFrame
     */
    
    public STTCollectionManager() {
        sttCollectionManager=this;
        
        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            Logger.getLogger(STTCollectionManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        initComponents();
        
        /* Draw simple pie chart
        
        JFrame frame = new JFrame();
        SimplePieChart simplePieChart = new SimplePieChart();
        frame.getContentPane().add(simplePieChart.getMyComponent());
        frame.setSize(300, 300);
        frame.setVisible(true);
        */
        
        /* Change Table Font
        
        Font font=crewTable.getFont();
        try { 
            InputStream fontStream = getClass().getResourceAsStream("/sttcollectionmanager/resources/Roddenberry.ttf"); 
            font = Font.createFont(Font.TRUETYPE_FONT, fontStream); 
            font = font.deriveFont(Font.PLAIN, 12); 
            fontStream.close(); 
        } catch(Exception ex) { 
            System.out.println(ex.getMessage()); 
        }
        
        crewTable.setFont(new Font(font.getName(), Font.PLAIN, 12));
        crewCollectionTable.setFont(new Font(font.getName(), Font.PLAIN, 12));
        */

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
        
            @Override
            public void windowClosing(WindowEvent event) {
                SQLConnectionBridge sqlConnectionBridge = new SQLConnectionBridge(jdbcUrl,user,password,sttCollectionManager);                
                
                saveSettings(sqlConnectionBridge);
                
                if(sqlConnectionBridge!=null) {
                    sqlConnectionBridge.shutdown();
                }
            }
        });

        
        
        SQLConnectionBridge sqlConnectionBridge = new SQLConnectionBridge(jdbcUrl,user,password,sttCollectionManager);
        
        createGUI();
        firstRunCheck(sqlConnectionBridge);
        loadSettings(sqlConnectionBridge);
        addFrameIcons(this);
        fillCrewTable("no search", null);
        fillCrewCollectionTable("no search", null);
        addTableMouseListener(crewTable);
        addTableMouseListener(crewCollectionTable);
        fillStats();
        
        //if(System.getProperty("javafx.runtime.version").isEmpty())
            //JOptionPane.showMessageDialog(this,"JavaFX not found, please install the latest JRE. If you are not using Oracle's JRE, JavaFX may not be included (OpenJDK for example).\nIn that case you may need to install JavaFX manually (OpenJFX for example).\nCharts won't work and trying to open them may crash the application if JavaFX isn't installed.","Info",1);
        createCharts();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        mainTabbedPane = new javax.swing.JTabbedPane();
        collectionPanel = new javax.swing.JPanel();
        collectionScrollPane = new javax.swing.JScrollPane();
        crewCollectionTable = new JTable()  {@Override public boolean isCellEditable(int row, int column) {switch(column){case 0: return false;case 1: return false;case 2: return false;case 3: return false;case 14: return false;case 15: return false;default: return true;}}             @Override             public Component prepareRenderer(TableCellRenderer renderer, int row, int col) {                 Component c = super.prepareRenderer(renderer, row, col);                 getColumn("Fully Equipped").setCellRenderer(                     new DefaultTableCellRenderer() {                         JCheckBox renderer = new JCheckBox();                          @Override                         public Component getTableCellRendererComponent(JTable table, Object value,                                 boolean isSelected, boolean hasFocus, int row, int column) {                             super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);                              if (value != null && value instanceof Boolean) {                                  Boolean b = (Boolean) value;                                 renderer.setSelected(b);                                 renderer.setOpaque(true);                                  if (isSelected) {                                     renderer.setForeground(table.getSelectionForeground());                                     renderer.setBackground(table.getSelectionBackground());                                 } else {                                     Color bg = getBackground();                                     renderer.setForeground(getForeground());                                      renderer.setBackground(new Color(bg.getRed(), bg.getGreen(), bg.getBlue()));                                 }                                  renderer.setHorizontalAlignment(SwingConstants.CENTER);                                 return renderer;                             } else {                                 return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);                             }                         }                 });                 getColumn("In Vault").setCellRenderer(                     new DefaultTableCellRenderer() {                         JCheckBox renderer = new JCheckBox();                          @Override                         public Component getTableCellRendererComponent(JTable table, Object value,                                 boolean isSelected, boolean hasFocus, int row, int column) {                             super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);                              if (value != null && value instanceof Boolean) {                                  Boolean b = (Boolean) value;                                 renderer.setSelected(b);                                 renderer.setOpaque(true);                                  if (isSelected) {                                     renderer.setForeground(table.getSelectionForeground());                                     renderer.setBackground(table.getSelectionBackground());                                 } else {                                     Color bg = getBackground();                                     renderer.setForeground(getForeground());                                      renderer.setBackground(new Color(bg.getRed(), bg.getGreen(), bg.getBlue()));                                 }                                  renderer.setHorizontalAlignment(SwingConstants.CENTER);                                 return renderer;                             } else {                                 return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);                             }                         }                 });                 getColumn("In Collection").setCellRenderer(                     new DefaultTableCellRenderer() {                         JCheckBox renderer = new JCheckBox();                          @Override                         public Component getTableCellRendererComponent(JTable table, Object value,                                 boolean isSelected, boolean hasFocus, int row, int column) {                             super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);                              if (value != null && value instanceof Boolean) {                                  Boolean b = (Boolean) value;                                 renderer.setSelected(b);                                 renderer.setOpaque(true);                                  if (isSelected) {                                     renderer.setForeground(table.getSelectionForeground());                                     renderer.setBackground(table.getSelectionBackground());                                 } else {                                     Color bg = getBackground();                                     renderer.setForeground(getForeground());                                      renderer.setBackground(new Color(bg.getRed(), bg.getGreen(), bg.getBlue()));                                 }                                  renderer.setHorizontalAlignment(SwingConstants.CENTER);                                 return renderer;                             } else {                                 return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);                             }                         }                 });                  Integer stars = Integer.parseInt(getValueAt(row, 3).toString());                 switch(stars){                                  case 1: c.setBackground(hex2Rgb("#9b9b9b")); break;                     case 2: c.setBackground(hex2Rgb("#50aa3c")); break;                     case 3: c.setBackground(hex2Rgb("#5aaaff")); break;                     case 4: c.setBackground(hex2Rgb("#aa2deb")); break;                     case 5: c.setBackground(hex2Rgb("#fdd26a")); break;                        default: c.setBackground(Color.WHITE);                 }                 return c;             }         };
        crewPanel = new javax.swing.JPanel();
        crewScrollPane = new javax.swing.JScrollPane();
        crewTable = new JTable()  {@Override public boolean isCellEditable(int row, int column) {switch(column){case 0: return false;case 1: return false;case 2: return false;case 3: return false;case 4: return false;case 5: return false;case 6: return false;case 7: return false;case 8: return false;case 9: return false;case 10: return false;case 11: return false;default: return true;}}             @Override             public Component prepareRenderer(TableCellRenderer renderer, int row, int col) {                 Component c = super.prepareRenderer(renderer, row, col);                 getColumn("In Collection").setCellRenderer(                     new DefaultTableCellRenderer() {                         JCheckBox renderer = new JCheckBox();                          @Override                         public Component getTableCellRendererComponent(JTable table, Object value,                                 boolean isSelected, boolean hasFocus, int row, int column) {                             super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);                              if (value != null && value instanceof Boolean) {                                  Boolean b = (Boolean) value;                                 renderer.setSelected(b);                                 renderer.setOpaque(true);                                  if (isSelected) {                                     renderer.setForeground(table.getSelectionForeground());                                     renderer.setBackground(table.getSelectionBackground());                                 } else {                                     Color bg = getBackground();                                     renderer.setForeground(getForeground());                                      renderer.setBackground(new Color(bg.getRed(), bg.getGreen(), bg.getBlue()));                                 }                                  renderer.setHorizontalAlignment(SwingConstants.CENTER);                                 return renderer;                             } else {                                 return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);                             }                         }                 });                  Integer stars = Integer.parseInt(getValueAt(row, 3).toString());                 switch(stars){                                  case 1: c.setBackground(hex2Rgb("#9b9b9b")); break;                     case 2: c.setBackground(hex2Rgb("#50aa3c")); break;                     case 3: c.setBackground(hex2Rgb("#5aaaff")); break;                     case 4: c.setBackground(hex2Rgb("#aa2deb")); break;                     case 5: c.setBackground(hex2Rgb("#fdd26a")); break;                        default: c.setBackground(Color.WHITE);                 }                 return c;             }         };
        logPanel = new javax.swing.JPanel();
        logScrollPane = new javax.swing.JScrollPane();
        logTextArea = new javax.swing.JTextArea();
        chartsPanel = new javax.swing.JPanel();
        statsPanel = new javax.swing.JPanel();
        statsScrollPane = new javax.swing.JScrollPane();
        statsScrollablePanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        slotsUsedLabel = new javax.swing.JLabel();
        slotsUsedValueLabel = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        star5ValueLabel = new javax.swing.JLabel();
        star4ValueLabel = new javax.swing.JLabel();
        star3ValueLabel = new javax.swing.JLabel();
        star2ValueLabel = new javax.swing.JLabel();
        star1ValueLabel = new javax.swing.JLabel();
        fullyEquippedValueLabel = new javax.swing.JLabel();
        inVaultValueLabel = new javax.swing.JLabel();
        averageLevelValueLabel = new javax.swing.JLabel();
        jSeparator3 = new javax.swing.JSeparator();
        crewSlotsTextField = new javax.swing.JTextField();
        mainMenuBar = new javax.swing.JMenuBar();
        fileMenu = new javax.swing.JMenu();
        rebuildMenuItem = new javax.swing.JMenuItem();
        refreshMenuItem = new javax.swing.JMenuItem();
        exitMenuItem = new javax.swing.JMenuItem();
        searchMenu = new javax.swing.JMenu();
        searchMenuItem = new javax.swing.JMenuItem();
        exportMenu = new javax.swing.JMenu();
        exportCrewMenuItem = new javax.swing.JMenuItem();
        exportCollectionMenuItem = new javax.swing.JMenuItem();
        skinMenu = new javax.swing.JMenu();
        textSizeMenu = new javax.swing.JMenu();
        increaseTextSizeMenuItem = new javax.swing.JMenuItem();
        decreaseTextSizeMenuItem = new javax.swing.JMenuItem();
        resetTextSizeMenuItem = new javax.swing.JMenuItem();
        changeFontMenuItem = new javax.swing.JMenuItem();
        helpMenu = new javax.swing.JMenu();
        helpMenuItem = new javax.swing.JMenuItem();
        infoMenuItem = new javax.swing.JMenuItem();
        aboutMenuItem = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("STT Collection Manager");
        setSize(new java.awt.Dimension(720, 576));
        getContentPane().setLayout(new java.awt.GridBagLayout());

        mainTabbedPane.setName(""); // NOI18N
        mainTabbedPane.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                mainTabbedPaneStateChanged(evt);
            }
        });
        mainTabbedPane.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                mainTabbedPaneMouseEntered(evt);
            }
        });

        crewCollectionTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        crewCollectionTable.setToolTipText("Right click on a crew member to make a copy (duplicates). Click on editable fields to enter custom values. Click on the Crew Name, Char Name, Race or any of the Traits to open the corresponding WiKi page. Press the \"In Collection\" checkbox to remove a crew member from the collection.");
        collectionScrollPane.setViewportView(crewCollectionTable);

        javax.swing.GroupLayout collectionPanelLayout = new javax.swing.GroupLayout(collectionPanel);
        collectionPanel.setLayout(collectionPanelLayout);
        collectionPanelLayout.setHorizontalGroup(
            collectionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(collectionScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 913, Short.MAX_VALUE)
        );
        collectionPanelLayout.setVerticalGroup(
            collectionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(collectionScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 404, Short.MAX_VALUE)
        );

        mainTabbedPane.addTab("Collection ", new javax.swing.ImageIcon(getClass().getResource("/sttcollectionmanager/resources/table.png")), collectionPanel); // NOI18N

        crewTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        crewTable.setToolTipText("Press on the checkbox to add a crew member to the collection. Click on the Crew Name, Char Name, Race or any of the Traits to open the corresponding WiKi page.");
        crewScrollPane.setViewportView(crewTable);
        crewTable.getAccessibleContext().setAccessibleName("");

        javax.swing.GroupLayout crewPanelLayout = new javax.swing.GroupLayout(crewPanel);
        crewPanel.setLayout(crewPanelLayout);
        crewPanelLayout.setHorizontalGroup(
            crewPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(crewScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 913, Short.MAX_VALUE)
        );
        crewPanelLayout.setVerticalGroup(
            crewPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(crewScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 404, Short.MAX_VALUE)
        );

        mainTabbedPane.addTab("Crew ", new javax.swing.ImageIcon(getClass().getResource("/sttcollectionmanager/resources/table.png")), crewPanel); // NOI18N

        logTextArea.setColumns(20);
        logTextArea.setRows(5);
        logScrollPane.setViewportView(logTextArea);

        javax.swing.GroupLayout logPanelLayout = new javax.swing.GroupLayout(logPanel);
        logPanel.setLayout(logPanelLayout);
        logPanelLayout.setHorizontalGroup(
            logPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(logScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 913, Short.MAX_VALUE)
        );
        logPanelLayout.setVerticalGroup(
            logPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(logScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 404, Short.MAX_VALUE)
        );

        mainTabbedPane.addTab("Log ", new javax.swing.ImageIcon(getClass().getResource("/sttcollectionmanager/resources/log3.png")), logPanel); // NOI18N

        chartsPanel.setLayout(new java.awt.GridBagLayout());
        mainTabbedPane.addTab("Charts ", new javax.swing.ImageIcon(getClass().getResource("/sttcollectionmanager/resources/stats.png")), chartsPanel); // NOI18N

        statsScrollablePanel.setLayout(new java.awt.GridBagLayout());

        jLabel1.setText("Crew slots");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 5);
        statsScrollablePanel.add(jLabel1, gridBagConstraints);

        slotsUsedLabel.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        slotsUsedLabel.setText("Crew slots used");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 10);
        statsScrollablePanel.add(slotsUsedLabel, gridBagConstraints);

        slotsUsedValueLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        slotsUsedValueLabel.setText("jLabel3");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridheight = 2;
        statsScrollablePanel.add(slotsUsedValueLabel, gridBagConstraints);

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel2.setText("5-star crew");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 10);
        statsScrollablePanel.add(jLabel2, gridBagConstraints);

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel3.setText("4-Star crew");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 10);
        statsScrollablePanel.add(jLabel3, gridBagConstraints);

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel4.setText("3-Star crew");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 10);
        statsScrollablePanel.add(jLabel4, gridBagConstraints);

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel5.setText("2-Star crew");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 10);
        statsScrollablePanel.add(jLabel5, gridBagConstraints);

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel6.setText("1-Star crew");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 10);
        statsScrollablePanel.add(jLabel6, gridBagConstraints);

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel7.setText("Fully equipped crew");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 10);
        statsScrollablePanel.add(jLabel7, gridBagConstraints);

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel8.setText("In vault crew");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 9;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 10);
        statsScrollablePanel.add(jLabel8, gridBagConstraints);

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel9.setText("Average crew level");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 10;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 10);
        statsScrollablePanel.add(jLabel9, gridBagConstraints);

        star5ValueLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        star5ValueLabel.setText("jLabel10");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridheight = 2;
        statsScrollablePanel.add(star5ValueLabel, gridBagConstraints);

        star4ValueLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        star4ValueLabel.setText("jLabel11");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridheight = 2;
        statsScrollablePanel.add(star4ValueLabel, gridBagConstraints);

        star3ValueLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        star3ValueLabel.setText("jLabel12");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 5;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridheight = 2;
        statsScrollablePanel.add(star3ValueLabel, gridBagConstraints);

        star2ValueLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        star2ValueLabel.setText("jLabel13");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 6;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridheight = 2;
        statsScrollablePanel.add(star2ValueLabel, gridBagConstraints);

        star1ValueLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        star1ValueLabel.setText("jLabel14");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 7;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridheight = 2;
        statsScrollablePanel.add(star1ValueLabel, gridBagConstraints);

        fullyEquippedValueLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        fullyEquippedValueLabel.setText("jLabel15");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 8;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridheight = 2;
        statsScrollablePanel.add(fullyEquippedValueLabel, gridBagConstraints);

        inVaultValueLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        inVaultValueLabel.setText("jLabel16");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 9;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridheight = 2;
        statsScrollablePanel.add(inVaultValueLabel, gridBagConstraints);

        averageLevelValueLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        averageLevelValueLabel.setText("jLabel17");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 10;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridheight = 2;
        statsScrollablePanel.add(averageLevelValueLabel, gridBagConstraints);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 12;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 910;
        gridBagConstraints.ipady = 9;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(6, 0, 352, 0);
        statsScrollablePanel.add(jSeparator3, gridBagConstraints);

        crewSlotsTextField.setMinimumSize(new java.awt.Dimension(30, 20));
        crewSlotsTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                crewSlotsTextFieldKeyReleased(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.ipadx = 35;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 10);
        statsScrollablePanel.add(crewSlotsTextField, gridBagConstraints);

        statsScrollPane.setViewportView(statsScrollablePanel);

        javax.swing.GroupLayout statsPanelLayout = new javax.swing.GroupLayout(statsPanel);
        statsPanel.setLayout(statsPanelLayout);
        statsPanelLayout.setHorizontalGroup(
            statsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(statsScrollPane)
        );
        statsPanelLayout.setVerticalGroup(
            statsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(statsScrollPane)
        );

        mainTabbedPane.addTab("Stats ", new javax.swing.ImageIcon(getClass().getResource("/sttcollectionmanager/resources/stats2.png")), statsPanel); // NOI18N

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridwidth = java.awt.GridBagConstraints.RELATIVE;
        gridBagConstraints.gridheight = java.awt.GridBagConstraints.RELATIVE;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        getContentPane().add(mainTabbedPane, gridBagConstraints);
        mainTabbedPane.getAccessibleContext().setAccessibleName("Collection");

        fileMenu.setText("File");

        rebuildMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_D, java.awt.event.InputEvent.CTRL_MASK));
        rebuildMenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sttcollectionmanager/resources/download2.png"))); // NOI18N
        rebuildMenuItem.setText("Download Crew");
        rebuildMenuItem.setToolTipText("Drops the Crew table and fetches all crew data again (also rebuilds the whole database structure if it does not exist).");
        rebuildMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rebuildMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(rebuildMenuItem);

        refreshMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_R, java.awt.event.InputEvent.CTRL_MASK));
        refreshMenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sttcollectionmanager/resources/refresh.png"))); // NOI18N
        refreshMenuItem.setText("Refresh Tables");
        refreshMenuItem.setToolTipText("Refreshes the table contents.");
        refreshMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(refreshMenuItem);

        exitMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Q, java.awt.event.InputEvent.CTRL_MASK));
        exitMenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sttcollectionmanager/resources/exit.png"))); // NOI18N
        exitMenuItem.setText("Exit");
        exitMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitMenuItemActionPerformed(evt);
            }
        });
        fileMenu.add(exitMenuItem);

        mainMenuBar.add(fileMenu);

        searchMenu.setText("Search");

        searchMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F, java.awt.event.InputEvent.CTRL_MASK));
        searchMenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sttcollectionmanager/resources/search3.png"))); // NOI18N
        searchMenuItem.setText("Search");
        searchMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                searchMenuItemActionPerformed(evt);
            }
        });
        searchMenu.add(searchMenuItem);

        mainMenuBar.add(searchMenu);

        exportMenu.setText("Export");

        exportCrewMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_E, java.awt.event.InputEvent.CTRL_MASK));
        exportCrewMenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sttcollectionmanager/resources/export3.png"))); // NOI18N
        exportCrewMenuItem.setText("Export All Crew");
        exportCrewMenuItem.setToolTipText("Export Crew table");
        exportCrewMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exportCrewMenuItemActionPerformed(evt);
            }
        });
        exportMenu.add(exportCrewMenuItem);

        exportCollectionMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_W, java.awt.event.InputEvent.CTRL_MASK));
        exportCollectionMenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sttcollectionmanager/resources/export3.png"))); // NOI18N
        exportCollectionMenuItem.setText("Export Crew Collection");
        exportCollectionMenuItem.setToolTipText("Export Crew Collection Table");
        exportCollectionMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exportCollectionMenuItemActionPerformed(evt);
            }
        });
        exportMenu.add(exportCollectionMenuItem);

        mainMenuBar.add(exportMenu);

        skinMenu.setText("Skin");
        mainMenuBar.add(skinMenu);

        textSizeMenu.setText("Font");

        increaseTextSizeMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_PLUS, java.awt.event.InputEvent.CTRL_MASK));
        increaseTextSizeMenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sttcollectionmanager/resources/zoomIn.png"))); // NOI18N
        increaseTextSizeMenuItem.setText("Increase Font Size");
        increaseTextSizeMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                increaseTextSizeMenuItemActionPerformed(evt);
            }
        });
        textSizeMenu.add(increaseTextSizeMenuItem);

        decreaseTextSizeMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_MINUS, java.awt.event.InputEvent.CTRL_MASK));
        decreaseTextSizeMenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sttcollectionmanager/resources/zoomOut.png"))); // NOI18N
        decreaseTextSizeMenuItem.setText("Decrease Font Size");
        decreaseTextSizeMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                decreaseTextSizeMenuItemActionPerformed(evt);
            }
        });
        textSizeMenu.add(decreaseTextSizeMenuItem);

        resetTextSizeMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_0, java.awt.event.InputEvent.CTRL_MASK));
        resetTextSizeMenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sttcollectionmanager/resources/resetZoom.png"))); // NOI18N
        resetTextSizeMenuItem.setText("Reset Font Size");
        resetTextSizeMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetTextSizeMenuItemActionPerformed(evt);
            }
        });
        textSizeMenu.add(resetTextSizeMenuItem);

        changeFontMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_G, java.awt.event.InputEvent.CTRL_MASK));
        changeFontMenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sttcollectionmanager/resources/font2.png"))); // NOI18N
        changeFontMenuItem.setText("Change Font");
        changeFontMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                changeFontMenuItemActionPerformed(evt);
            }
        });
        textSizeMenu.add(changeFontMenuItem);

        mainMenuBar.add(textSizeMenu);

        helpMenu.setText("Help");

        helpMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_H, java.awt.event.InputEvent.CTRL_MASK));
        helpMenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sttcollectionmanager/resources/help2.png"))); // NOI18N
        helpMenuItem.setText("Help");
        helpMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                helpMenuItemActionPerformed(evt);
            }
        });
        helpMenu.add(helpMenuItem);

        infoMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_I, java.awt.event.InputEvent.CTRL_MASK));
        infoMenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sttcollectionmanager/resources/info.png"))); // NOI18N
        infoMenuItem.setText("Info");
        infoMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                infoMenuItemActionPerformed(evt);
            }
        });
        helpMenu.add(infoMenuItem);

        aboutMenuItem.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.CTRL_MASK));
        aboutMenuItem.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sttcollectionmanager/resources/about.png"))); // NOI18N
        aboutMenuItem.setText("About");
        aboutMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                aboutMenuItemActionPerformed(evt);
            }
        });
        helpMenu.add(aboutMenuItem);

        mainMenuBar.add(helpMenu);

        setJMenuBar(mainMenuBar);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void rebuildMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rebuildMenuItemActionPerformed
        buildDB();
        fillCrewTable("no search", null);
        refreshStats=true;
        refreshCharts=true;
        try {
            if(crewPieChartWindow.getFrame().isShowing())
                refreshCrewPieChart("window");
            //refreshCrewCollectionPieChart();
            if(crewBarChartWindow.getFrame().isShowing())
                refreshCrewBarChart("window");
            if(crewStackedBarChartWindow.getFrame().isShowing())
                refreshCrewStackedBarChart("window");
        } catch (Exception e) {}
    }//GEN-LAST:event_rebuildMenuItemActionPerformed

    private void exportCrewMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exportCrewMenuItemActionPerformed
        exportTable(crewTable,"STT All Crew");
    }//GEN-LAST:event_exportCrewMenuItemActionPerformed

    private void exportCollectionMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exportCollectionMenuItemActionPerformed
        exportTable(crewCollectionTable,"STT Crew Collection");
    }//GEN-LAST:event_exportCollectionMenuItemActionPerformed

    private void aboutMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aboutMenuItemActionPerformed
        new AboutJFrame().setVisible(true);
    }//GEN-LAST:event_aboutMenuItemActionPerformed

    private void infoMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_infoMenuItemActionPerformed
        try {
            SQLConnectionBridge sqlConnectionBridge = new SQLConnectionBridge(jdbcUrl,user,password,sttCollectionManager);
            DatabaseMetaData meta = sqlConnectionBridge.getMeta();
            String info="<HTML>"
                    + "<p><p><b>Database product name:</b> "+meta.getDatabaseProductName()
                    +"<p><p><b>Database product version:</b> "+meta.getDatabaseProductVersion()
                    +"<p><p><b>JDBC Driver name:</b> "+meta.getDriverName()
                    +"<p><p><b>JDBC Driver version:</b> "+meta.getDriverVersion()
                    +"<p><p><b>Database URL:</b> "+meta.getURL()
                    +"<p><p><b>Username:</b> "+meta.getUserName()
                    +"<p><p><b>Java vendor:</b> "+System.getProperty("java.vendor")
                    +"<p><p><b>Java vendor url:</b> "+System.getProperty("java.vendor.url")
                    +"<p><p><b>Java version:</b> "+System.getProperty("java.version")
                    +"<p><p><b>JavaFX runtime version:</b> " + System.getProperties().get("javafx.runtime.version")
                    +"<p><p><b>Operating system architecture:</b> "+System.getProperty("os.arch")
                    +"<p><p><b>Operating system name:</b> "+System.getProperty("os.name")
                    +"<p><p><b>Operating system version:</b> "+System.getProperty("os.version")
                    +"<p><p><b>User working directory:</b> "+System.getProperty("user.dir")
                    +"</HTML>";
            System.out.println(info);
            JOptionPane.showMessageDialog(this,info,"Info",1);
        }
        catch (Exception e) {
            JOptionPane.showMessageDialog(this,"No_connection","Error",0);
        }
    }//GEN-LAST:event_infoMenuItemActionPerformed

    private void helpMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_helpMenuItemActionPerformed
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
                    Double width = screenSize.getWidth()/4.0;

                    JDialog helpDialog = new JDialog(this, "STT Collection Manager Help", false);
                    helpDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                    
                    HelpJPanel helpPanel = new HelpJPanel();
                    helpDialog.getContentPane().add(helpPanel);
                    helpDialog.setSize(960, 400);
                    helpDialog.setLocation(width.intValue(), 0);
                    helpDialog.pack();
                    helpDialog.setVisible(true);
    }//GEN-LAST:event_helpMenuItemActionPerformed

    private void exitMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitMenuItemActionPerformed
        this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
    }//GEN-LAST:event_exitMenuItemActionPerformed

    private void refreshMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshMenuItemActionPerformed
        java.util.List<? extends RowSorter.SortKey> crewSortkeys = crewTable.getRowSorter().getSortKeys();
        if(dialog==null || !dialog.isVisible())
            fillCrewTable("no search", null);
        crewTable.getRowSorter().setSortKeys(crewSortkeys);
        
        java.util.List<? extends RowSorter.SortKey> crewCollectionSortkeys = crewCollectionTable.getRowSorter().getSortKeys();
        if(dialog==null || !dialog.isVisible())
            fillCrewCollectionTable("no search", null);
        crewCollectionTable.getRowSorter().setSortKeys(crewCollectionSortkeys);
    }//GEN-LAST:event_refreshMenuItemActionPerformed

    private void increaseTextSizeMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_increaseTextSizeMenuItemActionPerformed
        //Font jTableFont = UIManager.getFont("JTable");
        Font crewTableFont = crewTable.getFont();
        String fontName = crewTableFont.getFontName();
        int jTableFontSize = crewTableFont.getSize();
        jTableFontSize+=1;
        crewTable.setFont(new Font(fontName, Font.PLAIN, jTableFontSize));
        crewCollectionTable.setFont(new Font(fontName, Font.PLAIN, jTableFontSize));
        crewTable.getTableHeader().setFont(new Font(fontName, Font.PLAIN, jTableFontSize));
        crewCollectionTable.getTableHeader().setFont(new Font(fontName, Font.PLAIN, jTableFontSize));
        updateRowHeights(crewTable);
        updateRowHeights(crewCollectionTable);
        resizeColumnWidth(crewTable);
        resizeColumnWidth(crewCollectionTable);
        
        SQLConnectionBridge sqlConnectionBridge = new SQLConnectionBridge(jdbcUrl,user,password,sttCollectionManager);
        String SQL="UPDATE STTCollectionManager.Settings SET value='"+jTableFontSize+"' WHERE Settings.setting='fontSize'";
        sqlConnectionBridge.update(SQL);
        //UIManager.put("JTable.font", new FontUIResource(new Font("Tahoma", Font.PLAIN, jTableFontSize)));
        //UIManager.put("Label.font", new FontUIResource(new Font("Dialog", Font.PLAIN, 10)));
        //UIManager.put("Button.font", new FontUIResource(new Font("Dialog", Font.BOLD, 10)));
        //UIManager.put("TextField.font", new FontUIResource(new Font("Dialog", Font.PLAIN, 10)));
    }//GEN-LAST:event_increaseTextSizeMenuItemActionPerformed

    private void decreaseTextSizeMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_decreaseTextSizeMenuItemActionPerformed
        //Font jTableFont = UIManager.getFont("JTable");
        Font crewTableFont = crewTable.getFont();
        String fontName = crewTableFont.getFontName();
        int jTableFontSize = crewTableFont.getSize();

        jTableFontSize-=1;
        if(jTableFontSize>1) {
            crewTable.setFont(new Font(fontName, Font.PLAIN, jTableFontSize));
            crewCollectionTable.setFont(new Font(fontName, Font.PLAIN, jTableFontSize));
            crewTable.getTableHeader().setFont(new Font(fontName, Font.PLAIN, jTableFontSize));
            crewCollectionTable.getTableHeader().setFont(new Font(fontName, Font.PLAIN, jTableFontSize));
            updateRowHeights(crewTable);
            updateRowHeights(crewCollectionTable);
            resizeColumnWidth(crewTable);
            resizeColumnWidth(crewCollectionTable);
            
            SQLConnectionBridge sqlConnectionBridge = new SQLConnectionBridge(jdbcUrl,user,password,sttCollectionManager);
            String SQL="UPDATE STTCollectionManager.Settings SET value='"+jTableFontSize+"' WHERE Settings.setting='fontSize'";
            sqlConnectionBridge.update(SQL);
        }
        
        //UIManager.put("JTable.font", new FontUIResource(new Font("Tahoma", Font.PLAIN, jTableFontSize)));
        //UIManager.put("Label.font", new FontUIResource(new Font("Dialog", Font.PLAIN, 10)));
        //UIManager.put("Button.font", new FontUIResource(new Font("Dialog", Font.BOLD, 10)));
        //UIManager.put("TextField.font", new FontUIResource(new Font("Dialog", Font.PLAIN, 10)));
        //if(jTableFontSize>1)
        //    UIManager.put("JTable.font", new FontUIResource(new Font("Tahoma", Font.PLAIN, jTableFontSize)));
    }//GEN-LAST:event_decreaseTextSizeMenuItemActionPerformed

    private void resetTextSizeMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetTextSizeMenuItemActionPerformed
        Font crewTableFont = crewTable.getFont();
        String fontName = crewTableFont.getFontName();
        crewTable.setFont(new Font(fontName, Font.PLAIN, 12));
        crewCollectionTable.setFont(new Font(fontName, Font.PLAIN, 12));
        crewTable.getTableHeader().setFont(new Font(fontName, Font.PLAIN, 12));
        crewCollectionTable.getTableHeader().setFont(new Font(fontName, Font.PLAIN, 12));
        updateRowHeights(crewTable);
        updateRowHeights(crewCollectionTable);
        resizeColumnWidth(crewTable);
        resizeColumnWidth(crewCollectionTable);
        
        SQLConnectionBridge sqlConnectionBridge = new SQLConnectionBridge(jdbcUrl,user,password,sttCollectionManager);
        String SQL="UPDATE STTCollectionManager.Settings SET value='12' WHERE Settings.setting='fontSize'";
        sqlConnectionBridge.update(SQL);
        //UIManager.put("JTable.font", new FontUIResource(new Font("Tahoma", Font.PLAIN, 11)));
    }//GEN-LAST:event_resetTextSizeMenuItemActionPerformed

    private void mainTabbedPaneStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_mainTabbedPaneStateChanged
        JTabbedPane sourceTabbedPane = (JTabbedPane) evt.getSource();
        int index = sourceTabbedPane.getSelectedIndex();
        
        switch(sourceTabbedPane.getSelectedIndex()) {
            case 0: {
                if (dialog!=null && dialog.isVisible()) {
                    dialog.dispose();
                    searchMenuItemActionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null));
                }
                /*
                if(refreshTables) {
                    refreshMenuItemActionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null));
                    refreshTables=false;
                }*/ break;
            }
            case 1: {
                if(dialog!=null && dialog.isVisible()) {
                    dialog.dispose();
                    searchMenuItemActionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null));
                }
                //fillCrewTable("no search", null);
                /*
                if(refreshTables) {
                    refreshMenuItemActionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null));
                    refreshTables=false;
                }*/ break;
            }
            case 2: {
                if(dialog!=null && dialog.isVisible())
                    dialog.dispose();
            }
            case 3: {
                if(dialog!=null && dialog.isVisible())
                    dialog.dispose();
                if(refreshCharts)  {
                    fillStats();
                    createCharts();
                    refreshCharts=false;
                }
            } break;
            case 4: {
                if(dialog!=null && dialog.isVisible())
                    dialog.dispose();
                if(refreshStats)  {
                    fillStats();
                    refreshStats=false;
                }
            } break;
            
            default: {
                if(dialog!=null && dialog.isVisible())
                    dialog.dispose();
            }
        }
    }//GEN-LAST:event_mainTabbedPaneStateChanged

    private void crewSlotsTextFieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_crewSlotsTextFieldKeyReleased
        SQLConnectionBridge sqlConnectionBridge = new SQLConnectionBridge(jdbcUrl,user,password,sttCollectionManager);
        String SQL="UPDATE STTCollectionManager.Settings SET value='"+crewSlotsTextField.getText()+"' WHERE Settings.setting='crewSlots'";
        sqlConnectionBridge.update(SQL);
        fillStats();
    }//GEN-LAST:event_crewSlotsTextFieldKeyReleased

    private void mainTabbedPaneMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mainTabbedPaneMouseEntered
        setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
    }//GEN-LAST:event_mainTabbedPaneMouseEntered

    private void searchMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_searchMenuItemActionPerformed
        SQLConnectionBridge sqlConnectionBridge = new SQLConnectionBridge(jdbcUrl,user,password,sttCollectionManager);                
        
        int selectedTab = mainTabbedPane.getSelectedIndex();

        switch(selectedTab) {
            case 0: {
                if(dialog==null || !dialog.isVisible()) {
                    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
                    Double width = screenSize.getWidth()/4.0;

                    dialog = new JDialog(this, "Search Crew Collection", false);
                    SearchJPanel searchPanel = new SearchJPanel(crewCollectionTable, sqlConnectionBridge, sttCollectionManager, dialog);
                    dialog.getContentPane().add(searchPanel);
                    dialog.setSize(640, 400);
                    dialog.setLocation(width.intValue(), 0);
                    dialog.pack();
                    dialog.setVisible(true);
                }
            } break;
            case 1: {
                if(dialog==null || !dialog.isVisible()) {
                    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
                    Double width = screenSize.getWidth()/4.0;

                    dialog = new JDialog(this, "Search All Crew", false);
                    SearchJPanel searchPanel = new SearchJPanel(crewTable, sqlConnectionBridge, sttCollectionManager, dialog);
                    dialog.getContentPane().add(searchPanel);
                    dialog.setSize(640, 400);
                    dialog.setLocation(width.intValue(), 0);
                    dialog.pack();
                    dialog.setVisible(true);
                }
            } break;
        }
    }//GEN-LAST:event_searchMenuItemActionPerformed

    private void changeFontMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_changeFontMenuItemActionPerformed
        JFontChooser fontChooser = new JFontChooser();
        fontChooser.showDialog(crewTable);
        //fontChooser.showDialog(crewCollectionTable);
        crewTable.setFont(fontChooser.getSelectedFont());
        crewTable.getTableHeader().setFont(fontChooser.getSelectedFont());
        crewCollectionTable.setFont(fontChooser.getSelectedFont());
        crewCollectionTable.getTableHeader().setFont(fontChooser.getSelectedFont());
        
        SQLConnectionBridge sqlConnectionBridge = new SQLConnectionBridge(jdbcUrl,user,password,sttCollectionManager);                
        String SQL="UPDATE STTCollectionManager.Settings SET value='"+crewTable.getFont().getFontName()+"' WHERE Settings.setting='font'";
        sqlConnectionBridge.update(SQL);
    }//GEN-LAST:event_changeFontMenuItemActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(STTCollectionManager.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(STTCollectionManager.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(STTCollectionManager.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(STTCollectionManager.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new STTCollectionManager().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem aboutMenuItem;
    private javax.swing.JLabel averageLevelValueLabel;
    private javax.swing.JMenuItem changeFontMenuItem;
    private javax.swing.JPanel chartsPanel;
    private javax.swing.JPanel collectionPanel;
    private javax.swing.JScrollPane collectionScrollPane;
    private javax.swing.JTable crewCollectionTable;
    private javax.swing.JPanel crewPanel;
    private javax.swing.JScrollPane crewScrollPane;
    private javax.swing.JTextField crewSlotsTextField;
    private javax.swing.JTable crewTable;
    private javax.swing.JMenuItem decreaseTextSizeMenuItem;
    private javax.swing.JMenuItem exitMenuItem;
    private javax.swing.JMenuItem exportCollectionMenuItem;
    private javax.swing.JMenuItem exportCrewMenuItem;
    private javax.swing.JMenu exportMenu;
    private javax.swing.JMenu fileMenu;
    private javax.swing.JLabel fullyEquippedValueLabel;
    private javax.swing.JMenu helpMenu;
    private javax.swing.JMenuItem helpMenuItem;
    private javax.swing.JLabel inVaultValueLabel;
    private javax.swing.JMenuItem increaseTextSizeMenuItem;
    private javax.swing.JMenuItem infoMenuItem;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JPanel logPanel;
    private javax.swing.JScrollPane logScrollPane;
    private javax.swing.JTextArea logTextArea;
    private javax.swing.JMenuBar mainMenuBar;
    private javax.swing.JTabbedPane mainTabbedPane;
    private javax.swing.JMenuItem rebuildMenuItem;
    private javax.swing.JMenuItem refreshMenuItem;
    private javax.swing.JMenuItem resetTextSizeMenuItem;
    private javax.swing.JMenu searchMenu;
    private javax.swing.JMenuItem searchMenuItem;
    private javax.swing.JMenu skinMenu;
    private javax.swing.JLabel slotsUsedLabel;
    private javax.swing.JLabel slotsUsedValueLabel;
    private javax.swing.JLabel star1ValueLabel;
    private javax.swing.JLabel star2ValueLabel;
    private javax.swing.JLabel star3ValueLabel;
    private javax.swing.JLabel star4ValueLabel;
    private javax.swing.JLabel star5ValueLabel;
    private javax.swing.JPanel statsPanel;
    private javax.swing.JScrollPane statsScrollPane;
    private javax.swing.JPanel statsScrollablePanel;
    private javax.swing.JMenu textSizeMenu;
    // End of variables declaration//GEN-END:variables
    private DBConnection conn;
    private String protocol="jdbc";
    private String driver="derby";
    private String host="localhost";
    private String port="1527";
    private String db="STTCollectionManager";
    private String user="STTCollectionManager";
    private String password="STTCollectionManager";
    private JdbcUrl jdbcUrl = new JdbcUrl(protocol,driver,host,port,db);
    private STTCollectionManager sttCollectionManager;
    private boolean showDisclaimer=true;
    private boolean disclaimerAccepted=false;
    //private SettingsManager settings = new SettingsManager();
    private UIManager.LookAndFeelInfo lafInfo[]=UIManager.getInstalledLookAndFeels();
    
    private DefaultTableModel crewSlotsSettingModel;
    private DefaultTableModel star5CrewModel;
    private DefaultTableModel star4CrewModel;
    private DefaultTableModel star3CrewModel;
    private DefaultTableModel star2CrewModel;
    private DefaultTableModel star1CrewModel;
    private DefaultTableModel star5CollectionModel;
    private DefaultTableModel star4CollectionModel;
    private DefaultTableModel star3CollectionModel;
    private DefaultTableModel star2CollectionModel;
    private DefaultTableModel star1CollectionModel;
    private DefaultTableModel fullyEquippedCrewModel;
    private DefaultTableModel inVaultCrewModel;
    
    private PieChartSTT crewPieChart;
    private PieChartSTT crewCollectionPieChart;
    private BarChartSTT crewBarChart;
    private StackedBarChartSTT crewStackedBarChart;
    private PieChartSTT crewPieChartWindow;
    private PieChartSTT crewCollectionPieChartWindow;
    private BarChartSTT crewBarChartWindow;
    private StackedBarChartSTT crewStackedBarChartWindow;
    
    private JFXPanel crewPieChartJfx = new JFXPanel();
    private JFXPanel crewCollectionPieChartJfx = new JFXPanel();
    private JFXPanel crewBarChartJfx = new JFXPanel();
    private JFXPanel crewStackedBarChartJfx = new JFXPanel();
    
    //private Boolean refreshTables=false;
    private Boolean refreshCharts=false;
    private Boolean refreshStats=false;
    
    JDialog dialog;
    //################################## Methods #################################

    public String todayNow() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd\tHH:mm:ss:SSS");
        Date date = new Date();
        return dateFormat.format(date);
    }

    public JTextArea getLogTextArea() {
        return logTextArea;
    }

    public void updateUI() {
        SwingUtilities.updateComponentTreeUI(this);
        int maximized=this.getExtendedState();
        Dimension dimension = new Dimension(this.getSize());
        if (maximized==0) {
            this.setMinimumSize(dimension);
            this.pack();
            this.setSize(dimension);
            this.setMinimumSize(null);
        }
        else
            this.setExtendedState(this.getExtendedState() | JFrame.MAXIMIZED_BOTH);
    }
    
    private void exportTable(JTable table, String title) {
        JFileChooser fc = new JFileChooser();
        FileFilter filter = new FileFilter() {

            @Override
            public boolean accept(File f) {
                if(f.getName().endsWith(".html"))
                    return true;
                else
                    return false;
            }

            @Override
            public String getDescription() {
                    return "HTML Files";
            }
        };
        fc.addChoosableFileFilter(filter);
        filter = new FileFilter() {

            @Override
            public boolean accept(File f) {
                if(f.getName().endsWith(".xls"))
                    return true;
                else
                    return false;
            }

            @Override
            public String getDescription() {
                    return "Excel Files";
            }
        };
        fc.addChoosableFileFilter(filter);

        filter = new FileFilter() {

            @Override
            public boolean accept(File f) {
                if(f.getName().endsWith(".txt"))
                    return true;
                else
                    return false;
            }

            @Override
            public String getDescription() {
                    return "Text Files";
            }
        };
        fc.addChoosableFileFilter(filter);

        fc.setAcceptAllFileFilterUsed(false);

        fc.setDialogTitle("Export table data");

        String fileName = title;

        fc.setSelectedFile(new File(fileName));

        int returnVal = fc.showSaveDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {

            if(fc.getFileFilter().getDescription().startsWith("HTML")) {
                try {
                    File file = new File(fc.getSelectedFile().getAbsolutePath()+".html");
                    HTMLExporter html = new HTMLExporter();
                    html.exportTable(table,file,title);
                    JOptionPane.showMessageDialog(this, "Table exported successfully to "+file.getName()+"!", "Information",1);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(this, "Export Error!\n"+ex.getMessage(), "Error",0);
                    System.out.println(ex.getMessage());
                }
            }
            else if(fc.getFileFilter().getDescription().startsWith("Excel")) {
                try {
                    File file = new File(fc.getSelectedFile().getAbsolutePath()+".xls");
                    ExcelExporter exp = new ExcelExporter();
                    exp.exportTable(table,file,title);
                    JOptionPane.showMessageDialog(this, "Table exported successfully to "+file.getName()+"!", "Information",1);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(this, "Export Error!\n"+ex.getMessage(), "Error",0);
                    System.out.println(ex.getMessage());
                }
            }
            else if (fc.getFileFilter().getDescription().startsWith("Text")) {
                try {
                    File file = new File(fc.getSelectedFile().getAbsolutePath()+".txt");
                    TextExporter txt = new TextExporter();
                    txt.exportTable(table,file,title);
                    JOptionPane.showMessageDialog(this, "Table exported successfully to "+file.getName()+"!", "Information",1);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(this, "Export Error!\n"+ex.getMessage(), "Error",0);
                    System.out.println(ex.getMessage());
                }
            }
        }
    }

    private void createGUI() {
        for(int i=0; i<lafInfo.length; i++) {
            skinMenu.add(lafInfo[i].getName());
            Icon icon = new ImageIcon(this.getClass().getResource("resources/skin2.png"));
            skinMenu.getItem(i).setIcon(icon);
            skinMenu.getItem(i).addActionListener(new skinMenuActionListener());
        }           
    }

    /**
     * @return the showDisclaimer
     */
    public boolean isShowDisclaimer() {
        return showDisclaimer;
    }

    /**
     * @param showDisclaimer the showDisclaimer to set
     */
    public void setShowDisclaimer(boolean showDisclaimer) {
        this.showDisclaimer = showDisclaimer;
    }

    /**
     * @return the disclaimerAccepted
     */
    public boolean isDisclaimerAccepted() {
        return disclaimerAccepted;
    }

    /**
     * @param disclaimerAccepted the disclaimerAccepted to set
     */
    public void setDisclaimerAccepted(boolean disclaimerAccepted) {
        this.disclaimerAccepted = disclaimerAccepted;
    }

//################################## Listeners #################################

    class skinMenuActionListener implements ActionListener {
        skinMenuActionListener() {
        }
        public void actionPerformed(ActionEvent e) {
            for(int i=0; i<skinMenu.getItemCount(); i++) {
                if(e.getSource() == skinMenu.getItem(i)) {
                    String skin=lafInfo[i].getName();
                    setSkin(skin);
                    SQLConnectionBridge sqlConnectionBridge = new SQLConnectionBridge(jdbcUrl,user,password,sttCollectionManager);
                    String SQL="UPDATE STTCollectionManager.Settings SET value='"+skin+"' WHERE Settings.setting='skin'";
                    sqlConnectionBridge.update(SQL);
                }
            }
        }
    }
    
    class tableDataListener implements TableModelListener {

        @Override
        public void tableChanged(TableModelEvent e) {
            SQLConnectionBridge sqlConnectionBridge = new SQLConnectionBridge(jdbcUrl,user,password,sttCollectionManager);
            int row = e.getFirstRow();
            int column = e.getColumn();

            DefaultTableModel model = (DefaultTableModel)e.getSource();
            
            int columnCount = model.getColumnCount();
            Boolean data = false;
            if(row>-1 && column>-1)
                data = (Boolean)model.getValueAt(row, columnCount-1);
            
            String[] dataRow=new String[columnCount-1];
 
            for (int i=0; i<columnCount-1; i++)
                dataRow[i]=(model.getValueAt(row,i).toString());
            
            if(columnCount==13) {
                dataRow[2] = "1";
                dataRow[3] = "1";
                dataRow[11] = "1";
            }
            
            if(column==columnCount-1) {
                if(data) {
                    String SQL = "SELECT MAX (Collection.id) FROM STTCollectionManager.Collection";
                    DefaultTableModel maxIdModel = sqlConnectionBridge.query(SQL,"ID");
                    String id ="";
                    if(maxIdModel.getValueAt(0,0)!=null) {
                        id = Integer.toString(Integer.parseInt(maxIdModel.getValueAt(0,0).toString())+1);
                        if(Integer.parseInt(id)>0)
                            SQL = "ALTER TABLE Collection ALTER COLUMN id RESTART WITH "+id;
                        else
                            SQL = "ALTER TABLE Collection ALTER COLUMN id RESTART WITH "+1;
                    }
                    sqlConnectionBridge.update(SQL);
                    SQL="INSERT INTO STTCollectionManager.Collection(crewName, starsFused, level, fullyEquipped, inVault, cmd, dip, eng, med, sci, sec, quantity, incollection) VALUES ('"+dataRow[1].replace("'", "''")+"', '"+dataRow[2]+"', '"+dataRow[3]+"', 'false', 'false', '"+dataRow[4]+"', '"+dataRow[5]+"', '"+dataRow[6]+"', '"+dataRow[7]+"', '"+dataRow[8]+"', '"+dataRow[9]+"', '"+dataRow[11]+"', '"+data+"')";
                    sqlConnectionBridge.update(SQL);

                    dataChangedRefreshComponents();

                } else {
                    if(model.equals(crewCollectionTable.getModel())) {
                        String SQL="DELETE FROM STTCollectionManager.Collection WHERE "
                        +"Collection.crewName='"+dataRow[1].replace("'", "''")+"' AND Collection.id="+dataRow[0]+"";
                        sqlConnectionBridge.update(SQL);

                        dataChangedRefreshComponents();
                    
                    }
                    else {
                        fillCrewTable("no search", null); //"Disable" checked checkbox                     
                    }
                    
                } 
            }
            else {
                if(columnCount!=13) {
                    String SQL="UPDATE STTCollectionManager.Collection SET starsFused='"+dataRow[4]+"', level='"+dataRow[5]+"', fullyEquipped='"+dataRow[6]+"', inVault='"+dataRow[7]+"', cmd='"+dataRow[8]+"', dip='"+dataRow[9]+"', eng='"+dataRow[10]+"', med='"+dataRow[11]+"', sci='"+dataRow[12]+"', sec='"+dataRow[13]+"', quantity='"+dataRow[16]+"' WHERE "
                    +"Collection.crewName='"+dataRow[1]+"' AND Collection.id="+dataRow[0]+"";
                    sqlConnectionBridge.update(SQL);

                    ActionListener al= new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                            int selectedRow = crewCollectionTable.getSelectedRow();
                            refreshMenuItemActionPerformed(evt);
                            crewCollectionTable.setRowSelectionInterval(selectedRow, selectedRow);
                            refreshStats=true;
                        }
                    };
                    Timer timer = new Timer(0,al);
                    timer.setRepeats(false);
                    timer.start();
                }
            }
        }
    }
    
    public void buildDbSettings() {
        SQLConnectionBridge sqlConnectionBridge = new SQLConnectionBridge(jdbcUrl,user,password,sttCollectionManager);
        String SQL = "CREATE TABLE STTCollectionManager.Settings(\n" +
        "id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),\n" +
        "setting VARCHAR(20),\n" +
        "value VARCHAR(20),\n" +
        "PRIMARY KEY (setting)\n" +
        ")";
        sqlConnectionBridge.update(SQL);
        SQL="INSERT INTO STTCollectionManager.Settings(setting, value) VALUES ('disclaimerAccepted', 'false')";
        sqlConnectionBridge.update(SQL);
        SQL="INSERT INTO STTCollectionManager.Settings(setting, value) VALUES ('showDisclaimer', 'true')";
        sqlConnectionBridge.update(SQL);
        String skin = UIManager.getLookAndFeel().getName();
        SQL="INSERT INTO STTCollectionManager.Settings(setting, value) VALUES ('skin', '"+skin+"')";
        sqlConnectionBridge.update(SQL);
        Dimension dimension = new Dimension(this.getSize());
        SQL="INSERT INTO STTCollectionManager.Settings(setting, value) VALUES ('windowHeight', '"+dimension.getHeight()+"')";
        sqlConnectionBridge.update(SQL);
        SQL="INSERT INTO STTCollectionManager.Settings(setting, value) VALUES ('windowWidth', '"+dimension.getWidth()+"')";
        sqlConnectionBridge.update(SQL);
        SQL="INSERT INTO STTCollectionManager.Settings(setting, value) VALUES ('windowMaximized', 'false')";
        sqlConnectionBridge.update(SQL);
        SQL="INSERT INTO STTCollectionManager.Settings(setting, value) VALUES ('windowPositionX', '0.0')";
        sqlConnectionBridge.update(SQL);
        SQL="INSERT INTO STTCollectionManager.Settings(setting, value) VALUES ('windowPositionY', '0.0')";
        sqlConnectionBridge.update(SQL);
        SQL="INSERT INTO STTCollectionManager.Settings(setting, value) VALUES ('fontSize', '12')";
        sqlConnectionBridge.update(SQL);
        SQL="INSERT INTO STTCollectionManager.Settings(setting, value) VALUES ('font', 'SansSerif')";
        sqlConnectionBridge.update(SQL);
        SQL="INSERT INTO STTCollectionManager.Settings(setting, value) VALUES ('crewSlots', '0')";
        sqlConnectionBridge.update(SQL);
    }

    public void buildDB() {
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        SQLConnectionBridge sqlConnectionBridge = new SQLConnectionBridge(jdbcUrl,user,password,sttCollectionManager);
        
        String SQL = "CREATE SCHEMA STTCollectionManager";
        sqlConnectionBridge.update(SQL);
        
        SQL = "ALTER TABLE STTCollectionManager.Collection DROP CONSTRAINT collection_fk";
        sqlConnectionBridge.update(SQL);
        
        SQL = "DROP TABLE STTCollectionManager.Crew";
        sqlConnectionBridge.update(SQL);

        SQL = "CREATE TABLE STTCollectionManager.Crew(\n" +
        "id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),\n" +
        "crewName VARCHAR(50),\n" +
        "charName VARCHAR(50),\n" +
        "stars VARCHAR(2),\n" +
        "cmd VARCHAR(5),\n" +
        "dip VARCHAR(5),\n" +
        "eng VARCHAR(5),\n" +
        "med VARCHAR(5),\n" +
        "sci VARCHAR(5),\n" +
        "sec VARCHAR(5),\n" +
        "race VARCHAR(50),\n" +
        "traits VARCHAR(255),\n" +
        "PRIMARY KEY (crewName)\n" +
        ")";
        sqlConnectionBridge.update(SQL);

        SQL = "CREATE TABLE STTCollectionManager.Collection(\n" +
        "id INTEGER NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),\n" +
        "crewName VARCHAR(50),\n" +
        "starsFused VARCHAR(2),\n" +
        "level VARCHAR(3),\n" +
        "fullyEquipped BOOLEAN,\n" +
        "inVault BOOLEAN,\n" +
        "cmd VARCHAR(5),\n" +
        //"cmdMin VARCHAR(5),\n" +
        //"cmdMax VARCHAR(5),\n" +
        "dip VARCHAR(5),\n" +
        //"dipMin VARCHAR(5),\n" +
        //"dipMax VARCHAR(5),\n" +
        "eng VARCHAR(5),\n" +
        //"engMin VARCHAR(5),\n" +
        //"engMax VARCHAR(5),\n" +
        "med VARCHAR(5),\n" +
        //"medMin VARCHAR(5),\n" +
        //"medMax VARCHAR(5),\n" +
        "sci VARCHAR(5),\n" +
        //"sciMin VARCHAR(5),\n" +
        //"sciMax VARCHAR(5),\n" +
        "sec VARCHAR(5),\n" +
        //"secMin VARCHAR(5),\n" +
        //"secMax VARCHAR(5),\n" +
        "quantity VARCHAR(2),\n" +
        "inCollection BOOLEAN,\n" +
        "PRIMARY KEY (id)\n" +
        ")";
        sqlConnectionBridge.update(SQL);
               
        WikiParser crewParser = new WikiParser();
        LinkedList<CrewMember> crewList=crewParser.getCrewMembers();

        for (int i=0; i < crewList.size(); i++) {
            CrewMember crewMember = crewList.get(i);
            String crewName = crewMember.getCrewName().replace("'", "''");
            String charName = crewMember.getCharName().replace("'", "''");
            String stars = crewMember.getStars();
            if(stars.length()>2) stars="0";
            String cmd = crewMember.getCmd();
            String dip = crewMember.getDip();
            String eng = crewMember.getEng();
            String med = crewMember.getMed();
            String sci = crewMember.getSci();
            String sec = crewMember.getSec();
            String race = crewMember.getRace().replace("'", "''");
            String[] traits = crewMember.getTraits();
            String mergedTraits = "";

            int traitsLength=0;
            for (int p=0; p < traits.length; p++) {
                if (traits[p] != null)
                traitsLength++;
            }

            if (traits != null) {
                for (int p=0; p < traitsLength; p++) {
                    mergedTraits = mergedTraits.concat(traits[p]).replace("'", "''");
                    if (p<traitsLength-1)
                    mergedTraits = mergedTraits.concat(", ");
                }
            }

            SQL="INSERT INTO STTCollectionManager.Crew(crewName, charName, stars, cmd, dip, eng, med, sci, sec, race, traits) VALUES ('"+crewName+"', '"+charName+"', '"+stars+"', '"+cmd+"', '"+dip+"', '"+eng+"', '"+med+"', '"+sci+"', '"+sec+"', '"+race+"', '"+mergedTraits+"')";
            sqlConnectionBridge.update(SQL);
            
            logTextArea.append("Crew Name: " + crewMember.getCrewName() + "\n");
            logTextArea.append("Character Name: " + crewMember.getCharName() + "\n");
            logTextArea.append("Stars: " + crewMember.getStars() + "\n\n");

            try {
                if (!crewMember.getCmd().equals("0"))
                    logTextArea.append("CMD: " + crewMember.getCmd() + "\n");
                if (!crewMember.getDip().equals("0"))
                    logTextArea.append("DIP: " + crewMember.getDip() + "\n");
                if (!crewMember.getEng().equals("0"))
                    logTextArea.append("ENG: " + crewMember.getEng() + "\n");
                if (!crewMember.getMed().equals("0"))
                    logTextArea.append("MED: " + crewMember.getMed() + "\n");
                if (!crewMember.getSci().equals("0"))
                    logTextArea.append("SCI: " + crewMember.getSci() + "\n");
                if (!crewMember.getSec().equals("0"))
                    logTextArea.append("SEC: " + crewMember.getSec() + "\n");
                logTextArea.append("\nRace: " + crewMember.getRace() + "\n");
                logTextArea.append("Traits: "+mergedTraits);

            } catch (Exception Ex) {

            }

            logTextArea.append("\n================================");
        }
        SQL = "ALTER TABLE STTCollectionManager.Collection ADD CONSTRAINT collection_fk\n" +
        "FOREIGN KEY (crewName) REFERENCES STTCollectionManager.Crew(crewName)";
        sqlConnectionBridge.update(SQL);
        setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
    }
    
    class IntComparator implements Comparator {
        @Override
        public int compare(Object o1, Object o2) {
            String regex = "\\d+";
            Integer int1;
            Integer int2;
            Pattern pattern = Pattern.compile(regex);
            if(pattern.matcher(o1.toString()).matches() && pattern.matcher(o2.toString()).matches()) {
                int1 = Integer.parseInt(o1.toString());
                int2 = Integer.parseInt(o2.toString());
            }
            else {
                int1 = Character.getNumericValue(o1.toString().charAt(0));
                int2 = Character.getNumericValue(o2.toString().charAt(0));
            }
            return int1.compareTo(int2);
        }

        @Override
        public boolean equals(Object o2) {
            return this.equals(o2);
        }
    }
    
    class StringComparator implements Comparator {
        @Override
        public int compare(Object o1, Object o2) {
            String str1;
            String str2;
            str1 = o1.toString();
            str2 = o2.toString();
            return str1.compareTo(str2);
        }

        @Override
        public boolean equals(Object o2) {
            return this.equals(o2);
        }
    }
    
    
    public void fillCrewTable(String search, DefaultTableModel searchModel) {
        DefaultTableModel model = new DefaultTableModel() {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.Boolean.class
            };

            @Override
            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
            
            @Override
            public boolean isCellEditable(int row, int column) {
                switch(column){             
                    case 0: return false;
                    case 1: return false;
                    case 2: return false;
                    case 3: return false;

                    default: return false;
                }
            }
        };
        
        crewTable.setModel(model);

        SQLConnectionBridge sqlConnectionBridge = new SQLConnectionBridge(jdbcUrl,user,password,sttCollectionManager);

        model.addColumn("ID");
        model.addColumn("CrewName");
        model.addColumn("CharName");
        model.addColumn("Stars");
        model.addColumn("CMD");
        model.addColumn("DIP");
        model.addColumn("ENG");
        model.addColumn("MED");
        model.addColumn("SCI");
        model.addColumn("SEC");
        model.addColumn("Race");
        model.addColumn("Traits");
        model.addColumn("In Collection");
        int columns=model.getColumnCount();
        String[] columnNames = new String[columns];
        for(int i=0; i<columns; i++)
            columnNames[i]=model.getColumnName(i);
        

        try {
            if(search.equals("no search")) {
                String SQL="SELECT DISTINCT Crew.*,Collection.inCollection " +
                           "FROM STTCollectionManager.Crew "
                         + "LEFT OUTER JOIN STTCollectionManager.Collection "
                         + "ON Crew.CrewName = Collection.CrewName";
                crewTable.setModel(sqlConnectionBridge.query(SQL, columnNames));
                
                if(crewTable.getModel().getRowCount()==0 && dialog==null) {
                    JOptionPane.showMessageDialog(this, "No crew data found. Crew data will be fetched and afterwards the application window will be shown, it may take a few seconds!");
                    buildDB();
                    fillCrewTable("no search", null);
                    /*
                    Exception e = new Exception("No Database");
                    return e;
                    */
                }
            } else if(search.equals("search")) {
                crewTable.setModel(searchModel);
            }

            TableColumn tc = crewTable.getColumnModel().getColumn(12);
            tc.setCellEditor(crewTable.getDefaultEditor(Boolean.class));
            tc.setCellRenderer(crewTable.getDefaultRenderer(Boolean.class));
   
            crewTable.setAutoCreateRowSorter(false);

            TableRowSorter<TableModel> trs = new TableRowSorter<TableModel>(crewTable.getModel());
            trs.setComparator(0, new IntComparator());
            trs.setComparator(3, new IntComparator());
            trs.setComparator(4, new IntComparator());
            trs.setComparator(5, new IntComparator());
            trs.setComparator(6, new IntComparator());
            trs.setComparator(7, new IntComparator());
            trs.setComparator(8, new IntComparator());
            trs.setComparator(9, new IntComparator());

            crewTable.setRowSorter(trs);
            crewTable.getRowSorter().toggleSortOrder(3);
            
            updateRowHeights(crewTable);
            resizeColumnWidth(crewTable);
            
            crewTable.getModel().addTableModelListener(new tableDataListener());
            
        } catch (Exception e) {     
            JOptionPane.showMessageDialog(this, "crewTable Exception: " + e.getMessage());
            //return e;
        }
        /*
        Exception e = new Exception("Ok");
        return e;
        */
   }

    public Exception fillCrewCollectionTable(String search, DefaultTableModel searchModel) {
        DefaultTableModel model = new DefaultTableModel(){
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Boolean.class, java.lang.Boolean.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.Boolean.class
            };

            @Override
            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
            
            @Override
            public boolean isCellEditable(int row, int column) {
                switch(column){             
                    case 0: return false;
                    case 1: return false;
                    case 2: return false;
                    case 3: return false;
                    case 14: return false;
                    case 15: return false;
                    default: return true;
                }
            }
        };
        
        crewCollectionTable.setModel(model);
        SQLConnectionBridge sqlConnectionBridge = new SQLConnectionBridge(jdbcUrl,user,password,sttCollectionManager);
        
        model.addColumn("ID");
        model.addColumn("CrewName");
        model.addColumn("CharName");
        model.addColumn("Stars");
        model.addColumn("Fused");
        model.addColumn("Level");
        model.addColumn("Fully Equipped");
        model.addColumn("In Vault");
        model.addColumn("CMD");
        //model.addColumn("MIN");
        //model.addColumn("MAX");
        model.addColumn("DIP");
        //model.addColumn("MIN");
        //model.addColumn("MAX");
        model.addColumn("ENG");
        //model.addColumn("MIN");
        //model.addColumn("MAX");
        model.addColumn("MED");
        //model.addColumn("MIN");
        //model.addColumn("MAX");
        model.addColumn("SCI");
        //model.addColumn("MIN");
        //model.addColumn("MAX");
        model.addColumn("SEC");
        //model.addColumn("MIN");
        //model.addColumn("MAX");
        model.addColumn("Race");
        model.addColumn("Traits");
        model.addColumn("Quantity");
        model.addColumn("In Collection");
        int columns=model.getColumnCount();
        String[] columnNames = new String[columns];
        for(int i=0; i<columns; i++)
            columnNames[i]=model.getColumnName(i);

        try {
            if(search.equals("no search")) {
                String SQL="SELECT Collection.ID, Crew.CrewName,Crew.CharName,Crew.Stars,Collection.starsFused,Collection.level,Collection.fullyEquipped,Collection.inVault,Collection.cmd,Collection.dip,Collection.eng,Collection.med,Collection.sci,Collection.sec,Crew.Race,Crew.Traits,Collection.Quantity,Collection.inCollection " +
                           "FROM STTCollectionManager.Crew " +
                           "INNER JOIN STTCollectionManager.Collection " +
                           "ON Crew.CrewName = Collection.CrewName";
                crewCollectionTable.setModel(sqlConnectionBridge.query(SQL, columnNames));
            } else if(search.equals("search")) {
                crewCollectionTable.setModel(searchModel);
            }
            
            TableColumn tc = crewCollectionTable.getColumnModel().getColumn(6);
            tc.setCellEditor(crewCollectionTable.getDefaultEditor(Boolean.class));
            tc.setCellRenderer(crewCollectionTable.getDefaultRenderer(Boolean.class));
            
            tc = crewCollectionTable.getColumnModel().getColumn(7);
            tc.setCellEditor(crewCollectionTable.getDefaultEditor(Boolean.class));
            tc.setCellRenderer(crewCollectionTable.getDefaultRenderer(Boolean.class));
            
            tc = crewCollectionTable.getColumnModel().getColumn(17);
            tc.setCellEditor(crewCollectionTable.getDefaultEditor(Boolean.class));
            tc.setCellRenderer(crewCollectionTable.getDefaultRenderer(Boolean.class));
            
            try {
                crewCollectionTable.setAutoCreateRowSorter(false);
                
                TableRowSorter<TableModel> trs = new TableRowSorter<TableModel>(crewCollectionTable.getModel());

                trs.setComparator(0, new IntComparator());
                //trs.setComparator(1, new StringComparator());
                //trs.setComparator(2, new StringComparator());
                trs.setComparator(3, new IntComparator());
                trs.setComparator(4, new IntComparator());
                trs.setComparator(5, new IntComparator());
                trs.setComparator(8, new IntComparator());
                trs.setComparator(9, new IntComparator());
                trs.setComparator(10, new IntComparator());
                trs.setComparator(11, new IntComparator());
                trs.setComparator(12, new IntComparator());
                trs.setComparator(13, new IntComparator());
                //trs.setComparator(14, new StringComparator());
                //trs.setComparator(15, new StringComparator());
                trs.setComparator(16, new IntComparator());

                crewCollectionTable.setRowSorter(trs);
                crewCollectionTable.getRowSorter().toggleSortOrder(3);
                
                updateRowHeights(crewCollectionTable);
                resizeColumnWidth(crewCollectionTable);
                
            }catch (Exception e) {
                JOptionPane.showMessageDialog(this, "crewCollectionTable RowSorter Exception: " + e.getMessage());
            return e;
            }

            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    new TableRightClick().initUI(sqlConnectionBridge,crewCollectionTable);
                }
            });
            
            crewCollectionTable.getModel().addTableModelListener(new tableDataListener());
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "crewCollectionTable Exception: " + e.getMessage());
            return e;
        }
        Exception e = new Exception("Ok");
        return e;
    }
    
    public void resizeColumnWidth(JTable table) {
        final TableColumnModel columnModel = table.getColumnModel();
        for (int column = 0; column < table.getColumnCount(); column++) {
            int width = 50; // Min width
            for (int row = 0; row < table.getRowCount(); row++) {
                TableCellRenderer renderer = table.getCellRenderer(row, column);
                Component comp = table.prepareRenderer(renderer, row, column);
                width = Math.max(comp.getPreferredSize().width +1 , width);
            }
            columnModel.getColumn(column).setPreferredWidth(width);
        }
    }
    
    public void updateRowHeights(JTable table) {
        for (int row = 0; row < table.getRowCount(); row++) {
            int rowHeight = table.getRowHeight();
            
            for (int column = 0; column < table.getColumnCount(); column++) {
                Component comp = table.prepareRenderer(table.getCellRenderer(row, column), row, column);
                rowHeight = Math.max(rowHeight, comp.getPreferredSize().height);
            }
            
            Double rheight;
            if (table.getFont().getSize()>8)
                rheight = rowHeight*table.getFont().getSize()*0.07;
            else
                rheight = rowHeight*9*0.07;
            
            rowHeight = rheight.intValue();
            
            table.setRowHeight(row, rowHeight);
        }
    }
    
    public void showDisclaimer() {
        JDialog dialog = new JDialog(this, "Disclaimer", true);
        dialog.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        DisclaimerJPanel disclaimer = new DisclaimerJPanel(sttCollectionManager,dialog);
        dialog.getContentPane().add(disclaimer);
        dialog.pack();
        dialog.setVisible(true);
        String accepted="";

        if(accepted.equals("false")) {
                WindowEvent windowClosing = new WindowEvent(this, WindowEvent.WINDOW_CLOSING);
                this.dispatchEvent(windowClosing);
        }
    }
    
    public void setSkin(Object skin) {
        for(int i=0; i<skinMenu.getItemCount(); i++) {
            if (skin instanceof ActionEvent) {
                if(((ActionEvent) skin).getSource() == skinMenu.getItem(i)) {
                    try {
                        UIManager.setLookAndFeel(lafInfo[i].getClassName());
                        break;
                    } catch (ClassNotFoundException ex) {
                    } catch (InstantiationException ex) {
                    } catch (IllegalAccessException ex) {
                    } catch (UnsupportedLookAndFeelException ex) {
                    } 
                }
            }
            if (skin instanceof String) {
                if(skin.equals(lafInfo[i].getName())) {
                    try {
                        UIManager.setLookAndFeel(lafInfo[i].getClassName());
                        break;
                    } catch (ClassNotFoundException ex) {
                    } catch (InstantiationException ex) {
                    } catch (IllegalAccessException ex) {
                    } catch (UnsupportedLookAndFeelException ex) {
                    } 
                }
            }  
        }
        updateUI();
    }
    
    public class TableRightClick {
        private JTable table;
        private SQLConnectionBridge sqlConnectionBridge;
        private int row;
        private int column;

        protected void initUI(SQLConnectionBridge sqlConnectionBridge, JTable table) {
            this.table = table;
            this.sqlConnectionBridge=sqlConnectionBridge;

            final JPopupMenu popupMenu = new JPopupMenu();
            JMenuItem deleteItem = new JMenuItem("Make copy");
            deleteItem.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {

                    DefaultTableModel model = (DefaultTableModel)table.getModel();

                    int columnCount = model.getColumnCount();

                    String[] dataRow=new String[columnCount-1];

                    for (int i=0; i<columnCount-1; i++)
                        dataRow[i]=(model.getValueAt(row,i).toString());

                    String SQL = "SELECT MAX (Collection.id) FROM STTCollectionManager.Collection";
                    DefaultTableModel maxIdModel = sqlConnectionBridge.query(SQL,"ID");
                    String id="";
                    if(maxIdModel.getValueAt(0,0)!=null) {
                        id = Integer.toString(Integer.parseInt(maxIdModel.getValueAt(0,0).toString())+1);
                        if(Integer.parseInt(id)>0)
                            SQL = "ALTER TABLE Collection ALTER COLUMN id RESTART WITH "+id;
                        else
                            SQL = "ALTER TABLE Collection ALTER COLUMN id RESTART WITH "+1;
                    }
                    sqlConnectionBridge.update(SQL);
                    SQL="INSERT INTO STTCollectionManager.Collection(crewName, starsFused, level, fullyEquipped, inVault, cmd, dip, eng, med, sci, sec, quantity, incollection) VALUES ('"+dataRow[1].replace("'", "''")+"', '1', '1', 'false', 'false', '"+dataRow[8]+"', '"+dataRow[9]+"', '"+dataRow[10]+"', '"+dataRow[11]+"', '"+dataRow[12]+"', '"+dataRow[13]+"', '"+dataRow[16]+"', 'true')";
                    sqlConnectionBridge.update(SQL);
   
                    dataChangedRefreshComponents();
                }
            });
            popupMenu.add(deleteItem);
            table.setComponentPopupMenu(popupMenu);
            
            popupMenu.addPopupMenuListener(new PopupMenuListener() {

                @Override
                public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            int rowAtPoint = table.rowAtPoint(SwingUtilities.convertPoint(popupMenu, new Point(0, 0), table));
                            if (rowAtPoint > -1) {
                                table.setRowSelectionInterval(rowAtPoint, rowAtPoint);
                                row=table.getSelectedRow();
                                column=table.getSelectedColumn();
                            }
                        }
                    });
                }

                @Override
                public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
                    // TODO Auto-generated method stub

                }

                @Override
                public void popupMenuCanceled(PopupMenuEvent e) {
                    // TODO Auto-generated method stub

                }
            });
        }
    }
    
    public void fillStats() {
        SQLConnectionBridge sqlConnectionBridge = new SQLConnectionBridge(jdbcUrl,user,password,sttCollectionManager);

        String SQL = "SELECT Settings.value FROM STTCollectionManager.Settings WHERE Settings.setting='crewSlots'";
        crewSlotsSettingModel = sqlConnectionBridge.query(SQL,"crewSlots");
        crewSlotsTextField.setText(crewSlotsSettingModel.getValueAt(0,0).toString());
        //slotsUsedLabel.setText("<html><b>Crew slots used:</b></html> "+crewCollectionTable.getRowCount()+"/"+crewSlotsTextField.getText());
        slotsUsedValueLabel.setText(crewCollectionTable.getRowCount()+"/"+crewSlotsTextField.getText());
        SQL = "SELECT COUNT(*) FROM STTCollectionManager.Crew WHERE Crew.stars='5'";
        star5CrewModel = sqlConnectionBridge.query(SQL,"stars");
        SQL = "SELECT COUNT(*) FROM STTCollectionManager.Crew, STTCollectionManager.Collection WHERE Crew.stars='5' AND Crew.crewName = Collection.crewName";
        star5CollectionModel = sqlConnectionBridge.query(SQL,"stars");
        try {
            //star5Label.setText("<html><b>5 Star crew:</b></html> "+star5CollectionModel.getValueAt(0,0).toString()+"/"+star5CrewModel.getValueAt(0,0).toString());
            star5ValueLabel.setText(star5CollectionModel.getValueAt(0,0).toString()+"/"+star5CrewModel.getValueAt(0,0).toString());
        } catch (Exception e) {
            //star5Label.setText("<html><b>5 Star crew:</b></html> 0/"+star5CrewModel.getValueAt(0,0).toString());
            star5ValueLabel.setText("0/"+star5CrewModel.getValueAt(0,0).toString());
        }
        SQL = "SELECT COUNT(*) FROM STTCollectionManager.Crew WHERE Crew.stars='4'";
        star4CrewModel = sqlConnectionBridge.query(SQL,"stars");
        SQL = "SELECT COUNT(*) FROM STTCollectionManager.Crew, STTCollectionManager.Collection WHERE Crew.stars='4' AND Crew.crewName = Collection.crewName";
        star4CollectionModel = sqlConnectionBridge.query(SQL,"stars");
        try {
            //star4Label.setText("<html><b>4 Star crew:</b></html> "+star4CollectionModel.getValueAt(0,0).toString()+"/"+star4CrewModel.getValueAt(0,0).toString());
            star4ValueLabel.setText(star4CollectionModel.getValueAt(0,0).toString()+"/"+star4CrewModel.getValueAt(0,0).toString());
        } catch (Exception e) {
            //star4Label.setText("<html><b>4 Star crew:</b></html> 0/"+star4CrewModel.getValueAt(0,0).toString());
            star4ValueLabel.setText("0/"+star4CrewModel.getValueAt(0,0).toString());
        }
        SQL = "SELECT COUNT(*) FROM STTCollectionManager.Crew WHERE Crew.stars='3'";
        star3CrewModel = sqlConnectionBridge.query(SQL,"stars");
        SQL = "SELECT COUNT(*) FROM STTCollectionManager.Crew, STTCollectionManager.Collection WHERE Crew.stars='3' AND Crew.crewName = Collection.crewName";
        star3CollectionModel = sqlConnectionBridge.query(SQL,"stars");
        try {
            //star3Label.setText("<html><b>3 Star crew:</b></html> "+star3CollectionModel.getValueAt(0,0).toString()+"/"+star3CrewModel.getValueAt(0,0).toString());
            star3ValueLabel.setText(star3CollectionModel.getValueAt(0,0).toString()+"/"+star3CrewModel.getValueAt(0,0).toString());
        } catch (Exception e) {
            //star3Label.setText("<html><b>3 Star crew:</b></html> 0/"+star3CrewModel.getValueAt(0,0).toString());
            star3ValueLabel.setText("0/"+star3CrewModel.getValueAt(0,0).toString());
        }
        SQL = "SELECT COUNT(*) FROM STTCollectionManager.Crew WHERE Crew.stars='2'";
        star2CrewModel = sqlConnectionBridge.query(SQL,"stars");
        SQL = "SELECT COUNT(*) FROM STTCollectionManager.Crew, STTCollectionManager.Collection WHERE Crew.stars='2' AND Crew.crewName = Collection.crewName";
        star2CollectionModel = sqlConnectionBridge.query(SQL,"stars");
        try {
            //star2Label.setText("<html><b>2 Star crew:</b></html> "+star2CollectionModel.getValueAt(0,0).toString()+"/"+star2CrewModel.getValueAt(0,0).toString());
            star2ValueLabel.setText(star2CollectionModel.getValueAt(0,0).toString()+"/"+star2CrewModel.getValueAt(0,0).toString());
        } catch (Exception e) {
            //star2Label.setText("<html><b>2 Star crew:</b></html> 0/"+star2CrewModel.getValueAt(0,0).toString());
            star2ValueLabel.setText("0/"+star2CrewModel.getValueAt(0,0).toString());
        }
        SQL = "SELECT COUNT(*) FROM STTCollectionManager.Crew WHERE Crew.stars='1'";
        star1CrewModel = sqlConnectionBridge.query(SQL,"stars");
        SQL = "SELECT COUNT(*) FROM STTCollectionManager.Crew, STTCollectionManager.Collection WHERE Crew.stars='1' AND Crew.crewName = Collection.crewName";
        star1CollectionModel = sqlConnectionBridge.query(SQL,"stars");
        try {
            //star1Label.setText("<html><b>1 Star crew:</b></html> "+star1CollectionModel.getValueAt(0,0).toString()+"/"+star1CrewModel.getValueAt(0,0).toString());
            star1ValueLabel.setText(star1CollectionModel.getValueAt(0,0).toString()+"/"+star1CrewModel.getValueAt(0,0).toString());
        } catch (Exception e) {
            //star1Label.setText("<html><b>1 Star crew:</b></html> 0/"+star1CrewModel.getValueAt(0,0).toString());
            star1ValueLabel.setText("0/"+star1CrewModel.getValueAt(0,0).toString());
        }
        SQL = "SELECT COUNT(*) FROM STTCollectionManager.Collection WHERE Collection.fullyEquipped='true'";
        fullyEquippedCrewModel = sqlConnectionBridge.query(SQL,"fullyEquipped");
        try {
            //fullyEquippedLabel.setText("<html><b>Fully equipped crew:</b></html> "+fullyEquippedCrewModel.getValueAt(0,0).toString()+"/"+crewCollectionTable.getRowCount());
            fullyEquippedValueLabel.setText(fullyEquippedCrewModel.getValueAt(0,0).toString()+"/"+crewCollectionTable.getRowCount());
        } catch (Exception e) {
            //fullyEquippedLabel.setText("<html><b>Fully equipped crew:</b></html> 0/"+crewCollectionTable.getRowCount());
            fullyEquippedValueLabel.setText("0/"+crewCollectionTable.getRowCount());
        }
        SQL = "SELECT COUNT(*) FROM STTCollectionManager.Collection WHERE Collection.inVault='true'";
        inVaultCrewModel = sqlConnectionBridge.query(SQL,"inVault");
        try {
            //inVaultLabel.setText("<html><b>In vault crew:</b></html> "+inVaultCrewModel.getValueAt(0,0).toString()+"/"+crewCollectionTable.getRowCount());
            inVaultValueLabel.setText(inVaultCrewModel.getValueAt(0,0).toString()+"/"+crewCollectionTable.getRowCount());
        } catch (Exception e) {
            //inVaultLabel.setText("<html><b>In vault crew:</b></html> 0/"+crewCollectionTable.getRowCount());
            inVaultValueLabel.setText("0/"+crewCollectionTable.getRowCount());
        }
        SQL = "SELECT AVG(Cast(Collection.level as Integer)) FROM STTCollectionManager.Collection";
        DefaultTableModel averageLevelCrewModel = sqlConnectionBridge.query(SQL,"averageLevel");
        
        try {
            //averageLevelLabel.setText("<html><b>Crew average level:</b></html> "+averageLevelCrewModel.getValueAt(0,0).toString());
            averageLevelValueLabel.setText(averageLevelCrewModel.getValueAt(0,0).toString());
        } catch (Exception e) {
            //averageLevelLabel.setText("<html><b>Crew average level:</b></html> 0");
            averageLevelValueLabel.setText("0");
        }
    }
    
    public void refreshCrewPieChart(String window) {
        ActionListener al= new java.awt.event.ActionListener() {
                            public void actionPerformed(java.awt.event.ActionEvent evt) {
                                String[] parameters = {star5CrewModel.getValueAt(0,0).toString(),star4CrewModel.getValueAt(0,0).toString(),star3CrewModel.getValueAt(0,0).toString(),star2CrewModel.getValueAt(0,0).toString(),star1CrewModel.getValueAt(0,0).toString()};
                                if(window.equals("window")) {
                                    try {
                                        crewPieChartWindow.getFrame().dispose();
                                        crewPieChartWindow = new PieChartSTT(parameters,"STT Collection Manager - All Crew Pie Chart");
                                        crewPieChartWindow.initAndShowGUI(window);
                                    } catch (Exception e) {
                                        crewPieChartWindow = new PieChartSTT(parameters,"STT Collection Manager - All Crew Pie Chart");
                                        crewPieChartWindow.initAndShowGUI(window);
                                    }
                                }
                                else {
                                    //createCharts();
                                    refreshStats=true;
                                    refreshCharts=true;
                                }
                            }
                        };
                        Timer timer = new Timer(0,al);
                        timer.setRepeats(false);
                        timer.start();
    }
    public void refreshCrewCollectionPieChart(String window) {
        ActionListener al= new java.awt.event.ActionListener() {
                            public void actionPerformed(java.awt.event.ActionEvent evt) {
                                String[] parameters = {star5CollectionModel.getValueAt(0,0).toString(),star4CollectionModel.getValueAt(0,0).toString(),star3CollectionModel.getValueAt(0,0).toString(),star2CollectionModel.getValueAt(0,0).toString(),star1CollectionModel.getValueAt(0,0).toString()};
                                if(window.equals("window")) {
                                    try {
                                        crewCollectionPieChartWindow.getFrame().dispose();
                                        crewCollectionPieChartWindow = new PieChartSTT(parameters,"STT Collection Manager - Crew Collection Pie Chart");
                                        crewCollectionPieChartWindow.initAndShowGUI(window);
                                    } catch (Exception e) {
                                        crewCollectionPieChartWindow = new PieChartSTT(parameters,"STT Collection Manager - Crew Collection Pie Chart");
                                        crewCollectionPieChartWindow.initAndShowGUI(window);
                                    }
                                }
                                else {
                                    //createCharts();
                                    refreshStats=true;
                                    refreshCharts=true;
                                }
                            }
                        };
                        Timer timer = new Timer(0,al);
                        timer.setRepeats(false);
                        timer.start();
    }
    public void refreshCrewBarChart(String window) {
        ActionListener al= new java.awt.event.ActionListener() {
                            public void actionPerformed(java.awt.event.ActionEvent evt) {
                                String[] parameters = {star5CrewModel.getValueAt(0,0).toString(),star4CrewModel.getValueAt(0,0).toString(),star3CrewModel.getValueAt(0,0).toString(),star2CrewModel.getValueAt(0,0).toString(),star1CrewModel.getValueAt(0,0).toString(),star5CollectionModel.getValueAt(0,0).toString(),star4CollectionModel.getValueAt(0,0).toString(),star3CollectionModel.getValueAt(0,0).toString(),star2CollectionModel.getValueAt(0,0).toString(),star1CollectionModel.getValueAt(0,0).toString()};
                                if(window.equals("window")) {
                                    try {
                                        crewBarChartWindow.getFrame().dispose();
                                        crewBarChartWindow = new BarChartSTT(parameters);
                                        crewBarChartWindow.initAndShowGUI(window);
                                    } catch (Exception e) {
                                        crewBarChartWindow = new BarChartSTT(parameters);
                                        crewBarChartWindow.initAndShowGUI(window);
                                    }
                                }
                                else {
                                    //createCharts();
                                    refreshStats=true;
                                    refreshCharts=true;
                                }
                            }
                        };
                        Timer timer = new Timer(0,al);
                        timer.setRepeats(false);
                        timer.start();
    }
    public void refreshCrewStackedBarChart(String window) {
        ActionListener al= new java.awt.event.ActionListener() {
                            public void actionPerformed(java.awt.event.ActionEvent evt) {
                                String[] parameters = {star5CrewModel.getValueAt(0,0).toString(),star4CrewModel.getValueAt(0,0).toString(),star3CrewModel.getValueAt(0,0).toString(),star2CrewModel.getValueAt(0,0).toString(),star1CrewModel.getValueAt(0,0).toString(),star5CollectionModel.getValueAt(0,0).toString(),star4CollectionModel.getValueAt(0,0).toString(),star3CollectionModel.getValueAt(0,0).toString(),star2CollectionModel.getValueAt(0,0).toString(),star1CollectionModel.getValueAt(0,0).toString()};
                                if(window.equals("window")) {
                                    try {
                                        crewStackedBarChartWindow.getFrame().dispose();
                                        crewStackedBarChartWindow = new StackedBarChartSTT(parameters);
                                        crewStackedBarChartWindow.initAndShowGUI(window);
                                    } catch (Exception e) {
                                        crewStackedBarChartWindow = new StackedBarChartSTT(parameters);
                                        crewStackedBarChartWindow.initAndShowGUI(window);
                                    }
                                }
                                else {
                                    //createCharts();
                                    refreshStats=true;
                                    refreshCharts=true;
                                }
                            }
                        };
                        Timer timer = new Timer(0,al);
                        timer.setRepeats(false);
                        timer.start();
    }
    
    public void createCharts() {
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 4; 
        c.gridy = 2;
        //c.gridwidth = 4;
        //c.gridheight = 10;
        c.fill = GridBagConstraints.BOTH;

        c.weightx = 0.5;
        c.weighty = 0.5;
        
        String[] parameters = {star5CrewModel.getValueAt(0,0).toString(),star4CrewModel.getValueAt(0,0).toString(),star3CrewModel.getValueAt(0,0).toString(),star2CrewModel.getValueAt(0,0).toString(),star1CrewModel.getValueAt(0,0).toString(),star5CollectionModel.getValueAt(0,0).toString(),star4CollectionModel.getValueAt(0,0).toString(),star3CollectionModel.getValueAt(0,0).toString(),star2CollectionModel.getValueAt(0,0).toString(),star1CollectionModel.getValueAt(0,0).toString()};
      
        crewPieChart = new PieChartSTT(parameters,"All Crew", crewPieChartJfx);
        crewPieChart.initAndShowGUI("no window");
        
        JFXPanel crewPieChartJfx = new JFXPanel();
        crewPieChartJfx = crewPieChart.getFxPanel();
        crewPieChartJfx.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                refreshCrewPieChart("window");
            }
        });
        
        c.gridx = 0;
        c.gridy = 0;
        chartsPanel.add(crewPieChartJfx,c);
        
        String[] collectionParameters = {star5CollectionModel.getValueAt(0,0).toString(),star4CollectionModel.getValueAt(0,0).toString(),star3CollectionModel.getValueAt(0,0).toString(),star2CollectionModel.getValueAt(0,0).toString(),star1CollectionModel.getValueAt(0,0).toString()};
        
        crewCollectionPieChart = new PieChartSTT(collectionParameters,"Crew Collection", crewCollectionPieChartJfx);
        crewCollectionPieChart.initAndShowGUI("no window");
        
        
        crewCollectionPieChartJfx = crewCollectionPieChart.getFxPanel();
        crewCollectionPieChartJfx.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                refreshCrewCollectionPieChart("window");
            }
        });
        c.gridx = 1;
        c.gridy = 0;
        chartsPanel.add(crewCollectionPieChartJfx,c);
      
        crewBarChart = new BarChartSTT(parameters, crewBarChartJfx);
        crewBarChart.initAndShowGUI("no window");
        
        c.weightx = 1.0;
        c.weighty = 1.0;
        
        JFXPanel crewBarChartJfxPanel = new JFXPanel();
        crewBarChartJfxPanel = crewBarChart.getFxPanel();
        crewBarChartJfxPanel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                refreshCrewBarChart("window");
            }
        });
        c.gridx = 0;
        c.gridy = 1;
        chartsPanel.add(crewBarChartJfxPanel,c);
       
        crewStackedBarChart = new StackedBarChartSTT(parameters, crewStackedBarChartJfx);
        crewStackedBarChart.initAndShowGUI("no window");
        
        JFXPanel crewStackedBarChartJfxPanel = new JFXPanel();
        crewStackedBarChartJfxPanel = crewStackedBarChart.getFxPanel();
        crewStackedBarChartJfxPanel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                refreshCrewStackedBarChart("window");
            }
        });
        c.gridx = 1;
        c.gridy = 1;
        chartsPanel.add(crewStackedBarChartJfxPanel,c);
    }
    
    public void dataChangedRefreshComponents() {
        ActionListener al= new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                            refreshMenuItemActionPerformed(evt);
                            
                            //refreshTables=true;
                            refreshStats=true;
                            refreshCharts=true;
                            
                            try {
                                if(crewCollectionPieChartWindow.getFrame().isShowing())
                                    refreshCrewCollectionPieChart("window");
                                if(crewBarChartWindow.getFrame().isShowing())
                                     refreshCrewBarChart("window");
                                if(crewStackedBarChartWindow.getFrame().isShowing())
                                    refreshCrewStackedBarChart("window");
                            } catch (Exception e) {}
                        }
                    };
                    Timer timer = new Timer(0,al);
                    timer.setRepeats(false);
                    timer.start();
    }
        
    public static void open(URL url) {
        if (Desktop.isDesktopSupported()) {
          try {
             Desktop.getDesktop().browse(new URI(url.toString()));
            } catch (IOException e) { /* TODO: error handling */ } catch (URISyntaxException ex) {
                Logger.getLogger(STTCollectionManager.class.getName()).log(Level.SEVERE, null, ex);
            }
         } else { /* TODO: error handling */ }
    }
    
    public int getMousePositionInTable(JTable table, MouseEvent e, int row, int col) {
        Point mousePt = new Point(e.getX(), e.getY());
        Rectangle rect = table.getCellRect(row, col, false);
        Point pt = rect.getLocation();
        Font crewTableFont = table.getFont();
        String fontName = crewTableFont.getFontName();
        int jTableFontSize = crewTableFont.getSize();

        int multiplier = jTableFontSize - 11;
        Double divider = 5*(Math.pow(1.07,multiplier));
        Double doublePos = mousePt.getX()-pt.getX();

        Double posDivided = Math.ceil(doublePos/divider);

        return posDivided.intValue();
    }
    
    public String getTrait(JTable table, int pos, int row, int col) {
        String trait = "";
                    
        int i=0;
        while((pos-i)>=0 && pos<table.getValueAt(row, col).toString().length() && table.getValueAt(row, col).toString().charAt(pos-i)!=',') {
            i++;
        }

        if((pos-i)<0)
            pos=0;
        else
            pos-=i-2;

        i=0;
        while(pos<table.getValueAt(row, col).toString().length() && table.getValueAt(row, col).toString().charAt(pos)!=',') {
            trait+=String.valueOf(table.getValueAt(row, col).toString().charAt(pos)).replace(" ", "");
            pos+=1;
        }
        return trait;
    }
    
    public void addTableMouseListener(JTable table) {
        table.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {

                int row = table.getSelectedRow();
                int col = table.getSelectedColumn();
                if(col==1) {

                    URL url = null;

                    try {
                        url = new URL("http://startrektimelineswiki.com/wiki/"+table.getValueAt(row, col).toString().replace(" ", "_").replace("\"", "%22"));
                    } catch (MalformedURLException ex) {
                        Logger.getLogger(STTCollectionManager.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    open(url);
                }
                if(table.getModel()==crewTable.getModel() && col==10) {

                    URL url = null;

                    try {
                        url = new URL("http://startrektimelineswiki.com/wiki/"+table.getValueAt(row, col).toString().replace(" ", "_").replace("\"", "%22"));
                    } catch (MalformedURLException ex) {
                        Logger.getLogger(STTCollectionManager.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    open(url);
                } else if(table.getModel()==crewCollectionTable.getModel() && col==14) {
                    URL url = null;

                    try {
                        url = new URL("http://startrektimelineswiki.com/wiki/"+table.getValueAt(row, col).toString().replace(" ", "_").replace("\"", "%22"));
                    } catch (MalformedURLException ex) {
                        Logger.getLogger(STTCollectionManager.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    open(url);
                }
                if(table.getModel()==crewTable.getModel() && col==11) {
                    
                    int pos = getMousePositionInTable(table, e, row, col);
                    
                    String trait = getTrait(table, pos, row, col);

                    URL url = null;

                    try {
                        url = new URL("http://startrektimelineswiki.com/wiki/"+trait.replace(" ", "_").replace("\"", "%22"));
                    } catch (MalformedURLException ex) {
                        Logger.getLogger(STTCollectionManager.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    open(url);
                } else if(table.getModel()==crewCollectionTable.getModel() && col==15) {
                    int pos = getMousePositionInTable(table, e, row, col);
                    
                    String trait = getTrait(table, pos, row, col);

                    URL url = null;

                    try {
                        url = new URL("http://startrektimelineswiki.com/wiki/"+trait.replace(" ", "_").replace("\"", "%22"));
                    } catch (MalformedURLException ex) {
                        Logger.getLogger(STTCollectionManager.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    open(url);
                }
            }   
        });
        table.addMouseMotionListener (new MouseAdapter() {
            public void mouseMoved (MouseEvent e) {
                table.getTableHeader().setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                if(table.getModel()==crewTable.getModel()) {
                    switch (table.columnAtPoint(e.getPoint())) {
                        case 1: setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); break;
                        case 10: setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); break;
                        case 11: setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); break;
                        
                        default: setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                    }
                } else if(table.getModel()==crewCollectionTable.getModel()) {
                    switch (table.columnAtPoint(e.getPoint())) {
                        case 1: setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); break;
                        case 4: setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR)); break;
                        case 5: setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR)); break;
                        case 8: setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR)); break;
                        case 9: setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR)); break;
                        case 10: setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR)); break;
                        case 11: setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR)); break;
                        case 12: setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR)); break;
                        case 13: setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR)); break;
                        
                        case 14: setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); break;
                        case 15: setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR)); break;
                        case 16: setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR)); break;
                        default: setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                    }
                }
            }
        });
    }
    
    public void addFrameIcons(JFrame frame) {
        Image image=null;
        ArrayList<BufferedImage> images = new ArrayList<BufferedImage>();
        try{
            List icons = new List();
            BufferedImage bi = ImageIO.read(getClass().getResource("/sttcollectionmanager/resources/logo16x16.png"));
            images.add(bi);
            bi = ImageIO.read(getClass().getResource("/sttcollectionmanager/resources/logo18x18.png"));
            images.add(bi);
            bi = ImageIO.read(getClass().getResource("/sttcollectionmanager/resources/logo20x20.png"));
            images.add(bi);
            bi = ImageIO.read(getClass().getResource("/sttcollectionmanager/resources/logo24x24.png"));
            images.add(bi);
            bi = ImageIO.read(getClass().getResource("/sttcollectionmanager/resources/logo32x32.png"));
            images.add(bi);
            bi = ImageIO.read(getClass().getResource("/sttcollectionmanager/resources/logo36x36.png"));
            images.add(bi);
            bi = ImageIO.read(getClass().getResource("/sttcollectionmanager/resources/logo40x40.png"));
            images.add(bi);
            bi = ImageIO.read(getClass().getResource("/sttcollectionmanager/resources/logo48x48.png"));
            images.add(bi);
            bi = ImageIO.read(getClass().getResource("/sttcollectionmanager/resources/logo64x64.png"));
            images.add(bi);
            bi = ImageIO.read(getClass().getResource("/sttcollectionmanager/resources/logo96x96.png"));
            images.add(bi);
            bi = ImageIO.read(getClass().getResource("/sttcollectionmanager/resources/logo128x128.png"));
            images.add(bi);
            bi = ImageIO.read(getClass().getResource("/sttcollectionmanager/resources/logo256x256.png"));
            images.add(bi);
            ImageIcon imi = new ImageIcon(bi);
            icons.add(imi.toString());
            frame.setIconImages(images);
        }catch( Exception e ) { 
            try {
                frame.setIconImage(ImageIO.read(getClass().getResource("/sttcollectionmanager/resources/logo16x16.png")));
            } catch (IOException ex) {
                Logger.getLogger(STTCollectionManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void loadSettings(SQLConnectionBridge sqlConnectionBridge) {
        String SQL = "SELECT Settings.value FROM STTCollectionManager.Settings WHERE Settings.setting='skin'";
        DefaultTableModel skinSettingModel = sqlConnectionBridge.query(SQL,"skin");
        setSkin(skinSettingModel.getValueAt(0, 0));
        
        SQL = "SELECT Settings.value FROM STTCollectionManager.Settings WHERE Settings.setting='windowHeight'";
        DefaultTableModel windowHeightSettingModel = sqlConnectionBridge.query(SQL,"windowHeight");
        Double height = new Double(windowHeightSettingModel.getValueAt(0, 0).toString());
        SQL = "SELECT Settings.value FROM STTCollectionManager.Settings WHERE Settings.setting='windowWidth'";
        DefaultTableModel windowWidthSettingModel = sqlConnectionBridge.query(SQL,"windowWidth");
        Double width = new Double(windowWidthSettingModel.getValueAt(0, 0).toString());
        Dimension dimension = new Dimension(width.intValue(),height.intValue());
        
        this.setMinimumSize(dimension);
        this.pack();
        this.setSize(dimension);
        this.setMinimumSize(null);
        
        SQL = "SELECT Settings.value FROM STTCollectionManager.Settings WHERE Settings.setting='windowPositionX'";
        DefaultTableModel windowPositionXSettingModel = sqlConnectionBridge.query(SQL,"windowPositionX");
        Double windowPositionX = new Double(windowPositionXSettingModel.getValueAt(0, 0).toString());
        SQL = "SELECT Settings.value FROM STTCollectionManager.Settings WHERE Settings.setting='windowPositionY'";
        DefaultTableModel windowPositionYSettingModel = sqlConnectionBridge.query(SQL,"windowPositionY");
        Double windowPositionY = new Double(windowPositionYSettingModel.getValueAt(0, 0).toString());
        this.setLocation(windowPositionX.intValue(),windowPositionY.intValue());

        SQL = "SELECT Settings.value FROM STTCollectionManager.Settings WHERE Settings.setting='windowMaximized'";
        DefaultTableModel windowMaximizedSettingModel = sqlConnectionBridge.query(SQL,"windowMaximized");
        String maximized=windowMaximizedSettingModel.getValueAt(0, 0).toString();
        if(maximized.equals("true"))
            this.setExtendedState(this.getExtendedState() | JFrame.MAXIMIZED_BOTH);
        
        SQL = "SELECT Settings.value FROM STTCollectionManager.Settings WHERE Settings.setting='fontSize'";
        DefaultTableModel fontSizeSettingModel = sqlConnectionBridge.query(SQL,"fontSize");
        
        SQL = "SELECT Settings.value FROM STTCollectionManager.Settings WHERE Settings.setting='font'";
        DefaultTableModel fontSettingModel = sqlConnectionBridge.query(SQL,"font");

        //Font crewTableFont = crewTable.getFont();
        //String fontName = crewTableFont.getFontName();
        String fontName = fontSettingModel.getValueAt(0, 0).toString();
        int jTableFontSize = Integer.parseInt(fontSizeSettingModel.getValueAt(0, 0).toString());
        crewTable.setFont(new Font(fontName, Font.PLAIN, jTableFontSize));
        crewCollectionTable.setFont(new Font(fontName, Font.PLAIN, jTableFontSize));
        crewTable.getTableHeader().setFont(new Font(fontName, Font.PLAIN, jTableFontSize));
        crewCollectionTable.getTableHeader().setFont(new Font(fontName, Font.PLAIN, jTableFontSize));
    }
    
    public void saveSettings(SQLConnectionBridge sqlConnectionBridge) {
                int maximized=sttCollectionManager.getExtendedState();
                String SQL;
                
                if(maximized<=0) {
                    SQL="UPDATE STTCollectionManager.Settings SET value='"+sttCollectionManager.getSize().getHeight()+"' WHERE Settings.setting='windowHeight'";
                    sqlConnectionBridge.update(SQL);
                    SQL="UPDATE STTCollectionManager.Settings SET value='"+sttCollectionManager.getSize().getWidth()+"' WHERE Settings.setting='windowWidth'";
                    sqlConnectionBridge.update(SQL);
                }

                if(maximized>0) {
                    SQL="UPDATE STTCollectionManager.Settings SET value='true' WHERE Settings.setting='windowMaximized'";
                    sqlConnectionBridge.update(SQL);
                } else {
                    SQL="UPDATE STTCollectionManager.Settings SET value='false' WHERE Settings.setting='windowMaximized'";
                    sqlConnectionBridge.update(SQL);
                }
                SQL="UPDATE STTCollectionManager.Settings SET value='"+sttCollectionManager.getLocationOnScreen().getX()+"' WHERE Settings.setting='windowPositionX'";
                sqlConnectionBridge.update(SQL);

                SQL="UPDATE STTCollectionManager.Settings SET value='"+sttCollectionManager.getLocationOnScreen().getY()+"' WHERE Settings.setting='windowPositionY'";
                sqlConnectionBridge.update(SQL);
    }
    
    public void firstRunCheck(SQLConnectionBridge sqlConnectionBridge) {
        String SQL = "SELECT Settings.Value FROM STTCollectionManager.Settings WHERE Settings.setting='showDisclaimer'";
        DefaultTableModel showDisclaimerSettingModel = sqlConnectionBridge.query(SQL,"showDisclaimer");

        if(showDisclaimerSettingModel.getRowCount()==0) {
            buildDbSettings();
            showDisclaimerSettingModel = sqlConnectionBridge.query(SQL,"showDisclaimer");
        }

        if(showDisclaimerSettingModel.getValueAt(0, 0)!=null) {
            if(showDisclaimerSettingModel.getValueAt(0, 0).equals("true"))
                showDisclaimer();
        } else
            showDisclaimer();
        
        if (disclaimerAccepted==true) {
            SQL = "UPDATE STTCollectionManager.Settings SET value='true' WHERE Settings.setting='disclaimerAccepted'";
            sqlConnectionBridge.update(SQL);
        }
        if (showDisclaimer==false) {
            SQL = "UPDATE STTCollectionManager.Settings SET value='false' WHERE Settings.setting='showDisclaimer'";
            sqlConnectionBridge.update(SQL);
        }    
        SQL = "SELECT Settings.Value FROM STTCollectionManager.Settings WHERE Settings.setting='disclaimerAccepted'";
        DefaultTableModel disclaimerAcceptedSettingModel = sqlConnectionBridge.query(SQL,"disclaimerAccepted");
        if(disclaimerAcceptedSettingModel.getValueAt(0, 0)!=null) {
            if(disclaimerAcceptedSettingModel.getValueAt(0, 0).equals("false"))
                this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
        }
        /*
        if(fillCrewTable("no search", null).toString().equals("java.lang.Exception: No Database")) {
            buildDB();
            fillCrewTable("no search", null);
            fillCrewCollectionTable("no search", null);
        } else {
            fillCrewTable("no search", null);
            fillCrewCollectionTable("no search", null);
        }
        */
    }
    
    public static Color hex2Rgb(String colorStr) {
        return new Color(
                Integer.valueOf( colorStr.substring( 1, 3 ), 16 ),
                Integer.valueOf( colorStr.substring( 3, 5 ), 16 ),
                Integer.valueOf( colorStr.substring( 5, 7 ), 16 ) );
    }
    
    public JTable getCrewTable(){
        return crewTable;
    }
    
    public JTable getCrewCollectionTable(){
        return crewCollectionTable;
    }
    
     public JdbcUrl getJdbcUrl(){
        return jdbcUrl;
    }
    
    public String getUser(){
        return user;
    }
    
    public String getPassword(){
        return password;
    }
}

/* Custom JTable creation code to fix Nimbus Alternating Row Color Checkbox bug and change row background colors.

JTable newtable = new JTable()  {
            // crew disable editing of some cells
            @Override public boolean isCellEditable(int row, int column) {switch(column){case 0: return false;case 1: return false;case 2: return false;case 3: return false;case 4: return false;case 5: return false;case 6: return false;case 7: return false;case 8: return false;case 9: return false;case 10: return false;case 11: return false;default: return true;}}
            
            // crewCollection disable editing of some cells
            @Override public boolean isCellEditable(int row, int column) {switch(column){case 0: return false;case 1: return false;case 2: return false;case 3: return false;case 14: return false;case 15: return false;default: return true;}}
            
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int col) {
                Component c = super.prepareRenderer(renderer, row, col);
                getColumn("Fully Equipped").setCellRenderer(
                    new DefaultTableCellRenderer() {
                        JCheckBox renderer = new JCheckBox();

                        @Override
                        public Component getTableCellRendererComponent(JTable table, Object value,
                                boolean isSelected, boolean hasFocus, int row, int column) {
                            super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                            if (value != null && value instanceof Boolean) {

                                Boolean b = (Boolean) value;
                                renderer.setSelected(b);
                                renderer.setOpaque(true);

                                if (isSelected) {
                                    renderer.setForeground(table.getSelectionForeground());
                                    renderer.setBackground(table.getSelectionBackground());
                                } else {
                                    Color bg = getBackground();
                                    renderer.setForeground(getForeground());

                                    renderer.setBackground(new Color(bg.getRed(), bg.getGreen(), bg.getBlue()));
                                }

                                renderer.setHorizontalAlignment(SwingConstants.CENTER);
                                return renderer;
                            } else {
                                return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                            }
                        }
                });
                getColumn("In Vault").setCellRenderer(
                    new DefaultTableCellRenderer() {
                        JCheckBox renderer = new JCheckBox();

                        @Override
                        public Component getTableCellRendererComponent(JTable table, Object value,
                                boolean isSelected, boolean hasFocus, int row, int column) {
                            super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                            if (value != null && value instanceof Boolean) {

                                Boolean b = (Boolean) value;
                                renderer.setSelected(b);
                                renderer.setOpaque(true);

                                if (isSelected) {
                                    renderer.setForeground(table.getSelectionForeground());
                                    renderer.setBackground(table.getSelectionBackground());
                                } else {
                                    Color bg = getBackground();
                                    renderer.setForeground(getForeground());

                                    renderer.setBackground(new Color(bg.getRed(), bg.getGreen(), bg.getBlue()));
                                }

                                renderer.setHorizontalAlignment(SwingConstants.CENTER);
                                return renderer;
                            } else {
                                return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                            }
                        }
                });
                getColumn("In Collection").setCellRenderer(
                    new DefaultTableCellRenderer() {
                        JCheckBox renderer = new JCheckBox();

                        @Override
                        public Component getTableCellRendererComponent(JTable table, Object value,
                                boolean isSelected, boolean hasFocus, int row, int column) {
                            super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                            if (value != null && value instanceof Boolean) {

                                Boolean b = (Boolean) value;
                                renderer.setSelected(b);
                                renderer.setOpaque(true);

                                if (isSelected) {
                                    renderer.setForeground(table.getSelectionForeground());
                                    renderer.setBackground(table.getSelectionBackground());
                                } else {
                                    Color bg = getBackground();
                                    renderer.setForeground(getForeground());

                                    renderer.setBackground(new Color(bg.getRed(), bg.getGreen(), bg.getBlue()));
                                }

                                renderer.setHorizontalAlignment(SwingConstants.CENTER);
                                return renderer;
                            } else {
                                return super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                            }
                        }
                });

                Integer stars = Integer.parseInt(getValueAt(row, 3).toString());
                switch(stars){             
                    case 1: c.setBackground(hex2Rgb("#9b9b9b")); break;
                    case 2: c.setBackground(hex2Rgb("#50aa3c")); break;
                    case 3: c.setBackground(hex2Rgb("#5aaaff")); break;
                    case 4: c.setBackground(hex2Rgb("#aa2deb")); break;
                    case 5: c.setBackground(hex2Rgb("#fdd26a")); break;
  
                    default: c.setBackground(Color.WHITE);
                }
                return c;
            }
        };
*/