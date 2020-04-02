// Generated code from Butter Knife. Do not modify!
package com.cinema.client.fragments;

import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.asksira.loopingviewpager.LoopingViewPager;
import com.cinema.client.R;
import com.liangfeizc.avatarview.AvatarView;
import java.lang.IllegalStateException;
import java.lang.Override;

public class MainFlowFragment_ViewBinding implements Unbinder {
  private MainFlowFragment target;

  @UiThread
  public MainFlowFragment_ViewBinding(MainFlowFragment target, View source) {
    this.target = target;

    target.linLayout = Utils.findRequiredViewAsType(source, R.id.linLayout, "field 'linLayout'", LinearLayout.class);
    target.filmMoreMainFlowFragmentButton = Utils.findRequiredViewAsType(source, R.id.filmMoreMainFlowFragmentButton, "field 'filmMoreMainFlowFragmentButton'", Button.class);
    target.textView23 = Utils.findRequiredViewAsType(source, R.id.textView23, "field 'textView23'", TextView.class);
    target.refreshMainFlow = Utils.findRequiredViewAsType(source, R.id.refreshMainFlow, "field 'refreshMainFlow'", SwipeRefreshLayout.class);
    target.buttonCinema = Utils.findRequiredViewAsType(source, R.id.buttonCinema, "field 'buttonCinema'", Button.class);
    target.buttonFilms = Utils.findRequiredViewAsType(source, R.id.buttonFilms, "field 'buttonFilms'", Button.class);
    target.comedyAvatar = Utils.findRequiredViewAsType(source, R.id.comedyAvatar, "field 'comedyAvatar'", AvatarView.class);
    target.actionAvatar = Utils.findRequiredViewAsType(source, R.id.actionAvatar, "field 'actionAvatar'", AvatarView.class);
    target.historicalAvatar = Utils.findRequiredViewAsType(source, R.id.historicalAvatar, "field 'historicalAvatar'", AvatarView.class);
    target.sciFiAvatar = Utils.findRequiredViewAsType(source, R.id.sciFiAvatar, "field 'sciFiAvatar'", AvatarView.class);
    target.horrorAvatar = Utils.findRequiredViewAsType(source, R.id.horrorAvatar, "field 'horrorAvatar'", AvatarView.class);
    target.ivano_frankivsk = Utils.findRequiredViewAsType(source, R.id.ivano_frankivsk, "field 'ivano_frankivsk'", AvatarView.class);
    target.lviv = Utils.findRequiredViewAsType(source, R.id.lviv, "field 'lviv'", AvatarView.class);
    target.kiyv = Utils.findRequiredViewAsType(source, R.id.kiyv, "field 'kiyv'", AvatarView.class);
    target.kharkiv = Utils.findRequiredViewAsType(source, R.id.kharkiv, "field 'kharkiv'", AvatarView.class);
    target.odessa = Utils.findRequiredViewAsType(source, R.id.odessa, "field 'odessa'", AvatarView.class);
    target.filmTitleMainFlowFragmentTextView = Utils.findRequiredViewAsType(source, R.id.filmTitleMainFlowFragmentTextView, "field 'filmTitleMainFlowFragmentTextView'", TextView.class);
    target.filmDateMainFlowFragmentTextView = Utils.findRequiredViewAsType(source, R.id.filmDateMainFlowFragmentTextView, "field 'filmDateMainFlowFragmentTextView'", TextView.class);
    target.filmDurationMainFlowFragmentTextView = Utils.findRequiredViewAsType(source, R.id.filmDurationMainFlowFragmentTextView, "field 'filmDurationMainFlowFragmentTextView'", TextView.class);
    target.viewPager = Utils.findRequiredViewAsType(source, R.id.viewpager, "field 'viewPager'", LoopingViewPager.class);
    target.favCinemasMainFlowFragmentRecyclerView = Utils.findRequiredViewAsType(source, R.id.favCinemasMainFlowFragmentRecyclerView, "field 'favCinemasMainFlowFragmentRecyclerView'", RecyclerView.class);
    target.favCinemasMainFlowFragmentCardView = Utils.findRequiredViewAsType(source, R.id.favCinemasMainFlowFragmentCardView, "field 'favCinemasMainFlowFragmentCardView'", CardView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    MainFlowFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.linLayout = null;
    target.filmMoreMainFlowFragmentButton = null;
    target.textView23 = null;
    target.refreshMainFlow = null;
    target.buttonCinema = null;
    target.buttonFilms = null;
    target.comedyAvatar = null;
    target.actionAvatar = null;
    target.historicalAvatar = null;
    target.sciFiAvatar = null;
    target.horrorAvatar = null;
    target.ivano_frankivsk = null;
    target.lviv = null;
    target.kiyv = null;
    target.kharkiv = null;
    target.odessa = null;
    target.filmTitleMainFlowFragmentTextView = null;
    target.filmDateMainFlowFragmentTextView = null;
    target.filmDurationMainFlowFragmentTextView = null;
    target.viewPager = null;
    target.favCinemasMainFlowFragmentRecyclerView = null;
    target.favCinemasMainFlowFragmentCardView = null;
  }
}
