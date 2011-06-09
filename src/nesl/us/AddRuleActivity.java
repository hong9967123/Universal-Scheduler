package nesl.us;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class AddRuleActivity extends Activity{

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_rule);
		
	    Spinner spinner = (Spinner) findViewById(R.id.trigger_name);
	    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
	            this, R.array.trigger_name_array, android.R.layout.simple_spinner_item);
	    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    spinner.setAdapter(adapter);
	    
	    spinner = (Spinner) findViewById(R.id.trigger_value);
	    adapter = ArrayAdapter.createFromResource(
	            this, R.array.trigger_value_array, android.R.layout.simple_spinner_item);
	    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    spinner.setAdapter(adapter);
	    
	    spinner = (Spinner) findViewById(R.id.action_name);
	    adapter = ArrayAdapter.createFromResource(
	            this, R.array.action_name_array, android.R.layout.simple_spinner_item);
	    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    spinner.setAdapter(adapter);
	}
	
	public void add(View v)
	{
		String actionName = ((Spinner) findViewById(R.id.action_name)).getSelectedItem().toString();
		String actionValue = ((EditText) findViewById(R.id.action_value)).getText().toString();
	    String triggerName = ((Spinner) findViewById(R.id.trigger_name)).getSelectedItem().toString();
	    String triggerValue = ((Spinner) findViewById(R.id.trigger_value)).getSelectedItem().toString();	  
	    
	    Action action = new Action(actionName);
	    
	    action.bundle.putString("message", actionValue);
    	CoreService.rules.addRule(new Event(triggerName, triggerValue) ,null, action);
    	//CoreService.rules.flush();

    	Toast.makeText(this, "[" + triggerName + ":" + triggerValue + "] => [" + actionName + "]", Toast.LENGTH_SHORT).show();
		finish();
	}
}
