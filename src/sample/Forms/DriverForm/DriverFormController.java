/*********************************************************
 * Форма водителя, является урезанной версией DirectForm *
 *********************************************************/
package sample.Forms.DriverForm;

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
import sample.DBManipulate.DBConstant;
import sample.DBManipulate.DBHandler;
import sample.Main;

public class DriverFormController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button showOrderButton;

    @FXML
    private Button showRefrigeratorsButton;

    @FXML
    private Button logOutButton;

    @FXML
    private Label fioLabel;

    @FXML
    private Label phoneLabel;

    @FXML
    private Label emailLabel;

    @FXML
    private Label loginLabel;

    @FXML
    private Button exitButton;

    @FXML
    void initialize() {
        addData();
        namingLabels();

        exitButton.setOnAction(event -> {  //Кнопочка выхода
            Alert exit = new Alert(Alert.AlertType.CONFIRMATION);

            exit.setTitle("Выход");
            exit.setContentText("Вы действительно хотите выйти?");
            exit.setHeaderText(null);
            Optional<ButtonType> option = exit.showAndWait();
            if (option.get() == ButtonType.OK) {
                System.exit(0);
            }
        });

        //Куча лямбда-выражений обрабатывающих нажатия на кнопки
        showOrderButton.setOnAction(actionEvent -> {
            showOrderButton.getScene().getWindow().hide();
            LoadForm("/sample/Forms/ShowOrdersForm/ShowOrdersFormStyle.fxml");
        });

        showRefrigeratorsButton.setOnAction(actionEvent -> {
            showRefrigeratorsButton.getScene().getWindow().hide();
            LoadForm("/sample/Forms/ShowRefrigeratorsForm/ShowRefrigeratorsFormStyle.fxml");
        });

        logOutButton.setOnAction(event ->{
            logOutButton.getScene().getWindow().hide();
            LoadForm("/sample/Forms/LoginForm/LoginFormStyle.fxml");
        });
        //Конец кучи
    }

    private void addData(){ //Получение данных о пользователе
        DBHandler dbh = new DBHandler();
        String login = Main.employee.getLogin();
        String fio = "", phone = "", email = "";
        ResultSet resultSet = dbh.NamingEmployeeLabel(login);
        try{
            resultSet.next();
            fio = resultSet.getString(DBConstant.EMPLOYEE_FIO);
            phone = resultSet.getString(DBConstant.EMPLOYEE_PHONE);
            email = resultSet.getString(DBConstant.EMPLOYEE_EMAIL);

        } catch (SQLException e) {
            e.printStackTrace();
        }
        Main.employee.setFio(fio);
        Main.employee.setPhone(phone);
        Main.employee.setEmail(email);
        Main.employee.setLogin(login);
    }

    private void namingLabels() { //Именование лейблов
        fioLabel.setText(Main.employee.getFio());
        phoneLabel.setText(Main.employee.getPhone());
        emailLabel.setText(Main.employee.getEmail());
        loginLabel.setText(Main.employee.getLogin());
    }

    //Единая для всего класса процедура открытия форм
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
