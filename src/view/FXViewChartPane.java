package view;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class FXViewChartPane extends VBox {
  
  private LineChart<String, Number> lc;
  private XYChart.Series<String, Number> seriesEUR;
  private XYChart.Series<String, Number> seriesUSD;
  private XYChart.Series<String, Number> seriesGBP;
  private XYChart.Series<String, Number> seriesCHF;  
  private HashMap<String,XYChart.Series<String, Number>> allSeries;
  private FXViewOrderPane op;
  private NumberAxis yAxis;
  private CategoryAxis xAxis;
  private Timeline tl1;
  private TabPane tp;
  private ArrayList<String> timeInSeconds;
  private Calendar calendar;
  private SimpleDateFormat sdf;

  
  public FXViewChartPane()
  {
    // intialise series???
    allSeries = new HashMap<String,XYChart.Series<String, Number>>();
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
    
    
    // ===================================================//
    tl1 = new Timeline();
 
    // ===================================================//
    
      
    lc.setAnimated(false);
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
    // need to think about how this is managed for each tab 

    timeInSeconds = new ArrayList<>();
    sdf = new SimpleDateFormat("HH:mm:ss");    
    
    calendar = Calendar.getInstance();
    
    for(int i = 0; i < 100; i++){    
      calendar.add(Calendar.SECOND,1);
      timeInSeconds.add(sdf.format(calendar.getTime()));     
    }
     
    
    
    ObservableList<String> options = 
    FXCollections.observableArrayList(timeInSeconds); 
    
    
    xAxis = new CategoryAxis(options);
    xAxis.invalidateRange(options);
    xAxis.setTickLabelRotation(90.0);
    xAxis.setAutoRanging(false);

    yAxis = new NumberAxis();
    
    // time
//    xAxis.setLowerBound(0);
//    xAxis.setUpperBound(100);
//    xAxis.setTickUnit(1);
//    xAxis.setForceZeroInRange(false);
//    xAxis.setAutoRanging(false);
    
    // price  
    yAxis.setAutoRanging(false);
    yAxis.setForceZeroInRange(false);
    yAxis.setLowerBound(1.000);
    yAxis.setUpperBound(1.100);
    yAxis.setTickUnit(0.001);   
    //    yAxis.setTickMarkVisible(false);
  
    lc = new LineChart<>(xAxis, yAxis); 
    lc.setPrefSize(650.0, 850.0);
    lc.setStyle(".chart-line-symbol { -fx-background-color: null, null }");    
    lc.setLegendVisible(false);
    lc.setCreateSymbols(false); //hide dots       
    // xAxis.setLabel("Time");
    
    System.out.println("Testing getting category spacing: ?1?!?1 " + xAxis.getCategorySpacing());

    
    lc.getData().add(allSeries.get(currency));   
    vb.getChildren().addAll(lc);
    return vb;
  }

  public LineChart<String, Number> getChart()
  {
    return lc;
  }
  
  public XYChart.Series<String, Number> getCertainSeries(String currency)
  {
    return allSeries.get(currency);
  }
  
  public void removeSeries(String currency)
  {
    this.allSeries.remove(currency);
  }
  
  public void updateSeries(String currency, String time, double price)
  {
    // add time label?
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
  
  public NumberAxis getYAxis()
  {
    return this.yAxis;
  }

  public void setYAxisUpper(double num)
  {
     this.yAxis.setUpperBound(num);
  }
  
  public void setYAxisLower(double num)
  {
     this.yAxis.setLowerBound(num);
  }
  
  public double getYAxisUpper()
  {
     return this.yAxis.getUpperBound();
  }
  
  public double setYAxisLower()
  {
     return this.yAxis.getLowerBound();
  }
  
  public double getYAxisLower()
  {
     return this.yAxis.getLowerBound();
  }
  
  public CategoryAxis getXAxis()
  {
      return this.xAxis;
  }
  
  public ObservableList<String> getXAxisCategories()
  {
      return this.xAxis.getCategories();
  }
  
  public void setXAxisCategorySpacing()
  {
       this.xAxis.categorySpacingProperty();
  }
  
  public void setXAxisCategories(ObservableList<String> newList)
  {
      this.xAxis.setCategories(newList);
  }
  
  public SimpleDateFormat getSDF()
  {
    return this.sdf;
  }
  
  public ArrayList<String> getTimeSeconds(){
    return timeInSeconds;
  }
  
  public void setTimeSeconds(ArrayList<String> timeInSeconds){
     this.timeInSeconds = timeInSeconds;
  }
  
  
  public void removeSubTimeInSeconds(int start, int finish)
  {
    this.timeInSeconds.subList(start,finish).clear();

  }
  
  public void addToTimeSeconds(int index, String time)
  {
    timeInSeconds.add(index, time);
  }
  
  public Date getCalenderInstanceTime()
  {
    return this.calendar.getTime();
  }
  
  public void addToCalender()
  {
    this.calendar.add(Calendar.SECOND, 1);
  }
     
  public Timeline getTimeline()
  {
    return tl1;
  }
}
