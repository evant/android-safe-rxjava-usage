package tatarka.me.rxandroidusage;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class BadRxJava extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final TextView text = (TextView) findViewById(R.id.text);

        // Assume getThing() runs on a background thread.
        Api.getThing()
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        text.setText(s);
                    }
                });
    }
}
