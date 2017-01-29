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
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class FXViewRegisterPage extends BorderPane {
  
private FXViewRegisterPane lp;
private Button b1, b2;
private TextField t1, t2, t3, t4, t5, t6;
private Label l1a, l2a, l3a, l4a, l5a, l6a; 
  
   public FXViewRegisterPage()
  {    
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
      
        
        Label l1 = new Label("First Name");
        l1.setFont(new Font("Arial", 15));
        l1a = new Label("- length between 3 & 15");
        l1a.setVisible(false);
        l1a.setTextFill(Color.web("red"));
        l1a.setPadding(new Insets(0, 0, 0, 5));
        HBox hb1 = new HBox(l1,l1a);       
        t1 = new TextField();    
        t1.setPrefSize(300,30); 
        
  
        Label l2 = new Label("Last Name");
        l2.setFont(new Font("Arial", 15)); 
        l2a = new Label("- length between 3 & 15");
        l2a.setVisible(false);
        l2a.setTextFill(Color.web("red"));
        l2a.setPadding(new Insets(0, 0, 0, 5));
        HBox hb2 = new HBox(l2,l2a);       
        t2 = new TextField();    
        t2.setPrefSize(300,30); 
   
        
        Label l3 = new Label("Username");
        l3.setFont(new Font("Arial", 15)); 
        l3a = new Label("- incorrect format");
        l3a.setVisible(false);
        l3a.setTextFill(Color.web("red"));
        l3a.setPadding(new Insets(0, 0, 0, 10));
        HBox hb3 = new HBox(l3,l3a);       
        t3 = new TextField();    
        t3.setPrefSize(300,30); 

        
        Label l4 = new Label("Email Address");
        l4.setFont(new Font("Arial", 15)); 
        l4a = new Label("- incorrect format");
        l4a.setVisible(false);
        l4a.setTextFill(Color.web("red"));
        l4a.setPadding(new Insets(0, 0, 0, 5));
        HBox hb4 = new HBox(l4,l4a);       
        t4 = new TextField();    
        t4.setPrefSize(300,30); 

        Label l5 = new Label("Password ");
        l5.setFont(new Font("Arial", 15)); 
        l5a = new Label("- incorrect format");
        l5a.setVisible(false);
        l5a.setTextFill(Color.web("red"));
        l5a.setPadding(new Insets(0, 0, 0, 5));
        HBox hb5 = new HBox(l5,l5a);       
        t5 = new TextField(); 
        t5.setPrefSize(300,30);

        
        Label l6 = new Label("Re-enter Password");
        l6.setFont(new Font("Arial", 15)); 
        l6a = new Label("- incorrect format");
        l6a.setVisible(false);
        l6a.setTextFill(Color.web("red"));
        l6a.setPadding(new Insets(0, 0, 0, 5));
        HBox hb6 = new HBox(l6,l6a); 
        t6 = new TextField();
        t6.setPrefSize(300,30); 

        
        
        VBox vb1 = new VBox();
        vb1.getChildren().addAll(hb1,t1,hb2,t2,hb3,t3,hb4,t4,hb5,t5,hb6,t6);
        vb1.setSpacing(2);
               
        b2 = new Button("Register");
        b2.setPrefSize(160,35);      
        b1 = new Button("Back");
        b1.setPrefSize(160,35);
              
        HBox hb7 = new HBox();
        hb7.getChildren().addAll(b1,b2);
        vb1.getChildren().addAll(hb7);
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
  
  public TextField getFirstName(){
    return t1;
  }
  
  public TextField getLastName(){
    return t2;
  }
  
  public TextField getUsername(){
    return t3;
  }
  
  public TextField getEmail(){
    return t4;
  }
     
  public TextField getPassword(){
    return t5;
  }
  
  public TextField getPasswordConfirmed(){
    return t6;
  }
  
  public Label getErrorFirstName(){
    return l1a;
  }
  
  public Label getErrorLastName(){
    return l2a;
  }
  
  public Label getErrorUsername(){
    return l3a;
  }
  
  public Label getErrorEmail(){
    return l4a;
  }
     
  public Label getErrorPassword(){
    return l5a;
  }
  
  public Label getErrorPasswordConfirmed(){
    return l6a;
  }
  

}
