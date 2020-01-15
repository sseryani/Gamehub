package fall2018.csc2017.GameCentre.ui.accountCreation;

import com.google.firebase.auth.FirebaseUser;

public interface RegistrationContract {

    interface View{
        void firebaseAuthenticationFailedMessage();
        void firebaseAuthenticationCompletedMessage(FirebaseUser firebaseUser);
        void startMainMenuActivity();
    }

    interface Presenter{
        void registerWithFirebase(final String username, String email, String password);
    }
}
