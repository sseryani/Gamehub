package fall2018.csc2017.GameCentre.ui.accountCreation.presenter;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

import fall2018.csc2017.GameCentre.ui.accountCreation.RegistrationContract;
import fall2018.csc2017.GameCentre.ui.accountCreation.model.Account;

public class RegistrationPresenter implements RegistrationContract.Presenter {

    private static final String TAG = RegistrationPresenter.class.getSimpleName();
    private RegistrationContract.View registerView;

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference elemRef = database.getReference("gamecenter-4564c");
    private Account account;

    public RegistrationPresenter(RegistrationContract.View registerView){
        this.registerView = registerView;
    }

    @Override
    public void registerWithFirebase(final String username, String email, String password) {
        FirebaseAuth.getInstance()
                .createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(!task.isSuccessful()){
                            registerView.firebaseAuthenticationFailedMessage();
                        }else{
                            registerView.firebaseAuthenticationCompletedMessage(FirebaseAuth.getInstance().getCurrentUser());
                            account = new Account(username);
                            Log.d(TAG, FirebaseAuth.getInstance().getUid());
                            elemRef.child("accounts").child(Objects.requireNonNull(FirebaseAuth.getInstance().getUid())).setValue(account);
                            updateDisplayNM(username);
                            registerView.startMainMenuActivity();
                        }
                    }
                });

    }

    private void updateDisplayNM(String username)
    {
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(username).build();
        Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).updateProfile(profileUpdates);
    }
}
