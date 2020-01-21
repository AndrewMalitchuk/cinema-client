// Generated code from Butter Knife. Do not modify!
package com.cinema.client.fragments;

import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.cinema.client.R;
import com.freegeek.android.materialbanner.MaterialBanner;
import com.liangfeizc.avatarview.AvatarView;
import java.lang.IllegalStateException;
import java.lang.Override;

public class MainFlowFragment_ViewBinding implements Unbinder {
  private MainFlowFragment target;

  @UiThread
  public MainFlowFragment_ViewBinding(MainFlowFragment target, View source) {
    this.target = target;

    target.linLayout = Utils.findRequiredViewAsType(source, R.id.linLayout, "field 'linLayout'", LinearLayout.class);
    target.button21 = Utils.findRequiredViewAsType(source, R.id.button21, "field 'button21'", Button.class);
    target.textView23 = Utils.findRequiredViewAsType(source, R.id.textView23, "field 'textView23'", TextView.class);
    target.materialBanner = Utils.findRequiredViewAsType(source, R.id.material_banner, "field 'materialBanner'", MaterialBanner.class);
    target.adsBanner = Utils.findRequiredViewAsType(source, R.id.material_banner_adds, "field 'adsBanner'", MaterialBanner.class);
    target.buttonCinema = Utils.findRequiredViewAsType(source, R.id.buttonCinema, "field 'buttonCinema'", Button.class);
    target.buttonFilms = Utils.findRequiredViewAsType(source, R.id.buttonFilms, "field 'buttonFilms'", Button.class);
    target.av1 = Utils.findRequiredViewAsType(source, R.id.av1, "field 'av1'", AvatarView.class);
    target.av2 = Utils.findRequiredViewAsType(source, R.id.av2, "field 'av2'", AvatarView.class);
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
  }

  @Override
  @CallSuper
  public void unbind() {
    MainFlowFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.linLayout = null;
    target.button21 = null;
    target.textView23 = null;
    target.materialBanner = null;
    target.adsBanner = null;
    target.buttonCinema = null;
    target.buttonFilms = null;
    target.av1 = null;
    target.av2 = null;
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
  }
}
