package controller;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import model.Database;
import model.Order;
import model.Trader;
import lib.BCrypt;
import view.FXViewChartPane;
import view.FXViewCurrencyPairPane;
import view.FXViewLoginPage;
import view.FXViewMenuPane;
import view.FXViewNewOrderForm;
import view.FXViewOrderPane;
import view.FXViewRegisterPage;
import view.FXViewRootPane;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Optional;
import java.util.regex.*;

public class FXController {
  
  private FXViewLoginPage lg;
  private FXViewRootPane rp;
  private FXViewRegisterPage re;
  private FXViewChartPane cp;
  private FXViewCurrencyPairPane cupp;
  private FXViewMenuPane mp;
  private FXViewNewOrderForm of;
  private FXViewOrderPane op;                 
  private Trader model;
  
  private Stage window;
  private Stage windowModal;

  private Scene login;
  private Scene register;
  private Scene market;
  private Scene modal;
  
  private static StringProperty message1; // static ? review this
  private static StringProperty message2;
  private static StringProperty message3;
  private static StringProperty message4;
  
  private int notStayingHere = 2;  
  // for bcrypt password
  private static int workload = 12;


  private static String update;
   
  public FXController (FXViewLoginPage view, Trader model)
  {
      this.lg = view;
      this.model = model;
     
      this.rp = new FXViewRootPane();    
      this.re = new FXViewRegisterPage(); 
      this.cp = this.rp.getChartPane();
      
      this.op = this.cp.returnOrderPane();
      // pop order table
      this.populateOrderTableOnStart(this.op.returnTableView());
            
      this.cupp = this.rp.getCurrencyPairPane();
      this.mp = this.rp.getMenuPane();
      this.of = new FXViewNewOrderForm();
          
      this.register = new Scene(this.re);
      this.market = new Scene(this.rp); 
      this.modal = new Scene(this.of, 400, 300);       
      this.attachEventHandlers();   
      
      message1 = new SimpleStringProperty();
      message2 = new SimpleStringProperty();
      message3 = new SimpleStringProperty();
      message4 = new SimpleStringProperty();

  }
  
  
  public void attachEventHandlers()
  {
    this.lg.addRegisterHandler(e -> this.setSceneToBeDisplayed("Register"));
    this.lg.addLoginHandler(e -> this.setSceneToBeDisplayed("Market"));   
    this.re.addBackHandler(e -> this.setSceneToBeDisplayed("Back"));
    this.re.addRegisterInfoHandler(e -> this.setSceneToBeDisplayed("Login"));
    this.mp.addLogOutHandler(e -> this.setSceneToBeDisplayed("Logout"));
    this.mp.addNewOrderHandler(e -> this.newOrderInputBox());
    this.mp.addDeleteOrderHandler(e -> this.deleteOrder());
    this.mp.addOpenChartHandler(e -> this.openCharts());
    this.of.buyNewOrderButtonHandler(e -> this.processBuyOrder());
    this.of.sellNewOrderButtonHandler(e -> this.processSellOrder());
    this.cupp.setEURUSDBuyButtonHandler(e->this.processOneClickBuy("EUR/USD"));
    this.cupp.setEURUSDSellButtonHandler(e->this.processOneClickSell("EUR/USD"));
    this.cupp.setUSDJPYBuyButtonHandler(e->this.processOneClickBuy("USD/JPY"));
    this.cupp.setUSDJPYSellButtonHandler(e->this.processOneClickSell("USD/JPY"));
    this.cupp.setGBPUSDBuyButtonHandler(e->this.processOneClickBuy("GBP/USD"));
    this.cupp.setGBPUSDSellButtonHandler(e->this.processOneClickSell("GBP/USD"));
    this.cupp.setUSDCHFBuyButtonHandler(e->this.processOneClickBuy("USD/CHF"));
    this.cupp.setUSDCHFSellButtonHandler(e->this.processOneClickSell("USD/CHF"));
  }
  
  public void populateOrderTableOnStart(TableView<Order> order)
  { 
    ObservableList<Order> options = FXCollections.observableArrayList(this.model.getOrders());
    this.op.setItemsTableView(options);   
  }
  
