package tiketsaya.astahub.com.Tickets;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import tiketsaya.astahub.com.HomeAct;
import tiketsaya.astahub.com.R;


public class TicketPreviewDetails extends AppCompatActivity {
    Button btn_buy_ticket;
    TextView title_ticket,
            location_ticket,
            photo_spot_ticket,
            wifi_ticket,
            festival_ticket,
            short_desc_ticket;
    ImageView header_ticket_detail;

    LinearLayout btn_back;

    DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_preview);


        btn_buy_ticket = findViewById(R.id.btn_buy_ticket);
        btn_back = findViewById(R.id.btn_back);

        header_ticket_detail = findViewById(R.id.header_ticket_detail);
        title_ticket = findViewById(R.id.title_tickets);
        location_ticket = findViewById(R.id.location_tickets);
        photo_spot_ticket = findViewById(R.id.photo_spot_ticket);
        wifi_ticket = findViewById(R.id.wifi_ticket);
        festival_ticket = findViewById(R.id.festival_ticket);
        short_desc_ticket = findViewById(R.id.short_desc_ticket);


        //ambildata dari intent
        Bundle bundle =getIntent().getExtras();
        final String jenis_tiket_baru = bundle.getString("jenis_tiket");

       //ambil data dari firebase berdsarkan intent
        reference = FirebaseDatabase.getInstance().getReference().child("Wisata").child(jenis_tiket_baru);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //menimpa data yg ada dengan ynag baru
                title_ticket.setText(dataSnapshot.child("nama_wisata").getValue().toString());
                location_ticket.setText(dataSnapshot.child("lokasi").getValue().toString());
                photo_spot_ticket.setText(dataSnapshot.child("is_photo_spot").getValue().toString());
                wifi_ticket.setText(dataSnapshot.child("is_wifi").getValue().toString());
                festival_ticket.setText(dataSnapshot.child("is_festival").getValue().toString());
                short_desc_ticket.setText(dataSnapshot.child("short_desc").getValue().toString());
                Picasso.with(TicketPreviewDetails.this)
                        .load(dataSnapshot.child("url_tumbnail")
                                .getValue().toString()).centerCrop().fit().into(header_ticket_detail);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        btn_buy_ticket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gocheckout = new Intent(TicketPreviewDetails.this, TicketBuying.class);
                //menaruh data di intent
                gocheckout.putExtra("jenis_tiket",jenis_tiket_baru);
                startActivity(gocheckout);
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotohome = new Intent(TicketPreviewDetails.this, HomeAct.class);
                startActivity(gotohome);
            }
        });
    }
}

