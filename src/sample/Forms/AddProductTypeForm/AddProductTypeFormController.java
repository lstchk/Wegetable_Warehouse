/*************************************************************************************
 * Добавление новой категории товаров, ввод осуществляется с помощью текстовых полей.*
 *************************************************************************************/
package sample.Forms.AddProductTypeForm;

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

import java.io.IOException;
import java.util.Optional;

public class AddProductTypeFormController {

    @FXML
    private TextField nameField;

    @FXML
    private TextField tempField;

    @FXML
    private TextField shelfField;

    @FXML
    private TextField humidityField;

    @FXML
    private TextField noteField;

    @FXML
    private Button saveButton;

    @FXML
    private Button returnButton;

    @FXML
    void initialize(){
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
            String name = nameField.getText().trim();
            String temperature = tempField.getText().trim();
            String shelf = shelfField.getText().trim();
            String humidity = humidityField.getText().trim();
            String note = noteField.getText().trim();

            if (name.equals("")|| temperature.equals("") || shelf.equals("") || humidity.equals("")) { //Проверка заполненности всех TextField'ов
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Ошибка!");
                alert.setHeaderText(null);
                alert.setContentText("Одно из обязательных полей незаполнено!");
                alert.showAndWait();
            } else {
                //Проверка, чтобы tinyint не переполнялась
                if(Integer.parseInt(temperature) < -128 || Integer.parseInt(temperature) > 127
                        || Integer.parseInt(shelf) < -128 || Integer.parseInt(shelf) > 127
                        || Integer.parseInt(humidity) < -128 || Integer.parseInt(humidity) > 127){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Ошибка!");
                    alert.setHeaderText(null);
                    alert.setContentText("Одно или несколько полей содержат недопустимые значения!");
                    alert.showAndWait();
                }
                else {
                    if (note.equals("")) //Проверка, чтобы в Note не отправлялась пустая строка
                        note = "null";
                    else
                        note = "'" + note + "'";
                    String insert = "INSERT INTO " + DBConstant.PRODUCT_TYPE_TABLE + " (Name, Temperature_Mode, Shelf_Life, Humidity, Note) VALUES ('"
                            + name + "', '" + temperature + "', '"
                            + shelf + "', '" + humidity + "', " +
                            note + ")";
                    DBHandler dbh = new DBHandler();
                    dbh.SetRow(insert, "Успешно добавленно!"); //Переход в метод добавления строки в БД
                }
            }
        });
    }
}
