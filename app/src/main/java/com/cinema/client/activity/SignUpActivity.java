package com.cinema.client.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cinema.client.R;
import com.dynamitechetan.flowinggradient.FlowingGradientClass;
import com.example.myloadingbutton.MyLoadingButton;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SignUpActivity extends AppCompatActivity implements
        View.OnClickListener, MyLoadingButton.MyLoadingButtonClick{


    @BindView(R.id.signUpLinearLayout)
    LinearLayout signUpLinearLayout;

    @BindView(R.id.signUpSignUpActivityButton)
    MyLoadingButton signUpSignUpActivityButton;

    @BindView(R.id.loginSignUpActivityTextView)
    TextView loginSignUpActivityTextView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        ButterKnife.bind(this);

        // Gradient
        FlowingGradientClass grad = new FlowingGradientClass();
        grad.setBackgroundResource(R.drawable.translate)
                .onLinearLayout(signUpLinearLayout)
                .setTransitionDuration(4000)
                .start();

        //
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );

        // LoadingButton
        signUpSignUpActivityButton.setMyButtonClickListener(this);
        setLoadingButtonStyle();

        // Login TextView
        loginSignUpActivityTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Click",Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(getApplicationContext(),LoginActivity.class);
                startActivity(intent);
            }
        });


    }

    private void setLoadingButtonStyle(){
        signUpSignUpActivityButton.setAnimationDuration(500)
                .setButtonLabel(getResources().getString(R.string.signUpSignUpActivityButtonString))
                .setButtonLabelSize(20)
                .setProgressLoaderColor(R.color.colorLoginActivityCardView)
                .setButtonLabelColor(R.color.colorLoginActivityCardView)
                .setProgressDoneIcon(getResources().getDrawable(R.drawable.ic_check_black_24dp)) // Set MyLoadingButton done icon drawable.
                .setProgressErrorIcon(getResources().getDrawable(R.drawable.ic_clear_black_24dp)) //Set MyLoadingButton error icon drawable.
                .setNormalAfterError(false);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        signUpSignUpActivityButton.showNormalButton();
    }

    @Override
    public void onMyLoadingButtonClick() {
        Toast.makeText(this, "MyLoadingButton Click", Toast.LENGTH_SHORT).show();

    }
}