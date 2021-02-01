/*********************************************************************************
 * Форма создания нового заказа.                                                 *
 * От пользователя требуется выбрать тип товара, вид товара и ввести количество, *
 * после чего выведется стоимость                                                *
 *********************************************************************************/
package sample.Forms.NewOrderForm;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import javafx.collections.*;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import sample.DBManipulate.DBConstant;
import sample.DBManipulate.DBHandler;
import sample.Main;

public class NewOrderFormController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button returnButton;

    @FXML
    private Label productLabel;

    @FXML
    private Button orderButton;

    @FXML
    private Label priceLabel;

    @FXML
    private ComboBox<String> typeBox;

    @FXML
    private ComboBox<String> productBox;

    @FXML
    private TextField qText;

    @FXML
    private Label qLabel;

    @FXML
    private Button qButton;

    @FXML
    private Button okButton1;

    @FXML
    private Button okButton2;

    @FXML
    private Label quantityLabel;

    @FXML
    private Label priceCurLabel;

    @FXML
    private Text priceText;

    @FXML
    private Text quantityText;

    @FXML
    void initialize() {
        //Ненужные на начало работы формы элементы скрыты
        priceLabel.setVisible(false);
        productBox.setVisible(false);
        productLabel.setVisible(false);
        orderButton.setVisible(false);
        qText.setVisible(false);
        qButton.setVisible(false);
        qLabel.setVisible(false);
        okButton2.setVisible(false);
        quantityLabel.setVisible(false);
        priceCurLabel.setVisible(false);
        priceText.setVisible(false);
        quantityText.setVisible(false);


        DBHandler dbh = new DBHandler(); //Получение списка типов продуктов
        String select = "SELECT " + DBConstant.PRODUCT_TYPE_NAME + " FROM " + DBConstant.PRODUCT_TYPE_TABLE;
        ResultSet resultSet = dbh.SelectSomething(select);
        ArrayList<String> tempList = new ArrayList<>();
        try{
            while (resultSet.next()) {
                String current = resultSet.getString("Name");
                tempList.add(current);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ObservableList<String> productType = FXCollections.observableArrayList(tempList);

        typeBox.setItems(productType);
        typeBox.getSelectionModel().select(0);



        okButton1.setOnAction(actionEvent -> { //Получение списка продуктов заданного типа
            productBox.setVisible(true); //Видимость необходимых элементов
            productLabel.setVisible(true);
            okButton2.setVisible(true);

            ResultSet resultSet2 = dbh.GetProduct(typeBox.getValue());
            ArrayList<String> tempList2 = new ArrayList<>();
            try{
                while (resultSet2.next()) {
                    String current = resultSet2.getString("Name");
                    tempList2.add(current);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            ObservableList<String> product = FXCollections.observableArrayList(tempList2);

            productBox.setItems(product);
            productBox.getSelectionModel().select(0);
            String prod = typeBox.getValue();
        });

        okButton2.setOnAction(actionEvent -> { //Работа с выбранным продуктом
            qLabel.setVisible(true);
            qButton.setVisible(true);
            qText.setVisible(true);
            quantityLabel.setVisible(true);
            priceCurLabel.setVisible(true);
            priceText.setVisible(true);
            quantityText.setVisible(true);
            priceLabel.setVisible(true);

            String select2 = "SELECT " + DBConstant.PRODUCT_PRICE + " FROM " + DBConstant.PRODUCT_TABLE +
                    " WHERE " + DBConstant.PRODUCT_TYPE_NAME + " = '" + productBox.getValue() + "'"; //Строка запроса для получения цены по имени
            ResultSet resultSet3 = dbh.SelectSomething(select2);
            String select3 = "SELECT " + DBConstant.PRODUCT_QUANTITY + " FROM " + DBConstant.PRODUCT_TABLE +
                    " WHERE " + DBConstant.PRODUCT_TYPE_NAME + " = '" + productBox.getValue() + "'"; //Строка запроса для получения количества нужного товара по имени
            ResultSet resultSet4 = dbh.SelectSomething(select3);

            try{ //Получаем цену единицы продукта
                while (resultSet3.next()) {
                    priceCurLabel.setText(resultSet3.getString(1));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try{ //Получаем количество продукта
                while (resultSet4.next()) {
                    quantityLabel.setText(resultSet4.getString(1));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
    });

        qButton.setOnAction(actionEvent -> {
            int quantity = Integer.parseInt(quantityLabel.getText());
            int price = Integer.parseInt(priceCurLabel.getText());
            int uQuantity = 0;
            try {
                uQuantity = Integer.parseInt(qText.getText().trim());
                if(uQuantity > 0 && uQuantity <= quantity) { //Проверка действительности значений введеных пользователем
                    Main.order.setQuantity(String.valueOf(uQuantity));
                    Main.order.setPrice(String.valueOf(uQuantity * price)); //Если изменить количество товара, то это никак не повлияет на текущие значения в системе
                    priceLabel.setText(String.valueOf(Main.order.getPrice())); //Финальная стоимость товара
                    orderButton.setVisible(true);
                }
                else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText(null);
                    alert.setContentText("Введите действительное количество товара!");
                    alert.setTitle("Ошибка!");
                    alert.showAndWait();
                }
            }catch (NumberFormatException e){
               e.printStackTrace();
               Alert alert = new Alert(Alert.AlertType.ERROR);
               alert.setTitle("Ошибка!");
               alert.setHeaderText(null);
               alert.setContentText("Введите корректное значение!");
               alert.showAndWait();
            }
        });

        orderButton.setOnAction(actionEvent -> { //Изменения в таблицах
                String price = Main.order.getPrice();
                String quantity = Main.order.getQuantity();
                String product = productBox.getValue();
            String insert = "INSERT INTO " + DBConstant.ORDERS_TABLE + "(Price, Client, Product_Name, Quantity) VALUES " +
                    " ('" + price + "', '" + Main.user.getFio() + "', '" + product + "', '" + quantity + "')";
                dbh.SetRow(insert, "Спасибо за покупку!");
            String update = "UPDATE " + DBConstant.PRODUCT_TABLE + " SET " + DBConstant.PRODUCT_QUANTITY + " = "
                    + DBConstant.PRODUCT_QUANTITY + " - " + quantity + " WHERE " + DBConstant.PRODUCT_NAME + " = " +
                    " '" + product + "' ";
            dbh.UpdateRow(update);
        });

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

