package br.com.rbarbioni.rbstore.service;

import br.com.rbarbioni.rbstore.model.CheckoutDiscount;
import br.com.rbarbioni.rbstore.model.OrderRequest;
import br.com.rbarbioni.rbstore.model.PromoCode;
import br.com.rbarbioni.rbstore.model.Subtotals;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by renan on 08/04/17.
 */

@Service
public class OrderService {

    private final PromoCodeService promoCodeService;

    private final BigDecimal discountInstallmentCount;

    private final MoipService moipService;

    private final ObjectMapper objectMapper;

    @Autowired
    public OrderService(@Value("${addition.installment.count.1x}") String discount, PromoCodeService promoCodeService, MoipService moipService, ObjectMapper objectMapper) {
        this.moipService = moipService;
        this.objectMapper = objectMapper;
        this.discountInstallmentCount = BigDecimal.valueOf(Double.valueOf(discount != null ? discount : "0"));
        this.promoCodeService = promoCodeService;
    }

    public Map<String, Object> order(String body) throws IOException {

        Map<String, Object> map = this.objectMapper.readValue(body, Map.class);
        HashMap<String, Object> subtotals = new HashMap<>();
        subtotals.put("subtotals", new Subtotals(BigDecimal.valueOf(100), BigDecimal.valueOf(50)));
        map.put("amount", subtotals);

        return this.moipService.requestOrder(map);
    }

    public Map<String, Object> payment (String orderId, String body) throws IOException {
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