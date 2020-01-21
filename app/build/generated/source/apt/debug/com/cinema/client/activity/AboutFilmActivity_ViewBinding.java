// Generated code from Butter Knife. Do not modify!
package com.cinema.client.activity;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.cinema.client.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.lang.IllegalStateException;
import java.lang.Override;

public class AboutFilmActivity_ViewBinding implements Unbinder {
  private AboutFilmActivity target;

  @UiThread
  public AboutFilmActivity_ViewBinding(AboutFilmActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public AboutFilmActivity_ViewBinding(AboutFilmActivity target, View source) {
    this.target = target;

    target.textView4 = Utils.findRequiredViewAsType(source, R.id.textView4, "field 'textView4'", TextView.class);
    target.scrollView = Utils.findRequiredViewAsType(source, R.id.mScrollView, "field 'scrollView'", ScrollView.class);
    target.fab = Utils.findRequiredViewAsType(source, R.id.floatingActionButton2, "field 'fab'", FloatingActionButton.class);
    target.linLayout = Utils.findRequiredViewAsType(source, R.id.linLayout, "field 'linLayout'", LinearLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    AboutFilmActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.textView4 = null;
    target.scrollView = null;
    target.fab = null;
    target.linLayout = null;
  }
}
