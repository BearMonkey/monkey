package org.monkey.product.controller;

import lombok.extern.slf4j.Slf4j;
import org.monkey.common.dto.Result;
import org.monkey.product.exception.ProductException;
import org.monkey.product.pojo.Product;
import org.monkey.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/product")
public class ProductController {
    
    @Autowired
    private ProductService productService;
    
    @PostMapping
    public Result addStore(@RequestBody Product product) {
        log.info("recieve req: /product, add product.");
        productService.addProduct(product);
        return Result.ok();
    }
    
    @PutMapping("/inventory/{id}/{number}")
    public Result deductingInventory(@PathVariable("id") Integer id, @PathVariable("number") int num) {
        try {
            productService.deductingInventory(id, num);
            return Result.ok();
        } catch (ProductException e) {
            return new Result(Result.SUCCESS_CODE, e.getMessage(), null);
        }
    }
    
    @GetMapping
    public Result lsit() {
        log.info("recieve req: /product, list product.");
        return new Result(productService.queryAllProduct());
    }
    
    @GetMapping("/{id}")
    public Result queryById(@PathVariable("id") Integer id) {
        return new Result(productService.queryById(id));
    }
}
