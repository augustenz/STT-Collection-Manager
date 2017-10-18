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

import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JScrollBar;

/**
 *
 * @author August Enzelberger <augustenz+github@gmail.com>
 * (https://github.com/augustenz)
 */
public class Progress {
        private ProgressJPanel progressPanel;
        private JDialog progressDialog;
        private int max;
        
        Progress(JFrame sttCollectionManager, int max) {
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            Double width = screenSize.getWidth()/4.0;
            
            this.max = max;
            this.progressDialog = new JDialog(sttCollectionManager, "Import progress", false);
            this.progressDialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

            this.progressPanel = new ProgressJPanel();
            if(max>0) {
                progressPanel.getProgressProgressBar().setMaximum(max);
                progressPanel.getProgressProgressBar().setStringPainted(true);
            }
            this.progressDialog.getContentPane().add(progressPanel);
            this.progressDialog.setSize(960, 400);
            this.progressDialog.setLocation(width.intValue(), 0);
            this.progressDialog.pack();
            this.progressDialog.setVisible(true);
        }

        public void showProgress(int position, String text) {
            this.progressPanel.getProgressTextArea().append(text);
            this.progressPanel.getProgressTextArea().setCaretPosition(this.progressPanel.getProgressTextArea().getText().length() - 1);
            JScrollBar vbar = this.progressPanel.getProgressScrollPane().getVerticalScrollBar();
            vbar.setValue(vbar.getMaximum());
            vbar.paint(vbar.getGraphics());
            this.progressPanel.getProgressTextArea().scrollRectToVisible(this.progressPanel.getProgressTextArea().getVisibleRect());
            this.progressPanel.getProgressTextArea().update(this.progressPanel.getProgressTextArea().getGraphics());
            if(position>=0) {
                this.progressPanel.getProgressProgressBar().setValue(position);
                this.progressPanel.getProgressProgressBar().setString("Importing Crew member "+(position+1)+" of "+max);
            }
            this.progressPanel.getProgressProgressBar().update(this.progressPanel.getProgressProgressBar().getGraphics());
        }
}
