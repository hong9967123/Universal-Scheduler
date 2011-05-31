package nesl.us;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Set;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.widget.Toast;

public class CoreService extends Service {

	private HashMap<String, Timestamp> states;
	static Rules rules = new Rules();

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onCreate() {
		Toast.makeText(this, "Core Service created", Toast.LENGTH_SHORT).show();
		states = new HashMap<String, Timestamp>();

    	
		try {
/*			String string = "<Rules><Rule><Trigger>locationChange</Trigger><States></States><Events><Event>toastLocationChange</Event></Events></Rule></Rules>";
			FileOutputStream fos = openFileOutput("rules.xml", Context.MODE_PRIVATE);
			fos.write(string.getBytes());
			fos.close();*/
			
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
	}

	@Override
	public void onDestroy() {
		Toast.makeText(this, "Core Service stopped", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onStart(Intent intent, int startid) {
		Toast.makeText(this, "Core Service started", Toast.LENGTH_SHORT).show();
	}

	private boolean checkStates(Set<String> required) {
		for (String state : required) {
			if (!states.containsKey(state)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// Get the trigger message from the intent
		String trigger = intent.getStringExtra("nesl.us.CoreService.trigger");

		// If there was no message, don't do anything
		if (trigger == null) {
			return START_STICKY;
		}

		// Add the trigger to our states table
		states.put(trigger, new Timestamp(Calendar.getInstance().getTime()
				.getTime()));
		
		// If there is no rule associated with this trigger, don't do anything
		if (rules.get(trigger) == null) {
			return START_STICKY;
		}

		for (Rule currentRule : rules.get(trigger)) {
			if (checkStates(currentRule.getStates())) {
				for (Event event : currentRule.getEvents()) {
					Intent i = new Intent();
					i.setAction("CoreService." + event);
					i.putExtra("CoreService.Event", event.getEvent());
					i.putExtra("CoreService.Bundle", event.getBundle());
					sendBroadcast(i);
				}
			}
		}
		
		// We want this service to continue running until it is explicitly
		// stopped, so return sticky.
		return START_STICKY;
	}
}