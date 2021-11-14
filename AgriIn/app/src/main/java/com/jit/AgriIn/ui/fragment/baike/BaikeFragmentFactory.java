package com.jit.AgriIn.ui.fragment.baike;

/**
 * @author crazyZhangxl on 2018/10/26.
 * Describe:
 */
public class BaikeFragmentFactory {

    private BaikeFragmentFactory(){

    }

    private static final class BaikeFragmentHolder{
        private static final BaikeFragmentFactory INSTANCE = new BaikeFragmentFactory();
    }

    public static BaikeFragmentFactory getInstance() {
        return BaikeFragmentHolder.INSTANCE;
    }

    private BaikeBingFragment mBaikeBingFragment;
    private BaikeMiaoFragment mBaikeMiaoFragment;
    private BaikePinFragment mBaikePinFragment;
    private BaikeWeiFragment mBaikeWeiFragment;
    private BaikeYaoFragment mBaikeYaoFragment;

    public BaikeBingFragment getBaikeBingFragment(){
        if (mBaikeBingFragment == null){
            synchronized (BaikeFragmentFactory.class){
                if (mBaikeBingFragment == null){
                    mBaikeBingFragment = new BaikeBingFragment();
                }
            }
        }
        return mBaikeBingFragment;
    }

    public BaikeMiaoFragment getBaikeMiaoFragment(){
        if (mBaikeMiaoFragment == null){
            synchronized (BaikeFragmentFactory.class){
                if (mBaikeMiaoFragment == null){
                    mBaikeMiaoFragment = new BaikeMiaoFragment();
                }
            }
        }
        return mBaikeMiaoFragment;
    }


    public BaikePinFragment getBaikePinFragment(){
        if (mBaikePinFragment == null){
            synchronized (BaikeFragmentFactory.class){
                if (mBaikePinFragment == null){
                    mBaikePinFragment = new BaikePinFragment();
                }
            }
        }
        return mBaikePinFragment;
    }

    public BaikeWeiFragment getBaikeWeiFragment(){
        if (mBaikeWeiFragment == null){
            synchronized (BaikeFragmentFactory.class){
                if (mBaikeWeiFragment == null){
                    mBaikeWeiFragment = new BaikeWeiFragment();
                }
            }
        }
        return mBaikeWeiFragment;
    }

    public BaikeYaoFragment getBaikeYaoFragment(){
        if (mBaikeYaoFragment == null){
            synchronized (BaikeFragmentFactory.class){
                if (mBaikeYaoFragment == null){
                    mBaikeYaoFragment = new BaikeYaoFragment();
                }
            }
        }
        return mBaikeYaoFragment;
    }
}
