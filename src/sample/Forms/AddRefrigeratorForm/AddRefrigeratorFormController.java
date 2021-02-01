/***************************************
 * Добавление нового рефрежиратора     *
 * Водитель выбирается через CombоBox  *
 * Остальное вводится через TextField  *
 ***************************************/
package sample.Forms.AddRefrigeratorForm;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import com.mysql.cj.x.protobuf.MysqlxDatatypes;
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

public class AddRefrigeratorFormController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField brandField;

    @FXML
    private TextField vinField;

    @FXML
    private TextField regField;

    @FXML
    private TextField noteField;

    @FXML
    private Button saveButton;

    @FXML
    private Button returnButton;

    @FXML
    private ComboBox<String> employeeBox;

    @FXML
    private DatePicker toDate;

    @FXML
    void initialize() {

        //Получение списка водителей
        DBHandler dbh = new DBHandler();
        String select = "SELECT " + DBConstant.EMPLOYEE_FIO + " FROM " + DBConstant.EMPLOYEE_TABLE + " WHERE " +
                DBConstant.EMPLOYEE_POSITION + " = '4'";
        ;
        ResultSet resultSet = dbh.SelectSomething(select);
        String current;
        ArrayList<String> tempList = new ArrayList<>();
        try {
            while (resultSet.next()) {
                current = resultSet.getString(1);
                tempList.add(current); //Заполнение списка списком должностей
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }

        ObservableList<String> employee = FXCollections.observableArrayList(tempList); //Перенос списка в ObservableList
        employeeBox.setItems(employee); // Заполнение ComboBox значениями из ObservableList
        employeeBox.getSelectionModel().select(0);

        saveButton.setOnAction(actionEvent -> {
            String brand = brandField.getText().trim();
            String vin = vinField.getText().trim();
            String  reg = regField.getText().trim();
            String date = String.valueOf(toDate.getValue());
            String note = noteField.getText().trim();

            //Проверка заполненности всех TextField'ов
            if (brand.equals("")|| vin.equals("") || reg.equals("")) {
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
                if(date.equals("null"))
                    date = "null";
                else
                    date = "'" + date + "'";
                String select2 = "SELECT " + DBConstant.EMPLOYEE_ID + " FROM " + DBConstant.EMPLOYEE_TABLE + " WHERE " +
                        DBConstant.EMPLOYEE_FIO + " = '" + employeeBox.getValue() + "'";
                ResultSet resultSet2 = dbh.SelectSomething(select2);
                String id = "";
                try {
                    while (resultSet2.next())
                        id = resultSet2.getString("Employee_ID");
                } catch (SQLException e) {
                        e.printStackTrace();
                }
                String insert = "INSERT INTO " + DBConstant.REFRIGERATOR_TABLE + " (Brand, VIN, Registration, Employee_ID, Last_TO_Date, " +
                                "Note) VALUES ('" + brand + "', '" + vin + "', '"
                        + reg + "', '" + id + "', " + date + ", " + note + ")";
                dbh.SetRow(insert, "Рефрижератор усрешно добавлен!"); //Переход в метод добавления строки в БД
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
                Parent root = loader.getRoot(); //Помещение формы в текущий поток и стейдж
                Stage stage = new Stage();
                stage.setTitle("Овощной склад");
                stage.setScene(new Scene(root));
                stage.showAndWait();
            }
        });
    }
}
