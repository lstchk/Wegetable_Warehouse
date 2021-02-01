/*********************************************************************
 * Вывод на экран общего списка продуктов, выполяняется в TableView, *
 * заполняемой экземплярами Product                                  *
 *********************************************************************/
package sample.Forms.ShowProductForm;

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
import sample.Objects.Order;
import sample.Objects.Product;

import javax.xml.crypto.dsig.spec.XSLTTransformParameterSpec;

public class ShowProductFormController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableView<Product> table;

    @FXML
    private TableColumn<Product, String> nameCol;

    @FXML
    private TableColumn<Product, String> priceCol;

    @FXML
    private TableColumn<Product, String> quantityCol;

    @FXML
    private TableColumn<Product, String> recCol;

    @FXML
    private TableColumn<Product, String> noteCol;

    @FXML
    private Button returnButton;

    @FXML
    void initialize() {
        //Определение свойств столбцов таблицы
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        quantityCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        recCol.setCellValueFactory(new PropertyValueFactory<>("receiptDate"));
        noteCol.setCellValueFactory(new PropertyValueFactory<>("note"));

        //Получение данных из БД
        DBHandler dbh = new DBHandler();
        String name, price, quantity, rec, note;
        String select = "SELECT " + DBConstant.PRODUCT_NAME + ", " + DBConstant.PRODUCT_PRICE + ", "
                + DBConstant.PRODUCT_QUANTITY + ", " + DBConstant.PRODUCT_RECEIPT + ", " + DBConstant.PRODUCT_NOTE
                + " FROM " + DBConstant.PRODUCT_TABLE;
        ResultSet resultSet = dbh.SelectSomething(select);

        ObservableList<Product> products = FXCollections.observableArrayList(); //ObservableList заполненный экземплярами Order
        try {
            while(resultSet.next()) {
                name = resultSet.getString(DBConstant.PRODUCT_NAME);
                price = resultSet.getString(DBConstant.PRODUCT_PRICE);
                quantity = resultSet.getString(DBConstant.PRODUCT_QUANTITY);
                rec = resultSet.getString(DBConstant.PRODUCT_RECEIPT);
                note = resultSet.getString(DBConstant.PRODUCT_NOTE);
                Product product = new Product(name, price, quantity,  rec, note);
                products.add(product);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        table.setItems(products); //Заполнение table

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
