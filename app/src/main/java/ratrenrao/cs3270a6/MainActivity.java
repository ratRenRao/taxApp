package ratrenrao.cs3270a6;

import android.app.Fragment;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.ContextThemeWrapper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity
{
    private SharedPreferences preferenceSettings;
    private SharedPreferences.Editor preferenceEditor;
    private static final int PREFERENCE_MODE_PRIVATE = 0;

    private float changeToMake = 0;
    private float changeSoFar = 0;
    private int correctChangeCount = 0;
    private int maxChange = 100;
    private int menuGroupId = Menu.NONE;
    private int menuItemId = Menu.FIRST;
    private ChangeResults changeResultsFragment = new ChangeResults();
    private ChangeButtons changeButtonsFragment = new ChangeButtons();
    private ChangeActions changeActionsFragment = new ChangeActions();

    public View changeResultsView;
    public View changeButtonsView;
    public View changeActionsView;

    private final View.OnClickListener buttonClickListenerAmounts =
            new View.OnClickListener() {
                @Override
                public void onClick(View view)
                {
                    updateAmount(view);
                    updateDisplay();
                    checkAmounts();
                    persistData();
                }
            };

    private final View.OnClickListener buttonClickListenerControls =
            new View.OnClickListener() {
                @Override
                public void onClick(View view)
                {
                    boolean newGame = Boolean.parseBoolean(view.getTag().toString());
                    play(newGame);
                }
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart()
    {
        super.onStart();

        addFragments();
        getPersistedData();
        //addButtonListeners();
        play(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        int order = 103;

        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main, menu);

        menu.addSubMenu(menuGroupId, menuItemId, order, R.string.menuZero);
        menu.addSubMenu(menuGroupId, menuItemId + 1, order + 1, R.string.menuSetMax);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int itemId = item.getItemId();
        //check selected menu item
        if(itemId == menuItemId)
        {
            zeroCorrectCount();
            updateScoreDisplay();
        }
        else
        {
            changeMaxAmountStart();
            updateDisplay();
        }
        return false;
    }

    protected void addFragments()
    {
        getFragmentManager().beginTransaction()
                .add(R.id.fragmentChangeResultsContainer, new ChangeResults(), "CR")
                .commit();

        getFragmentManager().beginTransaction()
                .add(R.id.fragmentChangeButtonsContainer, new ChangeButtons(), "CB")
                .commit();

        getFragmentManager().beginTransaction()
                .add(R.id.fragmentChangeActionsContainer, new ChangeActions(), "CA")
                .commit();
    }

    private void changeMaxAmountStart()
    {
        ChangeMaxAmount changeMaxFragment = new ChangeMaxAmount();

        getFragmentManager().beginTransaction()
                .replace(R.id.fragmentChangeResultsContainer, changeMaxFragment, "CM")
                .commit();

        findViewById(R.id.fragmentChangeButtonsContainer).setVisibility(View.INVISIBLE);
        findViewById(R.id.fragmentChangeActionsContainer).setVisibility(View.INVISIBLE);
    }

    public void changeMaxAmountFinish(int newMax)
    {
        maxChange = newMax;

        getFragmentManager().beginTransaction()
                .replace(R.id.fragmentChangeResultsContainer, new ChangeResults(), "CA")
                .commit();

        findViewById(R.id.fragmentChangeButtonsContainer).setVisibility(View.VISIBLE);
        findViewById(R.id.fragmentChangeActionsContainer).setVisibility(View.VISIBLE);
    }

    private void zeroCorrectCount()
    {
        correctChangeCount = 0;
        persistData();
    }

    public void addChangeClicked(View view)
    {
        updateAmount(view);
        updateDisplay();
        checkAmounts();
        persistData();
    }

    public void actionButtonClicked(View view)
    {
        boolean newGame = Boolean.parseBoolean(view.getTag().toString());
        play(newGame);
    }

    private void play(boolean newGame)
    {
        if(newGame)
        {
            changeToMake = toDollars(getRandomNumber());
            updateChangeToMakeDisplay();
        }

        resetValues();
        persistData();
        updateDisplay();
    }

    private void updateDisplay()
    {
        updateCurrentChangeDisplay();
        updateScoreDisplay();
    }

    private void updateChangeToMakeDisplay()
    {
        //System.out.println(getFragmentManager().findFragmentByTag("CR").toString());
        TextView currentChange = (TextView) findViewById(R.id.editChangeToMake);
        NumberFormat formatter = new DecimalFormat("#0.00");

        currentChange.setText(formatter.format(changeToMake));
    }

    private void updateCurrentChangeDisplay()
    {
        TextView currentChange = (TextView) findViewById(ratrenrao.cs3270a6.R.id.textChange);
        NumberFormat formatter = new DecimalFormat("#0.00");

        currentChange.setText(formatter.format(changeSoFar));
    }

    private void displayPopup(boolean correct)
    {
        AlertDialog.Builder helpBuilder = new AlertDialog.Builder(new ContextThemeWrapper(this, android.R.style.Theme_Holo_Light_Dialog));
        helpBuilder.setTitle(correct ? "You did it!" : "That's too much change.");
        helpBuilder.setMessage(correct ? "You were able to make the correct change." : "You should try again.");
        helpBuilder.setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which)
                    {
                        play(false);
                    }
                });

        AlertDialog helpDialog = helpBuilder.create();
        helpDialog.show();
    }

    /*
    private void addButtonListeners()
    {
        View view= findViewById(ratrenrao.cs3270a6.R.id.fragmentChangeButtonsContainer);
        ArrayList<View> buttons = view.getTouchables();

        for (View button : buttons)
            button.setOnClickListener(buttonClickListenerAmounts);

        findViewById(R.id.buttonNewAmount).setOnClickListener(buttonClickListenerControls);
        findViewById(R.id.buttonStartOver).setOnClickListener(buttonClickListenerControls);
    }
    */

    private void checkAmounts()
    {
        if(changeSoFar == changeToMake)
        {
            displayPopup(true);
            correctChangeCount++;
            updateScoreDisplay();
        }
        else if (changeSoFar > changeToMake)
            displayPopup(false);
    }

    private void updateScoreDisplay()
    {
        ((TextView) findViewById(ratrenrao.cs3270a6.R.id.textCorrectCountValue)).setText(Integer.toString(correctChangeCount));
    }

    private void updateAmount(View view)
    {
        double buttonValue = Double.parseDouble((String) view.getTag());
        changeSoFar += buttonValue;
        changeSoFar = (float) Math.floor(changeSoFar * 100) / 100;
    }

    private int getRandomNumber()
    {
        Random rand = new Random();
        return rand.nextInt(maxChange * 100) + 1;
    }

    private void persistData()
    {
        preferenceSettings = getPreferences(PREFERENCE_MODE_PRIVATE);
        preferenceEditor = preferenceSettings.edit();

        preferenceEditor.putFloat("changeToMake", changeToMake);
        preferenceEditor.putFloat("changeSoFar", changeSoFar);
        preferenceEditor.putInt("correctChangeCount", correctChangeCount);
        preferenceEditor.apply();
    }

    private void getPersistedData()
    {
        preferenceSettings = getPreferences(PREFERENCE_MODE_PRIVATE);

        changeToMake = preferenceSettings.getFloat("changeToMake", 0.0f);
        changeSoFar = preferenceSettings.getFloat("changeSoFar", 0.0f);
        correctChangeCount = preferenceSettings.getInt("correctChangeCount", 0);
    }

    private void resetValues()
    {
        changeSoFar = 0;
    }

    private float toDollars(int num)
    {
        return num / 100.00f;
    }
}
