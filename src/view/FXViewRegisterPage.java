package view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

public class FXViewRegisterPage extends BorderPane {
  
private FXViewRegisterPane lp;
private Button b1, b2;
  
  public FXViewRegisterPage(){
      
    lp = new FXViewRegisterPane();   
    AnchorPane ap = new AnchorPane();
    ap.getChildren().add(lp);

    this.setCenter(ap);
    this.setLeft(setBorderPaneSpacesLeftRight());
    this.setRight(setBorderPaneSpacesLeftRight());
    this.setTop(setBorderPaneSpacesUpDown());
    this.setBottom(setBorderPaneSpacesUpDown()); 
  }
  
  private class FXViewRegisterPane extends HBox {
    
    public FXViewRegisterPane(){
      
        Label l1 = new Label("First Name: ");
        l1.setFont(new Font("Arial", 15)); 
        TextField t1 = new TextField();    
        t1.setPrefSize(300,30); 
        
        Label l2 = new Label("Last Name: ");
        l2.setFont(new Font("Arial", 15)); 
        TextField t2 = new TextField();    
        t2.setPrefSize(300,30); 
        
        Label l3 = new Label("Username: ");
        l3.setFont(new Font("Arial", 15)); 
        TextField t3 = new TextField();    
        t3.setPrefSize(300,30); 
        
        Label l4 = new Label("Email Address: ");
        l4.setFont(new Font("Arial", 15)); 
        TextField t4 = new TextField();    
        t4.setPrefSize(300,30); 
        
        Label l5 = new Label("Password: ");
        l5.setFont(new Font("Arial", 15)); 
        TextField t5 = new TextField(); 
        t5.setPrefSize(300,30);
        
        Label l6 = new Label("Re-enter Password: ");
        l6.setFont(new Font("Arial", 15)); 
        TextField t6 = new TextField();
        t6.setPrefSize(300,30); 
        
        
        VBox vb1 = new VBox();
        vb1.getChildren().addAll(l1,t1,l2,t2,l3,t3,l4,t4,l5,t5,l6,t6);
        vb1.setSpacing(2);
               
        b2 = new Button("Register");
        b2.setPrefSize(160,35);      
        b1 = new Button("Back");
        b1.setPrefSize(160,35);
              
        HBox hb1 = new HBox();
        hb1.getChildren().addAll(b1,b2);
        vb1.getChildren().addAll(hb1);
        vb1.setPadding(new Insets(100, 100, 100, 100));
        vb1.setSpacing(10);
        
        this.setSpacing(8);
        this.getChildren().add(vb1);
     }
  }
  
  public VBox setBorderPaneSpacesLeftRight(){
    
    VBox vblr = new VBox();
    vblr.setPrefSize(400,500);      
    vblr.setStyle("-fx-background-color : white");   
    return vblr;
  }  
  
  public VBox setBorderPaneSpacesUpDown(){
    
    VBox vbud = new VBox();
    vbud.setPrefSize(100,50);      
    vbud.setStyle("-fx-background-color : white");   
    return vbud;
  } 
  
  public void addRegisterInfoHandler(EventHandler<ActionEvent> handler) {
    b2.setOnAction(handler);
  }
 
  public void addBackHandler(EventHandler<ActionEvent> handler) {
    b1.setOnAction(handler);
  }
      
  

}
