package sample;

import javafx.application.Application;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import sample.Objects.Employee;
import sample.Objects.Order;
import sample.Objects.Product;
import sample.Objects.User;

import java.io.IOException;
import java.util.Optional;


public class Main extends Application {
    //Не очень изящно, но решает многие проблемы
    public static User user = new User();
    public static Employee employee = new Employee();
    public static Product product = new Product();
    public static Order order = new Order();

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("/sample/Forms/LoginForm/LoginFormStyle.fxml")); //Форма для входа
        primaryStage.setTitle("Овощной склад");
        primaryStage.setScene(new Scene(root, 800, 600)); //Данная размерность сохраняется для всей программы
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}