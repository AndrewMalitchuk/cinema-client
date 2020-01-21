// Generated code from Butter Knife. Do not modify!
package com.cinema.client.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.appcompat.widget.AppCompatTextView;
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
    target.qrCodeTicketActivityImageView = Utils.findRequiredViewAsType(source, R.id.qrCodeTicketActivityImageView, "field 'qrCodeTicketActivityImageView'", ImageView.class);
    target.qrCodeTicketActivityEditText = Utils.findRequiredViewAsType(source, R.id.qrCodeTicketActivityEditText, "field 'qrCodeTicketActivityEditText'", AppCompatTextView.class);
    target.filmTitleTicketActivityEditText = Utils.findRequiredViewAsType(source, R.id.filmTitleTicketActivityEditText, "field 'filmTitleTicketActivityEditText'", AppCompatTextView.class);
    target.cinemaNameTicketActivityEditText = Utils.findRequiredViewAsType(source, R.id.cinemaNameTicketActivityEditText, "field 'cinemaNameTicketActivityEditText'", AppCompatTextView.class);
    target.filmDateTicketActivityEditText = Utils.findRequiredViewAsType(source, R.id.filmDateTicketActivityEditText, "field 'filmDateTicketActivityEditText'", AppCompatTextView.class);
    target.filmTimeTicketActivityEditText = Utils.findRequiredViewAsType(source, R.id.filmTimeTicketActivityEditText, "field 'filmTimeTicketActivityEditText'", AppCompatTextView.class);
    target.placeTicketActivityEditText = Utils.findRequiredViewAsType(source, R.id.placeTicketActivityEditText, "field 'placeTicketActivityEditText'", AppCompatTextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    TicketActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.ticketLinearLayout = null;
    target.qrCodeTicketActivityImageView = null;
    target.qrCodeTicketActivityEditText = null;
    target.filmTitleTicketActivityEditText = null;
    target.cinemaNameTicketActivityEditText = null;
    target.filmDateTicketActivityEditText = null;
    target.filmTimeTicketActivityEditText = null;
    target.placeTicketActivityEditText = null;
  }
}
