package com.sps.flickrfindr.utils;

import io.reactivex.SingleTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import javax.inject.Inject;

public class SchedulingUtil {

    @Inject
    public SchedulingUtil() {}

    public <T> SingleTransformer<T, T> singleSchedulers() {
        return single -> single.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }
}
