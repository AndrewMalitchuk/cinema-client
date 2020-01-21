// Generated code from Butter Knife. Do not modify!
package com.cinema.client.activity;

import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.cinema.client.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import java.lang.IllegalStateException;
import java.lang.Override;

public class Main2Activity_ViewBinding implements Unbinder {
  private Main2Activity target;

  @UiThread
  public Main2Activity_ViewBinding(Main2Activity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public Main2Activity_ViewBinding(Main2Activity target, View source) {
    this.target = target;

    target.linLayout = Utils.findRequiredViewAsType(source, R.id.linLayout, "field 'linLayout'", LinearLayout.class);
    target.bottomNavigationView = Utils.findRequiredViewAsType(source, R.id.navigation, "field 'bottomNavigationView'", BottomNavigationView.class);
    target.button21 = Utils.findRequiredViewAsType(source, R.id.button21, "field 'button21'", Button.class);
    target.textView23 = Utils.findRequiredViewAsType(source, R.id.textView23, "field 'textView23'", TextView.class);
    target.llProgressBar = Utils.findRequiredView(source, R.id.llProgressBar, "field 'llProgressBar'");
  }

  @Override
  @CallSuper
  public void unbind() {
    Main2Activity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.linLayout = null;
    target.bottomNavigationView = null;
    target.button21 = null;
    target.textView23 = null;
    target.llProgressBar = null;
  }
}
