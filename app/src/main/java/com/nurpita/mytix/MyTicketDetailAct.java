package com.nurpita.mytix;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

public class MyTicketDetailAct extends AppCompatActivity {

    DatabaseReference reference;
    TextView xnama_wisata, xlokasi, xtime_wisata, xdate_wisata, xketentuan;
    LinearLayout btn_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_ticket_detail);

        xnama_wisata = findViewById(R.id.xnama_wisata);
        xlokasi = findViewById(R.id.xlokasi);
        xtime_wisata = findViewById(R.id.xtime_wisata);
        xdate_wisata = findViewById(R.id.xdate_wisata);
        xketentuan = findViewById(R.id.xketentuan);
        btn_back = findViewById(R.id.btn_back);

        // mengambil data dari intent
        Bundle bundle = getIntent().getExtras();
        final String nama_wisata_baru = bundle.getString("nama_wisata");

        // mengambil data dari firebase
        reference = FirebaseDatabase.getInstance().getReference().child("Wisata").child(nama_wisata_baru);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                xnama_wisata.setText(snapshot.child("nama_wisata").getValue().toString());
                xlokasi.setText(snapshot.child("lokasi").getValue().toString());
                xtime_wisata.setText(snapshot.child("time_wisata").getValue().toString());
                xdate_wisata.setText(snapshot.child("date_wisata").getValue().toString());
                xketentuan.setText(snapshot.child("ketentuan").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoprofile = new Intent(MyTicketDetailAct.this,MyProfileAct.class);
                startActivity(gotoprofile);
            }
        });

    }
}