package com.sourceit.task2.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sourceit.task2.R;
import com.sourceit.task2.model.Data;
import com.sourceit.task2.utils.L;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity{

    private static final int DEFAULT = 10;
    private TextView countText;
    private Button countButton;
    private LinearLayout ll_root;

    private String KEY = "data";

    private EditText tempText;
    private int countChildWithText;
    private Map<Integer, String> childsWithText = new HashMap<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        addFields(DEFAULT);

        if (savedInstanceState != null){
            L.d("recovery...");
            Data dataRetained = (Data) savedInstanceState.get(KEY);
            countChildWithText = dataRetained.count;
            childsWithText = dataRetained.childsWithText;

            countText.setText(String.valueOf(countChildWithText));

            for (Map.Entry<Integer, String> tempChild : childsWithText.entrySet()){
                EditText tempField = (EditText) ll_root.getChildAt(tempChild.getKey());
                tempField.setText(tempChild.getValue());
            }
        }

        countButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countChildWithText = 0;
                for (int i = 0; i < ll_root.getChildCount(); i++) {
                    tempText = (EditText) ll_root.getChildAt(i);
                    if (tempText.getText().toString().length() > 0) {

                        countChildWithText++;
                        childsWithText.put(i, tempText.getText().toString());
                        L.d("countText++" + countChildWithText);
                    }
                }
                countText.setText(String.valueOf(countChildWithText));
            }
        });
    }

    private void init() {
        ll_root = (LinearLayout) findViewById(R.id.root);
        countButton = (Button) findViewById(R.id.buttonCount);
        countText = (TextView) findViewById(R.id.textViewCount);
    }

    private void addFields(int number){
        for (int i = 0; i < number; i++) {
            ll_root.addView(new EditText(this));
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Data data = new Data();
        data.count = countChildWithText;
        data.childsWithText = childsWithText;
        outState.putSerializable(KEY, data);
        L.d("create serializable..");
    }
}
