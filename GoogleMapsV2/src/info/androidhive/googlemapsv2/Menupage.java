package info.androidhive.googlemapsv2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.TextureView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class Menupage extends Activity{
	Context menu_page = this;
//	static int question_no = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.menu);
    	TextView t = (TextView) findViewById(R.id.textView1);
    	if(Variables_class.question_no == 1)
    		t.setText("Where is Andhra Pradesh located?");
    	Log.w("","question no is"+Integer.toString(Variables_class.question_no));
    	Button button1;
    	button1 = (Button) findViewById(R.id.button1);
    	button1.setOnClickListener(onClickListener);
    	
/*    	button1.setOnClickListener(new OnClickListener() {
    	public void onClick(View v) {
    	   // button 1 was clicked!
    	}
    	});*/
    }
    
    protected void onStart(){
    	super.onStart();
    	TextView t = (TextView) findViewById(R.id.textView1);
    	if(Variables_class.question_no == 2)
    		t.setText("What is the capital of India?");
    	Variables_class.question_no++;
    }
    
    private OnClickListener onClickListener = new OnClickListener() {

        @Override
        public void onClick(final View v) {
                 switch(v.getId()){
                     case R.id.button1:
	                   	 Intent i = new Intent(menu_page, MainActivity.class);
	                   	 startActivity(i);
	                   	 Log.w("clicked","sheep!!!");
	                     break;
                  }

        }
    };
    /*
	  public void onClick(View view) {
  		Log.w("clicked","sheep!!!");
		    switch (view.getId()) {
		    case R.id.button1:
		    		Log.w("clicked","abbba!!!");
		    		
		    		break;
		    }
	  }*/
}