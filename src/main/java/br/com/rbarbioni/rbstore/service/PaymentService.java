package br.com.rbarbioni.rbstore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Map;

/**
 * Created by renan on 14/04/17.
 */

@Service
public class PaymentService {

    private final MoipService moipService;

    @Autowired
    public PaymentService(MoipService moipService) {
        this.moipService = moipService;
    }

    public Map<String, Object> findStatus (String paymentId) throws IOException {
        return moipService.findStatus(paymentId);
    }

    public void processWebHook(String data){
        new Thread(new Runnable() {
            @Override
            public void run() {

            }
        });
    }
}
