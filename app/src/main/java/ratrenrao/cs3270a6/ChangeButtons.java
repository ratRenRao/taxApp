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
public class ChangeButtons extends android.app.Fragment {

    public View view = null;

    private final View.OnClickListener buttonClickListenerAmounts =
            new View.OnClickListener() {
                @Override
                public void onClick(View view)
                {
                    ((MainActivity)getActivity()).addChangeClicked(view);
                }
            };

    public ChangeButtons() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_change_buttons, container, false);

        //((MainActivity)getActivity()).changeButtonsView = view;
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
        ArrayList<View> buttons = view.getTouchables();

        for (View button : buttons)
            button.setOnClickListener(buttonClickListenerAmounts);
    }
}
