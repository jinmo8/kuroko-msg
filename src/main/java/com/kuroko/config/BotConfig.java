package com.kuroko.config;

import com.kuroko.util.MD5Encrypt;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.BotFactory;
import net.mamoe.mirai.utils.BotConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class BotConfig {

    public static String ACCESS_KEY;



    @Value("${bot-config.id}")
    private long id;

    @Value("${bot-config.passwd}")
    private String passwd;

    private static final Logger LOG = LoggerFactory.getLogger(BotConfig.class);


    @Bean
    public Bot myBotConfig() {
        LOG.info("configured id is: {}", id);
        LOG.info("configured password is: {}", passwd);

        Bot bot = BotFactory.INSTANCE.newBot(id, MD5Encrypt.encryptMd5(passwd), new BotConfiguration() {
            {
                setHighwayUploadCoroutineCount(16);
                fileBasedDeviceInfo();
            }
        });
        // bot.login();
        return bot;
    }

    // 常量初始化
    @Value("${bot-config.access-key}")
    public void setAccessKey(String accessKey) {
        BotConfig.ACCESS_KEY = accessKey;
    }




}
