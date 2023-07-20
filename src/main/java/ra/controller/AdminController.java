package ra.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ra.model.entity.Cart;
import ra.model.entity.User;
import ra.model.service.cartService.CartServiceImp;

import ra.model.service.user.UserServiceImp;

import java.util.List;

@Controller
public class AdminController {
    UserServiceImp userService = new UserServiceImp();
    CartServiceImp cartService = new CartServiceImp();


    @GetMapping("/dashboards")
    public String dashboard() {
        return "dashboard";
    }

    @GetMapping("userManagers")
    public String userManager(Model model) {
        List<User> userList = userService.findAll();
        model.addAttribute("list", userList);
        return "userManager";
    }
    @GetMapping("/blockUser/{id}")
    public String blockUser(@PathVariable("id") int id){
        boolean check = userService.blockUser(id);
        if (check){
            return "redirect:/userManagers";
        }else {
            return "error";
        }
    }



    @GetMapping("orderManager")
    public String showInvoice(Model model){
        List<Cart> cartList = cartService.getAllCart();
        float total = 0;
        for (Cart c:cartList) {
            total += c.getTotal();
        }
        model.addAttribute("total",total);
        model.addAttribute("list",cartList);
        return "orderManager";
    }
}
