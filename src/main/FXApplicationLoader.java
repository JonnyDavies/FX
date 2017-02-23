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

 
  
  public void init() {   
     
    view = new FXViewLoginPage();    
    model = new Trader(); 
    controller = new FXController(view, model);
  }
  

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
