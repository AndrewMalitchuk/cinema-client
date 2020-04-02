package com.cinema.client.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.cinema.client.requests.APIClient;
import com.cinema.client.requests.APIInterface;
import com.cinema.client.requests.OkHttpClientInstance;
import com.cinema.client.requests.TokenHolder;
import com.cinema.client.requests.TokenService;
import com.cinema.client.requests.entities.CreateUserResponse;
import com.cinema.client.requests.entities.RegistrationAPI;
import com.cinema.client.requests.entities.TokenAPI;
import com.cinema.client.requests.entities.UserAPI;
import com.dynamitechetan.flowinggradient.FlowingGradientClass;
import com.example.myloadingbutton.MyLoadingButton;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cinema.client.R;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pd.chocobar.ChocoBar;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.converter.gson.GsonConverterFactory;


public class LoginActivity extends AppCompatActivity implements
        View.OnClickListener, MyLoadingButton.MyLoadingButtonClick {


    @BindView(R.id.loginLinearLayout)
    LinearLayout loginLinearLayout;

    @BindView(R.id.loginLoginActivityButton)
    MyLoadingButton loginLoginActivityButton;

    @BindView(R.id.signUpLoginActivityTextView)
    TextView signUpLoginActivityTextView;


    @BindView(R.id.emailSignUpActivityEditText)
    EditText emailSignUpActivityEditText;

    @BindView(R.id.passwordSignUpActivityEditText)
    EditText passwordSignUpActivityEditText;


    public static final String ACCESS_TOKEN_PREF = "accessToken";
    public static final String REFRESH_TOKEN_PREF = "refreshToken";
    private SharedPreferences sharedpreferences_access;
    private SharedPreferences sharedpreferences_token;

    public static final String ACCOUNT_PREF = "accountPref";
    private SharedPreferences sharedpreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);

        // Gradient
        FlowingGradientClass grad = new FlowingGradientClass();
        grad.setBackgroundResource(R.drawable.translate)
                .onLinearLayout(loginLinearLayout)
                .setTransitionDuration(4000)
                .start();

        //


        getApplicationContext().getSharedPreferences("accountPref", 0).edit().clear().commit();



        // LoadingButton
        loginLoginActivityButton.setMyButtonClickListener(this);
        setLoadingButtonStyle();

        // SignUp TextView
        signUpLoginActivityTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "Click", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivity(intent);
            }
        });


