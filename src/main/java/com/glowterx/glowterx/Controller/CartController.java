package com.glowterx.glowterx.Controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.glowterx.glowterx.DOA.CartDAO;
import com.glowterx.glowterx.DOA.ProductDAO;
import com.glowterx.glowterx.DOA.TraineeDAO;
import com.glowterx.glowterx.Model.Cart;
import com.glowterx.glowterx.Model.Product;
import com.glowterx.glowterx.Model.Trainee;

@Controller
public class CartController {
    @Autowired
    private CartDAO cartDAO;

    @Autowired
    private TraineeDAO traineeDAO;

    @Autowired
    private ProductDAO productDAO;

    @GetMapping("/TraineeaddCart")
    public String addCart(
     @RequestParam("productId") int product_id, Model model) {
        Trainee trainee = traineeDAO.getInfoTrainee();
        Cart cart = new Cart(1,trainee.getId(), product_id);
        cartDAO.addCart(cart);

        List <Cart> cartAll = cartDAO.getAllCart();
        model.addAttribute("cart", cartAll);

        List <Product> productCart = new ArrayList<Product>();
        for (Cart c : cartAll) {
            productCart.add(productDAO.getProduct(c.getProduct_id()));
        }
        model.addAttribute("product", productCart);

        model.addAttribute("message", "Product added successfully into the Cart!");
        return "/Trainee/ListCart";
    }

    @GetMapping("/deleteCart")
    public String deleteCart(@RequestParam("cartId") int cart_id, Model model) {
        cartDAO.deleteCart(cart_id);

        List <Cart> cartAll = cartDAO.getAllCart();
        model.addAttribute("cart", cartAll);

        List<Product> productAll = productDAO.getAllProduct();
        model.addAttribute("product", productAll);

        model.addAttribute("message", "Cart deleted successfully!");

        return "Trainee/ListCart";
    }

    
}
