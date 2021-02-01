/************************************************************************
 * Форма директора, содержит все формы доступные сотрудникам              *
 * Присутствуюет информация о пользователе (по-сути просто украшательство)*
 ************************************************************************/
package sample.Forms.DirectForm;

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

public class DirectFormController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button addEmployeeButton;

    @FXML
    private Button addClientButton;

    @FXML
    private Button changeEmployeeButton;

    @FXML
    private Button changeClientButton;

    @FXML
    private Button addProductButton;

    @FXML
    private Button showProductButton;

    @FXML
    private Button addProductTypeButton;

    @FXML
    private Button showOrdersButton;

    @FXML
    private Button addRefrigeratorButton;

    @FXML
    private Button addRackButton;

    @FXML
    private Button addRackTypeButton;

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
    private Button showRefrigeratorsButton;

    @FXML
    private Button deleteButton;

    @FXML
    void initialize() {
        AddData(); //Получение данных о пользователе
        NamingLabels(); //Присваивание имен лейблам

        exitButton.setOnAction(event -> { //Выход из приложения
            Alert exit = new Alert(Alert.AlertType.CONFIRMATION);

            exit.setTitle("Выход");
            exit.setContentText("Вы действительно хотите выйти?");
            exit.setHeaderText(null);
            Optional<ButtonType> option = exit.showAndWait();
            if (option.get() == ButtonType.OK) {
                System.exit(0);
            }
        });

        //Куча лямбда-выражений открывающих нужную форму
        addEmployeeButton.setOnAction(event -> {
            addEmployeeButton.getScene().getWindow().hide();
            LoadForm("/sample/Forms/AddEmployeeForm/AddEmployeeFormStyle.fxml");
        });

        addClientButton.setOnAction(event -> {
            addClientButton.getScene().getWindow().hide();
            LoadForm("/sample/Forms/AddUserForm/AddUserFormStyle.fxml");
        });

        addRefrigeratorButton.setOnAction(event -> {
            addRefrigeratorButton.getScene().getWindow().hide();
            LoadForm("/sample/Forms/AddRefrigeratorForm/AddRefrigeratorFormStyle.fxml");
        });

        changeEmployeeButton.setOnAction(Event -> {
            changeEmployeeButton.getScene().getWindow().hide();
            LoadForm("/sample/Forms/ChangeEmployeeDataForm/ChangeEmployeeDataFormStyle.fxml");
        });

        changeClientButton.setOnAction(Event -> {
            changeClientButton.getScene().getWindow().hide();
            LoadForm("/sample/Forms/ChangeClientsDataForm/ChangeClientsDataFormStyle.fxml");
        });

        addProductButton.setOnAction(Event -> {
            addProductButton.getScene().getWindow().hide();
            LoadForm("/sample/Forms/AddProductForm/AddProductFormStyle.fxml");
        });

        addProductTypeButton.setOnAction(event ->{
            addProductTypeButton.getScene().getWindow().hide();
            LoadForm("/sample/Forms/AddProductTypeForm/AddProductTypeFormStyle.fxml");
        });

        addRackButton.setOnAction(event ->{
            addRackButton.getScene().getWindow().hide();
            LoadForm("/sample/Forms/AddRackForm/AddRackFormStyle.fxml");
        });

        logOutButton.setOnAction(event ->{
            logOutButton.getScene().getWindow().hide();
            LoadForm("/sample/Forms/LoginForm/LoginFormStyle.fxml");
        });

        showProductButton.setOnAction(actionEvent -> {
            showProductButton.getScene().getWindow().hide();
            LoadForm("/sample/Forms/ShowProductForm/ShowProductFormStyle.fxml");
        });

        showOrdersButton.setOnAction(actionEvent -> {
            showOrdersButton.getScene().getWindow().hide();
            LoadForm("/sample/Forms/ShowOrdersForm/ShowOrdersFormStyle.fxml");
        });

        addRackTypeButton.setOnAction(actionEvent -> {
            addRackTypeButton.getScene().getWindow().hide();
            LoadForm("/sample/Forms/AddRackTypeForm/AddRackTypeFormStyle.fxml");
        });

        showRefrigeratorsButton.setOnAction(actionEvent -> {
            showRefrigeratorsButton.getScene().getWindow().hide();
            LoadForm("/sample/Forms/ShowRefrigeratorsForm/ShowRefrigeratorsFormStyle.fxml");
        });

        deleteButton.setOnAction(actionEvent -> {
            deleteButton.getScene().getWindow().hide();
            LoadForm("/sample/Forms/DeleteForm/DeleteFormStyle.fxml");
        });
        //Конец куче лямбда-выражений
    }

    private void AddData(){ //Получение сведений о пользователе
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

    private void NamingLabels() { //Именование лейблов
        fioLabel.setText(Main.employee.getFio());
        phoneLabel.setText(Main.employee.getPhone());
        emailLabel.setText(Main.employee.getEmail());
        loginLabel.setText(Main.employee.getLogin());
    }

    private void LoadForm(String path){ //Единая для всего класса процедура открытия новой формы
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

