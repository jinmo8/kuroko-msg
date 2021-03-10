# 声明

Mirai声明

# 一些说明

本项目是类似 Qmsg/CoolPush 的同类作品，旨在以一个简单的请求调用实现 QQ 上的消息推送。

由于它们都不开源，~~功能也不太符合自己的需求（强迫症）~~，于是自己动手撸了一个。

> 本项目仅提供推送消息相关功能，需要更多功能请直接使用 mirai 官方提供的`mirai-console`

### 有什么特点？？？

# 使用

### 准备环境

- 本项目使用 Spring Boot 构建，请确保拥有 Java 环境（ >=1.8 ）

- 项目中使用到了 RabbitMQ，请自行搭建 RabbitMQ Server

  - Exchang

    [ 图片 ]

  - Queues

    [ 图片 ]

### 配置

> 注意事项
>
> - MD5的泄露任然有被盗号的风险，请谨慎保管！
> - 请先把机器人和推送号的好友加上，~~不然你会看到一大堆异常。~~

```yml
# 机器人相关配置
bot-config:
  id: 123456789 # 机器人的QQ号
  # 支持使用密码32位的MD5登录
  # passwd: 3CB95CFBE1035BCE8C448FCAF80FE7D9
  passwd: password # 密码
  target-id: 987654321 # 绑定接收消息的QQ号
  access-key: KLuypzUIRZ5O08Va0NUG4uqb6UqCfF9Z
mq-config:
  default-exchange: bot_mq_server # MQ中的交换机名字
# 服务器相关配置
server:
  error:
    include-stacktrace: never # 堆栈信息
  port: 8080 # 访问端口
spring:
# RabbitMQ设置
  rabbitmq:
    host: 127.0.0.1
    port: 5672
    username: guest
    password: guest
```

### 懒人启动

下载文件，启动呗。。

### 构建启动

> 自己构建去