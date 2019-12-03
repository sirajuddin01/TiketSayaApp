package tiketsaya.astahub.com;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import tiketsaya.astahub.com.Register.Register1;

public class GetStartedAct extends AppCompatActivity {

    Button btn_Sign_In, btn_new_account_create;
    Animation toptobottom,btntotop;
    ImageView emblem;
    TextView intro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_started);
        toptobottom = AnimationUtils.loadAnimation(this,R.anim.toptobottom);
        btntotop = AnimationUtils.loadAnimation(this,R.anim.btntotop);

        emblem = findViewById(R.id.emblem);
        intro = findViewById(R.id.intro);
        btn_Sign_In = findViewById(R.id.btn_Sign_In);
        btn_new_account_create = findViewById(R.id.btn_new_account_create);


        //run animation
        emblem.startAnimation(toptobottom);
        intro.startAnimation(toptobottom);
        btn_Sign_In.startAnimation(btntotop);
        btn_new_account_create.startAnimation(btntotop);


        btn_Sign_In.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goSignIn = new Intent(GetStartedAct.this,SignIn.class);
                startActivity(goSignIn);

            }
        });


        btn_new_account_create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goregister = new Intent(GetStartedAct.this, Register1.class);
                startActivity(goregister);
            }
        });
    }
}
