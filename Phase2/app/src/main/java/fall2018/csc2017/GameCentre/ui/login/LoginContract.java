package fall2018.csc2017.GameCentre.ui.login;

import android.app.Activity;

public interface LoginContract {

    interface View {

        void beginMainMenuActivity();

        void firebaseAuthenticationFailedMessage();

        void firebaseAuthenticationCompletedMessage();
    }

    interface Presenter {

        void logInWithFirebase(Activity activity, String email, String password);
    }
}
