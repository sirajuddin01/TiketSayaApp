package tiketsaya.astahub.com;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashAct extends AppCompatActivity {

    Animation app_splash,btntotop;
    ImageView app_logo;
    TextView splash_text;


    String USERNAME_KEY = "usernamekey";
    String username_key = "";
    String username_key_new = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //animasi logo
        app_splash = AnimationUtils.loadAnimation(this,R.anim.app_splash);
        btntotop = AnimationUtils.loadAnimation(this,R.anim.btntotop);

        //load elemen image/text
        app_logo = findViewById(R.id.app_logo);
        splash_text = findViewById(R.id.splash_text);

        //run animation
        app_logo.startAnimation(app_splash);
        splash_text.startAnimation(btntotop);

        getUsernameLocal();

    }
    public  void getUsernameLocal(){
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        username_key_new = sharedPreferences.getString(username_key,"");

        if (username_key_new.isEmpty()){
            //setting timer
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //pindah ke activity getstarted
                    Intent kegetstarted = new Intent(SplashAct.this,GetStartedAct.class);
                    startActivity(kegetstarted);
                    finish();
                }
            },2000); //delay splash screen
        }
        else{
            //setting timer
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    //pindah ke activity home
                    Intent kegetstarted = new Intent(SplashAct.this,HomeAct.class);
                    startActivity(kegetstarted);
                    finish();
                }
            },2000); //delay splash screen

        }
    }
}
