package fall2018.csc2017.GameCentre.ui.menu.view;

import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

import fall2018.csc2017.GameCentre.R;


/**
 * The change password activity_tiles_scores class
 */
public class ChangePassword extends AppCompatActivity {

    /**
     * The editable text for new password
     */
    private TextInputEditText newPassword;


    private Button newPasswordBtn;

    /**
     * The firebase user instance
     */
    private FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        newPasswordBtn = findViewById(R.id.changePassBtn);
        newPassword = findViewById(R.id.changePassPrompt);

        auth = FirebaseAuth.getInstance();
        onClickListener();

    }

    protected void onClickListener()
    {
        newPasswordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userPasswordNew = Objects.requireNonNull(newPassword.getText()).toString();
                changePasswordOperation(userPasswordNew);
            }
        });
    }

    @NonNull
    private void changePasswordOperation(String userPasswordNew) {
        Objects.requireNonNull(
                auth.getCurrentUser()).updatePassword(userPasswordNew)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(
                                    ChangePassword.this, "Password Changed",
                                    Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(ChangePassword.this,
                                    "Password Update Failed",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}
