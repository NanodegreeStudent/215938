package com.anakooter.guesstheshape;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.media.SoundPool.OnLoadCompleteListener;
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

public class PracticeActivity extends Activity implements OnClickListener {
	
	int shapes_id;
	String opt_name;
	int correct_id;
	String correct_name;
	String check;
	String comp1;
	String comp2;
	int noOfImages;
	int random;
	int index;
	int x;
	ArrayList<Integer> myImageList;	
	Button[] buttons; 
	RelativeLayout rl; 
	boolean valid = false;
	List<String> opt;
	Intent intent;
	ScaleAnimation animation;
	Random rand;
	ImageView imageview2;		
	TextView textview,textview2;
	AlphaAnimation setFloatOne,setFloatZero ;
	AlphaAnimation fade_out;
	MediaPlayer mp=new MediaPlayer();
	
	int counter,position = 0;
	SharedPreferences prefs;
	//SOUND CODE
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_practice);		
		setFloatOne = new AlphaAnimation(0.0f, 1f);
		setFloatZero = new AlphaAnimation(1.0f,0.0f);
		setFloatOne.setDuration(0);
		setFloatZero.setDuration(0);
		setFloatOne.setFillAfter(true);
		setFloatZero.setFillAfter(true);
		fade_out = new AlphaAnimation(1f, 0f);
	    fade_out.setDuration(600);
	    fade_out.setFillAfter(true);
		
		rand = new Random();
		intent=new Intent();
		intent=getIntent();

		textview2 = new TextView(this);
		textview2 = (TextView) findViewById(R.id.textView2); 
		
		textview = new TextView(this);
		textview = (TextView)findViewById(R.id.textView1);
		myImageList = new ArrayList<Integer>();
		myImageList =intent.getIntegerArrayListExtra("intent_avail_back");
		rl= (RelativeLayout) findViewById(R.id.prac);
		buttons = new Button[4];
		imageview2=new ImageView(this);
		imageview2 = (ImageView) findViewById(R.id.imageView1);
		
		prefs = getSharedPreferences("sound_value", MODE_PRIVATE); 
		position = prefs.getInt("text", 0);
		
		textview2.setAnimation(setFloatOne);
		textview2.startAnimation(setFloatOne);
		
		imageview2.setAnimation(setFloatZero);
		textview.setAnimation(setFloatZero);
		opt = new ArrayList<String>(Arrays.asList(getResources().getStringArray(R.array.shapes_opt)));		
		
		
		buttons[0] = (Button) findViewById(R.id.prac1);
		buttons[1] = (Button) findViewById(R.id.prac2);
		buttons[2] = (Button) findViewById(R.id.prac3);
		buttons[3] = (Button) findViewById(R.id.prac4);
		buttons[0].setOnClickListener(this);
		buttons[1].setOnClickListener(this);
		buttons[2].setOnClickListener(this);
		buttons[3].setOnClickListener(this);
		
		index=(int) rand.nextInt(100)% (myImageList.size());
		noOfImages=6;
		
		rl.setBackgroundResource(myImageList.get(index)); 
		comp2=(rl.getResources().getResourceEntryName(myImageList.get(index)).toString()).substring(5);//oval
		
		//--------+++++++++++SOUND CODE+++++-----------
		
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
		//--------+++++++++++SOUND CODE+++++-----------
		setAndCompare();
		valid();	
	
}
		public void setAndCompare()
		{
			
			
			for (int i=0;i<4;i++)
			{
				 x=i;
				setFloatOne.setAnimationListener(new AnimationListener() {
					
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
						ScaleAnimation animation2 = new ScaleAnimation(1.0f,1.5f,1.0f,1.5f,40f,40f);
						//animation2=new ScaleAnimation((float)0.8,(float)1.1,(float)0.8,(float)1.1,(float)50,(float)50);
						animation2.setDuration(1000);
						animation2.setRepeatCount(Animation.INFINITE);
						animation2.setRepeatMode(Animation.REVERSE);
						buttons[0].setAnimation(animation2);
						buttons[1].setAnimation(animation2);
						buttons[2].setAnimation(animation2);
						buttons[3].setAnimation(animation2);
						animation.setAnimationListener(null);
						
					}
				});
				buttons[i].setAnimation(setFloatOne);
				
				random=(int) rand.nextInt(100)%(noOfImages-i);
				opt_name = (opt.get(random));  //circle
				shapes_id=getResources().getIdentifier(opt_name, "drawable", getPackageName());
				buttons[i].setBackgroundResource(shapes_id); //circle
				buttons[i].setTag(opt_name); // opt_circle
				comp1=(buttons[i].getTag().toString()).substring(4); //circle
			
				if (comp1.equals(comp2))
				{
					valid = true;
					correct_name=comp2;
				}
				
				opt.remove(random);
			}
			myImageList.remove(index);
			
		}
	   public void valid()
		{
			if (!valid)
			{
				int r=(int) rand.nextInt(100)%4;
				correct_name=comp2;
				correct_id=getResources().getIdentifier("opt_"+comp2, "drawable", getPackageName());
				
				buttons[r].setBackgroundResource(correct_id);
				buttons[r].setTag("opt_"+comp2);
				
				Log.i("String", "Invalid");
				
			} 
		}

		@Override
		public void onClick(View v) {
			Button btn=(Button) v;
			if(mp.isPlaying())
			{
				mp.release();
			}
			String s=((String) btn.getTag());
			s=s.substring(4);
			
			
			//------------------CORRECT ANSWER CASE---------------
			
			
			if(s.equals(correct_name) && myImageList.size()>0)
			{
				
				Log.i("String", "Correct");
				setResult(3,intent);
				if(position==0){
					mp=MediaPlayer.create(PracticeActivity.this, R.raw.right_answer);
					mp.start();
				}
				//TODO ao jee
				imageview2.setImageResource(R.drawable.tick);				
			   
				for(int i=0;i<4;i++){
					buttons[i].setAnimation(fade_out);
				}
				AlphaAnimation anim = new AlphaAnimation(0.5f, 1f);
			    anim.setDuration(500);
			    
			    
			    imageview2.startAnimation(setFloatOne);
			 
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
					    
			        	finish();
			        }
			    });
			    imageview2.setAnimation(anim);
				
			    for(int i =0 ;i<4;i++){
			    	buttons[i].setClickable(false);
			    }
				
			}
			
			//------------------GAME END CASE---------------
			
			else if(s.equals(correct_name) && myImageList.size()<1)
			{
				textview2.setAnimation(setFloatZero);
				textview2.startAnimation(setFloatZero);
				Log.i("String", "Correct");
				for(int i=0;i<4;i++){
					buttons[i].setAnimation(fade_out);
				}
				
				if(position==0){
					mp=MediaPlayer.create(PracticeActivity.this, R.raw.right_answer);
					mp.start();
				}
				
				rl.setBackgroundResource(R.drawable.back_prac);
				imageview2.setImageResource(R.drawable.star_popup);				
			    AlphaAnimation anim = new AlphaAnimation(0.0f, 1f);
			    anim.setDuration(1000);
			    //todo this is correct textview code
			    
			    textview.setAnimation(setFloatOne);
			    
			   
			 
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
			        	/*ScaleAnimation anim2 = new ScaleAnimation(0.7f,1.4f,0.7f,1.4f,50f,50f);
					    anim2.setDuration(1000);
					    imageview2.setAnimation(anim2);
					    
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
								AlphaAnimation timeWasting = new AlphaAnimation(1f, 1f);
								timeWasting.setDuration(1300);
								
								 setResult(4,intent);
								 finish();	
							}
						});	
					   
			        }
			    });*/
			        	AlphaAnimation timeWasting = new AlphaAnimation(1f, 1f);
						timeWasting.setDuration(1300);
						
						 setResult(4,intent);
						 finish();
			        }
			 });
			    for(int i =0 ;i<4;i++){
			    	buttons[i].setClickable(false);
			    }
			    imageview2.setAnimation(anim);
			    
				
			}
			
			//------------------WRONG ANSWER CASE---------------
			
			else
			{
				Log.i("String", "Wrong");
				imageview2 = new ImageView(this);
				imageview2 = (ImageView) findViewById(R.id.imageView1);

				if(position==0){
					mp=MediaPlayer.create(PracticeActivity.this, R.raw.wrong_answer);
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

			    imageview2.setAnimation(anim);
			    
			   // anim.start();
			   // imageview2.startAnimation(anim);
				//imageview2.animate().start();
			}
				
			
		}
		public void onBackClick(View v){
			finish();
		}
}