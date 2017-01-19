package view;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class FXViewLoginPage extends BorderPane {
  
  private FXViewLoginPane lp;
  
  public FXViewLoginPage(){
    
    
    lp = new FXViewLoginPane();
    AnchorPane ap = new AnchorPane();
    ap.getChildren().add(lp);

    this.setCenter(ap);
    this.setLeft(setBorderPaneSpacesLeftRight());
    this.setRight(setBorderPaneSpacesLeftRight());
    this.setTop(setBorderPaneSpacesUpDown());
    this.setBottom(setBorderPaneSpacesUpDown()); 
  }
  
  private class FXViewLoginPane extends HBox {
    
    public FXViewLoginPane(){
      
        Label l1 = new Label("Email Address: ");
        l1.setFont(new Font("Arial", 15)); 
        TextField t1 = new TextField();
        t1.setPrefSize(300,30); 
        Label l2 = new Label("Password: ");
        l2.setFont(new Font("Arial", 15)); 
        TextField t2 = new TextField(); 
        t2.setPrefSize(300,30);
        
        VBox vb1 = new VBox();
        vb1.getChildren().addAll(l1,t1,l2,t2);
        vb1.setSpacing(2);
               
        Button b2 = new Button("Register");
        b2.setPrefSize(160,35);      
        Button b1 = new Button("Logon");
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
    vbud.setPrefSize(100,150);      
    vbud.setStyle("-fx-background-color : white");   
    return vbud;
  } 
    
  
}
