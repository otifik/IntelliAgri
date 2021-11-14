功能:
    1.包含了UI库,使用便捷; 各种UI库 ; 广播管理, 数据库管理 ,网络管理
    2.可使用高德地图定位  ----
       蒲公英进行在线升级 ----
       第三方分享(待定)  ----
    3. 图片浏览 ---- (包含数字 1/4 类-----可保存 在xin app里)


说明:
    该库目前包含有(后面会再进行升级):
        design库
        butterKnife注解
        permissionDispatcher 运行时权限库
        rxJava + retrofit + okHttp log注解 包含Gson
        picture_selector 图片选择器
        bottom-bar 主页底部
        fragmentation fragment管理器
        material-dialog 材质弹出框
        circleImageView  圆角图片
        superTextView 很不错的组合式的text
        高德地图定位
        BRVAH  RV的Adapter帮助工具
        eventBus 组件交互
        pickVIew 省市区以及时间选择器
        二维码
        litePal 数据库(首选 当然GreenDao后面有兴趣再去了解)
        multiDex 分包
        refreshLayout
        material-calenderView 材质日历
        material-spinner 材质Spinner
        router 类似广播
        flowLayout 流式布局
        PhotoView 图片控件
        glide-transformations 图片缩放
        picasso 毕加索 返回bitmap对象(后面这个可以换掉-->自己手写的下载图片)
        cityPicker 城市选择-------- 注意活动的主体背景
        蒲公英 ---- 在线升级
        Logger日志管理
        avi 比较不错的加载效果 ---

 秘籍:
   1.解决NestedScrollView与RecyclerView的卡顿(scrollView和Rv同向):
     linearLayoutManager.setSmoothScrollbarEnabled(true);
     linearLayoutManager.setAutoMeasureEnabled(true);
     recyclerView.setHasFixedSize(true); //*
     recyclerView.setNestedScrollingEnabled(false); //*
   2.recyclerView 添加分割线
             recyclerView.addItemDecoration(new RecycleViewDivider(
                     context, LinearLayoutManager.HORIZONTAL, UIUtils.dip2px(context, 0),
                     ContextCompat.getColor(context, R.color.colorTransparent)));
 模板代码说明:
    1. 动画模板:
       实现了点击视图按钮的按钮抖动动画 -----------


