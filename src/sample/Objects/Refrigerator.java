package sample.Objects;

public class Refrigerator {
    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getVin() {
        return vin;
    }

    public void setVin(String vin) {
        this.vin = vin;
    }

    public String getRegistration() {
        return registration;
    }

    public void setRegistration(String registration) {
        this.registration = registration;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getNameEmployee() {
        return nameEmployee;
    }

    public void setNameEmployee(String nameEmployee) {
        this.nameEmployee = nameEmployee;
    }

    private String nameEmployee;
    private String brand;
    private String vin;
    private  String registration;
    private String to;
    private String note;

    public Refrigerator( String brand, String vin, String registration, String to, String note) {
        this.brand = brand;
        this.vin = vin;
        this.registration = registration;
        this.to = to;
        this.note = note;
    }
}
