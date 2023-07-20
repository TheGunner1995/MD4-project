package ra.model.service.product;

import ra.model.entity.Product;
import ra.model.ultil.ConnectionToDB;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ProductServiceImp implements IProductService{
    @Override
    public List<Product> findAll() {
        List<Product> ProductList = null;
        Connection conn = null;
        CallableStatement call = null;
        try {
            ProductList = new ArrayList<>();
            conn = ConnectionToDB.getConnection();
            call = conn.prepareCall("{call getProduct()}");
            ResultSet rs = call.executeQuery();
            while (rs.next()){
                Product product = new Product();
                product.setPid(rs.getInt("Pid"));
                product.setProductName(rs.getString("ProductName"));
                product.setImg(rs.getString("img"));
                product.setPrice(rs.getFloat("price"));
                product.setProductQuantity(rs.getInt("productQuantity"));
                product.setProductStatus(rs.getBoolean("productStatus"));
                product.setCatalogId(rs.getInt("catalogId"));
                ProductList.add(product);
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            try {
                conn.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return ProductList;
    }

    @Override
    public boolean save(Product product) {
        Connection conn = null;
        CallableStatement call = null;
        try {
            conn = ConnectionToDB.getConnection();
            call = conn.prepareCall("{call createProduct(?,?,?,?)}");
            call.setString(1, product.getProductName());
            call.setString(2, product.getImg());
            call.setFloat(3,product.getPrice());
            call.setInt(4,product.getCatalogId());
            int rs = call.executeUpdate();
            if (rs == 1){
                return true;
            }

        }catch(Exception e){
            e.printStackTrace();
        }finally {
            try {
                conn.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return false;
    }

    @Override
    public boolean update(Product product) {
        Connection conn = null;
        CallableStatement call = null;
        try {
            conn = ConnectionToDB.getConnection();
            call = conn.prepareCall("{call updateProduct(?,?,?,?,?)}");
            call.setInt(1,product.getPid());
            call.setString(2,product.getProductName());
            call.setString(3,product.getImg());
            call.setFloat(4,product.getPrice());
            call.setInt(5,product.getCatalogId());
            ResultSet rs = call.executeQuery();
            if (rs.next()){
                return true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                conn.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return false;
    }

    @Override
    public Product findById(Integer idEdit) {
        Connection conn =null;
        CallableStatement call = null;
        Product product = null;
        try {
            conn = ConnectionToDB.getConnection();
            call = conn.prepareCall("{call findProductById(?)}");
            call.setInt(1,idEdit);
            ResultSet rs = call.executeQuery();
            if (rs.next()){
                product = new Product(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getFloat(4),rs.getInt(5),rs.getBoolean(6),rs.getInt(7));
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                conn.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return product;
    }

    @Override
    public boolean delete(Integer idDel) {
        Connection conn =null;
        CallableStatement call = null;
        try {
            conn = ConnectionToDB.getConnection();
            call = conn.prepareCall("{call deleteProduct(?)}");
            call.setInt(1,idDel);
            int rs = call.executeUpdate();
            if (rs ==1){
                return true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                conn.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return false;
    }
}
