package albrizy.support.app;

import android.os.Bundle;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

@SuppressWarnings("WeakerAccess")
public class Navigator {

    private static final String EXTRA_POSITION = "navigator_current_position";

    public interface Adapter {
        Fragment onCreateFragment(int position);
        String getTag(int position);
        int getCount();
    }

    private final FragmentManager manager;
    private final Adapter adapter;

    @IdRes
    private int containerId;
    private int defaultPosition;
    private int currentPosition;

    public Navigator(FragmentManager manager, Adapter adapter, @IdRes int containerId) {
        this.manager = manager;
        this.adapter = adapter;
        this.containerId = containerId;
    }

    public void onCreate(@Nullable Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            currentPosition = savedInstanceState.getInt(EXTRA_POSITION,
                    defaultPosition);
        }
    }

    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putInt(EXTRA_POSITION, currentPosition);
    }

    public void setDefaultPosition(int position) {
        this.defaultPosition = position;
    }

    public int getCurrentPosition() {
        return currentPosition;
    }

    public Fragment getCurrentFragment() {
        return getFragment(currentPosition);
    }

    public Fragment getFragment(int position) {
        return manager.findFragmentByTag(adapter.getTag(position));
    }

    public void showFragment(int position) {
        this.showFragment(position, false);
    }

    public void showFragment(int position, boolean reset) {
        this.showFragment(position, reset, false);
    }

    public void showFragment(int position, boolean reset, boolean allowingStateLoss) {
        FragmentTransaction ft = manager.beginTransaction();
        for(int i = 0; i < adapter.getCount(); i++) {
            if (position == i) {
                if (reset) {
                    remove(ft, position);
                    add(ft, position);
                } else {
                    show(ft, i);
                }
            } else {
                hide(ft, i);
            }
        }
        if (allowingStateLoss) {
            ft.commitAllowingStateLoss();
        } else {
            ft.commit();
        }
        currentPosition = position;
    }

    private void show(FragmentTransaction ft, int position) {
        String tag = adapter.getTag(position);
        Fragment fragment = manager.findFragmentByTag(tag);
        if (fragment == null) {
            add(ft, position);
        } else {
            ft.show(fragment);
        }
    }

    private void hide(FragmentTransaction ft, int position) {
        String tag = adapter.getTag(position);
        Fragment fragment = manager.findFragmentByTag(tag);
        if (fragment != null) {
            ft.hide(fragment);
        }
    }

    private void add(FragmentTransaction ft, int position) {
        String tag = adapter.getTag(position);
        Fragment fragment = adapter.onCreateFragment(position);
        ft.add(containerId, fragment, tag);
    }

    private void remove(FragmentTransaction ft, int position) {
        String tag = adapter.getTag(position);
        Fragment fragment = manager.findFragmentByTag(tag);
        if (fragment != null) {
            ft.remove(fragment);
        }
    }
}
