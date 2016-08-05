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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JDialog;
import javax.swing.JTable;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author August Enzelberger <augustenz+github@gmail.com>
 * (https://github.com/augustenz)
 */
public class SearchJPanel extends javax.swing.JPanel {
    JTable table;
    SQLConnectionBridge sqlConnectionBridge;
    STTCollectionManager sttCollectionManager;
    /**
     * Creates new form SearchPanel
     */
    public SearchJPanel(JTable table, SQLConnectionBridge sqlConnectionBridge, STTCollectionManager sttCollectionManager, JDialog dialog) {
        initComponents();
        dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        dialog.addWindowListener(new WindowAdapter() {
        
            @Override
            public void windowClosing(WindowEvent event) {
                clearButtonActionPerformed(new ActionEvent(this, ActionEvent.ACTION_PERFORMED, null));
            }
        });
        
        this.table = table;
        this.sqlConnectionBridge = sqlConnectionBridge;
        this.sttCollectionManager = sttCollectionManager;
        
        String[] starsComboBoxValues = {"","1","2","3","4","5"};
        DefaultComboBoxModel model = new DefaultComboBoxModel(starsComboBoxValues);
        starsComboBox.setModel(model);
        
        if(table.getModel().equals(sttCollectionManager.getCrewCollectionTable().getModel())) {
                      inCollectionCheckBox.setSelected(true);
                      inCollectionCheckBox.setEnabled(false);
        } else if(table.getModel().equals(sttCollectionManager.getCrewTable().getModel())) {
                      fullyEquippedCheckBox.setSelected(false);
                      fullyEquippedCheckBox.setEnabled(false);
                      inVaultCheckBox.setSelected(false);
                      inVaultCheckBox.setEnabled(false);
                      inCollectionCheckBox.setSelected(false);
                      inCollectionCheckBox.setEnabled(false);
        }
    }
    
    public void search() {
        String id = idTextField.getText();
        String crewName = crewNameTextField.getText();
        String charName = charNameTextField.getText();
        String stars = String.valueOf(starsComboBox.getSelectedIndex());
        String race = raceTextField.getText();
        String traits = traitsTextField.getText();
        Boolean fullyEquipped = fullyEquippedCheckBox.isSelected();
        Boolean inVault = inVaultCheckBox.isSelected();
        Boolean inCollection = inCollectionCheckBox.isSelected();
        String quantity = quantityTextField.getText();

        if(table.getModel().equals(sttCollectionManager.getCrewCollectionTable().getModel())) {
            String SQL="SELECT Collection.ID, Crew.CrewName,Crew.CharName,Crew.Stars,Collection.starsFused,Collection.level,Collection.fullyEquipped,Collection.inVault,Collection.cmd,Collection.dip,Collection.eng,Collection.med,Collection.sci,Collection.sec,Crew.Race,Crew.Traits,Collection.Quantity,Collection.inCollection " +
                       "FROM STTCollectionManager.Crew " +
                       "INNER JOIN STTCollectionManager.Collection " +
                       "ON Crew.CrewName = Collection.CrewName " +
                       "WHERE ";
                      if(!id.equals("")) SQL+="Collection.id = "+id+" AND ";
                      SQL+="Crew.crewName LIKE '%"+capitalizeFirstCharacter(crewName)+"%' AND ";
                      SQL+="Crew.charName LIKE '%"+capitalizeFirstCharacter(charName)+"%' AND ";
                      if(stars.equals("0"))
                          SQL+="Crew.stars  LIKE '%%' AND ";
                      else
                          SQL+="Crew.stars = '"+stars+"' AND ";
                      SQL+="Crew.race LIKE '%"+capitalizeFirstCharacter(race)+"%' AND ";
                      SQL+="Crew.traits LIKE '%"+capitalizeFirstCharacter(traits)+"%' AND ";
                      SQL+="Collection.fullyEquipped = "+fullyEquipped+" AND ";
                      SQL+="Collection.inVault = "+inVault+" AND ";
                      SQL+="Collection.inCollection = "+inCollection+" AND ";
                      SQL+="Collection.quantity LIKE '%"+quantity+"%'";
                      
            int columns=table.getModel().getColumnCount();

            String[] columnNames = new String[columns];
            for(int i=0; i<columns; i++)
                columnNames[i]=table.getModel().getColumnName(i);
            DefaultTableModel resultsModel = sqlConnectionBridge.query(SQL,columnNames);

            ActionListener al= new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        sttCollectionManager.fillCrewCollectionTable("search", resultsModel);
                    }
            };
            Timer timer = new Timer(0,al);
            timer.setRepeats(false);
            timer.start();  
        } else if(table.getModel().equals(sttCollectionManager.getCrewTable().getModel())) {
            String SQL="SELECT DISTINCT Crew.*,Collection.inCollection " +
                       "FROM STTCollectionManager.Crew "
                     + "LEFT OUTER JOIN STTCollectionManager.Collection "
                     + "ON Crew.CrewName = Collection.CrewName " +
                       "WHERE ";
                      if(!id.equals("")) SQL+="Crew.id = "+id+" AND ";
                      SQL+="Crew.crewName LIKE '%"+capitalizeFirstCharacter(crewName)+"%' AND ";
                      SQL+="Crew.charName LIKE '%"+capitalizeFirstCharacter(charName)+"%' AND ";
                      if(stars.equals("0"))
                          SQL+="Crew.stars  LIKE '%%' AND ";
                      else
                          SQL+="Crew.stars = '"+stars+"' AND ";
                      SQL+="Crew.race LIKE '%"+capitalizeFirstCharacter(race)+"%' AND ";
                      SQL+="Crew.traits LIKE '%"+capitalizeFirstCharacter(traits)+"%'";
                      //AND ";
                      //SQL+="Collection.inCollection = "+inCollection;
                      
            int columns=table.getModel().getColumnCount();

            String[] columnNames = new String[columns];
            for(int i=0; i<columns; i++)
                columnNames[i]=table.getModel().getColumnName(i);
            DefaultTableModel resultsModel = sqlConnectionBridge.query(SQL,columnNames);

            ActionListener al= new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        sttCollectionManager.fillCrewTable("search", resultsModel);
                    }
            };
            Timer timer = new Timer(0,al);
            timer.setRepeats(false);
            timer.start(); 
        } 
    }
    
    public static String capitalizeFirstCharacter(String inputString) {
        String outputString = "";
        if(!inputString.isEmpty()) {
            inputString = inputString.trim().replaceAll(" +", ",");
            inputString = inputString.replaceAll(",+", ",");
            String[] tokens = inputString.split(",");

            for (int i = 0; i < tokens.length; i++) {
                outputString = outputString.concat(String.valueOf(Character.toUpperCase(tokens[i].charAt(0)))).concat(tokens[i].substring(1)).trim()+"%";
            }           
        }
        return outputString;
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

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        idTextField = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        crewNameTextField = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        charNameTextField = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        starsComboBox = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        raceTextField = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        traitsTextField = new javax.swing.JTextField();
        fullyEquippedCheckBox = new javax.swing.JCheckBox();
        inVaultCheckBox = new javax.swing.JCheckBox();
        inCollectionCheckBox = new javax.swing.JCheckBox();
        jLabel7 = new javax.swing.JLabel();
        quantityTextField = new javax.swing.JTextField();
        clearButton = new javax.swing.JButton();

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        java.awt.GridBagLayout layout = new java.awt.GridBagLayout();
        layout.columnWeights = new double[] {0.0};
        setLayout(layout);

        jLabel1.setText("ID");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        add(jLabel1, gridBagConstraints);

        idTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                idTextFieldKeyReleased(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 200;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        add(idTextField, gridBagConstraints);

        jLabel2.setText("Crew Name");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        add(jLabel2, gridBagConstraints);

        crewNameTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                crewNameTextFieldKeyReleased(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 200;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        add(crewNameTextField, gridBagConstraints);

        jLabel3.setText("Char Name");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        add(jLabel3, gridBagConstraints);

        charNameTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                charNameTextFieldKeyReleased(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 200;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        add(charNameTextField, gridBagConstraints);

        jLabel4.setText("Stars");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        add(jLabel4, gridBagConstraints);

        starsComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        starsComboBox.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                starsComboBoxItemStateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        add(starsComboBox, gridBagConstraints);

        jLabel5.setText("Race");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        add(jLabel5, gridBagConstraints);

        raceTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                raceTextFieldKeyReleased(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 200;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        add(raceTextField, gridBagConstraints);

        jLabel6.setText("Traits");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        add(jLabel6, gridBagConstraints);

        traitsTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                traitsTextFieldKeyReleased(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 200;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        add(traitsTextField, gridBagConstraints);

        fullyEquippedCheckBox.setText("Fully Equipped");
        fullyEquippedCheckBox.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                fullyEquippedCheckBoxStateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        add(fullyEquippedCheckBox, gridBagConstraints);

        inVaultCheckBox.setText("In Vault");
        inVaultCheckBox.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                inVaultCheckBoxStateChanged(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        add(inVaultCheckBox, gridBagConstraints);

        inCollectionCheckBox.setText("In Collection");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 4;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        add(inCollectionCheckBox, gridBagConstraints);

        jLabel7.setText("Quantity");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        add(jLabel7, gridBagConstraints);

        quantityTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                quantityTextFieldKeyReleased(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 3;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.ipadx = 200;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 0.5;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        add(quantityTextField, gridBagConstraints);

        clearButton.setText("Clear");
        clearButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearButtonActionPerformed(evt);
            }
        });
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(5, 5, 5, 5);
        add(clearButton, gridBagConstraints);
    }// </editor-fold>//GEN-END:initComponents

    private void starsComboBoxItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_starsComboBoxItemStateChanged
        search();
    }//GEN-LAST:event_starsComboBoxItemStateChanged

    private void fullyEquippedCheckBoxStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_fullyEquippedCheckBoxStateChanged
        search();
    }//GEN-LAST:event_fullyEquippedCheckBoxStateChanged

    private void inVaultCheckBoxStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_inVaultCheckBoxStateChanged
        search();
    }//GEN-LAST:event_inVaultCheckBoxStateChanged

    private void clearButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearButtonActionPerformed
        idTextField.setText("");
        crewNameTextField.setText("");
        charNameTextField.setText("");
        starsComboBox.setSelectedIndex(0);
        raceTextField.setText("");
        traitsTextField.setText("");
        fullyEquippedCheckBox.setSelected(false);
        inVaultCheckBox.setSelected(false);
        if(table.getModel().equals(sttCollectionManager.getCrewTable().getModel()))
            inCollectionCheckBox.setSelected(false);
        quantityTextField.setText("");
        
        if(table.getModel().equals(sttCollectionManager.getCrewCollectionTable().getModel())) {
            ActionListener al= new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                            sttCollectionManager.fillCrewCollectionTable("no search", null);
                        }
            };
        } else if(table.getModel().equals(sttCollectionManager.getCrewTable().getModel())) {
            ActionListener al= new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                            sttCollectionManager.fillCrewTable("no search", null);
                        }
            };

            Timer timer = new Timer(0,al);
            timer.setRepeats(false);
            timer.start();
        }
    }//GEN-LAST:event_clearButtonActionPerformed

    private void traitsTextFieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_traitsTextFieldKeyReleased
        search();
    }//GEN-LAST:event_traitsTextFieldKeyReleased

    private void idTextFieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_idTextFieldKeyReleased
        search();
    }//GEN-LAST:event_idTextFieldKeyReleased

    private void crewNameTextFieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_crewNameTextFieldKeyReleased
        search();
    }//GEN-LAST:event_crewNameTextFieldKeyReleased

    private void charNameTextFieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_charNameTextFieldKeyReleased
        search();
    }//GEN-LAST:event_charNameTextFieldKeyReleased

    private void raceTextFieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_raceTextFieldKeyReleased
        search();
    }//GEN-LAST:event_raceTextFieldKeyReleased

    private void quantityTextFieldKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_quantityTextFieldKeyReleased
        search();
    }//GEN-LAST:event_quantityTextFieldKeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField charNameTextField;
    private javax.swing.JButton clearButton;
    private javax.swing.JTextField crewNameTextField;
    private javax.swing.JCheckBox fullyEquippedCheckBox;
    private javax.swing.JTextField idTextField;
    private javax.swing.JCheckBox inCollectionCheckBox;
    private javax.swing.JCheckBox inVaultCheckBox;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JTextField quantityTextField;
    private javax.swing.JTextField raceTextField;
    private javax.swing.JComboBox<String> starsComboBox;
    private javax.swing.JTextField traitsTextField;
    // End of variables declaration//GEN-END:variables
}
