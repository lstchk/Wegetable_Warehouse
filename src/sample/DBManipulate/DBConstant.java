/************************************************************
 * Класс констант наименований таблиц и строк в базе данных *
 ************************************************************/
package sample.DBManipulate;

public class DBConstant
{
    public static final String USER_TABLE = "users";
    public static final String USER_ID = "User_ID";
    public static final String USER_FIO = "FIO";
    public static final String USER_PASSWORD = "Password";
    public static final String USER_EMAIL = "EMail";
    public static final String USER_PHONE  = "Phone";
    public static final String USER_NOTE = "Note";

    public static final String ORDERS_TABLE = "orders";
    public static final String ORDERS_ID = "Order_ID";
    public static final String ORDERS_PRICE = "Price";
    public static final String ORDERS_CLIENT = "Client";
    public static final String ORDERS_PRODUCT = "Product_Name";
    public static final String ORDERS_QUANTITY = "Quantity";

    public static final String PRODUCT_TABLE = "product";
    public static final String PRODUCT_ID = "Product_ID";
    public static final String PRODUCT_NAME = "Name";
    public static final String PRODUCT_TYPE = "Product_Type_ID";
    public static final String PRODUCT_PRICE = "Price";
    public static final String PRODUCT_QUANTITY = "Quantity";
    public static final String PRODUCT_RECEIPT = "Receipt_Date";
    public static final String PRODUCT_NOTE = "Note";

    public static final String PRODUCT_TYPE_TABLE = "product_type";
    public static final String PRODUCT_TYPE_ID = "Product_Type_ID";
    public static final String PRODUCT_TYPE_NAME = "Name";
    public static final String PRODUCT_TYPE_TEMP = "Temperature_Mode";
    public static final String PRODUCT_TYPE_SHELF = "Shelf_Life";
    public static final String PRODUCT_TYPE_HUMIDITY = "Humidity";
    public static final String PRODUCT_TYPE_NOTE = "Note";

    public static final String POSITION_TABLE = "position";
    public static final String POSITION_ID = "Position_ID";
    public static final String POSITION_NAME = "Name";
    public static final String POSITION_SALARY = "Salary";
    public static final String POSITION_RESPONSIBILITIES = "Responsibilities";
    public static final String POSITION_REQUIREMENTS = "Requirements";
    public static final String POSITION_NOTE = "Note";
    public static final String POSITION_FORM = "Form_Type";

    public static final String RACK_TABLE = "rack";
    public static final String RACK_ID = "Rack_ID";
    public static final String RACK_PRODUCT_TYPE = "Product_Type_ID";
    public static final String RACK_PRODUCT = "Product_ID";
    public static final String RACK_TYPE = "Rack_Type_ID";
    public static final String RACK_NOTE = "Note";

    public static final String RACK_TYPE_TABLE = "rack_type";
    public static final String RACK_TYPE_ID = "Rack_Type_ID";
    public static final String RACK_TYPE_TEMP = "Temperature_Mode";
    public static final String RACK_TYPE_HUMIDITY = "Humidity";
    public static final String RACK_TYPE_NOTE = "Note";

    public static final String EMPLOYEE_TABLE = "employee";
    public static final String EMPLOYEE_ID = "Employee_ID";
    public static final String EMPLOYEE_FIO = "FIO";
    public static final String EMPLOYEE_POSITION = "Position_ID";
    public static final String EMPLOYEE_LOGIN = "Login";
    public static final String EMPLOYEE_PASSWORD = "Password";
    public static final String EMPLOYEE_PHONE = "Phone";
    public static final String EMPLOYEE_EMAIL = "EMail";
    public static final String EMPLOYEE_CONTRACT = "Contract_ID";
    public static final String EMPLOYEE_PASSPORT = "Passport";
    public static final String EMPLOYEE_ADDRESS = "Address";
    public static final String EMPLOYEE_NOTE = "Note";

    public static final String REFRIGERATOR_TABLE = "refrigerator";
    public static final String REFRIGERATOR_ID = "Refrigerator_ID";
    public static final String REFRIGERATOR_BRAND = "Brand";
    public static final String REFRIGERATOR_VIN = "VIN";
    public static final String REFRIGERATOR_REGISTRATION = "Registration";
    public static final String REFRIGERATOR_EMPLOYEE = "Employee_ID";
    public static final String REFRIGERATOR_TO = "Last_TO_Date";
    public static final String REFRIGERATOR_NOTE = "Note";


}
