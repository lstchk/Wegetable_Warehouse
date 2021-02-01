package sample.Objects;

public class Order {
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String sum) {
        this.price = sum;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public Order() {
        this.id = id;
        this.price = this.price;
        this.client = client;
        this.product = this.product;
        this.quantity = this.quantity;
    }

    private String id;

    public Order(String product, String quantity, String price) {
        this.product = product;
        this.quantity = quantity;
        this.price = price;
    }

    public  Order(String client, String product, String quantity, String price) {
        this.client = client;
        this.product = product;
        this.quantity = quantity;
        this.price = price;
    }

    private String price;
    private String client;
    private String product;
    private String quantity;
}
