package ra.model.service.cartService;

import ra.model.entity.Cart;
import ra.model.entity.CartItem;
import ra.model.ultil.ConnectionToDB;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class CartServiceImp implements ICartService{
    @Override
    public List<CartItem> findAll() {

        return null;
    }

    @Override
    public boolean save(CartItem cartItem) {
        Connection conn = null;
        CallableStatement call=null;
        try {

            conn = ConnectionToDB.getConnection();
            call = conn.prepareCall("{call createCartItem(?,?,?,?)}");
            call.setInt(1,cartItem.getOrderId());
            call.setInt(2,cartItem.getProductId());
            call.setFloat(3,cartItem.getPrice());
            call.setInt(4,cartItem.getQuantity());
            int rs = call.executeUpdate();
            if (rs==1) {
                return true;
            }
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public CartItem findById(Integer productId) {
        CartItem cartItem = null;
        Connection conn = null;
        CallableStatement call=null;
        try {
            conn = ConnectionToDB.getConnection();
            call = conn.prepareCall("{call findCartIdByUserLogin(?)}");
            call.setInt(1,productId);
            ResultSet rs = call.executeQuery();
            if (rs.next()){
                cartItem = new CartItem();
                cartItem.setId(rs.getInt("id"));
                cartItem.setOrderId(rs.getInt("orderId"));
                cartItem.setProductId(rs.getInt("productId"));
                cartItem.setProductName(rs.getString("productName"));
                cartItem.setImageUrl(rs.getString("image"));
                cartItem.setPrice(rs.getFloat("product_price"));
                cartItem.setQuantity(rs.getInt("quantity"));
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return cartItem;
    }

    @Override
    public boolean update(CartItem cartItem) {
        Connection conn = null;
        CallableStatement call = null;
        try {
            conn = ConnectionToDB.getConnection();
            call = conn.prepareCall("{call updateCartItem(?,?)}");
            call.setInt(1,cartItem.getId());
            call.setInt(2,cartItem.getQuantity());
            call.executeUpdate();
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean delete(Integer id) {
        Connection conn = null;
        CallableStatement call = null;
        try {
            conn = ConnectionToDB.getConnection();
            call = conn.prepareCall("{call delCartItem(?)}");
            call.setInt(1,id);
            call.executeUpdate();

        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public List<CartItem> findAllByUserLogin(int userId) {
        List<CartItem> list = null;
        Connection conn = null;
        CallableStatement call = null;
        try {
            list = new ArrayList<>();
            conn = ConnectionToDB.getConnection();
            call = conn.prepareCall("{call findListCartItemByCartId(?)}");
            call.setInt(1,userId);
            ResultSet rs = call.executeQuery();
            while (rs.next()){

                CartItem cartItem = new CartItem();
                cartItem.setId(rs.getInt("id"));
                cartItem.setOrderId(rs.getInt("orderId"));
                cartItem.setProductId(rs.getInt("productId"));
                cartItem.setImageUrl(rs.getString("img"));
                cartItem.setProductName(rs.getString("productName"));
                cartItem.setPrice(rs.getFloat("productPrice"));
                cartItem.setQuantity(rs.getInt("quantity"));
                list.add(cartItem);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public CartItem checkExistProduct(Integer proId, Integer cartId) {
        List<CartItem> list = findAllByUserLogin(cartId);
        for (CartItem o : list) {
            if (o.getProductId() == proId) {
                return o;
            }
        }
        return null;
    }

    @Override
    public void clearCartItem(int cartId) {

    }
    @Override
    public int checkOut(int orderId, float total, String phone, String address, int userId) {
        Connection conn=null;
        CallableStatement call=null;
        int cartnewid =-1;
        try {
            conn = ConnectionToDB.getConnection();
            call = conn.prepareCall("{call checkOut(?,?,?,?,?,?)}");
            call.setInt(1,orderId);
            call.setFloat(2,total);
            call.setString(3,phone);
            call.setString(4,address);
            call.setInt(5,userId);
            call.registerOutParameter(6,cartnewid);
            call.executeUpdate();
            cartnewid = call.getInt(6);

        }catch (Exception e){
            e.printStackTrace();
            return cartnewid;
        }finally {
            try {
                conn.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return cartnewid;
    }

    @Override
    public List<Cart> getAllCart() {
        List<Cart> carts = null;
        Connection conn= null;
        CallableStatement call = null;
        try {
            carts = new ArrayList<>();
            conn = ConnectionToDB.getConnection();
            call= conn.prepareCall("{call getAllCart()}");
            ResultSet rs = call.executeQuery();
            while (rs.next()){
                Cart cart = new Cart();
                cart.setCartId(rs.getInt("orderId"));
                cart.setTotal(rs.getFloat("total"));
                cart.setCreateDate(rs.getDate("createdDate"));
                cart.setAddress(rs.getString("address"));
                carts.add(cart);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return carts;
    }

    @Override
    public List<CartItem> getCartItemByCartId(int id) {
        List<CartItem> list = null;
        Connection conn = null;
        CallableStatement call = null;
        try {
            list = new ArrayList<>();
            conn = ConnectionToDB.getConnection();
            call = conn.prepareCall("{call getCartItemByCartId(?)}");
            call.setInt(1,id);
            ResultSet rs = call.executeQuery();
            while (rs.next()){

                CartItem cartItem = new CartItem();
                cartItem.setId(rs.getInt("id"));
                cartItem.setOrderId(rs.getInt("orderId"));
                cartItem.setProductId(rs.getInt("productId"));
                cartItem.setImageUrl(rs.getString("img"));
                cartItem.setProductName(rs.getString("productName"));
                cartItem.setPrice(rs.getFloat("productPrice"));
                cartItem.setQuantity(rs.getInt("quantity"));
                list.add(cartItem);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }
}
