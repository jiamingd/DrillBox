package com.example.jyu.drillbox;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class ScreenBuilder implements Parcelable {
//    @NonNull private Class<? extends Fragment> mFragmentClass;
    @NonNull private Class<Fragment> mFragmentClass;
    @NonNull private Bundle mExtras;

//    public ScreenBuilder(@NonNull final Class<? extends Fragment> fragmentClass) {
    public ScreenBuilder(@NonNull final Class fragmentClass) {
        //noinspection ConstantConditions
        if (fragmentClass == null) {
            throw new AssertionError("null fragment class");
        }
        mFragmentClass = fragmentClass;
        mExtras = new Bundle();
    }

    public ScreenBuilder(Parcel in) {
        mExtras = in.readBundle();
    }

    public ScreenBuilder() {

    }

    //TODO:
    public static final Creator<ScreenBuilder> CREATOR = new Creator<ScreenBuilder>() {
        @Override
        public ScreenBuilder createFromParcel(Parcel in) {
            return new ScreenBuilder(in);
        }

        @Override
        public ScreenBuilder[] newArray(int size) {
            return new ScreenBuilder[size];
        }
    };

    @NonNull
    public Fragment createFragment() {

        Fragment fragment;
        try {
            fragment = mFragmentClass.newInstance();
        } catch (InstantiationException e) {
            throw new NullPointerException("Can't instantiate fragment " + mFragmentClass.getSimpleName());
        } catch (IllegalAccessException e) {
            throw new NullPointerException("Can't instantiate fragment " + mFragmentClass.getSimpleName());
        }
        Bundle args = new Bundle();
        args.putAll(mExtras);
        fragment.setArguments(args);
        return fragment;
    }

//    public void replace(Context context) {
//        if (!(context instanceof Activity)) {
//            return;
//        }
//
//        try {
//            if (context instanceof AppCompatActivity) {
//                FragmentManager fm = ((AppCompatActivity) context).getSupportFragmentManager();
//                final Fragment current = fm.findFragmentById(R.id.main_content_view);
//                Fragment replacement = new Fragment();
//                replacement.setTargetFragment(current.getTargetFragment(), current.getTargetRequestCode());
//                fm.popBackStackImmediate();
//                FragmentTransaction fragmentTransaction = fm.beginTransaction();
//                // IMPORTANT: We turned off the animation on replace because it was causing a UI glitch
//                // when moving between ScannerCheckIn and CheckInSearch. This is easiest to see on
//                // Samsung Galaxy S4. If you turn on the animation make sure you test on the S4.
//                fragmentTransaction
//                        .replace(R.id.main_content_view, replacement)
//                        .addToBackStack(mFragmentClass.getSimpleName())
//                        .commit();
//                // IMPORTANT: Part of the S4 animation fix is to force the replace transaction
//                // to occur as soon as possible. We can do that by calling executePendingTransactions.
//                fm.executePendingTransactions();
//            }
//        } catch (IllegalStateException e) {
//            if (BuildConfig.DEBUG) {
//                throw e;
//            }
//            Log.e("fragment state weirdness", e.toString());
//        }
//    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeBundle(mExtras);
    }
}
