#### Android9.0 无法访问不到服务器也无法联网？？

[https://blog.csdn.net/Mike_Fei/article/details/89381305](https://blog.csdn.net/Mike_Fei/article/details/89381305)

#### [安卓标题栏不显示及标题栏无法隐藏问题](https://blog.csdn.net/taowuhua0505/article/details/86505436)
解决方法有两种
(1)将AppCompatActivity改为Activity，此时 requestWindowFeature(Window.FEATURE_NO_TITLE);是有效的

(2)在onCreate()方法中加入如下代码：
if (getSupportActionBar() != null){
    getSupportActionBar().hide();
}
这样就可以隐藏标题栏了

[https://blog.csdn.net/taowuhua0505/article/details/86505436](https://blog.csdn.net/taowuhua0505/article/details/86505436)

#### Android 设置APP桌面图标
[https://blog.csdn.net/zhw0596/article/details/80748884](https://blog.csdn.net/zhw0596/article/details/80748884)

#### android.view.WindowManager$BadTokenException: Unable to add window

[https://blog.csdn.net/u011038298/article/details/84104451](https://blog.csdn.net/u011038298/article/details/84104451)


#### Android之设置EditText输入类型(setInputType()方法和android:inputType属性)

[https://developer.android.google.cn/reference/android/widget/TextView.html?hl=en#attr_android:inputType](https://developer.android.google.cn/reference/android/widget/TextView.html?hl=en#attr_android:inputType)

[https://blog.csdn.net/wei_zhi/article/details/50094503](https://blog.csdn.net/wei_zhi/article/details/50094503)



#### Android Studio设置，减少对C盘空间的占用
[https://blog.csdn.net/ganmaotong/article/details/80980199](https://blog.csdn.net/ganmaotong/article/details/80980199)