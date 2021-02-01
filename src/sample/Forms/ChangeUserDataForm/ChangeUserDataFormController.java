/*************************************************************************************************
 * Форма для изменения клиентом данных о себе, позволяет изменить почту, пароль и номер телефона.*
 *************************************************************************************************/

package sample.Forms.ChangeUserDataForm;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import sample.DBManipulate.DBConstant;
import sample.DBManipulate.DBHandler;
import sample.Forms.UserForm.UserFormController;
import sample.Main;

public class ChangeUserDataFormController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label emailLabel;

    @FXML
    private Label phoneLabel;

    @FXML
    private Button changeEmailButton;

    @FXML
    private Button changePhoneButton;

    @FXML
    private Button changePasswordButton;

    @FXML
    private Label fioLabel;

    @FXML
    private Button returnButton;

    @FXML
    void initialize() {
        AddData(); //Присваивание значений лейблам
        DBHandler dbh = new DBHandler();

        changeEmailButton.setOnAction(actionEvent -> { //Изменение E-Mail
            TextInputDialog input = new TextInputDialog();
            input.setTitle("Изменение E-Mail");
            input.setHeaderText(null);
            input.setContentText("Новый E-Mail");
            Optional<String> result = input.showAndWait();
            result.ifPresent(name -> {
                Main.user.setEmail(name);
            });
            String input2 = "UPDATE " + DBConstant.USER_TABLE + " SET " + DBConstant.USER_EMAIL + " = '" + Main.user.getEmail()
                    + "' WHERE " + DBConstant.USER_ID + " = " + Main.user.getId();
            dbh.ChangeSomething(input2, "Почта изменена");
        });

        changePasswordButton.setOnAction(actionEvent -> { //Изменение пароля
            TextInputDialog input = new TextInputDialog();
            input.setTitle("Изменение пароля");
            input.setHeaderText(null);
            input.setContentText("Новый пароль");
            Optional<String> result = input.showAndWait();
            result.ifPresent(name -> {
                Main.user.setPassword(name);
            });
            String input3 = "UPDATE " + DBConstant.USER_TABLE + " SET " + DBConstant.USER_PASSWORD + " = " + Main.user.getPassword()
                    + " WHERE " + DBConstant.USER_ID + " = " + Main.user.getId();
            dbh.ChangeSomething(input3, "Пароль изменен");
        });

        changePhoneButton.setOnAction(actionEvent -> { //Изменение телефона
            TextInputDialog input = new TextInputDialog();
            input.setTitle("Изменение контактного телефона");
            input.setHeaderText(null);
            input.setContentText("Новый контактный телефон");
            Optional<String> result = input.showAndWait();
            result.ifPresent(name -> {
                Main.user.setPhone(name);
            });
            String input4 = "UPDATE " + DBConstant.USER_TABLE + " SET " + DBConstant.USER_PHONE + " = " + Main.user.getPhone()
                    + " WHERE " + DBConstant.USER_ID + " = " + Main.user.getId();
            dbh.ChangeSomething(input4, "Телефон изменен");
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

    private void AddData() //Присваивание значений лейблам
    {
        fioLabel.setText(Main.user.getFio());
        phoneLabel.setText(Main.user.getPhone());
        emailLabel.setText(Main.user.getEmail());
    }
}