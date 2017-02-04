package controller;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.Socket;
import java.net.URL;
import java.net.UnknownHostException;
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
import javafx.scene.control.TableView;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
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
      // reference to view - eventually view and controller.    
      this.lg = view;
      this.model = model;
     
      this.rp = new FXViewRootPane();
     
      this.re = new FXViewRegisterPage(); 
      this.cp = this.rp.getChartPane();
      this.op = this.cp.returnOrderPane();
      // pop order table
      this.model.addOrder(new Order("GBP", "2,000", "Buy", 1.0, 1.0, 1.0, 1.0, 0));
      this.populateOrderTableOnStart(this.op.returnTableView());
            
      this.cupp = this.rp.getCurrencyPairPane();
      this.mp = this.rp.getMenuPane();
      this.of = new FXViewNewOrderForm();
          
      register = new Scene(this.re);
      market = new Scene(this.rp); 
      modal = new Scene(this.of, 400, 300); 

        
      this.attachEventHandlers();       
  }
  
  
  public void attachEventHandlers()
  {
    this.lg.addRegisterHandler(e -> this.setSceneToBeDisplayed("Register"));
    this.lg.addLoginHandler(e -> this.setSceneToBeDisplayed("Market"));   
    this.re.addBackHandler(e -> this.setSceneToBeDisplayed("Back"));
    this.re.addRegisterInfoHandler(e -> this.setSceneToBeDisplayed("Login"));
    this.mp.addLogOutHandler(e -> this.setSceneToBeDisplayed("Logout"));
    this.mp.addOpenChartHandler(e -> this.newOrderInputBox());
    this.of.buyButtonHandler(e -> this.processBuyOrder());
    this.of.sellButtonHandler(e -> this.processSellOrder());
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
    
    
    Order buy = new Order(currency, quantity, direction, price, currentPrice, takeProfit, stopLoss, result);
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
    
    
    Order buy = new Order(currency, quantity, direction, price, currentPrice, takeProfit, stopLoss, result);
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
    bindSocketValuesToLabels();
    service.start();   
  }
  
  private static class FirstLineService extends Service<String> 
  {
    
    protected Task<String> createTask() {
              
       return new Task<String>() {
            protected String call() 
                throws IOException, MalformedURLException {     
              try (
                  Socket kkSocket = new Socket("192.168.1.20",4444);   
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
               //this.startSocketListener();
               window.setScene(market);
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
  
  public void bindSocketValuesToLabels ()
  {   
    message1 = new SimpleStringProperty();
    Label l1 = cupp.getLabel1();
    l1.textProperty().bind(message1); 
    l1.textProperty().addListener((observable, oldValue, newValue) -> {
        this.updateCharts();    
    });
    
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
  
  public void updateCharts()
  {
    XYChart.Series<Number, Number> series = this.cp.getSeries();
    String dubs = message1.get();
    
    double d = Double.parseDouble(dubs);
    this.cp.updateSeries(notStayingHere, d);
    notStayingHere++;
    
  }
}
