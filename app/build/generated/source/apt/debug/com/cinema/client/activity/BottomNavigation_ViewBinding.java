// Generated code from Butter Knife. Do not modify!
package com.cinema.client.activity;

import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.appcompat.widget.Toolbar;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.cinema.client.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import java.lang.IllegalStateException;
import java.lang.Override;

public class BottomNavigation_ViewBinding implements Unbinder {
  private BottomNavigation target;

  @UiThread
  public BottomNavigation_ViewBinding(BottomNavigation target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public BottomNavigation_ViewBinding(BottomNavigation target, View source) {
    this.target = target;

    target.bottomNavigationView = Utils.findRequiredViewAsType(source, R.id.navigation, "field 'bottomNavigationView'", BottomNavigationView.class);
    target.toolbar = Utils.findRequiredViewAsType(source, R.id.toolbar4, "field 'toolbar'", Toolbar.class);
    target.dummyTextView = Utils.findRequiredViewAsType(source, R.id.dummyTextView, "field 'dummyTextView'", TextView.class);
    target.llProgressBar = Utils.findRequiredView(source, R.id.llProgressBar, "field 'llProgressBar'");
    target.frame = Utils.findRequiredViewAsType(source, R.id.frame, "field 'frame'", FrameLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    BottomNavigation target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.bottomNavigationView = null;
    target.toolbar = null;
    target.dummyTextView = null;
    target.llProgressBar = null;
    target.frame = null;
  }
}
