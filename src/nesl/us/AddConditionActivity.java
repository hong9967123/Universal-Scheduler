package nesl.us;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class AddConditionActivity extends Activity{

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_condition);
		
	    Spinner spinner = (Spinner) findViewById(R.id.condition_name);
	    ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
	            this, R.array.trigger_name_array, android.R.layout.simple_spinner_item);
	    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    spinner.setAdapter(adapter);
	    
	    spinner = (Spinner) findViewById(R.id.condition_value);
	    adapter = ArrayAdapter.createFromResource(
	            this, R.array.trigger_value_array, android.R.layout.simple_spinner_item);
	    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    spinner.setAdapter(adapter);
	}
	
	public void add(View v)
	{
		String conditionName = ((Spinner) findViewById(R.id.condition_name)).getSelectedItem().toString();
		String conditionValue = ((Spinner) findViewById(R.id.condition_value)).getSelectedItem().toString();
	    String triggerName = getIntent().getStringExtra("triggerName");	    
	    String triggerValue = getIntent().getStringExtra("triggerValue");	  
	    int ruleNum = getIntent().getIntExtra("ruleNum", -1);
	    
    	CoreService.rules.addCondition(new Event(triggerName, triggerValue) ,new Event(conditionName, conditionValue), ruleNum);
    	//CoreService.rules.flush();

    	finish();
	}
}
