/*********************************************************************************
 * Форма добавления нового товара, категория товара выбирается с помощью ComBox   *
 * Дата вводится через DatePicker, остальные данные вводятся при помощи TextField *
 **********************************************************************************/
package sample.Forms.AddProductForm;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import sample.DBManipulate.DBConstant;
import sample.DBManipulate.DBHandler;
import sample.Main;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

public class AddProductFormController {

    @FXML
    private TextField nameField;

    @FXML
    private TextField priceField;

    @FXML
    private TextField noteField;

    @FXML
    private TextField quantityField;

    @FXML
    private Button saveButton;

    @FXML
    private Button returnButton;

    @FXML
    private ComboBox<String> typeBox;

    @FXML
    private DatePicker recDate;

    @FXML
    void initialize(){
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

        saveButton.setOnAction(actionEvent -> {
            String name = nameField.getText().trim();
            String price = priceField.getText().trim();
            String quantity = quantityField.getText().trim();
            String date = String.valueOf(recDate.getValue());
            String note = noteField.getText().trim();

            String id = ""; //Получение ID выбранного типа товара
            String select2 = "SELECT " + DBConstant.PRODUCT_TYPE_ID + " FROM " + DBConstant.PRODUCT_TYPE_TABLE +
                    " WHERE " + DBConstant.PRODUCT_TYPE_NAME + " = '" + typeBox.getValue() + "'";
            ResultSet resultSet2 = dbh.SelectSomething(select2);
            try{
                while (resultSet2.next()) {
                    id = resultSet2.getString(1);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            if (name.equals("")|| price.equals("") || quantity.equals("") || date.equals("null")) { //Проверка заполненности всех TextField'ов
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Ошибка!");
                alert.setHeaderText(null);
                alert.setContentText("Одно из обязательных полей незаполнено!");
                alert.showAndWait();
            } else {
                if (note.equals("")) //Проверка, чтобы в Note не отправлялась пустая строка
                    note = "null";
                else
                    note = "'" + note + "'";
                String insert = "INSERT INTO " + DBConstant.PRODUCT_TABLE + " (Name, Product_Type_ID, Price, Quantity, Receipt_Date, Note) VALUES ('"
                        + name + "', '" + id + "', '" + price + "', '" + quantity + "', '" + date + "', " + note + ")";
                dbh.SetRow(insert, "Продукт успешно добавлен!"); //Переход в метод добавления строки в БД
            }
        });






    }
}
