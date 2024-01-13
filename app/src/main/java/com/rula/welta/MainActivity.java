package com.rula.welta;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rula.welta.obj.User;


public class MainActivity extends AppCompatActivity {

    private boolean regscr;

    private Button butpp, buttu;

    private Button komprenis;
    private LinearLayout aptempt;
    private Button buttondown;
    private Button buttonup;
    private EditText inputname;
    private EditText inputmail;
    private EditText inputpassword;
    private EditText inputrepassword;
    private EditText inputid;
    private View vmarg;
    private TextView desctext;

    private ScrollView scrview;

    private FirebaseAuth Auth;
    private DatabaseReference dbr;

    private final String TAG = "Verification";

    Intent intent = new Intent(Intent.ACTION_VIEW);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        scrview = findViewById(R.id.scrview);
        scrview.setVisibility(View.GONE);

        dbr = FirebaseDatabase.getInstance().getReference();

        String[] splitvers = BuildConfig.VERSION_NAME.split("\\.");
        StringBuilder adaptvers = new StringBuilder();
        for(String elvers: splitvers){
            adaptvers.append(elvers);
        }
        dbr.child("welta").child("work").child(adaptvers.toString()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!snapshot.getValue(Boolean.class)){

                    new MaterialAlertDialogBuilder(MainActivity.this)
                            .setTitle("Поддержка закончена")
                            .setMessage("Эта версия не актуальная, поэтому больше не поддерживается. Если работа приложения долгое время не восстановлена, то рекомендуем скачать актуальную версию с нашего официального сайта rulav.repl.co")
                            .setPositiveButton("Хорошо", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    finishAffinity();
                                }
                            })
                            .setOnDismissListener(new DialogInterface.OnDismissListener() {
                                @Override
                                public void onDismiss(DialogInterface dialogInterface) {
                                    finishAffinity();
                                }
                            })
                            .show();
                }else{
                    butpp = findViewById(R.id.butpp);
                    buttu = findViewById(R.id.buttu);

                    aptempt = findViewById(R.id.aptempt);
                    komprenis = findViewById(R.id.komprenis);
                    inputname = findViewById(R.id.inputname);
                    inputmail = findViewById(R.id.inputmail);
                    inputpassword = findViewById(R.id.inputpassword);
                    inputrepassword = findViewById(R.id.inputreppassword);
                    inputid = findViewById(R.id.inputid);
                    vmarg = findViewById((R.id.vmarg));
                    regscr = false;
                    buttonup = findViewById(R.id.buttonup);
                    buttondown = findViewById(R.id.buttondown);
                    desctext = findViewById(R.id.desctext);

                    Auth = FirebaseAuth.getInstance();
                    FirebaseUser currentUser = Auth.getCurrentUser();
                    if(currentUser != null){
                        //userIsSigned
                        startActivity(new Intent(MainActivity.this, PrimaryActivity.class));
                        finish();
                    }else{
                        scrview.setVisibility(View.VISIBLE);
                        scrview.setBackground(getDrawable(R.drawable.backf2));
                    }

                    butpp.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            tosite("https://rulav.repl.co/privacypolicy/rulaprivacypolicy.html");
                        }
                    });
                    buttu.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            tosite("https://rulav.repl.co/terms/rulaterms.html");
                        }
                    });

                    komprenis.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (regscr) {
                                aptempt.setVisibility(View.GONE);

                                inputmail.setVisibility(View.VISIBLE);
                                inputpassword.setVisibility(View.VISIBLE);
                                buttonup.setVisibility(View.VISIBLE);
                                buttondown.setVisibility(View.VISIBLE);
                                desctext.setVisibility(View.VISIBLE);

                                desctext.setText("У вас уже есть аккаунт?");
                                buttondown.setText("Вход");
                                buttonup.setText("Зарегистрироваться");
                                vmarg.setVisibility(View.VISIBLE);
                                inputname.setVisibility(View.VISIBLE);
                                inputrepassword.setVisibility(View.VISIBLE);
                                inputid.setVisibility(View.VISIBLE);

                            }
                        }
                    });
                    buttondown.setOnClickListener(new View.OnClickListener(){
                        @Override
                        public void onClick(View view) {
                            // нажатие нижней кнопки, она может быть как для того чтобы перейти на "экран" входа,
                            // когда человек видит "экран" регистрации, так и наоборот
                            if((regscr)){
                                // меняем "экраны"
                                // если нужно войти
                                desctext.setText("У вас еще нет аккаунта?");
                                buttondown.setText("Регистрация");
                                buttonup.setText("Войти");
                                vmarg.setVisibility(View.GONE);
                                inputname.setVisibility(View.GONE);
                                inputrepassword.setVisibility(View.GONE);
                                inputid.setVisibility(View.GONE);
                                regscr = false;
                            }else{
                                // если нужно зарегаться
                                aptempt.setVisibility(View.VISIBLE);
                                inputmail.setVisibility(View.GONE);
                                inputpassword.setVisibility(View.GONE);
                                buttonup.setVisibility(View.GONE);
                                buttondown.setVisibility(View.GONE);
                                desctext.setVisibility(View.GONE);

                                regscr = true;
                            }
                        }
                    });
                    buttonup.setOnClickListener(new View.OnClickListener() {
                        // нажатие верхней кнопки, она может быть как для регистрации,
                        // когда человек видит "экран" регистрации, так и наоборот с входом
                        @Override
                        public void onClick(View view) {

                            if (regscr){
                                if(inputid.getText().toString().equals("")) {
                                    Toast.makeText(MainActivity.this, "Заполните RulaID", Toast.LENGTH_LONG).show();
                                }else if (inputmail.getText().toString().equals("")){
                                    Toast.makeText(MainActivity.this, "Заполните почту", Toast.LENGTH_LONG).show();
                                }else if (inputpassword.getText().toString().equals("")){
                                    Toast.makeText(MainActivity.this, "Заполните пароль", Toast.LENGTH_LONG).show();
                                }else if (inputrepassword.getText().toString().equals("")){
                                    Toast.makeText(MainActivity.this, "Заполните поле повтора пароль", Toast.LENGTH_LONG).show();
                                }else if (inputname.getText().toString().equals("")){
                                    Toast.makeText(MainActivity.this, "Заполните имя", Toast.LENGTH_LONG).show();
                                }
                                else{

                                    createUser(inputmail.getText().toString(), inputpassword.getText().toString());
                                }
                            }else{
                                if(inputmail.getText().toString().equals("")) {
                                    Toast.makeText(MainActivity.this, "Заполните почту", Toast.LENGTH_LONG).show();
                                }else if (inputpassword.getText().toString().equals("")){
                                    Toast.makeText(MainActivity.this, "Заполните пароль", Toast.LENGTH_LONG).show();
                                }
                                else{
                                    signIn(inputmail.getText().toString(), inputpassword.getText().toString());
                                }
                            }
                        }
                    });
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });



    }
    private void createUser(String email, String password){
        //регистрация юзера
        Auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // когда юзер успешно зарегистрирован
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = Auth.getCurrentUser();
                            //задаем отображаемое имя в userauth
                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(inputname.getText().toString()).build();
                            user.updateProfile(profileUpdates)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                //если успешно, то записываем пользователя
                                                Log.d(TAG, "User profile updated.");
                                                writeNewUser(user.getDisplayName(), inputid.getText().toString(), user.getUid());
                                                startActivity(new Intent(MainActivity.this , PrimaryActivity.class));
                                                finish();
                                            }
                                        }
                                    });


                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }
    private void signIn(String email, String password){
        Auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = Auth.getCurrentUser();
                            startActivity(new Intent(MainActivity.this, PrimaryActivity.class));
                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }
    private void writeNewUser(String name, String id, String uid) {
        User userobj = new User(name, id);
        dbr = FirebaseDatabase.getInstance().getReference();
        dbr.child("users").child(uid).setValue(userobj);
    }
    private void tosite(String url){
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }

}