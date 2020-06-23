package com.example.myapplication;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;


//在onCreate中实例化MyDatabase，并调用getWritableDatabase方法创建数据库和表

public class MyContentProvider extends ContentProvider {
    private MyDateBase myHelper;
    private static UriMatcher uriMatcher;
    public static final int CONTACTS_DIR = 0;//自定义代码
    public static final int CONTACTS_ITEM = 1;

    public static final String AUTHORITY = "com.example.andriod1.sy7.provider";//权限

    static {
        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        //UriMatcher中提供了一个 addURI()方法，这个方法接收三个参数，可以分别把权限、路径和一个自定义代码传进去。
        uriMatcher.addURI(AUTHORITY,"contacts",CONTACTS_DIR);
        uriMatcher.addURI(AUTHORITY,"contacts/#",CONTACTS_ITEM);

    }

    public boolean onCreate() {//初始化内容提供器的时候调用。通常会在这里完成对数据库的创建和升级等操作，返回 true 表示内容提供器初始化成功，返回 false 则表示失败

        myHelper=new MyDateBase(getContext(),"Contact.db",null,1);
        SQLiteDatabase db = myHelper.getWritableDatabase();//在onCreate中实例化MyDatabase，并调用getWritableDatabase方法创建数据库和表。

        return true;
    }

    //从内容提供器中查询数据。使用 uri 参数来确定查询哪张表
    public Cursor query(Uri uri,  String[] projection,  String selection,  String[] selectionArgs,  String sortOrder) {

        SQLiteDatabase db = myHelper.getReadableDatabase();
        Cursor cursor=null;
        switch (uriMatcher.match(uri)){//返回值是某个能够匹配这个 Uri 对象所对应的自定义代码，利用这个代码，我们就可以判断出调用方期望访问的是哪张表中的数据了。

            case CONTACTS_DIR://查询Contacts表里的所有数据
                cursor = db.query("Contacts",projection,selection,selectionArgs,null,null,sortOrder);
                break;

            case CONTACTS_ITEM://查询Contacts表里的一条数据
                String contactsId=uri.getPathSegments().get(1);
                /*此时URI为　content://com.example.sy7.provider/Contacts/#
                使用uri.getPathSegments会将URI权限之后的部分即从Contacts开始的部分按“/”进行分割————"content://com.example.sy7.provider/"与"Contacts/#"
                返回值是一个元素为String的List,每个元素都是从Uri截取出来的一部分，在这里Contacts和#为填入list的两个元素
                所以这个list中的第0个位置存放的就是路径，第一个位置存放的就是Id了，get(1)即取出Id
                 */
                cursor = db.query("Contacts",projection,"id = ?",new String[]{contactsId},null,null,sortOrder);
                /*第三个参数selection:where column=value 为指定where的约束条件 其中？为占位符
                第四个参数selectionArgs为where中的占位符提供具体的值contactsId
                 */
                break;
            default:
                break;
        }

        return cursor;//查询完成后返回的仍然是一个 Cursor 对象
    }

    //根据传入的内容 URI 来返回相应的 MIME 类型
    public String getType( Uri uri) {
        switch (uriMatcher.match(uri)){
            case CONTACTS_DIR:
                return "vnd.android.cursor.dir/vnd.com.example.andriod1.sy7.provide.contacts";
            case CONTACTS_ITEM:
                return "vnd.android.cursor.item/vnd.com.example.andriod1.sy7.provider.contacts";
        }

        return null;
    }

    
    //向内容提供器中添加一条数据。使用 uri 参数来确定要添加到的表
    public Uri insert( Uri uri,  ContentValues values) {
        SQLiteDatabase db = myHelper.getWritableDatabase();
        Uri uri1 = null;
        switch (uriMatcher.match(uri)){
            case CONTACTS_DIR:
            case CONTACTS_ITEM:
                Long newContactsId = db.insert("Contracts",null,values);
                uri1 = Uri.parse("content://"+AUTHORITY+"/contacts/"+newContactsId);
                break;
            default:
                break;
        }
        return uri1;
    }

    //更新内容提供器中已有的数据。使用 uri 参数来确定更新哪一张表中的数据
    public int update( Uri uri,  ContentValues values,  String selection,  String[] selectionArgs) {
        SQLiteDatabase db = myHelper.getWritableDatabase();
        int updatedRows = 0;
        switch (uriMatcher.match(uri)){
            case CONTACTS_DIR:
                updatedRows = db.update("Contacts",values,selection,selectionArgs);
                break;
            case CONTACTS_ITEM:
                String contactsId=uri.getPathSegments().get(1);
                updatedRows = db.update("Contacts",values,"id = ?",new String[]{contactsId});
                break;
            default:
                break;
        }
        return updatedRows;
    }


    //从内容提供器中删除数据。使用 uri 参数来确定删除哪一张表中的数据
    public int delete( Uri uri,  String selection,  String[] selectionArgs) {

        SQLiteDatabase db = myHelper.getWritableDatabase();
        int deletedRows = 0;
        switch (uriMatcher.match(uri)){
            case CONTACTS_DIR:
                deletedRows = db.delete("Contacts",selection,selectionArgs);
                break;
            case CONTACTS_ITEM:
                String contactsId=uri.getPathSegments().get(1);
                deletedRows = db.delete("Contacts","id = ?",new String[]{contactsId});
                break;
            default:
                break;
        }
        return deletedRows;
    }
}
