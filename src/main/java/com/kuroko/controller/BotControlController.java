package com.kuroko.controller;

import com.kuroko.config.BotConfig;
import com.kuroko.service.BotControlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class BotControlController {

    @Autowired
    private BotControlService botControlService;

    @GetMapping("/login/{key}")
    public void Login(@PathVariable("key") String key) {
        if (!key.equals(BotConfig.ACCESS_KEY))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized access.");
        botControlService.login();
    }

}
