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
                            .setTitle("Support has ended")
                            .setMessage("This version is outdated and no longer supported. If the application does not resume working after a long time, we recommend downloading the latest version from our official website https://retrula.netlify.app/")
                            .setPositiveButton("Okay", (dialogInterface, i) -> finishAffinity())
                            .setOnDismissListener(dialogInterface -> finishAffinity())
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

                    butpp.setOnClickListener(view -> tosite("https://retrula.netlify.app/privacypolicy/rulaprivacypolicy.html"));
                    buttu.setOnClickListener(view -> tosite("https://retrula.netlify.app/terms/rulaterms.html"));

                    komprenis.setOnClickListener(view -> {
                        if (regscr) {
                            aptempt.setVisibility(View.GONE);

                            inputmail.setVisibility(View.VISIBLE);
                            inputpassword.setVisibility(View.VISIBLE);
                            buttonup.setVisibility(View.VISIBLE);
                            buttondown.setVisibility(View.VISIBLE);
                            desctext.setVisibility(View.VISIBLE);

                            desctext.setText("Do you have an account?");
                            buttondown.setText("Sing in");
                            buttonup.setText("Registration");
                            vmarg.setVisibility(View.VISIBLE);
                            inputname.setVisibility(View.VISIBLE);
                            inputrepassword.setVisibility(View.VISIBLE);
                            inputid.setVisibility(View.VISIBLE);

                        }
                    });
                    buttondown.setOnClickListener(view -> {
                        if((regscr)){
                            desctext.setText("Do you have an account?");
                            buttondown.setText("Registration");
                            buttonup.setText("Sign in");
                            vmarg.setVisibility(View.GONE);
                            inputname.setVisibility(View.GONE);
                            inputrepassword.setVisibility(View.GONE);
                            inputid.setVisibility(View.GONE);
                            regscr = false;
                        }else{
                            aptempt.setVisibility(View.VISIBLE);
                            inputmail.setVisibility(View.GONE);
                            inputpassword.setVisibility(View.GONE);
                            buttonup.setVisibility(View.GONE);
                            buttondown.setVisibility(View.GONE);
                            desctext.setVisibility(View.GONE);

                            regscr = true;
                        }
                    });
                    buttonup.setOnClickListener(view -> {

                        if (regscr){
                            if(inputid.getText().toString().equals("")) {
                                Toast.makeText(MainActivity.this, "Fill RulaID", Toast.LENGTH_LONG).show();
                            }else if (inputmail.getText().toString().equals("")){
                                Toast.makeText(MainActivity.this, "Fill email", Toast.LENGTH_LONG).show();
                            }else if (inputpassword.getText().toString().equals("")){
                                Toast.makeText(MainActivity.this, "Fill password", Toast.LENGTH_LONG).show();
                            }else if (inputrepassword.getText().toString().equals("")){
                                Toast.makeText(MainActivity.this, "Fill submit password", Toast.LENGTH_LONG).show();
                            }else if (inputname.getText().toString().equals("")){
                                Toast.makeText(MainActivity.this, "Fill name", Toast.LENGTH_LONG).show();
                            }
                            else{

                                createUser(inputmail.getText().toString(), inputpassword.getText().toString());
                            }
                        }else{
                            if(inputmail.getText().toString().equals("")) {
                                Toast.makeText(MainActivity.this, "Fill email", Toast.LENGTH_LONG).show();
                            }else if (inputpassword.getText().toString().equals("")){
                                Toast.makeText(MainActivity.this, "Fill password", Toast.LENGTH_LONG).show();
                            }
                            else{
                                signIn(inputmail.getText().toString(), inputpassword.getText().toString());
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
        Auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "createUserWithEmail:success");
                        FirebaseUser user = Auth.getCurrentUser();
                        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                .setDisplayName(inputname.getText().toString()).build();
                        user.updateProfile(profileUpdates)
                                .addOnCompleteListener(task1 -> {
                                    if (task1.isSuccessful()) {
                                        Log.d(TAG, "User profile updated.");
                                        writeNewUser(user.getDisplayName(), inputid.getText().toString(), user.getUid());
                                        startActivity(new Intent(MainActivity.this , PrimaryActivity.class));
                                        finish();
                                    }
                                });


                    } else {
                        Log.w(TAG, "createUserWithEmail:failure", task.getException());
                        Toast.makeText(MainActivity.this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                    }
                });

    }
    private void signIn(String email, String password){
        Auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "signInWithEmail:success");
                        FirebaseUser user = Auth.getCurrentUser();
                        startActivity(new Intent(MainActivity.this, PrimaryActivity.class));
                        finish();
                    } else {
                        Log.w(TAG, "signInWithEmail:failure", task.getException());
                        Toast.makeText(MainActivity.this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
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