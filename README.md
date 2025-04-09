# 分布式商城系统

## 项目介绍
分布式商城系统是一个基于单体架构设计的电商平台，实现了商品管理、用户管理、订单处理、秒杀活动等核心功能。系统特别注重高并发场景的处理，采用了多级缓存、异步处理、限流等多种手段来保证系统的高可用性和稳定性。

### 核心功能
- 商品管理：商品的CRUD操作，库存管理
- 用户管理：用户注册、登录、权限控制
- 订单系统：订单创建、支付流程、订单查询
- 秒杀系统：高并发秒杀功能，防止超卖
- 支付系统：对接主流支付平台，支持多种支付方式

## 技术栈

### 后端技术
- 核心框架：Spring Boot
- 数据库：MySQL（主从复制）
- 缓存：Redis（缓存）
- 消息队列：RocketMQ（异步处理）
- 搜索引擎：Elasticsearch（全文检索）
- 对象存储：MinIO（图片存储）
- ORM框架：MyBatis
- 数据库连接池：Druid
- 接口文档：Swagger
- 安全框架：Spring Security

### 中间件
- 负载均衡：Nginx
- 分布式锁：Redis + Redisson
- 限流组件：自研限流器
- 服务注册与发现：Nacos

### 监控运维
- 容器化：Docker
- 日志：ELK Stack
- 监控：Prometheus + Grafana
- 链路追踪：SkyWalking

## 项目结构说明

```
src/main/java/pers/yhf/seckill/
├── common/                    # 通用代码
│   ├── constant/             # 常量定义
│   ├── enums/                # 枚举类
│   ├── exception/            # 异常处理
│   ├── result/               # 统一返回结果
│   ├── validator/            # 参数校验
│   └── utils/                # 工具类
├── component/                # 组件
│   ├── cache/                # 缓存组件
│   │   └── redis/           # Redis缓存实现
│   ├── limiter/             # 限流组件
│   └── mq                   # 消息队列组件
│   │   └── rocketmq         # Redis缓存实现
├── config/                  # 全局配置
├── controller/              # 控制器层
├── model/                   # 数据模型
│   ├── dto/                 # 数据传输对象
│   ├── entity/              # 实体类
│   └── vo/                  # 视图对象
├── repository/              # 数据访问层
│   └── mapper/              # MyBatis Mapper接口
└── service/                 # 业务逻辑层
    └── impl/                # 接口实现
```

## 环境要求
- JDK 1.8+
- Maven 3.6+
- MySQL 5.7+
- Redis 6.0+
- RocketMQ 4.9+
- Docker 20.10+

## 部署说明
1. 数据库配置
   - 执行`sql/seckill.sql`创建数据库和表
   - 配置主从复制以提升读性能

2. Redis集群部署
   - 按照`docs/install/rediscluster.md`搭建Redis集群
   - 配置Redis持久化和主从复制

3. 消息队列配置
   - 参考`docs/install/RocketMQ.md`部署RocketMQ
   - 配置死信队列和消息持久化

4. 应用部署
   - 修改配置文件中的数据库、Redis、MQ连接信息
   - 执行`mvn clean package`打包
   - 使用Docker部署：`cd deploy && docker-compose up -d`

## 性能优化

### 系统层面
- 使用Redis集群作为缓存，减少数据库压力
- 实现数据库读写分离，提升查询性能
- 采用异步处理机制，提高系统吞吐量
- 实现分布式锁，保证数据一致性

### 秒杀场景优化
- 页面静态化，减少服务器压力
- 接口限流，防止系统崩溃
- 消息队列削峰，缓解瞬时高并发
- 库存预扣减，防止超卖

### 代码规范说明

1. **分层规范**
   - Controller层：处理请求，参数校验，返回结果
   - Service层：实现业务逻辑
   - Repository层：数据访问层，封装Mapper操作
   - Model层：数据模型定义

2. **命名规范**
   - 类名：驼峰式命名，首字母大写
   - 方法名：驼峰式命名，首字母小写
   - 变量名：驼峰式命名，首字母小写
   - 常量名：全大写，下划线分隔

3. **包结构规范**
   - common：放置通用工具类、常量、枚举等
   - component：独立功能组件
   - config：配置类
   - model：数据模型，包含entity、dto、vo等

4. **注释规范**
   - 类注释：说明类的功能、作者、日期
   - 方法注释：说明方法的功能、参数、返回值
   - 关键代码注释：说明复杂逻辑

5. **异常处理**
   - 统一使用全局异常处理
   - 自定义业务异常继承RuntimeException
   - 异常信息统一管理

6. **返回值规范**
   - 统一使用Result类封装返回结果
   - 错误码统一在CodeMsg中定义

7. **工具类规范**
   - 工具类放在common.utils包下
   - 工具类使用静态方法
   - 工具类私有构造函数

8. **配置管理**
   - 配置类统一放在config包下

9. **缓存规范**
   - 缓存组件放在component.cache下
   - 缓存key统一管理
   - 缓存策略统一配置

10. **消息队列规范**
    - 消息队列组件放在component.mq下
    - 消息体使用DTO对象
    - 消息处理使用专门的Handler类

