package com.anakooter.guesstheshape;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity implements OnClickListener{
	//MediaPlayer music;
	
	private DataSources dbSource; 
	AvailableShapesTable available_obj=AvailableShapesTable.getInstance(); 
	ShapesTable shape_obj=ShapesTable.getInstance();
	List<String> shape_names;
	List<String> shape_types;
	int[] shape_lf;
	ArrayList<Integer> myImageList = new ArrayList<Integer>(Arrays.asList(R.drawable.prac_circle, R.drawable.prac_oval, R.drawable.prac_rect, R.drawable.prac_rh,R.drawable.prac_square,R.drawable.prac_tri));; 
	public static int position = 0;
	MediaPlayer mp;
	Button volume;
	SharedPreferences prefs;
	@Override
    
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        //////////////////////our work////////////////
        
        dbSource = new DataSources(this);
		shape_obj= dbSource.getShapesdata();
		available_obj=dbSource.getAvailableShapesData();
		//shape_obj.getfirstname();
		prefs = getSharedPreferences("sound_value", MODE_PRIVATE); 
		
		if(ShapesTable.name.size()==0)
		{
			
			shape_names=new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.shapes_names)));
			shape_types=new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.shapes_type)));
			//shape_lf=new ArrayList<Integer>(Arrays.asList(getResources().getIntArray(R.array.shapes_lf)));
			shape_lf=new int[18];
			shape_lf=getResources().getIntArray(R.array.shapes_lf);
			//shape_lf.add(R.array.shapes_lf);
			for(int i=0;i<18;i++)
			{
				dbSource.setShapesData(shape_names.get(i), shape_types.get(i), shape_lf[i],i+1);
				dbSource.setAvailableShapes(shape_names.get(i), shape_types.get(i),i+1,0,0);
			}
			shape_obj= dbSource.getShapesdata();
			available_obj=dbSource.getAvailableShapesData();
		}
		
       
		////////////////////////our work////////////////////////////////

        Button play = (Button) findViewById(R.id.play);
        
        play.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) 
			{
				if((AvailableShapesTable.correct_answers!=0)||(AvailableShapesTable.wrong_answers!=0))
				{
					AlertDialog.Builder builder_play=new AlertDialog.Builder(MainActivity.this);
					builder_play.setMessage("Start new game or continue?");
					builder_play.setCancelable(false);
					builder_play.setPositiveButton("New Game", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							AvailableShapesTable.set_data_members();
							Intent PlayActivity = new Intent(MainActivity.this,Play.class);
							startActivityForResult(PlayActivity,1);	
						}
					});
					builder_play.setNegativeButton("Continue", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							dialog.cancel();
							Intent PlayActivity = new Intent(MainActivity.this,Play.class);
							startActivityForResult(PlayActivity,1);	
						}
					});
					AlertDialog alert_play=builder_play.create();
					alert_play.show();
				}
				else
				{
					Intent PlayActivity = new Intent(MainActivity.this,Play.class);
					startActivityForResult(PlayActivity,1);	
				}
							
			}
		});
        
        
        Button learn = (Button) findViewById(R.id.learn);
        learn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent PlayActivity = new Intent(MainActivity.this,LearnActivity.class);
				startActivity(PlayActivity);				
				
			}
		});
        
        Button practice = (Button) findViewById(R.id.practice);
        
        practice.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent PracActivity = new Intent(MainActivity.this,PracticeActivity.class);
					PracActivity.putIntegerArrayListExtra("intent_avail_back", myImageList);
					startActivityForResult(PracActivity,1);							
				
			}
		});
        
        
        volume = (Button) findViewById(R.id.volume);
        if(position==1){
			volume.setBackgroundResource(R.drawable.mute);
		}
		else{
			volume.setBackgroundResource(R.drawable.volume);
		}
        volume.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//music.setLooping(true);
				if(position==0)
				{
				volume.setBackgroundResource(R.drawable.mute);
				mp.pause();
				position=1;
				}
				else
				{
					volume.setBackgroundResource(R.drawable.volume);
					mp.start();
					position=0;
				}
				SharedPreferences.Editor editor = getSharedPreferences("sound_value", MODE_PRIVATE).edit();
				editor.putInt("text", position);
				editor.commit();
				
			}
		});
           
    }
	
	public void resetApplication(View v){
		// TODO ask user in popup!
		AlertDialog.Builder builder= new AlertDialog.Builder(MainActivity.this);
		builder.setMessage("Are you sure you want to reset?");
		builder.setCancelable(false);
		builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				ShapesTable.reset();
				
			}
		});
		builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.cancel();
				
			}
		});
		AlertDialog alert=builder.create();
		alert.show();
	}
	
	@Override
	protected void onActivityResult(int requestCode,int resultCode,Intent data)
	{
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode==1)
		 {
			
			Intent PlayActivity = new Intent(MainActivity.this,Play.class);
			startActivityForResult(PlayActivity,1);
		 }
		 else if(resultCode==2)
		 {
			 AvailableShapesTable.set_data_members();
		 }
		 else if(resultCode==3)
		 {
			startActivityForResult(data, 3);
			
		 }
		 else if(resultCode==4)
		 {
			 myImageList = new ArrayList<Integer>(Arrays.asList(R.drawable.prac_circle, R.drawable.prac_oval, R.drawable.prac_rect, R.drawable.prac_rh,R.drawable.prac_square,R.drawable.prac_tri));
			 
		 }
		
	}
	
	
	@Override
	protected void onPause()
	{
		if(mp.isPlaying()){
		mp.pause();
		mp.stop();
		mp.release();}
		
		super.onStop();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		
	}
	@Override
	protected void onResume(){
		super.onResume();
		
		position = prefs.getInt("text", 0);
		if(position==1){
			volume.setBackgroundResource(R.drawable.mute);
		}
		else{
			volume.setBackgroundResource(R.drawable.volume);
		}
		mp = MediaPlayer.create(this, R.raw.intro);
		mp.setLooping(true);
		if(position==0){
		mp.start();
		}
	}
	
	
       
}
