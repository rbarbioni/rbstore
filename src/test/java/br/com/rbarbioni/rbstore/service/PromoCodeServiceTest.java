package br.com.rbarbioni.rbstore.service;

import br.com.rbarbioni.rbstore.model.PromoCode;
import br.com.rbarbioni.rbstore.repository.PromoCodeRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.mockito.InjectMocks;
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
public class PromoCodeServiceTest {

    @InjectMocks
    private PromoCodeService promoCodeService;

    @Mock
    private PromoCodeRepository promoCodeRepository;

    @Before
    public void before(){
        ReflectionTestUtils.setField(promoCodeService, "promoCodeRepository", promoCodeRepository);
        PromoCode promoCode = new PromoCode("Promo", "123", BigDecimal.valueOf(0.5));
        Mockito.when(promoCodeRepository.findAll()).thenReturn(Arrays.asList(promoCode));
        Mockito.when(promoCodeRepository.findById("123")).thenReturn(promoCode);
    }

    @Test
    public void findByCode(){
        PromoCode promoCode = this.promoCodeService.find("123");
        Assert.assertNotNull(promoCode);
    }

    @Test
    public void findByCodeInvalid(){
        PromoCode promoCode = this.promoCodeService.find("1234");
        Assert.assertNull(promoCode);
    }
}