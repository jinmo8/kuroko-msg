package com.kuroko.controller;

import com.kuroko.config.BotConfig;
import com.kuroko.config.RabbitConfig;
import com.kuroko.entiry.MessageBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.net.MalformedURLException;
import java.net.URL;

@RestController
public class MessageController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());


    @PostMapping("/send")
    public String sendPlainText(
            @RequestParam() String key,
            @RequestParam() String msg,
            @RequestParam(value = "id") String receiverIdStr) {

        if (!this.isAuthorized(key)) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized access.");
        long id = verifyReceiverId(receiverIdStr);
        Object result = rabbitTemplate.convertSendAndReceive(RabbitConfig.DEFAULT_EXCHANGE, RabbitConfig.ROUTING_PLAIN_TEXT, MessageBean.create(id, "plain", msg));
        return String.valueOf(result);
    }

    @PostMapping("/send/image")
    public String sendImage(
            @RequestParam() String key,
            @RequestParam(value = "id") String receiverIdStr,
            @RequestParam(value = "url") String urlStr) {

        if (!this.isAuthorized(key)) throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized access.");
        long id = verifyReceiverId(receiverIdStr);
        URL url = this.convertURL(urlStr);
        // rabbitTemplate.setReplyTimeout(20000);
        Object result = rabbitTemplate.convertSendAndReceive(RabbitConfig.DEFAULT_EXCHANGE, RabbitConfig.ROUTING_IMAGE, MessageBean.create(id, "image", url));
        if(result == null)  return "RPC failed";
        return String.valueOf(result);
    }

    private boolean isAuthorized(String key) {
        return key.equals(BotConfig.ACCESS_KEY);
    }

    private long verifyReceiverId(String id) {
        long result = 0;
        try {
            result = Long.valueOf(id);
        } catch (NumberFormatException e) {
            // e.printStackTrace();
            LOG.info("Unable to convert id.");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unable to convert id.");
        }
        return result;
    }

    private URL convertURL(String urlStr) {
        URL url = null;
        try {
            url = new URL(urlStr);
        } catch (MalformedURLException e) {
            //e.printStackTrace();
            LOG.info("Unable to convert URL.");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unable to convert URL.");
        }
        return url;
    }
}
