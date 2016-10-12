package com.fashare.injector;

import android.graphics.Bitmap;

import com.example.Get;

import rx.Observable;

/**
 * User: fashare(153614131@qq.com)
 * Date: 2016-10-11
 * Time: 22:09
 * <br/><br/>
 * @GET 注解将生成相应的实现类 ObservableProvider$$Impl
 */
interface ObservableProvider {

    @Get(url = Apis.URL, clazz = Bitmap.class)
    Observable<Bitmap> getImageObservable();

//    public Observable<Bitmap> getImageObservable(){
//        return ObservableUtil.newInstance(new ObservableUtil.OnLoadData<Bitmap>() {
//            @Override
//            public Bitmap loadData() {
//                return HttpUtils.getNetWorkBitmap(Apis.URL);
//            }
//        });
//    }
}  