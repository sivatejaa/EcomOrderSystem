package com.programmingtechie.orderservice.service;

import com.programmingtechie.orderservice.client.InventoryClient;
import com.programmingtechie.orderservice.dto.OrderRequest;
import com.programmingtechie.orderservice.model.Order;
import com.programmingtechie.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional

public class OrderService {

    private final OrderRepository orderRepository;

    private final InventoryClient inventoryClient;

    private final KafkaTemplate<String, Order> kafkaTemplate;

    public String placeOrder(OrderRequest orderRequest) {


        var inStock = inventoryClient.isInStock(orderRequest.skuCode(), orderRequest.quantity());
        if(inStock){
            System.out.println("called the stock");
            Order order = mapToOrder(orderRequest);
            orderRepository.save(order);
            kafkaTemplate.send("notificationTopic",order);
            return "Order placed successfully";
        }else {
            throw new RuntimeException("Exception occurred");
        }



    }

    private static Order mapToOrder(OrderRequest orderRequest) {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        order.setPrice(orderRequest.price());
        order.setQuantity(orderRequest.quantity());
        order.setSkuCode(orderRequest.skuCode());
        return order;
    }
}
