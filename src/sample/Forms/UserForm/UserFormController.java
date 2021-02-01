/**************************************************************************************
 *Форма клиента, структурно является клоном DirectForm, но имеет свои кнопки и лейблы *
 **************************************************************************************/
package sample.Forms.UserForm;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import sample.DBManipulate.*;
import sample.Main;

public class UserFormController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button shopButton;

    @FXML
    private Button logOutButton;

    @FXML
    private Button orderButton;

    @FXML
    private Button changeButton;

    @FXML
    private Button exitButton;

    @FXML
    private Label nameLabel;

    @FXML
    private Label emailLabel;

    @FXML
    private Label phoneLabel;

    @FXML
    void initialize() {
        addData();
        namingLabels();

        exitButton.setOnAction(event -> { //Кнпочка выхода
            Alert exit = new Alert(Alert.AlertType.CONFIRMATION);

            exit.setTitle("Выход");
            exit.setContentText("Вы действительно хотите выйти?");
            exit.setHeaderText(null);
            Optional<ButtonType> option = exit.showAndWait();
            if (option.get() == ButtonType.OK) {
                System.exit(0);
            }
        });

        //Куча лямбда-выражений обрабатывающих нажатие на кнопки
        logOutButton.setOnAction(event -> {
            logOutButton.getScene().getWindow().hide();
            LoadForm("/sample/Forms/LoginForm/LoginFormStyle.fxml");
        });

        orderButton.setOnAction(event -> {
            orderButton.getScene().getWindow().hide();
            LoadForm("/sample/Forms/UserAllOrderForm/UserAllOrderFormStyle.fxml");
        });

        changeButton.setOnAction(event -> {
            changeButton.getScene().getWindow().hide();
            LoadForm("/sample/Forms/ChangeUserDataForm/ChangeUserDataFormStyle.fxml");
        });

        shopButton.setOnAction(actionEvent -> {
            shopButton.getScene().getWindow().hide();
            LoadForm("/sample/Forms/NewOrderForm/NewOrderFormStyle.fxml");
        });
    }

    private void addData(){ //Получение данных о пользователе
        DBHandler dbh = new DBHandler();
        String email = Main.user.getEmail();
        String fio = "", phone = "", id = "";
        ResultSet resultSet = dbh.NamingUserLabel(email);
        try{
            resultSet.next();
            fio = resultSet.getString(DBConstant.USER_FIO);
            phone = resultSet.getString(DBConstant.USER_PHONE);
            id = resultSet.getString(DBConstant.USER_ID);
        } catch (SQLException e) {
                e.printStackTrace();
        }
        Main.user.setFio(fio);
        Main.user.setPhone(phone);
        Main.user.setId(id);
    }

    private void namingLabels() { //Именование лейблов
       nameLabel.setText(Main.user.getFio());
       phoneLabel.setText(Main.user.getPhone());
       emailLabel.setText(Main.user.getEmail());
    }

    //Единая для всего класса процедура загрузки форм
    private void LoadForm(String path) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(path)); //Открытие FMXL файла загружаемой формы
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
}
