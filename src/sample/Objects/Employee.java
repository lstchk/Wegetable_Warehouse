package sample.Objects;

public class Employee {

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContract() {
        return contract;
    }

    public void setContract(String contract) {
        this.contract = contract;
    }

    public String getPassport() {
        return passport;
    }

    public void setPassport(String passport) {
        this.passport = passport;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Employee() {
        this.fio = fio;
        this.position = position;
        this.login = login;
        this.password = password;
        this.phone = phone;
        this.email = email;
        this.contract = contract;
        this.passport = passport;
        this.address = address;
        this.note = note;
        this.form = form;
    }

    private String fio;
    private String position;
    private String login;
    private String password;
    private String phone;
    private String email;
    private String contract;
    private String passport;
    private String address;
    private String note;

    public int getId() {
        return form;
    }

    public void setId(int id) {
        this.form = id;
    }

    public int getForm() {
        return form;
    }

    public void setForm(int form) {
        this.form = form;
    }

    private int form;

}
