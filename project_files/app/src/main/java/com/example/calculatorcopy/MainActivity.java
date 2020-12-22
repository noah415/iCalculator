package com.example.calculatorcopy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    //class variables
    String operation = "";
    String checkResult = "";
    String display = "";
    String operator = "";
    int counter = 0;
    int decCounter = 0;
    int index;
    int decIndex;
    int power;
    double num1;
    double num2;
    double result;
    boolean operated = false;
    //format
    DecimalFormat f = new DecimalFormat("0.####");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //declaration of textview
        final TextView txtview = (TextView) findViewById(R.id.txtview);

        //declaration of other functional buttons
        Button deleteButton = findViewById(R.id.deleteButton);
        Button equalButton = findViewById(R.id.equalButton);
        final Button negativeButton = findViewById(R.id.negativeButton);
        final Button decimalButton = findViewById(R.id.decimalButton);


        //numeric button array
        ArrayList<Button> buttons = new ArrayList<Button>();

        buttons.add((Button) findViewById(R.id.b0));
        buttons.add((Button) findViewById(R.id.b1));
        buttons.add((Button) findViewById(R.id.b2));
        buttons.add((Button) findViewById(R.id.b3));
        buttons.add((Button) findViewById(R.id.b4));
        buttons.add((Button) findViewById(R.id.b5));
        buttons.add((Button) findViewById(R.id.b6));
        buttons.add((Button) findViewById(R.id.b7));
        buttons.add((Button) findViewById(R.id.b8));
        buttons.add((Button) findViewById(R.id.b9));

        //operation array
        final ArrayList<Button> operations = new ArrayList<Button>();

        operations.add((Button) findViewById(R.id.divideButton));
        operations.add((Button) findViewById(R.id.addButton));
        operations.add((Button) findViewById(R.id.minusButton));
        operations.add((Button) findViewById(R.id.multButton));

        //numeric buttons
        for (final Button i : buttons){
            i.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (operated) {
                        operation = "";
                    }

                    operation += i.getText().toString();
                    txtview.setText(operation);
                    operated = false;
                }
            });
        }
        //operation buttons
        for (final Button i : operations) {
            i.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (operation.length() > 0) {
                        if (counter == 0 && operation.length() < 9) {
                            num1 = Double.parseDouble(operation);
                            operation += i.getText().toString();
                            index = operation.indexOf(i.getText().toString());
                            operator = i.getText().toString();
                            txtview.setText(operation);
                            operated = false;
                            i.setEnabled(false);
                            counter++;
                        }
                    }
                }
            });
        }
        //negative button work on negative buttons
        negativeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (operation.contains("-") && operation.contains(operator) == false) {
                    operation = operation.substring(1);
                    txtview.setText(operation);
                }
                else if (operation.contains("-") && operation.contains(operator)) {
                    operation = operation.substring(0, operation.indexOf(operator)) + operation.substring(operation.indexOf(operator) + 2);
                    txtview.setText(operation);
                }
                else if (operation.contains(operator)) {
                    operation = operation.substring(0, operation.indexOf(operator)+1) + "(-" + operation.substring(operation.indexOf(operator)+1) + ")";
                    txtview.setText(operation);
                }
                else if (operation.contains(operator) == false) {
                    operation = "-" + operation;
                    txtview.setText(operation);
                }
            }
        });

        //delete button
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (operation.length() - index == 1) {
                    counter = 0;
                    for (final Button i : operations) {
                        i.setEnabled(true);
                    }
                }
                if (operation.length() - decIndex == 1) {
                    decCounter = 0;
                }
                if (operated) {
                    operation = "";
                    txtview.setText(operation);
                    operated = false;
                }

                else if (operation.length() > 0) {
                    operation = operation.substring(0, operation.length()-1);
                    txtview.setText(operation);
                }
            }
        });
        //decimal button
        decimalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (decCounter == 0){
                    if (operated) {
                        operation = "0";
                    }

                    else if (operation.length() == 0) {
                        operation = "0";
                    }
                    operation += decimalButton.getText().toString();
                    decIndex = operation.indexOf(decimalButton.getText().toString());
                    txtview.setText(operation);
                    operated = false;
                    decCounter += 1;
                }
            }
        });
        //equal button
        equalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (counter == 0) {
                    if (operation.substring(operation.length() - 2).equals(".") && operation.substring(operation.length() - 1).equals("0")) {
                        txtview.setText(operation.substring(0, operation.length()-2));
                    }
                }

                else {
                    counter = 0;
                    decCounter = 0;


                    //addition
                    if (operation.contains("+")) {
                        num2 = Double.parseDouble(operation.substring(operation.indexOf("+")+1));
                        result = num1 + num2;
                    }

                    //subtraction
                    else if (operation.contains("-")) {
                        num2 = Double.parseDouble(operation.substring(operation.indexOf("-")+1));
                        result = num1 - num2;
                    }

                    //division
                    else if (operation.contains("/")) {
                        num2 = Double.parseDouble(operation.substring(operation.indexOf("/")+1));
                        result = num1 / num2;
                    }

                    //multiplication
                    else if (operation.contains("x")) {
                        num2 = Double.parseDouble(operation.substring(operation.indexOf("x")+1));
                        result = num1 * num2;
                    }

                    //layout details
                    checkResult = Integer.toString((int)result);
                    operation = Double.toString(result);

                    if (operation.length() - checkResult.length() == 2 && operation.substring(operation.length() - 1).equals("0")) {
                        txtview.setText(checkResult);
                    }

                    else if (operation.length() > 10 && operation.substring(0, 1).equals("0.")) {
                        power = (operation.substring(2, operation.length()-4)).length();
                        operation = operation.substring(operation.length()-4, operation.length()-1);
                        display = operation + "x10^" + Integer.toString(power);
                        txtview.setText(display);

                    }

                    else {
                        txtview.setText(operation);
                    }

                    for (final Button i : operations) {
                        i.setEnabled(true);
                    }
                    operator = "";
                    operated = true;
                }
            }
        });
    }
}
