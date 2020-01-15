package fall2018.csc2017.GameCentre.ui.menu.presenter;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

import fall2018.csc2017.GameCentre.ui.menu.ProfileContract;

public class ProfilePresenter implements ProfileContract.Presenter {

    private final FirebaseAuth auth;
    private ProfileContract.View view;

    public ProfilePresenter(ProfileContract.View view) {
        this.view = view;
        this.auth = FirebaseAuth.getInstance();
    }

    public String getId()
    {
        return Objects.requireNonNull(auth.getCurrentUser()).getDisplayName();
    }

    @Override
    public void logout() {
        auth.signOut();
        view.redirectToLogin();
    }

    public void deactiveAccount() {
        Objects.requireNonNull(auth.getCurrentUser()).delete().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful())
                {
                    view.deactivatedSuccessfully();
                }
                else { view.deactivatedUnSuccessfully();}
            }
        });
    }
}
