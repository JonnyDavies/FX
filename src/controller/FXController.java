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
import javafx.scene.control.Spinner;
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
import view.FXViewPriceAlertConfirmation;
import view.FXViewPriceAlertForm;
import view.FXViewRegisterPage;
import view.FXViewRootPane;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;

import java.util.regex.*;

import org.controlsfx.control.Notifications;

public class FXController {

  private FXViewLoginPage lg;
  private FXViewRootPane rp;
  private FXViewRegisterPage re;
  private FXViewChartPane cp;
  private FXViewCurrencyPairPane cupp;
  private FXViewMenuPane mp;
  private FXViewNewOrderForm of;
  private FXViewPriceAlertForm pa;
  private FXViewPriceAlertConfirmation pc;
  private FXViewOrderPane op;
  private Trader model;

  private Stage window;
  private Stage windowModal;
  private Stage windowPrice;
  private Stage windowPriceAlert;


  private Scene login;
  private Scene register;
  private Scene market;
  private Scene modal;
  private Scene modalPrice;
  private Scene modalConfirm;
  
//  String imagePath = HelloNotificationPane.class.getResource("notification-pane-warning.png").toExternalForm();
//  ImageView image = new ImageView(imagePath);

  private static StringProperty message1; // static ? review this
  private static StringProperty message2;
  private static StringProperty message3;
  private static StringProperty message4;

  private int notStayingHereEUR = 0;
  private int notStayingHereUSD = 0;
  private int notStayingHereGBP = 0;
  private int notStayingHereCHF = 0;

  private HashMap<String, Boolean> tabStatus;

  // for bcrypt password
  private static int workload = 12;


  private static String update;

