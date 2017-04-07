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
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class FXViewStatusBar extends VBox {
  
  private Label l1, l1a, l2, l2a, n;
  private Button b1;
  private GlyphFont fontAwesome = GlyphFontRegistry.font("FontAwesome");

  public FXViewStatusBar(){
    
    this.setStyle("-fx-background-color : #4d4d4d");
    this.setPadding(new Insets(0, 4, 0, 0));
    
    
    l1 = new Label("Equity: GBP ");
    l1.setFont(new Font("Arial", 12));
    l1.setTextFill(Color.LIGHTGREY);
    
    l1a = new Label("1000.89");
    l1a.setFont(new Font("Arial", 12));
    l1a.setTextFill(Color.WHITE);
       
    l2 = new Label("Live Result: GBP ");
    l2.setFont(new Font("Arial", 12));
    l2.setTextFill(Color.LIGHTGREY);
    
    l2a = new Label("0.00");
    l2a.setFont(new Font("Arial", 12));
    l2a.setTextFill(Color.WHITE);
    
    b1 = new Button(" Account", fontAwesome.create(FontAwesome.Glyph.COG).color(Color.SILVER));
    b1.setPrefSize(80,20);
    b1.setTextFill(Color.WHITE);
    b1.setStyle("-fx-background-color : #4d4d4d");    
    
    HBox leftSection = new HBox(b1);
    HBox rightSection = new HBox(l2,l2a,l1,l1a);

    /* Center all sections and always grow them. Has the effect known as JUSTIFY. */
    HBox.setHgrow( leftSection, Priority.ALWAYS );
    HBox.setHgrow( rightSection, Priority.ALWAYS );

    leftSection.setAlignment( Pos.CENTER_LEFT );
    rightSection.setAlignment( Pos.CENTER_RIGHT );

   /* It might be harder to propagate some properties: */
   final int spacing = 10;
   ToolBar toolBar = new ToolBar();

   toolBar.setPadding( new Insets( 0, spacing, 2, 0) );
   leftSection.setSpacing(0);
   rightSection.setSpacing( spacing );
   toolBar.getItems().addAll(leftSection, rightSection);
   
   toolBar.setStyle("-fx-background-color : #4d4d4d");
    
   this.getChildren().add(toolBar);
  }
  
  
  public Label getLabelLiveResult()
  {
    return l2a;
  }
  public void setLabelLiveResult(String text)
  {
    l2a.setText(text);
  }
  
  public Label getLabelEquity()
  {
    return l1a;
  }
  public void setLabelEquity (String text)
  {
    l1a.setText(text);
  }
  
  public void addSettingsHandler(EventHandler<ActionEvent> handler) {
    b1.setOnAction(handler);
  }
}
