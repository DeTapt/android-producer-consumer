package com.pointwise.pointwisetask;


import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.ThreadLocalRandom;

public class MainActivity extends Activity {
	
	TextView tv_value, tv_size;
	Button btn_stop, btn_start;
	char[] chars; 
	StringBuilder sb;
	String output;
	DataObj current_object;
	SortedSet<DataObj> set;
	private int Producer_Interval = 2000; 
	private int Consumer_Interval = 4000; 
    private Handler mHandler;
    Iterator<DataObj> it;
    PriorityQueue<DataObj> myQueue;
    double d = 0.0;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		tv_value = (TextView) findViewById(R.id.tv_value);
		tv_size = (TextView) findViewById(R.id.tv_size);
		btn_stop = (Button) findViewById(R.id.button2);
		btn_start = (Button) findViewById(R.id.button1);
		chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
		
		
		set = new TreeSet<DataObj>();
		myQueue = new PriorityQueue<>(set);
		mHandler = new Handler();
		
		btn_start.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startRepeatingTask();
			}
		});
		
		
		
		btn_stop.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				stopRepeatingTask();
			}
		});
	}
	
   public Runnable Producer = new Runnable() {
        @Override 
        public void run() {
        	Generator();
        	mHandler.postDelayed(Producer, Producer_Interval);
        }
    };
    
    public Runnable Consumer = new Runnable() {
    	@Override
    	public void run() {
    		UpdateUserInfo();
    		mHandler.postDelayed(Consumer, Consumer_Interval);
    	}
    	
    };
    
    
    public void startRepeatingTask() {
    	Producer.run(); 
    	Consumer.run();
    }
    
    public void stopRepeatingTask() {
        mHandler.removeCallbacks(Producer);
        mHandler.removeCallbacks(Consumer);
    }
    
    public void UpdateUserInfo(){
    	if(myQueue.size() > 0){
    		DataObj curr_element = myQueue.poll();
    		Log.w("CONSUME: "+curr_element.getValue(), " - "+curr_element.getWeight());
    		d = Math.random();
    		
    		try{
    			if (d < 0.3){
    				throw new RuntimeException("test");
        		}
        		else{
        			tv_value.setText(curr_element.getValue()+", "+curr_element.getWeight());
        		}
    		}
    		catch(RuntimeException e){
    			Log.e("RUNTIME EXCEPTION", "!!!!");
    			myQueue.add(curr_element);
    			tv_size.setText(""+myQueue.size());
    		}
    		tv_size.setText(""+myQueue.size());
    	}
    }
	
	
	public void Generator(){
		//value
		sb = new StringBuilder();
		for (int i = 0; i < 4; i++) {
		    char c = chars[ThreadLocalRandom.current().nextInt(chars.length)];
		    sb.append(c);
		}

		current_object = new DataObj(sb.toString(),ThreadLocalRandom.current().nextInt(1, 11));
		
		Log.i("PRODUCE: "+current_object.getValue(), " - "+current_object.getWeight());
		myQueue.add(current_object);
		Log.e("QUEUE SIZE",""+myQueue.size());
    	

	}

}
