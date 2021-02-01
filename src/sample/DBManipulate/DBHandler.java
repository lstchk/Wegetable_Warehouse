/************************************************************
 * Единый класс с запросами к БД, все манипуляции с ней тут *
 ************************************************************/
package sample.DBManipulate;

import javafx.scene.control.Alert;
import sample.Forms.NewOrderForm.NewOrderFormController;
import sample.Main;
import sample.Objects.Employee;
import sample.Objects.User;

import java.sql.*;


public class DBHandler {
    //Строка подключения. Ответ на любые вопросы о ней: "Понятия не имею"
    public static String URL = "jdbc:mysql://localhost:3306/vegetablewarehouse?useUnicode=true&serverTimezone=UTC&useSSL=true&verifyServerCertificate=false",
            user = "root",
            password = "12345";
    public static Connection dbConnection;

    //Стандартная процедура подключения
    public Connection getDbConnection() throws ClassNotFoundException, SQLException{
        Class.forName("com.mysql.cj.jdbc.Driver");
        dbConnection = DriverManager.getConnection(URL, user, password);

        return dbConnection;
    }

    //Получение входных данных для пользователя, так проще, потому что если искать в самой БД, можно наткнуться на очень
    // неприятные исключения, потому учитывая небольшие размеры БД, лучше считать все. Справедливо и для GetEmployee()
    public ResultSet GetUser(User user) { //(LoginForm)
        ResultSet resultSet = null;
        String select = "SELECT * FROM " + DBConstant.USER_TABLE + " WHERE " + DBConstant.USER_EMAIL
                + "=? AND " + DBConstant.USER_PASSWORD + "=?"; //Строка для получения всех связкок почта-пароль

        try{
            PreparedStatement pst = getDbConnection().prepareStatement(select);
            pst.setString(1, user.getEmail());
            pst.setString(2, user.getPassword());
            resultSet = pst.executeQuery();
        }catch (SQLException e) {
            e.printStackTrace();
            DBNotFound();
        }catch (ClassNotFoundException e){
            e.printStackTrace();
            DBNotFound();
        }

    return resultSet;
    }

    //Аналогично предыдущему, но для сотрудника (LoginForm)
    public ResultSet GetEmployee(Employee employee){
        ResultSet resultSet = null;
        String select = "SELECT * FROM " + DBConstant.EMPLOYEE_TABLE + " WHERE " + DBConstant.EMPLOYEE_LOGIN
                + "=? AND " + DBConstant.EMPLOYEE_PASSWORD + "=?"; //Строка для получения всех связкок логин-пароль

        try{
            PreparedStatement pst = getDbConnection().prepareStatement(select);
            pst.setString(1, employee.getLogin());
            pst.setString(2, employee.getPassword());
            resultSet = pst.executeQuery();
        }catch (SQLException e) {
            e.printStackTrace();
            DBNotFound();
        }catch (ClassNotFoundException e){
            e.printStackTrace();
            DBNotFound();
        }

        return resultSet;
    }

    //Получение кода формы для сотрудника (LoginForm)
    public String GetForm(String login){
        ResultSet resultSet = null;
        ResultSet resultSet1 = null;
        String select = "SELECT * FROM " + DBConstant.EMPLOYEE_TABLE + " WHERE " + DBConstant.EMPLOYEE_LOGIN + " = "
                + " '" + login + "'"; //Строка для получения полной информации о сотруднике
        String pos_id = "";
        try{
            PreparedStatement pst = getDbConnection().prepareStatement(select);
            resultSet = pst.executeQuery();
            resultSet.next();
            while (true){
                pos_id = resultSet.getString(DBConstant.EMPLOYEE_POSITION); //Получам код его должности
                break;
            }
        }catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            DBNotFound();
        }

        String select1 = "SELECT * FROM " + DBConstant.POSITION_TABLE + " WHERE " + DBConstant.POSITION_ID + " = " + " '" + pos_id + "'";
        String form = ""; //Теперь в таблице position получаем код его формы
        try{
            PreparedStatement pst1 = getDbConnection().prepareStatement(select1);
            resultSet1 = pst1.executeQuery();
            resultSet1.next();
            while (true) {
                 form += resultSet1.getString(DBConstant.POSITION_FORM); //Собственно, сам код формы
                Main.employee.setForm(Integer.parseInt(form));
                 break;
            }
        }catch (SQLException e) {
            e.printStackTrace();
            DBNotFound();
        }catch (ClassNotFoundException e){
            e.printStackTrace();
            DBNotFound();
        }

