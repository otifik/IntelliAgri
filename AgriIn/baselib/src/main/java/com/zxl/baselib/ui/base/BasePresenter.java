package com.zxl.baselib.ui.base;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

/**
 * @author zxl
 * @date 2018/4/17
 *
 * 关于Reference解释: 一个接口即引用
 *     共计四个实现类: SoftReference;WeakReference;PhantomReference;FinalizerReference
 *
 *     SoftReference: 软引用,引用的对象发生GC时,假若内存比较紧张，就会释放其所占的内存，充实的话便不会释放
 *     WeakReference: 弱引用,所引用的对象发生GC时,不管内存如何都会释放其所占用的内存
 *     PhantomReference: /['fæntəm]/ 虚引用,无法引用一个对象
 *
 */

public class BasePresenter<V extends BaseView> {
    public BaseActivity mContext;

    public BasePresenter(BaseActivity context) {
        mContext = context;
    }

    protected Reference<V> mViewRef;
    public void attachView(V view){
        this.mViewRef = new WeakReference<>(view);
    }

    public void detachView(){
        if (mViewRef != null){
            mViewRef.clear();
            mViewRef = null;
        }
    }

    public V getView(){
        return mViewRef !=null?mViewRef.get():null;
    }
}
