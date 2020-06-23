package com.tamersarioglu.not;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class NotGuncelleActivity extends AppCompatActivity {

    private Toolbar toolbarNotGuncelle;
    private EditText editText_NotGuncelle_DersAdi, editText_NotGuncelle_Not1, editText_NotGuncelle_Not2;
    private Notlar not;
    private DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_not_guncelle);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference("notlar");

        editText_NotGuncelle_DersAdi = findViewById(R.id.editText_NotGuncelle_DersAdi);
        editText_NotGuncelle_Not1 = findViewById(R.id.editText_NotGuncelle_Not1);
        editText_NotGuncelle_Not2 = findViewById(R.id.editText_NotGuncelle_Not2);
        toolbarNotGuncelle = findViewById(R.id.toolbarNotGuncelle);

        not = (Notlar) getIntent().getSerializableExtra("nesne");
        assert not != null;
        editText_NotGuncelle_DersAdi.setText(not.getDers_adi());
        editText_NotGuncelle_Not1.setText(String.valueOf(not.getNot1()));
        editText_NotGuncelle_Not2.setText(String.valueOf(not.getNot2()));

        toolbarNotGuncelle.setTitle("NOT DETAY");
        setSupportActionBar(toolbarNotGuncelle);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_Sil:
                Snackbar.make(toolbarNotGuncelle, "Sil Tıklandı", Snackbar.LENGTH_SHORT).setAction("SİL", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        myRef.child(not.getNot_id()).removeValue();
                        startActivity(new Intent(NotGuncelleActivity.this, MainActivity.class));
                        finish();
                    }
                }).show();
                return true;
            case R.id.action_düzenle:
                String ders_adi = editText_NotGuncelle_DersAdi.getText().toString().trim();
                String ders_not1 = editText_NotGuncelle_Not1.getText().toString().trim();
                String ders_not2 = editText_NotGuncelle_Not2.getText().toString().trim();

                Map<String, Object> bilgiler = new HashMap<>();
                bilgiler.put("ders_adi", ders_adi);
                bilgiler.put("not1", Integer.parseInt(ders_not1));
                bilgiler.put("not2", Integer.parseInt(ders_not2));

                myRef.child(not.getNot_id()).updateChildren(bilgiler);

                startActivity(new Intent(NotGuncelleActivity.this, MainActivity.class));
                finish();
                return true;
            default:
                return false;
        }
    }
}
