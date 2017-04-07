package main;

import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import model.Trader;
import view.FXViewLoginPage;
import view.FXViewRegisterPage;
import view.FXViewRootPane;

import java.math.BigDecimal;

import controller.FXController;


/**
 * FXApplicationLoader starting point of
 * the application.
 *  @author JD
 */
public class FXApplicationLoader extends Application 
{
  private FXViewLoginPage view;
  private FXController controller;
  private Trader model;

  public void init() 
  {   
    view = new FXViewLoginPage();    
    model = new Trader(); 
    controller = new FXController(view, model);
  }
  
  @Override
  public void start(Stage window) throws Exception 
  {   
    Rectangle2D screenSize = Screen.getPrimary().getVisualBounds();
    Scene login = new Scene(view, screenSize.getWidth(), screenSize.getHeight());   
    controller.setStage(window);
    controller.setScene(login);
    window.setTitle("FX");
    window.setScene(login);
    window.show();   
    window.setMaximized(true);
  }
     
  public static void main(String[] args) 
  {
    launch(args);
  }

}
