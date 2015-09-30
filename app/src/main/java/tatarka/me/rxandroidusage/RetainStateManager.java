package tatarka.me.rxandroidusage;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;

/**
 * Abuse loaders to retain some state across rotations for a Fragment or Activity.
 */
public class RetainStateManager {

    private Context context;
    private LoaderManager loaderManager;

    public RetainStateManager(Context context, LoaderManager loaderManager) {
        this.context = context;
        this.loaderManager = loaderManager;
    }

    public <T> T get(int id, final OnCreate<T> onCreate) {
        RetainStateLoader<T> loader = (RetainStateLoader<T>) loaderManager.initLoader(id, null, new LoaderManager.LoaderCallbacks<T>() {
            @Override
            public Loader<T> onCreateLoader(int id, Bundle args) {
                RetainStateLoader<T> loader = new RetainStateLoader<>(context);
                loader.setState(onCreate.onCreate());
                return loader;
            }

            @Override
            public void onLoadFinished(Loader<T> loader, T data) {

            }

            @Override
            public void onLoaderReset(Loader<T> loader) {

            }
        });
        return loader.getState();
    }

    public interface OnCreate<T> {

        T onCreate();
    }

    private static class RetainStateLoader<T> extends Loader<T> {

        private T state;

        public RetainStateLoader(Context context) {
            super(context);
        }

        T getState() {
            return state;
        }

        void setState(T state) {
            this.state = state;
        }
    }
}
