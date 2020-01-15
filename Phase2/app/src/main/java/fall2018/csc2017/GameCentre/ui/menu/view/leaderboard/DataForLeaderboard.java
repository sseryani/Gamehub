package fall2018.csc2017.GameCentre.ui.menu.view.leaderboard;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import de.codecrafters.tableview.TableView;
import de.codecrafters.tableview.toolkit.SimpleTableDataAdapter;
import de.codecrafters.tableview.toolkit.SimpleTableHeaderAdapter;
import de.codecrafters.tableview.toolkit.TableDataRowBackgroundProviders;
import fall2018.csc2017.GameCentre.R;

public class DataForLeaderboard extends Fragment {

    private TabPresenter presenter;
    TableView<String[]> tableView;

    static String GAME;
    FloatingActionButton floatingBtn;
    int colorEvenRows, colorOddRows;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.leaderboard_tab_fragment, container,false);
        tableView =  v.findViewById(R.id.tableView);
        floatingBtn =  v.findViewById(R.id.fab);

        presenter = new TabPresenter(v, this, GAME);
        getColors(getContext());
        TableView<String[]> tableView = tableViewInit(v);
        presenter.updatePersonalScore();
        tableView.setDataAdapter(new SimpleTableDataAdapter(getActivity(), presenter.fetchData()));
        updateData();
        return v;
    }

    private void getColors(Context context)
    {
        colorEvenRows = ContextCompat.getColor(context, R.color.grey_3);
        colorOddRows = ContextCompat.getColor(context, R.color.grey_5);
    }

    @NonNull
    private TableView<String[]> tableViewInit(View v) {
        TableView<String[]> tableView = v.findViewById(R.id.tableView);
        tableView.setHeaderBackgroundColor(Color.parseColor("#FFFFFF"));
        tableView.setHeaderAdapter(new SimpleTableHeaderAdapter(getActivity(), presenter.getprobeHeaders()));
        tableView.setColumnCount(3);
        tableView.setDataRowBackgroundProvider(TableDataRowBackgroundProviders.alternatingRowColors(colorEvenRows, colorOddRows));
        return tableView;
    }

    private void updateData()
    {
        floatingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tableView.setDataAdapter(new SimpleTableDataAdapter(getActivity(), presenter.fetchData()));

            }
        });
    }

}
