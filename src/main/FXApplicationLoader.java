package main;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import view.FXViewRootPane;
import controller.FXController;

public class FXApplicationLoader extends Application {

  private FXViewRootPane view;
  private FXController controller;
 
  
  /**
   * Find out exactly what the init function does
   * one-time? obviously, what's called first etc
   */
  
  
  public void init() {   
    view = new FXViewRootPane();
  //  FXModel model = new FXModel(); // this will be something like FXTrader but rename once UML diagrams are done!
    controller = new FXController(view); //    new FXController(model, view); 

  }
  
  /**
   * Testing out Git and sourcetree!!
   */
    /*
     * ONce again another test!
     */
  /**
   * Same with this, what's the difference between the init and start?
   * They must do similar things!
   */
  
  @Override
  public void start(Stage stage) throws Exception {
      stage.setTitle("FX");
      stage.setScene(new Scene(view));
      stage.show();   
      // service start?
      controller.startSocketListener();
  }
     
  /*
   * This called first. Does launch look for ini or start next? 
   */
   public static void main(String[] args) {
    // TODO Auto-generated method stub
    launch(args);
  }

}
