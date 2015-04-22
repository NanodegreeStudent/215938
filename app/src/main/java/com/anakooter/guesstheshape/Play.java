package com.anakooter.guesstheshape;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class Play extends Activity implements OnClickListener {
	
	ImageView view;
	Button [] options;
	boolean valid_shape=false;
	Intent intent;
	int size;
	int index;
	String shape_drawable_name;
	String shape_drawable_type;
	int shape_id;
	String s_name;
	String s_type;
	int s_index;
	//Animation variables code
	TextView textview,textview2;
	ImageView imageview2;
	//Animation variables code
	AvailableShapesTable available_object=AvailableShapesTable.getInstance();
	ShapesTable shape_object=ShapesTable.getInstance();
	AlphaAnimation setFloatOne,setFloatZero ;
	AlphaAnimation fade_out;
	int x;
	Button btn_score;
	DataSources db_obj;
	ImageView starImage;
	double
	scoreValue = 0;
	int position = 0;
	SharedPreferences prefs;
	MediaPlayer mp=new MediaPlayer();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_play);
		db_obj = new DataSources(this);

		setFloatOne = new AlphaAnimation(0.0f, 1f);
		setFloatZero = new AlphaAnimation(1.0f,0.0f);
		setFloatOne.setDuration(0);
		setFloatZero.setDuration(0);
		setFloatOne.setFillAfter(true);
		setFloatZero.setFillAfter(true);
		fade_out = new AlphaAnimation(1f, 0f);
	    fade_out.setDuration(600);
	    fade_out.setFillAfter(true);
		getshape();
		setimage();
		setbuttons();
	}
	public void getshape()
	{
		size=available_object.getSize();
		index=(new Random().nextInt(100))%size;
		s_name=available_object.getName(index);
		s_type=available_object.getType(index);
		shape_id=getResources().getIdentifier(s_name, "drawable", getPackageName());
		prefs = getSharedPreferences("sound_value", MODE_PRIVATE); 
		position = prefs.getInt("text", 0);
	}
	public void setimage()
	{
		view=new ImageView(this);
		view=(ImageView) findViewById(R.id.outline);
		view.setAnimation(setFloatOne);
		imageview2 = new ImageView(this);
		imageview2 = (ImageView) findViewById(R.id.imageView1);
        
		starImage = new ImageView(this);
		starImage = (ImageView) findViewById(R.id.imageView2);
		starImage.startAnimation(setFloatZero);
		imageview2.startAnimation(setFloatZero);
		if(s_type.equals("c"))
		{
			view.setImageResource(R.drawable.circle_shape);
		}
		else if(s_type.equals("r"))
		{
			view.setImageResource(R.drawable.rectangle_shape);
		}
		else if(s_type.equals("rh"))
		{
			view.setImageResource(R.drawable.rhombus_shape);
		}
		else if(s_type.equals("o"))
		{
			view.setImageResource(R.drawable.oval_shape);
		}
		else if(s_type.equals("t"))
		{
			view.setImageResource(R.drawable.triangle_shape);
		}
		else if(s_type.equals("s"))
		{
			view.setImageResource(R.drawable.square_shape);
		}
		
	}
	public void setbuttons()
	{
		AlphaAnimation buttons_alpha_in = new AlphaAnimation(0f, 1f);
		
		
		final Button volume = (Button) findViewById(R.id.volume);
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
				
				position=1;
				
				
				}
				else
				{
					volume.setBackgroundResource(R.drawable.volume);
					
					position=0;
				}
				SharedPreferences.Editor editor = getSharedPreferences("sound_value", MODE_PRIVATE).edit();
				editor.putInt("text", position);
				editor.commit();
			}
		});
		// UNCHECKED COD
		textview = (TextView) findViewById(R.id.textView1);
		btn_score = (Button) findViewById(R.id.button_score);
		textview.setAnimation(setFloatZero);
	
		scoreValue=(AvailableShapesTable.correct_answers*10)-(AvailableShapesTable.wrong_answers*2);
	    if(scoreValue<0)
	    {
	    	AvailableShapesTable.wrong_answers--;
	       	scoreValue=0;
	    }
		int integerValue = (int) scoreValue;
		btn_score.setText(String.valueOf(integerValue));
		//UNCHECKED COD
		options=new Button[4];
		options[0]=(Button) findViewById(R.id.opt1);
		options[1]=(Button) findViewById(R.id.opt2);
		options[2]=(Button) findViewById(R.id.opt3);
		options[3]=(Button) findViewById(R.id.opt4);
		options[0].setOnClickListener(this);
		options[1].setOnClickListener(this);
		options[2].setOnClickListener(this);
		options[3].setOnClickListener(this);
		
		ArrayList<String> array_names=new ArrayList<String>(ShapesTable.name);	
		ArrayList<String> array_types=new ArrayList<String>(ShapesTable.type);	
		
		for(int j=0;j<array_types.size();j++)
		{
			if(array_types.get(j).equals(s_type)&&(!array_names.get(j).equals(s_name)))
			{
				array_types.remove(j);
				array_names.remove(j);
				j--;
			}
		}
		for(int i=0;i<4;i++)
		{
			
			x=i;
			buttons_alpha_in.setAnimationListener(new AnimationListener() {
			
				@Override
				public void onAnimationStart(Animation animation) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onAnimationRepeat(Animation animation) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onAnimationEnd(Animation animation) {
					// TODO Auto-generated method stub
					
					ScaleAnimation animation2=new ScaleAnimation((float)0.8,(float)1.1,(float)0.8,(float)1.1,(float)50,(float)50);
					animation2.setDuration(1000);
					animation2.setRepeatCount(Animation.INFINITE);
					animation2.setRepeatMode(Animation.REVERSE);
					options[0].setAnimation(animation2);
					options[1].setAnimation(animation2);
					options[2].setAnimation(animation2);
					options[3].setAnimation(animation2);
					
				}
			});
			options[i].setAnimation(buttons_alpha_in);
			Random value=new Random();
			int index1=(value.nextInt(100))%array_names.size();
			shape_drawable_name=array_names.get(index1);
			shape_drawable_type=array_types.get(index1);
			shape_id=getResources().getIdentifier(shape_drawable_name, "drawable", getPackageName());
			options[i].setBackgroundResource(shape_id);
			options[i].setId(shape_id);
			if(shape_drawable_name.equals(s_name))
			{
				s_index=shape_id;
				valid_shape=true;
			}
			for(int j=0;j<array_types.size();j++)
			{
				if(array_types.get(j).equals(shape_drawable_type))
				{
					array_types.remove(j);
					array_names.remove(j);
					j--;
				}
			}
		}
		if(!valid_shape)
		{
			Random value1=new Random();
			int value2=value1.nextInt(100)%4;
			shape_id=getResources().getIdentifier(s_name, "drawable", getPackageName());
			options[value2].setBackgroundResource(shape_id);
			options[value2].setId(shape_id);
			s_index=shape_id;
		}
	}	

	@Override
	public void onClick(View v) {
		Button btn=(Button) v;
		if(mp.isPlaying())
		{
			mp.release();
		}
		int temp=btn.getId();
		if(temp!=s_index)
		{
			AvailableShapesTable.wrong_answers++;
			imageview2 = new ImageView(this);
			imageview2 = (ImageView) findViewById(R.id.imageView1);
			if(position==0){
				mp=MediaPlayer.create(Play.this, R.raw.wrong_answer);
				mp.start();
			}
			
			
			imageview2.setImageResource(R.drawable.cross);				
		    AlphaAnimation anim = new AlphaAnimation(0.0f, 1f);
		    anim.setDuration(1000);
		    anim.setFillAfter(true);
		    
		    anim.setAnimationListener(new AnimationListener() {

		        @Override
		        public void onAnimationStart(Animation animation) {
		        	
		        }

		        @Override
		        public void onAnimationRepeat(Animation animation) {
		        }

		        @Override
		        public void onAnimationEnd(Animation animation) {
		            // Do stuff on animation end
		        	
		        	//animation.setAnimationListener(null);
		        	
				        	
					AlphaAnimation anim2 = new AlphaAnimation(1f, 0f);
					anim2.setDuration(1000);
					anim2.setFillAfter(true);
					imageview2.setAnimation(anim2);
					animation.setAnimationListener(null);
					
						        	
		        }
		    });
		    Log.i("Score Value",String.valueOf(scoreValue));
		    Log.i("Numerator", String.valueOf(AvailableShapesTable.correct_answers));
		    Log.i("Denominator", String.valueOf(AvailableShapesTable.correct_answers + AvailableShapesTable.wrong_answers));
		    scoreValue=(AvailableShapesTable.correct_answers*10)-(AvailableShapesTable.wrong_answers*2);
		    if(scoreValue<0)
		    {
		    	AvailableShapesTable.wrong_answers--;
		    	scoreValue=0;
		    }
		    int integerValue = (int) scoreValue;
			btn_score.setText(String.valueOf(integerValue));
		   // btn_score.setText(String.valueOf(scoreValue));
		    imageview2.setAnimation(anim);

		    
		}
		else
		{
			AvailableShapesTable.correct_answers++;
			AvailableShapesTable.name.remove(index);
			AvailableShapesTable.type.remove(index);

			if(AvailableShapesTable.name.size()==0)
			{
				for(int i =0;i<4;i++){
					options[i].setAnimation(setFloatZero);
					options[i].startAnimation(setFloatZero);
				}
				view.setAnimation(setFloatZero);
				view.startAnimation(setFloatZero);
				view.setClickable(false);
				
				textview.setAnimation(setFloatZero);
				textview.startAnimation(setFloatZero);
				textview.setClickable(false);
				
				imageview2.setAnimation(setFloatZero);
				imageview2.startAnimation(setFloatZero);
				imageview2.setClickable(false);
				
				btn_score.setAnimation(setFloatZero);
				btn_score.startAnimation(setFloatZero);
				btn_score.setClickable(false);
				
				starImage.setAnimation(setFloatZero);
				starImage.startAnimation(setFloatZero);
				starImage.setClickable(false);
				
				TextView textview2 = (TextView) findViewById(R.id.textView2);
				textview2.setAnimation(setFloatZero);
				textview2.startAnimation(setFloatZero);
				textview2.setClickable(false);
				
				RelativeLayout layout = (RelativeLayout) findViewById(R.id.relativeLayout_Play);
				//layout.setBackgroundResource(R.drawable.play_end);
				setResult(2, intent);
				AlphaAnimation stay_in_focus = new AlphaAnimation(1f,1f);
				stay_in_focus.setDuration(2500);
				mp=MediaPlayer.create(Play.this, R.raw.congratulations);
				mp.start();
				stay_in_focus.setAnimationListener(new AnimationListener() {
					
					@Override
					public void onAnimationStart(Animation animation) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void onAnimationRepeat(Animation animation) {
						// TODO Auto-generated method stub
						
					}
					
					@Override
					public void onAnimationEnd(Animation animation) {
						// TODO Auto-generated method stub
						finish();
					}
				});
				layout.setAnimation(stay_in_focus);
				
			}
			else
			{
				setResult(1,intent);
			
		    
			
		    AlphaAnimation anim2 = new AlphaAnimation(0.0f, 1f);
		    anim2.setDuration(1000);
		    anim2.setFillAfter(true);
		    anim2.setAnimationListener(new AnimationListener() {
				
				@Override
				public void onAnimationStart(Animation animation) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onAnimationRepeat(Animation animation) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void onAnimationEnd(Animation animation) {
					// TODO Auto-generated method stub
					finish();
				}
			});
		    
		    btn_score.setAnimation(setFloatZero);
		    btn_score.startAnimation(setFloatZero);
		    starImage.setAnimation(anim2);
		    starImage.startAnimation(anim2);
		    
		    for(int i =0;i<4;i++){
		    	options[i].setClickable(false);
		    }
		    if(position==0){
		    	
				MediaPlayer mp=MediaPlayer.create(Play.this, R.raw.right_answer);
				mp.start();
				}
			//view.setImageResource(R.drawable.star_popup);

			//Animation part
			for(int i=0;i<4;i++){
				
				options[i].setAnimation(setFloatZero);
				
			}
			btn_score.setAnimation(setFloatZero);
			//imageview2.setImageResource(R.drawable.star_popup);				
		//    AlphaAnimation anim = new AlphaAnimation(0.0f, 1f);
		  //  anim.setDuration(1000);
		    //todo this is correct textview code
		    imageview2.setAnimation(setFloatZero);
		    AlphaAnimation anim = new AlphaAnimation(0.0f, 1f);
			anim.setDuration(100);
		    textview.setAnimation(anim);
		    textview.startAnimation(anim);
		    view.setAnimation(setFloatZero);
		    view.startAnimation(setFloatZero);
		    
		}
		}
	}
	public void onBackClick(View v){
		finish();
	}
	@Override
	protected void onStop(){
		super.onStop();
		
		db_obj.truncate();
		for(int i=0;i<AvailableShapesTable.name.size();i++){
			db_obj.setAvailableShapes(AvailableShapesTable.name.get(i),AvailableShapesTable.type.get(i),i+1,AvailableShapesTable.correct_answers,AvailableShapesTable.wrong_answers);
		}
		
	}
}
