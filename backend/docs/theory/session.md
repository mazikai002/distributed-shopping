## &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; Session工作原理


&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;在讨论session原理之前，我们需要考虑如下一种情形：<br>
 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;用户提交表单后，若服务器一段时间都没有反应，用户以为没有成功提交表单，于是又重复提交了一次。造成表单重复提交的可能原因有 &nbsp;&nbsp;
 1)网络延迟;  2)表单提交后又点击刷新按钮;  3)提交表单后，点后退按钮到表单页面又再次提交。 <br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;解决方法：session令牌环方法   <br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;原理如下图所示：<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
![输入图片说明](https://images.gitee.com/uploads/images/2020/1012/101131_7e45ac41_1648495.png "session原理.png")  <br>

&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;①客户端访问服务器  <br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;②服务器为客户端与服务器之间的会话创建一个session对象，同时生成唯一的sessionId，存入到session中。  <br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;③服务器将生成的sessionId返回给客户端     <br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;④以后客户端再访问服务器时，都会携带sessionId，在服务器上找到对应的session对象，服务器可根据保存的sessionId值判断是否是同一用户。若比对成功，判断为同一用户访问，服务器会认为该用户为首次提交，就处理本次请求，然后将session中的sessionId移除；不相等说明是重复提交，就不再处理。
