package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import model.Order;

public class FXViewOrderPane extends VBox {
  
  private ObservableList<Order> orderList;
  private TableView<Order> order;
  
  public FXViewOrderPane()
  {
    
    this.setStyle("-fx-background-color : #4d4d4d");
    this.setPadding(new Insets(5, 0, 5, 0));

    order = new TableView<Order>();
    order.setPlaceholder(new Label("No Orders have been placed."));

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
  
    TableColumn<Order,Double> prc = new TableColumn<Order,Double>("Purchase Price");
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
    
    TableColumn<Order,Double> res = new TableColumn<Order,Double>("Result");
    res.prefWidthProperty().bind(order.widthProperty().divide(8));
    res.setCellValueFactory(new PropertyValueFactory<Order, Double>("result"));
    res.setStyle("-fx-alignment: CENTER;");
  
    order.setItems(orderList);
    order.getColumns().addAll(cpr, qty, dir, prc, cup, tp, sl, res);
    
    this.setSpacing(5);   
    this.getChildren().addAll(order);
  }

  public TableView<Order> returnTableView()
 {
      return this.order;
 }
  
  
  public void setItemsTableView(ObservableList<Order> orders)
 {
      this.order.setItems(orders);
 }
  
  public ObservableList<Order> getOrderList()
  {
      return this.orderList;
  }
  
  public void addToOrderList(Order o)
  {
       this.orderList.add(o);
  }
  
  public void refreshTableView()
  {
    order.getColumns().get(0).setVisible(false);
    order.getColumns().get(0).setVisible(true);  
  }


}
