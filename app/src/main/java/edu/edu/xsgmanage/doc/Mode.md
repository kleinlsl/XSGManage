# Factory-material-management
一个较为全面的工厂物资管理系统，其中包含人脸识别模块


## 工厂物资管理系统使用手册


#### 配置源程序

##### 附加SQLServer2008数据库
	
	（1）将Database文件夹中的数据库文件拷贝到本地机器中。
	
	（2）打开SQLServer2008数据库管理器，展开“数据库”节点，点击鼠标右键选择“附加”，即可打开附加数据库窗体，单击该窗体中的“附加”按钮，打开选择附加文件对话框，选择本项目数据库地址选择db_factory.MDF文件。单击“确定”按钮。即可完成数据库的附加。


#### 配置运行环境
	
	一、使用Visual Studio2017配置：
	
	（1）使用Visual Studio2017打开项目文件。
	
	（2）打开解决方案资源管理器，打开其中的App.config文件。修改其中的Data Source配置信息为本地数据库服务器的名称。
	
![01.png](https://github.com/kleinlsl/Factory-material-management/blob/master/image/doc/01.png)
 
	（3）点击Visual Studio2017的运行按钮即可运行。

	二、不使用Visual Studio2017配置：
	
	（1）打开项目文件夹 {项目路径}\工厂物资管理\bin\Debug，打开其中以下文件
![02.png](https://github.com/kleinlsl/Factory-material-management/blob/master/image/doc/02.png)
	
	（2）修改其中的Data Source配置信息为本地数据库服务器的名称或远程数据库服务器的名称。
![03.png](https://github.com/kleinlsl/Factory-material-management/blob/master/image/doc/01.png)
	
	（3）双击该目录下的以下应用即可完成配置：
 
#### 使用说明

##### 系统用户表

	用户ID	密码	所具有的权限
			系统管理	入库管理	出库管理	物资管理
	Test001	Test001	是	否	否	否
	Test002	Test002	否	是	否	否
	Test003	Test003	否	否	是	否
	Test004	Test004	否	否	否	是
	admin	admin	是	是	是	是



##### 系统功能结构

![04.png](https://github.com/kleinlsl/Factory-material-management/blob/master/image/doc/03.png)
 
##### 操作注意事项
	1.	使用本系统需要先将配置源程序部分完成，否则将无法使用。
	2.	本系统部分账户信息见系统用户表：其中存在超级管理员admin。
	3.	使用本系统的人脸识别功能，需连接网络。否则，将会导致失败。
	4.	请勿更改系统的其它配置信息，否则可能出现未知的错误。
	5.	若需要进行远程数据库配置，请保证以下几点：
		a)	保证sqlserver 服务器允许远程登陆
		b)	保证sqlserver 服务器远程登陆端口开启
		c)	若失败请关闭Windows防火墙后重新尝试
		d)	请确保远程服务器名称书写正确：ip，端口号。Eg：192.168.1.101,8080



## PPT展示
![02.png](https://github.com/kleinlsl/Factory-material-management/blob/master/image/ppt/%E5%B9%BB%E7%81%AF%E7%89%871.JPG)
![02.png](https://github.com/kleinlsl/Factory-material-management/blob/master/image/ppt/%E5%B9%BB%E7%81%AF%E7%89%872.JPG)
![02.png](https://github.com/kleinlsl/Factory-material-management/blob/master/image/ppt/%E5%B9%BB%E7%81%AF%E7%89%873.JPG)
![02.png](https://github.com/kleinlsl/Factory-material-management/blob/master/image/ppt/%E5%B9%BB%E7%81%AF%E7%89%874.JPG)
![02.png](https://github.com/kleinlsl/Factory-material-management/blob/master/image/ppt/%E5%B9%BB%E7%81%AF%E7%89%875.JPG)
![02.png](https://github.com/kleinlsl/Factory-material-management/blob/master/image/ppt/%E5%B9%BB%E7%81%AF%E7%89%876.JPG)
![02.png](https://github.com/kleinlsl/Factory-material-management/blob/master/image/ppt/%E5%B9%BB%E7%81%AF%E7%89%877.JPG)
![02.png](https://github.com/kleinlsl/Factory-material-management/blob/master/image/ppt/%E5%B9%BB%E7%81%AF%E7%89%878.JPG)
![02.png](https://github.com/kleinlsl/Factory-material-management/blob/master/image/ppt/%E5%B9%BB%E7%81%AF%E7%89%879.JPG)
![02.png](https://github.com/kleinlsl/Factory-material-management/blob/master/image/ppt/%E5%B9%BB%E7%81%AF%E7%89%8710.JPG)
![02.png](https://github.com/kleinlsl/Factory-material-management/blob/master/image/ppt/%E5%B9%BB%E7%81%AF%E7%89%8711.JPG)
![02.png](https://github.com/kleinlsl/Factory-material-management/blob/master/image/ppt/%E5%B9%BB%E7%81%AF%E7%89%8712.JPG)
![02.png](https://github.com/kleinlsl/Factory-material-management/blob/master/image/ppt/%E5%B9%BB%E7%81%AF%E7%89%8713.JPG)
![02.png](https://github.com/kleinlsl/Factory-material-management/blob/master/image/ppt/%E5%B9%BB%E7%81%AF%E7%89%8714.JPG)
![02.png](https://github.com/kleinlsl/Factory-material-management/blob/master/image/ppt/%E5%B9%BB%E7%81%AF%E7%89%8715.JPG)