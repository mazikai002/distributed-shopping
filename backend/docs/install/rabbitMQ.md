## &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;   RabbitMQ消息队列

### 一，RabbitMQ安装  <br>
1.下载最新版本的erlang到文件夹opt中，当前版本是20.2      <br>
 
2.使用命令tar -zxvf otp_src_20.2.tar.gz解压下载好的文件    <br>
  
3.编译安装Erlang对环境有要求，为防止在编译的时候提示某些软件包未安装之类的错误，所以我将Erlang需要的软件提前安装，直接使用yum进行安装即可。依次执行如下命令: <br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
yum install ncurses ncurses-base ncurses-devel ncurses-libs ncurses-static ncurses-term ocaml-curses ocaml-curses-devel -y
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;yum install openssl-devel zlib-devel –y   <br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;yum -y install make ncurses-devel gcc gcc-c++ unixODBC unixODBC-devel openssl openssl-devel  <br>

4.编译安装Erlang。首先切换到解压好的目录里面去，然后再configure 。prefix后指定的是安装目录，此处将Erlang安装到/usr/local/opt/erlang
  目录下。使用如下命令：   <br>
./configure --prefix=/usr/local/opt/erlang --with-ssl -enable-threads -enable-smmp-support -enable-kernel-poll --enable-hipe --without-javac   <br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;![输入图片说明](https://images.gitee.com/uploads/images/2018/1220/112437_b87af235_1648495.png "屏幕截图.png")  <br>
   编译完成后的图片如下图所示：  <br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;![输入图片说明](https://images.gitee.com/uploads/images/2018/1220/113248_12e8d079_1648495.png "屏幕截图.png")  <br>
接下来执行make&&make install命令, 安装完成后，执行命令 ln -s /usr/local/opt/erlang/bin/erl /usr/local/bin/erl 制作软连接  <br>
 
测试Erlang的安装是否成功：如下图所示，Erlang已经安装成功。<br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; ![输入图片说明](https://images.gitee.com/uploads/images/2018/1220/113522_a8c5404e_1648495.png "屏幕截图.png")   <br>
执行命令vi /etc/profile，修改环境变量,添加如下图内容。 <br> 
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;![输入图片说明](https://images.gitee.com/uploads/images/2018/1220/114032_ddc7ce30_1648495.png "屏幕截图.png")<br>
执行命令source /etc/profile 使环境变量立即生效。 <br>

5.下载最新版的rabbitmq，当前最新版本为3.6.10，如下图所示。 <br>
 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; ![输入图片说明](https://images.gitee.com/uploads/images/2018/1220/114237_bb8c4a37_1648495.png "屏幕截图.png")  <br>

使用命令yum -y install xz，安装xz解压软件,解压rabbitMQ，将解压好的文件移动到安装目录，如下图所示。 <br>
 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; ![输入图片说明](https://images.gitee.com/uploads/images/2018/1220/114730_a50caf2e_1648495.png "屏幕截图.png")   <br>
进入安装目录的sbin子目录，启动rabbitMQ，如下图所示  <br>
 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; ![输入图片说明](https://images.gitee.com/uploads/images/2018/1220/114829_1877f575_1648495.png "屏幕截图.png")   <br>

6.创建用户admin 密码admin  如下图所示。  <br>
 &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; ![输入图片说明](https://images.gitee.com/uploads/images/2018/1220/115145_e8302af8_1648495.png "屏幕截图.png")     <br>
  为用户admin分配权限， 如下图所示。   <br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; ![输入图片说明](https://images.gitee.com/uploads/images/2018/1220/115227_4db7e810_1648495.png "屏幕截图.png")     <br>

7.开启插件管理页面，如下图所示。  <br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;![输入图片说明](https://images.gitee.com/uploads/images/2018/1220/115310_d3caee70_1648495.png "屏幕截图.png")    <br>
浏览器输入IP地址，与端口号15672, 然后在输入地址进入管理页面     <br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; ![输入图片说明](https://images.gitee.com/uploads/images/2018/1220/115355_4a8b1e42_1648495.png "屏幕截图.png")    <br>

### 二，RabbitMQ消息队列模式

