package ratrenrao.cs3270a6;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ChangeMaxAmount extends android.app.Fragment
{
    public int maxAmount = 0;
    public View view = null;

    public ChangeMaxAmount()
    {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.fragment_change_max_amount, container, false);
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_change_max_amount, container, false);
        return view;
    }

    @Override
    public void onAttach(Context context)
    {
        super.onAttach(context);
        EditText editText = (EditText) view.findViewById(R.id.editMaxAmount);
        Button buttonSave = (Button) view.findViewById(R.id.buttonSave);

        addListeners(editText, buttonSave);
    }

    private void addListeners(EditText editText, Button buttonSave)
    {
        editText.addTextChangedListener(new TextWatcher()
        {

            @Override
            public void afterTextChanged(Editable s)
            {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after)
            {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count)
            {
                maxAmount = Integer.parseInt(s.toString());
            }
        });

        buttonSave.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view)
                    {
                        ((MainActivity)getActivity()).changeMaxAmountFinish(maxAmount);
                    }
                });
    }

}
