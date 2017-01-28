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
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import view.FXViewChartPane;
import view.FXViewCurrencyPairPane;
import view.FXViewLoginPage;
import view.FXViewMenuPane;
import view.FXViewRegisterPage;
import view.FXViewRootPane;
import java.util.regex.*;

public class FXController {
  
  private FXViewLoginPage lg;
  private FXViewRootPane rp;
  private FXViewRegisterPage re;
  private FXViewChartPane cp;
  private FXViewCurrencyPairPane cupp;
  private FXViewMenuPane mp;
  
  private Stage window;
  
  private static StringProperty message1; // static ? review this
  private static StringProperty message2;
  private static StringProperty message3;
  private static StringProperty message4;
  
  private Scene login;
  private Scene register;
  private Scene market;

  private static String update;
   
  public FXController (FXViewLoginPage lg)
  {
      // reference to view - eventually view and controller.    
      this.lg = lg;
      this.rp = new FXViewRootPane();
      this.re = new FXViewRegisterPage(); 
      this.cp = this.rp.getChartPane();
      this.cupp = this.rp.getCurrencyPairPane();
      this.mp = this.rp.getMenuPane();
          
      register = new Scene(this.re);
      market = new Scene(this.rp);
    
      this.attachEventHandlers();       
  }
  
  
  public void attachEventHandlers()
  {
    this.lg.addRegisterHandler(e -> this.setSceneToBeDisplayed("Register"));
    this.lg.addLoginHandler(e -> this.setSceneToBeDisplayed("Market"));   
    this.re.addBackHandler(e -> this.setSceneToBeDisplayed("Back"));
    this.re.addRegisterInfoHandler(e -> this.setSceneToBeDisplayed("Login"));
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
                  Socket kkSocket = new Socket("PC", 4444);          
                  PrintWriter out = new PrintWriter(kkSocket.getOutputStream(), true);         
                  BufferedReader in = new BufferedReader( new InputStreamReader(kkSocket.getInputStream()) );    
              )  
              {  
                  BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));        
                  String fromServer;          
                  while ((fromServer = in.readLine()) != null) {
                    
                      // Platform.runLater? 
                      
                      update = fromServer;
                      String[] s = update.split("-");
                      
                        Platform.runLater(new Runnable() {
                         public void run() {
                             message1.set("1." + s[0]);
                             message2.set("1." + s[1]);                         
                             message3.set("2." + s[2]);                         
                             message4.set("1." + s[3]);                         
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
             this.validateTraderDetails();
             this.setTraderDetails();
             window.setScene(login);
             break;
           case "Back":
             window.setScene(login);
             break;
           case "Market":
             window.setScene(market);
             break;
           case "Register":
             window.setScene(register);
             break;
         }
        
        window.show();
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
  }
  
  public boolean validateTraderDetails()
  {
    boolean traderDetailsValid = true;
    
    // first check that all has been entered!
    
    // validate first name, last name, 
    
    // username
    
    // email address
    
    // password and the re-entered
       
    
    if(validateName(this.re.getFirstName()))
    {
      System.out.println("The first name is valid!!!!");
    }
    else
    {
      System.out.println("This isn't valid mate :(");
    }
    
    if(validateUsername(this.re.getUsername()))
    {
      System.out.println("This username is validdddd!!!");
    }
    else
    {
      System.out.println("Username naaaahhhh!!");
    }
    
    if(validateEmail(this.re.getEmail()))
    {
      System.out.println("This is a VALID EMAIL");
    }
    else
    {
      System.out.println("This isn't a valid email");
    }
    
    if(vaildatePassword(this.re.getPassword()))
    {
      System.out.println("This is a VALID password");
      
      // if password is valid, check the user has reentered it properly
      if(this.re.getPassword().compareTo(this.re.getPasswordConfirmed()) == 0)
      {
        System.out.println("The renetered password is the same");
      }
      else
      {
        System.out.println("The reentered password is NOT the same");
      }
    }
    else
    {
      System.out.println("This isnt valid password");
    }
    
    
    // 
    
       
    return traderDetailsValid;
  }
  
  public boolean validateName(String name)
  {
    Pattern regex = Pattern.compile("[A-Za-z]{2,15}");    
    Matcher m = regex.matcher(name.trim());    
    boolean isNameValid = m.matches();
    return isNameValid;
  }
  
  public boolean validateUsername(String username)
  {
    Pattern regex = Pattern.compile("[A-Za-z0-9]{1,10}");
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
}
