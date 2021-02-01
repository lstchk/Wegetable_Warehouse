/**************************************************************************************************
 * Форма для изменения сведений о сотрудниках, изменить все кроме должности нужно через TextField *
 * Должность и сотрудник выбираются через ComboBox                                                *
 * Можно удалить сотрудника специальной кнопкой                                                   *
 **************************************************************************************************/
package sample.Forms.ChangeEmployeeDataForm;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

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

public class ChangeEmployeeDataFormController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField fioField;

    @FXML
    private TextField loginField;

    @FXML
    private TextField passwordField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField addressField;

    @FXML
    private TextField phoneField;

    @FXML
    private TextField orderField;

    @FXML
    private TextField passportField;

    @FXML
    private TextField noteField;

    @FXML
    private ComboBox<String> positionBox;

    @FXML
    private Button saveButton;

    @FXML
    private Button returnButton;

    @FXML
    private ComboBox<String> employeeBox1;

    @FXML
    private Button deleteButton;

    @FXML
    void initialize() {
        String select = "SELECT " + DBConstant.EMPLOYEE_FIO + " FROM " + DBConstant.EMPLOYEE_TABLE;
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

        String select2 = "SELECT " + DBConstant.POSITION_NAME + " FROM " + DBConstant.POSITION_TABLE;
        ResultSet resultSet2 = dbh.SelectSomething(select2);

        ArrayList<String> tempList2 = new ArrayList<>();
        try {
            while (resultSet2.next()) {
                current = resultSet2.getString(1);
                tempList2.add(current);
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }

        ObservableList<String> employee = FXCollections.observableArrayList(tempList); //Перенос списка в ObservableList
        employeeBox1.setItems(employee); // Заполнение ComboBox значениями из ObservableList
        employeeBox1.getSelectionModel().select(0);

        ObservableList<String> position = FXCollections.observableArrayList(tempList2); //Перенос списка в ObservableList
        positionBox.setItems(position); // Заполнение ComboBox значениями из ObservableList
        positionBox.getSelectionModel().select(0);

        saveButton.setOnAction(actionEvent -> {

            if(!fioField.getText().trim().equals("")) {
                String input = "UPDATE " + DBConstant.EMPLOYEE_TABLE + " SET " + DBConstant.EMPLOYEE_FIO + " = '" + fioField.getText().trim()
                        + "' WHERE " + DBConstant.EMPLOYEE_FIO + " = '" + employeeBox1.getValue() + "'";
                dbh.ChangeSomething(input, "ФИО изменено");
            }

            if(!fioField.getText().trim().equals("")) {
                String input = "UPDATE " + DBConstant.EMPLOYEE_TABLE + " SET " + DBConstant.EMPLOYEE_LOGIN + " = '" + loginField.getText().trim()
                        + "' WHERE " + DBConstant.EMPLOYEE_FIO + " = '" + employeeBox1.getValue() + "'";
                dbh.ChangeSomething(input, "Логин изменен");
            }

            if(!passwordField.getText().trim().equals("")) {
                String input = "UPDATE " + DBConstant.EMPLOYEE_TABLE + " SET " + DBConstant.EMPLOYEE_PASSWORD + " = '" + passwordField.getText().trim()
                        + "' WHERE " + DBConstant.EMPLOYEE_FIO + " = '" + employeeBox1.getValue() + "'";
                dbh.ChangeSomething(input, "Пароль изменен");
            }

            if(!emailField.getText().trim().equals("")) {
                String input = "UPDATE " + DBConstant.EMPLOYEE_TABLE + " SET " + DBConstant.EMPLOYEE_EMAIL + " = '" + emailField.getText().trim()
                        + "' WHERE " + DBConstant.EMPLOYEE_FIO + " = '" + employeeBox1.getValue() + "'";
                dbh.ChangeSomething(input, "Почта изменена");
            }

            if(!addressField.getText().trim().equals("")) {
                String input = "UPDATE " + DBConstant.EMPLOYEE_TABLE + " SET " + DBConstant.EMPLOYEE_ADDRESS + " = '" + addressField.getText().trim()
                        + "' WHERE " + DBConstant.EMPLOYEE_FIO + " = '" + employeeBox1.getValue() + "'";
                dbh.ChangeSomething(input, "Адрес изменен");
            }

            if(!phoneField.getText().trim().equals("")) {
                String input = "UPDATE " + DBConstant.EMPLOYEE_TABLE + " SET " + DBConstant.EMPLOYEE_PHONE + " = '" + phoneField.getText().trim()
                        + "' WHERE " + DBConstant.EMPLOYEE_FIO + " = '" + employeeBox1.getValue() + "'";
                dbh.ChangeSomething(input, "Телефон изменен");
            }

            if(!orderField.getText().trim().equals("")) {
                String input = "UPDATE " + DBConstant.EMPLOYEE_TABLE + " SET " + DBConstant.EMPLOYEE_CONTRACT + " = '" + orderField.getText().trim()
                        + "' WHERE " + DBConstant.EMPLOYEE_FIO + " = '" + employeeBox1.getValue() + "'";
                dbh.ChangeSomething(input, "Номер контракта изменен");
            }

            if(!passportField.getText().trim().equals("")) {
                String input = "UPDATE " + DBConstant.EMPLOYEE_TABLE + " SET " + DBConstant.EMPLOYEE_PASSPORT + " = '" + passportField.getText().trim()
                        + "' WHERE " + DBConstant.EMPLOYEE_FIO + " = '" + employeeBox1.getValue() + "'";
                dbh.ChangeSomething(input, "Номер паспорта изменен");
            }

            if(!noteField.getText().trim().equals("")) {
                String input = "UPDATE " + DBConstant.EMPLOYEE_TABLE + " SET " + DBConstant.EMPLOYEE_NOTE + " = '" + noteField.getText().trim()
                        + "' WHERE " + DBConstant.EMPLOYEE_FIO + " = '" + employeeBox1.getValue() + "'";
                dbh.ChangeSomething(input, "Примечание изменено");

            }
        });

        positionBox.setOnAction(actionEvent -> {
            String newPosition = positionBox.getValue();
            String select3 = "SELECT " + DBConstant.POSITION_ID + " FROM " + DBConstant.POSITION_TABLE + "WHERE" + DBConstant.POSITION_NAME +
                    " = '" + newPosition + "'";
            String positionID = "";
            ResultSet resultSet3 = dbh.SelectSomething(select);
            try {
                while (resultSet3.next()) {
                    positionID = resultSet3.getString(1);
                }
            }catch (SQLException e) {
                e.printStackTrace();
            }

            String input = "UPDATE " + DBConstant.EMPLOYEE_TABLE + " SET " + DBConstant.EMPLOYEE_POSITION + " = '" + positionID
                    + "' WHERE " + DBConstant.EMPLOYEE_FIO + " = '" + employeeBox1.getValue() + "'";
            dbh.ChangeSomething(input, "Должность изменена");
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
            String delete = "DELETE FROM " + DBConstant.EMPLOYEE_TABLE + " WHERE " + DBConstant.EMPLOYEE_FIO + " = '" +
                    employeeBox1.getValue() +"'";

            dbh.DeleteRow(delete);
        });
    }
}

