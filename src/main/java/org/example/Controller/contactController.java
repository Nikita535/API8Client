package org.example.Controller;


import org.example.Client;
import org.example.Entity.Task;
import org.example.ProductService.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class contactController {

    @Autowired
    ProductService productService;

    @GetMapping("/")
    public String getPage(Model model){
        System.out.println(productService.productsToJson(Client.allCases()));
        model.addAttribute("contacts", productService.productsToJson(Client.allCases()).toList());
        return "index";
    }

    @PostMapping("/addCase")
    @ResponseBody
    public Task saveCase(@RequestBody Task entity){
        return productService.addCase(entity);
    }

    @PostMapping("/deleteCase/{id}")
    @ResponseBody
    public Long deleteCase(@PathVariable Long id){
        productService.deleteCase(id);
        return id;
    }

    @PostMapping("/updateCase/{id}")
    @ResponseBody
    public Task updateCase(@RequestBody Task entity, @PathVariable long id){
        return productService.changeCase(new Task(id, entity.getDate(), entity.getName(),
                entity.getDescription()));
    }


    @PostMapping("/getCase/{id}")
    @ResponseBody
    public Task getContact(@PathVariable Long id){
        return productService.getCaseById(id);
    }
}
