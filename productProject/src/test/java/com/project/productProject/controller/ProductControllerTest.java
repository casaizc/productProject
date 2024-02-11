package com.project.productProject.controller;

import com.project.productProject.model.Product;
import com.project.productProject.service.ProductService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.mockito.Mockito;
import org.hamcrest.Matchers;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @Test
    public void testGetAllProducts() throws Exception {
        // Arrange
        Product product1 = new Product("Laptop", "High-performance laptop", 1200);
        Product product2 = new Product("Phone", "Smartphone with advanced features", 800);
        List<Product> products = Arrays.asList(product1, product2);

        Mockito.when(productService.findAll()).thenReturn(products);

        // Act and Assert
        mockMvc.perform(MockMvcRequestBuilders.get("/api/product"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].nombre", Matchers.is(product1.getNombre())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].nombre", Matchers.is(product2.getNombre())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].descripcion", Matchers.is(product1.getDescripcion())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].descripcion", Matchers.is(product2.getDescripcion())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].precio", Matchers.is(product1.getPrecio())))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].precio", Matchers.is(product2.getPrecio())));
    }
}

