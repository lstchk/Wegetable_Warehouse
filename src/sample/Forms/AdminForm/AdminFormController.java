/*******************************************************************
 * Форма не требующая наличия базы данных                          *
 * Лучше ей не рпользоваться, не зная, как работает программа.     *
 * Но ее можно использовать для востановления пароля директора или *
 * Других критических ситуаций                                     *
 *******************************************************************/
package sample.Forms.AdminForm;

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
import javafx.stage.Stage;

public class AdminFormController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button userButton;

    @FXML
    private Button logOutButton;

    @FXML
    private Button directButton;

    @FXML
    private Button driverButton;

    @FXML
    private Button exitButton;

    @FXML
    private Button stockButton;

    @FXML
    void initialize() {

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

        //Куча лямбда-выражений обрабатывающтх нажатия на кнопки для открытия форм
        userButton.setOnAction(actionEvent -> {
            userButton.getScene().getWindow().hide();
            LoadForm("/sample/Forms/UserForm/UserForm.fxml");
        });

        directButton.setOnAction(actionEvent -> {
            directButton.getScene().getWindow().hide();
            LoadForm("/sample/Forms/DirectForm/DirectFormStyle.fxml");
        });

        driverButton.setOnAction(actionEvent -> {
            driverButton.getScene().getWindow().hide();
            LoadForm("/sample/Forms/DriverForm/DriverFormStyle.fxml");
        });

        stockButton.setOnAction(actionEvent -> {
            stockButton.getScene().getWindow().hide();
            LoadForm("/sample/Forms/StockForm/StockFormStyle.fxml");
        });

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

