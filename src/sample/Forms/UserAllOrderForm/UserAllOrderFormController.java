/*********************************************************************
 * Вывод списка покупок у пользователя, выполнившего вход            *
 * Для вывода используется TableView заполняемая данными экземпляров *
 * класса Order.                                                     *
 *********************************************************************/
package sample.Forms.UserAllOrderForm;

import javafx.beans.value.ObservableStringValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import sample.DBManipulate.*;
import sample.Main;
import sample.Objects.Order;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

public class UserAllOrderFormController {


    @FXML
    private TableView<Order> table;

    @FXML
    private TableColumn<Order, String> productCol;

    @FXML
    private TableColumn<Order, String> quantityCol;

    @FXML
    private TableColumn<Order, String> priceCol;


    @FXML
    private Button returnButton;

    @FXML
    void initialize(){
        //Определение свойств столбцов таблицы
        productCol.setCellValueFactory(new PropertyValueFactory<>("product"));
        quantityCol.setCellValueFactory((new PropertyValueFactory<>("quantity")));
        priceCol.setCellValueFactory(new  PropertyValueFactory<>("price"));

        //Получение данных из БД
        DBHandler dbh = new DBHandler();
        String product, price, quantity;
        String select = "SELECT " + DBConstant.ORDERS_PRODUCT + ", " + DBConstant.ORDERS_QUANTITY + ", "
                + DBConstant.ORDERS_PRICE + " FROM " + DBConstant.ORDERS_TABLE + " WHERE " + DBConstant.ORDERS_CLIENT
                + " = '" + Main.user.getFio() + "'";
        ResultSet resultSet = dbh.SelectSomething(select);

        //Заполнение ObservableList для table
        ObservableList<Order> orderData = FXCollections.observableArrayList(); //ObservableList заполненный экземплярами Order
        try {
            while(resultSet.next()) {
                product = resultSet.getString(DBConstant.ORDERS_PRODUCT);
                price = resultSet.getString(DBConstant.ORDERS_PRICE);
                quantity = resultSet.getString(DBConstant.ORDERS_QUANTITY);
                Order order = new Order(product, quantity, price);
                orderData.add(order);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        table.setItems(orderData); //Заполнение table

        returnButton.setOnAction(event -> { //Возврат на предыдущую форму
            Alert exit = new Alert(Alert.AlertType.CONFIRMATION);
            exit.setTitle("Возврат");
            exit.setContentText("Вы действительно хотите вернутся?");
            exit.setHeaderText(null);
            Optional<ButtonType> option = exit.showAndWait();
            if (option.get() == ButtonType.OK) {
                returnButton.getScene().getWindow().hide(); //Скрытие текущей формы
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/sample/Forms/UserForm/UserForm.fxml")); //Открытие FMXL файла загружаемой формы
                try {
                    loader.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Parent root = loader.getRoot(); //Помещение формы в текущий поток и стейдж
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.setTitle("Овощной склад");
                stage.showAndWait();
            }
        });
    }
}