package com.example.ct60a2411_8;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TextView titleBottleDispenser;
    TextView titleMessages;
    TextView fieldMessages;
    SeekBar seekBar;


    int bottleDispenserStatus = 0;
    float money;

    Context context = null;




    BottleDispenser myBD = BottleDispenser.getInstance(); // Singleton!!!


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = MainActivity.this;

        seekBar = (SeekBar)findViewById(R.id.seekBarMoney);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress,
                                          boolean fromUser) {
                //Toast.makeText(getApplicationContext(),"seekbar progress: "+progress, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                //Toast.makeText(getApplicationContext(),"seekbar touch started!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                //Toast.makeText(getApplicationContext(),"seekbar touch stopped!", Toast.LENGTH_SHORT).show();
                float moneyIn = seekBar.getProgress()/10;
                fieldMessages.setText("Money to be added: " + moneyIn + " €" );
                money = moneyIn;
                //myBD.addMoney(moneyIn);
            }
        });

        myInit();
        //TextView test = (TextView) view.findViewById(R.id.textView2);
        //test.setBackgroundResource(context.getResources().getColor(android.R.color.holo_green_light));
    }

    public void pushAddMoneyButton(View v) {
        float moneyIn = money;
        fieldMessages.setText(R.string.textViewAddMoney);
        myBD.addMoney(moneyIn);
        fieldMessages.setText("Added: " + moneyIn + " €\n" + "Total: " + myBD.getMoney() + " €\n");
        // todo: Lisää seekBarin nollaus!!
        }

    public void pushBuyButton(View v) {
        fieldMessages.setText(R.string.textViewBuy);
        bottleDispenserStatus = myBD.buyBottle(0);

        switch(bottleDispenserStatus) {
            case -1:
                fieldMessages.setText(R.string.textAddMoneyFirst);
                System.out.println("Add money first!");
                break;
            case 0:
                fieldMessages.setText(R.string.textViewNoBeverages);
                System.out.println("Klink klink. Money came out!");
                break;
            default:
                fieldMessages.setText("KACHUNK! " + myBD.getName(0) + " came out of the dispenser!");
                System.out.println("KACHUNK! " + "selectedBottle.getName()" + " came out of the dispenser!");
                break;
        }
    }

    public void pushRefundMoneyButton(View v) {
        fieldMessages.setText("Refund: " + myBD.getMoney() + " €\n");
        myBD.returnMoney();
    }

    public void myInit() {
        System.out.println("Project CT60A2411");
        System.out.println("Initializing UI");
        System.out.println("Initializing variables");
        System.out.println("Initializing files"); //Todo: Create text files for init and receipt
        System.out.println(context.getFilesDir());

        titleBottleDispenser = (TextView) findViewById(R.id.textViewBottleDispenser);
        titleMessages = (TextView) findViewById(R.id.textViewMessages);
        fieldMessages = (TextView) findViewById(R.id.textViewMessageField);

        titleBottleDispenser.setText(R.string.textViewBottleDispenser);
        titleMessages.setText(R.string.textViewMessages);
        fieldMessages.setText(R.string.textViewEmpty);

        fieldMessages.setText(R.string.textViewDispenserFilled);

    }
}