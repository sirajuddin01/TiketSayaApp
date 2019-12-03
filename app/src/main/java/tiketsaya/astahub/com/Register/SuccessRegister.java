package tiketsaya.astahub.com.Register;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import tiketsaya.astahub.com.HomeAct;
import tiketsaya.astahub.com.R;

public class SuccessRegister extends AppCompatActivity {

    Button btn_explore;
    Animation toptobottom,btntotop,app_splash;
    ImageView icon_success;
    TextView success_text_title,success_text_subtitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success_register);
        //load animation
        toptobottom = AnimationUtils.loadAnimation(this,R.anim.toptobottom);
        btntotop = AnimationUtils.loadAnimation(this,R.anim.btntotop);
        app_splash = AnimationUtils.loadAnimation(this,R.anim.app_splash);

        //declare target
        btn_explore = findViewById(R.id.btn_explore);
        icon_success =findViewById(R.id.icon_success_register);
        success_text_subtitle =findViewById(R.id.successRegister_text_subtitle);
        success_text_title = findViewById(R.id.successRegister_text_title);

        //run animation
        btn_explore.setAnimation(btntotop);
        icon_success.startAnimation(app_splash);
        success_text_subtitle.startAnimation(toptobottom);
        success_text_title.startAnimation(toptobottom);



        btn_explore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gohome = new Intent(SuccessRegister.this, HomeAct.class);
                startActivity(gohome);
            }
        });

    }
}
