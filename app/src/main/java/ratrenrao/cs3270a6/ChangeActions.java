package ratrenrao.cs3270a6;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ChangeActions extends android.app.Fragment {

    public View view = null;

    private final View.OnClickListener buttonClickListenerControls =
            new View.OnClickListener() {
                @Override
                public void onClick(View view)
                {
                    ((MainActivity)getActivity()).actionButtonClicked(view);
                }
            };

    public ChangeActions() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_change_actions, container, false);
        ((MainActivity)getActivity()).changeActionsView = view;
        return view;
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        addButtonListeners();
    }

    private void addButtonListeners()
    {
        getView().findViewById(R.id.buttonNewAmount).setOnClickListener(buttonClickListenerControls);
        getView().findViewById(R.id.buttonStartOver).setOnClickListener(buttonClickListenerControls);
    }
}
