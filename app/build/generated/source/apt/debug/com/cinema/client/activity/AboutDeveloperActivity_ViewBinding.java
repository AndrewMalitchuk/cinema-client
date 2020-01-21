// Generated code from Butter Knife. Do not modify!
package com.cinema.client.activity;

import android.view.View;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.appcompat.widget.Toolbar;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.cinema.client.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class AboutDeveloperActivity_ViewBinding implements Unbinder {
  private AboutDeveloperActivity target;

  @UiThread
  public AboutDeveloperActivity_ViewBinding(AboutDeveloperActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public AboutDeveloperActivity_ViewBinding(AboutDeveloperActivity target, View source) {
    this.target = target;

    target.toolbar = Utils.findRequiredViewAsType(source, R.id.toolbar9, "field 'toolbar'", Toolbar.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    AboutDeveloperActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.toolbar = null;
  }
}
