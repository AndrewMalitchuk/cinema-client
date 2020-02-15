// Generated code from Butter Knife. Do not modify!
package com.cinema.client.activity;

import android.view.View;
import android.widget.ImageButton;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import androidx.appcompat.widget.Toolbar;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.cinema.client.R;
import com.developer.mtextfield.ExtendedEditText;
import java.lang.IllegalStateException;
import java.lang.Override;

public class NewNewCardActivity_ViewBinding implements Unbinder {
  private NewNewCardActivity target;

  @UiThread
  public NewNewCardActivity_ViewBinding(NewNewCardActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public NewNewCardActivity_ViewBinding(NewNewCardActivity target, View source) {
    this.target = target;

    target.imageButton = Utils.findRequiredViewAsType(source, R.id.imageButton2, "field 'imageButton'", ImageButton.class);
    target.toolbar = Utils.findRequiredViewAsType(source, R.id.toolbar5, "field 'toolbar'", Toolbar.class);
    target.filmTitleBillActivityExtendedEditText = Utils.findRequiredViewAsType(source, R.id.filmTitleBillActivityExtendedEditText, "field 'filmTitleBillActivityExtendedEditText'", ExtendedEditText.class);
    target.datetimeBillActivityExtendedEditText = Utils.findRequiredViewAsType(source, R.id.datetimeBillActivityExtendedEditText, "field 'datetimeBillActivityExtendedEditText'", ExtendedEditText.class);
    target.cinemaNameBillActivityExtendedEditText = Utils.findRequiredViewAsType(source, R.id.cinemaNameBillActivityExtendedEditText, "field 'cinemaNameBillActivityExtendedEditText'", ExtendedEditText.class);
    target.placesBillActivityExtendedEditText = Utils.findRequiredViewAsType(source, R.id.placesBillActivityExtendedEditText, "field 'placesBillActivityExtendedEditText'", ExtendedEditText.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    NewNewCardActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.imageButton = null;
    target.toolbar = null;
    target.filmTitleBillActivityExtendedEditText = null;
    target.datetimeBillActivityExtendedEditText = null;
    target.cinemaNameBillActivityExtendedEditText = null;
    target.placesBillActivityExtendedEditText = null;
  }
}
