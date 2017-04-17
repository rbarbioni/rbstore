package br.com.rbarbioni.rbstore.integration;

import br.com.rbarbioni.rbstore.model.Customer;
import br.com.rbarbioni.rbstore.model.PreOrderRequest;
import br.com.rbarbioni.rbstore.model.Product;
import br.com.rbarbioni.rbstore.service.LoginService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by renan on 17/04/17.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CriticalPathTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    private static final String ORDER = "{\"ownId\":\"0b2d664a-e93b-43be-a593-8a4073a1cd9c\",\"amount\":{\"currency\":\"BRL\",\"subtotals\":{\"shipping\":0,\"addition\":0,\"discount\":0,\"amount\":88.99}},\"items\":[{\"id\":1,\"name\":\"Jogo Xbox One DeadrRising 4\",\"image\":\"https://static.wmobjects.com.br/imgres/arquivos/ids/10190526-220-220/jogo-xbox-one-dead-rising-4.jpg\",\"detail\":\"Em Dead Rising 4, o fotojornalista Frank West está de volta em um novo capítulo da franquia\",\"price\":8899,\"ratting\":3,\"product\":\"Jogo Xbox One DeadrRising 4\",\"quantity\":1}],\"customer\":{\"id\":\"CUS-RON11BTKX4F0\",\"ownId\":\"seu_identificador_proprio_de_cliente\",\"fullname\":\"Jose Silva\",\"createdAt\":\"2016-04-14T14:46:51.000-03\",\"birthDate\":\"1988-12-30\",\"email\":\"nome@email.com\",\"fundingInstrument\":{\"creditCard\":{\"id\":\"CRC-HRULIWUUY23R\",\"brand\":\"VISA\",\"first6\":\"407302\",\"last4\":\"0002\",\"store\":true},\"method\":\"CREDIT_CARD\"},\"phone\":{\"countryCode\":\"55\",\"areaCode\":\"\",\"number\":\"66778899\"},\"taxDocument\":{\"type\":\"CPF\",\"number\":\"22222222222\"},\"_links\":{\"self\":{\"href\":\"https://sandbox.moip.com.br/v2/customers/CUS-RON11BTKX4F0\"}},\"fundingInstruments\":[{\"creditCard\":{\"id\":\"CRC-6YW3DFNJ0UMC\",\"brand\":\"VISA\",\"first6\":\"401200\",\"last4\":\"1112\",\"store\":true},\"method\":\"CREDIT_CARD\"},{\"creditCard\":{\"id\":\"CRC-T3WT6MQ7YBS1\",\"brand\":\"MASTERCARD\",\"first6\":\"555566\",\"last4\":\"8884\",\"store\":true},\"method\":\"CREDIT_CARD\"},{\"creditCard\":{\"id\":\"CRC-HRULIWUUY23R\",\"brand\":\"VISA\",\"first6\":\"407302\",\"last4\":\"0002\",\"store\":true},\"method\":\"CREDIT_CARD\"}]},\"installmentCount\":\"1\"}";
    private static final String PAYMENT = "{\"installmentCount\":\"1\",\"statementDescriptor\":\"\",\"fundingInstrument\":{\"method\":\"CREDIT_CARD\",\"creditCard\":{\"hash\":\"m6Y8NsEziJ+reNUwzN9iXGiIedERxlYHQlE39rBC0NvjXNXeG7iz1gHp6YuIXo9gMqGRoS8MdESIi0Iag9nQt0EPDvus6RwWp6pgOXYvKfmItIA08jzrwjGtGh4+DJojar7RRRzLnCZMe94dvZzGMvTyaac9FrfJgud56w505yypoqQZVeGvgHIsYVYEdJXmzpfZRiTYX7+VMqE2IcXLwBMs24tfLV0wJPWjdrfht29BvifPKmrHSNbkckoyg08wuUCgU71JpSKpoG4fa79nb+yeZkajwVoXALYl/Sxo7i6d3U7xQytxXxymQsgr9sofO2tInMtRMAeqUOWbZOBsnw==\",\"store\":true,\"holder\":{\"fullname\":\"Jose Silva\",\"birthdate\":\"1988-12-30\",\"taxDocument\":{\"type\":\"CPF\",\"number\":\"22222222222\"},\"phone\":{\"countryCode\":\"55\",\"areaCode\":\"\",\"number\":\"66778899\"}}}},\"creditCard\":{\"number\":5555666677778884,\"cvc\":\"123\",\"expirationMonth\":\"12\",\"expirationYear\":\"18\"}}";
    private static final String PAYMENT_WEBHOOK = "{\"event\":\"PAYMENT.IN_ANALYSIS\",\"resource\":{\"payment\":{\"id\":\"PAY-32LJ77AT4JNN\",\"status\":\"IN_ANALYSIS\",\"installmentCount\":1,\"amount\":{\"total\":2000,\"liquid\":1813,\"refunds\":0,\"fees\":187,\"currency\":\"BRL\"},\"fundingInstrument\":{\"method\":\"CREDIT_CARD\",\"creditCard\":{\"id\":\"CRC-BXXOA5RLGQR8\",\"holder\":{\"taxDocument\":{\"number\":\"33333333333\",\"type\":\"CPF\"},\"birthdate\":\"30/12/1988\",\"fullname\":\"Jose Portador da Silva\"},\"brand\":\"MASTERCARD\",\"first6\":\"555566\",\"last4\":\"8884\"}},\"events\":[{\"createdAt\":\"2015-03-16T18:11:19-0300\",\"type\":\"PAYMENT.IN_ANALYSIS\"},{\"createdAt\":\"2015-03-16T18:11:16-0300\",\"type\":\"PAYMENT.CREATED\"}],\"fees\":[{\"amount\":187,\"type\":\"TRANSACTION\"}],\"createdAt\":\"2015-03-16T18:11:16-0300\",\"updatedAt\":\"2015-03-16T18:11:19-0300\",\"_links\":{\"order\":{\"title\":\"ORD-SDZARE29MWVY\",\"href\":\"https://sandbox.moip.com.br/v2/orders/ORD-SDZARE29MWVY\"},\"self\":{\"href\":\"https://sandbox.moip.com.br/v2/payments/PAY-32LJ77AT4JNN\"}}}}}";

    static Product product;

    static String token;

    static Map<String, Object> customer;

    static String orderId;

    static String paymentId;

    @Test
    public void t1_findProducts() throws IOException {
        ResponseEntity<String> responseEntity = this.restTemplate.getForEntity("/api/product", String.class);
        Product[] products = this.objectMapper.readValue(responseEntity.getBody(), Product[].class);
        Assert.assertNotNull(products);
        product = products[0];
    }

    @Test
    public void t2_findProductById() throws IOException {
        ResponseEntity<String> responseEntity = this.restTemplate.getForEntity("/api/product/" + product.getId(), String.class);
        product = this.objectMapper.readValue(responseEntity.getBody(), Product.class);
        ReflectionTestUtils.setField(product, "quantity", 1);
        Assert.assertNotNull(product);
    }

    @Test
    public void t3_Login() throws IOException {

        Map<String, Object> map = new HashMap<>();
        map.put("email", "joaosilva@email.com");
        map.put("password", "testemoip");
        ResponseEntity<String> responseEntity = this.restTemplate.postForEntity("/api/login", map, String.class);
        map = this.objectMapper.readValue(responseEntity.getBody(), Map.class);
        Assert.assertNotNull(map);
        token = map.get("token").toString();
        customer = (Map<String, Object>) map.get("customer");
        Assert.assertNotNull(customer);
    }

    @Test
    public void t4_orderCalculator() throws IOException {

        PreOrderRequest preOrderRequest = new PreOrderRequest(Arrays.asList(product), null, 1, new Customer(customer.get("email").toString(), null, customer.get("id").toString()));

        HttpEntity request = new HttpEntity(preOrderRequest,getHeadersWithAuthorization());
        ResponseEntity<String> responseEntity = this.restTemplate.exchange("/secure/api/order/calculator", HttpMethod.POST, request, String.class);
        Map map = this.objectMapper.readValue(responseEntity.getBody(), Map.class);
        Assert.assertNotNull(map);
    }

    @Test
    public void t5_requestOrder() throws IOException {
        HttpEntity request = new HttpEntity(ORDER, getHeadersWithAuthorization());
        ResponseEntity<String> responseEntity = this.restTemplate.exchange("/secure/api/order", HttpMethod.POST, request, String.class);
        Map map = this.objectMapper.readValue(responseEntity.getBody(), Map.class);
        Assert.assertNotNull(map);
        orderId = map.get("id").toString();
        Assert.assertNotNull(orderId);
    }

    @Test
    public void t6_requestPayment() throws IOException {
        HttpEntity request = new HttpEntity(PAYMENT, getHeadersWithAuthorization());
        ResponseEntity<String> responseEntity = this.restTemplate.exchange("/secure/api/order/" + orderId + "/payment", HttpMethod.POST, request, String.class);
        Map map = this.objectMapper.readValue(responseEntity.getBody(), Map.class);
        Assert.assertNotNull(map);
        paymentId = map.get("id").toString();
    }

    @Test
    public void t7_paymentStatus() throws IOException {
        HttpEntity request = new HttpEntity(PAYMENT, getHeadersWithAuthorization());
        ResponseEntity<String> responseEntity = this.restTemplate.exchange("/secure/api/payment/" + paymentId, HttpMethod.GET, request, String.class);
        Map map = this.objectMapper.readValue(responseEntity.getBody(), Map.class);
        Assert.assertNotNull(map);
    }

    @Test
    public void t8_paymentWebHook() throws IOException {
        HttpEntity request = new HttpEntity(PAYMENT_WEBHOOK, getHeadersWithAuthorization());
        ResponseEntity<String> responseEntity = this.restTemplate.exchange("/api/webhook", HttpMethod.POST, request, String.class);
        Assert.assertEquals(responseEntity.getStatusCodeValue(), 200);
    }

    private HttpHeaders getHeadersWithAuthorization(){
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        headers.add("Authorization", token);
        return headers;
    }
}