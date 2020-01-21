// Generated code from Butter Knife. Do not modify!
package com.cinema.client.activity;

import android.view.View;
import android.widget.LinearLayout;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.cinema.client.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class QRZoomActivity_ViewBinding implements Unbinder {
  private QRZoomActivity target;

  @UiThread
  public QRZoomActivity_ViewBinding(QRZoomActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public QRZoomActivity_ViewBinding(QRZoomActivity target, View source) {
    this.target = target;

    target.layout = Utils.findRequiredViewAsType(source, R.id.layout, "field 'layout'", LinearLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    QRZoomActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.layout = null;
  }
}
