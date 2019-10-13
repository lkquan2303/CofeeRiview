package com.example.coffereview.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.coffereview.R;
import com.example.coffereview.Model.Post;
import com.example.coffereview.ViewController.PostDelete;
import com.example.coffereview.ViewController.PostUpdate;

import java.util.List;

public class AdapterPost extends ArrayAdapter<Post> {
    Context context;
    int resource;
    List<Post>object;
    TextView txtContent,txtFeeling,txttime,txtngay;


    public AdapterPost(@NonNull Context context, int resource, @NonNull List<Post> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.object = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(resource, parent, false);
        txtContent = view.findViewById(R.id.txtContent);
        txtFeeling = view.findViewById(R.id.txtfeeling);
        txttime = view.findViewById(R.id.txttime);
        txtngay = view.findViewById(R.id.txtngay);
        final Post post = object.get(position);
        txtFeeling.setText(post.getFeeling());
        txtContent.setText(post.getContent());
        txttime.setText(post.getTime());
        txtngay.setText(post.getDay());

        // thông báo sửa xoa
        // start
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                String[] options = {"SỬA", "XÓA"};
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // chọn sửa
                        if(i == 0){
                            // lấy tat ca thong tin post
                            String id = post.getId();
                            String content = post.getContent();
                            String feeling = post.getFeeling();
                            String time = post.getTime();
                            String day = post.getDay();
                            //
                            Intent intent = new Intent(context, PostUpdate.class);
                            intent.putExtra("pid", id);
                            intent.putExtra("pcontent", content);
                            intent.putExtra("pfeeling", feeling);
                            intent.putExtra("ptime", time);
                            intent.putExtra("pday", day);
                            // chuyen qua trang update
                            context.startActivity(intent);
                        }
                        // chọn xoa
                        if(i == 1){
                            // lay tat ca thong tin post
                            String id = post.getId();
                            String content = post.getContent();
                            String feeling = post.getFeeling();
                            String time = post.getTime();
                            String day = post.getDay();
                            //
                            Intent intent = new Intent(context, PostDelete.class);
                            intent.putExtra("pid", id);
                            intent.putExtra("pcontent", content);
                            intent.putExtra("pfeeling", feeling);
                            intent.putExtra("ptime", time);
                            intent.putExtra("pday", day);
                            // chuyen qua trang xoa
                            context.startActivity(intent);
                        }
                    }
                }).create().show();
            }
        });
        //end

        return view;
    }

}

