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

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.List;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.chart.XYChart.Data;
import javafx.scene.chart.XYChart.Series;
import javafx.scene.control.Tooltip;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 *
 * @author August Enzelberger <augustenz+github@gmail.com>
 * (https://github.com/augustenz)
 */
public class StackedBarChartSTT {
    
    private String[] parameters;
    private JFrame frame;
    private JFXPanel fxPanel;
    
    StackedBarChartSTT(String[] parameters, JFXPanel fxPanel) {
        this.parameters=parameters;
        this.fxPanel = fxPanel;
    }
    
    StackedBarChartSTT(String[] parameters) {
        this.parameters=parameters;
        fxPanel = new JFXPanel();
    }
    
    public void initAndShowGUI(String window) {
        // This method is invoked on the EDT thread
        if (! SwingUtilities.isEventDispatchThread()) {
            throw new IllegalStateException("Not on Event Dispatch Thread");
        }

        if (window.equals("window")) {
            GridBagConstraints c = new GridBagConstraints();

            c.fill = GridBagConstraints.BOTH;
            c.weightx = 1.0;
            c.weighty = 1.0;
            
            frame = new JFrame("STT Collection Manager - All Crew-Crew Collection Stacked Bar Chart");
            addFrameIcon();
            
            JPanel chartPanel = new JPanel();
            chartPanel.setLayout(new GridBagLayout());
            chartPanel.add(fxPanel,c);
            frame.add(chartPanel);
            
            frame.setSize(500,500);
            //frame.setLocation(1000, 0);
            frame.setVisible(true);
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        }

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                initFX(getFxPanel());
            }
        });
    }

    private void initFX(JFXPanel fxPanel) {
        // This method is invoked on the JavaFX thread
        if (! Platform.isFxApplicationThread()) {
            throw new IllegalStateException("Not on FX Application Thread");
        }
        Scene scene = createScene();
        /*
        scene.widthProperty().addListener(new ChangeListener<Number>() {
            @Override public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneWidth, Number newSceneWidth) {
                System.out.println("Width: " + newSceneWidth);
                fxPanel.setScene(createScene());
            }
        });
        scene.heightProperty().addListener(new ChangeListener<Number>() {
            @Override public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneHeight, Number newSceneHeight) {
                System.out.println("Height: " + newSceneHeight);
                fxPanel.setScene(createScene());
            }
        });
        */
        scene.getStylesheets().add("/sttcollectionmanager/resources/stackedBarStylesheet.css");
        fxPanel.setScene(scene);
    }


    private Scene createScene() {

        //Scene scene = new Scene(new Group());
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        final StackedBarChart<String, Number> sbc =
                new StackedBarChart<String, Number>(xAxis, yAxis);
        final XYChart.Series<String, Number> series1 =
                new XYChart.Series<String, Number>();
        final XYChart.Series<String, Number> series2 =
                new XYChart.Series<String, Number>();

        sbc.setTitle("All Crew - Crew Collection");
        //sbc.setPrefSize(200.0, 200.0);
        xAxis.setLabel("Stars");
        xAxis.setCategories(FXCollections.<String>observableArrayList(
                Arrays.asList("5-star","4-star","3-star","2-star","1-star")));
        yAxis.setLabel("Quantity");
        series1.setName("Total");
        series1.getData().add(new XYChart.Data<String, Number>("5-star", Double.parseDouble(parameters[0])));
        series1.getData().add(new XYChart.Data<String, Number>("4-star", Double.parseDouble(parameters[1])));
        series1.getData().add(new XYChart.Data<String, Number>("3-star", Double.parseDouble(parameters[2])));
        series1.getData().add(new XYChart.Data<String, Number>("2-star", Double.parseDouble(parameters[3])));
        series1.getData().add(new XYChart.Data<String, Number>("1-star", Double.parseDouble(parameters[4])));

        series2.setName("In Collection");
        series2.getData().add(new XYChart.Data<String, Number>("5-star", Double.parseDouble(parameters[5])));
        series2.getData().add(new XYChart.Data<String, Number>("4-star", Double.parseDouble(parameters[6])));
        series2.getData().add(new XYChart.Data<String, Number>("3-star", Double.parseDouble(parameters[7])));
        series2.getData().add(new XYChart.Data<String, Number>("2-star", Double.parseDouble(parameters[8])));
        series2.getData().add(new XYChart.Data<String, Number>("1-star", Double.parseDouble(parameters[9])));

        Scene scene = new Scene(sbc, 800, 600);
        sbc.getData().addAll(series1, series2);
        for (final Series<String, Number> series : sbc.getData()) {
            for (final Data<String, Number> data : series.getData()) {
                 Tooltip tooltip = new Tooltip();
                 tooltip.setText(data.getYValue().toString().replace(".0", ""));
                 Tooltip.install(data.getNode(), tooltip);                    
            }
}

        return (scene);
    }

    /**
     * @return the frame
     */
    public JFrame getFrame() {
        return frame;
    }

    /**
     * @return the fxPanel
     */
    public JFXPanel getFxPanel() {
        return fxPanel;
    }
    
    public void addFrameIcon() {
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
    
}

