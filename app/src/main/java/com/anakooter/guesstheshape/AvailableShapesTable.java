package com.anakooter.guesstheshape;

import java.util.ArrayList;

import android.app.Application;

public class AvailableShapesTable extends Application{
	private static AvailableShapesTable singleton=new AvailableShapesTable();
	static ArrayList<String> name;
	static ArrayList<String> type;
	static int size;
	static int correct_answers=0;
	static int wrong_answers=0;
	
	private AvailableShapesTable()
	{}
	public static AvailableShapesTable getInstance()
	{
		return 	singleton;
	}
	
	public AvailableShapesTable(int no) {
		super();
		name = new ArrayList<String>();
		type = new ArrayList<String>();
		size=no;
		
	}
	public ArrayList<String> getNames() {
		return name;
	}
	
	public String getName(int no)
	{
		return name.get(no);
	}
	public String getType(int no)
	{
		return type.get(no);
	}
	public int getSize()
	{
		return size=name.size();
	}
	public static void setName(String[] names) {
		for(int i=0;i<names.length;i++){
			name.set(i, names[i]);
		}
		
	}
	public ArrayList<String> getTypes() {
		
		return type;
	}
	public static void setType(String[] types) {
		for(int i=0;i<types.length;i++){
			type.set(i,types[i]);
		}
		
	}
	public static void set_data_members()
	{
		name=new ArrayList<String>(ShapesTable.name);
		type=new ArrayList<String>(ShapesTable.type);
		 correct_answers=0;
		 wrong_answers=0;
		
	}
	
	
}
