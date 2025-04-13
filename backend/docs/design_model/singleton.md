 

## &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;单例模式



单例模式作为Java中最简单的设计模式之一。该模式只涉及到一个单一的类提供了一种访问其唯一对象的方式，可直接访问，无需实例化该类的对象。
使用单例模式使得在内存里只存在唯一实例，减少了内存的开销，同时避免了对资源的多重占用。
以本系统为例，单例模式的UML描述如下图所示。


需要注意的是单例模式中的getInstance()方法需要使用同步机制防止多线程环境下易产生的的多次实例化问题。<br>
&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; ![输入图片说明](https://images.gitee.com/uploads/images/2018/1225/214534_6b95a286_1648495.png "屏幕截图.png")  <br>

单例模式存在几种实现方式，此处对于常见的几种方式，这里就不详述了，读者可以自行百度。重点需要介绍的是双检锁/双重校验锁方式。本系统即采用此种实现方式，该方式采用了双锁机制，且安全并能在多线程环境下保持高性能。本系统的该实现方式的实例如下代码所示。
&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;  

```
public class LoginVo{  
       //懒加载模式                                                  
	private static volatile LoginVo instance = null;       
        private LoginVo(){}                                    
	 public static LoginVo getInstance(){                  
		if(instance == null) createInstance();         
		return instance;                               
	}                                                         
    private synchronized static LoginVo createInstance() {       
		if(instance == null) instance = new LoginVo();    
		return instance;                       
	}                                        
}                 
```
                  



