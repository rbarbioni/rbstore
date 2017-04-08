package br.com.rbarbioni.rbstore.service;

import br.com.rbarbioni.rbstore.model.CheckoutDiscount;
import br.com.rbarbioni.rbstore.model.CheckoutRequest;
import br.com.rbarbioni.rbstore.model.PromoCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * Created by renan on 08/04/17.
 */

@Service
public class CheckoutService {

    private final PromoCodeService promoCodeService;

    private final BigDecimal discountInstallmentCount;

    @Autowired
    public CheckoutService(PromoCodeService promoCodeService, @Value("discount.installment.count.1x") String discount) {
        this.promoCodeService = promoCodeService;
        this.discountInstallmentCount = BigDecimal.valueOf(Double.valueOf(discount != null ? discount : "0"));
    }

    public CheckoutDiscount calc (CheckoutRequest checkoutRequest){

        PromoCode promoCode = this.promoCodeService.find(checkoutRequest.getCupom());

        BigDecimal bigDecimal = checkoutRequest.getAmmount();
        if(promoCode != null){
            bigDecimal = checkoutRequest.applyDiscount(bigDecimal, promoCode.getDiscount());
        }

        if(checkoutRequest.getInstallmentCount() == 1){
            bigDecimal = checkoutRequest.applyDiscount(bigDecimal, discountInstallmentCount);
        }

        return new CheckoutDiscount(bigDecimal, checkoutRequest.getAmmount().subtract(bigDecimal));
    }
}