package fall2018.csc2017.GameCentre.ui.accountCreation.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseUser;

import java.util.Objects;

import fall2018.csc2017.GameCentre.R;
import fall2018.csc2017.GameCentre.ui.accountCreation.RegistrationContract;
import fall2018.csc2017.GameCentre.ui.accountCreation.presenter.RegistrationPresenter;
import fall2018.csc2017.GameCentre.ui.menu.view.MenuActivity;

public class RegistrationActivity extends AppCompatActivity implements RegistrationContract.View {

    /**
     * All text obtained from the screen
     */
    private TextInputEditText username, email, password, confirmPassword;

    /**
     * The register button
     */
    private Button regButton;

    private ProgressBar registrationProgress;

    private RegistrationPresenter registerPresenter;
    private String emailStr;

    private static final String TAG = RegistrationActivity.class.getSimpleName();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        create();
        registerOnClickListener();
    }

    /**
     * Sets up the views
     */
    private void create(){
        username = findViewById(R.id.username);
        email = findViewById(R.id.regEmail);
        password = findViewById(R.id.passPrompt);
        regButton = findViewById(R.id.btnRegister);
        confirmPassword = findViewById(R.id.confirmPass);
        registerPresenter = new RegistrationPresenter(this);
        registrationProgress = new ProgressBar(this);
    }

    private void registerOnClickListener()
    {
        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayProgress(true);
                checkRegistrationDetails();
            }
        });
    }

    private void checkRegistrationDetails(){

        String usernameStr = Objects.requireNonNull(username.getText()).toString();
        String passwordStr =Objects.requireNonNull(password.getText()).toString();
        emailStr = Objects.requireNonNull(email.getText()).toString();
        String confirmPasswordStr =
                Objects.requireNonNull(confirmPassword.getText()).toString();

        checkInputs(usernameStr, passwordStr, confirmPasswordStr);

    }

    private void checkInputs(String usernameStr, String passwordStr, String confirmPasswordStr) {
        if(!TextUtils.isEmpty(emailStr) && !TextUtils.isEmpty(usernameStr) &&
                !TextUtils.isEmpty(passwordStr) && !TextUtils.isEmpty(confirmPasswordStr) &&
                passwordStr.equals(confirmPasswordStr)){
            register(usernameStr, emailStr, confirmPasswordStr);
        }
        else
        {
            if(TextUtils.isEmpty(emailStr)){
                email.setError("Please enter a valid email!");
            }
            if(TextUtils.isEmpty(usernameStr)){
                username.setError("Please enter a valid username!");
            }
            if(TextUtils.isEmpty(passwordStr)){
                password.setError("Please enter a valid password!");
            }
            if(TextUtils.isEmpty(confirmPasswordStr)){
                confirmPassword.setError("Please enter a valid confirmation password!");
            }
        }
    }

    private void register(String usernameStr, String emailStr, String confirmPassword) {
            registerPresenter.registerWithFirebase(usernameStr, emailStr, confirmPassword);
    }

    private void displayProgress(boolean show) {
        registrationProgress.setVisibility(show ? View.VISIBLE : View.GONE);
        regButton.setVisibility(show ? View.GONE : View.VISIBLE);
    }

    @Override
    public void firebaseAuthenticationFailedMessage() {
        Log.e(TAG, emailStr);
        Toast.makeText(RegistrationActivity.this, "Authentication failed.",
                Toast.LENGTH_SHORT).show();
        displayProgress(false);

    }

    @Override
    public void firebaseAuthenticationCompletedMessage(FirebaseUser firebaseUser) {
        Log.e(TAG, "Created Account: " + firebaseUser.getUid());
        Toast.makeText(RegistrationActivity.this, "Account Successfully Created" ,
                Toast.LENGTH_SHORT).show();
        displayProgress(false);

    }

    @Override
    public void startMainMenuActivity() {
        finish();
        startActivity(new Intent(getApplicationContext(), MenuActivity.class));
        displayProgress(false);
    }
}
