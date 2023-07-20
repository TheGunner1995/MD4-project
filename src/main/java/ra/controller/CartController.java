package ra.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ra.model.entity.CartItem;
import ra.model.entity.Product;
import ra.model.entity.UserLogin;
import ra.model.service.cartService.CartServiceImp;
import ra.model.service.cartService.ICartService;
import ra.model.service.product.IProductService;
import ra.model.service.product.ProductServiceImp;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class CartController {
    ICartService cartService = new CartServiceImp();
    IProductService productService = new ProductServiceImp();
    @GetMapping("/cart")
    public String cart(Model model, HttpServletRequest request){
        UserLogin userLogin = (UserLogin) request.getSession().getAttribute("userLogin");
        List<CartItem> list =  cartService.findAllByUserLogin(userLogin.getUid());
        float sum = 0;
        for (CartItem c:list) {
            sum += c.getPrice()*c.getQuantity();
        }
        model.addAttribute("listCart",list);
        model.addAttribute("sum",sum);
        return "cart";
    }

    @GetMapping("addToCart/{id}")
    public String addToCart(@PathVariable("id") int productId, HttpServletRequest request, Model model){
        UserLogin userLogin = (UserLogin) request.getSession().getAttribute("userLogin");
        CartItem cartItem = cartService.checkExistProduct(productId,userLogin.getUid());
        Product p = productService.findById(productId);

        if (cartItem==null){
            cartService.save(new CartItem(userLogin.getCartId(),productId,p.getPrice(),1));
        }else {
            cartItem.setQuantity(cartItem.getQuantity()+1);
            cartService.update(cartItem);
        }
        return "redirect:/cart";
    }
    @GetMapping("deleteCart/{id}")
    public String deleteCart(@PathVariable("id") int id){
        cartService.delete(id);
        return "redirect:/cart";
    }
    @PostMapping ("updateCart")
    public String updateCart(@RequestParam("idUp") int id, @RequestParam("quantityUp") int quantity){
        cartService.update(new CartItem(id,quantity));
        return "redirect:/cart";
    }
    @PostMapping("checkOut")
    public String checkOut(String phone, String address, HttpServletRequest request){
        UserLogin userLogin = (UserLogin) request.getSession().getAttribute("userLogin");
        List<CartItem> list =  cartService.findAllByUserLogin(userLogin.getUid());

        float sum = 0;
        for (CartItem c:list) {
            sum += c.getPrice()*c.getQuantity();
        }
       int cartId = cartService.checkOut(userLogin.getCartId(),sum,phone,address, userLogin.getUid());
        userLogin.setCartId(cartId);
        request.getSession().setAttribute("userLogin", userLogin);
        return "redirect:backToShop";
    }
    @GetMapping("backToShop")
    public String backToShop(Model model, HttpSession session){
        UserLogin userLogin = (UserLogin) session.getAttribute("userLogin");
        List<CartItem> list =  cartService.findAllByUserLogin(userLogin.getUid());
        model.addAttribute("list",list);
        List<Product> productList = productService.findAll();
        model.addAttribute("listProduct",productList);
        return "home";
    }

    @GetMapping("/cartDetail/{id}")
    public String showCartDetail(@PathVariable("id") int id,Model model){
        List<CartItem> cartItemList = cartService.getCartItemByCartId(id);
        int total = 0;
        for (CartItem c:cartItemList) {
            total +=(c.getPrice()*c.getQuantity());
        }
        model.addAttribute("total",total);
        model.addAttribute("list",cartItemList);
        return "cartDetail";
    }
}
