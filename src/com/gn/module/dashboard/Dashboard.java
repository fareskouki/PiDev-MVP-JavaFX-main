/*
 * Copyright (C) Gleidson Neves da Silveira
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
package com.gn.module.dashboard;

import com.jfoenix.controls.JFXListView;
import eu.hansolo.tilesfx.Tile;
import eu.hansolo.tilesfx.chart.ChartData;
import java.net.MalformedURLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.net.URL;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.text.TextFlow;
import rsscreator.ui.main.FXMLDocumentController;
import static rsscreator.ui.main.FXMLDocumentController.createFeedListItem;
import static rsscreator.ui.main.FXMLDocumentController.myList;
import rsscreator.ui.resourses.FeedMessage;
import rsscreator.ui.resourses.filesHandler;
import javafx.scene.web.WebView;

/**
 * @author Gleidson Neves da Silveira | gleidisonmt@gmail.com
 * Create on  20/10/2018
 * Version 1.0
 */
public class Dashboard implements Initializable {

//    @FXML private Tile calendar;
    @FXML private AreaChart<String, Number> areaChart;

    @FXML private PieChart pieChart;
    
    
    @FXML
    private JFXListView<TextFlow> listView;
    public static JFXListView<TextFlow> myList;

    @SuppressWarnings("unchecked")
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        
        listView.setExpanded(true);
        listView.depthProperty().set(1);
        myList = listView;
        
        try {
                URL url = new URL("https://kotaku.com/rss");
                filesHandler.importFrom(url);
            } catch (MalformedURLException ex) {
                Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
            }

     /*  ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                new PieChart.Data("Sun", 20),
                new PieChart.Data("IBM", 12),
                new PieChart.Data("HP", 25),
                new PieChart.Data("Dell", 22),
                new PieChart.Data("Apple", 30)
        );

        pieChart.setData(pieChartData);

        pieChart.setClockwise(false);

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Legend 1");
        series.getData().add(new XYChart.Data<>("0", 2D));
        series.getData().add(new XYChart.Data<>("1", 8D));
        series.getData().add(new XYChart.Data<>("2", 5D));
        series.getData().add(new XYChart.Data<>("3", 3D));
        series.getData().add(new XYChart.Data<>("4", 6D));
        series.getData().add(new XYChart.Data<>("5", 8D));
        series.getData().add(new XYChart.Data<>("6", 5D));
        series.getData().add(new XYChart.Data<>("7", 6D));
        series.getData().add(new XYChart.Data<>("8", 5D));

        areaChart.getData().setAll(series);
        areaChart.setCreateSymbols(true);*/
    }
    
    public static void createList(){
        if (filesHandler.feed==null)return;
        myList.getItems().clear();
        for (FeedMessage msg:filesHandler.feed.getEntries()){
            myList.getItems().add(createFeedListItem(msg));
        }
    }
}
