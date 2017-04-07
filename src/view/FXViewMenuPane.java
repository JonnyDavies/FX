package view;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import org.controlsfx.glyphfont.FontAwesome;
import org.controlsfx.glyphfont.GlyphFont;
import org.controlsfx.glyphfont.GlyphFontRegistry;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class FXViewMenuPane extends VBox {
  
  private Button b1, b2, b3, b4;
  private ComboBox <String> cb1, cb2;
  private Label l1, l2, l3;
  private GlyphFont fontAwesome = GlyphFontRegistry.font("FontAwesome");

  
  public FXViewMenuPane(){
    
    

    b1 = new Button(" Logout", fontAwesome.create(FontAwesome.Glyph.SIGN_OUT).color(Color.SILVER));
    b1.setPrefSize(80,30);
    b1.setTextFill(Color.WHITE);
    b1.setStyle("-fx-background-color : #4d4d4d");
    
    b2 = new Button(" New Order", fontAwesome.create(FontAwesome.Glyph.PLUS).color(Color.LIME));
    b2.setPrefSize(95,30);
    b2.setTextFill(Color.WHITE);
    b2.setStyle("-fx-background-color : #4d4d4d");
    
    b3 = new Button(" Delete Order", fontAwesome.create(FontAwesome.Glyph.REMOVE).color(Color.RED));
    b3.setPrefSize(105,30);
    b3.setTextFill(Color.WHITE);
    b3.setStyle("-fx-background-color : #4d4d4d");
    
    b4 = new Button(" Price Alert", fontAwesome.create(FontAwesome.Glyph.BELL).color(Color.YELLOW));
    b4.setPrefSize(95,30);
    b4.setTextFill(Color.WHITE);
    b4.setStyle("-fx-background-color : #4d4d4d");
    
    
          
    ObservableList<String> options = 
        FXCollections.observableArrayList(
            "EUR/USD",
            "USD/JPY",
            "GBP/USD",
            "USD/CHF"
        );

    cb1 = new ComboBox<String>(options);
    cb1.setPrefSize(95,30);
    cb1.setStyle("-fx-background-color : #d9d9d9");
    cb1.getSelectionModel().selectFirst();

    
    l1 = new Label(" Charts ", fontAwesome.create(FontAwesome.Glyph.AREA_CHART).color(Color.SKYBLUE));
    l1.setTextFill(Color.WHITE);
    
    
    
    ObservableList<String> quantity = 
      FXCollections.observableArrayList(
          "1000",
          "2000",
          "5000"
      );
    
    cb2 = new ComboBox<String>(quantity);
    cb2.setPrefSize(95,30);
    cb2.setStyle("-fx-background-color : #d9d9d9");
    cb2.getSelectionModel().selectFirst();
    
    
    
    
    l2 = new Label(" Quantity ", fontAwesome.create(FontAwesome.Glyph.GBP).color(Color.ORANGE));
    l2.setTextFill(Color.WHITE);
       
    HBox leftSection = new HBox();
    HBox rightSection = new HBox(l2,cb2,l1,cb1,b3,b2,b4,b1);

    /* Center all sections and always grow them. Has the effect known as JUSTIFY. */
    HBox.setHgrow( leftSection, Priority.ALWAYS );
    HBox.setHgrow( rightSection, Priority.ALWAYS );

    leftSection.setAlignment( Pos.CENTER_LEFT );
    rightSection.setAlignment( Pos.CENTER_RIGHT );

   /* It might be harder to propagate some properties: */
   final int spacing = 10;
   ToolBar toolBar = new ToolBar();

   toolBar.setPadding( new Insets( 4, spacing, 4, spacing ) );
   leftSection.setSpacing( spacing );
   rightSection.setSpacing( spacing );
   toolBar.getItems().addAll(leftSection, rightSection);
   
   toolBar.setStyle("-fx-background-color : #4d4d4d");
   
   this.getChildren().add(toolBar);
   
  }
  
  public void addLogOutHandler(EventHandler<ActionEvent> handler) {
    b1.setOnAction(handler);
  }
  
  public void addNewOrderHandler(EventHandler<ActionEvent> handler) {
    b2.setOnAction(handler);
  }
  
  public void addDeleteOrderHandler(EventHandler<ActionEvent> handler) {
    b3.setOnAction(handler);
  }
  
  public void addPriceAlert(EventHandler<ActionEvent> handler) {
    b4.setOnAction(handler);
  }
  
  
  public void addOpenChartHandler(EventHandler<ActionEvent> handler) {
    cb1.setOnAction(handler);
  }
  
  public ComboBox <String> getChartCombo()
  {
    return cb1;
  }

  public ComboBox <String> getQuantityCombo()
  {
    return cb2;
  }


}
