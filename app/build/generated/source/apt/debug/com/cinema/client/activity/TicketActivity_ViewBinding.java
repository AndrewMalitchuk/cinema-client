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

public class TicketActivity_ViewBinding implements Unbinder {
  private TicketActivity target;

  @UiThread
  public TicketActivity_ViewBinding(TicketActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public TicketActivity_ViewBinding(TicketActivity target, View source) {
    this.target = target;

    target.ticketLinearLayout = Utils.findRequiredViewAsType(source, R.id.ticketLinearLayout, "field 'ticketLinearLayout'", LinearLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    TicketActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.ticketLinearLayout = null;
  }
}
