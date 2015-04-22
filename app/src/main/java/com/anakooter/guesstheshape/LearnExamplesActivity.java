package com.anakooter.guesstheshape;

import java.util.ArrayList;
import java.util.Arrays;

import android.app.Activity;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.RelativeLayout;

public class LearnExamplesActivity extends Activity {
	RelativeLayout layout;
	Button[] imageButtons;
	Button prevButton;
	Button nextButton;
	int[] shapeIds;
	final int no_of_examples = 3;
	Intent intent;
	ArrayList<String> shapes_array;
	ArrayList<String> type_array;
	int index;
	String type;
	MediaPlayer mp =new MediaPlayer();
	RatingBar ratingBar1;
	//UNCONSULTED CODE
	
	DataSources obj;
	int starCount;
	
	//END OF UNCONSULTE CODE
	
	// SOUND CODE
		private ArrayList<String>sound_names=new ArrayList<String>(3);
		private ArrayList<Integer>sound_ids=new ArrayList<Integer>(3);
		boolean plays = false, loaded = false;
		float actVolume, maxVolume, volume;
		AudioManager audioManager;
		int counter;
		//SOUND CODE
	
	ShapesTable shape_object=ShapesTable.getInstance();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_learn_examples);
		obj = new DataSources(this);
		obj.open();
		shapes_array = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.shapes)));
		type_array = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.types)));
		shapeIds = new int[no_of_examples];
		layout = (RelativeLayout) findViewById(R.id.realtiveLayoutLearn2);
		
		imageButtons = new Button[no_of_examples];
		imageButtons[0] = (Button) findViewById(R.id.button1);
		imageButtons[1] = (Button) findViewById(R.id.button2);
		imageButtons[2] = (Button) findViewById(R.id.button3);
		nextButton = (Button) findViewById(R.id.button5);
		prevButton = (Button) findViewById(R.id.button4);
		intent = getIntent();
		index = intent.getIntExtra("shape",-1);
		starCount = 0;
		ratingBar1 = (RatingBar) findViewById(R.id.ratingBarExamples1);
		setUI(index);
	}
	public void setUI(int shapeIndex){
		starCount = 0;
		if(shapeIndex==0){
			//prevButton.setAlpha((float)0.5);
			prevButton.setClickable(false);
			prevButton.setBackgroundResource(R.drawable.prevfaded);
			nextButton.setClickable(true);
			nextButton.setBackgroundResource(R.drawable.next);
		}
		else if(shapeIndex>0&&shapeIndex<5){
			prevButton.setClickable(true);
			prevButton.setBackgroundResource(R.drawable.prev);
			nextButton.setClickable(true);
			nextButton.setBackgroundResource(R.drawable.next);
		}
		else if(shapeIndex==5){
			prevButton.setClickable(true);
			prevButton.setBackgroundResource(R.drawable.prev);
			nextButton.setClickable(false);
			nextButton.setBackgroundResource(R.drawable.nextfaded);
		}
		String shapeName = shapes_array.get(shapeIndex);
		type = type_array.get(shapeIndex);
		layout.setBackgroundResource(
				getResources().getIdentifier(shapeName, "drawable", getPackageName()));
		
		
		int  j =0;
		for(int i=0;i<18;i++){
			String type2 = ShapesTable.type.get(i);
			if(type.equals(type2)){
				if(ShapesTable.learningFactor[i]==1){
					String example_name = ShapesTable.name.get(i);
					imageButtons[j].setBackgroundResource(
					getResources().getIdentifier(example_name, "drawable", getPackageName()));
					shapeIds[j] = i;
					j++;
					starCount++;
				}
				else{
					String example_name = ShapesTable.name.get(i);
					example_name+= "_black";
					imageButtons[j].setBackgroundResource(
							getResources().getIdentifier(example_name, "drawable", getPackageName()));
					shapeIds[j] = i;
					j++;
					
				}
			}
		}
		ratingBar1.setProgress(starCount);
		///////////----------------------SOUND-----------------------/////////////////
		
		// the counter will help us recognize the stream id of the sound played  now
		counter = 0;
		sound_names = new ArrayList<String>();
		sound_ids = new ArrayList<Integer>();
		// Load the sounds
		
		
		
		for(int i=0;i<shapeIds.length;i++)
		{
				sound_names.add(ShapesTable.name.get(shapeIds[i]));
				sound_ids.add( getResources().getIdentifier(sound_names.get(i), "raw", getPackageName()));
		}

////////////////////////////////////////sound//////////////////////////////////

	}
	public void onClick(View view){
		
		int id = view.getId();
		
		switch(id){
		case R.id.button1:
			if(ShapesTable.learningFactor[shapeIds[0]] == 0){
				ShapesTable.learningFactor[shapeIds[0]] = 1;
				imageButtons[0].setBackgroundResource(
						getResources().getIdentifier(
								ShapesTable.name.get(shapeIds[0]), "drawable", getPackageName()));
				starCount++;
				ratingBar1.setProgress(starCount);
				
			}
			if(mp!=null){
				
				
				if(mp.isPlaying()){
					Log.i("sds", "Iski pehn nu");
					mp.pause();
					mp.release();

				}
			}
			
			mp=MediaPlayer.create(LearnExamplesActivity.this, sound_ids.get(0));
			mp.start();
			break;
		case R.id.button2:
			if(ShapesTable.learningFactor[shapeIds[1]] == 0){
				ShapesTable.learningFactor[shapeIds[1]] = 1;
				imageButtons[1].setBackgroundResource(
						getResources().getIdentifier(
								ShapesTable.name.get(shapeIds[1]), "drawable", getPackageName()));
				starCount++;
				ratingBar1.setProgress(starCount);
			}
			if(mp!=null){
				
				
				if(mp.isPlaying()){
					Log.i("sds", "Iski pehn nu");
					mp.pause();
					mp.release();

				}
			}
			mp=MediaPlayer.create(LearnExamplesActivity.this, sound_ids.get(1));
			mp.start();
			break;
		case R.id.button3:
			if(ShapesTable.learningFactor[shapeIds[2]] == 0){
				ShapesTable.learningFactor[shapeIds[2]] = 1;
				imageButtons[2].setBackgroundResource(
						getResources().getIdentifier(
								ShapesTable.name.get(shapeIds[2]), "drawable", getPackageName()));
				starCount++;
				ratingBar1.setProgress(starCount);
				
			}
			if(mp!=null){
				
				
				if(mp.isPlaying()){
					mp.pause();
					mp.release();

				}
			}
			mp=MediaPlayer.create(LearnExamplesActivity.this, sound_ids.get(2));
			mp.start();
			break;
		}
				
	}
	public void onBackClick(View v){
		finish();
	}
	public void prevForwBtn(View view){
		int id = view.getId();
		switch(id){
		case R.id.button4:
			index = index-1;
			setUI(index);
			
			// backward case
			break;

		case R.id.button5:
			index = index+1;
			setUI(index);
			
			//forward case:
			break;
		default:
				break;
		}
	}
	
	@Override
	protected void onStop(){
		super.onStop();
		
		obj.truncateShapesTable();
		for(int i=0;i<18;i++){
			obj.setShapesData(ShapesTable.name.get(i),ShapesTable.type.get(i),
					ShapesTable.learningFactor[i],i+1);
		}
		
	}
}
