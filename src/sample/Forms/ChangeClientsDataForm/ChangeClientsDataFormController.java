/**************************************************************************************************
 * Форма для изменения сведений о клиентах, изменить все изменяется через TextField               *
 * Клиент выбирается через ComboBox                                                               *
 * Можно удалить клиента специальной кнопкой                                                      *
 **************************************************************************************************/
package sample.Forms.ChangeClientsDataForm;

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

public class ChangeClientsDataFormController {

    @FXML
    private TextField fioField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField passwordField;

    @FXML
    private TextField noteField;

    @FXML
    private TextField phoneField;

    @FXML
    private Button saveButton;

    @FXML
    private Button returnButton;

    @FXML
    private ComboBox<String > clientBox;

    @FXML
    private Button deleteButton;

    @FXML
    void initialize(){
        String select = "SELECT " + DBConstant.USER_FIO + " FROM " + DBConstant.USER_TABLE;
        DBHandler dbh = new DBHandler();
        ResultSet resultSet = dbh.SelectSomething(select);

        String current;
        ArrayList<String> tempList = new ArrayList<>();
        try {
            while (resultSet.next()) {
                current = resultSet.getString(1);
                tempList.add(current); //Заполнение списка списком должностей
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }

        ObservableList<String> clients = FXCollections.observableArrayList(tempList); //Перенос списка в ObservableList
        clientBox.setItems(clients); // Заполнение ComboBox значениями из ObservableList
        clientBox.getSelectionModel().select(0);

        saveButton.setOnAction(actionEvent -> {
            if(!fioField.getText().trim().equals("")) {
                String input = "UPDATE " + DBConstant.USER_TABLE + " SET " + DBConstant.USER_FIO + " = '" + fioField.getText().trim()
                        + "' WHERE " + DBConstant.USER_FIO + " = '" + clientBox.getValue() + "'";
                dbh.ChangeSomething(input, "ФИО изменено!");
            }

            if(!emailField.getText().trim().equals("")) {
                String input = "UPDATE " + DBConstant.USER_TABLE + " SET " + DBConstant.USER_EMAIL + " = '" + emailField.getText().trim()
                        + "' WHERE " + DBConstant.USER_FIO + " = '" + clientBox.getValue() + "'";
                dbh.ChangeSomething(input, "Почта изменена");
            }

            if(!passwordField.getText().trim().equals("")) {
                String input = "UPDATE " + DBConstant.USER_TABLE + " SET " + DBConstant.USER_PASSWORD + " = '" + passwordField.getText().trim()
                        + "' WHERE " + DBConstant.USER_FIO + " = '" + clientBox.getValue() + "'";
                dbh.ChangeSomething(input, "Пароль изменен");
            }

            if(!phoneField.getText().trim().equals("")) {
                String input = "UPDATE " + DBConstant.USER_TABLE + " SET " + DBConstant.USER_PHONE + " = '" + phoneField.getText().trim()
                        + "' WHERE " + DBConstant.USER_FIO + " = '" + clientBox.getValue() + "'";
                dbh.ChangeSomething(input, "Телефон изменен");
            }

            if(!noteField.getText().trim().equals("")) {
                String input = "UPDATE " + DBConstant.USER_TABLE + " SET " + DBConstant.USER_NOTE + " = '" + noteField.getText().trim()
                        + "' WHERE " + DBConstant.USER_FIO + " = '" + clientBox.getValue() + "'";
                dbh.ChangeSomething(input, "Примечание изменено");
            }

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

        deleteButton.setOnAction(actionEvent -> {
            String delete = "DELETE FROM " + DBConstant.USER_TABLE + " WHERE " + DBConstant.USER_FIO + " = '" +
                    clientBox.getValue() +"'";

            dbh.DeleteRow(delete);
        });
    }

}
