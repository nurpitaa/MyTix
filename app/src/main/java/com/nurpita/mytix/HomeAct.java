package com.nurpita.mytix;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import io.github.florent37.shapeofview.shapes.CircleView;

public class HomeAct extends AppCompatActivity {

    LinearLayout btn_ticket_lemukutan, btn_ticket_randayan,
            btn_ticket_palapabeach, btn_ticket_sinkazoo,
            btn_ticket_rindualam, btn_ticket_samudraindah;
    CircleView btn_to_profile;
    ImageView photo_home_user, item_one, item_two;
    TextView user_balance, nama_lengkap, bio;

    DatabaseReference reference;

    String USERNAME_KEY = "usernamekey";
    String username_key = "";
    String username_key_new = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        getUsernameLocal();

        btn_ticket_lemukutan = findViewById(R.id.btn_ticket_lemukutan);
        btn_to_profile = findViewById(R.id.btn_to_profile);
        item_one = findViewById(R.id.item_one);
        item_two = findViewById(R.id.item_two);

        btn_ticket_randayan = findViewById(R.id.btn_ticket_randayan);
        btn_ticket_palapabeach = findViewById(R.id.btn_ticket_palapabeach);
        btn_ticket_sinkazoo = findViewById(R.id.btn_ticket_sinkazoo);
        btn_ticket_rindualam = findViewById(R.id.btn_ticket_rindualam);
        btn_ticket_samudraindah = findViewById(R.id.btn_ticket_samudraindah);

        photo_home_user = findViewById(R.id.photo_home_user);
        user_balance = findViewById(R.id.user_balance);
        nama_lengkap = findViewById(R.id.nama_lengkap);
        bio = findViewById(R.id.bio);

        reference = FirebaseDatabase.getInstance().getReference()
                .child("Users").child(username_key_new);

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                nama_lengkap.setText(snapshot.child("nama_lengkap").getValue().toString());
                bio.setText(snapshot.child("bio").getValue().toString());
                user_balance.setText("US$ " + snapshot.child("user_balance").getValue().toString());
                Picasso.with(HomeAct.this)
                        .load(snapshot.child("url_photo_profile")
                                .getValue().toString()).centerCrop().fit()
                        .into(photo_home_user);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });

        btn_to_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoprofile = new Intent(HomeAct.this,MyProfileAct.class);
                startActivity(gotoprofile);
            }
        });

        btn_ticket_lemukutan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotolemukutanticket = new Intent(HomeAct.this,TicketDetailAct.class);
                // meletakkan data kepada intent
                gotolemukutanticket.putExtra("jenis_tiket", "Lemukutan");
                startActivity(gotolemukutanticket);
            }
        });

        btn_ticket_randayan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotolemukutanticket = new Intent(HomeAct.this,TicketDetailAct.class);
                gotolemukutanticket.putExtra("jenis_tiket", "Randayan");
                startActivity(gotolemukutanticket);
            }
        });

        btn_ticket_palapabeach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotolemukutanticket = new Intent(HomeAct.this,TicketDetailAct.class);
                gotolemukutanticket.putExtra("jenis_tiket", "Palapa Beach");
                startActivity(gotolemukutanticket);
            }
        });

        btn_ticket_sinkazoo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotolemukutanticket = new Intent(HomeAct.this,TicketDetailAct.class);
                gotolemukutanticket.putExtra("jenis_tiket", "Sinka Zoo");
                startActivity(gotolemukutanticket);
            }
        });

        btn_ticket_rindualam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotolemukutanticket = new Intent(HomeAct.this,TicketDetailAct.class);
                gotolemukutanticket.putExtra("jenis_tiket", "Rindu Alam");
                startActivity(gotolemukutanticket);
            }
        });

        btn_ticket_samudraindah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotolemukutanticket = new Intent(HomeAct.this,TicketDetailAct.class);
                gotolemukutanticket.putExtra("jenis_tiket", "Samudra Indah");
                startActivity(gotolemukutanticket);
            }
        });

        item_one.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoUrl("https://www.youtube.com/watch?v=d-vsAApUe24");
            }
        });

        item_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoUrl("https://phinemo.com/7-tempat-wisata-di-singkawang-dengan-panorama-alam-menakjubkan/");
            }
        });

    }

    private void gotoUrl(String s) {
        Uri uri = Uri.parse(s);
        startActivity(new Intent(Intent.ACTION_VIEW, uri));
    }

    public void getUsernameLocal(){
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        username_key_new = sharedPreferences.getString(username_key, "");
    }
}