/***********************************************************************************************
 * Форма входа, является стартовой                                                             *
 * Считывает логин и пароль, после чего определяет таблицу                                     *
 * Для сотрудников - логин и пароль, проверка по данным таблицы employee                        *
 * Для клиентов - почта и пароль, проверка из данных таблицы users                             *
 * Для админа - данные student, student                                                        *
 * Для сотрудников перераспределяет на три формы, в зависимости от указанной в таблице position*
 ***********************************************************************************************/
package sample.Forms.LoginForm;

import java.io.IOException;
import java.util.Optional;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.DBManipulate.*;
import sample.Main;

import java.sql.*;

import static sample.Main.employee;

public class LoginFormController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField loginField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private RadioButton employeeRad;

    @FXML
    private ToggleGroup is;

    @FXML
    private RadioButton userRad;

    @FXML
    private Button loginButton;

    @FXML
    private Button helpBut;

    @FXML
    private Button exitButton;

    @FXML
    void initialize() {
        //Табличка с информацией о проекте
        helpBut.setOnAction(event -> {
            Alert helpForm = new Alert(Alert.AlertType.INFORMATION);
            helpForm.setTitle("О программе");
            helpForm.setHeaderText(null);
            helpForm.setContentText("Овощной склад\n" +
                    "Разработчик: Севостьянов Дмитрий Юрьевич ИС-181");
            helpForm.showAndWait();
        });

        //Запуск процедуры авторизации
        loginButton.setOnAction(event -> {
            String login = loginField.getText().trim();
            String password = passwordField.getText().trim();
            if (login.equals("student") && password.equals("student")) { //Возможность войти в приложении без базы данных
                LoadForm("/sample/Forms/AdminForm/AdminFormStyle.fxml");
            } else
                Authorisation(login, password);
        });

        //Выход из программы
        exitButton.setOnAction(event -> {
            Alert exit = new Alert(Alert.AlertType.CONFIRMATION);

            exit.setTitle("Выход");
            exit.setContentText("Вы действительно хотите выйти?");
            exit.setHeaderText(null);
            Optional<ButtonType> option = exit.showAndWait();
            if (option.get() == ButtonType.OK) {
                System.exit(0);
            }
        });
    }



    //Процедура авторизации
    private void Authorisation(String login, String password) {
        if (userRad.isSelected())
            UserAuthorisation(login, password);
        else
            EmployeeAuthorisation(login, password);
    }

    private void UserAuthorisation(String login, String password) { //Авторизация для пользователей
        Main.user.setEmail(login);
        Main.user.setPassword(password);
        DBHandler dbh = new DBHandler();

        ResultSet resultSetlst = dbh.GetUser(Main.user);
        int count = 0;  //Если counter больше нуля, то такой пользователь существует
        while (true) {
            try {
                if (!resultSetlst.next()) break;
            } catch (SQLException e) {
                e.printStackTrace();
            }
            count++;
        }
        if (count == 0) { //Если равен нулю, то такого пользователя нет и выводится сообщение об этом
            Alert failedLogin = new Alert(Alert.AlertType.ERROR);
            failedLogin.setTitle("Ошибка входа");
            failedLogin.setHeaderText(null);
            failedLogin.setContentText("Неправильный логин и/или пароль!");
            failedLogin.showAndWait();
        } else {
            LoadForm("/sample/Forms/UserForm/UserForm.fxml");
        }
    }

    private void EmployeeAuthorisation(String login, String password){ //Авторизация для сотрудников
        employee.setLogin(login);
        employee.setPassword(password);
        DBHandler dbh = new DBHandler();

        ResultSet resultSet =  dbh.GetEmployee(employee);
        int count = 0;  //Если counter больше нуля, то такой пользователь существует
        while (true) {
            try {
                if (!resultSet.next()) break;
            } catch (SQLException e) {
                e.printStackTrace();
            }
            count++;
        }
        if (count == 0) { //Если равен нулю, то такого пользователя нет и выводится сообщение об этом
            Alert failedLogin = new Alert(Alert.AlertType.ERROR);
            failedLogin.setTitle("Ошибка входа");
            failedLogin.setHeaderText(null);
            failedLogin.setContentText("Неправильный логин и/или пароль!");
            failedLogin.showAndWait();
        } else
            SelectFormFromEmployee(dbh, login);
    }

    private void SelectFormFromEmployee(DBHandler dbh, String login) { //Определение необходимой формы для загрузки
        int form = Integer.parseInt(dbh.GetForm(login));
        switch (form){
            case 1: LoadForm("/sample/Forms/DirectForm/DirectFormStyle.fxml");
                break;
            case 3: LoadForm("/sample/Forms/StockForm/StockFormStyle.fxml");
                break;
            case 4: LoadForm("/sample/Forms/DriverForm/DriverFormStyle.fxml");
                break;
        }
    }

    //Общая для всего класса процедура загрузки форм
    private void LoadForm(String path){
        //Процедура загрузки формы
        loginButton.getScene().getWindow().hide(); //Скрытие текущей формы
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
        //Конец процедуры загрузки формы
    }

}
