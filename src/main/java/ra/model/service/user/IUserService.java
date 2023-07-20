package ra.model.service.user;
import ra.model.entity.User;
import ra.model.entity.UserLogin;
import ra.model.service.*;

public interface IUserService extends IService<User,Integer>{
    UserLogin login(String email, String password);
    boolean checkExistsUsername(String username);

    boolean blockUser(int uId);
    User checkEmail(String email);

}
