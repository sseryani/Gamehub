package fall2018.csc2017.GameCentre.ui.login.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Objects;

import fall2018.csc2017.GameCentre.R;
import fall2018.csc2017.GameCentre.ui.PasswordActivity;
import fall2018.csc2017.GameCentre.ui.accountCreation.view.RegistrationActivity;
import fall2018.csc2017.GameCentre.ui.login.LoginContract;
import fall2018.csc2017.GameCentre.ui.login.presenter.LoginPresenter;
import fall2018.csc2017.GameCentre.ui.menu.view.MenuActivity;


public class LoginActivity extends AppCompatActivity implements LoginContract.View {

    private LoginPresenter presenter;
    private static final String TAG = LoginActivity.class.getSimpleName();

    private RelativeLayout relativeLayout;
    private ProgressBar loginProgress;
    private Button loginBtn;
    private TextView forgotPassword, createAccount;
    private TextInputEditText loginEmail, passPrompt;
    private Handler animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        createBindings();
        animation.postDelayed(threadAnimation, 1600);
        presenter = new LoginPresenter(this);
        loginOnClickListener();
        createAccountOnClickListener();
        forgotPasswordOnClickListener();

    }

    private void loginOnClickListener() {
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkLoginDetails();
            }
        });
    }

    private void createAccountOnClickListener()
    {
        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegistrationActivity.class));
            }
        });
    }

    private void forgotPasswordOnClickListener()
    {
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, PasswordActivity.class));
            }
        });
    }

    private void createBindings()
    {

        loginProgress = findViewById(R.id.progressbar);
        loginBtn = findViewById(R.id.loginBtn);
        loginEmail = findViewById(R.id.loginEmail);
        passPrompt = findViewById(R.id.passPrompt);
        relativeLayout = findViewById(R.id.relLayout);
        createAccount = findViewById(R.id.registrationTV);
        forgotPassword = findViewById(R.id.forgotPassword);
        animation = new Handler();
    }


    Runnable threadAnimation = new Runnable() {
        @Override
        public void run() {
            relativeLayout.setVisibility(View.VISIBLE);
        }
    };

    @Override
    public void onStart() {
        super.onStart();
        presenter.setLoginListener();
    }

    @Override
    public void onStop() {
        super.onStop();
        presenter.removeLoginListener();
    }

    void login(String email, String password) {
        displayProgress(true);
        presenter.logInWithFirebase(this, email, password);
    }


    void checkLoginDetails() {
        String emailStr = Objects.requireNonNull(loginEmail.getText()).toString();
        String passPromptStr = Objects.requireNonNull(passPrompt.getText()).toString();

        if(!TextUtils.isEmpty(emailStr) && !TextUtils.isEmpty(passPromptStr)){
            login(emailStr, passPromptStr);
        }
        else{
            if(TextUtils.isEmpty(emailStr)){
                loginEmail.setError("Please enter a valid email!");
            }
            if(TextUtils.isEmpty(passPromptStr)){
                passPrompt.setError("Please enter a valid password!");
            }
        }
    }

    @Override
    public void beginMainMenuActivity() {
        finish();
        startActivity(new Intent(this, MenuActivity.class));
        displayProgress(false);
    }


    @Override
    public void firebaseAuthenticationFailedMessage() {
        Log.e(TAG, loginEmail.toString());
        Toast.makeText(LoginActivity.this, "Authentication failed.",
                Toast.LENGTH_SHORT).show();
        displayProgress(false);

    }

    @Override
    public void firebaseAuthenticationCompletedMessage() {
        Toast.makeText(LoginActivity.this, "Authentication Completed.",
                Toast.LENGTH_SHORT).show();
        displayProgress(false);
    }

    private void displayProgress(boolean show) {
        loginProgress.setVisibility(show ? View.VISIBLE : View.GONE);
        loginBtn.setVisibility(show ? View.GONE : View.VISIBLE);
    }
}