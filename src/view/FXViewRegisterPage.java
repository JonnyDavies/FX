package view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

public class FXViewRegisterPage extends GridPane {
  
private Button b1, b2;
private TextField t1, t2, t3;
private PasswordField p1, p2;
private Label l1a, l2a, l3a, l4a, l5a; 
  

    public FXViewRegisterPage(){
      
        this.getColumnConstraints().add(new ColumnConstraints(200)); 
        this.getColumnConstraints().add(new ColumnConstraints(200));
        
        this.setAlignment(Pos.CENTER);
        this.setVgap(8);
        this.setStyle("-fx-background-color : #4d4d4d");

        
        TextFlow text = new TextFlow();
        text.setLineSpacing(3);
        text.setPadding(new Insets(10));
        text.setStyle("-fx-background-color :  #d9d9d9");
        text.setDisable(false);
        
        Text txt1 = new Text("\t\tPassword Creation Requirements \n");
        txt1.setFont(Font.font("", FontWeight.BOLD, 16));
        txt1.setFill(Color.BLACK);
        text.getChildren().add(txt1);
    
        Text txt2 = new Text(" - Length between 7 & 30 characters \n - Must contain atleast one upper case letter (A - Z)\n " + 
        "- Must contain atleast one lower case letter (a - z)\n - Must contain atleast one number (0 - 9)");
        txt2.setFill(Color.BLACK);
        txt2.setFont(new Font(13));
        text.getChildren().add(txt2);

        this.add(text, 0, 1, 2, 1);
      
        
        Label l1 = new Label("First Name");
        l1.setFont(Font.font("", FontWeight.BOLD, 15));
        l1.setTextFill(Color.WHITE);

        l1a = new Label("Length between 3 & 15");
        l1a.setVisible(false);
        l1a.setFont(Font.font("", FontWeight.BOLD, 13));
        l1a.setTextFill(Color.web("white"));
        l1a.setPrefSize(200,20); 
        l1a.setAlignment(Pos.CENTER);
        l1a.setStyle("-fx-background-color : red");
        
        this.add(l1, 0, 2);
        this.add(l1a, 1, 2);

                
        t1 = new TextField();    
        t1.setPrefSize(300,30); 
        
        this.add(t1, 0, 3, 2, 1);
 
        Label l2 = new Label("Last Name");
        l2.setFont(Font.font("", FontWeight.BOLD, 15)); 
        l2.setTextFill(Color.WHITE);

        l2a = new Label("Length between 3 & 15");
        l2a.setVisible(false);
        l2a.setFont(Font.font("", FontWeight.BOLD, 13));
        l2a.setTextFill(Color.web("white"));
        l2a.setPrefSize(200,20); 
        l2a.setAlignment(Pos.CENTER);
        l2a.setStyle("-fx-background-color : red");
        
        this.add(l2, 0, 4);
        this.add(l2a, 1, 4);
        
        
        t2 = new TextField();    
        t2.setPrefSize(300,30); 

        this.add(t2, 0, 5, 5, 1);
        
        Label l3 = new Label("Email Address");
        l3.setFont(Font.font("", FontWeight.BOLD, 15)); 
        l3.setTextFill(Color.WHITE);
        
        l3a = new Label("Incorrect format");
        l3a.setVisible(false);
        l3a.setFont(Font.font("", FontWeight.BOLD, 13));
        l3a.setTextFill(Color.web("white"));
        l3a.setPrefSize(200,20); 
        l3a.setAlignment(Pos.CENTER);
        l3a.setStyle("-fx-background-color : red");
        
        this.add(l3, 0, 6);
        this.add(l3a, 1, 6);
        
        t3 = new TextField();    
        t3.setPrefSize(300,30); 
        
        this.add(t3, 0, 7, 7, 1);

        Label l4 = new Label("Password ");
        l4.setFont(Font.font("", FontWeight.BOLD, 15)); 
        l4.setTextFill(Color.WHITE);
       
        l4a = new Label("Incorrect format. See above");
        l4a.setVisible(false);
        l4a.setFont(Font.font("", FontWeight.BOLD, 13));
        l4a.setTextFill(Color.web("white"));
        l4a.setPrefSize(200,20); 
        l4a.setAlignment(Pos.CENTER);
        l4a.setStyle("-fx-background-color : red");
        
        this.add(l4, 0, 8);
        this.add(l4a, 1, 8);
        
        p1 = new PasswordField(); 
        p1.setPrefSize(300,30);

        this.add(p1, 0, 9, 9, 1);

        Label l5 = new Label("Re-enter Password");
        l5.setFont(Font.font("", FontWeight.BOLD, 15)); 
        l5.setTextFill(Color.WHITE);

        
        l5a = new Label("Incorrect format. See above");
        l5a.setVisible(false);
        l5a.setFont(Font.font("", FontWeight.BOLD, 13));
        l5a.setTextFill(Color.web("white"));
        l5a.setPrefSize(200,20); 
        l5a.setAlignment(Pos.CENTER);
        l5a.setStyle("-fx-background-color : red");
        this.add(l5, 0, 10);
        this.add(l5a, 1, 10);
        
        p2 = new PasswordField();
        p2.setPrefSize(300,30); 

        this.add(p2, 0, 11, 11, 1);
               
        b2 = new Button("Register");
        b2.setFont(Font.font("", FontWeight.BOLD, 14));
        b2.setPrefSize(200,35);  
        b2.setPadding( new Insets( 0, 0, 0, 1));

        b1 = new Button("Back");
        b1.setFont(Font.font("", FontWeight.BOLD, 14));
        b1.setPrefSize(200,35);
        b1.setPadding( new Insets( 0, 1, 0, 0));

             
        this.add(b2, 1, 12);
        this.add(b1, 0, 12);

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
    
  public TextField getEmail(){
    return t3;
  }
     
  public PasswordField getPassword(){
    return p1;
  }
  
  public PasswordField getPasswordConfirmed(){
    return p2;
  }
  
  public Label getErrorFirstName(){
    return l1a;
  }
  
  public Label getErrorLastName(){
    return l2a;
  }
  
  public Label getErrorEmail(){
    return l3a;
  }
     
  public Label getErrorPassword(){
    return l4a;
  }
  
  public Label getErrorPasswordConfirmed(){
    return l5a;
  }

}
