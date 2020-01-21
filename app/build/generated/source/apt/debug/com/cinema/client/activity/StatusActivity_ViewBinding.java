// Generated code from Butter Knife. Do not modify!
package com.cinema.client.activity;

import android.view.View;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.appcompat.widget.Toolbar;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.cinema.client.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.transferwise.sequencelayout.SequenceLayout;
import java.lang.IllegalStateException;
import java.lang.Override;

public class StatusActivity_ViewBinding implements Unbinder {
  private StatusActivity target;

  @UiThread
  public StatusActivity_ViewBinding(StatusActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public StatusActivity_ViewBinding(StatusActivity target, View source) {
    this.target = target;

    target.sequenceLayout = Utils.findRequiredViewAsType(source, R.id.status, "field 'sequenceLayout'", SequenceLayout.class);
    target.selectedDateTimeTextView = Utils.findRequiredViewAsType(source, R.id.selectedDateTimeTextView, "field 'selectedDateTimeTextView'", TextView.class);
    target.floatingActionButton = Utils.findRequiredViewAsType(source, R.id.floatingActionButton, "field 'floatingActionButton'", FloatingActionButton.class);
    target.toolbar = Utils.findRequiredViewAsType(source, R.id.toolbar7, "field 'toolbar'", Toolbar.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    StatusActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.sequenceLayout = null;
    target.selectedDateTimeTextView = null;
    target.floatingActionButton = null;
    target.toolbar = null;
  }
}
