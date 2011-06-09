package nesl.us;

import java.util.HashMap;
import java.util.Set;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

public class CoreService extends Service {

	private HashMap<String, String> states;
	static Rules rules = new Rules();

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		Toast.makeText(this, "Core Service created", Toast.LENGTH_SHORT).show();
		states = new HashMap<String, String>();

    	/*
		try {
		
			//String string = "<Rules><Rule><Trigger>locationChange</Trigger><States></States><Events><Event>toastLocationChange</Event></Events></Rule></Rules>";
			//FileOutputStream fos = openFileOutput("rules.xml", Context.MODE_PRIVATE);
			//fos.write(string.getBytes());
			//fos.close();
			
			// Retrieve file
			InputStream inFile = openFileInput("rules.xml");
			
			// Parse the rules in the file
			rules.parseRules(inFile);
			inFile.close();
			
			FileOutputStream outFile;
			outFile = openFileOutput("rules.xml", Context.MODE_PRIVATE);
			rules.setOutFile(outFile);
			rules.flush();
	
		} catch (Exception e) {
			e.printStackTrace();
		}
		*/
	}

	@Override
	public void onDestroy() {
		Toast.makeText(this, "Core Service stopped", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onStart(Intent intent, int startid) {
		Toast.makeText(this, "Core Service started", Toast.LENGTH_SHORT).show();
	}

	private boolean checkConditions(Set<Event> required) {
		for (Event e : required) {
			if (states.get(e.name) == null || !states.get(e.name).equals(e.value)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// Get the trigger message from the intent
		String triggerName = intent.getStringExtra("nesl.us.CoreService.triggerName");
		String triggerValue = intent.getStringExtra("nesl.us.CoreService.triggerValue");
		
		// If there was no message, don't do anything
		if (triggerName == null) {
			return START_STICKY;
		}

		Event trigger = new Event(triggerName, triggerValue);
		
		// Add the trigger to our states table
		//states.put(trigger, new Timestamp(Calendar.getInstance().getTime().getTime()));
		states.put(trigger.name, trigger.value);
		
		// If there is no rule associated with this trigger, don't do anything
		if (rules.get(trigger) == null) {
			return START_STICKY;
		}
		
		

		for (Rule currentRule : rules.get(trigger)) {
			if (checkConditions(currentRule.getConditions())) {
				for (Action a: currentRule.getActions()) {
					
					Intent i = new Intent();
					i.setAction("CoreService." + a.name);
					i.putExtra("CoreService.Action", a.name);
					i.putExtra("CoreService.Bundle", a.bundle);
					sendBroadcast(i);
				}
			}
		}
		
		// We want this service to continue running until it is explicitly
		// stopped, so return sticky.
		return START_STICKY;
	}
}