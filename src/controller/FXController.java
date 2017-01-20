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
   
  public FXController (FXViewLoginPage lg){
      // reference to view - eventually view and controller.    
      this.lg = lg;
      this.rp = new FXViewRootPane();
      this.re = new FXViewRegisterPage(); 
      this.cp = rp.getChartPane();
      this.cupp = rp.getCurrencyPairPane();
      this.mp = rp.getMenuPane();
      
      login = new Scene(new FXViewLoginPage(), 1000, 1200);
      market = new Scene(new FXViewRootPane(), 1000,  1200);
      register = new Scene(new FXViewRegisterPage(), 1000, 1200);

      //attach event handlers to view using private helper method      
      this.attachEventHandlers();     
   
  }
  
//  public void attachEventHandlers(){
//    System.out.println("?");
//    cp.addUpHandler(new UpHandler());
//    // cp.addDownHandler(new DownHandler());
//  }
  
  public void attachEventHandlers(){
    lg.addRegisterHandler(e -> window.setScene(register));
    lg.addLoginHandler(e -> window.setScene(market));   
    re.addBackHandler(e -> window.setScene(login));
    re.addRegisterInfoHandler(e -> window.setScene(login));
  }
  
  
  
//  private class UpHandler implements EventHandler<ActionEvent> {
//      public void handle(ActionEvent e) {
//        System.out.println("????");
//        cp.addToChart();
//      }
//  }
  
  public void startSocketListener(){
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
  
  private static class FirstLineService extends Service<String> {
    
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
                    
                      System.out.println(fromServer);
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
              catch (UnknownHostException e) {
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
  

  
  public void setStage(Stage stage){  
      this.window = stage;    
  };
  
  public void bindSocketValuesToLabels (){ 
   
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
