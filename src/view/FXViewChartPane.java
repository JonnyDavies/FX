package view;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
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
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.chart.AreaChart;
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
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class FXViewChartPane extends VBox {
  
  private AreaChart<String, Number> lcEUR;
  private AreaChart<String, Number> lcUSD;
  private AreaChart<String, Number> lcGBP;
  private AreaChart<String, Number> lcCHF;

  private XYChart.Series<String, Number> seriesEUR;
  private XYChart.Series<String, Number> seriesUSD;
  private XYChart.Series<String, Number> seriesGBP;
  private XYChart.Series<String, Number> seriesCHF; 
  
  private HashMap<String,XYChart.Series<String, Number>> allSeries;
  private FXViewOrderPane op;
  
  private NumberAxis   yAxisEUR;
  private NumberAxis   yAxisUSD;
  private NumberAxis   yAxisGBP;
  private NumberAxis   yAxisCHF;

  private CategoryAxis xAxisEUR;  
  private CategoryAxis xAxisUSD;  
  private CategoryAxis xAxisGBP;
  private CategoryAxis xAxisCHF;
  
  private TabPane tp;
  
  private ArrayList<String> timeInSecondsEUR;
  private ArrayList<String> timeInSecondsUSD;
  private ArrayList<String> timeInSecondsGBP;
  private ArrayList<String> timeInSecondsCHF;

  
  private Calendar calendarEUR;
  private Calendar calendarUSD;
  private Calendar calendarGBP;
  private Calendar calendarCHF;
  private Tab t1, t2, t3, t4; 
  DecimalFormat df = new DecimalFormat("#.000"); 

  private SimpleDateFormat sdf;

  
  public FXViewChartPane()
  {
    // intialise series
    allSeries = new HashMap<String,XYChart.Series<String, Number>>();
    
    SplitPane sp = new SplitPane(); 
    sp.setOrientation(Orientation.VERTICAL);  

    this.tp = new TabPane();
    this.addTabPane("EUR/USD", "1.006");


    this.op = new FXViewOrderPane();
       
    
    sp.getItems().addAll(tp, op);
    sp.setDividerPositions(0.8);

    this.getChildren().add(sp);
    this.setStyle("-fx-background-color:   #4d4d4d;");
    this.setPadding(new Insets( 0, 0, 0, 0));
  }
  
  public void addTabPane(String currency, String currencyPrice)
  {

    switch (currency)
    {
      case "EUR/USD" :       
        t1 =  new Tab();
        t1.setText(currency);
        t1.setContent(addEURPane(currency, currencyPrice)); 
        this.tp.getTabs().addAll(t1); 
        break;
        
      case "USD/JPY" :       
        t2 =  new Tab();
        t2.setText(currency);
        t2.setContent(addUSDPane(currency, currencyPrice)); 
        this.tp.getTabs().addAll(t2); 
        break;
        
      case "GBP/USD" :
        t3 =  new Tab();
        t3.setText(currency);
        t3.setContent(addGBPPane(currency, currencyPrice)); 
        this.tp.getTabs().addAll(t3); 
        break;
        
      case "USD/CHF" :
        t4 =  new Tab();
        t4.setText(currency);
        t4.setContent(addCHFPane(currency, currencyPrice)); 
        this.tp.getTabs().addAll(t4); 
        break;
    }
    
  }
  
 
  
  public VBox addEURPane(String currency, String currencyPrice) 
  {
    allSeries.put("EUR/USD", this.seriesEUR = new AreaChart.Series<>());
    
    VBox vb = new VBox();
    vb.setStyle("-fx-background-color :  #e6e6e6");
    vb.setPadding(new Insets(10, 10, 10, 10));

    timeInSecondsEUR = new ArrayList<>();
    sdf = new SimpleDateFormat("HH:mm:ss");    
    
    calendarEUR = Calendar.getInstance();
    
    for(int i = 0; i < 100; i++){    
      calendarEUR.add(Calendar.SECOND,1);
      timeInSecondsEUR.add(sdf.format(calendarEUR.getTime()));     
    }
        
    ObservableList<String> options = 
    FXCollections.observableArrayList(timeInSecondsEUR); 
    
    
    xAxisEUR = new CategoryAxis(options);
    xAxisEUR.invalidateRange(options);
    xAxisEUR.setTickLabelRotation(90.0);
    xAxisEUR.setAutoRanging(false);
    xAxisEUR.setStartMargin(0.0);
    xAxisEUR.setEndMargin(0.0);

    yAxisEUR = new NumberAxis();
    yAxisEUR.setAutoRanging(false);
    yAxisEUR.setForceZeroInRange(false);

    yAxisEUR.setTickUnit(0.001);  
    
    
    BigDecimal lower = new BigDecimal(currencyPrice);
    lower = lower.setScale(1, RoundingMode.HALF_UP);
    BigDecimal upper = lower.add(new BigDecimal("0.1"));  
    lower = lower.setScale(3, RoundingMode.HALF_UP);
    upper = upper.setScale(3, RoundingMode.HALF_UP);

    // calculate bounds
    
    yAxisEUR.setLowerBound(lower.doubleValue());
    yAxisEUR.setUpperBound(upper.doubleValue());
    
 
    
    yAxisEUR.setTickLabelFormatter(new NumberAxis.DefaultFormatter(yAxisEUR) {
      
      @Override
      public String toString(Number object) {
        return String.format("%.3f", object);
      }
    });

    lcEUR = new AreaChart<>(xAxisEUR, yAxisEUR); 
    lcEUR.setPrefSize(650.0, 850.0);
    lcEUR.setStyle(".chart-line-symbol { -fx-background-color: null, null }");    
    lcEUR.setLegendVisible(false);
    lcEUR.setCreateSymbols(false); //hide dots       
    
    lcEUR.getData().add(allSeries.get(currency));  
    
    Node fill = this.seriesEUR.getNode().lookup(".chart-series-area-fill"); // only for AreaChart
    Node line = this.seriesEUR.getNode().lookup(".chart-series-area-line");

    Color color = Color.RED; // or any other color
    
    lcEUR.setAnimated(false);

    String rgb = String.format("%d, %d, %d",
            (int) (color.getRed() * 255),
            (int) (color.getGreen() * 255),
            (int) (color.getBlue() * 255));

    fill.setStyle("-fx-fill: rgba(" + rgb + ", 0.15);");
    line.setStyle("-fx-stroke: rgba(" + rgb + ", 1.0);");
   
    
    vb.getChildren().addAll(lcEUR);
    
    return vb;
  }
  
  
  public VBox addUSDPane(String currency, String currencyPrice) 
  {
    allSeries.put("USD/JPY", this.seriesUSD = new AreaChart.Series<>());
    
    VBox vb = new VBox();
    vb.setStyle("-fx-background-color :  #e6e6e6");
    vb.setPadding(new Insets(10, 10, 10, 10));

    timeInSecondsUSD = new ArrayList<>();
    sdf = new SimpleDateFormat("HH:mm:ss");    
    
    calendarUSD = Calendar.getInstance();
    
    for(int i = 0; i < 100; i++){    
      calendarUSD.add(Calendar.SECOND,1);
      timeInSecondsUSD.add(sdf.format(calendarUSD.getTime()));     
    }
        
    ObservableList<String> options = 
    FXCollections.observableArrayList(timeInSecondsUSD); 
    
    
    xAxisUSD = new CategoryAxis(options);
    xAxisUSD.invalidateRange(options);
    xAxisUSD.setTickLabelRotation(90.0);
    xAxisUSD.setAutoRanging(false);
    xAxisUSD.setStartMargin(0.0);
    xAxisUSD.setEndMargin(0.0);

    yAxisUSD = new NumberAxis();
    yAxisUSD.setAutoRanging(false);
    yAxisUSD.setForceZeroInRange(false);
    
    yAxisUSD.setTickLabelFormatter(new NumberAxis.DefaultFormatter(yAxisUSD) {
      
      @Override
      public String toString(Number object) {
        return String.format("%.3f", object);
      }
    });

      
    BigDecimal lower = new BigDecimal(currencyPrice);
    lower = lower.setScale(1, RoundingMode.HALF_UP);
    BigDecimal upper = lower.add(new BigDecimal("0.1"));  
    lower = lower.setScale(3, RoundingMode.HALF_UP);
    upper = upper.setScale(3, RoundingMode.HALF_UP);

    // calculate bounds
    
    yAxisUSD.setLowerBound(lower.doubleValue());
    yAxisUSD.setUpperBound(upper.doubleValue());
    
    
    
    yAxisUSD.setTickUnit(0.001);   

    lcUSD = new AreaChart<>(xAxisUSD, yAxisUSD); 
    lcUSD.setPrefSize(650.0, 850.0);
    lcUSD.setStyle(".chart-line-symbol { -fx-background-color: null, null }");    
//    lcUSD.setStyle(".chart-series-area-line { fx-stroke: CHART_COLOR_2 }");    
//    lcUSD.setStyle(".chart-series-area-fill { fx-stroke: CHART_COLOR_2 }");    

    
    lcUSD.setLegendVisible(false);
    lcUSD.setCreateSymbols(false); //hide dots       
    
    lcUSD.getData().add(allSeries.get(currency));   
    lcUSD.setAnimated(false);
    
    Node fill = this.seriesUSD.getNode().lookup(".chart-series-area-fill"); // only for AreaChart
    Node line = this.seriesUSD.getNode().lookup(".chart-series-area-line");

    Color color = Color.BLUE; // or any other color

    String rgb = String.format("%d, %d, %d",
            (int) (color.getRed() * 255),
            (int) (color.getGreen() * 255),
            (int) (color.getBlue() * 255));

    fill.setStyle("-fx-fill: rgba(" + rgb + ", 0.15);");
    line.setStyle("-fx-stroke: rgba(" + rgb + ", 1.0);");
    
    
    vb.getChildren().addAll(lcUSD);
    
    return vb;
  }

  public VBox addGBPPane(String currency, String currencyPrice) 
  {
    allSeries.put("GBP/USD", this.seriesGBP = new AreaChart.Series<>());
    
    VBox vb = new VBox();
    vb.setStyle("-fx-background-color :  #e6e6e6");
    vb.setPadding(new Insets(10, 10, 10, 10));

    timeInSecondsGBP = new ArrayList<>();
    sdf = new SimpleDateFormat("HH:mm:ss");    
    
    calendarGBP = Calendar.getInstance();
    
    for(int i = 0; i < 100; i++){    
      calendarGBP.add(Calendar.SECOND,1);
      timeInSecondsGBP.add(sdf.format(calendarGBP.getTime()));     
    }
        
    ObservableList<String> options = 
    FXCollections.observableArrayList(timeInSecondsGBP); 
    
    xAxisGBP = new CategoryAxis(options);
    xAxisGBP.invalidateRange(options);
    xAxisGBP.setTickLabelRotation(90.0);
    xAxisGBP.setAutoRanging(false);
    xAxisGBP.setStartMargin(0.0);
    xAxisGBP.setEndMargin(0.0);

    yAxisGBP = new NumberAxis();
    yAxisGBP.setAutoRanging(false);
    yAxisGBP.setForceZeroInRange(false);
    
    yAxisGBP.setTickLabelFormatter(new NumberAxis.DefaultFormatter(yAxisGBP) {
      
      @Override
      public String toString(Number object) {
        return String.format("%.3f", object);
      }
    });
    
    
    
    BigDecimal lower = new BigDecimal(currencyPrice);
    lower = lower.setScale(1, RoundingMode.HALF_UP);
    BigDecimal upper = lower.add(new BigDecimal("0.1"));  
    lower = lower.setScale(3, RoundingMode.HALF_UP);
    upper = upper.setScale(3, RoundingMode.HALF_UP);
    
    // calculate bounds
    
    
    
    yAxisGBP.setLowerBound(lower.doubleValue());
    yAxisGBP.setUpperBound(upper.doubleValue());
    
    
    
    yAxisGBP.setTickUnit(0.001);   

    lcGBP = new AreaChart<>(xAxisGBP, yAxisGBP); 
    lcGBP.setPrefSize(650.0, 850.0);
    lcGBP.setStyle(".chart-line-symbol { -fx-background-color: null, null }");    

    lcGBP.setLegendVisible(false);
    lcGBP.setCreateSymbols(false); //hide dots       
    
    lcGBP.getData().add(allSeries.get(currency));
    lcGBP.setAnimated(false);
    
    Node fill = this.seriesGBP.getNode().lookup(".chart-series-area-fill"); // only for AreaChart
    Node line = this.seriesGBP.getNode().lookup(".chart-series-area-line");

    Color color = Color.BLACK; // or any other color

    String rgb = String.format("%d, %d, %d",
            (int) (color.getRed() * 255),
            (int) (color.getGreen() * 255),
            (int) (color.getBlue() * 255));

    fill.setStyle("-fx-fill: rgba(" + rgb + ", 0.15);");
    line.setStyle("-fx-stroke: rgba(" + rgb + ", 1.0);");
 
    vb.getChildren().addAll(lcGBP);
    
    return vb;
  }

  public VBox addCHFPane(String currency, String currencyPrice) 
  {
    allSeries.put("USD/CHF", this.seriesCHF = new AreaChart.Series<>());

    VBox vb = new VBox();
    vb.setStyle("-fx-background-color :  #e6e6e6");
    vb.setPadding(new Insets(10, 10, 10, 10));

    timeInSecondsCHF = new ArrayList<>();
    sdf = new SimpleDateFormat("HH:mm:ss");    
    
    calendarCHF = Calendar.getInstance();
    
    for(int i = 0; i < 100; i++){    
      calendarCHF.add(Calendar.SECOND,1);
      timeInSecondsCHF.add(sdf.format(calendarCHF.getTime()));     
    }
        
    ObservableList<String> options = 
    FXCollections.observableArrayList(timeInSecondsCHF); 
    
    xAxisCHF = new CategoryAxis(options);
    xAxisCHF.invalidateRange(options);
    xAxisCHF.setTickLabelRotation(90.0);
    xAxisCHF.setAutoRanging(false);
    xAxisCHF.setStartMargin(0.0);
    xAxisCHF.setEndMargin(0.0);

    yAxisCHF = new NumberAxis();
    yAxisCHF.setAutoRanging(false);
    yAxisCHF.setForceZeroInRange(false);
    
    yAxisCHF.setTickLabelFormatter(new NumberAxis.DefaultFormatter(yAxisCHF) {
      
      @Override
      public String toString(Number object) {
        return String.format("%.3f", object);
      }
    });
    
    
    // calculate bounds
    
    BigDecimal lower = new BigDecimal(currencyPrice);
    lower = lower.setScale(1, RoundingMode.HALF_UP);
    BigDecimal upper = lower.add(new BigDecimal("0.1"));  
    lower = lower.setScale(3, RoundingMode.HALF_UP);
    upper = upper.setScale(3, RoundingMode.HALF_UP);
    
    yAxisCHF.setLowerBound(lower.doubleValue());
    yAxisCHF.setUpperBound(upper.doubleValue());
      
    yAxisCHF.setTickUnit(0.001);   

    lcCHF = new AreaChart<>(xAxisCHF, yAxisCHF); 
    lcCHF.setPrefSize(650.0, 850.0);
    lcCHF.setStyle(".chart-line-symbol { -fx-background-color: null, null }");    
    lcCHF.setLegendVisible(false);
    lcCHF.setCreateSymbols(false); //hide dots       
    
    lcCHF.getData().add(allSeries.get(currency)); 
    lcCHF.setAnimated(false);
    
    Node fill = this.seriesCHF.getNode().lookup(".chart-series-area-fill"); // only for AreaChart
    Node line = this.seriesCHF.getNode().lookup(".chart-series-area-line");

    Color color = Color.GREEN; // or any other color

    String rgb = String.format("%d, %d, %d",
            (int) (color.getRed() * 255),
            (int) (color.getGreen() * 255),
            (int) (color.getBlue() * 255));

    fill.setStyle("-fx-fill: rgba(" + rgb + ", 0.15);");
    line.setStyle("-fx-stroke: rgba(" + rgb + ", 1.0);");

    vb.getChildren().addAll(lcCHF);
    
    return vb;
  }
  
  public void setCloseRequesTab1 (EventHandler<Event> e)
  {
        t1.setOnCloseRequest(e);
  }
  
  public void setCloseRequesTab2 (EventHandler<Event> e)
  {
        t2.setOnCloseRequest(e);
  }

  public void setCloseRequesTab3 (EventHandler<Event> e)
  {
        t3.setOnCloseRequest(e);
  }

  public void setCloseRequesTab4 (EventHandler<Event> e)
  {
        t4.setOnCloseRequest(e);
  }

  public AreaChart<String, Number> getChartEur()
  {
    return this.lcEUR;
  }
  
  public AreaChart<String, Number> getChartUSD()
  {
    return this.lcUSD;
  }
  public AreaChart<String, Number> getChartGBP()
  {
    return this.lcGBP;
  }
  public AreaChart<String, Number> getChartCHF()
  {
    return this.lcCHF;
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
  
  public void removeSeriesElement(String currency, int index)
  {
    // add time label?
    allSeries.get(currency).getData().remove(index);    
  }
    
  public FXViewOrderPane returnOrderPane()
  {
      return this.op;
  }
  
  public TabPane getTabPanes()
  {
      return this.tp;
  }
  
  // =================================== need versions of these methods for the other three CHF etc charts.
  
  public NumberAxis getYAxisEUR()
  {
    return this.yAxisEUR;
  }

  public void setYAxisUpperEUR(double num)
  {
     this.yAxisEUR.setUpperBound(num);
  }
  
  public void setYAxisLowerEUR(double num)
  {
     this.yAxisEUR.setLowerBound(num);
  }
  
  public double getYAxisUpperEUR()
  {
     return this.yAxisEUR.getUpperBound();
  }
  
  public double setYAxisLowerEUR()
  {
     return this.yAxisEUR.getLowerBound();
  }
  
  public double getYAxisLowerEUR()
  {
     return this.yAxisEUR.getLowerBound();
  }
  
  public CategoryAxis getXAxisEUR()
  {
      return this.xAxisEUR;
  }
  
  public ObservableList<String> getXAxisCategoriesEUR()
  {
      return this.xAxisEUR.getCategories();
  }
  
  public void setXAxisCategorySpacingEUR()
  {
       this.xAxisEUR.categorySpacingProperty();
  }
  
  public void setXAxisCategoriesEUR(ObservableList<String> newList)
  {
      this.xAxisEUR.setCategories(newList);
  }
  
  public NumberAxis getYAxisUSD()
  {
    return this.yAxisUSD;
  }

  public void setYAxisUpperUSD(double num)
  {
     this.yAxisUSD.setUpperBound(num);
  }
  
  public void setYAxisLowerUSD(double num)
  {
     this.yAxisUSD.setLowerBound(num);
  }
  
  public double getYAxisUpperUSD()
  {
     return this.yAxisUSD.getUpperBound();
  }
  
  public double setYAxisLowerUSD()
  {
     return this.yAxisUSD.getLowerBound();
  }
  
  public double getYAxisLowerUSD()
  {
     return this.yAxisUSD.getLowerBound();
  }
  
  public CategoryAxis getXAxisUSD()
  {
      return this.xAxisUSD;
  }
  
  public ObservableList<String> getXAxisCategoriesUSD()
  {
      return this.xAxisUSD.getCategories();
  }
  
  public void setXAxisCategorySpacingUSD()
  {
       this.xAxisUSD.categorySpacingProperty();
  }
  
  public void setXAxisCategoriesUSD(ObservableList<String> newList)
  {
      this.xAxisUSD.setCategories(newList);
  }
  
  public NumberAxis getYAxisGBP()
  {
    return this.yAxisGBP;
  }

  public void setYAxisUpperGBP(double num)
  {
     this.yAxisGBP.setUpperBound(num);
  }
  
  public void setYAxisLowerGBP(double num)
  {
     this.yAxisGBP.setLowerBound(num);
  }
  
  public double getYAxisUpperGBP()
  {
     return this.yAxisGBP.getUpperBound();
  }
  
  public double setYAxisLowerGBP()
  {
     return this.yAxisGBP.getLowerBound();
  }
  
  public double getYAxisLowerGBP()
  {
     return this.yAxisGBP.getLowerBound();
  }
  
  public CategoryAxis getXAxisGBP()
  {
      return this.xAxisGBP;
  }
  
  public ObservableList<String> getXAxisCategoriesGBP()
  {
      return this.xAxisGBP.getCategories();
  }
  
  public void setXAxisCategorySpacingGBP()
  {
       this.xAxisGBP.categorySpacingProperty();
  }
  
  public void setXAxisCategoriesGBP(ObservableList<String> newList)
  {
      this.xAxisGBP.setCategories(newList);
  }

  public NumberAxis getYAxisCHF()
  {
    return this.yAxisCHF;
  }

  public void setYAxisUpperCHF(double num)
  {
     this.yAxisCHF.setUpperBound(num);
  }
  
  public void setYAxisLowerCHF(double num)
  {
     this.yAxisCHF.setLowerBound(num);
  }
  
  public double getYAxisUpperCHF()
  {
     return this.yAxisCHF.getUpperBound();
  }
  
  public double setYAxisLowerCHF()
  {
     return this.yAxisCHF.getLowerBound();
  }
  
  public double getYAxisLowerCHF()
  {
     return this.yAxisCHF.getLowerBound();
  }
  
  public CategoryAxis getXAxisCHF()
  {
      return this.xAxisCHF;
  }
  
  public ObservableList<String> getXAxisCategoriesCHF()
  {
      return this.xAxisCHF.getCategories();
  }
  
  public void setXAxisCategorySpacingCHF()
  {
       this.xAxisCHF.categorySpacingProperty();
  }
  
  public void setXAxisCategoriesCHF(ObservableList<String> newList)
  {
      this.xAxisCHF.setCategories(newList);
  }
  
  public SimpleDateFormat getSDF()
  {
    return this.sdf;
  }
 
  public ArrayList<String> getTimeSecondsEUR(){
    return timeInSecondsEUR;
  }
  
  public void setTimeSecondsEUR(ArrayList<String> timeInSeconds){
     this.timeInSecondsEUR = timeInSeconds;
  }
  
  
  public void removeSubTimeInSecondsEUR(int start, int finish)
  {
    this.timeInSecondsEUR.subList(start,finish).clear();

  }
  
  public void addToTimeSecondsEUR(int index, String time)
  {
    timeInSecondsEUR.add(index, time);
  }
  
  public ArrayList<String> getTimeSecondsUSD(){
    return timeInSecondsUSD;
  }
  
  public void setTimeSecondsUSD(ArrayList<String> timeInSeconds){
     this.timeInSecondsUSD = timeInSeconds;
  }
  
  public void removeSubTimeInSecondsUSD(int start, int finish)
  {
    this.timeInSecondsUSD.subList(start,finish).clear();

  }
  
  public void addToTimeSecondsUSD(int index, String time)
  {
    timeInSecondsUSD.add(index, time);
  }
  
  public ArrayList<String> getTimeSecondsGBP(){
    return timeInSecondsGBP;
  }
  
  public void setTimeSecondsGBP(ArrayList<String> timeInSeconds){
     this.timeInSecondsGBP = timeInSeconds;
  }
  
  public void removeSubTimeInSecondsGBP(int start, int finish)
  {
    this.timeInSecondsGBP.subList(start,finish).clear();

  }
  
  public void addToTimeSecondsGBP(int index, String time)
  {
    timeInSecondsGBP.add(index, time);
  }
  
  public ArrayList<String> getTimeSecondsCHF(){
    return timeInSecondsCHF;
  }
  
  public void setTimeSecondsCHF(ArrayList<String> timeInSeconds){
     this.timeInSecondsCHF = timeInSeconds;
  }
  
  public void removeSubTimeInSecondsCHF(int start, int finish)
  {
    this.timeInSecondsCHF.subList(start,finish).clear();

  }
  
  public void addToTimeSecondsCHF(int index, String time)
  {
    timeInSecondsCHF.add(index, time);
  }
    
  public Date getCalenderInstanceTimeEUR()
  {
    return this.calendarEUR.getTime();
  }
  
  public void addToCalenderEUR()
  {
    this.calendarEUR.add(Calendar.SECOND, 1);
  }
    
  public Date getCalenderInstanceTimeUSD()
  {
    return this.calendarUSD.getTime();
  }
  
  public void addToCalenderUSD()
  {
    this.calendarUSD.add(Calendar.SECOND, 1);
  }
    
  public Date getCalenderInstanceTimeGBP()
  {
    return this.calendarGBP.getTime();
  }
  
  public void addToCalenderGBP()
  {
    this.calendarGBP.add(Calendar.SECOND, 1);
  }
    
  public Date getCalenderInstanceTimeCHF()
  {
    return this.calendarCHF.getTime();
  }
  
  public void addToCalenderCHF()
  {
    this.calendarCHF.add(Calendar.SECOND, 1);
  }
   
  public Tab getEURtab()
  {
      return t1;
  }
  
  public Tab getUSDtab()
  {
      return t2;
  }
  
  public Tab getGBPtab()
  {
      return t3;
  }
  
  public Tab getCHFtab()
  {
      return t4;
  }
}
