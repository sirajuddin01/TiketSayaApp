package tiketsaya.astahub.com;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import tiketsaya.astahub.com.Register.Register1;

public class SignIn extends AppCompatActivity {


    TextView btn_new_account;
    Button btn_Sign_In;
    EditText xusername, xpassword;

    DatabaseReference reference;

    String USERNAME_KEY = "usernamekey";
    String username_key = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        btn_new_account = findViewById(R.id.btn_new_account);
        btn_Sign_In = findViewById(R.id.btn_Sign_In);
        xusername = findViewById(R.id.xusername);
        xpassword = findViewById(R.id.xpassword);



        btn_new_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent new_account = new Intent(SignIn.this, Register1.class);
                startActivity(new_account);
            }
        });

        btn_Sign_In.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // ubah state jadi loading
                btn_Sign_In.setEnabled(false);
                btn_Sign_In.setText("Loading...");

               final String username = xusername.getText().toString();
               final String password = xpassword.getText().toString();

               if ( username.isEmpty()){
                   Toast.makeText(getApplicationContext(), "Username / Password Kosong!!!", Toast.LENGTH_SHORT).show();
                   btn_Sign_In.setEnabled(true);
                   btn_Sign_In.setText("SIGN IN");
               }else {
                   if (password.isEmpty()){
                       Toast.makeText(getApplicationContext(), "Username / Password Kosong!!!", Toast.LENGTH_SHORT).show();
                       btn_Sign_In.setEnabled(true);
                       btn_Sign_In.setText("SIGN IN");
                   }else {
                       reference = FirebaseDatabase.getInstance().getReference().child("Users")
                               .child(username);
                       reference.addListenerForSingleValueEvent(new ValueEventListener() {
                           @Override
                           public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                               if (dataSnapshot.exists()){

                                   //ambil data password dari firebase
                                   String passowrdFromFirebase = dataSnapshot.child("password").getValue().toString();

                                   //validasi password dengan password di firebase
                                   if (password.equals(passowrdFromFirebase)){

                                       //simpan username di lokal
                                       //simpan data lokal di hp
                                       SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
                                       SharedPreferences.Editor editor = sharedPreferences.edit();
                                       editor.putString(username_key,xusername.getText().toString());
                                       editor.apply();

                                       //pindah activity
                                       Intent goSignIn = new Intent(SignIn.this,HomeAct.class);
                                       startActivity(goSignIn);

                                   }else {
                                       Toast.makeText(getApplicationContext(), "Password Salah!!", Toast.LENGTH_SHORT).show();
                                       btn_Sign_In.setEnabled(true);
                                       btn_Sign_In.setText("SIGN IN");

                                   }
                               }
                               else{
                                   Toast.makeText(getApplicationContext(), "Username tidak ada!!", Toast.LENGTH_SHORT).show();
                                   btn_Sign_In.setEnabled(true);
                                   btn_Sign_In.setText("SIGN IN");
                               }
                           }

                           @Override
                           public void onCancelled(@NonNull DatabaseError databaseError) {
                               Toast.makeText(getApplicationContext(), "Database Error!!", Toast.LENGTH_SHORT).show();
                           }
                       });
                   }
               }
            }
        });


    }
}
