package ra.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ra.model.entity.Catalog;
import ra.model.entity.Product;
import ra.model.service.catalog.CatalogServiceImp;
import ra.model.service.product.ProductServiceImp;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Controller
public class ProductController {
    CatalogServiceImp catalogService = new CatalogServiceImp();
    ProductServiceImp productService = new ProductServiceImp();

    @GetMapping("productManager")
    public String productManager(Model model){
        List<Catalog> catalogList = catalogService.findAll();
        model.addAttribute("list", catalogList);
        List<Product> productList = productService.findAll();
        model.addAttribute("listPro", productList);
        return "productManager";
    }

    @PostMapping("createProduct")
    public String createProduct( String ProductName,@RequestParam("img")MultipartFile img, Float price, int catalogId, Model model) throws IOException {
        String upLoadPath = "D:/MD4/project-MD4/src/main/resources/assets/img";
        File file = new File(upLoadPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        String fileName = img.getOriginalFilename();
        FileCopyUtils.copy(img.getBytes(),new File(upLoadPath+File.separator + fileName));
        model.addAttribute("fileName",fileName);
        Product product = new Product(ProductName,fileName,price,catalogId);
        productService.save(product);
        return "redirect:/productManager";
    }
    @GetMapping("/deletePro/{id}")
    public String deleteProduct(@PathVariable("id") String id){
        productService.delete(Integer.parseInt(id));
        return "redirect:/productManager";
    }

    @GetMapping("/editPro/{id}")
    public String editProduct(@PathVariable("id") String id, Model model){
        List<Catalog> catalogList = catalogService.findAll();
        model.addAttribute("list", catalogList);
        Product product = productService.findById(Integer.parseInt(id));
        model.addAttribute("editPro", product);
        return "editProduct";
    }

    @PostMapping("updateProduct")
        public String updateProduct(@RequestParam("imgInput") MultipartFile img, @ModelAttribute Product product, Model model) throws IOException {
            if (img.isEmpty()){
                product.setImg(productService.findById(product.getPid()).getImg());
            }else {
                String uploadPath = "D:/MD4/project-MD4/src/main/resources/assets/img";
                File file = new File(uploadPath);
                if (!file.exists()) {
                    file.mkdirs();
                }
                String fileName = img.getOriginalFilename();
                FileCopyUtils.copy(img.getBytes(),new File(uploadPath+File.separator + fileName));
                model.addAttribute("fileName",fileName);
                product.setImg(fileName);
            }
            productService.update(product);
            return "redirect:/productManager";
    }
}
