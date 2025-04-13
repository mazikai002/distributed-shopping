## &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;   SpringMVC基本工作原理

SpringMVC的基本工作原理如下图所示。  <br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;   ![输入图片说明](https://images.gitee.com/uploads/images/2018/1225/232921_be99f333_1648495.png "屏幕截图.png")   <br>
图中各数字代码表示的过程如下：  <br>
1.用户发送请求到前端控制器DispatchServlet    <br>
2.前端控制器收到请求调用HandlerMapping处理器映射器      <br>
3.处理器映射器找到具体的处理器，生成处理器对象以及处理器拦截器，一并返回给前端控制器  <br>
4.前端控制器调用HandlerAdapter处理器适配器    <br>
5.HandlerAdapter经过适配调用具体的处理器（Controller）       <br>
6.处理器Controller执行完成后返回 ModelAndView            <br>
7.HandlerAdapter将Controller执行结果ModelAndView返回给前端控制器    <br>
8.前端控制器将ModelAndView传给 ViewResolver视图解析器       <br>
9.ViewResolver解析后返回具体的View         <br>
10.前端控制器根据View进行渲染视图（也即将模型数据填充至视图中）   <br>
11.前端控制器响应用户
