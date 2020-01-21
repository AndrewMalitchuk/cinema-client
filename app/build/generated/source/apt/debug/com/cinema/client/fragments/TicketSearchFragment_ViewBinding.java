// Generated code from Butter Knife. Do not modify!
package com.cinema.client.fragments;

import android.view.View;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.arlib.floatingsearchview.FloatingSearchView;
import com.cinema.client.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class TicketSearchFragment_ViewBinding implements Unbinder {
  private TicketSearchFragment target;

  @UiThread
  public TicketSearchFragment_ViewBinding(TicketSearchFragment target, View source) {
    this.target = target;

    target.recyclerView = Utils.findRequiredViewAsType(source, R.id.myTicketsRecycleView, "field 'recyclerView'", RecyclerView.class);
    target.mSearchView = Utils.findRequiredViewAsType(source, R.id.floating_search_view, "field 'mSearchView'", FloatingSearchView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    TicketSearchFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.recyclerView = null;
    target.mSearchView = null;
  }
}
