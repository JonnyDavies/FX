package main;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.Trader;
import view.FXViewLoginPage;
import view.FXViewRegisterPage;
import view.FXViewRootPane;
import controller.FXController;

public class FXApplicationLoader extends Application {

  // normally this
  // private FXViewRootPane view;
  private FXViewLoginPage view;
  private FXController controller;
  private Trader model;

 
  
  /**
   * Find out exactly what the init function does
   * one-time? obviously, what's called first etc
   */
  public void init() {   
    

    view = new FXViewLoginPage();    
    model = new Trader(); // this will be something like FXTrader but rename once UML diagrams are done!
    controller = new FXController(view, model); //    new FXController(model, view); 
  }
  

  /**
   * Same with this, what's the difference between the init and start?
   * They must do similar things!
   */
  
  @Override
  public void start(Stage window) throws Exception {
    
      Scene login = new Scene(view);
    
      // we need to figure out where how we're going to change the scene obviously contoller 
      controller.setStage(window);
      // important we keep this reference to the scene
      controller.setScene(login);
      window.setTitle("FX");
      window.setScene(login);
      window.show();   
      window.setMaximized(true);

      // service start?
      
      /**
       * Commenting this out for a bit while work on login and register page
       */
  }
     
  /*
   * This called first. Does launch look for ini or start next? 
   */
   public static void main(String[] args) {
    // TODO Auto-generated method stub
    launch(args);
  }

}
