package tiketsaya.astahub.com.Tickets;

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
import tiketsaya.astahub.com.Profile.MyProfile;
import tiketsaya.astahub.com.R;

public class SuccessBuyTicket extends AppCompatActivity {

    Button btn_view_ticket, btn_my_dashboard;
    Animation toptobottom,btntotop,app_splash;
    ImageView icon_success;
    TextView success_text_title,success_text_subtitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success_buy_ticket);

        toptobottom = AnimationUtils.loadAnimation(this,R.anim.toptobottom);
        btntotop = AnimationUtils.loadAnimation(this,R.anim.btntotop);
        app_splash = AnimationUtils.loadAnimation(this,R.anim.app_splash);

        //declare target
        btn_my_dashboard =findViewById(R.id.btn_to_my_dashboard);
        btn_view_ticket = findViewById(R.id.btn_view_tickets);
        icon_success =findViewById(R.id.icon_success_tiket);
        success_text_subtitle =findViewById(R.id.successBuy_text_title);
        success_text_title = findViewById(R.id.successBuy_text_subtitle);

        //run animation
        icon_success.setAnimation(app_splash);

        success_text_title.setAnimation(toptobottom);
        success_text_subtitle.setAnimation(toptobottom);

        btn_view_ticket.setAnimation(btntotop);
        btn_my_dashboard.setAnimation(btntotop);


        btn_view_ticket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoprofile= new Intent(SuccessBuyTicket.this, MyProfile.class);
                startActivity(gotoprofile);
            }
        });

        btn_my_dashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotodashboard = new Intent(SuccessBuyTicket.this, HomeAct.class);
                startActivity(gotodashboard);
            }
        });


    }
}