  public FXController(FXViewLoginPage view, Trader model) {
    this.lg = view;
    this.model = model;

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

    this.register = new Scene(this.re, 1300, 700);
    this.market = new Scene(this.rp, 1300, 700);
    this.modal = new Scene(this.of, 400, 300);
    this.modalPrice = new Scene(this.pa, 300, 230);
    this.modalConfirm = new Scene(this.pc, 300, 230);

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

  public void confirmPriceAlert()
  {
    // validation need for this!!!!!!!!!
    
    // THIS IS CHANGING TOOO !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    
   Double priceAlert = Double.parseDouble(this.pa.returnPrice().getText());
   String currency = this.pa.returnCurrencyPair().getValue();  
   
   System.out.println("Price Alerts = " + priceAlert);
   System.out.println("Currency = " + currency);

   this.model.makePriceAlert(currency, priceAlert);

   windowPrice.close();

  }
  
  public void undisableTakeProfitSpinner() {
    Spinner<Double> temp = this.of.returnTakeProfit();
    Boolean disabled = false;
    disabled = (temp.isDisabled() == true) ? false : true;
    this.of.setDisableTakeProfit(disabled);
  }

  public void undisableStopLossSpinner() {
    Spinner<Double> temp = this.of.returnStopLoss();
    Boolean disabled = false;
    disabled = (temp.isDisabled() == true) ? false : true;
    this.of.setDisableStopLoss(disabled);
  }


  public void closingHousekeepingforUSD() {
    System.out.println("Testing USD");
    notStayingHereUSD = 0;
    this.cp.removeSeries("USD/JPY");
    tabStatus.put("USD/JPY", false);
  }

  public void closingHousekeepingforGBP() {
    System.out.println("Testing GBP");
    notStayingHereGBP = 0;
    this.cp.removeSeries("GBP/USD");
    tabStatus.put("GBP/USD", false);
  }

  public void closingHousekeepingforCHF() {
    System.out.println("Testing CHF");
    notStayingHereCHF = 0;
    this.cp.removeSeries("USD/CHF");
    tabStatus.put("USD/CHF", false);
  }

  public void populateOrderTableOnStart(TableView<Order> order) {
    ObservableList<Order> options = FXCollections.observableArrayList(this.model.getOrders());
    this.op.setItemsTableView(options);
  }

  public void newOrderInputBox() {
    windowModal = new Stage();
    windowModal.initModality(Modality.APPLICATION_MODAL);
    windowModal.alwaysOnTopProperty();
    windowModal.centerOnScreen();
    windowModal.setScene(modal);
    windowModal.getIcons().removeAll();
    windowModal.showAndWait();
  }

  public void priceAlertInputBox() {
    windowPrice = new Stage();
    windowPrice.initModality(Modality.APPLICATION_MODAL);
    windowPrice.alwaysOnTopProperty();
    windowPrice.centerOnScreen();
    windowPrice.setScene(modalPrice);
    windowPrice.getIcons().removeAll();
    windowPrice.showAndWait();
  }

  public void deleteOrder() {
    // get selected from table view
    // delete from table
    // delete it from Orders
    // maybe have class variables for this, give each order a unique value????
    int indexOrder;

    Order selected = this.op.returnTableView().getSelectionModel().getSelectedItem();

    if (selected != null) {
      indexOrder = this.model.getOrders().indexOf(selected);
      this.model.removeOrder(indexOrder);
      ObservableList<Order> orderList = FXCollections.observableArrayList(this.model.getOrders());
      this.op.setItemsTableView(orderList);
    }

    // deal with this, disable button maybe??
    return;
  }



  public HashMap<String, Boolean> findOpenTabs() {
    ObservableList<Tab> options = this.cp.getTabPanes().getTabs();
    tabStatus = new HashMap<String, Boolean>();

    boolean tab1Open = false;
    boolean tab2Open = false;
    boolean tab3Open = false;
    boolean tab4Open = false;

    for (Tab t : options) {
      if (t.getText().equals("EUR/USD")) {
        tab1Open = true;
      }
      if (t.getText().equals("USD/JPY")) {
        tab2Open = true;
      }
      if (t.getText().equals("GBP/USD")) {
        tab3Open = true;
      }
      if (t.getText().equals("USD/CHF")) {
        tab4Open = true;
      }
    }

    tabStatus.put("EUR/USD", tab1Open);
    tabStatus.put("USD/JPY", tab2Open);
    tabStatus.put("GBP/USD", tab3Open);
    tabStatus.put("USD/CHF", tab4Open);

    return tabStatus;
  }

  public void openCharts() {
    String selectedChart = this.mp.getChartCombo().getValue();
    HashMap<String, Boolean> openTabs = this.findOpenTabs();
    // if open don't want it open obviously
    // how are we going to do this??
    // Could even have new panes for each?

    switch (selectedChart) {

      case "EUR/USD":
        if (!openTabs.get("EUR/USD")) {
          // add tab pane()
          this.cp.addTabPane("EUR/USD");
          // add series data
          this.cp.getTabPanes().getSelectionModel().select(this.cp.getEURtab());
        } else {
          // open alert tab already open
          this.cp.getTabPanes().getSelectionModel().select(this.cp.getEURtab());
        }


        break;
      case "USD/JPY":
        // add tab pane()
        if (!openTabs.get("USD/JPY")) {
          this.cp.addTabPane("USD/JPY");
          this.cp.setCloseRequesTab2(e -> this.closingHousekeepingforUSD());
          this.cp.getTabPanes().getSelectionModel().select(this.cp.getUSDtab());
        } else {
          // open alert tab already open
          this.cp.getTabPanes().getSelectionModel().select(this.cp.getUSDtab());
        }

        break;
      case "GBP/USD":

        if (!openTabs.get("GBP/USD")) {
          // add tab pane()
          this.cp.addTabPane("GBP/USD");
          this.cp.setCloseRequesTab3(e -> this.closingHousekeepingforGBP());
          this.cp.getTabPanes().getSelectionModel().select(this.cp.getGBPtab());
        } else {
          // open alert tab already open
          this.cp.getTabPanes().getSelectionModel().select(this.cp.getGBPtab());
        }
        break;
      case "USD/CHF":

        if (!openTabs.get("USD/CHF")) {
          // add tab pane()
          this.cp.addTabPane("USD/CHF");
          this.cp.setCloseRequesTab4(e -> this.closingHousekeepingforCHF());
          this.cp.getTabPanes().getSelectionModel().select(this.cp.getCHFtab());
        } else {
          // open alert tab already open
          this.cp.getTabPanes().getSelectionModel().select(this.cp.getCHFtab());
        }
        break;
    }
  }

  public void processOneClickBuy(String currencyPair) {
    String quantity;

    if (this.cupp.getQuantityToggle1().isSelected()) {
      quantity = this.cupp.getQuantityToggle1().getText();

    } else if (this.cupp.getQuantityToggle2().isSelected()) {
      quantity = this.cupp.getQuantityToggle2().getText();
    } else {
      quantity = this.cupp.getQuantityToggle3().getText();
    }

    String currency = currencyPair;
    String direction = "Buy";
    double currentPrice = Double.parseDouble(this.getCurrentPrice(currency));
    double price = Double.parseDouble(this.getCurrentPrice(currency));
    double takeProfit = 0.0;
    double stopLoss = 0.0;
    Integer result = 0;
    boolean oneClickOrder = true;


    Order buy = new Order(currency, quantity, direction, price, currentPrice, takeProfit, stopLoss,
        result, oneClickOrder);
    this.model.addOrder(buy);
    ObservableList<Order> orderList = FXCollections.observableArrayList(this.model.getOrders());
    this.op.setItemsTableView(orderList);
  }

  public void processOneClickSell(String currencyPair) {
    String quantity;

    if (this.cupp.getQuantityToggle1().isSelected()) {
      quantity = this.cupp.getQuantityToggle1().getText();

    } else if (this.cupp.getQuantityToggle2().isSelected()) {
      quantity = this.cupp.getQuantityToggle2().getText();
    } else {
      quantity = this.cupp.getQuantityToggle3().getText();
    }

    String currency = currencyPair;
    String direction = "Sell";

    double currentPrice = Double.parseDouble(this.getCurrentPrice(currency));
    double price = Double.parseDouble(this.getCurrentPrice(currency));

    double takeProfit = 0.0;
    double stopLoss = 0.0;
    Integer result = 0;
    boolean oneClickOrder = true;


    Order buy = new Order(currency, quantity, direction, price, currentPrice, takeProfit, stopLoss,
        result, oneClickOrder);
    this.model.addOrder(buy);

    ObservableList<Order> orderList = FXCollections.observableArrayList(this.model.getOrders());
    this.op.setItemsTableView(orderList);
  }

  public String getCurrentPrice(String currency) {
    String price = "";

    switch (currency) {
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



  public void processBuyOrder() {
    // create an order from new order box
    // add it to Trader's existing orders
    // or if it is empty populate their list
    // Populate the open order pane
    // Store in the DB

    String quantity = this.of.returnQuantity().getValue();
    String currency = this.of.returnCurrencyPair().getValue();
    String direction = "Buy";

    double currentPrice = Double.parseDouble(this.getCurrentPrice(currency));
    double price = Double.parseDouble(this.getCurrentPrice(currency));
    double takeProfit = this.of.returnTakeProfit().getValue();
    double stopLoss = this.of.returnStopLoss().getValue();
    Integer result = 0;
    boolean oneClickOrder = false;


    Order buy = new Order(currency, quantity, direction, price, currentPrice, takeProfit, stopLoss,
        result, oneClickOrder);
    this.model.addOrder(buy);
    ObservableList<Order> orderList = FXCollections.observableArrayList(this.model.getOrders());
    this.op.setItemsTableView(orderList);

    // store in db. =================== database function started finish it off

    windowModal.close();
  }

  public void processSellOrder() {
    System.out.println("Sell Order");

    // create an order from new order box
    // add it to Trader's existing orders
    // or if it is empty populate their list
    // Populate the open order pane
    // Store in the DB
    String quantity = this.of.returnQuantity().getValue();
    String currency = this.of.returnCurrencyPair().getValue();
    String direction = "Sell";
    double currentPrice = Double.parseDouble(this.getCurrentPrice(currency));
    double price = Double.parseDouble(this.getCurrentPrice(currency));
    double takeProfit = this.of.returnTakeProfit().getValue();
    double stopLoss = this.of.returnStopLoss().getValue();
    Integer result = 0;
    boolean oneClickOrder = false;


    Order buy = new Order(currency, quantity, direction, price, currentPrice, takeProfit, stopLoss,
        result, oneClickOrder);
    this.model.addOrder(buy);
    ObservableList<Order> orderList = FXCollections.observableArrayList(this.model.getOrders());
    this.op.setItemsTableView(orderList);

    // store in db. =================== database function started finish it off

    windowModal.close();
  }

  public void startSocketListener() {
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

  private static class FirstLineService extends Service<String> {

    protected Task<String> createTask() {

      return new Task<String>() {
        protected String call() throws IOException, MalformedURLException {
          try (Socket kkSocket = new Socket("PC", 4444);
              // 10.34.98.62 Uni IP
              // 192.168.1.20 ethernet

              PrintWriter out = new PrintWriter(kkSocket.getOutputStream(), true);
              BufferedReader in =
                  new BufferedReader(new InputStreamReader(kkSocket.getInputStream()));) {
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
          } catch (UnknownHostException e) {
            System.err.println("Don't know about host");
            System.exit(1);

          } catch (IOException e) {
            System.err.println("Couldn't get I/O for the connection to ");
            System.exit(1);
          }
          return "Test";
        }
      };
    }
  }

  public void startTimeline() {
    Timeline tl = this.cp.getTimeline();
    tl.getKeyFrames().add(new KeyFrame(Duration.millis(500), new EventHandler<ActionEvent>() {
      @Override
      public void handle(ActionEvent actionEvent) {
        updateCharts();
        updateOrders();
        checkSLTP();
        checkPriceAlerts();

      }
    }));
    tl.setCycleCount(Animation.INDEFINITE);
    tl.setAutoReverse(true);
    tl.play();
  }
  
  public void checkPriceAlerts()
  {
    
    // Let's have 4 array lists, we'll loop through each one
    
    /**
     * Need to get each price
     * Loop through each array list to see if price exists
     * Open modal to alert them
     * Remove price from array list
     */
    
    Double eur =  Double.parseDouble(message1.get());
    Double usd =  Double.parseDouble(message2.get());
    Double gbp =  Double.parseDouble(message3.get());
    Double chf =  Double.parseDouble(message4.get());
    
    ArrayList<Double> eurPA = this.model.getPriceAlertsEUR();
    ArrayList<Double> usdPA = this.model.getPriceAlertsUSD();
    ArrayList<Double> gbpPA = this.model.getPriceAlertsGBP();
    ArrayList<Double> chfPA = this.model.getPriceAlertsCHF();

    System.out.println(eurPA);
    System.out.println(usdPA);
    System.out.println(gbpPA);
    System.out.println(chfPA);
    

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
                 .graphic(null) // this didnt work but we can figure this out later
                 .title("Price Alert")
                 .text("The Currency Pair " + currency + " has reached " + price + ".")
                 .showWarning();    
  }

  public void updateOrders() {
    ArrayList<Order> orders = this.model.getOrders();

    for (Order o : orders) 
    {
      o.setCurrentPrice(Double.parseDouble(this.getCurrentPrice(o.getCurrencyPair())));
    }

    ObservableList<Order> orderList = FXCollections.observableArrayList(orders);
    this.op.setItemsTableView(orderList);
    this.op.refreshTableView();


  }

  public void checkSLTP() {

    // check for each order for a stop loss or take profit
    // if it has one
    // check if either has been activated
    // if it has remove order and trigger modal (check out delete orders)
    // if not continue


    /**
     * This being removed wrong
     */

    ArrayList<Order> orders = this.model.getOrders();
    ArrayList<Integer> removeOrders = new ArrayList<Integer>();

    for (Order o : orders) {
      // get the current price for the currency pair of this order!
      Double currentPrice =
          new Double(Double.parseDouble(this.getCurrentPrice(o.getCurrencyPair())));

      // get the order stop loss
      Double stopLoss = new Double(o.getStopLoss());

      // get the order take profit
      Double takeProfit = new Double(o.getTakeProfit());


      // dont remove by default
      boolean removeOrder = false;
      String reason = "";


      // if wasn't one click order
      if (stopLoss != 0.0) {
        if (currentPrice.compareTo(stopLoss) == -1 || currentPrice.compareTo(stopLoss) == 0) {
          removeOrder = true;
          reason = "Stop Loss";
        }
      }

      // if wasn't one click order
      if (takeProfit != 0.0) {

        // if less then or equal to, this is right!
        if (currentPrice.compareTo(takeProfit) == 1 || currentPrice.compareTo(takeProfit) == 0) {
          System.out.println("Make it?");
          removeOrder = true;
          reason = "Take Profit";
        }
      }

      if (removeOrder) {
        int id = o.getId();
        removeOrders.add(id);
      }
    }


    for (Order o : this.model.getOrders()) {
      for (Integer i : removeOrders) {
        if (i == o.getId()) {
          this.model.removeSingleOrder(o);
        }
      }
    }

    ObservableList<Order> orderList = FXCollections.observableArrayList(this.model.getOrders());
    this.op.setItemsTableView(orderList);

  }

  public void setSceneToBeDisplayed(String nextScreen) {

    switch (nextScreen) {
      case "Login":
        // validation
        // put a condition round this if validation fails return back to page
        if (this.validateTraderDetails()) {
          this.setTraderDetails();
          window.setScene(login);
        } else {
          window.setScene(register);
        }
        break;
      case "Back":
        window.setScene(login);
        break;
      case "Market":
        if (this.authenticate()) {
          this.startSocketListener();
          // move this
          window.setScene(market);
          this.startTimeline();

        } else {
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


  public void setStage(Stage stage) {
    this.window = stage;
  }

  public void setScene(Scene scene) {
    this.login = scene;
  }

  public void setTraderDetails() {
    System.out.println(this.re.getFirstName());
    Database db = new Database();

    if (!db.doesTableExist("trader")) {
      db.createTable();
    } else {
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

  public boolean validateTraderDetails() {
    boolean traderDetailsValid = true;

    if (validateName(this.re.getFirstName().getText())) {
      System.out.println("The first name is valid!!!!");
    } else {
      this.re.getFirstName().setStyle("-fx-text-box-border: red");
      this.re.getErrorFirstName().setVisible(true);
      traderDetailsValid = false;
    }

    if (validateName(this.re.getLastName().getText())) {
      System.out.println("The first name is valid!!!!");
    } else {
      System.out.println("This isn't valid mate :(");
      this.re.getLastName().setStyle("-fx-text-box-border: red ");
      this.re.getErrorLastName().setVisible(true);
      traderDetailsValid = false;
    }


    if (validateEmail(this.re.getEmail().getText())) {
      System.out.println("This is a VALID EMAIL");
    } else {
      this.re.getEmail().setStyle("-fx-text-box-border: red ");
      this.re.getErrorEmail().setVisible(true);
      traderDetailsValid = false;
    }

    if (vaildatePassword(this.re.getPassword().getText())) {
      // if password is valid, check the user has reentered it properly
      int result =
          this.re.getPassword().getText().compareTo(this.re.getPasswordConfirmed().getText());
      System.out.println(result);

      if (result == 0) {
        System.out.println("The renetered password is the same");
      } else {
        this.re.getPasswordConfirmed().setStyle("-fx-text-box-border: red ");
        this.re.getErrorPasswordConfirmed().setVisible(true);
        traderDetailsValid = false;
      }
    } else {
      this.re.getPasswordConfirmed().setStyle("-fx-text-box-border: red ");
      this.re.getPassword().setStyle("-fx-text-box-border: red ");
      this.re.getErrorPasswordConfirmed().setVisible(true);
      this.re.getErrorPassword().setVisible(true);
      traderDetailsValid = false;
    }

    return traderDetailsValid;
  }

  public boolean validateName(String name) {
    Pattern regex = Pattern.compile("[a-zA-Z]{2,15}");
    Matcher m = regex.matcher(name.trim());
    boolean isNameValid = m.matches();
    return isNameValid;
  }

  public boolean validateUsername(String username) {
    Pattern regex = Pattern.compile("[a-zA-Z0-9]{1,15}");
    Matcher m = regex.matcher(username.trim());
    boolean isUsernameValid = m.matches();
    return isUsernameValid;
  }

  public boolean validateEmail(String email) {
    Pattern regex = Pattern.compile("^[a-zA-Z0-9]{1,}+@[a-za-zA-Z0-9]{1,}+.[a-z]{1,}?.[a-z]{1,}$");
    Matcher m = regex.matcher(email);
    boolean isEmailValid = m.matches();
    return isEmailValid;
  }

  public boolean vaildatePassword(String password) {
    Pattern regex = Pattern.compile("^((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!#$&*?@^~]).{7,30})$");
    Matcher m = regex.matcher(password.trim());
    boolean isPasswordValid = m.matches();
    return isPasswordValid;
  }

  /**
   * Taking this out for now !!!!!!!!!!!!!!!!!!!!!
   */

  public void bindSocketValuesToLabels() {
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

  public boolean authenticate() {
    boolean traderExist = false;
    String userEnterdEmail = this.lg.getEmail().getText();
    String userEnterPwd = this.lg.getPassword().getText();


    Database db = new Database();

    if (db.doesTraderExist(userEnterdEmail)) {
      String storedHashedPwd = db.retreiveTradersPassword(userEnterdEmail);
      traderExist = BCrypt.checkpw(userEnterPwd, storedHashedPwd);
    }

    return traderExist;
  }


  // call this method in the timeline
  // so it will series and label for currency pairs, should make it smoother

  public void updateCharts() {

    String selectedChart = this.mp.getChartCombo().getValue();
    HashMap<String, Boolean> openTabs = this.findOpenTabs();

    BigDecimal difference = new BigDecimal("0.010");

    ArrayList<String> timeInSecondsEUR = this.cp.getTimeSecondsEUR();
    ArrayList<String> timeInSecondsUSD = this.cp.getTimeSecondsUSD();
    ArrayList<String> timeInSecondsGBP = this.cp.getTimeSecondsGBP();
    ArrayList<String> timeInSecondsCHF = this.cp.getTimeSecondsCHF();

    // update labels here to
    this.updateLabels();

    // check if update seconds -- MAY NOT BE STAYING HERE
    // But no errors? so could be working
    // Need the xAxis!
    // so if size reaches 100 add another 100.
    // ======= possible 25% increment like the other axis, only one way

    if (openTabs.get("EUR/USD")) {
      // chart >= current time on the chart
      if (notStayingHereEUR >= timeInSecondsEUR.size()) {
        ArrayList<String> newCatEUR = new ArrayList<String>();


        int seriesFutureSize = timeInSecondsEUR.size() + 1;

        // add 1 seconds into the future
        for (int i = timeInSecondsEUR.size(); i < seriesFutureSize; i++) {
          this.cp.addToCalenderEUR();
          this.cp.addToTimeSecondsEUR(i,
              this.cp.getSDF().format(this.cp.getCalenderInstanceTimeEUR()));
          newCatEUR.add(this.cp.getSDF().format(this.cp.getCalenderInstanceTimeEUR()));
        }


        // remove first 20 seconds of category, so screen doesn't pile up
        ArrayList<String> sub = this.cp.getTimeSecondsEUR();
        sub.subList(0, 1).clear();


        /**
         * When removing and adding 1,2 or 30 seconds from the both axis then make sure you're also
         * removing the same from the index, otherwise it goes awry.
         */
        // remove 2 from the index so that stays in sync
        notStayingHereEUR -= 1;

        XYChart.Series<String, Number> sum = this.cp.getCertainSeries("EUR/USD");

        if (sum.getData().size() >= 100) {
          this.cp.removeSeriesElement("EUR/USD", 0);
        }

        this.cp.setTimeSecondsEUR(sub);
        // remove first 20 from series

        ObservableList<String> updatedCategory =
            FXCollections.observableArrayList(this.cp.getTimeSecondsEUR());
        this.cp.setXAxisCategoriesEUR(updatedCategory);
        // dunno?
        this.cp.getXAxisEUR().invalidateRange(updatedCategory);

      }


      String dubs1 = message1.get();
      double d1 = Double.parseDouble(dubs1);

      this.cp.updateSeries("EUR/USD", timeInSecondsEUR.get(notStayingHereEUR), d1);

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

      if (threeQuater.compareTo(currentbd) == 0 || threeQuater.compareTo(currentbd) == -1) {
        this.cp.setYAxisUpperEUR(Double.parseDouble(upperbd.add(difference).toString()));
        this.cp.setYAxisLowerEUR(Double.parseDouble(lowerbd.add(difference).toString()));
      }

      // check if the current value is less then or equal to the bottom quarter on the chart
      BigDecimal oneQuater = lowerbd.add(difference);

      if (oneQuater.compareTo(currentbd) == 0 || oneQuater.compareTo(currentbd) == 1) {
        this.cp.setYAxisUpperEUR(Double.parseDouble(upperbd.subtract(difference).toString()));
        this.cp.setYAxisLowerEUR(Double.parseDouble(lowerbd.subtract(difference).toString()));
      }

      notStayingHereEUR++;
    }



    if (openTabs.get("USD/JPY")) {
      // chart >= current time on the chart
      if (notStayingHereUSD >= timeInSecondsUSD.size()) {
        ArrayList<String> newCatUSD = new ArrayList<String>();


        int seriesFutureSize = timeInSecondsUSD.size() + 1;

        // add 1 seconds into the future
        for (int i = timeInSecondsUSD.size(); i < seriesFutureSize; i++) {
          this.cp.addToCalenderUSD();
          this.cp.addToTimeSecondsUSD(i,
              this.cp.getSDF().format(this.cp.getCalenderInstanceTimeUSD()));
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
        notStayingHereUSD -= 1;

        XYChart.Series<String, Number> sum = this.cp.getCertainSeries("USD/JPY");

        if (sum.getData().size() >= 100) {
          this.cp.removeSeriesElement("USD/JPY", 0);
        }

        this.cp.setTimeSecondsUSD(sub);
        // remove first 20 from series

        ObservableList<String> updatedCategory =
            FXCollections.observableArrayList(this.cp.getTimeSecondsUSD());
        this.cp.setXAxisCategoriesUSD(updatedCategory);
        // dunno?
        this.cp.getXAxisUSD().invalidateRange(updatedCategory);

      }


      String dubs2 = message2.get();
      double d2 = Double.parseDouble(dubs2);

      this.cp.updateSeries("USD/JPY", timeInSecondsUSD.get(notStayingHereUSD), d2);

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

      if (threeQuater.compareTo(currentbd) == 0 || threeQuater.compareTo(currentbd) == -1) {
        this.cp.setYAxisUpperUSD(Double.parseDouble(upperbd.add(difference).toString()));
        this.cp.setYAxisLowerUSD(Double.parseDouble(lowerbd.add(difference).toString()));
      }

      // check if the current value is less then or equal to the bottom quarter on the chart
      BigDecimal oneQuater = lowerbd.add(difference);

      if (oneQuater.compareTo(currentbd) == 0 || oneQuater.compareTo(currentbd) == 1) {
        this.cp.setYAxisUpperUSD(Double.parseDouble(upperbd.subtract(difference).toString()));
        this.cp.setYAxisLowerUSD(Double.parseDouble(lowerbd.subtract(difference).toString()));
      }

      notStayingHereUSD++;
    }



    if (openTabs.get("GBP/USD")) {
      // chart >= current time on the chart
      if (notStayingHereGBP >= timeInSecondsGBP.size()) {
        ArrayList<String> newCatGBP = new ArrayList<String>();


        int seriesFutureSize = timeInSecondsGBP.size() + 1;

        // add 1 seconds into the future
        for (int i = timeInSecondsGBP.size(); i < seriesFutureSize; i++) {
          this.cp.addToCalenderGBP();
          this.cp.addToTimeSecondsGBP(i,
              this.cp.getSDF().format(this.cp.getCalenderInstanceTimeGBP()));
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
        notStayingHereGBP -= 1;

        XYChart.Series<String, Number> sum = this.cp.getCertainSeries("GBP/USD");

        if (sum.getData().size() >= 100) {
          this.cp.removeSeriesElement("GBP/USD", 0);
        }

        this.cp.setTimeSecondsGBP(sub);
        // remove first 20 from series

        ObservableList<String> updatedCategory =
            FXCollections.observableArrayList(this.cp.getTimeSecondsGBP());
        this.cp.setXAxisCategoriesGBP(updatedCategory);
        // dunno?
        this.cp.getXAxisGBP().invalidateRange(updatedCategory);

      }


      String dubs3 = message3.get();
      double d3 = Double.parseDouble(dubs3);

      this.cp.updateSeries("GBP/USD", timeInSecondsGBP.get(notStayingHereGBP), d3);

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

      if (threeQuater.compareTo(currentbd) == 0 || threeQuater.compareTo(currentbd) == -1) {
        this.cp.setYAxisUpperGBP(Double.parseDouble(upperbd.add(difference).toString()));
        this.cp.setYAxisLowerGBP(Double.parseDouble(lowerbd.add(difference).toString()));
      }

      // check if the current value is less then or equal to the bottom quarter on the chart
      BigDecimal oneQuater = lowerbd.add(difference);

      if (oneQuater.compareTo(currentbd) == 0 || oneQuater.compareTo(currentbd) == 1) {
        this.cp.setYAxisUpperGBP(Double.parseDouble(upperbd.subtract(difference).toString()));
        this.cp.setYAxisLowerGBP(Double.parseDouble(lowerbd.subtract(difference).toString()));
      }

      notStayingHereGBP++;
    }


    if (openTabs.get("USD/CHF")) {
      // chart >= current time on the chart
      if (notStayingHereCHF >= timeInSecondsCHF.size()) {
        ArrayList<String> newCatCHF = new ArrayList<String>();


        int seriesFutureSizeCHF = timeInSecondsCHF.size() + 1;

        // add 1 seconds into the future
        for (int i = timeInSecondsCHF.size(); i < seriesFutureSizeCHF; i++) {
          this.cp.addToCalenderCHF();
          this.cp.addToTimeSecondsCHF(i,
              this.cp.getSDF().format(this.cp.getCalenderInstanceTimeCHF()));
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
        notStayingHereCHF -= 1;

        XYChart.Series<String, Number> sum = this.cp.getCertainSeries("USD/CHF");

        if (sum.getData().size() >= 100) {
          this.cp.removeSeriesElement("USD/CHF", 0);
        }

        this.cp.setTimeSecondsCHF(sub);
        // remove first 20 from series

        ObservableList<String> updatedCategory =
            FXCollections.observableArrayList(this.cp.getTimeSecondsCHF());
        this.cp.setXAxisCategoriesCHF(updatedCategory);
        // dunno?
        this.cp.getXAxisCHF().invalidateRange(updatedCategory);

      }


      String dubs4 = message4.get();
      double d4 = Double.parseDouble(dubs4);

      this.cp.updateSeries("USD/CHF", timeInSecondsCHF.get(notStayingHereCHF), d4);

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

      if (threeQuater.compareTo(currentbd) == 0 || threeQuater.compareTo(currentbd) == -1) {
        this.cp.setYAxisUpperCHF(Double.parseDouble(upperbd.add(difference).toString()));
        this.cp.setYAxisLowerCHF(Double.parseDouble(lowerbd.add(difference).toString()));
      }

      // check if the current value is less then or equal to the bottom quarter on the chart
      BigDecimal oneQuater = lowerbd.add(difference);

      if (oneQuater.compareTo(currentbd) == 0 || oneQuater.compareTo(currentbd) == 1) {
        this.cp.setYAxisUpperCHF(Double.parseDouble(upperbd.subtract(difference).toString()));
        this.cp.setYAxisLowerCHF(Double.parseDouble(lowerbd.subtract(difference).toString()));
      }

      notStayingHereCHF++;
    }

  }

  public void updateLabels() {
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
