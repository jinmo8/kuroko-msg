## 声明

- kuroko-msg 基于Mirai开发，完全免费且代码开源~~（可以白嫖）~~
- 同样使用**`AGPLv3`** 开源，仅供学习和娱乐用途使用。不支持其他用途

## 一些说明

本项目是类似 Qmsg/CoolPush 的同类作品，旨在以一个简单的请求调用实现 QQ 上的消息推送。

由于它们都不开源，~~功能也不太符合自己的需求（强迫症）~~，于是自己动手撸了一个。

> 本项目仅提供推送消息相关功能，需要更多功能请直接使用 mirai 官方提供的`mirai-console`

- 项目使用mirai-core开发，可以当个Java开发Mirai的例子看看
- 使用消息队列实现的RPC
- 分离出文本消息队列，和图片队列。下载图片网络IO延时高不影响文本消息的发送
- 作者很懒。~~可能会鸽~~

## 开发者

项目大概的结构如下，感兴趣的可以看下源码，欢迎交流。~~我是菜鸡~~

![QQ截图20210310120125](https://raw.githubusercontent.com/ht-kuroko/kuroko-msg/main/images/QQ%E6%88%AA%E5%9B%BE20210310120125.png )

> 模块并没有分开，功能不多就写在一起了

## 使用

>  本项目并没有完善鉴权，请不要在公网使用

### 准备环境

- 本项目使用 Spring Boot ，请确保拥有 Java 环境（ >=1.8 ）

- 项目中使用到了 RabbitMQ，请自行搭建 RabbitMQ Server


### 配置文件

> 配置密码可以使用密码的32位MD5（大写），但是MD5的泄露任然有被盗号的风险，请谨慎保管！
>
> 请先把机器人和推送号的好友加上，~~不然你会看到一大堆异常。~~

```yml
bot-config:
  id: 123456                    # QQ号
  passwd: heeloworld            # 密码
  access-key: balabalaba        # 访问api的密码
server:
  error:
    include-stacktrace: never
  port: 3344                    # 服务器端口
spring:
  rabbitmq:
    host: 127.0.0.1             # RabbitMQ 地址
    port: 5672                  # RabbitMQ 端口
    username: guest             # RabbitMQ 账号
    password: guest             # RabbitMQ 密码
    template:
      reply-timeout: 20000      # RPC响应超时时间。默认设置为20秒
```

启动成功后大概会有如下输出：~~不要问我jar文件怎么运行~~

![QQ截图20210309210304](https://raw.githubusercontent.com/ht-kuroko/kuroko-msg/main/images/QQ%E6%88%AA%E5%9B%BE20210309210304.png )

### 登录

在配置文件中配置账号后启动程序并不会自动登录，需要手动调用接口登录。

> 需要自动登录可以自行构建，取消BotConfig.java:41的注释

#### 登录验证

通常情况下，你会遇到登录保护。可以参考 [常见登陆失败原因](mirai-dev-doc) 

如果遇到滑块验证码，推荐使用`mirai-login-solver-selenium`登录成功以后获取device.json

> 第一次登录生成会在同目录生成device.json，直接替换即可

登陆成功你会看到Mirai输出的日志，可以使用了。

![image-20210310113234883](https://raw.githubusercontent.com/ht-kuroko/kuroko-msg/main/images/image-20210310113234883.png )

### 接口说明

#### 登录

```
[GET] /login/{key}
```

这条请求可能花费~~很多时间~~，成功没有返回值；登录是否成功请检查控制输出

#### 发送文本消息

```
[POST] /send
```

| 参数名称 |  类型  | 可选  | 说明                                              |
| :------: | :----: | :---: | ------------------------------------------------- |
|    id    |  Long  | false | 消息目标好友的QQ                                  |
|   key    | String | false | 访问key                                           |
|   msg    | String | false | 消息内容。根据mirai的源码说明，最多支持4500个字符 |

#### 发送图片消息

```
[POST] /send/image
```
一次只能发一张图！！！


| 参数名称 |  类型  | 可选  | 说明                                                         |
| :------: | :----: | :---: | ------------------------------------------------------------ |
|    id    |  Long  | false | 消息目标好友的QQ                                             |
|   key    | String | false | 访问key                                                      |
|   url    | String | false | 图片地址；例：https://i.pixiv.cat/img-original/img/2018/04/24/01/51/35/68377968_p0.png |

> 发送图片不咋稳定，有Bug请重启应用

[mirai-dev-doc]:https://github.com/mamoe/mirai/blob/dev/docs/Bots.md#%E5%B8%B8%E8%A7%81%E7%99%BB%E5%BD%95%E5%A4%B1%E8%B4%A5%E5%8E%9F%E5%9B%A0

[启动成功]:https://raw.githubusercontent.com/ht-kuroko/kuroko-msg/main/images/QQ%E6%88%AA%E5%9B%BE20210309210304.png
[登陆成功]:https://raw.githubusercontent.com/ht-kuroko/kuroko-msg/main/images/image-20210310113234883.png
[架构]: https://raw.githubusercontent.com/ht-kuroko/kuroko-msg/main/images/QQ%E6%88%AA%E5%9B%BE20210310120125.png