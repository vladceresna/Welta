package com.rula.welta;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.rula.welta.R;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rula.welta.adapters.PostAdapter;
import com.rula.welta.obj.Post;

import java.util.ArrayList;
import java.util.Arrays;

public class PrimaryActivity extends AppCompatActivity {

    MaterialToolbar toolbar;
    DatabaseReference reference;
    ImageButton send, filchabut;
    EditText message, filters;
    String id;
    ListView listView;
    ArrayList<Post> posts;
    ArrayList<Post> allposts;
    PostAdapter postAdapter;
    boolean rostoest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_primary);
        posts = new ArrayList<>();
        allposts = new ArrayList<>();
        listView = findViewById(R.id.listView);
        toolbar = findViewById(R.id.toolbar);
        send = findViewById(R.id.send);
        message = findViewById(R.id.message);
        filters = findViewById(R.id.filters);
        filchabut = findViewById(R.id.filchabut);

        try {
            if(getPackageManager().getPackageInfo("com.rula.rosto",0) != null){
                rostoest = true;
            }else{
                rostoest = false;
            }
        } catch (PackageManager.NameNotFoundException e) {
            rostoest = false;
            e.printStackTrace();
        }


        reference = FirebaseDatabase.getInstance().getReference();

        FirebaseDatabase.getInstance().getReference().child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("id").get()
                .addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {
                        if (!task.isSuccessful()) {
                            Log.e("firebase", "Error getting data", task.getException());
                        } else {
                            Log.d("firebase", String.valueOf(task.getResult().getValue()));
                            id = String.valueOf(task.getResult().getValue());
                            reference.child("welta").child("chat").addChildEventListener(childEventListener());
                        }
                    }
                });

        filchabut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listView.setAdapter(null);
                allposts.clear();
                posts.clear();
                reference.child("welta").child("chat").addChildEventListener(childEventListener());

            }
        });


        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.profile:
                        if(rostoest) {
                            startActivity(new Intent().setClassName("com.rula.rosto", "com.rula.rosto.ProfileActivity"));
                        }else{
                            new MaterialAlertDialogBuilder(PrimaryActivity.this)
                                    .setTitle("Приложение Rosto не обнаружено")
                                    .setMessage("Приложение Rosto нужно для нормального функционирования ретсистемы Rula. В нем находятся общие функции системы, такие как действия с профилями, и т.д. Установить его можна с нашего официального сайта rulav.repl.co")
                                    .setPositiveButton("Хорошо", null).show();
                        }
                        return true;
                    case R.id.checkupd:
                        startActivity(new Intent(PrimaryActivity.this,CheckingUpdatesActivity.class));
                        return true;
                    case R.id.abouti:
                        startActivity(new Intent(PrimaryActivity.this,AboutActivity.class));
                        return true;
                    case R.id.exitacc:
                        new MaterialAlertDialogBuilder(PrimaryActivity.this)
                                .setTitle("Really?")
                                .setMessage("Вы выйдете из аккаунта только в этом сервисе. Чтобы все сервисы Rula работали корректно, нужно чтобы во всех них был выполнен вход под одним и тем же аккаунтом. Вы действительно хотите выйти из вашего аккаунта?")
                                .setPositiveButton("Да", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        FirebaseAuth.getInstance().signOut();
                                        finishAffinity();
                                    }
                                })
                                .setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {}
                                }).show();
                        return true;
                    case R.id.actfil:
                        ArrayList<String> filers = new ArrayList<>();
                        for (int x = allposts.toArray().length-1; x>=0;x--){
                            if (!filers.contains(allposts.get(x).getFilters())) {
                                filers.add(allposts.get(x).getFilters());
                            }
                        }
                        String str = "";
                        String str1 = "";
                        boolean start = true;
                        for (String f:filers){
                            if (f.equals("")){
                                if (start){
                                    start = false;
                                    str += "Основной";
                                    str1 += "_";
                                }else {
                                    str += "#!"+"Основной";
                                    str1 += "#!"+"_";
                                }
                            }else if (start){
                                start = false;
                                str += f;
                                str1 += f;
                            }else {
                                str += "#!" +f;
                                str1 += "#!" +f;
                            }
                        }
                        String[] filerstop = str.split("#!");
                        String[] filerstop1 = str1.split("#!");

                        new MaterialAlertDialogBuilder(PrimaryActivity.this)
                                .setTitle("Фильтры популярные сейчас")
                                .setPositiveButton("Хорошо", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.dismiss();
                                    }
                                })
                                .setItems(filerstop, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        if (filerstop1[i].equals("_")) {
                                            filters.setText("");
                                        }else{
                                            filters.setText(filerstop1[i]);
                                        }
                                        listView.setAdapter(null);
                                        posts.clear();
                                        allposts.clear();
                                        reference.child("welta").child("chat").addChildEventListener(childEventListener());
                                    }
                                })
                                .create()
                                .show();
                        return true;
                    default:
                        throw new IllegalStateException("Unexpected value: " + item.getItemId());
                }
            }
        });
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (id == null) {
                    reference.child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("id").get()
                            .addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DataSnapshot> task) {
                                    if (!task.isSuccessful()) {
                                        Log.e("firebase", "Error getting data", task.getException());
                                    } else {
                                        Log.d("firebase", String.valueOf(task.getResult().getValue()));
                                        id = String.valueOf(task.getResult().getValue());
                                        reference.child("welta").child("chat").push().setValue(new Post(id, message.getText().toString(), String.valueOf(System.currentTimeMillis()), filters.getText().toString()));

                                        message.setText("");
                                    }
                                }
                            });
                } else {
                    reference.child("welta").child("chat").push().setValue(new Post(id, message.getText().toString(), String.valueOf(System.currentTimeMillis()), filters.getText().toString()));
                    message.setText("");
                }

            }
        });
    }
    ChildEventListener childEventListener(){
        return new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Post post = snapshot.getValue(Post.class);
                allposts.add(post);
                if (post.filters == null){
                    post.filters = "";
                }
                if (post.getFilters().equals(filters.getText().toString())) {
                    posts.add(post);
                    postAdapter = new PostAdapter(PrimaryActivity.this, posts, id);
                    listView.setAdapter(postAdapter);
                }
            }
            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {}
            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {}
            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {}
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        };
    }

}