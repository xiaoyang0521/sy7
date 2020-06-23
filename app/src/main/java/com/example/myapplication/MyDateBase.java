package com.example.myapplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;


/*里面有一个表contacts用于保存联系人的基本信息，这些信息包括姓名，联系电话，性别。*/
public class MyDateBase extends SQLiteOpenHelper {

    public static final String CREATE_CONTACTS =" create table Contacts("
            + "id integer primary key autoincrement,"
            + "name string,"
            + "phone string,"
            + "sex string)";

    private Context mContext;

    public MyDateBase(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext = context;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_CONTACTS);
        Toast.makeText(mContext,"Create succeeded !",Toast.LENGTH_LONG).show();

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists Contacts");
        onCreate(db);
    }
}
