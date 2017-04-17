package br.com.rbarbioni.rbstore.service;

import br.com.rbarbioni.rbstore.exception.BusinessException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Map;

/**
 * Created by renan on 08/04/17.
 */

@Service
public class MoipService {

    private static final String PAYMENT_STATUS_URL = "https://sandbox.moip.com.br/v2/payments/{id}";

    private static final String CUSTOMER_URL = "https://sandbox.moip.com.br/v2/customers/{id}";

    private static final String ORDER_URL = "https://sandbox.moip.com.br/v2/orders";

    private static final String PAYMENT_URL = "https://sandbox.moip.com.br/v2/orders/{id}/payments";

    private final ObjectMapper objectMapper;

    private final String authorization;

    private final RestTemplate restTemplate;

    @Autowired
    public MoipService(ObjectMapper objectMapper, @Value("${security.moip.authorization}") String authorization) {
        this.objectMapper = objectMapper;
        this.authorization = authorization;
        this.restTemplate = new RestTemplate();
    }

    public Map<String, Object> findStatus(String moipId) throws IOException {
        return this.execute(PAYMENT_STATUS_URL.replace("{id}", moipId), HttpMethod.GET, null, Map.class);
    }

    public Map<String, Object> findCustomer(String moipId) throws IOException {
        return this.execute(CUSTOMER_URL.replace("{id}", moipId), HttpMethod.GET, null, Map.class);
    }

    public Map<String, Object> requestPaymnent(String orderId, String body) throws IOException {
        return this.execute(PAYMENT_URL.replace("{id}",  orderId), HttpMethod.POST, body, Map.class);
    }

    public Map<String, Object> requestOrder (Object body) throws IOException {
        return this.execute(ORDER_URL, HttpMethod.POST, body, Map.class);
    }

    private <T> T execute (final String url, final HttpMethod httpMethod, final Object body, final Class<T> responseBody) throws IOException {

        HttpEntity<T> request = new HttpEntity(getHeadersWithAuthorization());

        if (body != null) {
            request = new HttpEntity(body, getHeadersWithAuthorization());
        }

        ResponseEntity<String> responseEntity = this.restTemplate.exchange(url, httpMethod, request, String.class);
        if(responseEntity.getStatusCodeValue() >= 200 && responseEntity.getStatusCodeValue() <= 299){
            return this.objectMapper.readValue(responseEntity.getBody(), responseBody);
        }

        throw new BusinessException(responseEntity.getStatusCodeValue(), this.objectMapper.writeValueAsString(responseEntity.getBody()));
    }

    private HttpHeaders getHeadersWithAuthorization(){
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + this.authorization);
        headers.add("Content-Type", "application/json");
        return headers;
    }
}