        return form;
    }

    public ResultSet NamingUserLabel(String email){ //Получение данных для лейблов на форме клиента (UserForm)
        ResultSet resultSet = null;

        String select = "SELECT * FROM " + DBConstant.USER_TABLE + " WHERE " + DBConstant.USER_EMAIL + " = '" + email + "'";

        try {
            PreparedStatement pst = getDbConnection().prepareStatement(select);
            resultSet = pst.executeQuery();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return resultSet;
    }



    public ResultSet NamingEmployeeLabel(String login){ //Получение данных для лейблов на формах сотрудников ( Все формы сотрудников)
        ResultSet resultSet = null;

        String select = "SELECT * FROM " + DBConstant.EMPLOYEE_TABLE + " WHERE " + DBConstant.EMPLOYEE_LOGIN + " = '" + login + "'";

        try {
            PreparedStatement pst = getDbConnection().prepareStatement(select);
            resultSet = pst.executeQuery();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return resultSet;
    }

    public ResultSet SelectSomething(String select) { //Получение списка категорий товаров (NewOrderForm, AddProductForm, AddRackForm)
        ResultSet resultSet = null;
        try {
            PreparedStatement pst = getDbConnection().prepareStatement(select);
            resultSet = pst.executeQuery();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return resultSet;
    }

    public ResultSet GetProduct(String type) { //Получения списка продектов в заданной категории (NewOrderForm, AddRackForm)
        ResultSet resultSet = null;
        String select = "SELECT " + DBConstant.PRODUCT_TYPE_ID + " FROM " + DBConstant.PRODUCT_TYPE_TABLE +
                " WHERE " + DBConstant.PRODUCT_TYPE_NAME + " = '" + type + "'"; //Строка запроса для получения ID нужного типа товара

        try {
            PreparedStatement pst = getDbConnection().prepareStatement(select);
            resultSet = pst.executeQuery();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        String id = "";
        try {
            while (resultSet.next()) {
                id = resultSet.getString(DBConstant.PRODUCT_TYPE_ID);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        resultSet = null;
        String select2 = "SELECT " + DBConstant.PRODUCT_NAME + " FROM " + DBConstant.PRODUCT_TABLE +
                " WHERE " + DBConstant.PRODUCT_TYPE_ID  + " = '" + id + "'"; //Строка запроса для получения списка товаров по аданному ID
        try {
            PreparedStatement pst = getDbConnection().prepareStatement(select2);
            resultSet = pst.executeQuery();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return resultSet;
    }

    //Добавление строки в таблицу.
    public void SetRow(String insert, String message) {
        PreparedStatement pst = null;
        try {
            pst = getDbConnection().prepareStatement(insert);
            pst.execute();
            Alert alert = new Alert(Alert.AlertType.INFORMATION); //Сообщение о том, что платеж добавлен
            alert.setHeaderText(null);
            alert.setTitle("Успешно!");
            alert.setContentText(message);
            alert.showAndWait();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR); //Сообщение об ошибке, если что-то пошло не так.
            alert.setTitle("Ошибка!");
            alert.setContentText("Непредвиденная ошибка! Обратитесь к администратору.");
            alert.setHeaderText(null);
            alert.showAndWait();
        }
    }

    public void UpdateRow(String update){
        PreparedStatement pst;
        try {
            pst = getDbConnection().prepareStatement(update);
            pst.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void ChangeSomething(String input, String message) { //Изменение EMail пользователем (ChangeUserDataForm)

        try {
            PreparedStatement pst = getDbConnection().prepareStatement(input);
            pst.executeUpdate();
            Alert alert = new Alert(Alert.AlertType.INFORMATION); //Сообщение о том, что платеж добавлен
            alert.setHeaderText(null);
            alert.setTitle("Успешно!");
            alert.setContentText("message");
            alert.showAndWait();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR); //Сообщение об ошибке, если что-то пошло не так.
            alert.setTitle("Ошибка!");
            alert.setContentText("Непредвиденная ошибка! Обратитесь к администратору.");
            alert.setHeaderText(null);
            alert.showAndWait();
        }
    }

    public void DeleteRow(String delete) {
        PreparedStatement pst = null;
        try {
            pst = getDbConnection().prepareStatement(delete);
            pst.execute();
            Alert alert = new Alert(Alert.AlertType.INFORMATION); //Сообщение о том, что платеж добавлен
            alert.setHeaderText(null);
            alert.setTitle("Успешно!");
            alert.setContentText("Успешно удалено!");
            alert.showAndWait();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR); //Сообщение об ошибке, если что-то пошло не так.
            alert.setTitle("Ошибка!");
            alert.setContentText("Непредвиденная ошибка! Обратитесь к администратору.");
            alert.setHeaderText(null);
            alert.showAndWait();
        }
    }

    private void DBNotFound(){ //Сообщение об ошибке подключения к БД (LoginForm)
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка базы данных");
        alert.setHeaderText(null);
        alert.setContentText("Ошибка подключения к базе данных! " +
                "и/или данные для входа");
        alert.showAndWait();
    }

}
