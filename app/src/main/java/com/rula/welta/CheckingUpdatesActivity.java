package com.rula.welta;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rula.welta.obj.NewVersionView;

public class CheckingUpdatesActivity extends AppCompatActivity {
    DatabaseReference reference;
    TextView tversion, twhatsnew, tactvers;
    LinearLayout butdownload;
    Uri uri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checking_updates);
        tversion = findViewById(R.id.tversion);
        twhatsnew = findViewById(R.id.twhatsnew);
        butdownload = findViewById(R.id.butdownload);
        tactvers = findViewById(R.id.tactvers);
        reference = FirebaseDatabase.getInstance().getReference();

        butdownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Intent.ACTION_VIEW, uri));
            }
        });

        reference.child("welta").child("lastVersion").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                NewVersionView versionView = snapshot.getValue(NewVersionView.class);
                uri = Uri.parse(versionView.getUrl());
                tversion.setText(versionView.getVersion());
                twhatsnew.setText(versionView.getWhatsnew());
                String versionName = BuildConfig.VERSION_NAME;
                if (versionName.equals(versionView.getVersion())){
                    tactvers.setText("Вы используете\nактуальную версию");
                }else{
                    tactvers.setText("Вы используете не самую\nактуальную версию.\nНекоторые функции могут работать со сбоями");
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("firebase", "loadPost:onCancelled", error.toException());
            }
        });

    }
}