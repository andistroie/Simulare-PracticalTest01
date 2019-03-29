package ro.pub.cs.systems.eim.simulare_practicaltest01;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SimularePracticalTest01MainActivity extends AppCompatActivity {

    private EditText textLeft, textRight;
    private Button buttonLeft, buttonRight, navigateToSecond;
    private int cntLeft = 0;
    private int cntRight = 0;

    private int serviceStatus = 0;
    private IntentFilter intentFilter = new IntentFilter();

    private GenericButtonClickListener genericButtonClickListener = new GenericButtonClickListener();
    private class GenericButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.buttonPressMeLeft) {
                cntLeft++;
                textLeft.setText(String.valueOf(cntLeft));
            } else if (view.getId() == R.id.buttonPressMeRight) {
                cntRight++;
                textRight.setText(String.valueOf(cntRight));
            } else if (view.getId() == R.id.sec_button) {
                Intent intent = new Intent(getApplicationContext(), SimularePracticalTest01SecondaryActivity.class);
                int numberOfClicks = cntLeft + cntRight;
                intent.putExtra("numberOfClicks", numberOfClicks);
                startActivityForResult(intent,1);
            }
            if (cntLeft + cntRight > 5
                    && serviceStatus == 0) {
                Intent intent = new Intent(getApplicationContext(), SimularePracticalTest01Service.class);
                intent.putExtra("firstNumber", cntLeft);
                intent.putExtra("secondNumber", cntRight);
                getApplicationContext().startService(intent);
                serviceStatus = 1;
            }
        }
    }

    private MessageBroadcastReceiver messageBroadcastReceiver = new MessageBroadcastReceiver();
    private class MessageBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.d("[Message]", intent.getStringExtra("message"));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simulare_practical_test01_main);

        textLeft = findViewById(R.id.pressMeLeft);
        textRight = findViewById(R.id.pressMeRight);

        buttonLeft = findViewById(R.id.buttonPressMeLeft);
        buttonLeft.setOnClickListener(genericButtonClickListener);

        buttonRight = findViewById(R.id.buttonPressMeRight);
        buttonRight.setOnClickListener(genericButtonClickListener);

        navigateToSecond = findViewById(R.id.sec_button);
        navigateToSecond.setOnClickListener(genericButtonClickListener);

        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey("leftCount")) {
                textLeft.setText(savedInstanceState.getString("leftCount"));
            } else {
                textLeft.setText(String.valueOf(0));
            }
            if (savedInstanceState.containsKey("rightCount")) {
                textRight.setText(savedInstanceState.getString("rightCount"));
            } else {
                textRight.setText(String.valueOf(0));
            }
        } else {
            textLeft.setText(String.valueOf(0));
            textRight.setText(String.valueOf(0));
        }

        for (int index = 0; index < ProcessingThread.actionTypes.length; index++) {
            intentFilter.addAction(ProcessingThread.actionTypes[index]);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(messageBroadcastReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        unregisterReceiver(messageBroadcastReceiver);
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        Intent intent = new Intent(this, SimularePracticalTest01Service.class);
        stopService(intent);
        super.onDestroy();
    }


    @Override
    protected void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        savedInstanceState.putString("leftCount", textLeft.getText().toString());
        savedInstanceState.putString("rightCount", textRight.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState.containsKey("leftCount")) {
            textLeft.setText(savedInstanceState.getString("leftCount"));
        } else {
            textLeft.setText(String.valueOf(0));
        }
        if (savedInstanceState.containsKey("rightCount")) {
            textRight.setText(savedInstanceState.getString("rightCount"));
        } else {
            textRight.setText(String.valueOf(0));
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == 1) {
            Toast.makeText(this, "The activity returned with result " + resultCode, Toast.LENGTH_LONG).show();
        }
    }

}

