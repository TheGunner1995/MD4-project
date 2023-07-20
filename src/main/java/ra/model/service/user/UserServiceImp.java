package ra.model.service.user;

import ra.model.entity.User;
import ra.model.entity.UserLogin;
import ra.model.ultil.ConnectionToDB;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class UserServiceImp implements IUserService{
    @Override
    public List<User> findAll() {
        List<User> user = null;
        Connection conn = null;
        CallableStatement call = null;
        try {
            user = new ArrayList<>();
            conn = ConnectionToDB.getConnection();
            call = conn.prepareCall("{call getAllUser()}");
            ResultSet rs =call.executeQuery();
            while (rs.next()){
                User users =new User();
                users.setUid(rs.getInt("Uid"));
                users.setUserName(rs.getString("UserName"));
                users.setEmail(rs.getString("Email"));
                users.setAge(rs.getInt("Age"));
                users.setSex(rs.getBoolean("Sex"));
                users.setAddress(rs.getString("Address"));
                users.setPhone(rs.getString("Phone"));
                users.setRole(rs.getInt("Role"));
                users.setUserStatus(rs.getBoolean("UserStatus"));
                user.add(users);
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            try {
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return user;
    }

    @Override
    public boolean save(User user) {
        Connection conn = null;
        try {
            conn= ConnectionToDB.getConnection();
            CallableStatement callSt = conn.prepareCall("{call PROC_REGISTER(?,?,?,?,?,?,?)}");
            callSt.setString(1,user.getUserName());
            callSt.setString(2,user.getEmail());
            callSt.setString(3, user.getPassword());
            callSt.setInt(4,user.getAge());
            callSt.setBoolean(5,user.isSex());
            callSt.setString(6,user.getAddress());
            callSt.setString(7,user.getPhone());
            callSt.executeUpdate();
        }catch (Exception e) {
            e.printStackTrace();
            return false;
        }finally {
            try {
                ConnectionToDB.close(conn);
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    @Override
    public boolean update(User user) {
        return false;
    }

    @Override
    public User findById(Integer integer) {
        return null;
    }

    @Override
    public boolean delete(Integer integer) {
        return false;
    }

    @Override
    public UserLogin login(String email, String password) {
        UserLogin userLogin= null;
        Connection conn = null;
        try {
            conn= ConnectionToDB.getConnection();
            CallableStatement callSt = conn.prepareCall("{call PROC_LOGIN(?,?)}");
            callSt.setString(1, email);
            callSt.setString(2, password);
            ResultSet rs = callSt.executeQuery();
            if (rs.next()) {
                userLogin= new UserLogin();
                userLogin.setUid(rs.getInt(1));
                userLogin.setUserName(rs.getString(2));
                userLogin.setEmail(rs.getString(3));
                userLogin.setPassword(rs.getString(4));
                userLogin.setAge(rs.getInt(5));
                userLogin.setSex(rs.getBoolean(6));
                userLogin.setAddress(rs.getString(7));
                userLogin.setPhone(rs.getString(8));
                userLogin.setRole(rs.getInt(9));
                userLogin.setUserStatus(rs.getBoolean(10));
                userLogin.setCartId(rs.getInt(11));
            }
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                ConnectionToDB.close(conn);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return userLogin;
    }

    @Override
    public boolean checkExistsUsername(String email) {

        Connection conn = null;
        try {
            conn= ConnectionToDB.getConnection();
            CallableStatement callSt = conn.prepareCall("{call PROC_FINDBYEMAIL(?)}");
            callSt.setString(1,email);

            ResultSet rs = callSt.executeQuery();
            if (rs.next()) {
                return true;
            }
        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                ConnectionToDB.close(conn);
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }
    @Override
    public boolean blockUser(int uId) {
        Connection conn = null;
        CallableStatement call = null;
        try {
            conn = ConnectionToDB.getConnection();
            call = conn.prepareCall("{call blockUser(?)}");
            call.setInt(1,uId);
            int rs = call.executeUpdate();
            if (rs==1){
                return true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public User checkEmail(String email) {
        Connection conn = null;
        CallableStatement call = null;
        User user = null;
        try{
            conn = ConnectionToDB.getConnection();
            call = conn.prepareCall("{call checkExistEmail(?)}");
            call.setString(1,email);
            ResultSet rs = call.executeQuery();
            while (rs.next()){
                user = new User();
                user.setUid(rs.getInt("Uid"));
                user.setUserName(rs.getString("userName"));
                user.setPassword(rs.getString("password"));
                user.setEmail(rs.getString("email"));
                user.setAge(rs.getInt("age"));
                user.setSex(rs.getBoolean("sex"));
                user.setAddress(rs.getString("address"));
                user.setPhone(rs.getString("phone"));
                user.setRole(rs.getInt("role"));
                user.setUserStatus(rs.getBoolean("userStatus"));
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return user;
    }
}