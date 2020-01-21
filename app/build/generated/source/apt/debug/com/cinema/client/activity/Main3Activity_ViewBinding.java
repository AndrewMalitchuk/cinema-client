// Generated code from Butter Knife. Do not modify!
package com.cinema.client.activity;

import android.view.View;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.appcompat.widget.Toolbar;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.cinema.client.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import java.lang.IllegalStateException;
import java.lang.Override;

public class Main3Activity_ViewBinding implements Unbinder {
  private Main3Activity target;

  @UiThread
  public Main3Activity_ViewBinding(Main3Activity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public Main3Activity_ViewBinding(Main3Activity target, View source) {
    this.target = target;

    target.toolbar = Utils.findRequiredViewAsType(source, R.id.toolbar10, "field 'toolbar'", Toolbar.class);
    target.llProgressBar = Utils.findRequiredView(source, R.id.llProgressBar, "field 'llProgressBar'");
    target.bottomNavigationView = Utils.findRequiredViewAsType(source, R.id.navigation, "field 'bottomNavigationView'", BottomNavigationView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    Main3Activity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.toolbar = null;
    target.llProgressBar = null;
    target.bottomNavigationView = null;
  }
}
