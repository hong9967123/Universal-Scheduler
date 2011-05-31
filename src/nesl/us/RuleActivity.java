package nesl.us;

import java.util.ArrayList;
import java.util.List;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class RuleActivity extends ListActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Display the list
		display();
		
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
	  inflater.inflate(R.menu.rule_menu, menu);
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
	  AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
	  switch (item.getItemId()) {
	  case R.id.remove_event:
	    String event = (String)getListView().getAdapter().getItem(info.position);
	    Toast.makeText(this, event, Toast.LENGTH_SHORT).show();
	    String trigger = getIntent().getStringExtra("trigger");	    
	    CoreService.rules.removeEvent(trigger, event);
    	CoreService.rules.flush();
	    display();
	    return true;
	  default:
	    return super.onContextItemSelected(item);
	  }
	}
	
	private void display()
	{
		ArrayList<String> events = new ArrayList<String>();
		String trigger = getIntent().getStringExtra("trigger");
		List<Rule> rules = CoreService.rules.get(trigger);
		
		// Check to make sure a rule for the trigger still exists
		if (rules != null)
		{
			// Add the events to the list to be displayed
			for (Rule r : rules)
			{
				for (Event e : r.getEvents())
				{
					events.add(e.getEvent());
				}
			}
		}
		
		// Display the list of events
		setListAdapter(new ArrayAdapter<String>(this, R.layout.rule, events.toArray(new String[0])));
	}
}
