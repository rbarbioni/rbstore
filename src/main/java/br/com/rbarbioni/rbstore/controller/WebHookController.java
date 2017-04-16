package br.com.rbarbioni.rbstore.controller;

import br.com.rbarbioni.rbstore.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * Created by renan on 16/04/17.
 */

@RestController
@RequestMapping("/api/webhook")
public class WebHookController {

    private final PaymentService paymentService;

    @Autowired
    public WebHookController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @RequestMapping(method = {RequestMethod.POST})
    public void webhook(@RequestBody String data) throws IOException {
        this.paymentService.processWebHook(data);
    }
}
