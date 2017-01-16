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
import javafx.scene.control.Label;
import view.FXViewChartPane;
import view.FXViewCurrencyPairPane;
import view.FXViewMenuPane;
import view.FXViewRootPane;

public class FXController {
  
  private FXViewRootPane view;
  private FXViewChartPane cp;
  private FXViewCurrencyPairPane cupp;
  private FXViewMenuPane mp;
  
  private static StringProperty message1; // static ?
  private static StringProperty message2;
  private static StringProperty message3;
  private static StringProperty message4;

  private static String update;
  
  
  public FXController (FXViewRootPane view){
      // reference to view - eventually view and controller.
      this.view = view;
      
      // get a reference to all panes
      this.cp = view.getChartPane();
      this.cupp = view.getCurrencyPairPane();
      this.mp = view.getMenuPane();
      
      //attach event handlers to view using private helper method
      this.attachEventHandlers();
  }
  
  public void attachEventHandlers(){
    System.out.println("?");
    cp.addUpHandler(new UpHandler());
    // cp.addDownHandler(new DownHandler());
  }
  
  private class UpHandler implements EventHandler<ActionEvent> {
      public void handle(ActionEvent e) {
        System.out.println("????");
        cp.addToChart();
      }
  }
  
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
