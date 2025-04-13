## &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Mybatis基本工作原理
![输入图片说明](https://images.gitee.com/uploads/images/2018/1018/111935_4f0820ba_1648495.png "6.png")

(1)全局配置文件<br>
我们配置数据源、事务、映射关系等是为了操作数据库，然而这不是一蹴而就的，是需要步骤的。Mybatis运行首先需要读取全局配置文件缓存到Coufiguration对象，用来创建SqlSessionFactory(会话工厂)。<br>
   <br>
(2)SqlSessionFactory(会话工厂)<br>
SqlSessionFactory是通过SqlSessionFactoryBuilder去构建而成的，它是Mybatis的核心类，主要功能时提供创建Mybatis的核心接口SqlSession，我们需要创建SqlSessionFactory，为此我们提供配置文件和相关参数。<br>
   <br>
(3)SqlSession(会话)<br>
内部通过执行器操作数据库<br>
   <br>
(4)Executor(执行器)<br>
执行器是一个真正执行Java和数据交互的地方<br>
   <br>
(5)MappedStatement(底层封装对象)<br>
我们通过执行器操作数据库是需要相关信息的，如SQL语句，输入参数等，MappedStatement就提供了对这些操作数据库所需信息进行封装，最后输出结果类型。<br>
