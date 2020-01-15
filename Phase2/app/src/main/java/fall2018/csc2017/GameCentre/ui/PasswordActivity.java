package fall2018.csc2017.GameCentre.ui;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import fall2018.csc2017.GameCentre.R;
import fall2018.csc2017.GameCentre.ui.login.view.LoginActivity;


public class PasswordActivity extends AppCompatActivity {

    private EditText passwordEmail;
    private Button resetPasswordBtn;
    private FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);
        initializeViews();
        resetPassword();
    }

    private void initializeViews()
    {
        passwordEmail = findViewById(R.id.passwordEmail);
        resetPasswordBtn = findViewById(R.id.sendBtn);
        auth = FirebaseAuth.getInstance();

    }

    private void resetPassword()
    {
        resetPasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String useremail = passwordEmail.getText().toString().trim();

                if(useremail.equals("")){
                    Toast.makeText(PasswordActivity.this, "Please enter the email used to create a profile.", Toast.LENGTH_SHORT).show();
                }else{
                    changePasswordOperation(useremail);
                }
            }
        });

    }

    private void changePasswordOperation(String useremail) {
        auth.sendPasswordResetEmail(useremail).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(PasswordActivity.this, "Password Reset Email sent!", Toast.LENGTH_SHORT).show();
                    finish();
                    startActivity(new Intent(PasswordActivity.this, LoginActivity.class));
                }else{
                    Toast.makeText(PasswordActivity.this, "Error in Sending Password Reset Email!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
