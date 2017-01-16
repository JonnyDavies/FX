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

public class FXViewChartPane extends TabPane {
  
  private Button b1, b2;
  LineChart<Number, Number> lc;
  
  public FXViewChartPane(){

    final Tab t1 = new Tab();
    t1.setText("EUR/USD");
    t1.setContent(addFlowPane());
    
    final Tab t2 = new Tab();
    t2.setText("GBP/USD");
    t2.setContent(addFlowPane());
       
    this.getTabs().addAll(t1,t2);
  }
  
  public FlowPane addFlowPane() {
    
    FlowPane flow = new FlowPane();
    flow.setStyle("-fx-background-color :  #e6e6e6");
    flow.setPadding(new Insets(10, 10, 10, 10));
    flow.setVgap(4);
    flow.setHgap(4);
    Double[] data = {0.1, 0.4, 0.5, 0.7, 0.9, 1.0, 0.9, 0.8};
     
    b1 = new Button("Up");
    b1.setMinSize(100,50);
    b1.setMaxSize(100,50);
    b1.setPrefSize(100,50);
    
    b2 = new Button("Down");
    b2.setMinSize(100,50);
    b2.setMaxSize(100,50);
    b2.setPrefSize(100,50);

    lc = createLineChart(data);  
    
    flow.getChildren().addAll(lc,b1,b2);
    return flow;
  }

  private LineChart<Number, Number> createLineChart(Double[] axisValues) {
  
      //defining the axes
      final NumberAxis xAxis = new NumberAxis();
      final NumberAxis yAxis = new NumberAxis();
      xAxis.setLabel("Time");
      
      //creating the chart
      final LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);    
      lineChart.setTitle("");
      lineChart.setPrefSize(950.0, 650.0);
      
      //defining a series
      XYChart.Series<Number, Number> series = new LineChart.Series<>();
      series.setName("X Axis");
      
      //populating the series with data
      series.getData().add(new XYChart.Data(4.2, 193.2));
      series.getData().add(new XYChart.Data(2.8, 33.6));
      series.getData().add(new XYChart.Data(6.2, 24.8));
      series.getData().add(new XYChart.Data(1, 14));
      series.getData().add(new XYChart.Data(1.2, 26.4));
      series.getData().add(new XYChart.Data(4.4, 114.4));
      series.getData().add(new XYChart.Data(8.5, 323));
      series.getData().add(new XYChart.Data(6.9, 289.8));
      series.getData().add(new XYChart.Data(9.9, 287.1));
      
      lineChart.getData().add(series);
      return lineChart;
      
  } 
  
  public void addUpHandler(EventHandler<ActionEvent> handler) {
      b1.setOnAction(handler);
  }
  
  public void addToChart(){
    XYChart.Series<Number, Number> series2 = new LineChart.Series<>();
    series2.getData().add(new XYChart.Data(9.9, 300.1));
    lc.getData().add(series2);
  }


}
