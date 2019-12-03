package tiketsaya.astahub.com.Tickets;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
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

import java.util.Random;

import tiketsaya.astahub.com.R;

public class TicketBuying extends AppCompatActivity {

    Button btn_buy_ticket,btn_min,btn_plus;
    LinearLayout btn_back;
    TextView textJumlahtiket,
            text_totalharga,
            text_jumlahUang,
            nama_wisata,
            lokasi,
            ketentuan;


    Integer nilaiJumlahTiket = 1;
    Integer jumlahUang= 0;
    Integer nilaiTotalHarga = 0;
    Integer nilaihargaTiket = 0;
    ImageView notif_TidakCukup;
    Integer sisa_balance = 0;

    DatabaseReference reference,reference2,reference3,reference4;

    String USERNAME_KEY = "usernamekey";
    String username_key = "";
    String username_key_new = "";

    String date_wisata ="";
    String time_wisata ="";

    //generate nomor int random
    //karena ingin membuat transaksi secara unik
    Integer nomor_transaksi = new Random().nextInt();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket_buy);

        getUsernameLocal();

        //mengambil data dari intent
        Bundle bundle =getIntent().getExtras();
        final String jenis_tiket_baru = bundle.getString("jenis_tiket");

        //declare target
        btn_buy_ticket = findViewById(R.id.btn_buy_ticket);
        btn_min = findViewById(R.id.btn_min);
        btn_plus = findViewById(R.id.btn_plus);
        textJumlahtiket= findViewById(R.id.textJumlahtiket);
        notif_TidakCukup = findViewById(R.id.notif_TidakCukup);

        text_jumlahUang= findViewById(R.id.text_jumlahUang);
        text_totalharga= findViewById(R.id.text_totalharga);

        nama_wisata = findViewById(R.id.nama_wisata);
        lokasi = findViewById(R.id.lokasi);
        ketentuan = findViewById(R.id.ketentuan);

        btn_back = findViewById(R.id.btn_back);

        //setting value baru u/ beberapa komponen
        textJumlahtiket.setText(nilaiJumlahTiket.toString());

        //defaulntnya btn_min disembunyikan
        btn_min.animate().alpha(1).setDuration(300).start();
        btn_min.setEnabled(true);
        notif_TidakCukup.setVisibility(View.GONE);

        //mengambil data user darai firebase
        reference2 = FirebaseDatabase.getInstance().getReference().child("Users").child(username_key_new);
        reference2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                jumlahUang = Integer.valueOf(dataSnapshot.child("user_balance").getValue().toString());
                text_jumlahUang.setText("US$ "+ jumlahUang+"");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //ambil data dari firebase berdsarkan intent
        reference = FirebaseDatabase.getInstance().getReference().child("Wisata").child(jenis_tiket_baru);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //menimpa data yg ada dengan ynag baru
                nama_wisata.setText(dataSnapshot.child("nama_wisata").getValue().toString());
                lokasi.setText(dataSnapshot.child("lokasi").getValue().toString());
                ketentuan.setText(dataSnapshot.child("ketentuan").getValue().toString());
                date_wisata = dataSnapshot.child("date_wisata").getValue().toString();
                time_wisata = dataSnapshot.child("time_wisata").getValue().toString();
                nilaihargaTiket = Integer.valueOf(dataSnapshot.child("harga_tiket").getValue().toString());

                nilaiTotalHarga = nilaihargaTiket * nilaiJumlahTiket;
                text_totalharga.setText("US$ "+nilaiTotalHarga+"");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //Mengurangi nilaijumlah tiket
        btn_min.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nilaiJumlahTiket-=1;
                textJumlahtiket.setText(nilaiJumlahTiket.toString());
               if (nilaiJumlahTiket < 2){
                   btn_min.animate().alpha(0).setDuration(300).start();
                   btn_min.setEnabled(false);
               }
               nilaiTotalHarga = nilaihargaTiket * nilaiJumlahTiket;
               text_totalharga.setText("US$ "+nilaiTotalHarga+"");
               if (nilaiTotalHarga < jumlahUang){
                   btn_buy_ticket.animate().translationY(0).alpha(1).setDuration(350).start();
                   btn_buy_ticket.setEnabled(true);
                   text_jumlahUang.setTextColor(Color.parseColor("#203DD1"));
                   notif_TidakCukup.setVisibility(View.GONE);
               }
            }
        });

        //Tambah nilaijumlah tiket
        btn_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              nilaiJumlahTiket+=1;
                textJumlahtiket.setText(nilaiJumlahTiket.toString());
            if (nilaiJumlahTiket > 1){
                btn_min.animate().alpha(1).setDuration(300).start();
                btn_min.setEnabled(true);
            }
                nilaiTotalHarga = nilaihargaTiket * nilaiJumlahTiket;
                text_totalharga.setText("US$ "+nilaiTotalHarga+"");
                if (nilaiTotalHarga > jumlahUang){
                    btn_buy_ticket.animate().translationY(250).alpha(0).setDuration(350).start();
                    btn_buy_ticket.setEnabled(false);
                    text_jumlahUang.setTextColor(Color.parseColor("#D1206B"));
                    notif_TidakCukup.setVisibility(View.VISIBLE);
                }

            }
        });



        btn_buy_ticket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //mengambil user ke firebase dan buat tabel baru"MyTicketsDetails"
                reference3 = FirebaseDatabase.getInstance().getReference().child("MyTicketsDetails")
                        .child(username_key_new).child(nama_wisata.getText().toString() + nomor_transaksi);
                reference3.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        reference3.getRef().child("id_ticket").setValue(nama_wisata.getText().toString()+ nomor_transaksi);
                        reference3.getRef().child("nama_wisata").setValue(nama_wisata.getText().toString());
                        reference3.getRef().child("lokasi").setValue(lokasi.getText().toString());
                        reference3.getRef().child("ketentuan").setValue(ketentuan.getText().toString());
                        reference3.getRef().child("date_wisata").setValue(date_wisata);
                        reference3.getRef().child("time_wisata").setValue(time_wisata);
                        reference3.getRef().child("jumlah_tiket").setValue(nilaiJumlahTiket.toString());
                        Intent goticketsuccess = new Intent(TicketBuying.this, SuccessBuyTicket.class);
                        startActivity(goticketsuccess);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                //update data balance ke user  (yg sedang login)
                //mengambil data user darai firebase
                reference4 = FirebaseDatabase.getInstance().getReference().child("Users").child(username_key_new);
                reference4.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        sisa_balance = jumlahUang - nilaiTotalHarga;
                        reference4.getRef().child("user_balance").setValue(sisa_balance);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gobacktoticketdetails = new Intent(TicketBuying.this, TicketPreviewDetails.class);
                startActivity(gobacktoticketdetails  );
            }
        });



    }

    public  void getUsernameLocal(){
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        username_key_new = sharedPreferences.getString(username_key,"");
    }
}
