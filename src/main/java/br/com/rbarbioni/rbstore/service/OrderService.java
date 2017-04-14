package br.com.rbarbioni.rbstore.service;

import br.com.rbarbioni.rbstore.model.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
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

    private final HttpSession httpSession;

    @Autowired
    public OrderService(@Value("${addition.installment.count.1x}") String discount, PromoCodeService promoCodeService, MoipService moipService, ObjectMapper objectMapper, HttpSession httpSession) {
        this.moipService = moipService;
        this.objectMapper = objectMapper;
        this.discountInstallmentCount = BigDecimal.valueOf(Double.valueOf(discount != null ? discount : "0"));
        this.promoCodeService = promoCodeService;
        this.httpSession = httpSession;
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

    public PreOrderResponse calculator(PreOrderRequest preOrderRequest){

        PromoCode promoCode = this.promoCodeService.find(preOrderRequest.getCode());
        BigDecimal amount = preOrderRequest.getAmount();

        // discounts
        BigDecimal discount = BigDecimal.ZERO;
        if(promoCode != null){
            discount = preOrderRequest.applyDiscount(amount, promoCode.getDiscount());
            amount = amount.subtract(discount);        }

        // additional
        BigDecimal additional = BigDecimal.ZERO;
        if(preOrderRequest.getInstallmentCount() != null && preOrderRequest.getInstallmentCount() > 1){
            additional = preOrderRequest.applyAdditional(amount, discountInstallmentCount);
            amount = amount.add(additional);
        }

        return new PreOrderResponse(amount, discount, additional);
    }
}