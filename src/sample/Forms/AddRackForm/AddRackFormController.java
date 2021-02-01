/****************************************************************************************
 * Добавляение нового стелажа в Базу данных                                             *
 * Тип вид стелажей, типа продуктов и сам продукт выбираются через ComboBox             *
 * Примечание вводится через TextBox                                                    *
 ****************************************************************************************/
package sample.Forms.AddRackForm;

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

public class AddRackFormController {

    @FXML
    private Button saveButton;

    @FXML
    private Button returnButton;

    @FXML
    private ComboBox<String> typeRackBox;

    @FXML
    private ComboBox<String> typeProductBox;

    @FXML
    private ComboBox<String> productBox;

    @FXML
    private TextField noteField;

    @FXML
    private Button searchButton;
    @FXML
    void initialize(){
        //Скрытие ненужных на начало работы элементов
        productBox.setVisible(false);
        saveButton.setVisible(false);
        noteField.setVisible(false);

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

        typeProductBox.setItems(productType);
        typeProductBox.getSelectionModel().select(0);

        //Получение списка типов стелажей
        String select2 = "SELECT " + DBConstant.RACK_TYPE_ID + " FROM " + DBConstant.RACK_TYPE_TABLE;
        ResultSet resultSet3 = dbh.SelectSomething(select2);
        ArrayList<String> tempList3 = new ArrayList<>();
        try{
            while (resultSet3.next()) {
                String current = resultSet3.getString(1);
                tempList3.add(current);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        ObservableList<String> rackType = FXCollections.observableArrayList(tempList3);

        typeRackBox.setItems(rackType);
        typeRackBox.getSelectionModel().select(0);

        searchButton.setOnAction(actionEvent -> {
            productBox.setVisible(true);
            noteField.setVisible(true);
            saveButton.setVisible(true);

            //Получение общего списка продуктов
            ResultSet resultSet2 = dbh.GetProduct(typeProductBox.getValue());
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

        });

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
            String note = noteField.getText().trim();
            if (note.equals("")) //Проверка, чтобы в Note не отправлялась пустая строка
                note = "null";
            else
                note = "'" + note + "'";

            String productTypeID = ""; //Получение ID выбранного типа товара
            String select3 = "SELECT " + DBConstant.PRODUCT_TYPE_ID + " FROM " + DBConstant.PRODUCT_TYPE_TABLE +
                    " WHERE " + DBConstant.PRODUCT_TYPE_NAME + " = '" + typeProductBox.getValue() + "'";
            ResultSet resultSet2 = dbh.SelectSomething(select3);
            try{
                while (resultSet2.next()) {
                    productTypeID = resultSet2.getString(1);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            String productID = ""; //Получение ID выбранного типа товара
            String select4 = "SELECT " + DBConstant.PRODUCT_ID + " FROM " + DBConstant.PRODUCT_TABLE +
                    " WHERE " + DBConstant.PRODUCT_NAME + " = '" + productBox.getValue() + "'";
            ResultSet resultSet4 = dbh.SelectSomething(select4);
            try{
                while (resultSet4.next()) {
                    productID = resultSet4.getString(1);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            String rackTypeID = typeRackBox.getValue();


            String insert  = "INSERT INTO " + DBConstant.RACK_TABLE + " (Product_Type_ID, Product_ID, Rack_Type_ID, Note) VALUES ('"
                    + productTypeID + "', '" + productID + "', '" + rackTypeID + "', " + note + ")";
            dbh.SetRow(insert, "Стелаж умпешно добавлен!");
        });
    }

}
