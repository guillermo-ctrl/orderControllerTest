package de.neuefische.java213orderdbserver.controller;

import de.neuefische.java213orderdbserver.model.Order;
import de.neuefische.java213orderdbserver.model.Product;
import de.neuefische.java213orderdbserver.repository.OrderRepository;
import de.neuefische.java213orderdbserver.service.OrderService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.annotation.Resource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

class OrderControllerTest {

    @LocalServerPort
    private int port;

    @Resource
    private TestRestTemplate testRestTemplate;

    @Test
    @DisplayName("get all orders but no orders exist")
    public void getAllOrdersButNoOrdersExist() {
        //Given
        String url = String.format("http://localhost:%d/order/all", port);

        //When
        ResponseEntity<Order[]> response = testRestTemplate.getForEntity(url, Order[].class);

        Order[] actual =  response.getBody();

        assertEquals(0, actual.length);
    }

    @Test
    public void testAddOrder() {
        // given
        String url = String.format("http://localhost:%d/order", port);
        String[] body = {"1", "2"};

        // when
        ResponseEntity<Order> response = testRestTemplate.postForEntity(url,body,Order.class);

        // then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        List<Product> actual = response.getBody().getProducts();
        List<Product> expected = List.of(
                new Product("1", "piano"),
                new Product("2", "guitar")
        );
        assertEquals(expected, actual);
    }



}