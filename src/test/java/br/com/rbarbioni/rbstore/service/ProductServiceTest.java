package br.com.rbarbioni.rbstore.service;

import br.com.rbarbioni.rbstore.model.Product;
import br.com.rbarbioni.rbstore.repository.ProductRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by renan on 08/04/17.
 */

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(MockitoJUnitRunner.class)
public class ProductServiceTest {

    @InjectMocks
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @Before
    public void before() throws IOException {
        Product product = new Product(1L, "name", "image", "detail", BigDecimal.ZERO, 0, 0);
        Mockito.when(this.productRepository.findAll()).thenReturn(Arrays.asList(product));
        Mockito.when(this.productRepository.findById(1L)).thenReturn(product);
    }

    @Test
    public void findAll() throws IOException {
        Collection<Product> products = this.productService.findAll();
        Assert.assertNotNull(products);
    }

    @Test
    public void findById() throws IOException {
        Product product = this.productService.findById(1L);
        Assert.assertNotNull(product);
    }
}