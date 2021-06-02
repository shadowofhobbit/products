package julia.products.products;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductsController.class)
class ProductsControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProductsService productsService;

    @Test
    void create() throws Exception {
        Product pen = prepareData(null,
                List.of(new ProductDetails("en", "Pen", "Blue pen")),
                List.of(new Price("EUR", BigDecimal.valueOf(1.5))), null);
        Product createdPen = prepareData(1L,
                List.of(new ProductDetails("en", "Pen", "Blue pen")),
                List.of(new Price("EUR", BigDecimal.valueOf(1.5))), LocalDate.now());
        when(productsService.create(pen)).thenReturn(createdPen);
        var json = objectMapper.writeValueAsString(pen);
        var expectedJson = objectMapper.writeValueAsString(createdPen);
        mockMvc.perform(
                post("/products/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andExpect(content().json(expectedJson));
    }

    @Test
    void getById() throws Exception {
        var prices = List.of(new Price("EUR", BigDecimal.valueOf(1.5)),
                new Price("USD", BigDecimal.valueOf(1.83)));
        var details = List.of(
                new ProductDetails("ru", "ручка", "синяя ручка"),
                new ProductDetails("en", "pen", "blue pen"));
        Product pen = prepareData(1L, details,
                prices, LocalDate.now().minusDays(10));
        when(productsService.get(1L)).thenReturn(Optional.of(pen));
        var expectedJson = objectMapper.writeValueAsString(pen);
        mockMvc.perform(
                get("/products/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));
    }

    private Product prepareData(Long id, List<ProductDetails> details, List<Price> prices, LocalDate createdAt) {
        var product = new Product();
        product.setId(id);
        product.setProductDetails(details);
        product.setPrices(prices);
        product.setCreatedAt(createdAt);
        return product;
    }

    @Test
    void getNotFound() throws Exception {
        when(productsService.get(1L)).thenReturn(Optional.empty());
        mockMvc.perform(
                get("/products/{id}", 1))
                .andExpect(status().isNotFound());
    }

    @Test
    void getAll() throws Exception {
        Product pen = prepareData(1L,
                List.of(new ProductDetails("en", "Pen", "Blue pen")),
                List.of(new Price("EUR", BigDecimal.valueOf(1.5))),
                LocalDate.now().minusDays(10));
        Product laptop = prepareData(2L,
                List.of(new ProductDetails("en", "Laptop", "Cool brand-new laptop")),
                List.of(new Price("EUR", BigDecimal.valueOf(1499.9))),
                LocalDate.now().minusDays(1));
        var products = List.of(pen, laptop);
        when(productsService.getAll()).thenReturn(products);
        var expectedJson = objectMapper.writeValueAsString(products);
        mockMvc.perform(
                get("/products/"))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));
    }

    @Test
    void update() throws Exception {
        Product pen = prepareData(1L,
                List.of(new ProductDetails("en", "Pen", "Blue pen")),
                List.of(new Price("EUR", BigDecimal.valueOf(1.5))), LocalDate.now().minusDays(1));
        Product updatedPen = prepareData(1L,
                List.of(new ProductDetails("en", "Pen", "Blue pen")),
                List.of(new Price("EUR", BigDecimal.valueOf(1.5))), LocalDate.now().minusDays(1));
        updatedPen.setUpdatedAt(LocalDate.now());
        when(productsService.update(1L, pen)).thenReturn(updatedPen);
        var json = objectMapper.writeValueAsString(pen);
        var expectedJson = objectMapper.writeValueAsString(updatedPen);
        mockMvc.perform(
                put("/products/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));
    }

    @Test
    void deleteById() throws Exception {
        mockMvc.perform(
                delete("/products/{id}", 1))
                .andExpect(status().isNoContent());
        verify(productsService).delete(1L);
    }
}
