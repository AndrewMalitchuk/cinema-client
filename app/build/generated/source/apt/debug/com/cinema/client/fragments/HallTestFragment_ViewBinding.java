// Generated code from Butter Knife. Do not modify!
package com.cinema.client.fragments;

import android.view.View;
import android.widget.TableLayout;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.cinema.client.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class HallTestFragment_ViewBinding implements Unbinder {
  private HallTestFragment target;

  @UiThread
  public HallTestFragment_ViewBinding(HallTestFragment target, View source) {
    this.target = target;

    target.tableLayout1 = Utils.findRequiredViewAsType(source, R.id.tableLayout1, "field 'tableLayout1'", TableLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    HallTestFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tableLayout1 = null;
  }
}
