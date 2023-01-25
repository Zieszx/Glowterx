package com.glowterx.glowterx.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.glowterx.glowterx.DOA.CartDAO;
import com.glowterx.glowterx.DOA.ProductDAO;
import com.glowterx.glowterx.Model.Cart;
import com.glowterx.glowterx.Model.Product;

@Controller
public class CartController {
    @Autowired
    private CartDAO cartDAO;

    @PostMapping("/Trainee/addCart")
    public String addCart(@RequestParam("prod_name") String prod_name,
            @RequestParam("quantity") int quantity,
            @RequestParam("person_id") int person_id,
            @RequestParam("product_id") int product_id, Model model) {
        Cart cart = new Cart(quantity, person_id, product_id);
        cartDAO.addCart(cart);
        model.addAttribute("message", "Product added successfully into the Cart!");
        return "/Trainee/ListCart";
    }

    
}
