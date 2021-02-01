package sample.Objects;

public class Product {
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getReceiptDate() {
        return receiptDate;
    }

    public void setReceiptDate(String receiptDate) {
        this.receiptDate = receiptDate;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Product() {
        this.id = id;
        this.name = name;
        this.productType = productType;
        this.price = price;
        this.quantity = quantity;
        this.receiptDate = receiptDate;
        this.note = note;
    }




    private String id;
    private String name;
    private String productType;
    private String price;
    private String quantity;
    private String receiptDate;
    private String note;

    public Product(String name, String price, String quantity, String receiptDate, String note) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.receiptDate = receiptDate;
        this.note = note;
    }
}
