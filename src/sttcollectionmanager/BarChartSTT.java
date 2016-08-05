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
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;

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
public class BarChartSTT {
    
    private String[] parameters;
    private JFrame frame;
    private JFXPanel fxPanel;
    
    BarChartSTT(String[] parameters, JFXPanel fxPanel) {
        this.parameters=parameters;
        this.fxPanel = fxPanel;
    }
    
    BarChartSTT(String[] parameters) {
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
            
            frame = new JFrame("STT Collection Manager - All Crew-Crew Collection Bar Chart");
            addFrameIcon();
            
            JPanel chartPanel = new JPanel();
            chartPanel.setLayout(new GridBagLayout());
            chartPanel.add(fxPanel,c);
            frame.add(chartPanel);
            
            frame.setSize(500,500);
            //frame.setLocation(500, 0);
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
        scene.getStylesheets().add("/sttcollectionmanager/resources/chartsStylesheet.css");
        fxPanel.setScene(scene);
    }

    
    private Scene createScene() {

        //Scene scene = new Scene(new Group());
        final CategoryAxis xAxis = new CategoryAxis();
        final NumberAxis yAxis = new NumberAxis();
        final BarChart<String,Number> bc = 
            new BarChart<String,Number>(xAxis,yAxis);
        bc.setTitle("All Crew - Crew Collection");
        //bc.setPrefSize(200.0, 200.0);
        //bc.setBarGap(3);
        //bc.setCategoryGap(20);
        xAxis.setLabel("Stars");       
        yAxis.setLabel("Quantity");
 
        XYChart.Series<String,Number> series1 = new XYChart.Series<String,Number>();
        series1.setName("5-star");       
        series1.getData().add(new XYChart.Data<String,Number>("Total", Double.parseDouble(parameters[0])));
        series1.getData().add(new XYChart.Data<String,Number>("In Collection", Double.parseDouble(parameters[5])));
     
        
        XYChart.Series<String,Number> series2 = new XYChart.Series<String,Number>();
        series2.setName("4-star");
        series2.getData().add(new XYChart.Data<String,Number>("Total", Double.parseDouble(parameters[1])));
        series2.getData().add(new XYChart.Data<String,Number>("In Collection", Double.parseDouble(parameters[6]))); 
        
        XYChart.Series<String,Number> series3 = new XYChart.Series<String,Number>();
        series3.setName("3-star");
        series3.getData().add(new XYChart.Data<String,Number>("Total", Double.parseDouble(parameters[2])));
        series3.getData().add(new XYChart.Data<String,Number>("In Collection", Double.parseDouble(parameters[7])));
        
        XYChart.Series<String,Number> series4 = new XYChart.Series<String,Number>();
        series4.setName("2-star");
        series4.getData().add(new XYChart.Data<String,Number>("Total", Double.parseDouble(parameters[3])));
        series4.getData().add(new XYChart.Data<String,Number>("In Collection", Double.parseDouble(parameters[8])));
        
        XYChart.Series<String,Number> series5 = new XYChart.Series<String,Number>();
        series5.setName("1-star");
        series5.getData().add(new XYChart.Data<String,Number>("Total", Double.parseDouble(parameters[4])));
        series5.getData().add(new XYChart.Data<String,Number>("In Collection", Double.parseDouble(parameters[9])));
        
        Scene scene  = new Scene(bc,800,600);
        bc.getData().addAll(series1, series2, series3, series4, series5);
        for (final Series<String, Number> series : bc.getData()) {
            for (final Data<String, Number> data : series.getData()) {
                 Tooltip tooltip = new Tooltip();
                 tooltip.setText(data.getYValue().toString().replace(".0", ""));
                 Tooltip.install(data.getNode(), tooltip);                    
            }
}
        //chart.setLabelLineLength(10);
        //chart.setLegendSide(Side.LEFT);
        //chart.setClockwise(false)
        //setStartAngle(180)
        //((Group) scene.getRoot()).getChildren().add(chart);
        
        /*
        Label caption = new Label("");
        caption.setTextFill(Color.DARKORANGE);
        caption.setStyle("-fx-font: 24 arial;");

        for (PieChart.Data data : chart.getData()) {
            data.getNode().addEventHandler(MouseEvent.MOUSE_RELEASED,
                new EventHandler<MouseEvent>() {
                    @Override public void handle(MouseEvent e) {
                        caption.setTranslateX(e.getSceneX());
                        caption.setTranslateY(e.getSceneY());
                        caption.setText(String.valueOf(data.getPieValue()) + "%");
                     }
                });
        }
        */
        
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
