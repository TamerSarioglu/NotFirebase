package com.tamersarioglu.not;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private NotlarAdapter adapter;
    private ArrayList<Notlar> notlarArrayList;

    private DatabaseReference myRef;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Write a message to the database
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference("notlar");

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Not Uygulaması");
        setSupportActionBar(toolbar);

        RecyclerView rv = findViewById(R.id.recyclerView);
        FloatingActionButton fab = findViewById(R.id.floatingActionButton);

        rv.setHasFixedSize(true);
        rv.setLayoutManager(new LinearLayoutManager(this));
        notlarArrayList = new ArrayList<>();
        adapter = new NotlarAdapter(this, notlarArrayList);
        rv.setAdapter(adapter);
        tumNotlariGetir();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, YeniKayitActivity.class));
            }
        });
    }

    public void tumNotlariGetir() {
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                notlarArrayList.clear();
                double toplam = 0.0;

                for (DataSnapshot d : snapshot.getChildren()) {
                    Notlar not = d.getValue(Notlar.class);
                    assert not != null;
                    not.setNot_id(d.getKey());
                    notlarArrayList.add(not);
                    toplam = toplam + (Double.parseDouble(String.valueOf(not.getNot1() + not.getNot2()))) / 2;
                }
                adapter.notifyDataSetChanged();
                toolbar.setSubtitle("Ortalama: " + toplam / notlarArrayList.size());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Error Database Process", error.getMessage().trim());
            }
        });
    }
}
