package com.example.ct60a2411_8;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    TextView titleBottleDispenser;
    TextView titleMessages;
    TextView fieldMessages;
    SeekBar seekBarMoney;


    private int bottleDispenserStatus = 0;
    private int beverageType = -1; // Start status, no beverages selected to buy
    private float moneyInMachine;
    private Bottle lastSoldBottle = new Bottle();

    Context context = null;

    BottleDispenser myBD = BottleDispenser.getInstance(); // Singleton!!!
    Spinner spinnerBottleSelection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = MainActivity.this;

        seekBarMoney = (SeekBar)findViewById(R.id.seekBarMoney);
        seekBarMoney.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
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
                float moneyIn = (float)seekBar.getProgress()/10;
                fieldMessages.setText("Money to be added: " + String.format( "%.2f", moneyIn) + " €" );
                moneyInMachine = moneyIn;
                //myBD.addMoney(moneyIn);
            }
        });

       spinnerBottleSelection = (Spinner) findViewById(R.id.spinnerBottleSelection);

        ArrayList<Bottle> bottleSelection = new ArrayList<>();
        bottleSelection.add(new Bottle(getString(R.string.selectionString)));
        Bottle bottle = new Bottle();
        // Add Bottle-objects into the arraylist for Spinner/Adapter
        for(int i = 0; i < 5; i++) {
            bottle = new Bottle(i);
            String bottleName = bottle.getName() + " " + String.valueOf(bottle.getSize()) + " l";
            bottleSelection.add(new Bottle(bottleName));
            System.out.println(bottleName);
        }

        // Create an ArrayAdapter using the string array and a default spinner layout
       ArrayAdapter<Bottle> adapterBottleSelection = new ArrayAdapter<Bottle>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, bottleSelection);
        // Specify the layout to use when the list of choices appears
        adapterBottleSelection.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinnerBottleSelection.setAdapter(adapterBottleSelection);

        spinnerBottleSelection.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                String selected = parent.getItemAtPosition(pos).toString();
                if (selected.equals("Select product")) {
                    System.out.println("Start position");
                }
                else {
                    beverageType = pos-1;
                    if (beverageType < 0)
                        fieldMessages.setText("Selected: " + selected +  ", Price: " + myBD.getPrice(pos) + " €");
                    else
                        fieldMessages.setText("Selected: " + selected +  ", Price: " + myBD.getPrice(beverageType) + " €");
                }

        }
            public void onNothingSelected(AdapterView<?> parent) {
                fieldMessages.setText("Nothing selected!");
            }
        });

        myInit();
    }

    public void pushAddMoneyButton(View v) {
        float moneyIn = moneyInMachine;
        fieldMessages.setText(R.string.textViewAddMoney);
        myBD.addMoney(moneyIn);
        seekBarMoney.setProgress(0);
        fieldMessages.setText("Added: " + String.format( "%.2f", moneyIn) + " €\n" + "Total: " + String.format( "%.2f", myBD.getMoney()) + " €\n");
    }

    public void pushBuyButton(View v) {
        if (beverageType != -1) {
            bottleDispenserStatus = myBD.buyBottle(beverageType);

            switch (bottleDispenserStatus) {
                case -1:
                    fieldMessages.setText(R.string.textAddMoneyFirst);
                    System.out.println("Add money first!");
                    break;
                case 0:
                    fieldMessages.setText(R.string.textViewNoBeverages);
                    System.out.println("Klink klink. Money came out!");
                    break;
                default:
                    fieldMessages.setText("KACHUNK!\n " + myBD.getName(beverageType) + " " + String.format( "%.2f", myBD.getSize(beverageType)) + " l came out of the dispenser!");
                    System.out.println("KACHUNK! " + myBD.getName(beverageType) + " came out of the dispenser!");
                    lastSoldBottle.setName(myBD.getName(beverageType));
                    lastSoldBottle.setSize(myBD.getSize(beverageType));
                    lastSoldBottle.setPrice(myBD.getPrice(beverageType));
                    break;
            }
        }
        else {
            System.out.println("WTF");
            fieldMessages.setText("Select product first!");
        }
    }

    public void pushRefundMoneyButton(View v) {
        fieldMessages.setText("Refund: " + String.format( "%.2f", myBD.getMoney()) + " €\n");
        myBD.returnMoney();
        seekBarMoney.setProgress(0);
        moneyInMachine = 0;
    }

    public void pushReceiptButton(View v) {
        String receipt = parseReceipt();
        fieldMessages.setText(receipt);
        writeReceipt(receipt);
    }

    private String parseReceipt() {
        String title = "RECEIPT\n\n";
        String product = "Product " + lastSoldBottle.getName() + " ";
        String size = lastSoldBottle.getSize() + " l\n";
        String price = "Paid: " + lastSoldBottle.getPrice() + " €";
        String ret = "\n";
        String thanks = "THANK YOU!";

        String receipt = title + product + size + price + ret + ret + thanks;

        return receipt;
    }

    public void writeReceipt(String receipt) {
        String filename = "Receipt.txt";
        if (receipt == null)
            fieldMessages.setText(R.string.noReceipt);
        else {
            try {
                OutputStreamWriter osw = new OutputStreamWriter(context.openFileOutput(filename, Context.MODE_PRIVATE));
                osw.write(receipt);
                osw.close();
            } catch (IOException e) {
                Log.e("IOException", "Error in write");
                fieldMessages.setText(R.string.errorWriteString);
            } finally {
                System.out.println("File written!");
            }
        }
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