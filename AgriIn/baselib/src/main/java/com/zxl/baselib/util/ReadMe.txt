该分类为帮助工具:
app: 系统工具
formatter: 为格式工具
           BigDecimalUtils:资金精确值转换工具
           JsonUtils: fastJason之json格式数据和bean等的转换
image: 图片处理工具
media: 音视频处理工具
time:  时间工具
ui:    视图帮助工具
window:窗口帮助工具

活动间动画Sample:
Intent intent = new Intent(this, MainActivity.class);
startActivity(intent);
overridePendingTransition(R.anim.screen_zoom_in, R.anim.screen_zoom_out);

帧动画-----


条目水波纹效果sample:(就是设置了个布局罢了 没什么神奇的)
<LinearLayout
    android:id="@+id/ll_one_item"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:background="@drawable/ripple_one_item_bg"
    android:orientation="vertical"
    android:paddingLeft="5dp"
    android:paddingTop="5dp">
</LinearLayout>


Dialog设置及回调
//1、设置alertDialog的显示的使用，返回键失效
dialog.setCancelabel(false);

//2、设置监听alertDialog的取消事件
dialog.setOnCancelListener(new OnCancelListener(){
	@Override
	public void onCancel(DialogInterface dialog){
		//取消和事件处理
	}
});

// 解决popupWindow显示后不消失问题
menuPopupWindow.setBackgroundDrawable(new BitmapDrawable());
menuPopupWindow.setOutsideTouchable(true);

//必须执行完上面2个函数后再显示popupwindow，否则无效
menuPopupWindow.showAsDropDown(mIbMenu);

