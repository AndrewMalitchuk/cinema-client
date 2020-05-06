// Generated code from Butter Knife. Do not modify!
package com.cinema.client.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.cinema.client.R;
import com.liangfeizc.avatarview.AvatarView;
import com.vivekkaushik.datepicker.DatePickerTimeline;
import java.lang.IllegalStateException;
import java.lang.Override;

public class AboutCinemaActivity_ViewBinding implements Unbinder {
  private AboutCinemaActivity target;

  @UiThread
  public AboutCinemaActivity_ViewBinding(AboutCinemaActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public AboutCinemaActivity_ViewBinding(AboutCinemaActivity target, View source) {
    this.target = target;

    target.scrollView = Utils.findRequiredViewAsType(source, R.id.mScrollView, "field 'scrollView'", ScrollView.class);
    target.linLayout = Utils.findRequiredViewAsType(source, R.id.linLayout, "field 'linLayout'", LinearLayout.class);
    target.cv1 = Utils.findRequiredViewAsType(source, R.id.cv1, "field 'cv1'", CardView.class);
    target.cv2 = Utils.findRequiredViewAsType(source, R.id.cv2, "field 'cv2'", CardView.class);
    target.cv3 = Utils.findRequiredViewAsType(source, R.id.cv3, "field 'cv3'", CardView.class);
    target.datePickerTimeline = Utils.findRequiredViewAsType(source, R.id.datePickerTimeline, "field 'datePickerTimeline'", DatePickerTimeline.class);
    target.cinemaPictureCinemaActivityAvatarView = Utils.findRequiredViewAsType(source, R.id.cinemaPictureCinemaActivityAvatarView, "field 'cinemaPictureCinemaActivityAvatarView'", AvatarView.class);
    target.cinemaNameBigCinemaActivityTextView = Utils.findRequiredViewAsType(source, R.id.cinemaNameBigCinemaActivityTextView, "field 'cinemaNameBigCinemaActivityTextView'", TextView.class);
    target.cinemaLocationCinemaActivityTextView = Utils.findRequiredViewAsType(source, R.id.cinemaLocationCinemaActivityTextView, "field 'cinemaLocationCinemaActivityTextView'", TextView.class);
    target.telephoneAboutCinemaTextView = Utils.findRequiredViewAsType(source, R.id.telephoneAboutCinemaTextView, "field 'telephoneAboutCinemaTextView'", TextView.class);
    target.myToolbar = Utils.findRequiredViewAsType(source, R.id.my_toolbar, "field 'myToolbar'", Toolbar.class);
    target.blurImageView = Utils.findRequiredViewAsType(source, R.id.imageView5, "field 'blurImageView'", ImageView.class);
    target.relativeLayout2 = Utils.findRequiredViewAsType(source, R.id.relativeLayout2, "field 'relativeLayout2'", ConstraintLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    AboutCinemaActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.scrollView = null;
    target.linLayout = null;
    target.cv1 = null;
    target.cv2 = null;
    target.cv3 = null;
    target.datePickerTimeline = null;
    target.cinemaPictureCinemaActivityAvatarView = null;
    target.cinemaNameBigCinemaActivityTextView = null;
    target.cinemaLocationCinemaActivityTextView = null;
    target.telephoneAboutCinemaTextView = null;
    target.myToolbar = null;
    target.blurImageView = null;
    target.relativeLayout2 = null;
  }
}
