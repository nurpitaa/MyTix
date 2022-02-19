package com.nurpita.mytix;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

public class TicketDetailAct extends AppCompatActivity {

    Button btn_buy_ticket;
    TextView title_ticket, location_ticket,
            photo_spot_ticket, wifi_ticket,
            kantin_ticket, short_desc_ticket;

    DatabaseReference reference;

    ImageView header_ticket_detail;

    LinearLayout btn_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_detail);

        location_ticket = findViewById(R.id.location_ticket);
        btn_buy_ticket = findViewById(R.id.btn_buy_ticket);
        header_ticket_detail = findViewById(R.id.header_ticket_detail);
        btn_back = findViewById(R.id.btn_back);

        title_ticket = findViewById(R.id.title_ticket);
        photo_spot_ticket = findViewById(R.id.photo_spot_ticket);
        wifi_ticket = findViewById(R.id.wifi_ticket);
        kantin_ticket = findViewById(R.id.kantin_ticket);
        short_desc_ticket = findViewById(R.id.short_desc_ticket);

        // mengambil data dari intent
        Bundle bundle = getIntent().getExtras();
        String jenis_tiket_baru = bundle.getString("jenis_tiket");

        // mengambil data dari firebase berdasarkan intent
        reference = FirebaseDatabase.getInstance().getReference().child("Wisata").child(jenis_tiket_baru);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                // menimpa data yang ada dengan data yang baru
                title_ticket.setText(snapshot.child("nama_wisata").getValue().toString());
                location_ticket.setText(snapshot.child("lokasi").getValue().toString());
                photo_spot_ticket.setText(snapshot.child("is_photo_spot").getValue().toString());
                wifi_ticket.setText(snapshot.child("is_wifi").getValue().toString());
                kantin_ticket.setText(snapshot.child("is_kantin").getValue().toString());
                short_desc_ticket.setText(snapshot.child("short_desc").getValue().toString());
                Picasso.with(TicketDetailAct.this)
                        .load(snapshot.child("url_thumbnail")
                                .getValue().toString()).centerCrop().fit()
                        .into(header_ticket_detail);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        btn_buy_ticket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotocheckout = new Intent(TicketDetailAct.this,TicketCheckoutAct.class);
                // meletakkan data kepada intent
                gotocheckout.putExtra("jenis_tiket", jenis_tiket_baru);
                startActivity(gotocheckout);
            }
        });

        location_ticket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("geo:0.847277,108.880255"));
                Intent chooser = Intent.createChooser(intent, "Launch Maps");
                startActivity(chooser);
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoback = new Intent(TicketDetailAct.this,HomeAct.class);
                startActivity(gotoback);
            }
        });
    }
}