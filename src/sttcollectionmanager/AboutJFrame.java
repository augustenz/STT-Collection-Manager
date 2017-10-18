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
import java.awt.Image;
import java.awt.List;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;

/**
 *
 * August Enzelberger <augustenz+github@gmail.com> (https://github.com/augustenz)
 */
public class AboutJFrame extends javax.swing.JFrame {

    /**
     * Creates new form aboutJFrame
     */
    public AboutJFrame() {
        initComponents();
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
            this.setIconImages(images);
        }catch( Exception e ) { 
            try {
                this.setIconImage(ImageIO.read(getClass().getResource("/sttcollectionmanager/resources/logo16x16.png")));
            } catch (IOException ex) {
                Logger.getLogger(STTCollectionManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        String text="<p><b>Author:</b> August Enzelberger (<a href=\"mailto:augustenz+github@gmail.com\">augustenz+github@gmail.com</a>)</p>"
                + "<p><b>Project Homepage:</b> <a href=https://github.com/augustenz/STT-Collection-Manager>https://github.com/augustenz/STT-Collection-Manager</a></p>"
                + "\n"
                + "<p>STT Collection Manager was created with Netbeans IDE 8.1."
                + "<p>Crew data comes from the very awesome Star Trek Timelines Wiki (<a href=https://stt.wiki>https://stt.wiki</a>) and its awesome users.</p>"
                + "<p>App icon by Memory Alpha (<a href=http://memory-alpha.wikia.com>http://memory-alpha.wikia.com</a>).</p>"
                + "<p>Roddenberry font by Jaynz (<a href=http://www.fontspace.com/pixel-sagas/roddenberry>http://www.fontspace.com/pixel-sagas/roddenberry</a>).</p>"//
                + "<p>Icons from the Noun Project (<a href=https://thenounproject.com>https://thenounproject.com</a>): Info by Karthick Nagarajan, Export by Mourad Mokrane, Refresh by Alex Auda Samora, Download by Guillaume Bahri, Zoom out by useiconic.com, Zoom in by useiconic.com, Reset zoom by Leonardo Schneider, Font by iconsmind.com, Help by Dima Lagunov, Log by Arthur Shlain, Stats by Calvin Goodman, Charts by Prerak Patel, About by Gregor Črešnar, Skin by Rflor, Exit by Andres Gleixner, Tables by Viktor Vorobyev, Search by Iconfactory Team, Settings by Michal Beno.</p>"
                + "<p>JFontChooser by Masahiko Sawai (<a href=http://jfontchooser.osdn.jp/site/jfontchooser>http://jfontchooser.osdn.jp/site/jfontchooser</a>).</p>"
                + "<p>Nimbus checkbox alternate row background color bug fix by Harald Barsnes (<a href=https://github.com/compomics>https://github.com/compomics</a>).</p>"
                + "<p>Many thanks to all the awesome people at Stack Overflow (<a href=http://stackoverflow.com>http://stackoverflow.com</a>) and Coderunch (<a href=https://coderanch.com>https://coderanch.com</a>), without all of the excellent information there this project would have been much harder to develop.</p>"
                + "<p>This software is released under the GNU General Public License, version 3 (<a href=https://www.gnu.org/licenses/gpl-3.0.html>https://www.gnu.org/licenses/gpl-3.0.html</a>).</p>"
                + "<p>Game content and materials are trademarks and copyrights of their respective publisher and its licensors.</p>";
        
        aboutEditorPane.setText(text);
        aboutEditorPane.addHyperlinkListener(new HyperlinkListener() {
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

        aboutLabel = new javax.swing.JLabel();
        okButton = new javax.swing.JButton();
        beerButton = new javax.swing.JButton();
        logosLabel = new javax.swing.JLabel();
        logoLabel = new javax.swing.JLabel();
        aboutScrollPane = new javax.swing.JScrollPane();
        aboutEditorPane = new javax.swing.JEditorPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("About STT Collection Manager");
        setPreferredSize(new java.awt.Dimension(1024, 730));
        setResizable(false);

        aboutLabel.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        aboutLabel.setText("About STT Collection Manager v1.2");

        okButton.setText("Ok");
        okButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                okButtonActionPerformed(evt);
            }
        });

        beerButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sttcollectionmanager/resources/beer32x32.png"))); // NOI18N
        beerButton.setText("Beer! :)");
        beerButton.setToolTipText("If you like this Software feel free to buy me a beer!");
        beerButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                beerButtonActionPerformed(evt);
            }
        });

        logosLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sttcollectionmanager/resources/logos2.png"))); // NOI18N

        logoLabel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/sttcollectionmanager/resources/appLogoSmallNew.png"))); // NOI18N

        aboutEditorPane.setEditable(false);
        aboutEditorPane.setContentType("text/html"); // NOI18N
        aboutScrollPane.setViewportView(aboutEditorPane);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(logoLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 268, Short.MAX_VALUE)
                        .addComponent(logosLabel))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(aboutLabel)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(okButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(beerButton)))
                .addContainerGap())
            .addComponent(aboutScrollPane, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(logoLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(aboutLabel))
                    .addComponent(logosLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(aboutScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 385, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(okButton, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(beerButton, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void okButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_okButtonActionPerformed
        this.dispose();
    }//GEN-LAST:event_okButtonActionPerformed

    private void beerButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_beerButtonActionPerformed
        URI uri = null;
        try {
            uri = new URI("https://www.paypal.me/augustenz");
        } catch (URISyntaxException ex) {
            Logger.getLogger(AboutJFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        open(uri);
    }//GEN-LAST:event_beerButtonActionPerformed

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
            java.util.logging.Logger.getLogger(AboutJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AboutJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AboutJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AboutJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AboutJFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JEditorPane aboutEditorPane;
    private javax.swing.JLabel aboutLabel;
    private javax.swing.JScrollPane aboutScrollPane;
    private javax.swing.JButton beerButton;
    private javax.swing.JLabel logoLabel;
    private javax.swing.JLabel logosLabel;
    private javax.swing.JButton okButton;
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

