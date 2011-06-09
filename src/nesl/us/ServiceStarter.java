package nesl.us;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TabHost;

public class ServiceStarter extends TabActivity{

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tabs);
		
		// Start the services
		startService(new Intent(this, CoreService.class));
		startService(new Intent("nesl.us.GTG.trigger"));
		startService(new Intent("nesl.us.GEH.trigger"));
		
	    TabHost tabhost = getTabHost();  	// The activity TabHost
	    TabHost.TabSpec spec;  				// Reusable TabSpec for each tab
	    Intent intent;  					// Reusable Intent for each
	    
	    // Create an Intent to launch an Activity for the tab (to be reused)
	    intent = new Intent().setClass(this, TriggersActivity.class);
	    
	    // Initialize a TabSpec for each tab and add it to the TabHost
	    spec = tabhost.newTabSpec("Rules");
	    spec.setContent(intent);
	    spec.setIndicator("Rules");
	    tabhost.addTab(spec);
	    
	    // Create an Intent to launch an Activity for the tab (to be reused)
	    intent = new Intent().setClass(this, SettingsActivity.class);
	    
	    // Initialize a TabSpec for each tab and add it to the TabHost
	    spec = tabhost.newTabSpec("Settings");
	    spec.setContent(intent);
	    spec.setIndicator("Settings");
	    tabhost.addTab(spec);
	    
	    tabhost.setCurrentTab(0);

	}

}