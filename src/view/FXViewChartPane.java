package view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

public class FXViewChartPane extends VBox {
  
  private Button b1;
  private LineChart<Number, Number> lc;
  private XYChart.Series<Number, Number> series;
  private FXViewOrderPane op;
  private NumberAxis xAxis;
  private NumberAxis yAxis;

  
  public FXViewChartPane(){

    TabPane tb = new TabPane();
    
    final Tab t1 = new Tab();
    t1.setText("EUR/USD");
    t1.setContent(addFlowPane());
       
    tb.getTabs().addAll(t1);
    op = new FXViewOrderPane();
    this.getChildren().addAll(tb, op);
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
    yAxis.setTickUnit  (0.001);
  
    lc = new LineChart<>(xAxis, yAxis); 
    lc.setPrefSize(850.0, 1050.0);
    lc.setStyle(".chart-line-symbol { -fx-background-color: null, null }");    
    lc.setLegendVisible(false);
    
    series = new LineChart.Series<>();
    lc.getData().add(series); 
    
    b1 = new Button("Test");
    b1.setOnAction(e -> { 
          //this.clear();
          System.out.println("TESTING");
          series.getData().add(new XYChart.Data(  7,    1.012));

    });

    vb.getChildren().addAll(lc,b1);
    return vb;
  }

  private  XYChart.Series<Number, Number> returnSeries() {
  
      //defining a series       
      //populating the series with data
      
                   // add(new XYChart.Data (TIME,    PRICE);
                                          //time,    price
      series.getData().add(new XYChart.Data(  0,    1.000));
      series.getData().add(new XYChart.Data(  1,    1.001));      

      return series;     
  } 
  
  public void addUpHandler(EventHandler<ActionEvent> handler) 
  {
      b1.setOnAction(handler);
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
  
  
}
