package com.glowterx.glowterx.Controller;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.glowterx.glowterx.DOA.CartDAO;
import com.glowterx.glowterx.DOA.OrderDAO;
import com.glowterx.glowterx.DOA.PaymentDAO;
import com.glowterx.glowterx.DOA.ProductDAO;
import com.glowterx.glowterx.DOA.TraineeDAO;
import com.glowterx.glowterx.Model.Cart;
import com.glowterx.glowterx.Model.Payment;
import com.glowterx.glowterx.Model.Product;
import com.glowterx.glowterx.Model.Trainee;

@Controller
public class PaymentController {
    @Autowired
    private CartDAO cartDAO;

    @Autowired
    private TraineeDAO traineeDAO;

    @Autowired
    private ProductDAO productDAO;

    @Autowired
    private PaymentDAO paymentDAO;

    @Autowired
    private OrderDAO orderDAO;

    @GetMapping("/CartCheckout")
    public String CartCheckout(Model model) {
        double subtotal = 0, total = 0;
        Trainee trainee = traineeDAO.getInfoTrainee();
        List<Cart> cartAll = cartDAO.getAllCartTrainee(trainee.getId());
        model.addAttribute("cart", cartAll);

        List<Product> productCart = new ArrayList<Product>();
        int i = 0;
        for (Cart c : cartAll) {
            productCart.add(productDAO.getProduct(c.getProduct_id()));
            subtotal += productCart.get(i++).getProd_price();
        }
        total = subtotal + 10;

        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now();
        String formattedTime = time.format(DateTimeFormatter.ofPattern("HH:mm"));
        model.addAttribute("currentDate", date);
        model.addAttribute("currentDateTime", formattedTime);
        model.addAttribute("total", total);
        model.addAttribute("subtotal", subtotal);
        model.addAttribute("product", productCart);
        model.addAttribute("trainee", trainee);

        return "/Trainee/CustomerCheckout";
    }

    @PostMapping("/checkout")
    public String checkout(@RequestParam("total") String temptotal, @RequestParam("paymentMethod") String paymentMethod,
            Model model) {
        Trainee trainee = traineeDAO.getInfoTrainee();
        List<Cart> cartAll = cartDAO.getAllCartTrainee(trainee.getId());
        List<Product> productAll = productDAO.getAllProduct();
        Product tempProduct = new Product();
        int order_quantity = 0;
        int product_quantity = 0;
        for (Cart c : cartAll) {
            tempProduct = productDAO.getProduct(c.getProduct_id());
            product_quantity = tempProduct.getProd_quantity() - c.getQuantity();
            productDAO.updateProductQuantity(tempProduct.getId(), product_quantity);
            cartDAO.deleteCart(c.getId(), trainee.getId());
            order_quantity++;
        }
        double total = Double.parseDouble(temptotal);
        LocalDate localDate = LocalDate.now();
        java.sql.Date sqlDate = java.sql.Date.valueOf(localDate);
        Payment payment = paymentDAO.createPaymentOrder(trainee.getId(), total, sqlDate, paymentMethod);
        orderDAO.createOrder(trainee.getId(), payment, order_quantity);

        model.addAttribute("product", productAll);
        return "Trainee/AddToCart";
    }
}
