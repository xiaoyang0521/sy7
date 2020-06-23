package com.example.myapplication;


import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    private String newId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sy7_mainactivity);

        Button addData = (Button)findViewById(R.id.add_data);
        addData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //添加数据
                Uri uri = Uri.parse("content://com.example.andriod1.sy7.provider/contacts");//将uri字符串解析成 Uri 对象才并作为参数传入
                ContentValues values = new ContentValues();
                values.put("name","小杨");
                values.put("phone","1720501259");
                values.put("sex","male");
                Uri newUri = getContentResolver().insert(uri,values);//返回值是新数据行的uri
                newId = newUri.getPathSegments().get(1);//新行的id
                if(newId!=null){
                    Toast.makeText(MainActivity.this,"add succeed",Toast.LENGTH_LONG).show();
                }
            }
        });

        Button queryData = (Button)findViewById(R.id.query_data);
        queryData.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                //查询数据
                Uri uri = Uri.parse("content://com.example.andriod1.sy7.provider/contacts");
                Cursor cursor = getContentResolver().query(uri,null,null,null,null);
                //通过Context 中的getContentResolver()方法获取到该类的实例，然后调用query方法将数据从 Cursor 对象中逐个读取出来了
                if(cursor!=null){
                    while(cursor.moveToNext()){
                        String name = cursor.getString(cursor.getColumnIndex("name"));
                        String phone = cursor.getString(cursor.getColumnIndex("phone"));
                        String sex = cursor.getString(cursor.getColumnIndex("sex"));
                        Log.d("MainActivity","people name is"+name);
                    }
                    //cursor.close();
                }
                Toast.makeText(MainActivity.this,"query succeed",Toast.LENGTH_LONG).show();
            }
        });

        Button updateData = (Button)findViewById(R.id.update_data);
        updateData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("content://com.example.andriod1.sy7.provider/contacts"+newId);//将uri字符串解析成 Uri 对象才并作为参数传入
                ContentValues values = new ContentValues();
                values.put("name","小幸");
                values.put("phone","123456789");
                values.put("sex","female");
                getContentResolver().update(uri,values,null,null);

            }
        });

        Button deleteData = (Button) findViewById(R.id.delete_data);
        deleteData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 删除数据
                Uri uri = Uri.parse("content://com.example.andriod1.sy7.provider/contacts"+newId);
                getContentResolver().delete(uri, null, null);
            }
        });



    }

}

