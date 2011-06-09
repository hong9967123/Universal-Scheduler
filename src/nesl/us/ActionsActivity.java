package nesl.us;

import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class ActionsActivity extends ListActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Display the list
		display();
		
	    String triggerName = getIntent().getStringExtra("triggerName");	    
	    String triggerValue = getIntent().getStringExtra("triggerValue");	  
	    int ruleNum = getIntent().getIntExtra("ruleNum", -1);
	    
		setTitle("[" + triggerName + ":" + triggerValue + "] Rule " + ruleNum + " Actions");
		
		ListView lv = getListView();
		lv.setTextFilterEnabled(true);
		registerForContextMenu(lv);
		
		lv.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// When clicked, show a toast with the TextView text
				//Toast.makeText(getApplicationContext(),
				//		((TextView) view).getText(), Toast.LENGTH_SHORT).show();
			}
		});
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
	                                ContextMenuInfo menuInfo) {
	  super.onCreateContextMenu(menu, v, menuInfo);
	  MenuInflater inflater = getMenuInflater();
	  inflater.inflate(R.menu.actions_context_menu, menu);
	}
	
	@Override
	public void onResume()
	{
		super.onResume();
		display();
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
	  AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
	  switch (item.getItemId()) {
	  case R.id.remove_action:
	    String actionName = (String)getListView().getAdapter().getItem(info.position);
	    String triggerName = getIntent().getStringExtra("triggerName");	    
	    String triggerValue = getIntent().getStringExtra("triggerValue");	
	    int ruleNum = getIntent().getIntExtra("ruleNum", -1);
	    CoreService.rules.removeAction(new Event(triggerName, triggerValue), actionName, ruleNum);
    	//CoreService.rules.flush();
	    display();
	    return true;
	  default:
	    return super.onContextItemSelected(item);
	  }
	}
	
	private void display()
	{
		ArrayList<String> actions = new ArrayList<String>();
	    String triggerName = getIntent().getStringExtra("triggerName");	    
	    String triggerValue = getIntent().getStringExtra("triggerValue");	
	    int ruleNum = getIntent().getIntExtra("ruleNum", -1);
	    
		List<Rule> rules = CoreService.rules.get(new Event(triggerName, triggerValue));
		
		// Check to make sure a rule for the trigger still exists
		if (rules != null)
		{
			// Add the events to the list to be displayed
			for (Rule r : rules)
			{
				if (r.getID() == ruleNum)
				{
					for (Action a : r.getActions())
					{
						actions.add(a.name);
					}
					break;
				}
			}
		}
		
		// Display the list of events
		setListAdapter(new ArrayAdapter<String>(this, R.layout.rule, actions.toArray(new String[0])));
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.actions_menu, menu);
	    return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle item selection
	    switch (item.getItemId()) {
	    case R.id.add_action:
	    	/*CoreService.rules.addRule("GPSOn",null, "toastGPSOn");
	    	CoreService.rules.addRule("locationChange",null, "toastLocationChange");
	    	
	    	FileOutputStream outFile;
			try {
				outFile = openFileOutput("rules.xml", Context.MODE_PRIVATE);
				CoreService.rules.flush(outFile);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}*/
		    String triggerName = getIntent().getStringExtra("triggerName");	    
		    String triggerValue = getIntent().getStringExtra("triggerValue");	  
		    int ruleNum = getIntent().getIntExtra("ruleNum", -1);
			Intent i = new Intent(getApplicationContext(),AddActionActivity.class);
			i.putExtra("triggerName", triggerName);
			i.putExtra("triggerValue", triggerValue);
			i.putExtra("ruleNum", ruleNum);
	    	startActivity(i);
	        return true;
	    case R.id.refresh:
	    	display();
	        return true;
	    default:
	        return super.onOptionsItemSelected(item);
	    }
	}
}
