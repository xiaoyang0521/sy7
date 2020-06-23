package com.example.myapplication;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class ContactsListview extends AppCompatActivity {
    private List<Contact> lists;
    private ListView listview;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sy7_listview);
        lists = new ArrayList<Contact>();
        listview = (ListView) findViewById(R.id.list_view);

        Button queryData = (Button)findViewById(R.id.Add);
        queryData.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                //查询数据
                Uri uri = Uri.parse("content://com.example.andriod1.sy7.provider/contacts");
                Cursor cursor = getContentResolver().query(uri,null,null,null,null);
                //通过Context 中的getContentResolver()方法获取到该类的实例，然后调用query方法将数据从 Cursor 对象中逐个读取出来了
                if(cursor!=null && cursor.getCount() > 0){
                    while(cursor.moveToNext()){
                        String name = cursor.getString(cursor.getColumnIndex("name"));
                        String phone = cursor.getString(cursor.getColumnIndex("phone"));
                        String sex = cursor.getString(cursor.getColumnIndex("sex"));
                        Log.d("MainActivity","people name is"+name);

                        //把数据封装到Javabean
                        Contact contacts = new Contact();
                        contacts.setName(name);
                        contacts.setPhone(phone);
                        contacts.setSex(sex);

                        lists.add(contacts);

                    }
                    //cursor.close();
                    listview.setAdapter(new MyAdapter());
                }
            }
        });


    }
    private class MyAdapter extends BaseAdapter {


        public int getCount() {
            return lists.size();
        }


        public Object getItem(int position) {
            return null;
        }


        public long getItemId(int position) {
            return 0;
        }


        public View getView(int position, View convertView, ViewGroup parent) {
            View view;
            if (convertView == null) {
                //创建新的view 对象
                view = View.inflate(getApplicationContext(), R.layout.sy7_text, null);

            }else{
                view = convertView;

            }
            //[找到控件用来显示数据]
            TextView name = (TextView) view.findViewById(R.id.contacts_name);
            TextView phone = (TextView) view.findViewById(R.id.contacts_phone);
            TextView sex=(TextView)view.findViewById(R.id.contacts_sex);

            //如何显示数据
            Contact contacts = lists.get(position);
            name.setText(contacts.getName());
            phone.setText(contacts.getPhone());
            sex.setText(contacts.getSex());

            return view;
        }

    }
}
