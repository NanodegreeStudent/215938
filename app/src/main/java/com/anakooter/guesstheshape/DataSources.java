package com.anakooter.guesstheshape;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


public class DataSources {

	// Database fields
	private SQLiteDatabase database;
	private MySQLiteHelper dbHelper;
	private int no_of_shapes = 18;
	public void open() throws SQLException {
	    database = dbHelper.getWritableDatabase();
	  }

	  public void close() {
	    dbHelper.close();
	  }
	public String[] allColumnsShapes = { MySQLiteHelper.COLUMN_NAME_S,
			MySQLiteHelper.COLUMN_TYPE_S,
		MySQLiteHelper.COLUMN_LF_S};
	
	public String[] allColumnsAvailable = { MySQLiteHelper.COLUMN_NAME,
			MySQLiteHelper.COLUMN_TYPE,MySQLiteHelper.COLUMN_CORRECT_ANSWERS,MySQLiteHelper.COLUMN_WRONG_ANSWERS};

	public DataSources (Context context){
		dbHelper = new MySQLiteHelper(context);
		open();
	}
	public void initiate (Context context){
		dbHelper = new MySQLiteHelper(context);
		ContentValues values = new ContentValues();
	    
	    values.put(MySQLiteHelper.COLUMN_ID, "1");
	    values.put(MySQLiteHelper.COLUMN_NAME, "ShapeName");
	    values.put(MySQLiteHelper.COLUMN_TYPE, "shapetype");
	    values.put(MySQLiteHelper.COLUMN_CORRECT_ANSWERS, "0");
	    values.put(MySQLiteHelper.COLUMN_WRONG_ANSWERS, "0");
	    
	    ContentValues values_shapes = new ContentValues();
	    
	    values.put(MySQLiteHelper.COLUMN_ID_S, "1");
	    values.put(MySQLiteHelper.COLUMN_NAME_S, "ShapeName");
	    values.put(MySQLiteHelper.COLUMN_TYPE_S, "shapetype");
	    values.put(MySQLiteHelper.COLUMN_LF_S, "0");
	    
	    try{
	    	database.insert(MySQLiteHelper.TABLE_AVAILABLE, null,values);
	    	
	    }
	    catch(Exception ex){
	    	Log.i("Available Table Error", ex.toString());
	    }
	   
	    try{
	    	database.insert(MySQLiteHelper.TABLE_SHAPES, null,values_shapes);
	    	
	    }
	    catch(Exception ex){
	    	Log.i("Shapes Table Error", ex.toString());
	    }
		
	}
		
	
	public void setShapesData(String name, String type, int learningFactor, int id ){
		
		ContentValues dataToInsert = new ContentValues();
		dataToInsert.put(MySQLiteHelper.COLUMN_ID_S,id);
		dataToInsert.put(MySQLiteHelper.COLUMN_NAME_S, name);
		dataToInsert.put(MySQLiteHelper.COLUMN_TYPE_S, type);
		dataToInsert.put(MySQLiteHelper.COLUMN_LF_S, learningFactor);
		
		try{    
			database.insert(MySQLiteHelper.TABLE_SHAPES, null,dataToInsert);
		}
		catch (Exception e){
		    e.getMessage().toString();
		}
	}
	public void truncate(){
		try{    
			database.delete(MySQLiteHelper.TABLE_AVAILABLE,"1",null);
		}
		catch (Exception e){
		    e.getMessage().toString();
		}
	}
	public void truncateShapesTable(){
		try{    
			database.delete(MySQLiteHelper.TABLE_SHAPES,"1",null);
		}
		catch (Exception e){
		    e.getMessage().toString();
		}
	}
	public void setAvailableShapes(String name, String type, int id,int correct_ans,int wrong_ans ){
		
		
		ContentValues dataToInsert = new ContentValues(); 
		dataToInsert.put(MySQLiteHelper.COLUMN_ID, id);
		dataToInsert.put(MySQLiteHelper.COLUMN_NAME, name);
		dataToInsert.put(MySQLiteHelper.COLUMN_TYPE, type);
		dataToInsert.put(MySQLiteHelper.COLUMN_CORRECT_ANSWERS, correct_ans);
		dataToInsert.put(MySQLiteHelper.COLUMN_WRONG_ANSWERS, wrong_ans);
		
		try{    
			database.insert(MySQLiteHelper.TABLE_AVAILABLE, null,dataToInsert);
		}
		catch (Exception e){
		    e.getMessage().toString();
		}
	}
		
	public ShapesTable getShapesdata() {
	    ShapesTable allData = new ShapesTable(no_of_shapes);
	    Cursor cursor = database.query(MySQLiteHelper.TABLE_SHAPES,
	        allColumnsShapes, null, null, null, null, null);

	    cursor.moveToFirst();
	    int i=0;
	    while (!cursor.isAfterLast()) {
	    	 //String.valueOf(cursor.getInt(0));
	    	 allData.name.add(cursor.getString(0));
	    	 allData.type.add(cursor.getString(1));
	    	 allData.learningFactor[i] = cursor.getInt(2);
	    	 i++;
	    	 cursor.moveToNext();
	    }
	    // Make sure to close the cursor
	    cursor.close();
	    return allData;
	  }
	
	public AvailableShapesTable getAvailableShapesData() {
	    AvailableShapesTable allData = new AvailableShapesTable(no_of_shapes);
	    Cursor cursor = database.query(MySQLiteHelper.TABLE_AVAILABLE,
	        allColumnsAvailable, null, null, null, null, null);

	    cursor.moveToFirst();
	    while (!cursor.isAfterLast()) {
	    	 //cursor.getInt(0);
	    	 allData.name.add( cursor.getString(0));
	    	 allData.type.add(cursor.getString(1));
	    	 allData.correct_answers = cursor.getInt(2);
	    	 allData.wrong_answers = cursor.getInt(3);
	    	 
	    	 cursor.moveToNext();
	     }
	    // Make sure to close the cursor
	    cursor.close();
	    return allData;
	  }
	
}
