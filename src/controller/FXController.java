package controller;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;

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
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.Tab;
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Screen;
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
import view.FXViewPriceAlertConfirmation;
import view.FXViewPriceAlertForm;
import view.FXViewRegisterPage;
import view.FXViewRootPane;
import view.FXViewSettingsForm;
import view.FXViewStatusBar;

import java.util.ArrayList;

import java.util.HashMap;


import java.util.regex.*;

import org.controlsfx.control.Notifications;

public class FXController 
{

  private FXViewLoginPage lg;
  private FXViewRootPane rp;
  private FXViewRegisterPage re;
  private FXViewChartPane cp;
  private FXViewCurrencyPairPane cupp;
  private FXViewMenuPane mp;
  private FXViewNewOrderForm of;
  private FXViewPriceAlertForm pa;
  private FXViewPriceAlertConfirmation pc;
  private FXViewStatusBar sb;
  private FXViewOrderPane op;
  private FXViewSettingsForm sf;

  private Trader model;

  private Stage window;
  private Stage windowModal;
  private Stage windowPrice;
  private Stage windowPriceAlert;
  private Stage windowSettings;

  private Scene login;
  private Scene register;
  private Scene market;
  private Scene modal;
  private Scene modalPrice;
  private Scene modalSettings;

  private static StringProperty message1; 
  private static StringProperty message2;
  private static StringProperty message3;
  private static StringProperty message4;
  
  private Database db;  
  private static FirstLineService service;
  private Timeline tl;
  private int indexEUR = 0;
  private int indexUSD = 0;
  private int indexGBP = 0;
  private int indexCHF = 0;

  private HashMap<String, Boolean> tabStatus;

  // for bcrypt password
  private static int workload = 12;
  private static String update;
  private static String equity = "11000";
  private static String traderFirstName = "Test";
  private boolean alreadyLoggedOn;
  
  
  public FXController(FXViewLoginPage view, Trader model) 
  {
    this.lg = view;
    this.model = model;   
    this.db = new Database();
    this.rp = new FXViewRootPane();
    this.re = new FXViewRegisterPage();
    this.cp = this.rp.getChartPane();
    this.op = this.cp.returnOrderPane();
    this.populateOrderTableOnStart(this.op.returnTableView());
    
    this.cupp = this.rp.getCurrencyPairPane();
    this.mp = this.rp.getMenuPane();
    this.of = new FXViewNewOrderForm();
    this.pa = new FXViewPriceAlertForm();
    this.pc = new FXViewPriceAlertConfirmation();
    this.sb = this.rp.getStatusBarPane();
      
    // maintain maximised screen throughout whole application
    Rectangle2D screenSize = Screen.getPrimary().getVisualBounds();
    this.register = new Scene(this.re, screenSize.getWidth(), screenSize.getHeight());
    this.market = new Scene(this.rp, screenSize.getWidth(), screenSize.getHeight());
    this.modal = new Scene(this.of, 350, 330);
    this.modalPrice = new Scene(this.pa, 300, 230);
    
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
    this.mp.addPriceAlert(e -> this.priceAlertInputBox());
    this.mp.addDeleteOrderHandler(e -> this.deleteOrder());
    this.mp.addOpenChartHandler(e -> this.openCharts());
    this.sb.addSettingsHandler(e -> this.newSettingsBox());
    this.of.buyNewOrderButtonHandler(e -> this.processBuyOrder());
    this.of.sellNewOrderButtonHandler(e -> this.processSellOrder());
    this.of.takeProfitButtonHandler(e -> this.undisableTakeProfitSpinner());
    this.of.stopLossButtonHandler(e -> this.undisableStopLossSpinner());
    this.pa.cancelButtonHandler(e -> this.cancelPriceAlert());
    this.pa.okButtonHandler(e -> this.confirmPriceAlert());
    this.pc.okConfirmButtonHandler(e -> this.confirmPriceAlert()); 
    this.cupp.setEURUSDBuyButtonHandler(e -> this.processOneClickBuy("EUR/USD"));
    this.cupp.setEURUSDSellButtonHandler(e -> this.processOneClickSell("EUR/USD"));
    this.cupp.setUSDJPYBuyButtonHandler(e -> this.processOneClickBuy("USD/JPY"));
    this.cupp.setUSDJPYSellButtonHandler(e -> this.processOneClickSell("USD/JPY"));
    this.cupp.setGBPUSDBuyButtonHandler(e -> this.processOneClickBuy("GBP/USD"));
    this.cupp.setGBPUSDSellButtonHandler(e -> this.processOneClickSell("GBP/USD"));
    this.cupp.setUSDCHFBuyButtonHandler(e -> this.processOneClickBuy("USD/CHF"));
    this.cupp.setUSDCHFSellButtonHandler(e -> this.processOneClickSell("USD/CHF"));
  }
  
  public void cancelPriceAlert()
  {
    windowPrice.close();
  }
  
  public void cancelPriceConfirmation()
  {
    windowPriceAlert.close();
  }
  
  public void cancelSettings()
  {
    windowSettings.close();
  }
  
  public void confirmPriceAlert()
  {
   Double priceAlert = Double.parseDouble(this.pa.returnPrice().getText());
   String currency = this.pa.returnCurrencyPair().getValue();  
   this.model.makePriceAlert(currency, priceAlert);
   windowPrice.close();
  }
  
  public void undisableTakeProfitSpinner() 
  {
    Spinner<Double> temp = this.of.returnTakeProfit();
    Boolean disabled = false;
    disabled = (temp.isDisabled() == true) ? false : true;
    this.of.setDisableTakeProfit(disabled);
  }

  public void undisableStopLossSpinner() 
  {
    Spinner<Double> temp = this.of.returnStopLoss();
    Boolean disabled = false;
    disabled = (temp.isDisabled() == true) ? false : true;
    this.of.setDisableStopLoss(disabled);
  }

  public void closingHousekeepingforUSD() 
  {  
    indexUSD = 0;
    this.cp.removeSeries("USD/JPY");
    tabStatus.put("USD/JPY", false);
  }

  public void closingHousekeepingforGBP() 
  {
    indexGBP = 0;
    this.cp.removeSeries("GBP/USD");
    tabStatus.put("GBP/USD", false);
  }

  public void closingHousekeepingforCHF() 
  {
    indexCHF = 0;
    this.cp.removeSeries("USD/CHF");
    tabStatus.put("USD/CHF", false);
  }
  
  public void closingHousekeepingforEUR() 
  {
    indexEUR = 0;
    this.cp.removeSeries("EUR/USD");
    tabStatus.put("EUR/USD", false);
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
  }
  
