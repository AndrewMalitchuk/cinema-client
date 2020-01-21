// Generated code from Butter Knife. Do not modify!
package com.cinema.client.activity;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.Toolbar;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.cinema.client.R;
import com.flaviofaria.kenburnsview.KenBurnsView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;
import java.lang.IllegalStateException;
import java.lang.Override;
import me.gujun.android.taggroup.TagGroup;

public class AboutFilmActivity_ViewBinding implements Unbinder {
  private AboutFilmActivity target;

  @UiThread
  public AboutFilmActivity_ViewBinding(AboutFilmActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public AboutFilmActivity_ViewBinding(AboutFilmActivity target, View source) {
    this.target = target;

    target.filmDescriptionFilmActivityEditText = Utils.findRequiredViewAsType(source, R.id.filmDescriptionFilmActivityEditText, "field 'filmDescriptionFilmActivityEditText'", TextView.class);
    target.scrollView = Utils.findRequiredViewAsType(source, R.id.mScrollView, "field 'scrollView'", ScrollView.class);
    target.fab = Utils.findRequiredViewAsType(source, R.id.floatingActionButton2, "field 'fab'", FloatingActionButton.class);
    target.linLayout = Utils.findRequiredViewAsType(source, R.id.linLayout, "field 'linLayout'", LinearLayout.class);
    target.filmTitleFilmActivityEditText = Utils.findRequiredViewAsType(source, R.id.filmTitleFilmActivityEditText, "field 'filmTitleFilmActivityEditText'", AppCompatTextView.class);
    target.myToolbar = Utils.findRequiredViewAsType(source, R.id.my_toolbar, "field 'myToolbar'", Toolbar.class);
    target.dateFilmActivityEditText = Utils.findRequiredViewAsType(source, R.id.dateFilmActivityEditText, "field 'dateFilmActivityEditText'", AppCompatTextView.class);
    target.youtubePreviewFilmActivityPlayerView = Utils.findRequiredViewAsType(source, R.id.youtubePreviewFilmActivityPlayerView, "field 'youtubePreviewFilmActivityPlayerView'", YouTubePlayerView.class);
    target.filmPosterFilmActivityImageView = Utils.findRequiredViewAsType(source, R.id.filmPosterFilmActivityImageView, "field 'filmPosterFilmActivityImageView'", KenBurnsView.class);
    target.additionalInfoFilmActivityTagGroup = Utils.findRequiredViewAsType(source, R.id.additionalInfoFilmActivityTagGroup, "field 'additionalInfoFilmActivityTagGroup'", TagGroup.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    AboutFilmActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.filmDescriptionFilmActivityEditText = null;
    target.scrollView = null;
    target.fab = null;
    target.linLayout = null;
    target.filmTitleFilmActivityEditText = null;
    target.myToolbar = null;
    target.dateFilmActivityEditText = null;
    target.youtubePreviewFilmActivityPlayerView = null;
    target.filmPosterFilmActivityImageView = null;
    target.additionalInfoFilmActivityTagGroup = null;
  }
}
