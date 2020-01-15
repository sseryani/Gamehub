package fall2018.csc2017.GameCentre.ui.login.presenter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import fall2018.csc2017.GameCentre.ui.login.LoginContract;

public class LoginPresenter implements LoginContract.Presenter {

    private static final String TAG =
            fall2018.csc2017.GameCentre.ui.login.view.LoginActivity.class.getSimpleName();

    private final LoginContract.View viewInterface;

    private final FirebaseAuth firebaseAuth;

    public LoginPresenter(LoginContract.View currView) {
        viewInterface = currView;
        firebaseAuth = FirebaseAuth.getInstance();
    }

    private FirebaseAuth.AuthStateListener authListener = new FirebaseAuth.AuthStateListener() {
        @Override
        public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
            FirebaseUser user = firebaseAuth.getCurrentUser();
            if (user != null) {
                Log.d(TAG, "Logged In");
                viewInterface.beginMainMenuActivity();
            } else {
                Log.d(TAG, "Logged Out");
            }
        }
    };

    public void setLoginListener() {
        firebaseAuth.addAuthStateListener(authListener);
    }

    public void removeLoginListener() {
        firebaseAuth.removeAuthStateListener(authListener);
    }

    @Override
    public void logInWithFirebase(Activity activity, String email, String password) {

        FirebaseAuth.getInstance()
                .signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());
                        if(!task.isSuccessful()){
                            Log.w(TAG, "signInWithCredential", task.getException());
                            viewInterface.firebaseAuthenticationFailedMessage();
                        }
                        else {
                            viewInterface.firebaseAuthenticationCompletedMessage();
                            viewInterface.beginMainMenuActivity();
                        }
                    }
                });
    }
}