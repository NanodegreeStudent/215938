package com.anakooter.guesstheshape;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class MySQLiteHelper extends SQLiteOpenHelper {

	public static final String TABLE_AVAILABLE = "Available_Shapes";
	public static final String COLUMN_ID= "id";
	public static final String COLUMN_NAME = "name";	 
	public static final String COLUMN_TYPE  = "type";
	public static final String COLUMN_CORRECT_ANSWERS  = "correct_answers";
	public static final String COLUMN_WRONG_ANSWERS  = "wrong_answers";
	
	public static final String TABLE_SHAPES = "shapes";   //FOR SHAPES USE _S
	public static final String COLUMN_ID_S= "id";
	public static final String COLUMN_NAME_S = "name";	 
	public static final String COLUMN_TYPE_S  = "type";
	public static final String COLUMN_LF_S= "learning_factor";
	
	
    
	  private static final String DATABASE_NAME = "guess_shape.db"; //NAME OF DATABASE
	  private static final int DATABASE_VERSION = 1;

	  // Database creation sql statement
	  private static final String DATABASE_CREATE_AVAIL = "create table IF NOT EXISTS "
	      + TABLE_AVAILABLE + " (" + COLUMN_ID + " INT NOT NULL,"+  COLUMN_NAME + " VARCHAR(15) , "+ COLUMN_TYPE +" VARCHAR(2) NOT NULL,"+ COLUMN_CORRECT_ANSWERS +" INT DEFAULT 0 , "+ COLUMN_WRONG_ANSWERS +" INT DEFAULT 0);";

	  private static final String DATABASE_CREATE_SHAPES = "create table IF NOT EXISTS "
		      + TABLE_SHAPES + " (" + COLUMN_ID_S + " INT NOT NULL,"+  COLUMN_NAME_S + " VARCHAR(15) , "+ COLUMN_TYPE_S +" VARCHAR(2) NOT NULL , "+ COLUMN_LF_S +" INT DEFAULT 0);";
	  public MySQLiteHelper(Context context) {
	    super(context, DATABASE_NAME, null, DATABASE_VERSION);
	    
	  }

	  @Override
	  public void onCreate(SQLiteDatabase database) {
	    database.execSQL(DATABASE_CREATE_AVAIL);
	    database.execSQL(DATABASE_CREATE_SHAPES);
	    
	  }

	  @Override
	  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	    //Log.w(MySQLiteHelper.class.getName(),
	    //    "Upgrading database from version " + oldVersion + " to "
	    //        + newVersion + ", which will destroy all old data");
	   // db.execSQL("DROP TABLE IF EXISTS " + TABLE_COMMENTS);
	  //  onCreate(db);
	  }

	} 
