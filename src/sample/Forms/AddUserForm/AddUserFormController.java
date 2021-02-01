/****************************************************************************
 * Добавление нового клиента, ввод осуществляется с помощью текстовых полей.*
 ****************************************************************************/
package sample.Forms.AddUserForm;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.DBManipulate.DBConstant;
import sample.DBManipulate.DBHandler;
import sample.Main;
import sample.Objects.User;

public class AddUserFormController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

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
    void initialize() {

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
            sample.Objects.User newUser = new User(); //Запись данных из формы
            newUser.setFio(fioField.getText().trim());
            newUser.setEmail(emailField.getText().trim());
            newUser.setPassword(passwordField.getText().trim());
            newUser.setPhone(phoneField.getText().trim());
            newUser.setNote(noteField.getText().trim());

            if(newUser.getFio().equals("") || newUser.getEmail().equals("") || newUser.getPassword().equals("") ||
            newUser.getPhone().equals("")){ //Проверка на заполненность всех обязательных полей
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Ошибка!");
                alert.setHeaderText(null);
                alert.setContentText("Одно из обязательных полей незаполнено!");
                alert.showAndWait();
            }
            else {
                if (newUser.getNote().equals("")) //Проверка, чтобы в Note не отправлялась пустая строка
                    newUser.setNote("null");
                else
                    newUser.setNote("'" + newUser.getNote() + "'");
                String insert = "INSERT INTO " + DBConstant.USER_TABLE + " (FIO, Password, EMAIL, Phone, Note)  VALUES ('"
                        + newUser.getFio() + "', '" + newUser.getPassword() + "', '"
                        + newUser.getEmail() + "', '" + newUser.getPhone() + "', " +
                        newUser.getNote() + ")";
                DBHandler dbh = new DBHandler();
                dbh.SetRow(insert, "Клиент успешно добавлен!");
            }
        });
    }
}

