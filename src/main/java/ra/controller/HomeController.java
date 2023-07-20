package ra.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ra.model.entity.Product;
import ra.model.entity.User;
import ra.model.entity.UserLogin;
import ra.model.service.product.ProductServiceImp;
import ra.model.service.user.UserServiceImp;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class HomeController {


    private UserServiceImp userService = new UserServiceImp();
    ProductServiceImp productService = new ProductServiceImp();

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("product")
    public String product(Model model) {
        List<Product> productList = productService.findAll();
        model.addAttribute("listPro", productList);
        return "product";
    }

    @PostMapping("/login")
    public String formLogin(String email, String password, Model model, HttpServletRequest request) {
        if (email.equals("")||password.equals("")){
            model.addAttribute("login","Not Required");
            return "login";
        }
        UserLogin user = userService.login(email, password);

        if (user == null) {
            model.addAttribute("login", "Email or password incorrect");
            return "login";
        } else if (!user.isUserStatus()){
            model.addAttribute("login", "Your account has been locked, please contact admin");
            return "login";
        }else {
            request.getSession().setAttribute("userLogin", user);
            if (user.getRole() == 1) {
                return "redirect:dashboards";
            } else if (user.getRole() == 0) {
                // đây là người dùng
                return "home";
            } else {
                return "home";
            }
        }


    }

    @GetMapping("/formRegister")
    public String formRegister(Model model) {
        User user = new User();
        model.addAttribute("newUser", user);
        return "register";
    }

    @GetMapping("/formLogin")
    public String login() {
        return "login";
    }

    @PostMapping("/register")
    public String register(User newUser, Model model, String repass) {
        User user = userService.checkEmail(newUser.getEmail());
        if (user != null) {
            model.addAttribute("error", "email existed, please try again!");
            return "register";
        } else if (newUser.getUserName().trim() == "" || newUser.getEmail().trim() == "" || newUser.getPassword().trim() == "" || newUser.getAddress().trim() == "" || newUser.getPhone().trim() == "") {
            model.addAttribute("error", "Not Required");
            return "register";
        } else if (newUser.getPassword().length() < 5) {
            model.addAttribute("error", "passWord must be at least 5 characters");
            return "register";
        } else if (!newUser.getPassword().equals(repass)) {
            model.addAttribute("error", "rePassword not match, please try again!");
            return "register";
        } else {
            userService.save(newUser);
            return "redirect:formLogin";
        }
    }



        @GetMapping("/logout")
        public String logout (HttpServletRequest request){
            request.getSession().removeAttribute("userLogin");
            return "home";
        }

}
