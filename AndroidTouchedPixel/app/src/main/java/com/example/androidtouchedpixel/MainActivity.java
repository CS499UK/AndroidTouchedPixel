package com.example.androidtouchedpixel;


import android.graphics.Color;
import android.os.Bundle;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;
import android.widget.TextView;

import android.app.Notification;
import android.app.NotificationManager;


import java.util.Locale;
import java.util.Random;

import android.app.Activity;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;



import android.media.RingtoneManager;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;



public class MainActivity extends Activity {
	
	TextView touchedXY, invertedXY, imgSize, colorRGB, write, color_name;
	ImageView imgSource1, imgSource2;
    TextToSpeech ttobj;
    //private EditText write;

 /*   NotificationCompat.Builder builder =
            new NotificationCompat.Builder(this)
                    .setSmallIcon(R.drawable.ic_launcher)
                    .setContentTitle("Notifications Example")
                    .setContentText("This is a test notification");


    Intent notificationIntent = new Intent(this, MenuScreen.class);

    PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent,
            PendingIntent.FLAG_UPDATE_CURRENT);

    builder.setContentIntent(contentIntent);
    builder.setAutoCancel(true);
    builder.setLights(Color.BLUE, 500, 500);
    long[] pattern = {500,500,500,500,500,500,500,500,500};
    builder.setVibrate(pattern);
    builder.setStyle(new NotificationCompat.InboxStyle());
    // Add as notification
    NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
    manager.notify(1, builder.build());
*/

    /*@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        write = (EditText)findViewById(R.id.editText1);
        ttobj=new TextToSpeech(getApplicationContext(),
                new TextToSpeech.OnInitListener() {
                    @Override
                    public void onInit(int status) {
                        if(status != TextToSpeech.ERROR){
                            ttobj.setLanguage(Locale.UK);
                        }
                    }
                });
    }
    */
    @Override
    public void onPause(){
        if(ttobj !=null){
            ttobj.stop();
            ttobj.shutdown();
        }
        super.onPause();
    }

    public void speakText(View view){
        String toSpeak = write.getText().toString();
        Toast.makeText(getApplicationContext(), toSpeak,
                Toast.LENGTH_SHORT).show();
        ttobj.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        touchedXY = (TextView)findViewById(R.id.xy);
        invertedXY = (TextView)findViewById(R.id.invertedxy);
        imgSize = (TextView)findViewById(R.id.size);
        colorRGB = (TextView)findViewById(R.id.colorrgb);
    	imgSource1 = (ImageView)findViewById(R.id.source1);
    	imgSource2 = (ImageView)findViewById(R.id.source2);
    	
    	imgSource1.setOnTouchListener(imgSourceOnTouchListener);
    	imgSource2.setOnTouchListener(imgSourceOnTouchListener);
        //write = (TextView)findViewById(R.id.colorrgb);


        ttobj=new TextToSpeech(getApplicationContext(),
                new TextToSpeech.OnInitListener() {
                    @Override
                    public void onInit(int status) {
                        if(status != TextToSpeech.ERROR){
                            ttobj.setLanguage(Locale.US);
                        }
                    }
                });
    	
    }
    
    OnTouchListener imgSourceOnTouchListener
    = new OnTouchListener(){

		@Override
		public boolean onTouch(View view, MotionEvent event) {
			
			float eventX = event.getX();
			float eventY = event.getY();
			float[] eventXY = new float[] {eventX, eventY};
			
			Matrix invertMatrix = new Matrix();
			((ImageView)view).getImageMatrix().invert(invertMatrix);
			
			invertMatrix.mapPoints(eventXY);
			int x = Integer.valueOf((int)eventXY[0]);
			int y = Integer.valueOf((int)eventXY[1]);
			
			touchedXY.setText(
					"touched position: "
					+ String.valueOf(eventX) + " / " 
					+ String.valueOf(eventY));
			invertedXY.setText(
					"touched position: "
					+ String.valueOf(x) + " / " 
					+ String.valueOf(y));

			Drawable imgDrawable = ((ImageView)view).getDrawable();
			Bitmap bitmap = ((BitmapDrawable)imgDrawable).getBitmap();
			
			imgSize.setText(
					"drawable size: "
					+ String.valueOf(bitmap.getWidth()) + " / " 
					+ String.valueOf(bitmap.getHeight()));
			
			//Limit x, y range within bitmap
			if(x < 0){
				x = 0;
			}else if(x > bitmap.getWidth()-1){
				x = bitmap.getWidth()-1;
			}
			
			if(y < 0){
				y = 0;
			}else if(y > bitmap.getHeight()-1){
				y = bitmap.getHeight()-1;
			}

			int touchedRGB = bitmap.getPixel(x, y);
            if(touchedRGB == Color.WHITE){
                //color_name.setText("white");

                colorRGB.setText("touched color: WHITE");
                colorRGB.setTextColor(Color.BLACK);
                write = (TextView)color_name;
            }
            else {
                colorRGB.setText("touched color: " + "#" + Integer.toHexString(touchedRGB));
                colorRGB.setTextColor(touchedRGB);
            }



			return true;

		}};



}