  public void newOrderInputBox()
  {
    windowModal = new Stage();
    windowModal.initModality(Modality.APPLICATION_MODAL);
    windowModal.alwaysOnTopProperty();
    windowModal.centerOnScreen();
    windowModal.setScene(modal);
    windowModal.getIcons().removeAll();
    windowModal.showAndWait();  
   
   // this.of.sellButtonHandler(e -> System.out.println("Buy Order"));

  }
  
  public void deleteOrder()
  {
    //get selected from table view
    // delete from table 
    // delete it from Orders    
    // maybe have class variables for this, give each order a unique value????
    int indexOrder;    
    
    Order selected = this.op.returnTableView().getSelectionModel().getSelectedItem();
    
    if(selected != null)
    {
      indexOrder = this.model.getOrders().indexOf(selected);
      this.model.removeOrder(indexOrder);
      ObservableList<Order> orderList =  FXCollections.observableArrayList(this.model.getOrders());
      this.op.setItemsTableView(orderList); 
    }
    
    // deal with this, disable button maybe??
    return;    
  }
  
  public void processOneClickBuy(String currencyPair)
  {
    String quantity;
       
    if(this.cupp.getQuantityToggle1().isSelected())
    {
        quantity = this.cupp.getQuantityToggle1().getText();
       
    }  
    else if( this.cupp.getQuantityToggle2().isSelected())
    {
        quantity = this.cupp.getQuantityToggle2().getText();
    }
    else
    {
        quantity = this.cupp.getQuantityToggle3().getText();
    }
    
    String currency = currencyPair; 
    String direction = "Buy";    
    double price = 0.0;
    double currentPrice = 0.0;
    double takeProfit = 0.0;
    double stopLoss = this.of.returnStopLoss().getValue();
    Integer result = 0;
    boolean oneClickOrder = true;
    
    
    Order buy = new Order(currency, quantity, direction, price, currentPrice, takeProfit, stopLoss, result, oneClickOrder);
    this.model.addOrder(buy);
    ObservableList<Order> orderList =  FXCollections.observableArrayList(this.model.getOrders());
    this.op.setItemsTableView(orderList); 
  }
  
  public HashMap<String,Boolean> findOpenTabs()
  {
    ObservableList<Tab> options = this.cp.getTabPanes().getTabs();
    HashMap<String,Boolean> tabStatus = new HashMap<String,Boolean>();

    boolean tab1Open = false; 
    boolean tab2Open = false;
    boolean tab3Open = false;
    boolean tab4Open = false;
 
    for(Tab t : options){
      if(t.getText().equals("EUR/USD")){
        tab1Open = true;
      }
      if(t.getText().equals("USD/JPY")){
        tab2Open = true;
      }
      if(t.getText().equals("GBP/USD")){
        tab3Open = true;
      }
      if(t.getText().equals("USD/CHF")){
        tab4Open = true;
      }
    }
     
    tabStatus.put("EUR/USD",tab1Open);
    tabStatus.put("USD/JPY",tab2Open);
    tabStatus.put("GBP/USD",tab3Open);
    tabStatus.put("USD/CHF",tab4Open);
    
    return tabStatus;
  }
  
  public void openCharts()
  {
   String selectedChart = this.mp.getChartCombo().getValue();
   HashMap<String,Boolean> openTabs = this.findOpenTabs();
   // if open don't want it open obviously
   // how are we going to do this??
   // Could even have new panes for each?
      
   switch (selectedChart)
   {
     
     case "EUR/USD" :                                                   
                        if(!openTabs.get("EUR/USD")){ 
                          // add tab pane()
                           this.cp.addTabPane("EUR/USD");
                           
                           // add series data
                        }
                        else{
                          // open alert tab already open
                        }
                        
                       
         break;
     case "USD/JPY" :
                          // add tab pane()
                           if(!openTabs.get("USD/JPY")){ 
                             this.cp.addTabPane("USD/JPY");
                           }
                           else{
                             // open alert tab already open
                           }
           
         break;   
     case "GBP/USD" :
                        
                         if(!openTabs.get("GBP/USD")){
                           // add tab pane()
                           this.cp.addTabPane("GBP/USD");
                         }
                         else{
                           // open alert tab already open
                         }
         break;
     case "USD/CHF" :
       
                           if(!openTabs.get("USD/CHF")){ 
                          // add tab pane()
                             this.cp.addTabPane("USD/CHF");
                           }
                           else{
                             // open alert tab already open
                           }
         break; 
   }   
  }
  
