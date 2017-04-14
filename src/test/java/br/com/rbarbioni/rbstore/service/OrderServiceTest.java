package br.com.rbarbioni.rbstore.service;

import br.com.rbarbioni.rbstore.model.*;
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

import java.math.BigDecimal;
import java.util.Arrays;

/**
 * Created by renan on 08/04/17.
 */

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(MockitoJUnitRunner.class)
public class OrderServiceTest {


    @InjectMocks
    private OrderService orderService;

    @Mock
    private PromoCodeService promoCodeService;

    private Customer customer = new Customer("joaosilva@email.com", "testemoip", "CUS-12345678901");

    @Before
    public void before(){
        ReflectionTestUtils.setField(orderService, "discountInstallmentCount", BigDecimal.valueOf(0.025));
    }

    @Test
    public void calcApplyingAllDiscountsTest(){

        Mockito.when(promoCodeService.find(Matchers.anyString())).thenReturn(new PromoCode("Promo 1", "11231223", BigDecimal.valueOf(0.05)));

        Product product = new Product(1L, "", "", "", BigDecimal.valueOf(100), 1, 1);
        PreOrderRequest preOrderRequest = new PreOrderRequest(Arrays.asList(product), "", 1, customer);
        PreOrderResponse preOrderResponse = orderService.calculator(preOrderRequest);
        Assert.assertTrue(92.625 == preOrderResponse.getAmount().doubleValue());
        Assert.assertTrue((100.00-92.625) == preOrderResponse.getDiscount().doubleValue() );
    }

    @Test
    public void calcApplyingPromoCodeTest(){
        ReflectionTestUtils.setField(orderService, "discountInstallmentCount", BigDecimal.valueOf(0));
        Mockito.when(promoCodeService.find(Matchers.anyString())).thenReturn(new PromoCode("Promo 1", "11231223", BigDecimal.valueOf(0.05)));

        Product product = new Product(1L, "", "", "", BigDecimal.valueOf(100), 1, 1);
        PreOrderRequest preOrderRequest = new PreOrderRequest(Arrays.asList(product), "", 1, customer);
        PreOrderResponse preOrderResponse = orderService.calculator(preOrderRequest);
        Assert.assertTrue(95.00 == preOrderResponse.getAmount().doubleValue());
        Assert.assertTrue((100.00-95.00) == preOrderResponse.getDiscount().doubleValue());
    }

    @Test
    public void calcApplyingInstallmentCountTest(){
        Mockito.when(promoCodeService.find(Matchers.anyString())).thenReturn(null);
        Product product = new Product(1L, "", "", "", BigDecimal.valueOf(100), 1, 1);
        PreOrderRequest preOrderRequest = new PreOrderRequest(Arrays.asList(product), "", 1, customer);
        PreOrderResponse preOrderResponse = orderService.calculator(preOrderRequest);
        Assert.assertTrue(97.50 == preOrderResponse.getAmount().doubleValue());
        Assert.assertTrue((100.00-97.50) == preOrderResponse.getDiscount().doubleValue());
    }

    @Test
    public void calcWithoutDiscountTest(){
        Mockito.when(promoCodeService.find(Matchers.anyString())).thenReturn(null);
        ReflectionTestUtils.setField(orderService, "discountInstallmentCount", BigDecimal.valueOf(0));
        Product product = new Product(1L, "", "", "", BigDecimal.valueOf(100), 1, 1);
        PreOrderRequest preOrderRequest = new PreOrderRequest(Arrays.asList(product), "", 1, customer);
        PreOrderResponse preOrderResponse = orderService.calculator(preOrderRequest);
        Assert.assertTrue(100.00 == preOrderResponse.getAmount().doubleValue());
        Assert.assertTrue(0.0 == preOrderResponse.getDiscount().doubleValue());
    }

    @Test
    public void calcWithoutDiscountManyProductsMultiQuantityTest(){
        Mockito.when(promoCodeService.find(Matchers.anyString())).thenReturn(null);
        ReflectionTestUtils.setField(orderService, "discountInstallmentCount", BigDecimal.valueOf(0));
        Product product1 = new Product(1L, "", "", "", BigDecimal.valueOf(150), 1, 2);
        Product product2 = new Product(1L, "", "", "", BigDecimal.valueOf(150), 1, 3);
        PreOrderRequest preOrderRequest = new PreOrderRequest(Arrays.asList(product1, product2), "", 1, customer);
        PreOrderResponse preOrderResponse = orderService.calculator(preOrderRequest);
        Assert.assertTrue(750.00 == preOrderResponse.getAmount().doubleValue());
        Assert.assertTrue(0.0 == preOrderResponse.getDiscount().doubleValue());
    }
}