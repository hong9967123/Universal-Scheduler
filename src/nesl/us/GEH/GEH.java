package nesl.us.GEH;

import nesl.us.Action;
import nesl.us.R;
import nesl.us.util.EventReceiver;
import nesl.us.util.MessageListener;
import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.widget.Toast;


public class GEH extends Service implements EventReceiver{

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	@Override
	public void onDestroy() {
		Toast.makeText(this, "GEH stopped", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onStart(Intent intent, int startid) {
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
	    return START_STICKY;
	}
	
	@Override
	public void onCreate() {
		Toast.makeText(this, "GEH created", Toast.LENGTH_SHORT).show();
		
		// Register receiver
		MessageListener intentReceiver = new MessageListener(this);

		// Register actions that we can handle
		IntentFilter intentFilter = new IntentFilter("CoreService.toast");
		registerReceiver(intentReceiver, intentFilter); 	

		intentFilter = new IntentFilter("CoreService.sound");
		registerReceiver(intentReceiver, intentFilter); 	
	}

	@Override
	public void onAction(Action action) {
		if (action.name == null)
		{
			return;
		}

		else if (action.name.equals("toast"))
		{
			Toast.makeText(this, action.bundle.getString("message"), Toast.LENGTH_SHORT).show();
		}
		else if (action.name.equals("sound"))
		{
			MediaPlayer mp = MediaPlayer.create(getApplicationContext(), R.raw.ding);
			mp.start();
		}
	}
}