package com.myproject.product_service.dto;

import java.math.BigDecimal;

// it eleminates the unnecessary boiler plate code like setters,getters etc.,
// also it is immutable
//1.	Immutable Data: Fields in a record are final, meaning once an instance is created, its state cannot be altered.
//2.	Concise Syntax: Records reduce the amount of boilerplate code needed for constructors, getters, toString(), equals(), and hashCode() methods.
//3.	Automatic Method Generation: Java automatically generates implementations for commonly used methods like toString(), equals(), and hashCode().
public record ProductRequest(String name, String description, BigDecimal price) {
}
