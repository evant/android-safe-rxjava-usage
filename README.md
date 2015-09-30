# android-safe-rxjava-usage
An example of safely using rxjava on Android


It's very easy to see rxjava's api in completly forget it has all the same issues as AsyncTask when trying to fit it in with the Activity Lifecycle. As with any background task, you have to be careful not to leak the Activity. [BadRxJava.java](https://github.com/evant/android-safe-rxjava-usage/blob/master/app/src/main/java/tatarka/me/rxandroidusage/BadRxJava.java) shows a deceptivily simple but wrong usage of rxjava on Android. [GoodRxJavaRetainFragment.java](https://github.com/evant/android-safe-rxjava-usage/blob/master/app/src/main/java/tatarka/me/rxandroidusage/GoodRxJavaRetainFragment.java) shows a way a possible way to solve these issues. Note: this is not the _only_ way to solve the problem and may not be the _best_ way to do it either. It does illustrate all the things you have to be careful about.
