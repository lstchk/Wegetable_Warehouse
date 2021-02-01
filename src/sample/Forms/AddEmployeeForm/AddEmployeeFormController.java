/*******************************************************
 * Контроллер формы добавляющей нового сотрудника в БД  *
 * С TextField'ов вводятся ФИО, логин, пароль и т.д.    *
 * Должность выбирается из заполненного из БД ComboBox'a*
 *******************************************************/
package sample.Forms.AddEmployeeForm;

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

public class AddEmployeeFormController {

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
    void initialize() {

        DBHandler dbh = new DBHandler();
        String select = "SELECT " + DBConstant.POSITION_NAME + " FROM " + DBConstant.POSITION_TABLE;
        ResultSet resultSet = dbh.SelectSomething(select); //Получение списка должностей

        String current = null;
        ArrayList<String> tempList = new ArrayList<>();
        try {
            while (resultSet.next()) {

                current = resultSet.getString(DBConstant.POSITION_NAME);
                tempList.add(current); //Заполнение списка списком должностей
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }

        ObservableList<String> position = FXCollections.observableArrayList(tempList); //Перенос списка в ObservableList
        positionBox.setItems(position); // Заполнение ComboBox значениями из ObservableList
        positionBox.getSelectionModel().select(0);

        saveButton.setOnAction(actionEvent -> { //Формирование строки для  добавления нового пользователя
            String select2 = "SELECT " + DBConstant.POSITION_ID + " FROM " + DBConstant.POSITION_TABLE + " WHERE "
                    +  DBConstant.POSITION_NAME + " = "  + "'" + positionBox.getValue() + "'";
            ResultSet rss = dbh.SelectSomething(select2); //Получение ID должности
            String positionID = "";
            try {
                while (rss.next()) {
                    positionID = rss.getString(DBConstant.POSITION_ID);
                }
            }catch (SQLException e) {
                e.printStackTrace();
            }

            String fio = fioField.getText().trim();
            String login = loginField.getText().trim();
            String password = passwordField.getText().trim();
            String email = emailField.getText().trim();
            String phone = phoneField.getText().trim();
            String order = orderField.getText().trim();
            String passport = passportField.getText().trim();
            String address = addressField.getText().trim();
            String note = noteField.getText().trim();

            if (fio.equals("")|| login.equals("") || passport.equals("") || password.equals("") || email.equals("") || phone.equals("")
                    || order.equals("") || address.equals("")) { //Проверка заполненности всех TextField'ов
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
                String insert = "INSERT INTO " + DBConstant.EMPLOYEE_TABLE + " (FIO, Login, Password, EMail, Phone, " +
                        "Contract_ID, Passport, Adress, Note, Position_ID) VALUES ('" + fio + "', '" + login + "', '"
                        + password + "', '" + email + "', '" + phone + "', '" + order + "', '" + passport + "', '" + address + "', " +
                        note + ", '" + positionID + "')";
                dbh.SetRow(insert, "Сотрудник успешно добавлен!"); //Переход в метод добавления строки в БД
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
                Parent root = loader.getRoot();
                Stage stage = new Stage();
                stage.setTitle("Овощной склад");
                stage.setScene(new Scene(root));
                stage.showAndWait();
            }
        });
    }
}

