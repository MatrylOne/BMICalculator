package pl.wroc.edu.nadolny.firstlab;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pl.wroc.edu.nadolny.firstlab.BMICounter.CountBMIKGM;
import pl.wroc.edu.nadolny.firstlab.BMICounter.CountBMILB;
import pl.wroc.edu.nadolny.firstlab.BMICounter.ICountBMI;

import static pl.wroc.edu.nadolny.firstlab.Helpers.StringHelper.StringToFloat;

public class MainActivity extends AppCompatActivity {

    private enum MassStatus{UNDERWEIGHT, OK, OVERWEIGHT}

    float lastBMI = 0;
    boolean isCounted = false;

    ICountBMI BmiModel;
    @BindView(R.id.heightTxt)
    EditText heightTxt;
    @BindView(R.id.massTxt)
    EditText massTxt;
    @BindView(R.id.switchBtn)
    Switch switchBtn;
    @BindView(R.id.countBMIBtn)
    Button countBMIBtn;
    @BindView(R.id.returnTxt)
    TextView returnTxt;
    @BindView(R.id.heightMeasureLabel)
    TextView heightMeasureLabel;
    @BindView(R.id.massMeasureLabel)
    TextView massMeasureLabel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        loadFromROM();

        switchBtn.setOnCheckedChangeListener(switchListener);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mymenu, menu);
        return true;
    }

    @OnClick(R.id.countBMIBtn)
    public void onCountBMIClick() {
        try {
            if(switchBtn.isChecked())
                BmiModel = new CountBMILB();
            else
                BmiModel = new CountBMIKGM();

            float mass = StringToFloat(massTxt.getText().toString());
            float height = StringToFloat(heightTxt.getText().toString());

            lastBMI = Math.round(BmiModel.countBMI(mass, height) * 10) / 10;
            isCounted = true;

            returnTxt.setTextColor(valueToColor(lastBMI));
            returnTxt.setText(("BMI : " +  lastBMI));

        } catch (Exception e) {
            returnTxt.setTextColor(ResourcesCompat.getColor(getResources(), R.color.blackColor, null));
            returnTxt.setText((R.string.badValues));
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.shareButton:
                shareResults();
                break;
            case R.id.authorButton:
                Intent i = new Intent(getApplicationContext(),AuthorActivity.class);
                startActivity(i);
                break;
            case R.id.saveButton:
                saveToROM();
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    CompoundButton.OnCheckedChangeListener switchListener = new CompoundButton.OnCheckedChangeListener() {
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked) {
                heightMeasureLabel.setText(getString(R.string.inch_label));
                massMeasureLabel.setText(getString(R.string.lbs_label));
            } else {
                heightMeasureLabel.setText(getString(R.string.m_label));
                massMeasureLabel.setText(getString(R.string.kg_label));
            }
        }
    };

    private int valueToColor(float value){
        if(value > 20 && value < 27) {
            return ResourcesCompat.getColor(getResources(), R.color.greenColor, null);
        }else{
            return ResourcesCompat.getColor(getResources(), R.color.redColor, null);
        }
    }

    private MassStatus bmiComparator(float value){
        if(value > 20 && value < 27) {
            return MassStatus.OK;
        }else if(value <= 20){
            return MassStatus.UNDERWEIGHT;
        }else{
            return MassStatus.OVERWEIGHT;
        }
    }

    private void loadFromROM(){
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        heightTxt.setText(sharedPref.getString("savedHeight", ""));
        massTxt.setText(sharedPref.getString("savedMass", ""));
        returnTxt.setText(sharedPref.getString("savedResult", ""));
        returnTxt.setTextColor(sharedPref.getInt("savedColor", 0));
    }

    private void saveToROM(){
        SharedPreferences sharedPref = this.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("savedMass", massTxt.getText().toString());
        editor.putString("savedHeight", heightTxt.getText().toString());
        editor.putString("savedResult", returnTxt.getText().toString());
        editor.putInt("savedColor", returnTxt.getCurrentTextColor());

        editor.commit();
    }

    private void shareResults(){
        String shareBody;
        if(isCounted){
            shareBody = generateMessage(bmiComparator(lastBMI));
        }else{
            shareBody = getString(R.string.shareNotCounted);
        }

        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(sharingIntent, "Share via"));
    }

    private String generateMessage(MassStatus status){
        if(status == MassStatus.OK){
            return getString(R.string.shareMassOK);
        }else if(status == MassStatus.OVERWEIGHT){
            return getString(R.string.shareUnderweight);
        }else{
            return getString(R.string.shareOverweight);
        }
    }
}