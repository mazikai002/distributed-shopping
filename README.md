# 分布式商城系统

## 项目结构

```
├── controller     // 控制层
├── service       // 业务逻辑层
│   ├── interface // 接口定义
│   └── impl      // 接口实现
├── repository    // 数据访问层
├── model         // 数据模型层
│   ├── entity    // 数据库实体
│   ├── dto       // 数据传输对象
│   ├── vo        // 视图对象
│   └── param     // 请求参数对象
├── common        // 公共模块
│   ├── constant  // 常量定义
│   ├── enums     // 枚举类
│   ├── exception // 自定义异常
│   ├── result    // 统一返回结果
│   ├── utils     // 工具类
│   └── aspect    // 切面类
├── component     // 组件目录
│   ├── mq        // 消息队列组件
│   ├── storage   // 存储组件
│   ├── search    // 搜索引擎组件
│   └── config    // 配置类
├── integration   // 外部服务集成
│   ├── client    // 服务调用客户端
│   ├── config    // 服务配置
│   └── model     // 服务数据模型
└── resources     // 资源文件
    └── application.yml // 配置文件
```
  

#### 数据库
- SQL执行顺序
- MySQL索引原理

#### 设计模式
- [单例模式](https://github.com/yhf56davis/distributed-shopping/blob/master/docs/design_model/singleton.md)

#### 多线程
- sychronized关键字原理
- 线程池
- volatile关键字原理

#### Java虚拟机（JVM）
- 类加载机制
- 垃圾回收


#### 应用程序框架
- [SpringMVC基本工作原理](https://github.com/yhf56davis/distributed-shopping/blob/master/docs/framework/springMVC.md)
- Springboot启动过程
- [Mybatis基本工作原理](https://github.com/yhf56davis/distributed-shopping/blob/master/docs/theory/mybatis.md)

#### 网络技术
- Http协议原理
- TCP协议介绍
- [重定向与转发](https://github.com/yhf56davis/distributed-shopping/blob/master/docs/network/forward_redirect.md)

#### 架构设计
- [Session工作原理](https://github.com/yhf56davis/distributed-shopping/blob/master/docs/theory/session.md)
- [超卖问题处理](https://github.com/yhf56davis/distributed-shopping/blob/master/docs/oversold/oversold.md)
- [弃用JSP原因](https://github.com/yhf56davis/distributed-shopping/blob/master/docs/structure/jspleave.md)
- [Docker](https://github.com/yhf56davis/distributed-shopping/blob/master/docs/install/docker.md) <br>
-  [RabbitMQ消息队列](https://github.com/yhf56davis/distributed-shopping/blob/master/docs/install/rabbitMQ.md)   <br>
-  [Redis集群](https://github.com/yhf56davis/distributed-shopping/blob/master/docs/install/rediscluster.md)
- MySQL读写分离配置
- Tomcat优化
- SQL优化

