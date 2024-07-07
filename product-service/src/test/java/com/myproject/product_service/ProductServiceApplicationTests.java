package com.myproject.product_service;

import com.myproject.product_service.dto.ProductRequest;
import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Import;
import org.testcontainers.containers.MongoDBContainer;
import org.hamcrest.Matchers;

import java.math.BigDecimal;

@Import(TestcontainersConfiguration.class)
//using test containers here - mongo db
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductServiceApplicationTests {

	@ServiceConnection // with this Spring boot will automaticalluy create the uri for mongoDB
	static MongoDBContainer mongoDBContainer= new MongoDBContainer("mongo:7.0.5");
	//creating/using the docker container which is already created.

	@LocalServerPort //wherever the application is running it will inject that port here.
	private Integer port;

	@BeforeEach
	void setup(){
		RestAssured.baseURI="http://localhost";
		RestAssured.port=port;
	}
	static {
		mongoDBContainer.start();
	}

	@Test
	void shouldCreateProduct() throws Exception {
		ProductRequest productRequest = getProductRequest();

		RestAssured.given()
				.contentType("application/json")
				.body(productRequest)
				.when()
				.post("/api/product")
				.then()
				.log().all()
				.statusCode(201)
				.body("id", Matchers.notNullValue())
				.body("name", Matchers.equalTo(productRequest.name()))
				.body("description", Matchers.equalTo(productRequest.description()))
				.body("price", Matchers.is(productRequest.price().intValueExact()));
	}

	private ProductRequest getProductRequest() {
		return new ProductRequest("iPhone 13", "iPhone 13", BigDecimal.valueOf(1200));
	}

}
