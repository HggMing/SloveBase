package com.ming.slove.base.ui.splash;

import android.support.annotation.NonNull;

import rx.subscriptions.CompositeSubscription;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Presenter
 */
public class SplashPresenter implements SplashContract.Presenter {
    @NonNull
    private final SplashContract.View mView;
    @NonNull
    private CompositeSubscription mSubscriptions;

    public SplashPresenter(@NonNull SplashContract.View splashView) {
        mView = checkNotNull(splashView);

        mSubscriptions = new CompositeSubscription();
        mView.setPresenter(this);
    }

    @Override
    public void subscribe() {
    }

    @Override
    public void unsubscribe() {
        mSubscriptions.clear();
    }

}
