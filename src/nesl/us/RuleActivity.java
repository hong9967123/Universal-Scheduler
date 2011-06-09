package nesl.us;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class RuleActivity extends ListActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
	    String triggerName = getIntent().getStringExtra("triggerName");	    
	    String triggerValue = getIntent().getStringExtra("triggerValue");	 
	    int ruleNum = getIntent().getIntExtra("ruleNum", -1);
	    
		setTitle("[" + triggerName + ":" + triggerValue + "] Rule " + ruleNum);
		
		String menu[] = {"Conditions", "Actions"};
		
		// Display the list
		setListAdapter(new ArrayAdapter<String>(this, R.layout.rule, menu));
		
		ListView lv = getListView();
		lv.setTextFilterEnabled(true);
		registerForContextMenu(lv);
		
		lv.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				
			    String triggerName = getIntent().getStringExtra("triggerName");	    
			    String triggerValue = getIntent().getStringExtra("triggerValue");	 
			    int ruleNum = getIntent().getIntExtra("ruleNum", -1);
			
				// When clicked, show a toast with the TextView text
				if (((TextView) view).getText().equals("Conditions"))
				{
					Intent i = new Intent(getApplicationContext(),ConditionsActivity.class);
					i.putExtra("triggerName", triggerName);
					i.putExtra("triggerValue", triggerValue);
					i.putExtra("ruleNum", ruleNum);
					startActivity(i);
				}
				else
				{
					Intent i = new Intent(getApplicationContext(),ActionsActivity.class);
					i.putExtra("triggerName", triggerName);
					i.putExtra("triggerValue", triggerValue);
					i.putExtra("ruleNum", ruleNum);
					startActivity(i);
				}
			}
		});
	}
}
