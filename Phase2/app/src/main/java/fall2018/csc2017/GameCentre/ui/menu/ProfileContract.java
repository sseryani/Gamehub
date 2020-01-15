package fall2018.csc2017.GameCentre.ui.menu;

public interface ProfileContract {
    interface View
    {
        void redirectToLogin();

        void deactivatedSuccessfully();

        void deactivatedUnSuccessfully();
    }
    interface Presenter
    {
        void logout();
    }
}
