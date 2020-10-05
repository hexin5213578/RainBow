package com.YiDian.RainBow.base;

import java.lang.ref.WeakReference;


/**
 * @ProjectName: Movie
 * @Package: com.bw.movie.base
 * @ClassName: BasePresenter
 * @Description: (java类作用描述)
 * @Author: 何梦洋
 * @CreateDate: 2020/4/17 23:51
 */
public abstract class BasePresenter <V extends BaseView>{
    private WeakReference<V> weakReference;

    public BasePresenter(V v){
        weakReference = new WeakReference<>(v);
        initModel();
    }
    public V getView(){
        if(weakReference!=null){
            return weakReference.get();
        }
        return null;
    }

    public void detachView(){
        if(weakReference!=null){
            weakReference.clear();
            weakReference=null;
        }
    }
    protected abstract void initModel();
}
