package com.tamersarioglu.not;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class YeniKayitActivity extends AppCompatActivity {
    private EditText editTextDersAdi, editTextNot1, editTextNot2;
    private DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yeni_kayit);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference("notlar");

        Toolbar toolbarYeniKayit = findViewById(R.id.toolbarYeniKayit);
        editTextDersAdi = findViewById(R.id.editTextDersAdi);
        editTextNot1 = findViewById(R.id.editTextNot1);
        editTextNot2 = findViewById(R.id.editTextNot2);
        Button buttonKaydet = findViewById(R.id.buttonKaydet);

        toolbarYeniKayit.setTitle("Not Kayıt");
        setSupportActionBar(toolbarYeniKayit);

        buttonKaydet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String ders_adi = editTextDersAdi.getText().toString().trim();
                String ders_not1 = editTextNot1.getText().toString().trim();
                String ders_not2 = editTextNot2.getText().toString().trim();

                Notlar not = new Notlar("", ders_adi, Integer.parseInt(ders_not1), Integer.parseInt(ders_not2));

                myRef.push().setValue(not);

                startActivity(new Intent(YeniKayitActivity.this, MainActivity.class));
                finish();
            }
        });
    }
}
