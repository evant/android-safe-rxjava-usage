package tatarka.me.rxandroidusage;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.subscriptions.CompositeSubscription;

public class GoodRxJavaRetainFragment extends AppCompatActivity {

    private TextView text;
    private ThingFragment thingFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text = (TextView) findViewById(R.id.text);

        if (savedInstanceState == null) {
            thingFragment = new ThingFragment();
            thingFragment.load();
            getSupportFragmentManager().beginTransaction()
                    .add(thingFragment, "THING_FRAGMENT")
                    .commit();
        } else {
            thingFragment = (ThingFragment) getSupportFragmentManager().findFragmentByTag("THING_FRAGMENT");
        }

        thingFragment.setCallback(new ThingFragment.Callback() {
            @Override
            public void onResult(Result<String> result) {
                try {
                    text.setText(result.get());
                } catch (Throwable e) {
                    text.setText("There was an error!");
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Don't want to leak the Activity!
        thingFragment.setCallback(null);
    }

    public static class ThingFragment extends Fragment {

        private CompositeSubscription subscriptions = new CompositeSubscription();
        private Callback callback;
        private Result<String> result;

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setRetainInstance(true);
        }

        public void setCallback(Callback callback) {
            this.callback = callback;
            if (callback != null && result != null) {
                callback.onResult(result);
            }
        }

        public void load() {
            subscriptions.add(Api.getThing()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Action1<String>() {
                        @Override
                        public void call(String s) {
                            result = Result.success(s);
                            if (callback != null) {
                                callback.onResult(result);
                            }
                        }
                    }, new Action1<Throwable>() {
                        @Override
                        public void call(Throwable error) {
                            result = Result.error(error);
                            if (callback != null) {
                                callback.onResult(result);
                            }
                        }
                    }));
        }

        @Override
        public void onDestroy() {
            super.onDestroy();
            subscriptions.unsubscribe();
        }

        public interface Callback {

            void onResult(Result<String> result);
        }
    }
}
