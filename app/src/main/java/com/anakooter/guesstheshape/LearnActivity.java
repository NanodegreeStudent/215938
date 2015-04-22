package com.anakooter.guesstheshape;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.RatingBar;

public class LearnActivity extends Activity {

	
	ShapesTable shape_object=ShapesTable.getInstance();
	int no_of_examples=18;
	int no_of_shapes = 6;
	int circleStars = 0;
	int triangleStars = 0;
	int rhombusStars = 0;
	int ovalStars = 0;
	int rectangleStars = 0;
	int squareStars = 0;
	RatingBar[] ratingBar;
	Button[] animationButtons; 
	Button backButton;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_learn);
		ratingBar = new RatingBar[no_of_shapes];
		
		ratingBar[0] = (RatingBar) findViewById(R.id.ratingBar1);
		ratingBar[1] = (RatingBar) findViewById(R.id.RatingBar2);
		ratingBar[2] = (RatingBar) findViewById(R.id.ratingBar3);
		ratingBar[3] = (RatingBar) findViewById(R.id.ratingBar4);
		ratingBar[4] = (RatingBar) findViewById(R.id.ratingBar5);
		ratingBar[5] = (RatingBar) findViewById(R.id.ratingBar6);
		
		backButton = (Button) findViewById(R.id.backButtonLearn);
		animationButtons = new Button[no_of_shapes];
		
		animationButtons[0] = (Button) findViewById(R.id.button1);
		animationButtons[1] = (Button) findViewById(R.id.Button2);
		animationButtons[2] = (Button) findViewById(R.id.button3);
		animationButtons[3] = (Button) findViewById(R.id.button4);
		animationButtons[4] = (Button) findViewById(R.id.button5);
		animationButtons[5] = (Button) findViewById(R.id.button6);


		for(int i=0;i<animationButtons.length;i++){
	
			try{
			
			RotateAnimation animation = new RotateAnimation(-8,8,150,150);
			
			animation.setDuration(1000);
			animation.setRepeatCount(Animation.INFINITE);
			animation.setRepeatMode(Animation.REVERSE);
			animationButtons[i].setAnimation(animation);
			
			
			}
			catch(Exception ex){
				Log.i("Exception",ex.getMessage());
			}
		}
	}

	@Override
	protected void onResume(){
		super.onResume();
		circleStars=0;
		rectangleStars = 0;
		triangleStars= 0 ;
		squareStars = 0;
		rhombusStars = 0;
		ovalStars = 0;
		for(int i=0;i<no_of_examples;i++){
			if(ShapesTable.type.get(i).equals("c")){
				if(ShapesTable.learningFactor[i]==1){
				circleStars++;
				}
			}
			else if(ShapesTable.type.get(i).equals("r")){
				if(ShapesTable.learningFactor[i]==1){
				rectangleStars++;
				}
			}
			else if(ShapesTable.type.get(i).equals("rh")){
				if(ShapesTable.learningFactor[i]==1){
				rhombusStars++;
				}
			}
			else if(ShapesTable.type.get(i).equals("o")){
				if(ShapesTable.learningFactor[i]==1){
				ovalStars++;
				}
			}
			else if(ShapesTable.type.get(i).equals("s")){
				if(ShapesTable.learningFactor[i]==1){
				squareStars++;
				}
			}
			else if(ShapesTable.type.get(i).equals("t")){
				if(ShapesTable.learningFactor[i]==1){
				triangleStars++;
				}
			}
		}
		
		ratingBar[0].setProgress(circleStars);
		ratingBar[1].setProgress(triangleStars);
		ratingBar[2].setProgress(squareStars);
		ratingBar[3].setProgress(rectangleStars);
		ratingBar[4].setProgress(rhombusStars);
		ratingBar[5].setProgress(ovalStars);
		
	}
	
	public void onBackClick(View v){
		finish();
	}
	public void onClick(View view){
		Intent intent = new Intent(this,LearnExamplesActivity.class);
		
		switch(view.getId()){
		
		case R.id.button1:
			intent.putExtra("shape",0);
			
			startActivity(intent);
			break;
		case R.id.Button2:
			intent.putExtra("shape",1);
			
			startActivity(intent);
			break;
		case R.id.button3:
			intent.putExtra("shape",2);
			
			startActivity(intent);
			break;
		case R.id.button4:
			intent.putExtra("shape",3);
			
			startActivity(intent);
			break;
		case R.id.button5:
			intent.putExtra("shape",4);
			
			startActivity(intent);
			break;
		case R.id.button6:
			intent.putExtra("shape",5);
			
			startActivity(intent);
			break;
		default:
			break;
		
		}
	}
	public class DownloadFilesTask extends AsyncTask<Void,Void,Void>{
	     

		@Override
		protected Void doInBackground(Void... params) {
			// TODO Auto-generated method stub
			while(true){
				
			}
		
		}
	     protected void onProgressUpdate(Integer... progress) {
	         
	     }

	     protected void onPostExecute(Long result) {
	         
	     }

	 }
	
}

