package com.glowterx.glowterx.Controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

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
            @RequestParam("prod_status") String prod_status,
            @RequestParam("prod_image") MultipartFile file, Model model) {

        Product product = new Product(prod_name, prod_price, prod_quantity, prod_category, prod_status);
        productDAO.addProduct(product);

        try {
            // get the bytes of the image file
            byte[] imageBytes = file.getBytes();

            // set the imageBytes to the trainee object
            productDAO.uploadProductImage(prod_name, imageBytes);

        } catch (IOException e) {
            e.printStackTrace();
        }

        List<Product> productAll = productDAO.getAllProduct();
        model.addAttribute("product", productAll);

        model.addAttribute("message", "Product added successfully!");
        return "/Admin/ManageShop";
    }

    @GetMapping("/ProductImage")
    public ResponseEntity<byte[]> getProfilePicture(
            @RequestParam("productId") int product_id, Model model) {
        byte[] image = productDAO.getProductImage(product_id);
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        return new ResponseEntity<>(image, headers, HttpStatus.OK);
    }

    @PostMapping("/uploadProductImage")
    public String uploadProfilePicture(@RequestParam("id") int id,
            @RequestParam("imageFile") MultipartFile file, Model model) {
        Product product = productDAO.getProduct(id);
        model.addAttribute("product", product);
        if (!file.isEmpty()) {
            try {
                productDAO.uploadProductImage(product.getProd_name(), file.getBytes());
                model.addAttribute("message", "Successfully uploaded image");
                model.addAttribute("message", "Product updated successfully!");
                return "Admin/EditProduct";
            } catch (IOException e) {
                model.addAttribute("message", "Failed to upload image");
                return "Admin/EditProduct";
            }
        } else {
            model.addAttribute("message", "File is empty");
            return "Admin/EditProduct";
        }
    }

    @GetMapping("/deleteProduct")
    public String deleteProduct(@RequestParam("productId") int prod_id, Model model) {
        productDAO.deleteProduct(prod_id);
        List<Product> productAll = productDAO.getAllProduct();
        model.addAttribute("product", productAll);
        model.addAttribute("message", "Product deleted successfully!");
        return "Admin/ManageShop";
    }

    @GetMapping("/editProduct")
    public String editProduct(@RequestParam("productId") int prod_id, Model model) {
        Product product = productDAO.getProduct(prod_id);
        model.addAttribute("product", product);
        return "Admin/EditProduct";
    }

    @PostMapping("/updateProduct")
    public String updateProduct(
    @ModelAttribute("product") Product product, @RequestParam("category")String catergory, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "error";
        }
        product.setProd_category(catergory);
        productDAO.updateProduct(product);
        List<Product> productAll = productDAO.getAllProduct();
        model.addAttribute("product", productAll);
        model.addAttribute("message", "Product updated successfully!");
        return "Admin/ManageShop";
    }
}
