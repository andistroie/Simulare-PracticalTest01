package ro.pub.cs.systems.eim.simulare_practicaltest01;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import ro.pub.cs.systems.eim.simulare_practicaltest01.R;

public class SimularePracticalTest01SecondaryActivity extends AppCompatActivity {

    private EditText textSum;
    private Button buttonOk, buttonCancel;

    private GenericButtonClickListener genericButtonClickListener = new GenericButtonClickListener();
    private class GenericButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            switch(view.getId()) {
                case R.id.buttonOk:
                    setResult(0, null);
                    break;
                case R.id.buttonCancel:
                    setResult(1, null);
                    break;
            }
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simulare_practical_test01_secondary);

        textSum = findViewById(R.id.totalSumText);

        Intent intent = getIntent();
        if (intent != null && intent.getExtras().containsKey("numberOfClicks")) {
            System.out.println("Aici");
            int numberOfClicks = intent.getIntExtra("numberOfClicks", -1);
            textSum.setText(String.valueOf(numberOfClicks));
        }


        buttonOk = findViewById(R.id.buttonOk);
        buttonOk.setOnClickListener(genericButtonClickListener);

        buttonCancel = findViewById(R.id.buttonCancel);
        buttonCancel.setOnClickListener(genericButtonClickListener);
    }
}
