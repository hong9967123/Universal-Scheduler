package nesl.us;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class AddRuleActivity extends Activity{

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_rule);
	}
	
	public void add(View v)
	{
		EditText trigger = (EditText) findViewById(R.id.trigger);
		EditText event = (EditText) findViewById(R.id.event);
		
    	CoreService.rules.addRule(trigger.getText().toString(),null, event.getText().toString());
    	CoreService.rules.flush();
    	
    	Toast.makeText(this, trigger.getText().toString() + " " + event.getText().toString(), Toast.LENGTH_SHORT).show();
		finish();
	}
}
