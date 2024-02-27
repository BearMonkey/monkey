package org.monkey.dist.store.controller;

import lombok.extern.slf4j.Slf4j;
import org.monkey.dist.store.exception.StoreException;
import org.monkey.dist.store.pojo.Product;
import org.monkey.dist.store.service.ProductService;
import org.monkey.oauth.common.dto.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dist/store")
@Slf4j
public class StoreController {

    @Autowired
    private ProductService productService;

    @PostMapping
    public Result addStore(@RequestBody Product product) {
        log.info("recieve req: /dist/store, add product.");
        productService.addProduct(product);
        return Result.ok();
    }

    @PutMapping("/inventory/{id}/{number}")
    public Result deductingInventory(@PathVariable("id") Integer id, @PathVariable("number") int num) {
        try {
            productService.deductingInventory(id, num);
            return Result.ok();
        } catch (StoreException e) {
            return new Result(Result.SUCCESS_CODE, e.getMessage(), null);
        }
    }

    @GetMapping
    public Result lsit() {
        return new Result(productService.queryAllProduct());
    }

    @GetMapping("/{id}")
    public Result queryById(@PathVariable("id") Integer id) {
        return new Result(productService.queryById(id));
    }
}