  public void processOneClickSell(String currencyPair)
  {
    String quantity;
    
    if(this.cupp.getQuantityToggle1().isSelected())
    {
        quantity = this.cupp.getQuantityToggle1().getText();
       
    }  
    else if( this.cupp.getQuantityToggle2().isSelected())
    {
        quantity = this.cupp.getQuantityToggle2().getText();
    }
    else
    {
        quantity = this.cupp.getQuantityToggle3().getText();
    }
    
    String currency = currencyPair; 
    String direction = "Sell";    
    double price = 0.0;
    
    double currentPrice = 0.0;
    
    double takeProfit = 0.0;
    double stopLoss = this.of.returnStopLoss().getValue();
    Integer result = 0;
    boolean oneClickOrder = true;
    
    
    Order buy = new Order(currency, quantity, direction, price, currentPrice, takeProfit, stopLoss, result, oneClickOrder);
    this.model.addOrder(buy);
    ObservableList<Order> orderList =  FXCollections.observableArrayList(this.model.getOrders());
    this.op.setItemsTableView(orderList); 
  }
  
  
  
  
  public void processBuyOrder()
  {    
    // create an order from new order box
    // add it to Trader's existing orders 
    // or if it is empty populate their list
    // Populate the open order pane
    // Store in the DB
      
    String quantity = this.of.returnQuantity().getValue();
    String currency = this.of.returnCurrencyPair().getValue(); 
    String direction = "Buy";
    
    double price = 0.0;
    double currentPrice = 0.0;  
    double takeProfit = this.of.returnTakeProfit().getValue();
    double stopLoss = this.of.returnStopLoss().getValue();
    Integer result = 0;
    boolean oneClickOrder = false;

    
    Order buy = new Order(currency, quantity, direction, price, currentPrice, takeProfit, stopLoss, result, oneClickOrder);
    this.model.addOrder(buy);
    ObservableList<Order> orderList =  FXCollections.observableArrayList(this.model.getOrders());
    this.op.setItemsTableView(orderList);   
    
    // store in db. =================== database function started finish it off
    
    windowModal.close();
  }
  
  public void processSellOrder()
  {
    System.out.println("Sell Order");
    
    // create an order from new order box
    // add it to Trader's existing orders 
    // or if it is empty populate their list
    // Populate the open order pane
    // Store in the DB      
    String quantity = this.of.returnQuantity().getValue();
    String currency = this.of.returnCurrencyPair().getValue(); 
    String direction = "Sell";
    double price = 0.0;
    double currentPrice = 0.0;    
    double takeProfit = this.of.returnTakeProfit().getValue();
    double stopLoss = this.of.returnStopLoss().getValue();
    Integer result = 0;
    boolean oneClickOrder = false;

  
    Order buy = new Order(currency, quantity, direction, price, currentPrice, takeProfit, stopLoss, result, oneClickOrder);
    this.model.addOrder(buy);
    ObservableList<Order> orderList =  FXCollections.observableArrayList(this.model.getOrders());
    this.op.setItemsTableView(orderList);   
    
    // store in db. =================== database function started finish it off
    
    windowModal.close();
  }
  
