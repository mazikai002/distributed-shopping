### &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; CentOS7.4环境下的Docker安装

   


1.&nbsp;目前，CentOS 仅发行版本中的内核支持 Docker。Docker 运行在 CentOS 7 上，要求系统为64位、系统内核版本为 3.10 以上。.本次Docker安装基于的是centos7.4，使用命令uname -r 查看内核版本，如下图所示。显然满足要求。<br>
&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp; ![输入图片说明](https://images.gitee.com/uploads/images/2018/1222/222551_20212fba_1648495.png "屏幕截图.png")  <br>


2.&nbsp;使用 yum 安装DockerDocker 要求CentOS系统的内核版本高于 3.10 ，查看本页面的前提条件来验证你的CentOS 版本是否支持Docker。<br>

  1)如下图，使用如下命令移除旧的版本。   <br>
&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp; ![输入图片说明](https://images.gitee.com/uploads/images/2018/1222/223345_71d6d7d7_1648495.png "屏幕截图.png")   <br>

  2)如下图，使用命令 sudo yum install -y yum-utils device-mapper-persistent-data lvm2  安装相关必要的系统工具。 <br>
&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;![输入图片说明](https://images.gitee.com/uploads/images/2018/1222/223756_7eed74f9_1648495.png "屏幕截图.png")  <br>

  3)如下图，使用命令 sudo yum-config-manager --add-repo http://mirrors.aliyun.com/docker-ce/linux/centos/docker-ce.repo  添加软件源信息。  <br>
&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp; ![输入图片说明](https://images.gitee.com/uploads/images/2018/1222/224133_c7af79e8_1648495.png "屏幕截图.png")  <br>

  4)如下图，使用命令 sudo yum makecache fast 更新 yum 缓存。 <br>
&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp; ![输入图片说明](https://images.gitee.com/uploads/images/2018/1222/224244_ce6d71fe_1648495.png "屏幕截图.png")  <br>

  5)如下图，使用命令 sudo yum -y install docker-ce 安装 Docker-ce。     <br>
&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp; ![输入图片说明](https://images.gitee.com/uploads/images/2018/1222/224405_922ac9d1_1648495.png "屏幕截图.png")  <br>
&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;  ![输入图片说明](https://images.gitee.com/uploads/images/2018/1222/224430_746ec181_1648495.png "屏幕截图.png") <br>

  
  6)使用命令 sudo systemctl start docker 启动Docker后台服务,同时获取到的docker版本。


 
