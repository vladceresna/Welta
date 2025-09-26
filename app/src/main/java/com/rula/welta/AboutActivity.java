package com.rula.welta;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class AboutActivity extends AppCompatActivity {
    LinearLayout buttelbot, butusmail, butofsite;
    TextView bprivpoll, bterms;

    Intent intent = new Intent(Intent.ACTION_VIEW);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        buttelbot = findViewById(R.id.buttelbot);
        butusmail = findViewById(R.id.butusmail);
        butofsite = findViewById(R.id.butofsite);
        bprivpoll = findViewById(R.id.bprivpoll);
        bterms = findViewById(R.id.bterms);

        bprivpoll.setOnClickListener(view -> tosite("https://retrula.netlify.app/privacypolicy/rulaprivacypolicy.html"));
        bterms.setOnClickListener(view -> tosite("https://retrula.netlify.app/terms/rulaterms.html"));

        buttelbot.setOnClickListener(view -> tosite("https://t.me/rula_support_bot/"));
        butusmail.setOnClickListener(view -> tosite("mailto:vladceresna6@gmail.com"));
        butofsite.setOnClickListener(view -> tosite("https://rulav.repl.co"));
    }
    private void tosite(String url){
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }
}