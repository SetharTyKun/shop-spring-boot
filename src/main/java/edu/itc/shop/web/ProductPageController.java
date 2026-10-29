package edu.itc.shop.web;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import edu.itc.shop.domain.Product;
import edu.itc.shop.repository.ProductRepository;
import edu.itc.shop.service.ProductService;
import jakarta.validation.Valid;
import edu.itc.shop.service.ProductService;

@Controller
@RequestMapping("/products")
public class ProductPageController {

    private final ProductRepository repo;
    private final ProductService service;

    public ProductPageController(ProductRepository repo, ProductService service) {
        this.repo = repo;
        this.service = service;
    }
    // GET /products  (list + search)
    @GetMapping
    public String list(@RequestParam(defaultValue = "") String q, Model model) {
        model.addAttribute("products",
                q.isBlank() ? repo.findAll() : repo.findByNameContaining(q));
        model.addAttribute("q", q);
        return "products/list";
    }

    // GET /products/new  (empty form)
    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("productForm", new ProductForm());
        return "products/form";
    }

    // POST /products  (save)
    @PostMapping
    public String create(@Valid @ModelAttribute("productForm") ProductForm form,
                         BindingResult result,
                         RedirectAttributes redirect) {
        if (result.hasErrors()) {
            return "products/form";            // show the form again with errors
        }
        try {
            repo.insert(new Product(null, form.getName(), form.getPrice(), form.getStock()));
        } catch (DuplicateKeyException e) {
            result.rejectValue("name", "duplicate", "A product with this name already exists");
            return "products/form";
        }
        redirect.addFlashAttribute("message", "Product saved: " + form.getName());
        return "redirect:/products";           // POST-redirect-GET
    }
    @PostMapping("/restock")
    @ResponseBody
    public String restock(@RequestParam long from, @RequestParam long to, @RequestParam int qty) {
        service.restock(from, to, qty);
        return "OK";
    }
}