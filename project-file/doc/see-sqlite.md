# Android开发查看sqlite数据库的方式


免去了每次都导出db文件到本地然后再用工具查看的繁琐。

## 注：
    
    以下命令应在安卓shell命令下输入
###### 打开安卓shell
    
    在 cmd 命令中进去{SDK_PATH}\SDK\platform-tools
    输入：adb shell   #进入
    
    安卓Linux命令：
        cd      #打开目录 
        ls      #浏览目录下文件
        getprop  #获取模拟器系统属性
        
        
        
###### 更改权限
    D:\Program Files\Android\SDK\platform-tools>adb shell  #进入安卓Linux命令窗口
    generic_x86:/ $ su root                                #更改为root用户

## 方法：
###### 一、在Path里面配置adb环境变量

       D:\Program Files\Android\SDK\platform-tools   
    或 直接进入该目录

###### 二、使用命令进入SqLite命令模式

    adb shell       #进入命令行
    cd /data/data/{ package_name }/databases/     #进入对应程序得到databases文件夹下；该目录下存放sqlite数据库文件
    
    sqlite3 something.db                   #打开数据库为something.db的数据库     


###### 三、查看表结构以及其它命令

> > - .tables //显示所有表
> > - .schema //显示数据库的schema
> > 
> > - .schema table_name //显示表的schema
> > 
> > - .headers on //显示标题栏，即字段名栏，如在查看数据中数据时，默认select * from table_name 不显示字段名。
> > 
> > - alter table //修改表。改变表名 - ALTER TABLE 旧表名 RENAME TO 新表名；增加一列 - ALTER TABLE 表名 ADD COLUMN 列名 数据类型 限定符
> > 
> > - select * from sqlite_master where type="table"; //显示所有表的结构
> > 
> > - select * from sqlite_master where type="table" and name="table_name"; //显示某个表的结构
> > 
> > - drop table table_name //删除表
> > 
> > - .quit //退出
> > 
> > - .read FileName //执行FileName中的sql


######  四、查找数据
select * from tablename; 



--------------------- 
作者：silence__star 
来源：CSDN
原文： [https://blog.csdn.net/silence__star/article/details/79889375](https://blog.csdn.net/silence__star/article/details/79889375)