  public void startSocketListener()
  {
    FirstLineService service = new FirstLineService();
    service.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
      @Override
      public void handle(WorkerStateEvent t) {
          System.out.println("done:" + t.getSource().getValue());
      }
    });
    // bindSocketValuesToLabels();
    service.start();   
  }
  
  private static class FirstLineService extends Service<String> 
  {
    
    protected Task<String> createTask() {
              
       return new Task<String>() {
            protected String call() 
                throws IOException, MalformedURLException {     
              try (
                  Socket kkSocket = new Socket("PC",4444);   
                  // 10.34.98.62 Uni IP
                  // 192.168.1.20 ethernet
              
                  PrintWriter out = new PrintWriter(kkSocket.getOutputStream(), true);         
                  BufferedReader in = new BufferedReader( new InputStreamReader(kkSocket.getInputStream()) );    
              )  
              {  
                  BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));        
                  String fromServer;          
                  while ((fromServer = in.readLine()) != null) {
                    
                      // Platform.runLater? 
                      
                      update = fromServer;
                      System.out.println(update);
                      String[] s = update.split("-");
                      
                        Platform.runLater(new Runnable() {
                         public void run() {
                             message1.set(s[0]);
                             message2.set(s[1]);                         
                             message3.set(s[2]);                         
                             message4.set(s[3]);                             
                         }
                        });
                        
            
                      if (fromServer.equals("Bye."))
                          break;     
                  }
              }
              catch (UnknownHostException e) 
              {
                  System.err.println("Don't know about host");
                  System.exit(1);
                  
              } 
              catch (IOException e) 
              {
                  System.err.println("Couldn't get I/O for the connection to ");
                  System.exit(1);
              }              
              return "Test";
            }
        };
    }
  }
  
  public void startTimeline()
  {
    Timeline tl = this.cp.getTimeline();
    tl.getKeyFrames().add(
    new KeyFrame(Duration.millis(500), 
       new EventHandler<ActionEvent>() { 
          @Override public void handle(ActionEvent actionEvent) {          
                updateCharts();            
              }      
         }
     ));
    tl.setCycleCount(Animation.INDEFINITE);
    tl.setAutoReverse(true);
    tl.play(); 
  }
  
  public void setSceneToBeDisplayed(String nextScreen)
  {
        
        switch(nextScreen)
        {
           case "Login":  
             // validation           
             // put a condition round this if validation fails return back to page
             if(this.validateTraderDetails())
             {
               this.setTraderDetails();
               window.setScene(login);
             }
             else
             {
               window.setScene(register);
             }
             break;
           case "Back":
             window.setScene(login);
             break;
           case "Market":
             if (this.authenticate())
             {
               this.startSocketListener();
               // move this
               window.setScene(market);
               this.startTimeline();

             }
             else
             {
               window.setScene(login);
             }
             break;
           case "Logout":
             window.setScene(login);
             break;
           case "Register":
             window.setScene(register);
             break;
         }
        
        window.show();
        window.setMaximized(true);
  }

  
  public void setStage(Stage stage)
  {  
    this.window = stage;    
  }
  
  public void setScene(Scene scene)
  {  
    this.login = scene;    
  }
  
  public void setTraderDetails()
  {
    System.out.println(this.re.getFirstName());
    Database db = new Database();
   
    if (!db.doesTableExist("trader"))
    {
        db.createTable();
    }
    else
    {
      System.out.println("TABLE EXISTS");
    }
    
    // store registration detail values
    ArrayList<String> traderDetails = new ArrayList<String>();
    traderDetails.add(this.re.getFirstName().getText());
    traderDetails.add(this.re.getLastName().getText());
    traderDetails.add(this.re.getEmail().getText());
    
    // hash password using Bcrypt for security.   
    String salt = BCrypt.gensalt(workload);
    String hashed_password = BCrypt.hashpw(this.re.getPassword().getText(), salt); 
    traderDetails.add(hashed_password);
    
    db.insertTraderDetails(traderDetails);    
    // successful message on logon page
  }
  
  public boolean validateTraderDetails()
  {
    boolean traderDetailsValid = true;
        
    if(validateName(this.re.getFirstName().getText()))
    {
      System.out.println("The first name is valid!!!!");
    }
    else
    {
      this.re.getFirstName().setStyle("-fx-text-box-border: red");
      this.re.getErrorFirstName().setVisible(true);
      traderDetailsValid = false;
    }
    
    if(validateName(this.re.getLastName().getText()))
    {
      System.out.println("The first name is valid!!!!");
    }
    else
    {
      System.out.println("This isn't valid mate :(");   
      this.re.getLastName().setStyle("-fx-text-box-border: red ");  
      this.re.getErrorLastName().setVisible(true);
      traderDetailsValid = false;
    }
    
    
    if(validateEmail(this.re.getEmail().getText()))
    {
      System.out.println("This is a VALID EMAIL");
    }
    else
    {
      this.re.getEmail().setStyle("-fx-text-box-border: red ");  
      this.re.getErrorEmail().setVisible(true);
      traderDetailsValid = false;
    }
    
    if(vaildatePassword(this.re.getPassword().getText()))
    {      
      // if password is valid, check the user has reentered it properly 
      int result = this.re.getPassword().getText().compareTo(this.re.getPasswordConfirmed().getText());
      System.out.println(result);
      
      if(result == 0)
      {
        System.out.println("The renetered password is the same");
      }
      else
      {
        this.re.getPasswordConfirmed().setStyle("-fx-text-box-border: red ");
        this.re.getErrorPasswordConfirmed().setVisible(true);
        traderDetailsValid = false;
      }
    }
    else
    {
      this.re.getPasswordConfirmed().setStyle("-fx-text-box-border: red ");  
      this.re.getPassword().setStyle("-fx-text-box-border: red ");  
      this.re.getErrorPasswordConfirmed().setVisible(true);
      this.re.getErrorPassword().setVisible(true);
      traderDetailsValid = false;
    }
 
    return traderDetailsValid;
  }
  
  public boolean validateName(String name)
  {
    Pattern regex = Pattern.compile("[a-zA-Z]{2,15}");    
    Matcher m = regex.matcher(name.trim());    
    boolean isNameValid = m.matches();
    return isNameValid;
  }
  
  public boolean validateUsername(String username)
  {
    Pattern regex = Pattern.compile("[a-zA-Z0-9]{1,15}");
    Matcher m = regex.matcher(username.trim());
    boolean isUsernameValid = m.matches();
    return isUsernameValid;  
  }
  
  public boolean validateEmail(String email)
  {
    Pattern regex = Pattern.compile("^[a-zA-Z0-9]{1,}+@[a-za-zA-Z0-9]{1,}+.[a-z]{1,}?.[a-z]{1,}$");
    Matcher m = regex.matcher(email);
    boolean isEmailValid = m.matches();
    return isEmailValid;
  }
  
  public boolean vaildatePassword(String password)
  {
    Pattern regex = Pattern.compile("^((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!#$&*?@^~]).{7,30})$");
    Matcher m = regex.matcher(password.trim());
    boolean isPasswordValid = m.matches();
    return isPasswordValid;
  }
  
  /**
   * Taking this out for now !!!!!!!!!!!!!!!!!!!!!
   */
  
  public void bindSocketValuesToLabels ()
  {   
    message1 = new SimpleStringProperty();
    Label l1 = cupp.getLabel1();
    l1.textProperty().bind(message1); 

    message2 = new SimpleStringProperty();
    Label l2 = cupp.getLabel2();
    l2.textProperty().bind(message2);   
        
    message3 = new SimpleStringProperty();
    Label l3 = cupp.getLabel3();
    l3.textProperty().bind(message3);   
       
    message4 = new SimpleStringProperty();
    Label l4 = cupp.getLabel4();
    l4.textProperty().bind(message4);
   }
  
  public boolean authenticate()
  {
    boolean traderExist = false;
    String userEnterdEmail = this.lg.getEmail().getText();
    String userEnterPwd = this.lg.getPassword().getText();
    
    
    Database db = new Database();
    
    if(db.doesTraderExist(userEnterdEmail))
    {
      String storedHashedPwd = db.retreiveTradersPassword(userEnterdEmail);
      traderExist = BCrypt.checkpw(userEnterPwd, storedHashedPwd);      
    }
       
    return traderExist;
  }
  
  // jonathandavies27@gmail.com
  
  
  // call this method in the timeline
  // so it will series and label for currency pairs, should make it smoother
  
  public void updateCharts()
  {
    String selectedChart = this.mp.getChartCombo().getValue();
    HashMap<String,Boolean> openTabs = this.findOpenTabs();
    
    BigDecimal difference = new BigDecimal("0.010");
    ArrayList<String> timeInSeconds = this.cp.getTimeSeconds();

    
    // update labels here to
    this.updateLabels();
    
    
    
    
    // check if update seconds -- MAY NOT BE STAYING HERE
    // But no errors? so could be working
    // Need the xAxis! 
    // so if size reaches 100 add another 100.
    
    // ======= possible 25% increment like the other axis, only one way
    
    ArrayList<String> newCat = new ArrayList<String>();

    
    // chart >= current time on the chart 
    if(notStayingHere >= timeInSeconds.size())      
    {
      
      int seriesFutureSize = timeInSeconds.size() + 1;
      
      
     // add 1 seconds into the future
      for(int i = timeInSeconds.size(); i < seriesFutureSize; i++){        
        this.cp.addToCalender();      
        this.cp.addToTimeSeconds(i,this.cp.getSDF().format(this.cp.getCalenderInstanceTime()));       
        newCat.add(this.cp.getSDF().format(this.cp.getCalenderInstanceTime()));
      }
      

      // remove first 20 seconds of category, so screen doesn't pile up    
      ArrayList<String> sub = this.cp.getTimeSeconds();   
      sub.subList(0,1).clear();
      
      
      /** 
       * When removing and adding 1,2 or 30 seconds from 
       * the both axis then make sure you're also removing
       * the same from the index, otherwise it goes awry.
       * */
      // remove 2 from the index so that stays in sync
      notStayingHere -= 1;

      

      XYChart.Series<String, Number> sum = this.cp.getCertainSeries("EUR/USD");     
      System.out.println("TESTING series size: ");
      System.out.println(sum.getData().size());
      
      if(sum.getData().size() >= 100){
        this.cp.removeSeriesElement("EUR/USD",0);
      }
      
      this.cp.setTimeSeconds(sub);
     //  remove first 20 from series
      
      
      ObservableList<String> updatedCategory =  FXCollections.observableArrayList(this.cp.getTimeSeconds()); 
      this.cp.setXAxisCategories(updatedCategory);    
      // dunno?
      this.cp.getXAxis().invalidateRange(updatedCategory);      
 }
    
    
    if(openTabs.get("EUR/USD"))
    { 
        String dubs1 = message1.get();
        double d1 = Double.parseDouble(dubs1);
        
        this.cp.updateSeries("EUR/USD", timeInSeconds.get(notStayingHere), d1);
                
        // get upper, lowers boundary strings and current value
        String upper = String.format("%.3f", this.cp.getYAxisUpper());
        String lower = String.format("%.3f", this.cp.getYAxisLower());
        String current = String.format("%.3f", d1);
                
        // turn upper, lower and current boundaries into BigDecimals
        BigDecimal upperbd = new BigDecimal(upper); 
        BigDecimal lowerbd = new BigDecimal(lower);
        BigDecimal currentbd = new BigDecimal(current); 
        
                
        // check if the current value is greater then or equal to current 
        BigDecimal threeQuater = upperbd.subtract(difference);             
        
        if(threeQuater.compareTo(currentbd) == 0 || threeQuater.compareTo(currentbd) == -1)
        {        
          this.cp.setYAxisUpper(Double.parseDouble(upperbd.add(difference).toString()));
          this.cp.setYAxisLower(Double.parseDouble(lowerbd.add(difference).toString())); 
        }
                
        // check if the current value is less then or equal to the bottom quarter on the chart
        BigDecimal oneQuater = lowerbd.add(difference); 
        
        if(oneQuater.compareTo(currentbd) == 0 || oneQuater.compareTo(currentbd) == 1)
        {        
          this.cp.setYAxisUpper(Double.parseDouble(upperbd.subtract(difference).toString()));
          this.cp.setYAxisLower(Double.parseDouble(lowerbd.subtract(difference).toString())); 
        }
    }
    
    if(openTabs.get("GBP/USD"))
    {     
        String dubs3 = message3.get();
        double d3 = Double.parseDouble(dubs3);
        this.cp.updateSeries("GBP/USD",timeInSeconds.get(notStayingHere), d3);
    }
    
    
    notStayingHere++;  
  }
  
  public void updateSeconds()
  {
    // here we're updating the number of seconds for the bottom of the axis
    
    // we start off
  }
  
  public void updateLabels()
  {
    String l1 = message1.get();
    this.cupp.setLabel1Text(l1);
    
    String l2 = message2.get();
    this.cupp.setLabel2Text(l2);
    
    String l3 = message3.get();
    this.cupp.setLabel3Text(l3);
    
    String l4 = message4.get();
    this.cupp.setLabel4Text(l4); 
    
  }
}
