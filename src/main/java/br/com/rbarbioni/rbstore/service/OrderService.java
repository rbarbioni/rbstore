package br.com.rbarbioni.rbstore.service;

import br.com.rbarbioni.rbstore.model.CheckoutDiscount;
import br.com.rbarbioni.rbstore.model.OrderRequest;
import br.com.rbarbioni.rbstore.model.PromoCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;

/**
 * Created by renan on 08/04/17.
 */

@Service
public class OrderService {

    private final PromoCodeService promoCodeService;

    private final BigDecimal discountInstallmentCount;

    private final MoipService moipService;

    @Autowired
    public OrderService(@Value("${discount.installment.count.1x}") String discount, PromoCodeService promoCodeService, MoipService moipService) {
        this.moipService = moipService;
        this.discountInstallmentCount = BigDecimal.valueOf(Double.valueOf(discount != null ? discount : "0"));
        this.promoCodeService = promoCodeService;
    }

    public Map<String, Object> order (String body) throws IOException {
        return this.moipService.requestOrder(body);
    }

    public Map<String, Object> payment (String orderId, String body) throws IOException {

//        final Customer customer = this.customerRepository.findByEmail("joaosilva@email.com");
//        MoipPaymentRequest moipPaymentRequest = new MoipPaymentRequest(1, new FundingInstrument(PaymentType.CREDIT_CARD, new CreditCard(12, 25, "5555666677778884", "123", customer)));
        return this.moipService.requestPaymnent(orderId, body);
}

    public CheckoutDiscount calc (OrderRequest orderRequest){

        PromoCode promoCode = this.promoCodeService.find(orderRequest.getCode());

        BigDecimal bigDecimal = orderRequest.getAmmount();
        if(promoCode != null){
            bigDecimal = orderRequest.applyDiscount(bigDecimal, promoCode.getDiscount());
        }

        if(orderRequest.getInstallmentCount() != null && 1 == orderRequest.getInstallmentCount()){
            bigDecimal = orderRequest.applyDiscount(bigDecimal, discountInstallmentCount);
        }

        return new CheckoutDiscount(bigDecimal, orderRequest.getAmmount().subtract(bigDecimal));
    }
}