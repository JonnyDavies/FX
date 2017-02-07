package view;

import java.util.HashMap;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

public class FXViewChartPane extends VBox {
  
  private LineChart<Number, Number> lc;
  private XYChart.Series<Number, Number> seriesEUR;
  private XYChart.Series<Number, Number> seriesUSD;
  private XYChart.Series<Number, Number> seriesGBP;
  private XYChart.Series<Number, Number> seriesCHF;  
  private HashMap<String,XYChart.Series<Number, Number>> allSeries;
  private FXViewOrderPane op;
  private NumberAxis xAxis, yAxis;
  private TabPane tp;

  
  public FXViewChartPane()
  {
    // intialise series???
    allSeries = new HashMap<String,XYChart.Series<Number, Number>>();
    allSeries.put("EUR/USD", this.seriesEUR = new LineChart.Series<>());
    allSeries.put("USD/JPY", this.seriesUSD = new LineChart.Series<>());
    allSeries.put("GBP/USD", this.seriesGBP = new LineChart.Series<>());
    allSeries.put("USD/CHF", this.seriesCHF = new LineChart.Series<>());

    SplitPane sp = new SplitPane(); 
    sp.setOrientation(Orientation.VERTICAL);  

    this.tp = new TabPane();

    final Tab t1 = new Tab();
    t1.setText("EUR/USD");
    t1.setContent(addFlowPane("EUR/USD"));
       
    tp.getTabs().addAll(t1);
    
    this.op = new FXViewOrderPane();
    

    sp.getItems().addAll(tp, op);
    this.getChildren().add(sp);
  }
  
  public void addTabPane(String currency)
  {
    // add tab
    // will we need to hold the series in a class?
    // we won't in the db, too much effort.
    Tab t =  new Tab();
    t.setText(currency);
    t.setContent(addFlowPane(currency)); 
    this.tp.getTabs().addAll(t); 
    
  }
  
  
  public VBox addFlowPane(String currency) 
  {
    
    VBox vb = new VBox();
    vb.setStyle("-fx-background-color :  #e6e6e6");
    vb.setPadding(new Insets(10, 10, 10, 10));
    
    //defining the axes
    xAxis = new NumberAxis();
    yAxis = new NumberAxis();
    
    // time
    xAxis.setAutoRanging(false);
    xAxis.setLowerBound(0);
    xAxis.setUpperBound(100);
    xAxis.setTickUnit(1);
    xAxis.setTickMarkVisible(false);
    
    // price  
    yAxis.setAutoRanging(false);
    yAxis.setLowerBound(1.000);
    yAxis.setUpperBound(1.100);
    yAxis.setTickUnit(0.001);
    yAxis.setTickMarkVisible(false);
  
    lc = new LineChart<>(xAxis, yAxis); 
    lc.setPrefSize(650.0, 850.0);
    lc.setStyle(".chart-line-symbol { -fx-background-color: null, null }");    
    lc.setLegendVisible(false);
    lc.setCreateSymbols(false); //hide dots

    
    lc.getData().add(allSeries.get(currency));   
    vb.getChildren().addAll(lc);
    return vb;
  }

  public LineChart<Number, Number> getChart()
  {
    return lc;
  }
  
  public XYChart.Series<Number, Number> getCertainSeries(String currency)
  {
    return allSeries.get(currency);
  }
  
  public void updateSeries(String currency, int time, double price)
  {
    allSeries.get(currency).getData().add(new XYChart.Data(time, price));    
  }
  
  public FXViewOrderPane returnOrderPane()
  {
      return this.op;
  }
  
  public TabPane getTabPanes()
  {
      return this.tp;
  }
  
  
}
