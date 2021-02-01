/***********************************************************************************************************************
 * Удаление объекта из  любого из имущества, выбирается сначала тип удаляемого, потом раздел при помощи двух ComboBox  *
 ***********************************************************************************************************************/
package sample.Forms.DeleteForm;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import sample.DBManipulate.DBConstant;
import sample.DBManipulate.DBHandler;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;

public class DeleteFormController {

    @FXML
    private Button returnButton;

    @FXML
    private ComboBox<String> typeBox;

    @FXML
    private ComboBox<String> objectBox;

    @FXML
    private Button deleteButton;

    @FXML
    private Button typeButton;

    @FXML
    void initialize(){
        ObservableList<String> type =  FXCollections.observableArrayList("Товары", "Категории товаров", "Рефрижераторы",
                "Стеллажи", "Категории стелажей"); //Создание списка вариантов объектов удаления
        typeBox.setItems(type);
        typeBox.getSelectionModel().select(0);

        typeButton.setOnAction(actionEvent -> {
            String qType = typeBox.getValue();
            String select = "";
            switch (qType){ //Выбор варианта и создание запроса на получение объектов
                case "Товары":
                   select = "SELECT " + DBConstant.PRODUCT_NAME + " FROM " + DBConstant.PRODUCT_TABLE;
                   break;
                case "Категории товаров":
                    select = "SELECT " + DBConstant.PRODUCT_TYPE_NAME + " FROM " + DBConstant.PRODUCT_TYPE_TABLE;
                    break;
                case "Рефрижераторы":
                    select = "SELECT " + DBConstant.REFRIGERATOR_BRAND + " FROM " + DBConstant.REFRIGERATOR_TABLE;
                    break;
                case "Стелажи":
                    select = "SELECT " + DBConstant.RACK_ID + " FROM " + DBConstant.RACK_TABLE;
                    break;
                case "Категории стелажей":
                    select = "SELECT " + DBConstant.RACK_TYPE_ID + " FROM " + DBConstant.RACK_TABLE;
                    break;
            }
            //Вытягивание списка объектов из БД
            DBHandler dbh = new DBHandler();
            ResultSet resultSet = dbh.SelectSomething(select);
            String current;
            ArrayList<String> tempList = new ArrayList<>();
            try {
                while (resultSet.next()) {
                    current = resultSet.getString(1);
                    tempList.add(current);
                }
            }catch (SQLException e) {
                e.printStackTrace();
            }

            ObservableList<String> objects = FXCollections.observableArrayList(tempList); //Перенос списка в ObservableList
            objectBox.setItems(objects); // Заполнение ComboBox значениями из ObservableList
            objectBox.getSelectionModel().select(0);
        });

        deleteButton.setOnAction(actionEvent -> {
            String qType = typeBox.getValue();
            String delete = "";
            switch (qType) { //Создание ссылки на удаление
                case "Товары":
                    delete = "DELETE FROM " + DBConstant.PRODUCT_TABLE + " WHERE " + DBConstant.PRODUCT_NAME + " = '" + objectBox.getValue() + "'";
                    break;
                case "Категории товаров":
                    delete = "DELETE FROM " + DBConstant.PRODUCT_TYPE_TABLE + " WHERE " + DBConstant.PRODUCT_TYPE_NAME + " = '" + objectBox.getValue() + "'";
                    break;
                case "Рефрижераторы":
                    delete = "DELETE FROM " + DBConstant.REFRIGERATOR_TABLE + " WHERE " + DBConstant.REFRIGERATOR_BRAND + " = '" + objectBox.getValue() + "'";
                    break;
                case "Стелажи":
                    delete = "DELETE FROM " + DBConstant.RACK_TABLE + " WHERE " + DBConstant.RACK_ID + " = '" + objectBox.getValue() + "'";
                    break;
                case "Категории стелажей":
                    delete = "DELETE FROM " + DBConstant.RACK_TYPE_TABLE + " WHERE " + DBConstant.RACK_TYPE_ID + " = '" + objectBox.getValue() + "'";
                    break;
            }
            DBHandler dbh = new DBHandler();
            dbh.DeleteRow(delete); //Удаление
        });

        returnButton.setOnAction(actionEvent -> { //Кнопка возврата на главную форму
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Возврат");
            alert.setHeaderText(null);
            alert.setContentText("Вернуться назад?");
            Optional<ButtonType> option = alert.showAndWait();
            if (option.get() == ButtonType.OK) {
                returnButton.getScene().getWindow().hide(); //Скрытие текущей формы
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/sample/Forms/DirectForm/DirectFormStyle.fxml")); //Открытие FMXL файла загружаемой формы
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
