// Generated code from Butter Knife. Do not modify!
package com.cinema.client.activity;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.CallSuper;
import androidx.annotation.UiThread;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.cinema.client.R;
import com.example.myloadingbutton.MyLoadingButton;
import java.lang.IllegalStateException;
import java.lang.Override;

public class SignUpActivity_ViewBinding implements Unbinder {
  private SignUpActivity target;

  @UiThread
  public SignUpActivity_ViewBinding(SignUpActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public SignUpActivity_ViewBinding(SignUpActivity target, View source) {
    this.target = target;

    target.signUpLinearLayout = Utils.findRequiredViewAsType(source, R.id.signUpLinearLayout, "field 'signUpLinearLayout'", LinearLayout.class);
    target.signUpSignUpActivityButton = Utils.findRequiredViewAsType(source, R.id.signUpSignUpActivityButton, "field 'signUpSignUpActivityButton'", MyLoadingButton.class);
    target.loginSignUpActivityTextView = Utils.findRequiredViewAsType(source, R.id.loginSignUpActivityTextView, "field 'loginSignUpActivityTextView'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    SignUpActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.signUpLinearLayout = null;
    target.signUpSignUpActivityButton = null;
    target.loginSignUpActivityTextView = null;
  }
}