//        getWindow().setFlags(
//                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
//                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
//        );


    }

    private void setLoadingButtonStyle() {
        loginLoginActivityButton.setAnimationDuration(500)
                .setButtonLabel("Login")
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
        loginLoginActivityButton.showNormalButton();
    }

    @Override
    public void onMyLoadingButtonClick() {
        Toast.makeText(this, "MyLoadingButton Click", Toast.LENGTH_SHORT).show();

        //
        // https://www.woolha.com/tutorials/android-retrofit-2-refresh-access-token-with-okhttpclient-and-authenticator
        APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);


        if (emailSignUpActivityEditText.getText().equals("") || passwordSignUpActivityEditText.getText().equals("") ||
                emailSignUpActivityEditText.getText().length() == 0 || passwordSignUpActivityEditText.getText().length() == 0) {

            ChocoBar
                    .builder()
                    .setActivity(LoginActivity.this)
                    .setText("Please, check fields")
                    .setDuration(ChocoBar.LENGTH_LONG)
                    .red()
                    .show();
            loginLoginActivityButton.showErrorButton();


        } else {


            if (passwordSignUpActivityEditText.getText().length() < 8) {
                ChocoBar
                        .builder()
                        .setActivity(LoginActivity.this)
                        .setText("Password length must be more than 8 symbols")
                        .setDuration(ChocoBar.LENGTH_LONG)
                        .red()
                        .show();
                loginLoginActivityButton.showErrorButton();
            } else {


                setLoadingButtonStyle();

                RequestBody password_ = RequestBody.create(MediaType.parse("text/plain"),
                        passwordSignUpActivityEditText.getText().toString());

                RequestBody username_ = RequestBody.create(MediaType.parse("text/plain"),
                        emailSignUpActivityEditText.getText().toString().split("@")[0]);


//        String token;

                Call<TokenAPI> call = apiInterface.refreshToken(username_, password_);
                call.enqueue(new Callback<TokenAPI>() {
                    @Override
                    public void onResponse(Call<TokenAPI> call, Response<TokenAPI> response) {
                        loginLoginActivityButton.showDoneButton();

                        if (response.isSuccessful()) {

                            Log.d("TOKEN", response.body().getAccess());
//                token=response.body().getAccess();
                            sharedpreferences_access = getSharedPreferences(ACCESS_TOKEN_PREF, Context.MODE_PRIVATE);
                            sharedpreferences_token = getSharedPreferences(REFRESH_TOKEN_PREF, Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor_access = sharedpreferences_access.edit();
                            SharedPreferences.Editor editor_refresh = sharedpreferences_token.edit();


                            editor_access.putString("accessToken", response.body().getAccess());
                            editor_refresh.putString("refreshToken", response.body().getRefresh());
                            editor_refresh.putString("username",  emailSignUpActivityEditText.getText().toString().split("@")[0]);
                            editor_refresh.putString("password", passwordSignUpActivityEditText.getText().toString());
                            editor_access.commit();
                            editor_refresh.commit();



                            //

                            sharedpreferences = getSharedPreferences(ACCOUNT_PREF, Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedpreferences.edit();

                            int userId;

                            Call<UserAPI> getLoggedUser=apiInterface.getCurrentUser("Bearer "+response.body().getAccess());
                            getLoggedUser.enqueue(new Callback<UserAPI>() {
                                @Override
                                public void onResponse(Call<UserAPI> call, Response<UserAPI> response) {
                                    if(response.isSuccessful()){
                                        SharedPreferences.Editor editor = sharedpreferences.edit();
                                        editor.putInt("userId",response.body().getId());
                                        Log.d("id",response.body().getId().toString());
                                        editor.commit();

                                    }
                                }

                                @Override
                                public void onFailure(Call<UserAPI> call, Throwable t) {

                                }
                            });




                            //


                            //
//                            sharedpreferences = getSharedPreferences(ACCOUNT_PREF, Context.MODE_PRIVATE);
//                            SharedPreferences.Editor editor = sharedpreferences.edit();
//                            editor = sharedpreferences.edit();
                            editor.putString("token", response.body().getAccess());
                            editor.putString("login", emailSignUpActivityEditText.getText().toString().split("@")[0]);
                            editor.putString("password", passwordSignUpActivityEditText.getText().toString());

                            editor.commit();
                            //








                            ChocoBar
                                    .builder()
                                    .setActivity(LoginActivity.this)
                                    .setText("Success!")
                                    .setDuration(ChocoBar.LENGTH_LONG)
                                    .green()
                                    .setAction("LOGIN", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            startActivity(new Intent(LoginActivity.this, Main3Activity.class));
                                        }
                                    })
                                    .show();


                        } else {
                            loginLoginActivityButton.showErrorButton();


                            ChocoBar
                                    .builder()
                                    .setActivity(LoginActivity.this)
                                    .setText("Check your data, please.")
                                    .setDuration(ChocoBar.LENGTH_LONG)
                                    .red()
                                    .show();


                        }

                    }

                    @Override
                    public void onFailure(Call<TokenAPI> call, Throwable t) {
                        loginLoginActivityButton.showErrorButton();
                        ChocoBar
                                .builder()
                                .setActivity(LoginActivity.this)
                                .setText(t.getLocalizedMessage())
                                .setDuration(ChocoBar.LENGTH_LONG)
                                .red()
                                .show();

                    }
                });


                // Не работает, кек
//                TokenHolder myServiceHolder = new TokenHolder();
//                OkHttpClient okHttpClient = new OkHttpClientInstance.Builder(LoginActivity.this, myServiceHolder)
//                        .addHeader("Authorization", getSharedPreferences(REFRESH_TOKEN_PREF, Context.MODE_PRIVATE).getString("accessToken", null))
//                        .build();
//
////        OkHttpClient client = new OkHttpClient();
//
//                TokenService myService = new retrofit2.Retrofit.Builder()
//                        .baseUrl(APIClient.HOST)
//                        .addConverterFactory(GsonConverterFactory.create())
//                        .client(okHttpClient)
//                        .build()
//                        .create(TokenService.class);
//
//
//                myServiceHolder.setMyService(myService);
            }
        }

    }
}
