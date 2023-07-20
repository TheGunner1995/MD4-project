package ra.model.entity;

import java.util.Date;

public class Cart {
    private int cartId;
    private float total;
    private Date createDate;
    private String address;

    public Cart() {
    }

    public Cart(int cartId, float total, Date createDate, String address) {
        this.cartId = cartId;
        this.total = total;
        this.createDate = createDate;
        this.address = address;
    }

    public int getCartId() {
        return cartId;
    }

    public void setCartId(int cartId) {
        this.cartId = cartId;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