  public void newSettingsBox() 
  {
    FXViewSettingsForm newSettingsForm = new FXViewSettingsForm();
    newSettingsForm.getFirstNameLabel().setText(this.model.getFirstName());
    newSettingsForm.getLastNameLabel().setText(this.model.getLastName());
    newSettingsForm.getEmailLabel().setText(this.model.getEmail());
    newSettingsForm.getOrdersLabel().setText(String.valueOf(this.model.getOrders().size()));
    newSettingsForm.getEquityLabel().setText(this.model.getEquity().toString());
    newSettingsForm.cancelSettingsButtonHandler(e -> this.cancelSettings()); 
    
    this.modalSettings = new Scene(newSettingsForm, 420, 350);
    
    windowSettings = new Stage();
    windowSettings.initModality(Modality.APPLICATION_MODAL);
    windowSettings.alwaysOnTopProperty();
    windowSettings.centerOnScreen();
    windowSettings.setScene(modalSettings);
    windowSettings.getIcons().removeAll();
    windowSettings.showAndWait();
  }

  public void priceAlertInputBox() 
  {
    windowPrice = new Stage();
    windowPrice.initModality(Modality.APPLICATION_MODAL);
    windowPrice.alwaysOnTopProperty();
    windowPrice.centerOnScreen();
    windowPrice.setScene(modalPrice);
    windowPrice.getIcons().removeAll();
    windowPrice.showAndWait();
  }

  public void deleteOrder() 
  {
 
    int indexOrder;
    Order selected = this.op.returnTableView().getSelectionModel().getSelectedItem();

    if (selected != null) 
    {     
      BigDecimal result = selected.getResult();
      BigDecimal equity = this.model.getEquity();
      BigDecimal newEquity = equity.add(result);
      this.model.setEquity(newEquity);
      
      indexOrder = this.model.getOrders().indexOf(selected);
      
      int id = selected.getDBId();
      this.db.deleteOrderDetails(id, this.model.getEmail());
            
      this.model.removeOrder(indexOrder);
      ObservableList<Order> orderList = FXCollections.observableArrayList(this.model.getOrders());
      this.op.setItemsTableView(orderList);
    }
    
    return;
  }

  public HashMap<String, Boolean> findOpenTabs() 
  {
    ObservableList<Tab> options = this.cp.getTabPanes().getTabs();
    tabStatus = new HashMap<String, Boolean>();

    boolean tab1Open = false;
    boolean tab2Open = false;
    boolean tab3Open = false;
    boolean tab4Open = false;

    for (Tab t : options) 
    {
      if (t.getText().equals("EUR/USD")) 
      {
        tab1Open = true;
      }
      if (t.getText().equals("USD/JPY")) 
      {
        tab2Open = true;
      }
      if (t.getText().equals("GBP/USD")) 
      {
        tab3Open = true;
      }
      if (t.getText().equals("USD/CHF")) 
      {
        tab4Open = true;
      }
    }

    tabStatus.put("EUR/USD", tab1Open);
    tabStatus.put("USD/JPY", tab2Open);
    tabStatus.put("GBP/USD", tab3Open);
    tabStatus.put("USD/CHF", tab4Open);

    return tabStatus;
  }

  public void openCharts() 
  {
    String selectedChart = this.mp.getChartCombo().getValue();
    HashMap<String, Boolean> openTabs = this.findOpenTabs();
    
    switch (selectedChart) 
    {

      case "EUR/USD":
                      if (!openTabs.get("EUR/USD")) 
                      {
                        indexEUR = 0;
                        this.cp.addTabPane("EUR/USD",  this.getCurrentPrice("EUR/USD"));
                        this.cp.setCloseRequesTab1(e -> this.closingHousekeepingforEUR());          
                        this.cp.getTabPanes().getSelectionModel().select(this.cp.getEURtab());
                      } 
                      else 
                      {
                        this.cp.getTabPanes().getSelectionModel().select(this.cp.getEURtab());
                      }
              
                      break;
      case "USD/JPY":
                      if (!openTabs.get("USD/JPY")) 
                      {
                        this.cp.addTabPane("USD/JPY", this.getCurrentPrice("USD/JPY"));
                        this.cp.setCloseRequesTab2(e -> this.closingHousekeepingforUSD());
                        this.cp.getTabPanes().getSelectionModel().select(this.cp.getUSDtab());
                      } 
                      else 
                      {
                        this.cp.getTabPanes().getSelectionModel().select(this.cp.getUSDtab());
                      }
              
                      break;
      case "GBP/USD":
                      if (!openTabs.get("GBP/USD")) 
                      {
                        this.cp.addTabPane("GBP/USD", this.getCurrentPrice("GBP/USD"));
                        this.cp.setCloseRequesTab3(e -> this.closingHousekeepingforGBP());
                        this.cp.getTabPanes().getSelectionModel().select(this.cp.getGBPtab());
                      } 
                      else 
                      {
                        this.cp.getTabPanes().getSelectionModel().select(this.cp.getGBPtab());
                      }
                      break;
      case "USD/CHF":              
                      if (!openTabs.get("USD/CHF")) 
                      {
                        this.cp.addTabPane("USD/CHF", this.getCurrentPrice("USD/CHF"));
                        this.cp.setCloseRequesTab4(e -> this.closingHousekeepingforCHF());
                        this.cp.getTabPanes().getSelectionModel().select(this.cp.getCHFtab());
                      } 
                      else 
                      {
                        this.cp.getTabPanes().getSelectionModel().select(this.cp.getCHFtab());
                      }
                      break;
    }
  }

  public void processOneClickBuy(String currencyPair) 
  { 
    String quantity;
    quantity = this.mp.getQuantityCombo().getValue();   
    String currency = currencyPair;
    String direction = "Buy";
    double currentPrice = Double.parseDouble(this.getCurrentPrice(currency));
    double price = Double.parseDouble(this.getCurrentPrice(currency));
    double takeProfit = 0.0;
    double stopLoss = 0.0;
    BigDecimal result = new BigDecimal("0.00");
    boolean oneClickOrder = true;

    // get last id of order
    int lastId = this.db.getLastOrderId();
    this.db.insertOrderDetails(++lastId, this.model.getEmail(), currency,  quantity, direction, price, takeProfit, stopLoss);
     
    Order buy = new Order(currency, quantity, direction, price, currentPrice, takeProfit, stopLoss, result, oneClickOrder, lastId );   
    this.model.addOrder(buy);
    ObservableList<Order> orderList = FXCollections.observableArrayList(this.model.getOrders());
    this.op.setItemsTableView(orderList);
  }

