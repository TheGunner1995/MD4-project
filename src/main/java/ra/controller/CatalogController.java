package ra.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ra.model.entity.Catalog;
import ra.model.entity.User;
import ra.model.service.catalog.CatalogServiceImp;
import ra.model.service.catalog.ICatalogService;
import ra.model.service.user.IUserService;
import ra.model.service.user.UserServiceImp;

import java.util.List;

@Controller
public class CatalogController {
    ICatalogService catalogService = new CatalogServiceImp();
    IUserService userService = new UserServiceImp();


    @GetMapping("catalogManager")
    public String catalogManager(Model model){
        List<Catalog> catalogList = catalogService.findAll();
        model.addAttribute("list", catalogList);
    return "catalogManager";
    }

    @PostMapping("createCatalog")
    public String createCatalog(String catalogName) {
        Catalog catalog = new Catalog();
        catalog.setCatalogName(catalogName);
        boolean check = catalogService.save(catalog);
        if (check) {
            return "redirect:catalogManager";
        }else {
            return "";
        }
    }

    @GetMapping("/deleteCatalog/{id}")
    public String deleteCatalg(@PathVariable("id") String id){
      catalogService.delete(Integer.parseInt(id));
            return "redirect:/catalogManager";
    }
    @GetMapping("/editCatalog/{id}")
    public String editCatalog(@PathVariable("id") int id, Model model){
        Catalog catalog = catalogService.findById(id);
        model.addAttribute("editCatalog", catalog);
        return "editCatalog";
    }
    @PostMapping("updateCatalog")
    public String updateCatalog(Catalog catalog){
        catalogService.update(catalog);
        return "redirect:catalogManager";
    }
}

