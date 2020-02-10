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
import com.cinema.client.requests.entities.RegistrationAPI;
import com.cinema.client.requests.entities.TokenAPI;
import com.dynamitechetan.flowinggradient.FlowingGradientClass;
import com.example.myloadingbutton.MyLoadingButton;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cinema.client.R;
import com.pd.chocobar.ChocoBar;

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


    public static final String ACCESS_TOKEN_PREF="accessToken";
    public static final String REFRESH_TOKEN_PREF="refreshToken";
    private SharedPreferences sharedpreferences_access;
    private SharedPreferences sharedpreferences_token;


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
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        );


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
                .setProgressDoneIcon(getResources().getDrawable(R.drawable.ic_check_black_24dp)) // Set MyLoadingButton done icon drawable.
                .setProgressErrorIcon(getResources().getDrawable(R.drawable.ic_clear_black_24dp)); //Set MyLoadingButton error icon drawable.

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

//        RequestBody password_ = RequestBody.create(MediaType.parse("text/plain"),
//                passwordSignUpActivityEditText.getText().toString());
//
//        RequestBody username_ = RequestBody.create(MediaType.parse("text/plain"),
//                emailSignUpActivityEditText.getText().toString().split("@")[0]);

        RequestBody password_ = RequestBody.create(MediaType.parse("text/plain"),
                "root1234");

        RequestBody username_ = RequestBody.create(MediaType.parse("text/plain"),
                "kek_lol_1");

//        String token;

        Call<TokenAPI> call = apiInterface.refreshToken(username_,password_);
        call.enqueue(new Callback<TokenAPI>() {
            @Override
            public void onResponse(Call<TokenAPI> call, Response<TokenAPI> response) {
                loginLoginActivityButton.showDoneButton();
//                ChocoBar
//                        .builder()
//                        .setActivity(LoginActivity.this)
//                        .setText("Success!")
//                        .setDuration(ChocoBar.LENGTH_LONG)
//                        .green()
//                        .setAction("LOGIN", new View.OnClickListener() {
//                            @Override
//                            public void onClick(View view) {
//                                startActivity(new Intent(LoginActivity.this, LoginActivity.class));
//                            }
//                        })
//                        .show();

                Log.d("TOKEN",response.body().getAccess());
//                token=response.body().getAccess();
                sharedpreferences_access = getSharedPreferences(ACCESS_TOKEN_PREF, Context.MODE_PRIVATE);
                sharedpreferences_token = getSharedPreferences(REFRESH_TOKEN_PREF, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor_access = sharedpreferences_access.edit();
                SharedPreferences.Editor editor_refresh = sharedpreferences_token.edit();


                editor_access.putString("accessToken",response.body().getAccess());
                editor_refresh.putString("refreshToken",response.body().getRefresh());
                editor_access.commit();
                editor_refresh.commit();


            }

            @Override
            public void onFailure(Call<TokenAPI> call, Throwable t) {
                loginLoginActivityButton.showErrorButton();
//                ChocoBar
//                        .builder()
//                        .setActivity(SignUpActivity.this)
//                        .setText(t.getLocalizedMessage())
//                        .setDuration(ChocoBar.LENGTH_LONG)
//                        .red()
//                        .show();

            }
        });



        //
        TokenHolder myServiceHolder = new TokenHolder();
        OkHttpClient okHttpClient = new OkHttpClientInstance.Builder(LoginActivity.this, myServiceHolder)
                .addHeader("Authorization", getSharedPreferences(REFRESH_TOKEN_PREF, Context.MODE_PRIVATE).getString("accessToken",null))
                .build();

        OkHttpClient client = new OkHttpClient();

        TokenService myService = new retrofit2.Retrofit.Builder()
                .baseUrl(APIClient.HOST)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
                .create(TokenService.class);


        myServiceHolder.setMyService(myService);

    }
}
