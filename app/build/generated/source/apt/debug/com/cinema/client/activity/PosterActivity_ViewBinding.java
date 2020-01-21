// Generated code from Butter Knife. Do not modify!
package com.cinema.client.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.appcompat.widget.Toolbar;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.cinema.client.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class PosterActivity_ViewBinding implements Unbinder {
  private PosterActivity target;

  @UiThread
  public PosterActivity_ViewBinding(PosterActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public PosterActivity_ViewBinding(PosterActivity target, View source) {
    this.target = target;

    target.linearLayout = Utils.findRequiredViewAsType(source, R.id.linearLayout, "field 'linearLayout'", LinearLayout.class);
    target.stop = Utils.findRequiredViewAsType(source, R.id.stop, "field 'stop'", LinearLayout.class);
    target.imageView4 = Utils.findRequiredViewAsType(source, R.id.imageView4, "field 'imageView4'", ImageView.class);
    target.textView31 = Utils.findRequiredViewAsType(source, R.id.textView31, "field 'textView31'", TextView.class);
    target.toolbar = Utils.findRequiredViewAsType(source, R.id.toolbar10, "field 'toolbar'", Toolbar.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    PosterActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.linearLayout = null;
    target.stop = null;
    target.imageView4 = null;
    target.textView31 = null;
    target.toolbar = null;
  }
}
