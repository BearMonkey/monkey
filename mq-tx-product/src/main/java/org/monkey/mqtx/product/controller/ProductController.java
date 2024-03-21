package org.monkey.mqtx.product.controller;

import lombok.extern.slf4j.Slf4j;
import org.monkey.common.dto.Result;
import org.monkey.mqtx.product.pojo.Product;
import org.monkey.mqtx.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/mqtx/product")
@Slf4j
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping("/products")
    public Result products() {
        log.info("receive request /mqtx/product/products, method=GET");
        List<Product> products = productService.queryAllProduct();
        return Result.ok(products);
    }
}
