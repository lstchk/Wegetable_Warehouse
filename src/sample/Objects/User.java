package sample.Objects;

public class User
{
    public String getFio()
    {
        return fio;
    }

    public void setFio(String fio)
    {
        this.fio = fio;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getPhone()
    {
        return phone;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public String getId() {return id;}

    public void setId(String id) {this.id = id;}

    public String getNote()
    {
        return note;
    }

    public void setNote(String note)
    {
        this.note = note;
    }

    private String fio;
    private String email;
    private String password;
    private String phone;
    private String note;
    private String id;

    public User() { }
    public User(String fio, String email, String password, String phone, String note)
    {
        this.fio = fio;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.note = note;
        this.id = id;
    }
}
