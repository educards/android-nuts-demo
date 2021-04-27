package com.educards.nuts.demo;

import android.app.Activity;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.LifecycleOwner;
import com.educards.nuts.ui.TemplateBinder;
import com.educards.nuts.ui.TemplateData;
import com.google.android.material.snackbar.Snackbar;

public class SnackbarTemplateBinder<T> extends TemplateBinder<T> {

    private Activity activity;

    private ViewDataBinding viewDataBinding;

    public SnackbarTemplateBinder(Activity activity, ViewDataBinding viewDataBinding, @NonNull TemplateData<T> scenario1LiveData, @NonNull LifecycleOwner lifecycleOwner) {
        super(scenario1LiveData, lifecycleOwner);
        this.activity = activity;
        this.viewDataBinding = viewDataBinding;

        onAuthError(failData -> {
            // TODO Localize
            Snackbar.make(viewDataBinding.getRoot(), "This action cannot be done without login", Snackbar.LENGTH_SHORT)
                    .setAction("LOGIN", v -> { doAuth(); })
                    .show();
        });

        onServerError(failData -> {
            // TODO Localize
            Snackbar.make(viewDataBinding.getRoot(), "Action failed due to server error. Please contact server admin if the error persists.", Snackbar.LENGTH_SHORT).show();
        });

        onNetworkingDisabled(failData -> {
            // TODO Localize
            Snackbar.make(viewDataBinding.getRoot(), "Your device doesn't allow internet connection.", Snackbar.LENGTH_SHORT).show();
        });

        onOtherError(failData -> {
            // TODO Localize
            Snackbar.make(viewDataBinding.getRoot(), "Action failed. Please try again later or contact system admin.", Snackbar.LENGTH_SHORT).show();
        });

    }

    public void doAuth() {
        LoginActivityLauncher loginActivityLauncher = ((NutsDemoApplication) activity.getApplication()).getLoginActivityLauncher();
        loginActivityLauncher.startAuthentication(activity);
    }

    public void setupRequestInProgressBehavior(View progressBar, View contentView) {

        observeRequestInProgress(requestInProgress -> {

            // This only happens if someone explicitly pass null
            // value to requestInProgress LiveData. In such case
            // let user decide what to do.
            if (requestInProgress == null) return;

            progressBar.setVisibility(requestInProgress ? View.VISIBLE : View.GONE);
            contentView.setVisibility(requestInProgress ? View.GONE : View.VISIBLE);
        });
    }

}
