package ra.model.entity;

public class Product {
    private int Pid;
    private String ProductName;
    private String Img;
    private Float Price;
    private int ProductQuantity;
    private boolean ProductStatus;
    private int catalogId;

    public Product() {
    }

    public Product(String productName, String img, Float price, int catalogId) {
        ProductName = productName;
        Img = img;
        Price = price;
        this.catalogId = catalogId;
    }

    public Product(int pid, String productName, String img, Float price, int productQuantity, boolean productStatus, int catalogId) {
        Pid = pid;
        ProductName = productName;
        Img = img;
        Price = price;
        ProductQuantity = productQuantity;
        ProductStatus = productStatus;
        this.catalogId = catalogId;
    }

    public int getPid() {
        return Pid;
    }

    public void setPid(int pid) {
        Pid = pid;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public String getImg() {
        return Img;
    }

    public void setImg(String img) {
        Img = img;
    }

    public Float getPrice() {
        return Price;
    }

    public void setPrice(Float price) {
        Price = price;
    }

    public int getProductQuantity() {
        return ProductQuantity;
    }

    public void setProductQuantity(int productQuantity) {
        ProductQuantity = productQuantity;
    }

    public boolean isProductStatus() {
        return ProductStatus;
    }

    public void setProductStatus(boolean productStatus) {
        ProductStatus = productStatus;
    }

    public int getCatalogId() {
        return catalogId;
    }

    public void setCatalogId(int catalogId) {
        this.catalogId = catalogId;
    }
}
