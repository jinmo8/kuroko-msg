package com.kuroko.service;

import net.mamoe.mirai.Bot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BotControlService {

    @Autowired
    private Bot bot;

    public void login() {
        bot.login();
    }

}
