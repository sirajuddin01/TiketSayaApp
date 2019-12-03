package tiketsaya.astahub.com.Profile;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import tiketsaya.astahub.com.HomeAct;
import tiketsaya.astahub.com.MyTicket;
import tiketsaya.astahub.com.R;
import tiketsaya.astahub.com.SignIn;
import tiketsaya.astahub.com.TicketAdapter;

public class MyProfile extends AppCompatActivity {


    Button btn_edit_profile,btn_backto_home,btn_sign_out;

    TextView nama_lengkap,bio;
    ImageView photo_profile;

    DatabaseReference reference,reference2;

    String USERNAME_KEY = "usernamekey";
    String username_key = "";
    String username_key_new = "";

    RecyclerView myTicket_place;
    ArrayList<MyTicket> list;
    TicketAdapter ticketAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        getUsernameLocal();

        btn_edit_profile = findViewById(R.id.btn_edit_profile);
        btn_backto_home = findViewById(R.id.btn_backto_home);
        btn_sign_out = findViewById(R.id.btn_sign_out);

        nama_lengkap= findViewById(R.id.nama_lengkap);
        bio = findViewById(R.id.bio);
        photo_profile = findViewById(R.id.photo_profile);

        myTicket_place = findViewById(R.id.myticket_place);
        myTicket_place.setLayoutManager(new LinearLayoutManager(this));
        list = new ArrayList<MyTicket>();

        reference = FirebaseDatabase.getInstance().getReference().child("Users").child(username_key_new);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                nama_lengkap.setText(dataSnapshot.child("nama_lengkap").getValue().toString());
                bio.setText(dataSnapshot.child("bio").getValue().toString());
                Picasso.with(MyProfile.this)
                        .load(dataSnapshot.child("url_photo_profile").getValue().toString()).centerCrop().fit().into(photo_profile);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


       btn_edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goeditprofiel = new Intent(MyProfile.this, EditProfile.class);
                startActivity(goeditprofiel);
            }
        });

       reference2 = FirebaseDatabase.getInstance().getReference().child("MyTicketsDetails").child(username_key_new);
       reference2.addListenerForSingleValueEvent(new ValueEventListener() {
           @Override
           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                   MyTicket p = dataSnapshot1.getValue(MyTicket.class);
                   list.add(p);
               }
               ticketAdapter = new TicketAdapter(MyProfile.this,list);
               myTicket_place.setAdapter(ticketAdapter);
           }

           @Override
           public void onCancelled(@NonNull DatabaseError databaseError) {

           }
       });

       btn_backto_home.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent gotohome = new Intent(MyProfile.this, HomeAct.class);
               startActivity(gotohome);
           }
       });

       btn_sign_out.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               // menghapus isi / nilai dari usrname
               //simpan data lokal di hp
               SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
               SharedPreferences.Editor editor = sharedPreferences.edit();
               editor.putString(username_key,null);
               editor.apply();

               Intent gotofirstpage = new Intent(MyProfile.this, SignIn.class);
               startActivity(gotofirstpage);
               finish();

           }
       });

    }
    public  void getUsernameLocal(){
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        username_key_new = sharedPreferences.getString(username_key,"");
    }
}
