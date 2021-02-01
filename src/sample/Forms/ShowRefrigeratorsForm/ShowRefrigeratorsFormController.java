/*********************************************************************
 * Вывод на экран общего списка продуктов, выполяняется в TableView, *
 * заполняемой экземплярами Refrigerator                                  *
 *********************************************************************/
package sample.Forms.ShowRefrigeratorsForm;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import sample.DBManipulate.DBConstant;
import sample.DBManipulate.DBHandler;
import sample.Main;
import sample.Objects.Product;
import sample.Objects.Refrigerator;

public class ShowRefrigeratorsFormController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableView<Refrigerator> table;

    @FXML
    private TableColumn<Refrigerator, String> brandCol;

    @FXML
    private TableColumn<Refrigerator, String> vinCol;

    @FXML
    private TableColumn<Refrigerator, String> regCol;

    @FXML
    private TableColumn<Refrigerator, String> toCol;

    @FXML
    private TableColumn<Refrigerator, String> noteCol;

    @FXML
    private Button returnButton;

    @FXML
    void initialize() {
        //Определение свойств столбцов таблицы
        brandCol.setCellValueFactory(new PropertyValueFactory<>("brand"));
        vinCol.setCellValueFactory(new PropertyValueFactory<>("vin"));
        regCol.setCellValueFactory(new PropertyValueFactory<>("registration"));
        toCol.setCellValueFactory(new PropertyValueFactory<>("to"));
        noteCol.setCellValueFactory(new PropertyValueFactory<>("note"));

        String select = "SELECT * FROM " + DBConstant.REFRIGERATOR_TABLE;
        DBHandler dbHandler = new DBHandler();
        ResultSet resultSet = dbHandler.SelectSomething(select);
        ObservableList<Refrigerator> refrigerators = FXCollections.observableArrayList(); //ObservableList заполненный экземплярами Order
        String brand, vin, registration, to, note;
        try {
            while(resultSet.next()) {
                brand = resultSet.getString(DBConstant.REFRIGERATOR_BRAND);
                vin = resultSet.getString(DBConstant.REFRIGERATOR_VIN);
                registration = resultSet.getString(DBConstant.REFRIGERATOR_REGISTRATION);
                to = resultSet.getString(DBConstant.REFRIGERATOR_TO);
                note = resultSet.getString(DBConstant.REFRIGERATOR_NOTE);
                Refrigerator refrigerator = new Refrigerator(brand, vin, registration,  to, note);
                refrigerators.add(refrigerator);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        table.setItems(refrigerators); //Заполнение table

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
