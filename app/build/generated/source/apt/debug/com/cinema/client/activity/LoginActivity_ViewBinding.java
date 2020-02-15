// Generated code from Butter Knife. Do not modify!
package com.cinema.client.activity;

import android.view.View;
import android.widget.EditText;
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

public class LoginActivity_ViewBinding implements Unbinder {
  private LoginActivity target;

  @UiThread
  public LoginActivity_ViewBinding(LoginActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public LoginActivity_ViewBinding(LoginActivity target, View source) {
    this.target = target;

    target.loginLinearLayout = Utils.findRequiredViewAsType(source, R.id.loginLinearLayout, "field 'loginLinearLayout'", LinearLayout.class);
    target.loginLoginActivityButton = Utils.findRequiredViewAsType(source, R.id.loginLoginActivityButton, "field 'loginLoginActivityButton'", MyLoadingButton.class);
    target.signUpLoginActivityTextView = Utils.findRequiredViewAsType(source, R.id.signUpLoginActivityTextView, "field 'signUpLoginActivityTextView'", TextView.class);
    target.emailSignUpActivityEditText = Utils.findRequiredViewAsType(source, R.id.emailSignUpActivityEditText, "field 'emailSignUpActivityEditText'", EditText.class);
    target.passwordSignUpActivityEditText = Utils.findRequiredViewAsType(source, R.id.passwordSignUpActivityEditText, "field 'passwordSignUpActivityEditText'", EditText.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    LoginActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.loginLinearLayout = null;
    target.loginLoginActivityButton = null;
    target.signUpLoginActivityTextView = null;
    target.emailSignUpActivityEditText = null;
    target.passwordSignUpActivityEditText = null;
  }
}