  public void processOneClickSell(String currencyPair) 
  { 
    String quantity;
    quantity = this.mp.getQuantityCombo().getValue();
    String currency = currencyPair;
    String direction = "Sell";
    double currentPrice = Double.parseDouble(this.getCurrentPrice(currency));
    double price = Double.parseDouble(this.getCurrentPrice(currency));
    double takeProfit = 0.0;
    double stopLoss = 0.0;
    BigDecimal result = new BigDecimal("0.00");
    boolean oneClickOrder = true;

    int lastId = this.db.getLastOrderId();
    this.db.insertOrderDetails(++lastId,  this.model.getEmail(), currency,  quantity, direction, price, takeProfit, stopLoss);
 
    Order sell = new Order(currency, quantity, direction, price, currentPrice, takeProfit, stopLoss, result, oneClickOrder, lastId );    
    this.model.addOrder(sell);
    ObservableList<Order> orderList = FXCollections.observableArrayList(this.model.getOrders());
    this.op.setItemsTableView(orderList);
  }

  public String getCurrentPrice(String currency) 
  {
    String price = "";

    switch (currency) 
    {
      case "EUR/USD":
                      price = this.cupp.getLabel1().getText();
                      break;
      case "USD/JPY":
                      price = this.cupp.getLabel2().getText();
                      break;
      case "GBP/USD":
                      price = this.cupp.getLabel3().getText();
                      break;
      case "USD/CHF":
                      price = this.cupp.getLabel4().getText();
                      break;
    }

    return price;
  }

  public void processBuyOrder() 
  { 
    
    Label error = this.of.returnErrorLabel();
    error.setVisible(false);
    
    String quantity = this.of.returnQuantity().getValue();
    String currency = this.of.returnCurrencyPair().getValue();
    double stopLoss = 0.0;
    double takeProfit = 0.0;
    boolean oneClickOrder = false;
      
    
    if (quantity != null && currency != null)
    {
        String direction = "Buy";
        double currentPrice = Double.parseDouble(this.getCurrentPrice(currency));
        double price = Double.parseDouble(this.getCurrentPrice(currency));
        
        // check whether control is disabled, set appropriate value
        if (this.of.returnTakeProfit().isDisable())
        {
          oneClickOrder = true;
        }
        else
        {
          takeProfit = this.of.returnTakeProfit().getValue();       
        }
        
        // check whether control is disabled, set appropriate value
        if (this.of.returnStopLoss().isDisable())
        {
          oneClickOrder = true;
        }
        else
        {
          stopLoss = this.of.returnStopLoss().getValue();      
        }
          
        BigDecimal result = new BigDecimal("0.00");    
        int lastId = this.db.getLastOrderId();        
        this.db.insertOrderDetails(++lastId,  this.model.getEmail(), currency,  quantity, direction, price, takeProfit, stopLoss);
        
        Order sell = new Order(currency, quantity, direction, price, currentPrice, takeProfit, stopLoss, result, oneClickOrder, lastId );   
        this.model.addOrder(sell);    
        
        ObservableList<Order> orderList = FXCollections.observableArrayList(this.model.getOrders());
        this.op.setItemsTableView(orderList);
        
        // reset disable
        this.of.returnTakeProfit().setDisable(true);
        this.of.returnStopLoss().setDisable(true);
        
        windowModal.close();
    }
    else
    {
      // show error label
      error.setVisible(true);      
      return;
    }
  }

  public void processSellOrder() 
  {
    Label error = this.of.returnErrorLabel();
    error.setVisible(false);
    
    String quantity = this.of.returnQuantity().getValue();
    String currency = this.of.returnCurrencyPair().getValue();    
    double takeProfit = 0.0;
    double stopLoss = 0.0;
    boolean oneClickOrder = false;
    
    if (quantity != null && currency != null) 
    {   
        String direction = "Sell";
        double currentPrice = Double.parseDouble(this.getCurrentPrice(currency));
        double price = Double.parseDouble(this.getCurrentPrice(currency));   
       
        
        if (this.of.returnTakeProfit().isDisable())
        {
           oneClickOrder = true;
        }
        else
        {
          takeProfit = this.of.returnTakeProfit().getValue();       
        }
        
        // check whether control is disabled, set appropriate value
        if (this.of.returnStopLoss().isDisable())
        {
          oneClickOrder = true;
        }
        else
        {
          stopLoss = this.of.returnStopLoss().getValue();      
        }
            
        BigDecimal result = new BigDecimal("0.00");       
        int lastId = this.db.getLastOrderId();        
        this.db.insertOrderDetails(++lastId,  this.model.getEmail(), currency,  quantity, direction, price, takeProfit, stopLoss);
     
        Order buy = new Order(currency, quantity, direction, price, currentPrice, takeProfit, stopLoss, result, oneClickOrder, lastId ); 
        this.model.addOrder(buy);
        
        ObservableList<Order> orderList = FXCollections.observableArrayList(this.model.getOrders());
        this.op.setItemsTableView(orderList);
        
        // reset disable
        this.of.returnTakeProfit().setDisable(true);
        this.of.returnStopLoss().setDisable(true);
        
        windowModal.close();
     }
     else
     {
        // show error label
        error.setVisible(true);      
        return;
     }
  }

  public void startSocketListener() 
  {
    service = new FirstLineService();
    service.setOnSucceeded(new EventHandler<WorkerStateEvent>() 
    {
      @Override
      public void handle(WorkerStateEvent t) 
      {
        //System.out.println("done:" + t.getSource().getValue());
      }
    });
    service.start();
  }
 
