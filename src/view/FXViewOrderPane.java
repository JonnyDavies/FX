package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import model.Order;

public class FXViewOrderPane extends VBox {
  
  public FXViewOrderPane()
  {
    
    this.setStyle("-fx-background-color : grey");
    this.setPadding(new Insets(5, 0, 5, 0));

    TableView<Order> order = new TableView<Order>();
    ObservableList<Order> orderList = 
        FXCollections.observableArrayList(            
          new Order("GBP/EUR", 10000, "Buy", 0.0, 0.0, 0.0, 0.0, 234),
          new Order("USD/JPY", 50000, "Buy", 1.34560, 1.34564, 1.34567, 1.34569, 234)  
        );
    
    TableColumn<Order,String> cpr = new TableColumn<Order,String>("Currency Pair");
    cpr.prefWidthProperty().bind(order.widthProperty().divide(8));
    cpr.setCellValueFactory(new PropertyValueFactory<Order, String>("currencyPair"));
    cpr.setStyle("-fx-alignment: CENTER");
    
    TableColumn<Order,Integer> qty = new TableColumn<Order,Integer>("Quantity");
    qty.prefWidthProperty().bind(order.widthProperty().divide(8));
    qty.setCellValueFactory(new PropertyValueFactory<Order, Integer>("quantity"));
    qty.setStyle("-fx-alignment: CENTER");
    
    TableColumn<Order,String> dir = new TableColumn<Order,String>("Direction");
    dir.prefWidthProperty().bind(order.widthProperty().divide(8));
    dir.setCellValueFactory(new PropertyValueFactory<Order, String>("direction"));
    dir.setStyle("-fx-alignment: CENTER");
    
    TableColumn<Order,Double> prc = new TableColumn<Order,Double>("Price");
    prc.prefWidthProperty().bind(order.widthProperty().divide(8));
    prc.setCellValueFactory(new PropertyValueFactory<Order, Double>("price"));
    prc.setStyle("-fx-alignment: CENTER");
    
    TableColumn<Order,Double> cup = new TableColumn<Order,Double>("Current Price");
    cup.prefWidthProperty().bind(order.widthProperty().divide(8));
    cup.setCellValueFactory(new PropertyValueFactory<Order, Double>("currentPrice"));
    cup.setStyle("-fx-alignment: CENTER");
    
    TableColumn<Order,Double> tp  = new TableColumn<Order,Double>("Take Profit");
    tp.prefWidthProperty().bind(order.widthProperty().divide(8));
    tp.setCellValueFactory(new PropertyValueFactory<Order, Double>("takeProfit"));
    tp.setStyle("-fx-alignment: CENTER");
    
    TableColumn<Order,Double> sl  = new TableColumn<Order,Double>("Stop Loss");
    sl.prefWidthProperty().bind(order.widthProperty().divide(8));
    sl.setCellValueFactory(new PropertyValueFactory<Order, Double>("stopLoss"));
    sl.setStyle("-fx-alignment: CENTER");
    
    TableColumn<Order,Integer> res = new TableColumn<Order,Integer>("Result");
    res.prefWidthProperty().bind(order.widthProperty().divide(8));
    res.setCellValueFactory(new PropertyValueFactory<Order, Integer>("result"));
    res.setStyle("-fx-alignment: CENTER");
    
    order.setItems(orderList);
    order.getColumns().addAll(cpr, qty, dir, prc, cup, tp, sl, res);
    
    this.setSpacing(5);   
    this.getChildren().addAll(order);
  }

}