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
import com.glowterx.glowterx.Model.OrderUser;
import com.glowterx.glowterx.Model.Payment;
import com.glowterx.glowterx.Model.Product;
import com.glowterx.glowterx.Model.Trainee;

@Controller
public class OrderController {
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

    @GetMapping("/myOrder")
    public String myOrder(Model model) {
        Trainee trainee = traineeDAO.getInfoTrainee();
        List<OrderUser> orderAll = orderDAO.getAllOrdersByID(trainee.getId());
        List<Payment> paymentAll = new ArrayList<Payment>();
        List<Product> productAll = new ArrayList<Product>();
        double subtotal = 0, total = 0;
        int i = 0;
        int sizeOrder = orderAll.size();
        for (OrderUser o : orderAll) {
            paymentAll.add(paymentDAO.getPaymentbyID(o.getPayment_id()));
            productAll.add(productDAO.getProduct(o.getProduct_id()));
            subtotal += productAll.get(i++).getProd_price();
        }

        total = subtotal + 10;
        System.out.println(sizeOrder);
        model.addAttribute("order", orderAll);
        model.addAttribute("payment", paymentAll);
        model.addAttribute("product", productAll);
        model.addAttribute("subtotal", subtotal);
        model.addAttribute("total", total);
        model.addAttribute("sizeOrder", sizeOrder);
        return "Trainee/ListOrder";
    }

    @GetMapping("/ManageOrder")
    public String ManageOrder(Model model) {
        List<OrderUser> orderAll = orderDAO.getAllOrders();
        List<Payment> paymentAll = new ArrayList<Payment>();
        List<Product> productAll = new ArrayList<Product>();
        List<Trainee> traineeAll = new ArrayList<Trainee>();
        int sizeOrder = orderAll.size();
        for (OrderUser o : orderAll) {
            paymentAll.add(paymentDAO.getPaymentbyID(o.getPayment_id()));
            productAll.add(productDAO.getProduct(o.getProduct_id()));
            traineeAll.add(traineeDAO.getInfoTraineebyId(o.getPerson_id()));
        }
        System.out.println(sizeOrder);
        model.addAttribute("order", orderAll);
        model.addAttribute("payment", paymentAll);
        model.addAttribute("product", productAll);
        model.addAttribute("trainee", traineeAll);
        model.addAttribute("sizeOrder", sizeOrder);
        return "Admin/ListOrderEdit";
    }

    @PostMapping("/ManageOrder")
    public String ManageOrder(Model model, @RequestParam("id") int id, @RequestParam("status") String status) {
        orderDAO.updateOrderStatus(id, status);
        List<OrderUser> orderAll = orderDAO.getAllOrders();
        List<Payment> paymentAll = new ArrayList<Payment>();
        List<Product> productAll = new ArrayList<Product>();
        List<Trainee> traineeAll = new ArrayList<Trainee>();
        int sizeOrder = orderAll.size();
        for (OrderUser o : orderAll) {
            paymentAll.add(paymentDAO.getPaymentbyID(o.getPayment_id()));
            productAll.add(productDAO.getProduct(o.getProduct_id()));
            traineeAll.add(traineeDAO.getInfoTraineebyId(o.getPerson_id()));
        }
        System.out.println(sizeOrder);
        model.addAttribute("order", orderAll);
        model.addAttribute("payment", paymentAll);
        model.addAttribute("product", productAll);
        model.addAttribute("trainee", traineeAll);
        model.addAttribute("sizeOrder", sizeOrder);
        return "Admin/ListOrderEdit";
    }
}
