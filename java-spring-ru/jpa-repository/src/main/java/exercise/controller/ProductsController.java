package exercise.controller;

import exercise.exception.ResourceNotFoundException;
import exercise.model.Product;
import exercise.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductsController {

    @Autowired
    private ProductRepository productRepository;

    // BEGIN
    @GetMapping(path = "")
    public List<Product> findByPrice(
            @RequestParam(required = false) Integer min,
            @RequestParam(required = false) Integer max) {

        List<Product> product;
        if (min == null && max == null) {
            product = productRepository.findAll(Sort.by(Sort.Order.asc("price")));
        } else if (min == null) {
            product = productRepository.findByPriceIsLessThan(max, Sort.by(Sort.Order.asc("price")));
        } else if (max == null) {
            product = productRepository.findByPriceGreaterThan(min, Sort.by(Sort.Order.asc("price")));
        } else {
            product = productRepository.findByPriceBetween(min, max, Sort.by(Sort.Order.asc("price")));
        }

        return product;
    }
    // END

    @GetMapping(path = "/{id}")
    public Product show(@PathVariable long id) {

        var product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product with id " + id + " not found"));

        return product;
    }
}
