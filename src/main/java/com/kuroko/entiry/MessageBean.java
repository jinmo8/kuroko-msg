package com.kuroko.entiry;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.net.URL;

@Data
public class MessageBean implements Serializable {

    private long receiverId;
    private String type; // 未实现
    private String plainText;
    private URL imageUrl;

    public static MessageBean create(long receiverId, String type, String plainText) {
        MessageBean messageBean = new MessageBean();
        messageBean.setReceiverId(receiverId);
        messageBean.setType(type);
        messageBean.setPlainText(plainText);
        return messageBean;
    }

    public static MessageBean create(long receiverId, String type, URL url) {
        MessageBean messageBean = new MessageBean();
        messageBean.setReceiverId(receiverId);
        messageBean.setType(type);
        messageBean.setImageUrl(url);
        return messageBean;
    }

}
