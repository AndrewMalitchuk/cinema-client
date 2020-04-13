package com.cinema.client.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cinema.client.R;
import com.cinema.client.requests.APIClient;
import com.cinema.client.requests.APIInterface;
import com.cinema.client.requests.entities.CreateUserResponse;
import com.cinema.client.requests.entities.RegistrationAPI;
import com.dynamitechetan.flowinggradient.FlowingGradientClass;
import com.example.myloadingbutton.MyLoadingButton;
import com.google.firebase.inappmessaging.internal.ApiClient;
import com.google.gson.Gson;
import com.pd.chocobar.ChocoBar;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity implements
        View.OnClickListener, MyLoadingButton.MyLoadingButtonClick {


    @BindView(R.id.signUpLinearLayout)
    LinearLayout signUpLinearLayout;

    @BindView(R.id.signUpSignUpActivityButton)
    MyLoadingButton signUpSignUpActivityButton;

    @BindView(R.id.loginSignUpActivityTextView)
    TextView loginSignUpActivityTextView;

    @BindView(R.id.emailSignUpActivityEditText)
    EditText emailSignUpActivityEditText;

    @BindView(R.id.passwordSignUpActivityEditText)
    EditText passwordSignUpActivityEditText;


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

        // Login TextView
        loginSignUpActivityTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });


    }

    private void setLoadingButtonStyle() {
        signUpSignUpActivityButton.setAnimationDuration(500)
                .setButtonLabel(getResources().getString(R.string.signUpSignUpActivityButtonString))
                .setButtonLabelSize(20)
                .setProgressLoaderColor(R.color.colorLoginActivityCardView)
                .setButtonLabelColor(R.color.colorLoginActivityCardView)
                .setProgressDoneIcon(getResources().getDrawable(R.drawable.ic_check_white_24dp)) // Set MyLoadingButton done icon drawable.
                .setProgressErrorIcon(getResources().getDrawable(R.drawable.ic_clear_white_24dp)) //Set MyLoadingButton error icon drawable.
                .setNormalAfterError(true);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        signUpSignUpActivityButton.showNormalButton();
    }

    @Override
    public void onMyLoadingButtonClick() {




        if (emailSignUpActivityEditText.getText().equals("") || passwordSignUpActivityEditText.getText().equals("") ||
                emailSignUpActivityEditText.getText().length() == 0 || passwordSignUpActivityEditText.getText().length() == 0) {

            ChocoBar
                    .builder()
                    .setActivity(SignUpActivity.this)
                    .setText("Please, check fields")
                    .setDuration(ChocoBar.LENGTH_LONG)
                    .red()
                    .show();
            signUpSignUpActivityButton.showErrorButton();


        } else {

            if (passwordSignUpActivityEditText.getText().length() < 8) {
                ChocoBar
                        .builder()
                        .setActivity(SignUpActivity.this)
                        .setText("Password length must be more than 8 symbols")
                        .setDuration(ChocoBar.LENGTH_LONG)
                        .red()
                        .show();
                signUpSignUpActivityButton.showErrorButton();
            } else {


                setLoadingButtonStyle();


                APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);


                RequestBody email_ = RequestBody.create(MediaType.parse("text/plain"),
                        emailSignUpActivityEditText.getText().toString());

                RequestBody password_ = RequestBody.create(MediaType.parse("text/plain"),
                        passwordSignUpActivityEditText.getText().toString());

                RequestBody username_ = RequestBody.create(MediaType.parse("text/plain"),
                        emailSignUpActivityEditText.getText().toString().split("@")[0]);


                Call<RegistrationAPI> call = apiInterface.createNewUser(email_, password_, username_);
                call.enqueue(new Callback<RegistrationAPI>() {
                    @Override
                    public void onResponse(Call<RegistrationAPI> call, Response<RegistrationAPI> response) {


                        if (response.isSuccessful()) {

                            signUpSignUpActivityButton.showDoneButton();
                            ChocoBar
                                    .builder()
                                    .setActivity(SignUpActivity.this)
                                    .setText("Success!")
                                    .setDuration(ChocoBar.LENGTH_LONG)
                                    .green()
                                    .setAction("LOGIN", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                                        }
                                    })
                                    .show();
                        } else {
                            signUpSignUpActivityButton.showErrorButton();

                            try {

                                String request = response.errorBody().string();

                                Gson gson = new Gson().newBuilder().create();
                                CreateUserResponse createUserResponse = gson.fromJson(request, CreateUserResponse.class);


                                ChocoBar
                                        .builder()
                                        .setActivity(SignUpActivity.this)
                                        .setText(createUserResponse.getUsername().get(0))
                                        .setDuration(ChocoBar.LENGTH_LONG)
                                        .red()
                                        .show();


                            } catch (IOException e) {
                                e.printStackTrace();
                                Intent intent=new Intent(SignUpActivity.this, ErrorActivity.class);
                                intent.putExtra("isNetworkError",true);
                                startActivity(intent);
                            }


                        }
                    }

                    @Override
                    public void onFailure(Call<RegistrationAPI> call, Throwable t) {
                        signUpSignUpActivityButton.showErrorButton();
                        ChocoBar
                                .builder()
                                .setActivity(SignUpActivity.this)
                                .setText(t.getLocalizedMessage())
                                .setDuration(ChocoBar.LENGTH_LONG)
                                .red()
                                .show();

                    }
                });
            }
        }

    }
}