/***************************************************************
 * Вывод всех заказов в таблице. Таблица - это TableView       *
 * Ее заполнение осуществляется, через экземпляры класса Order *
 ***************************************************************/
package sample.Forms.ShowOrdersForm;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import sample.DBManipulate.DBConstant;
import sample.DBManipulate.DBHandler;
import sample.Main;
import sample.Objects.Order;

public class ShowOrdersFormController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableView<Order> table;


   @FXML
    private TableColumn<Order, ObservableValue<String>> clientCol;

    @FXML
    private TableColumn<Order, ObservableValue<String>> productCol;

    @FXML
    private TableColumn<Order, ObservableValue<String>> quantityCol;

    @FXML
    private TableColumn<Order, ObservableValue<String>> priceCol;

    @FXML
    private Button returnButton;

    @FXML
    void initialize() throws SQLException {
        //Свойства для столбцов
        clientCol.setCellValueFactory(new PropertyValueFactory<>("client"));
        productCol.setCellValueFactory(new PropertyValueFactory<>("product"));
        quantityCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        //Получение данных из БД.
        DBHandler dbh = new DBHandler();
        String select = "SELECT "  + DBConstant.ORDERS_CLIENT +", " + DBConstant.ORDERS_PRODUCT + ", " + DBConstant.ORDERS_QUANTITY +
                ", " + DBConstant.ORDERS_PRICE + " FROM " + DBConstant.ORDERS_TABLE;
        ResultSet resultSet = dbh.SelectSomething(select);

        //Вытаскивание данных из resultSet
        ObservableList<Order> orders = FXCollections.observableArrayList();
        String client, product, quantity, price;
        try {
            while (resultSet.next()) {
                client = resultSet.getString(DBConstant.ORDERS_CLIENT);
                product = resultSet.getString(DBConstant.ORDERS_PRODUCT);
                quantity = resultSet.getString(DBConstant.ORDERS_QUANTITY);
                price = resultSet.getString(DBConstant.ORDERS_PRICE);
                Order order = new Order(client, product, quantity, price);
                orders.add(order);
            }
            table.setItems(orders); //Заполнение таблицы
        }
        catch(Exception e){
            e.printStackTrace();
        }

        returnButton.setOnAction(actionEvent -> { //Кнопка возврата на главную форму
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Возврат");
            alert.setHeaderText(null);
            alert.setContentText("Вернуться назад?");
            Optional<ButtonType> option = alert.showAndWait();
            if (option.get() == ButtonType.OK) {
                String path ="";
                if(Main.employee.getForm() == 1)
                    path = "/sample/Forms/DirectForm/DirectFormStyle.fxml";
                else if(Main.employee.getForm() == 3)
                    path = "/sample/Forms/StockForm/StockFormStyle.fxml";
                else
                    path = "/sample/Forms/DriverForm/DriverFormStyle.fxml";
                returnButton.getScene().getWindow().hide(); //Скрытие текущей формы
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource(path)); //Открытие FMXL файла загружаемой формы
                try {
                    loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Parent root = loader.getRoot(); //Помещение формы в текущий поток и стейдж
                Stage stage = new Stage();
                stage.setTitle("Овощной склад");
                stage.setScene(new Scene(root));
                stage.showAndWait();
            }
        });
    }
}

