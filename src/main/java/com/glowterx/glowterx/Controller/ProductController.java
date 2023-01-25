package com.glowterx.glowterx.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.glowterx.glowterx.DOA.ProductDAO;
import com.glowterx.glowterx.Model.Product;

@Controller
public class ProductController {
    @Autowired
    private ProductDAO productDAO;

    @PostMapping("/AdminaddProduct")
    public String addProduct(@RequestParam("prod_name") String prod_name,
            @RequestParam("prod_price") double prod_price,
            @RequestParam("prod_quantity") int prod_quantity,
            @RequestParam("prod_category") String prod_category,
            @RequestParam("prod_status") String prod_status, Model model) {
        Product product = new Product(prod_name, prod_price, prod_quantity, prod_category, prod_status);
        productDAO.addProduct(product);
        List<Product> productAll = productDAO.getAllProduct();
        model.addAttribute("product", productAll);
        
        model.addAttribute("message", "Product added successfully!");
        return "/Admin/ManageShop";
    }

    
}
