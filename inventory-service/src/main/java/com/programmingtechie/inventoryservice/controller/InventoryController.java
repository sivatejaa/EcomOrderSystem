package com.programmingtechie.inventoryservice.controller;

import com.programmingtechie.inventoryservice.service.InventoryService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @SneakyThrows
    public boolean isInStock(@RequestParam String skuCode, @RequestParam Integer quantity) {
        System.out.println("Service called");
        Thread.sleep(10000);
        return inventoryService.isInStock(skuCode, quantity);
    }


}
