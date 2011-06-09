package nesl.us;

import java.util.ArrayList;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import android.widget.AdapterView.OnItemClickListener;

public class RulesActivity extends ListActivity {

	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Display the list
		display();
		
	    String triggerName = getIntent().getStringExtra("triggerName");	    
	    String triggerValue = getIntent().getStringExtra("triggerValue");	  
	    setTitle("[" + triggerName + ":" + triggerValue + "] Rules");
		
		ListView lv = getListView();
		lv.setTextFilterEnabled(true);

		lv.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
			    String triggerName = getIntent().getStringExtra("triggerName");	    
			    String triggerValue = getIntent().getStringExtra("triggerValue");	
				Intent i = new Intent(getApplicationContext(),RuleActivity.class);
				i.putExtra("triggerName", triggerName);
				i.putExtra("triggerValue", triggerValue);
				i.putExtra("ruleNum", Integer.parseInt(((TextView) view).getText().toString()));
				startActivity(i);
			}
		});
	}
	
	@Override
	public void onResume()
	{
		super.onResume();
		display();
	}	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.rules_menu, menu);
	    return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle item selection
	    switch (item.getItemId()) {
	    case R.id.add_rule:
	    	/*CoreService.rules.addRule("GPSOn",null, "toastGPSOn");
	    	CoreService.rules.addRule("locationChange",null, "toastLocationChange");
	    	
	    	FileOutputStream outFile;
			try {
				outFile = openFileOutput("rules.xml", Context.MODE_PRIVATE);
				CoreService.rules.flush(outFile);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}*/
	    	
	    	startActivity(new Intent(this, AddRuleActivity.class));
	        return true;
	    case R.id.refresh:
	    	display();
	        return true;
	    default:
	        return super.onOptionsItemSelected(item);
	    }
	}
	
	private void display()
	{
		ArrayList<String> rules = new ArrayList<String>();
	    String triggerName = getIntent().getStringExtra("triggerName");	    
	    String triggerValue = getIntent().getStringExtra("triggerValue");	  
		Event trigger = new Event(triggerName, triggerValue);
		if (CoreService.rules.get(trigger) != null)
		{
			for (Rule r : CoreService.rules.get(trigger))
			{
				rules.add(r.getID() + "");
			}
		}
		
		setListAdapter(new ArrayAdapter<String>(this, R.layout.rule, rules.toArray(new String[0])));
	}
}
