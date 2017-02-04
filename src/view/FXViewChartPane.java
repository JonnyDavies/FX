package view;

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
  private XYChart.Series<Number, Number> series;
  private FXViewOrderPane op;
  private NumberAxis xAxis;
  private NumberAxis yAxis;

  
  public FXViewChartPane(){

    SplitPane sp = new SplitPane(); 
    sp.setOrientation(Orientation.VERTICAL);  

    TabPane tb = new TabPane();
    
    final Tab t1 = new Tab();
    t1.setText("EUR/USD");
    t1.setContent(addFlowPane());
       
    tb.getTabs().addAll(t1);
    this.op = new FXViewOrderPane();
    
    sp.getItems().addAll(tb, op);
    this.getChildren().add(sp);
  }
  
  public VBox addFlowPane() {
    
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
    
    // price  
    yAxis.setAutoRanging(false);
    yAxis.setLowerBound(1.000);
    yAxis.setUpperBound(1.101);
    yAxis.setTickUnit(0.001);
  
    lc = new LineChart<>(xAxis, yAxis); 
    lc.setPrefSize(650.0, 850.0);
    lc.setStyle(".chart-line-symbol { -fx-background-color: null, null }");    
    lc.setLegendVisible(false);
    lc.setCreateSymbols(false); //hide dots

    
    series = new LineChart.Series<>();
    lc.getData().add(series); 
    

    vb.getChildren().addAll(lc);
    return vb;
  }

  public LineChart<Number, Number> getChart()
  {
    return lc;
  }
  
  public XYChart.Series<Number, Number> getSeries()
  {
    return series;
  }
  
  public void updateSeries(int time, double price)
  {
    series.getData().add(new XYChart.Data(time, price));     
  }
  
  public FXViewOrderPane returnOrderPane()
  {
      return this.op;
  }
  
  
}
