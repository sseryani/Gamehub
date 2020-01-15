package fall2018.csc2017.GameCentre.ui.menu.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Objects;

import fall2018.csc2017.GameCentre.R;
import fall2018.csc2017.GameCentre.ui.login.view.LoginActivity;
import fall2018.csc2017.GameCentre.ui.menu.ProfileContract;
import fall2018.csc2017.GameCentre.ui.menu.presenter.ProfilePresenter;

public class ProfileFragment extends Fragment implements ProfileContract.View {

    ProfilePresenter profilePresenter;
    ListView listView;
    TextView profile;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile_fragment, container, false);
        profilePresenter = new ProfilePresenter(this);

        profile = view.findViewById(R.id.usrname);
        profile.setText(profilePresenter.getId());

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        listView = Objects.requireNonNull(getView()).findViewById(R.id.listView);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                Objects.requireNonNull(getActivity()),
                android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.options));
        listViewOnClickListener();
        listView.setAdapter(adapter);

    }

    private void listViewOnClickListener() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View v, int i, long l)
            {
                switch (listView.getItemAtPosition(i).toString()) {
                    case "Change Password":
                        startActivity(new Intent(getActivity(), ChangePassword.class));
                        break;
                    case "Deactivate Account":
                        dialogToDeactivate();
                        break;
                    case "Logout":
                        profilePresenter.logout();
                        break;
                }
            }
        });
    }

    @Override
    public void redirectToLogin() {
        startActivity(new Intent(getActivity(), LoginActivity.class));
    }

    private void dialogToDeactivate()
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(Objects.requireNonNull(getContext()));
        builder.setTitle("Please confirm you are going to DEACTIVATE the account!");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                profilePresenter.deactiveAccount();
            }
        });
        builder.setNegativeButton("No", null);
        builder.show();
    }

    public void deactivatedSuccessfully()
    {
        Toast.makeText(getActivity(), "Account has been deactivated", Toast.LENGTH_SHORT)
                .show();
        Objects.requireNonNull(getActivity()).finish();
        startActivity(new Intent(getActivity(), LoginActivity.class));
    }

    public void deactivatedUnSuccessfully()
    {
        Toast.makeText(getActivity(), "Account could not be deactivated! Please try again.",
                Toast.LENGTH_SHORT).show();
    }
}
