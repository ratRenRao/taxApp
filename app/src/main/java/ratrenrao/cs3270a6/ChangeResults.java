package ratrenrao.cs3270a6;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;


/**
 * A simple {@link Fragment} subclass.
 */
public class ChangeResults extends android.app.Fragment {

    public View view = null;

    public ChangeResults() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_change_results, container, false);
       // ((MainActivity)getActivity()).changeResultsView = view;
        return view;
    }

}
