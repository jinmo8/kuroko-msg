package com.kuroko.service;


import com.kuroko.entiry.MessageBean;
import net.mamoe.mirai.Bot;
import net.mamoe.mirai.contact.Friend;
import net.mamoe.mirai.message.data.Image;
import net.mamoe.mirai.utils.ExternalResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;

@Service
public class BotMessageService {

    private final Logger LOG = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private Bot bot;

    @RabbitListener(queues = "qq.message.rpc.plaintext")
    // @SendTo(value = "rpc_recall")
    // 使用直接回复 amq.rabbitmq.reply-to
    public String sendPlainText(MessageBean bean) {
        if (this.isBotOnline()) {
            Friend targetFriend = this.getFriend(bean.getReceiverId());
            if (targetFriend == null) {
                LOG.info("Can not get object[net.mamoe.mirai.contact.Friend]. No such Friend");
                return "Message dropped. No such friend.";
            } else {
                targetFriend.sendMessage(bean.getPlainText());
                return "Message sent... (may be)";
            }

        } else {
            // System.out.println("Message dropped. bot is offline. received message is: " +msg.getMessageContent());
            return "Message dropped. bot is offline.";
        }
    }

    @RabbitListener(queues = "qq.message.rpc.image")
    public String sendImage(MessageBean bean) {
        if (this.isBotOnline()) {
            Friend targetFriend = this.getFriend(bean.getReceiverId());
            if (targetFriend == null) {
                LOG.info("Can not get object[net.mamoe.mirai.contact.Friend]. No such Friend");
                return "Message dropped. No such friend.";
            } else {
                try (InputStream inputStream = bean.getImageUrl().openStream(); ExternalResource externalResource = ExternalResource.create(inputStream)) {
                    Image image = targetFriend.uploadImage(externalResource);
                    targetFriend.sendMessage(image);
                } catch (IOException e) {
                    LOG.info("image IO error.");
                    //e.printStackTrace();
                    return "Image message dropped. Unable to download Image.";
                }
            }
            return "Image message sent... (may be)";

        } else {
            return "Image message dropped.bot is offline.";
        }

    }


    private boolean isBotOnline() {
        boolean botIsOnLine = false;
        try {
            botIsOnLine = bot.isOnline();
        } catch (Exception e) {
            // 异常原因:机器人没登录，却调用了isOnline(),Kotlin有懒加载的东西没有初始化
            // System.out.println("bot is offline.");
            // e.printStackTrace();
            LOG.info("bot is offline.");
        }
        return botIsOnLine;
    }

    // 如果获取不到会有空指针异常，注意处理
    private Friend getFriend(long id) {
        return bot.getFriend(id);
    }

}
