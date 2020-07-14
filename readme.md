# 学生成绩管理系统

这是一个基于Android+Django+sqlit3开发的学生成绩管理系统，项目分为客户端和服务器端：
- 客户端：
    - GitHub：[https://github.com/kleinlsl/XSGManage](https://github.com/kleinlsl/XSGManage)
- 服务器：
    - Github：[https://github.com/kleinlsl/XSGM](https://github.com/kleinlsl/XSGM)

# 项目功能结构
> 项目分为三类用户：学生、教师和管理员。学生和教师通过Android进行访问，管理员通过Django-web后台管理。

![](project-file/image/项目功能结构图.png)

# 项目结构

```
D:.
├─.gradle    //编译产生，可删除后打开
├─.idea      //编译产生，可删除后打开
├─app        //项目核心代码
│  ├─build  //构建产生，若报错可删除后打开
│  ├─debug  //运行时产生app的位置
│  ├─libs   //相关Java依赖包
│  └─src
│      ├─androidTest   //安卓测试代码位置，本项目未使用可忽略
│      │  └─java
│      │      └─edu
│      │          └─edu
│      │              └─xsgmanage
│      │                  └─Utils
│      ├─main         //主要代码区域
│      │  ├─java
│      │  │  └─edu
│      │  │      └─edu
│      │  │          └─xsgmanage     
│      │  │              ├─constant    //存放静态常量的包，如：服务器地址等
│      │  │              ├─domain      //存放实体类的包
│      │  │              ├─Student     //存放学生用户的相关界面和业务
│      │  │              ├─Teacher     //存放教师用户的相关界面和业务
│      │  │              ├─Utils       //存放一些工具类
│      │  │              ├─LoginActivity.java     //登录界面
│      │  │              ├─MainActivity.java      //主界面
│      │  │              ├─MyClassActivity.java   //我的班级
│      │  │              ├─MyInfoActivity.java    //我的信息
│      │  │              ├─MyUpdataPassActivity.java  //更改密码
│      │  │              └─RegisterActivity.java  //注册
│      │  └─res         //相关的资源文件
│      │      ├─drawable  //相关的样式和图片
│      │      ├─drawable-v24
│      │      ├─layout    //app页面布局目录
│      │      ├─mipmap-anydpi-v26
│      │      ├─mipmap-hdpi
│      │      ├─mipmap-mdpi
│      │      ├─mipmap-xhdpi
│      │      ├─mipmap-xxhdpi
│      │      ├─mipmap-xxxhdpi
│      │      └─values
│      └─test       //Java单元测试代码存放位置，可在此写单元测试
│          └─java
│              └─edu
│                  └─edu
│                      └─xsgmanage
│                          └─Utils
├─gradle
│  └─wrapper
└─project-file     //存放一些项目文件，非必要
    └─doc
 注：若无法直接打开，可考虑通过新建项目将app下的核心代码拷贝至您的项目使用。

```

# 项目相关界面

> 参见此处：[here](project-file)

# 项目相关依赖
```jshelllanguage
    testImplementation 'junit:junit:4.12'     //测试依赖：单元测试
    implementation 'com.squareup.okhttp3:okhttp:3.10.0'     //okhttp：发送http请求
    implementation files('libs/gson-2.8.6.jar')         //gson：解析json数据
```
# Android开发查看sqlite数据库的方式
> 参见此处：[here](project-file/doc/see-sqlite.md)

# references.md
> 参见此处:[here](project-file/doc/references.md)
