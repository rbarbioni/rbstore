package br.com.rbarbioni.rbstore.service;

import br.com.rbarbioni.rbstore.model.CheckoutDiscount;
import br.com.rbarbioni.rbstore.model.CheckoutRequest;
import br.com.rbarbioni.rbstore.model.Product;
import br.com.rbarbioni.rbstore.model.PromoCode;
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
public class CheckoutServiceTest {


    @InjectMocks
    private CheckoutService checkoutService;

    @Mock
    private PromoCodeService promoCodeService;

    @Before
    public void before(){
        ReflectionTestUtils.setField(checkoutService, "discountInstallmentCount", BigDecimal.valueOf(0.025));
    }

    @Test
    public void calcApplyingAllDiscountsTest(){

        Mockito.when(promoCodeService.find(Matchers.anyString())).thenReturn(new PromoCode("Promo 1", "11231223", BigDecimal.valueOf(0.05)));

        Product product = new Product(1L, "", "", "", BigDecimal.valueOf(100), 1);
        CheckoutRequest checkoutRequest = new CheckoutRequest(Arrays.asList(product), "", 1);
        CheckoutDiscount checkoutDiscount = checkoutService.calc(checkoutRequest);
        Assert.assertTrue(92.625 == checkoutDiscount.getAmmount().doubleValue());
        Assert.assertTrue((100.00-92.625) == checkoutDiscount.getDiscount().doubleValue() );
    }

    @Test
    public void calcApplyingPromoCodeTest(){
        ReflectionTestUtils.setField(checkoutService, "discountInstallmentCount", BigDecimal.valueOf(0));
        Mockito.when(promoCodeService.find(Matchers.anyString())).thenReturn(new PromoCode("Promo 1", "11231223", BigDecimal.valueOf(0.05)));

        Product product = new Product(1L, "", "", "", BigDecimal.valueOf(100), 1);
        CheckoutRequest checkoutRequest = new CheckoutRequest(Arrays.asList(product), "", 1);
        CheckoutDiscount checkoutDiscount = checkoutService.calc(checkoutRequest);
        Assert.assertTrue(95.00 == checkoutDiscount.getAmmount().doubleValue());
        Assert.assertTrue((100.00-95.00) == checkoutDiscount.getDiscount().doubleValue());
    }

    @Test
    public void calcApplyingInstallmentCountTest(){
        Mockito.when(promoCodeService.find(Matchers.anyString())).thenReturn(null);
        Product product = new Product(1L, "", "", "", BigDecimal.valueOf(100), 1);
        CheckoutRequest checkoutRequest = new CheckoutRequest(Arrays.asList(product), "", 1);
        CheckoutDiscount checkoutDiscount = checkoutService.calc(checkoutRequest);
        Assert.assertTrue(97.50 == checkoutDiscount.getAmmount().doubleValue());
        Assert.assertTrue((100.00-97.50) == checkoutDiscount.getDiscount().doubleValue());
    }

    @Test
    public void calcWithoutDiscountTest(){
        Mockito.when(promoCodeService.find(Matchers.anyString())).thenReturn(null);
        ReflectionTestUtils.setField(checkoutService, "discountInstallmentCount", BigDecimal.valueOf(0));
        Product product = new Product(1L, "", "", "", BigDecimal.valueOf(100), 1);
        CheckoutRequest checkoutRequest = new CheckoutRequest(Arrays.asList(product), "", 1);
        CheckoutDiscount checkoutDiscount = checkoutService.calc(checkoutRequest);
        Assert.assertTrue(100.00 == checkoutDiscount.getAmmount().doubleValue());
        Assert.assertTrue(0.0 == checkoutDiscount.getDiscount().doubleValue());
    }
}