  private static class FirstLineService extends Service<String> 
  {
    protected Task<String> createTask() 
    {
      return new Task<String>() 
      {
        protected String call() throws IOException, MalformedURLException 
        {
          try (Socket socket = new Socket("PC", 4444);
              // 10.34.98.62 Uni IP
              // 192.168.1.2 ethernet
              PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
              BufferedReader in =
                  new BufferedReader(new InputStreamReader(socket.getInputStream()));) {
              BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
              
              String fromServer;          
              String traderName = traderFirstName;

              while ((fromServer = in.readLine()) != null) 
              {
                update = fromServer;
                //System.out.println(update);           
                String[] s = update.split("-");

                Platform.runLater(new Runnable() 
                {
                  public void run() 
                  {
                    message1.set(s[0]);
                    message2.set(s[1]);
                    message3.set(s[2]);
                    message4.set(s[3]);
                  }
                });

                //System.out.println("What we getting? " + equity);
                //System.out.println("What name? " + traderName);
                String equitySend = equity;
               // System.out.println(equitySend);
                out.println(equitySend + "-" + traderName);
              
                if (isCancelled())
                {
                  // System.out.println("We in cancelled?");
                  out.println("END");
                  out.close();
                  in.close();
                  socket.close();
                  break;
                }
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
          return "Service";    
        }
      };
    }
  }

  public void startTimeline() 
  {
    tl = new Timeline();
    tl.getKeyFrames().add(new KeyFrame(Duration.millis(500), new EventHandler<ActionEvent>() 
    {
      @Override
      public void handle(ActionEvent actionEvent) 
      {
        updateCharts();
        updateOrders();
        checkSLTP();
        checkPriceAlerts();
        updateOrderResult();
        calculateLiveResultAndEquity();
        setTraderNameAndEquity();
      }
    }));
    tl.setCycleCount(Animation.INDEFINITE);
    tl.setAutoReverse(true);
    alreadyLoggedOn = true;    
    tl.play(); 
  }
  
  public void setTraderNameAndEquity()
  {
    equity = this.sb.getLabelEquity().getText();  
  }
  
  public void checkPriceAlerts()
  {    
    Double eur =  Double.parseDouble(message1.get());
    Double usd =  Double.parseDouble(message2.get());
    Double gbp =  Double.parseDouble(message3.get());
    Double chf =  Double.parseDouble(message4.get());
    
    ArrayList<Double> eurPA = this.model.getPriceAlertsEUR();
    ArrayList<Double> usdPA = this.model.getPriceAlertsUSD();
    ArrayList<Double> gbpPA = this.model.getPriceAlertsGBP();
    ArrayList<Double> chfPA = this.model.getPriceAlertsCHF();
    
    for(int i = 0; i < eurPA.size(); i++)
    {
      if(eur.compareTo(eurPA.get(i)) == 0 || eur.compareTo(eurPA.get(i)) == 1)
      {
        this.createPriceAlertNotification("EUR/USD", eurPA.get(i));
        this.model.removePriceAlert("EUR/USD", eurPA.get(i));
      }
    }
    
    for(int i = 0; i < usdPA.size(); i++)
    {
      if(usd.compareTo(usdPA.get(i)) == 0 || usd.compareTo(usdPA.get(i)) == 1)
      {
        this.createPriceAlertNotification("USD/JPY", usdPA.get(i));
        this.model.removePriceAlert("USD/JPY", usdPA.get(i));
      }
    }
    
    for(int i = 0; i < gbpPA.size(); i++)
    {
      if(gbp.compareTo(gbpPA.get(i)) == 0 || gbp.compareTo(gbpPA.get(i)) == 1)
      {
        this.createPriceAlertNotification("GBP/USD", gbpPA.get(i));
        this.model.removePriceAlert("GBP/USD", gbpPA.get(i));
      }
    }
       
    for(int i = 0; i < chfPA.size(); i++)
    {
      if(chf.compareTo(chfPA.get(i)) == 0 || chf.compareTo(chfPA.get(i)) == 1)
      {
        this.createPriceAlertNotification("USD/CHF", chfPA.get(i));
        this.model.removePriceAlert("USD/CHF", chfPA.get(i));
      }
    }
  }
  
  public void createPriceAlertNotification(String currency, double price)
  {
    Notifications.create()
                 .darkStyle()
                 .title("Price Alert")
                 .text(" The Currency Pair " + currency + " has reached " + price + ".")
                 .showWarning();    
  }
  
  public void createTPSLNotification()
  {
    Notifications.create()
                 .darkStyle()
                 .title("Order Complete")
                 .text(" The Take Profit/Stop Loss value has been reached.")
                 .showInformation();    
  }

  public void calculateLiveResultAndEquity()
  {
    ArrayList<Order> orders = this.model.getOrders();    
    BigDecimal liveResult = new BigDecimal("0.00");
    
    for (Order o : orders) 
    {
      liveResult = liveResult.add(o.getResult());
    }

    this.sb.setLabelLiveResult(liveResult.toString());
    BigDecimal equity = this.model.getEquity();
    BigDecimal newEquity = equity.add(liveResult);  
    this.sb.setLabelEquity(newEquity.toString());
  }
  
  public void updateOrderResult() 
  {
    ArrayList<Order> orders = this.model.getOrders();

    for (int k = 0; k < orders.size(); k++) 
    {
      if(orders.get(k).getDirection().equals("Buy"))
      {               
        // get the quantity of buy order
        BigDecimal quantityOfBuyOrder = new BigDecimal(orders.get(k).getQuantity());
        
        // get the initial currency price
        BigDecimal initialCurrencyPriceOfBuyOrder = new BigDecimal(Double.toString(orders.get(k).getPrice()));

        // get the initial total
        BigDecimal initialTotal =  quantityOfBuyOrder.multiply(initialCurrencyPriceOfBuyOrder);

        //get the current currency price
        BigDecimal currentCurrencyPriceOfBuyOrder = new BigDecimal(this.getCurrentPrice(orders.get(k).getCurrencyPair()));

        // get the current total 
        BigDecimal currentTotal = quantityOfBuyOrder.multiply(currentCurrencyPriceOfBuyOrder);

        // take away the initial total against current total to work out profit/loss store it in table view
        BigDecimal overallProfitLoss =  currentTotal.subtract(initialTotal).setScale(2);  
        
        // format the string to two decimal places
        overallProfitLoss = overallProfitLoss.setScale(2, RoundingMode.HALF_UP);
        
        // update result for table view
        orders.get(k).setResult(overallProfitLoss);     
      }
      else
      {             
        // get the quantity of sell order
        BigDecimal quantityOfSellOrder = new BigDecimal(orders.get(k).getQuantity());
        
        // get the initial currency price
        BigDecimal initialCurrencyPriceOfSellOrder = new BigDecimal(Double.toString(orders.get(k).getPrice()));
        
        // get the initial total
        BigDecimal initialTotal =  quantityOfSellOrder.multiply(initialCurrencyPriceOfSellOrder);

        //get the current currency price
        BigDecimal currentCurrencyPriceOfSellOrder = new BigDecimal(this.getCurrentPrice(orders.get(k).getCurrencyPair()));
        
        // get the current total 
        BigDecimal currentTotal = initialTotal.divide(currentCurrencyPriceOfSellOrder, 2, RoundingMode.HALF_UP);

        // get the difference 
        BigDecimal difference = currentTotal.subtract(quantityOfSellOrder).setScale(2);
        
        // convert the difference back
        BigDecimal finalDifference = difference.multiply(currentCurrencyPriceOfSellOrder).setScale(2, RoundingMode.HALF_UP);
        
        finalDifference = finalDifference.setScale(2, RoundingMode.HALF_UP);
        // format the string to two decimal places
        
        // update result for table view
        orders.get(k).setResult(finalDifference);
      }  
    }

    ObservableList<Order> orderList = FXCollections.observableArrayList(orders);
    this.op.setItemsTableView(orderList);
    this.op.refreshTableView();
  }
  
  public void updateOrders() 
  {
    ArrayList<Order> orders = this.model.getOrders();

    for (Order o : orders) 
    {
      o.setCurrentPrice(Double.parseDouble(this.getCurrentPrice(o.getCurrencyPair())));
    }

    ObservableList<Order> orderList = FXCollections.observableArrayList(orders);
    this.op.setItemsTableView(orderList);
    this.op.refreshTableView();
  }
  
  public void checkSLTP() 
  {

    ArrayList<Order> orders = this.model.getOrders();
    ArrayList<Integer> removeOrders = new ArrayList<Integer>();

    for (Order o : orders) 
    {
      // get the current price for the currency pair of this order!
      Double currentPrice = new Double(Double.parseDouble(this.getCurrentPrice(o.getCurrencyPair())));

      // get the order stop loss
      Double stopLoss = new Double(o.getStopLoss());

      // get the order take profit
      Double takeProfit = new Double(o.getTakeProfit());

      // dont remove by default
      boolean removeOrder = false;
      String reason = "";

      // if wasn't one click order
      if (stopLoss != 0.0) 
      {
        if (currentPrice.compareTo(stopLoss) == -1 || currentPrice.compareTo(stopLoss) == 0) 
        {
          removeOrder = true;
          reason = "Stop Loss";
        }
      }

      // if wasn't one click order
      if (takeProfit != 0.0) 
      {
        // if less then or equal to, this is right!
        if (currentPrice.compareTo(takeProfit) == 1 || currentPrice.compareTo(takeProfit) == 0) 
        {
          removeOrder = true;
          reason = "Take Profit";
        }
      }

      if (removeOrder) 
      { 
        int id = o.getId();
        removeOrders.add(id);
      }
    }


    for (int j = 0; j < this.model.getOrders().size(); j++) 
    {
      for (Integer i : removeOrders) 
      {
        if (i ==  this.model.getOrders().get(j).getId())
        {
          // add or subtract result to equity permanently
          BigDecimal result = this.model.getOrders().get(j).getResult();
          BigDecimal equity = this.model.getEquity();
          BigDecimal newEquity = equity.add(result);
          this.model.setEquity(newEquity);
          
          // remove from db
          this.db.deleteOrderDetails(this.model.getOrders().get(j).getDBId(),this.model.getEmail());
          // remove from tableview
          this.model.removeSingleOrder(this.model.getOrders().get(j));
          // create notification
          this.createTPSLNotification();
        }
      }
    }

    // update table view in order pane
    ObservableList<Order> orderList = FXCollections.observableArrayList(this.model.getOrders());
    this.op.setItemsTableView(orderList);
  }
  

  public void setSceneToBeDisplayed(String nextScreen) 
  {
    switch (nextScreen) 
    {    
      case "Login":
                      // validation
                      if (this.validateTraderDetails()) 
                      {
                          this.saveTraderDetailsDB();                        
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
                        
                        this.model = new Trader(); 
                        this.setCurrentTraderDetails();
              
                        traderFirstName = "FXClient 2";                       
                        this.populateOrders();
                        
                        if(alreadyLoggedOn)
                        {
                          window.setScene(market);           
                          indexEUR = 0;
                          this.cp.addTabPane("EUR/USD",  this.getCurrentPrice("EUR/USD"));
                          this.cp.setCloseRequesTab1(e -> this.closingHousekeepingforEUR());          
                          this.cp.getTabPanes().getSelectionModel().select(this.cp.getEURtab());                       
                        }
                        else
                        {
                          this.startSocketListener();            
                          window.setScene(market);
                          this.startTimeline();
                        }
              
                      } 
                      else 
                      {
                        window.setScene(login);
                      }
                      break;         
      case "Logout":
                      window.setScene(login);
                      this.logOffHousekeeping();
                      break;                  
      case "Register":
                      window.setScene(register);
                      break;
    }
    window.show();
    window.setMaximized(true);
  }

  public void setCurrentTraderDetails()
  {
    ResultSet rs = db.retrieveTraderDetails(this.lg.getEmail().getText());        
    try 
    {
      while (rs.next()) 
      {
        this.model.setFirstName(rs.getString("firstname"));    
        this.model.setLastName(rs.getString("lastname"));  
        this.model.setEmail(rs.getString("email"));   
        this.model.setEquity(new BigDecimal(Double.toString(rs.getDouble("equity"))));
      }
    } 
    catch (SQLException e1) 
    {  
      e1.printStackTrace();
    }
  }
    
  public void setStage(Stage stage) 
  {
    this.window = stage;
  }

  public void setScene(Scene scene) 
  {
    this.login = scene;
  }

  public void populateOrders()
  {
    // ids start at 1 
    if(this.db.getLastOrderId() > 0 )
    {      
      ResultSet rs = this.db.returnOrders(this.model.getEmail());
      
      try 
      {
        while (rs.next()) 
        {        
         Order buy = new Order(rs.getString("currency"),  rs.getString("quantity") , rs.getString("direction"),  rs.getDouble("price") ,  
             0.000, rs.getDouble("takeProfit"), rs.getDouble("stopLoss"), new BigDecimal("0.0"), true, rs.getInt("id")  );       
         this.model.addOrder(buy); 
        }
      } 
      catch (SQLException e) 
      {
        e.printStackTrace();
      }
          
      ObservableList<Order> orderList = FXCollections.observableArrayList(this.model.getOrders());
      this.op.setItemsTableView(orderList);
    }   
  }
  
  public void logOffHousekeeping()
  {       
   indexEUR = 0;
   indexUSD = 0;
   indexGBP = 0;
   indexCHF = 0;
   
   HashMap<String, Boolean> openTabs = this.findOpenTabs();
   
   if (openTabs.get("EUR/USD")) 
   {
       this.cp.getTabPanes().getTabs().remove(this.cp.getEURtab());
       this.cp.removeSeries("EUR/USD");
   }
   
   if (openTabs.get("USD/JPY")) 
   {
     this.cp.getTabPanes().getTabs().remove(this.cp.getUSDtab());
     this.cp.removeSeries("USD/JPY");
   }
   
   if (openTabs.get("GBP/USD")) 
   {
     this.cp.getTabPanes().getTabs().remove(this.cp.getGBPtab());
     this.cp.removeSeries("GBP/USD");
   }
   
   if (openTabs.get("USD/CHF")) 
   {
     this.cp.getTabPanes().getTabs().remove(this.cp.getCHFtab());
     this.cp.removeSeries("USD/CHF");
   }
   
   // save the current trader's equity
   db.updateEquity(this.model.getEquity().doubleValue(), this.model.getEmail());
   
   // remove table view orders for log out
   this.model.getOrders().clear();
   ObservableList<Order> orderList = FXCollections.observableArrayList(this.model.getOrders());
   this.op.setItemsTableView(orderList);
  }
  
  public void saveTraderDetailsDB() 
  {  
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
  }

  public boolean validateTraderDetails() 
  {
    boolean traderDetailsValid = true;
    
    // reset labels for re-attempts
    this.re.getErrorFirstName().setVisible(false);
    this.re.getErrorLastName().setVisible(false);
    this.re.getErrorEmail().setVisible(false);
    this.re.getErrorPasswordConfirmed().setVisible(false);
    this.re.getErrorPassword().setVisible(false);

    // reset styling for re-attempts
    this.re.getFirstName().setStyle("-fx-text-box-border: transparent");
    this.re.getLastName().setStyle("-fx-text-box-border: transparent");
    this.re.getEmail().setStyle("-fx-text-box-border: transparent");    
    this.re.getPasswordConfirmed().setStyle("-fx-text-box-border: transparent");    
    this.re.getPassword().setStyle("-fx-text-box-border: transparent");
           
    if (validateName(this.re.getFirstName().getText())) 
    {
      //System.out.println("The first name is valid!");
    } 
    else 
    {
      this.re.getFirstName().setStyle("-fx-text-box-border: red");
      this.re.getErrorFirstName().setVisible(true);
      traderDetailsValid = false;
    }

    if (validateName(this.re.getLastName().getText())) 
    {
      //System.out.println("The last name is valid!");
    } 
    else 
    {
      this.re.getLastName().setStyle("-fx-text-box-border: red ");
      this.re.getErrorLastName().setVisible(true);
      traderDetailsValid = false;
    }

    // we need to check that email doesn't already exist in db
    if (validateEmail(this.re.getEmail().getText())) 
    {    
      boolean traderEmailExist = this.db.doesTraderExist(this.re.getEmail().getText());
      
      if(traderEmailExist)
      {
        this.re.getErrorEmail().setText("This email is already in use");
        this.re.getEmail().setStyle("-fx-text-box-border: red ");
        this.re.getErrorEmail().setVisible(true);
        traderDetailsValid = false;
      }      
    } 
    else 
    {
      this.re.getEmail().setStyle("-fx-text-box-border: red ");
      this.re.getErrorEmail().setVisible(true);
      traderDetailsValid = false;
    }
    
    if (vaildatePassword(this.re.getPassword().getText())) 
    {
      // if password is valid, check the user has reentered it properly
      int result = this.re.getPassword().getText().compareTo(this.re.getPasswordConfirmed().getText());
      

      if (result == 0) 
      {
        //System.out.println("The renetered password is the same");
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
    
    if (traderDetailsValid)
    {
      String reg = "Your registration was successful! Login below.";
      this.lg.getInfoLabel().setText(reg);
      this.lg.getInfoLabel().setStyle("-fx-background-color : green");
      this.lg.getInfoLabel().setVisible(true);     
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

  public boolean authenticate() 
  {  
    boolean traderExist = false;
    String userEnterdEmail = this.lg.getEmail().getText();
    String userEnterPwd = this.lg.getPassword().getText();
    Database db = new Database();

    if (db.doesTraderExist(userEnterdEmail)) 
    {
      String storedHashedPwd = db.retreiveTradersPassword(userEnterdEmail);
      traderExist = BCrypt.checkpw(userEnterPwd, storedHashedPwd);
    }
    else
    {
      String aut = "Authentication failed! Try again or Register.";
      this.lg.getInfoLabel().setText(aut);
      this.lg.getInfoLabel().setStyle("-fx-background-color : red");
      this.lg.getInfoLabel().setVisible(true);
    }   
    return traderExist;
  }

  public void updateCharts() 
  {

    String selectedChart = this.mp.getChartCombo().getValue();
    HashMap<String, Boolean> openTabs = this.findOpenTabs();
    BigDecimal difference = new BigDecimal("0.010");

    ArrayList<String> timeInSecondsEUR = this.cp.getTimeSecondsEUR();
    ArrayList<String> timeInSecondsUSD = this.cp.getTimeSecondsUSD();
    ArrayList<String> timeInSecondsGBP = this.cp.getTimeSecondsGBP();
    ArrayList<String> timeInSecondsCHF = this.cp.getTimeSecondsCHF();

    // update labels here to
    this.updateLabels();


    if (openTabs.get("EUR/USD")) 
    {
      // chart >= current time on the chart
      if (indexEUR >= timeInSecondsEUR.size()) 
      {
        ArrayList<String> newCatEUR = new ArrayList<String>();
        int seriesFutureSize = timeInSecondsEUR.size() + 1;

        // add 1 seconds into the future
        for (int i = timeInSecondsEUR.size(); i < seriesFutureSize; i++) 
        {
          this.cp.addToCalenderEUR();
          this.cp.addToTimeSecondsEUR(i, this.cp.getSDF().format(this.cp.getCalenderInstanceTimeEUR()));
          newCatEUR.add(this.cp.getSDF().format(this.cp.getCalenderInstanceTimeEUR()));
        }

        // remove first 20 seconds of category, so screen doesn't pile up
        ArrayList<String> sub = this.cp.getTimeSecondsEUR();
        sub.subList(0, 1).clear();

        // remove 2 from the index so that stays in sync
        indexEUR -= 1;
        XYChart.Series<String, Number> sum = this.cp.getCertainSeries("EUR/USD");

        if (sum.getData().size() >= 100) 
        {
          this.cp.removeSeriesElement("EUR/USD", 0);
        }

        this.cp.setTimeSecondsEUR(sub);
        // remove first 20 from series

        ObservableList<String> updatedCategory = FXCollections.observableArrayList(this.cp.getTimeSecondsEUR());
        this.cp.setXAxisCategoriesEUR(updatedCategory);
        this.cp.getXAxisEUR().invalidateRange(updatedCategory);

      }

      String dubs1 = message1.get();
      double d1 = Double.parseDouble(dubs1);
      this.cp.updateSeries("EUR/USD", timeInSecondsEUR.get(indexEUR), d1);

      // get upper, lowers boundary strings and current value
      String upper = String.format("%.3f", this.cp.getYAxisUpperEUR());
      String lower = String.format("%.3f", this.cp.getYAxisLowerEUR());
      String current = String.format("%.3f", d1);

      // turn upper, lower and current boundaries into BigDecimals
      BigDecimal upperbd = new BigDecimal(upper);
      BigDecimal lowerbd = new BigDecimal(lower);
      BigDecimal currentbd = new BigDecimal(current);

      // check if the current value is greater then or equal to current
      BigDecimal threeQuater = upperbd.subtract(difference);

      if (threeQuater.compareTo(currentbd) == 0 || threeQuater.compareTo(currentbd) == -1) 
      {
        this.cp.setYAxisUpperEUR(Double.parseDouble(upperbd.add(difference).toString()));
        this.cp.setYAxisLowerEUR(Double.parseDouble(lowerbd.add(difference).toString()));
      }

      // check if the current value is less then or equal to the bottom quarter on the chart
      BigDecimal oneQuater = lowerbd.add(difference);

      if (oneQuater.compareTo(currentbd) == 0 || oneQuater.compareTo(currentbd) == 1) 
      {
        this.cp.setYAxisUpperEUR(Double.parseDouble(upperbd.subtract(difference).toString()));
        this.cp.setYAxisLowerEUR(Double.parseDouble(lowerbd.subtract(difference).toString()));
      }
      
      indexEUR++;
    }

    if (openTabs.get("USD/JPY")) 
    {
      // chart >= current time on the chart
      if (indexUSD >= timeInSecondsUSD.size()) 
      {
        ArrayList<String> newCatUSD = new ArrayList<String>();

        int seriesFutureSize = timeInSecondsUSD.size() + 1;

        // add 1 seconds into the future
        for (int i = timeInSecondsUSD.size(); i < seriesFutureSize; i++) 
        {
          this.cp.addToCalenderUSD();
          this.cp.addToTimeSecondsUSD(i, this.cp.getSDF().format(this.cp.getCalenderInstanceTimeUSD()));
          newCatUSD.add(this.cp.getSDF().format(this.cp.getCalenderInstanceTimeUSD()));
        }

        // remove first 20 seconds of category, so screen doesn't pile up
        ArrayList<String> sub = this.cp.getTimeSecondsUSD();
        sub.subList(0, 1).clear();

        /**
         * When removing and adding 1,2 or 30 seconds from the both axis then make sure you're also
         * removing the same from the index, otherwise it goes awry.
         */
        // remove 2 from the index so that stays in sync
        indexUSD -= 1;
        XYChart.Series<String, Number> sum = this.cp.getCertainSeries("USD/JPY");

        if (sum.getData().size() >= 100) 
        {
          this.cp.removeSeriesElement("USD/JPY", 0);
        }

        this.cp.setTimeSecondsUSD(sub);
        // remove first 20 from series

        ObservableList<String> updatedCategory = FXCollections.observableArrayList(this.cp.getTimeSecondsUSD());
        this.cp.setXAxisCategoriesUSD(updatedCategory);
        // dunno?
        this.cp.getXAxisUSD().invalidateRange(updatedCategory);

      }


      String dubs2 = message2.get();
      double d2 = Double.parseDouble(dubs2);
      this.cp.updateSeries("USD/JPY", timeInSecondsUSD.get(indexUSD), d2);

      // get upper, lowers boundary strings and current value
      String upper = String.format("%.3f", this.cp.getYAxisUpperUSD());
      String lower = String.format("%.3f", this.cp.getYAxisLowerUSD());
      String current = String.format("%.3f", d2);

      // turn upper, lower and current boundaries into BigDecimals
      BigDecimal upperbd = new BigDecimal(upper);
      BigDecimal lowerbd = new BigDecimal(lower);
      BigDecimal currentbd = new BigDecimal(current);


      // check if the current value is greater then or equal to current
      BigDecimal threeQuater = upperbd.subtract(difference);

      if (threeQuater.compareTo(currentbd) == 0 || threeQuater.compareTo(currentbd) == -1) 
      {
        this.cp.setYAxisUpperUSD(Double.parseDouble(upperbd.add(difference).toString()));
        this.cp.setYAxisLowerUSD(Double.parseDouble(lowerbd.add(difference).toString()));
      }

      // check if the current value is less then or equal to the bottom quarter on the chart
      BigDecimal oneQuater = lowerbd.add(difference);

      if (oneQuater.compareTo(currentbd) == 0 || oneQuater.compareTo(currentbd) == 1) 
      {
        this.cp.setYAxisUpperUSD(Double.parseDouble(upperbd.subtract(difference).toString()));
        this.cp.setYAxisLowerUSD(Double.parseDouble(lowerbd.subtract(difference).toString()));
      }
      indexUSD++;
    }



    if (openTabs.get("GBP/USD")) 
    {
      // chart >= current time on the chart
      if (indexGBP >= timeInSecondsGBP.size()) 
      {
        ArrayList<String> newCatGBP = new ArrayList<String>();
        int seriesFutureSize = timeInSecondsGBP.size() + 1;

        // add 1 seconds into the future
        for (int i = timeInSecondsGBP.size(); i < seriesFutureSize; i++) 
        {
          this.cp.addToCalenderGBP();
          this.cp.addToTimeSecondsGBP(i, this.cp.getSDF().format(this.cp.getCalenderInstanceTimeGBP()));
          newCatGBP.add(this.cp.getSDF().format(this.cp.getCalenderInstanceTimeGBP()));
        }

        // remove first 20 seconds of category, so screen doesn't pile up
        ArrayList<String> sub = this.cp.getTimeSecondsGBP();
        sub.subList(0, 1).clear();
        /**
         * When removing and adding 1,2 or 30 seconds from the both axis then make sure you're also
         * removing the same from the index, otherwise it goes awry.
         */
        // remove 2 from the index so that stays in sync
        indexGBP -= 1;
        XYChart.Series<String, Number> sum = this.cp.getCertainSeries("GBP/USD");

        if (sum.getData().size() >= 100) 
        {
          this.cp.removeSeriesElement("GBP/USD", 0);
        }

        this.cp.setTimeSecondsGBP(sub);
        // remove first 20 from series

        ObservableList<String> updatedCategory = FXCollections.observableArrayList(this.cp.getTimeSecondsGBP());
        this.cp.setXAxisCategoriesGBP(updatedCategory);
        // dunno?
        this.cp.getXAxisGBP().invalidateRange(updatedCategory);

      }


      String dubs3 = message3.get();
      double d3 = Double.parseDouble(dubs3);
      this.cp.updateSeries("GBP/USD", timeInSecondsGBP.get(indexGBP), d3);

      // get upper, lowers boundary strings and current value
      String upper = String.format("%.3f", this.cp.getYAxisUpperGBP());
      String lower = String.format("%.3f", this.cp.getYAxisLowerGBP());
      String current = String.format("%.3f", d3);

      // turn upper, lower and current boundaries into BigDecimals
      BigDecimal upperbd = new BigDecimal(upper);
      BigDecimal lowerbd = new BigDecimal(lower);
      BigDecimal currentbd = new BigDecimal(current);

      // check if the current value is greater then or equal to current
      BigDecimal threeQuater = upperbd.subtract(difference);

      if (threeQuater.compareTo(currentbd) == 0 || threeQuater.compareTo(currentbd) == -1) 
      {
        this.cp.setYAxisUpperGBP(Double.parseDouble(upperbd.add(difference).toString()));
        this.cp.setYAxisLowerGBP(Double.parseDouble(lowerbd.add(difference).toString()));
      }

      // check if the current value is less then or equal to the bottom quarter on the chart
      BigDecimal oneQuater = lowerbd.add(difference);

      if (oneQuater.compareTo(currentbd) == 0 || oneQuater.compareTo(currentbd) == 1) 
      {
        this.cp.setYAxisUpperGBP(Double.parseDouble(upperbd.subtract(difference).toString()));
        this.cp.setYAxisLowerGBP(Double.parseDouble(lowerbd.subtract(difference).toString()));
      }
      indexGBP++;
    }


    if (openTabs.get("USD/CHF")) 
    {
      // chart >= current time on the chart
      if (indexCHF >= timeInSecondsCHF.size()) 
      {
        ArrayList<String> newCatCHF = new ArrayList<String>();
        int seriesFutureSizeCHF = timeInSecondsCHF.size() + 1;

        // add 1 seconds into the future
        for (int i = timeInSecondsCHF.size(); i < seriesFutureSizeCHF; i++) 
        {
          this.cp.addToCalenderCHF();
          this.cp.addToTimeSecondsCHF(i, this.cp.getSDF().format(this.cp.getCalenderInstanceTimeCHF()));
          newCatCHF.add(this.cp.getSDF().format(this.cp.getCalenderInstanceTimeCHF()));
        }

        // remove first 20 seconds of category, so screen doesn't pile up
        ArrayList<String> sub = this.cp.getTimeSecondsCHF();
        sub.subList(0, 1).clear();
        /**
         * When removing and adding 1,2 or 30 seconds from the both axis then make sure you're also
         * removing the same from the index, otherwise it goes awry.
         */
        // remove 2 from the index so that stays in sync
        indexCHF -= 1;

        XYChart.Series<String, Number> sum = this.cp.getCertainSeries("USD/CHF");
        if (sum.getData().size() >= 100) 
        {
          this.cp.removeSeriesElement("USD/CHF", 0);
        }

        this.cp.setTimeSecondsCHF(sub);
        // remove first 20 from series

        ObservableList<String> updatedCategory = FXCollections.observableArrayList(this.cp.getTimeSecondsCHF());
        this.cp.setXAxisCategoriesCHF(updatedCategory);
        // dunno?
        this.cp.getXAxisCHF().invalidateRange(updatedCategory);

      }


      String dubs4 = message4.get();
      double d4 = Double.parseDouble(dubs4);
      this.cp.updateSeries("USD/CHF", timeInSecondsCHF.get(indexCHF), d4);

      // get upper, lowers boundary strings and current value
      String upper = String.format("%.3f", this.cp.getYAxisUpperCHF());
      String lower = String.format("%.3f", this.cp.getYAxisLowerCHF());
      String current = String.format("%.3f", d4);

      // turn upper, lower and current boundaries into BigDecimals
      BigDecimal upperbd = new BigDecimal(upper);
      BigDecimal lowerbd = new BigDecimal(lower);
      BigDecimal currentbd = new BigDecimal(current);

      // check if the current value is greater then or equal to current
      BigDecimal threeQuater = upperbd.subtract(difference);

      if (threeQuater.compareTo(currentbd) == 0 || threeQuater.compareTo(currentbd) == -1) 
      {
        this.cp.setYAxisUpperCHF(Double.parseDouble(upperbd.add(difference).toString()));
        this.cp.setYAxisLowerCHF(Double.parseDouble(lowerbd.add(difference).toString()));
      }

      // check if the current value is less then or equal to the bottom quarter on the chart
      BigDecimal oneQuater = lowerbd.add(difference);

      if (oneQuater.compareTo(currentbd) == 0 || oneQuater.compareTo(currentbd) == 1) {
        this.cp.setYAxisUpperCHF(Double.parseDouble(upperbd.subtract(difference).toString()));
        this.cp.setYAxisLowerCHF(Double.parseDouble(lowerbd.subtract(difference).toString()));
      }
      indexCHF++;
    }
  }

  public void updateLabels() 
  {  
    String now1 = message1.get();
    String before1 = this.cupp.getLabel1().getText();
          
    if (now1.compareTo(before1) < 0)
    {
      this.cupp.getLabel1().setTextFill(Color.RED);  
    }
    else if (now1.compareTo(before1) > 0)
    {
      this.cupp.getLabel1().setTextFill(Color.LIME);
    }
    else
    {
      this.cupp.getLabel1().setTextFill(Color.BLACK);
    }
    
    this.cupp.setLabel1Text(now1);

    /**~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~***/
    
    String now2 = message2.get();   
    String before2 = this.cupp.getLabel2().getText();
    
    
    if (now2.compareTo(before2) < 0)
    {
      this.cupp.getLabel2().setTextFill(Color.RED);  
    }
    else if (now2.compareTo(before2) > 0)
    {
      this.cupp.getLabel2().setTextFill(Color.LIME);
    }
    else
    {
      this.cupp.getLabel2().setTextFill(Color.BLACK);
    }
    
    this.cupp.setLabel2Text(now2);

    /**~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~***/
    
    String now3 = message3.get();   
    String before3 = this.cupp.getLabel3().getText();
    
    
    if (now3.compareTo(before3) < 0)
    {
      this.cupp.getLabel3().setTextFill(Color.RED);  
    }
    else if (now3.compareTo(before3) > 0)
    {
      this.cupp.getLabel3().setTextFill(Color.LIME);
    }
    else
    {
      this.cupp.getLabel3().setTextFill(Color.BLACK);
    }
    
    this.cupp.setLabel3Text(now3);

    /**~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~*~***/
    
    String now4 = message4.get();
    String before4 = this.cupp.getLabel4().getText();
    
    
    if (now4.compareTo(before4) < 0)
    {
      this.cupp.getLabel4().setTextFill(Color.RED);  
    }
    else if (now4.compareTo(before4) > 0)
    {
      this.cupp.getLabel4().setTextFill(Color.LIME);
    }
    else
    {
      this.cupp.getLabel4().setTextFill(Color.BLACK);
    }
    
    this.cupp.setLabel4Text(now4);
  }
}
