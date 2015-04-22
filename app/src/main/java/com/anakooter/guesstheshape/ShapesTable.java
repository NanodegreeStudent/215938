package com.anakooter.guesstheshape;

import java.util.ArrayList;

import android.app.Application;

public class ShapesTable extends Application{

	private static ShapesTable singleton=new ShapesTable();
	static ArrayList<String> name;
	static ArrayList<String> type;
	static int   [] learningFactor;
	static int size;
		
	private ShapesTable()
	{}
	public static ShapesTable getInstance()
	{
		return 	singleton;
	}
	public ShapesTable(int no) {
		super();
		name = new ArrayList<String>();
		type = new ArrayList<String>();
		learningFactor = new int[no];
		size=no;
	}
	public static void reset()
	{
		AvailableShapesTable.set_data_members();
		for(int i=0;i<learningFactor.length;i++)
		{
			learningFactor[i]=0;
		}
	}
	public String getfirstname()
	{
		return name.get(0);
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
		return size;
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
	public int[] getLearningFactor() {
		return learningFactor;
	}
	public static void setLearningFactor(int[] learningFactors) {
		for(int i=0;i<learningFactors.length;i++){
			learningFactor[i] = learningFactors[i];
		}
	}
	
}
