package com.rula.welta.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.rula.etime.EasyTime;
import com.rula.etime.TimeData;
import com.rula.welta.PrimaryActivity;
import com.rula.welta.R;
import com.rula.welta.obj.Post;

import java.util.List;

public class PostAdapter extends BaseAdapter {
    private List<Post> posts;
    private Context context;
    private String myid;

    public PostAdapter(Context context, List<Post> posts, String myid) {
        this.myid = myid;
        this.posts = posts;
        this.context = context;

    }
    @Override
    public int getCount() {
        return posts.size();
    }

    @Override
    public Object getItem(int i) {
        return posts.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null){
            view = LayoutInflater.from(context).
                    inflate(R.layout.post_layout, viewGroup, false);
        }
        Post post = (Post) getItem(i);

        LinearLayout lspace = view.findViewById(R.id.lspace);
        LinearLayout rspace = view.findViewById(R.id.rspace);

        TextView textname = view.findViewById(R.id.textname);
        TextView textmessage = view.findViewById(R.id.textmessage);
        TextView texttime = view.findViewById(R.id.texttime);

        textname.setOnClickListener(view1 -> {
            try {
                if(context.getPackageManager().getPackageInfo("com.rula.rosto",0) != null){
                    context.startActivity(new Intent().setClassName("com.rula.rosto", "com.rula.rosto.UserProfileActivity").putExtra("id", post.getSenderid()));
                }else{
                    new MaterialAlertDialogBuilder(context)
                            .setTitle("Rosto application not found")
                            .setMessage("The Rosto application is required for the normal functioning of the Rula ret system. It contains general system functions, such as profile management, etc. You can install it from our official website https://retrula.netlify.app/")
                            .setPositiveButton("Okay", null).show();
                }
            } catch (PackageManager.NameNotFoundException e) {
                new MaterialAlertDialogBuilder(context)
                        .setTitle("Rosto application not found")
                        .setMessage("The Rosto application is required for the normal functioning of the Rula ret system. It contains general system functions, such as profile management, etc. You can install it from our official website https://retrula.netlify.app/")
                        .setPositiveButton("Okay", null).show();
                e.printStackTrace();
            }
        });
        textname.setText(post.getSenderid());
        textmessage.setText(post.getText());


        String s = ".";
        TimeData td = new EasyTime().getConvTime(Long.parseLong(post.getTime()));
        texttime.setText(td.M() +s+ td.D() +" "+ td.H() +":"+ td.m());

        if (myid.equals(post.getSenderid())){
            rspace.setVisibility(View.GONE);
            lspace.setVisibility(View.VISIBLE);
        }else{
            lspace.setVisibility(View.GONE);
            rspace.setVisibility(View.VISIBLE);
        }

        return view;
    }
}
