package view;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class FXViewLoginPage extends BorderPane {
  
  private FXViewLoginPane lp;
  
  public FXViewLoginPage(){
    
    lp = new FXViewLoginPane();
    this.setCenter(lp);
    
  }
  
  private class FXViewLoginPane extends HBox {
    
    public FXViewLoginPane(){
        // ==================================================== Sort this out in a bit
      
        Label l1 = new Label("Email Address: ");
        l1.setFont(new Font("Arial", 20));
        Label l2 = new Label("Password: ");
        l2.setFont(new Font("Arial", 20));    
        VBox vb1 = new VBox();
        vb1.getChildren().addAll(l1,l2);
        
        TextField t1 = new TextField();
        TextField t2 = new TextField();       
        VBox vb2 = new VBox();
        vb2.getChildren().addAll(t1,t2);
        
        Button b2 = new Button("Register");
        b2.setMinSize(75,35);
        b2.setMaxSize(75,35);
        b2.setPrefSize(75,35);
        
        Button b1 = new Button("Logon");
        b1.setMinSize(75,35);
        b1.setMaxSize(75,35);
        b1.setPrefSize(75,35);  
        
        HBox hb1 = new HBox();
        hb1.getChildren().addAll(b1,b2);

        
        this.setSpacing(8);
        this.getChildren().add(vb1);;
        this.getChildren().add(vb2);
        this.getChildren().add(hb1);

     }
  }
}
