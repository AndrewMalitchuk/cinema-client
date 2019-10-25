package com.cinema.client.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.cinema.client.R;
import com.developer.mtextfield.ExtendedEditText;
import com.developer.mtextfield.SimpleTextChangedWatcher;
import com.developer.mtextfield.TextFieldBoxes;
import com.tenbis.library.views.CompactCreditCardInput;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewCardActivity extends AppCompatActivity {


//    @BindView(R.id.extended_edit_text)
//    ExtendedEditText extendedEditText;

    @BindView(R.id.compact_credit_card_input)
    CompactCreditCardInput compactCreditCardInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_card);


        ButterKnife.bind(this);

//        final TextFieldBoxes textFieldBoxes = findViewById(R.id.text_field_boxes);
//        textFieldBoxes.setSimpleTextChangeWatcher(new SimpleTextChangedWatcher() {
//            @Override
//            public void onTextChanged(String theNewText, boolean isError) {
//                if(theNewText.length()>=4) {
//
//                    Log.d("len",">=4");
//
//
//                    Log.d("#",extendedEditText.getText().toString());
//                    extendedEditText.setText(extendedEditText.getText().toString()+" ");
//
//                }
//            }
//        });





    }
}
