package com.yourstar.cenotomy.Activities;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.youstar.bloggerssport.R;

public class Select extends AppCompatActivity {

    private GridView gridview;
    private String[] blogname;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);
        blogname = getResources().getStringArray(R.array.blognames);
        initView();
    }

    private void initView() {
        gridview = (GridView) findViewById(R.id.gridview);
        gridview.setAdapter(new GridAdapter());
    }

    private class GridAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return blogname.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(convertView==null){
                convertView  = getLayoutInflater().inflate(R.layout.singleblog,null,false);
                TextView txtblogname = convertView.findViewById(R.id.theblogname);
                txtblogname.setText(blogname[position]);
            }

            return convertView;
        }


    }

}
