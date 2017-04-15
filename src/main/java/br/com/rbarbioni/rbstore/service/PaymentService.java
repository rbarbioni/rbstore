package br.com.rbarbioni.rbstore.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

/**
 * Created by renan on 14/04/17.
 */

@Service
public class PaymentService {

    private final ObjectMapper objectMapper;

    private final MoipService moipService;

    private final SimpMessageSendingOperations simpMessageSendingOperations;

    @Autowired
    public PaymentService(ObjectMapper objectMapper, MoipService moipService, SimpMessageSendingOperations simpMessageSendingOperations) {
        this.objectMapper = objectMapper;
        this.moipService = moipService;
        this.simpMessageSendingOperations = simpMessageSendingOperations;
    }

    public Map<String, Object> findStatus (String paymentId) throws IOException {
        return moipService.findStatus(paymentId);
    }

    public void processWebHook(final String data){
        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    Map map = objectMapper.readValue(data, Map.class);
                    Map resource = (Map) map.get("resource");
                    Map payment = (Map) resource.get("payment");

                    simpMessageSendingOperations.convertAndSend("/topic/app/payment/" + payment.get("id") + "/updates", data);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).run();
    }
}
