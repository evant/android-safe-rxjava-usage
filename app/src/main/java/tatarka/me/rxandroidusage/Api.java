package tatarka.me.rxandroidusage;

import java.util.concurrent.TimeUnit;

import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class Api {

    public static rx.Observable<String> getThing() {
        return rx.Observable.timer(1, TimeUnit.SECONDS, Schedulers.io()).map(new Func1<Long, String>() {
            @Override
            public String call(Long aLong) {
                return "Thing";
            }
        });
    }
}
