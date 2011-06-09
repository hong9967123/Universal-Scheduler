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

public class TriggersActivity extends ListActivity {

	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Display the list
		display();
		
		ListView lv = getListView();
		lv.setTextFilterEnabled(true);

		lv.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent i = new Intent(getApplicationContext(),RulesActivity.class);
				String trigger[] = ((TextView) view).getText().toString().split(": ", -2);
				i.putExtra("triggerName", trigger[0]);
				i.putExtra("triggerValue", trigger[1]);
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
		ArrayList<String> triggers = new ArrayList<String>();
		for (Event e : CoreService.rules.keySet())
		{
			triggers.add(e.name + ": " + e.value);
		}
		
		setListAdapter(new ArrayAdapter<String>(this, R.layout.rule, triggers.toArray(new String[0])));
	}
}
