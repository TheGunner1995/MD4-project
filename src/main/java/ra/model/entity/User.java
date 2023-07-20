package ra.model.entity;

public class User {
    private int Uid;
    private String userName;
    private String email;
    private String password;
    private int age;
    private boolean sex;
    private String address;
    private String phone;
    private int role;
    private boolean userStatus;

    public User() {
    }

    public User(String userName, String email, String password, int age, boolean sex, String address, String phone) {
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.age = age;
        this.sex = sex;
        this.address = address;
        this.phone = phone;
    }

    public User(int uid, String userName, String email, String password, int age, boolean sex, String address, String phone, int role, boolean userStatus) {
        Uid = uid;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.age = age;
        this.sex = sex;
        this.address = address;
        this.phone = phone;
        this.role = role;
        this.userStatus = userStatus;
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public int getUid() {
        return Uid;
    }

    public void setUid(int uid) {
        Uid = uid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean isSex() {
        return sex;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public boolean isUserStatus() {
        return userStatus;
    }

    public void setUserStatus(boolean userStatus) {
        this.userStatus = userStatus;
    }
}