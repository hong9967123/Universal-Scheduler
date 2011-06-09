package nesl.us.GTG;

import nesl.us.util.MessageHandler;
import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.Toast;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;

public class GTG extends Service {

	private MessageHandler myMessageHandler;
	private LocationManager myLocationManger;

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
	@Override
	public void onDestroy() {
		Toast.makeText(this, "GTG stopped", Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onStart(Intent intent, int startid) {
	}
	
	@Override
	public void onCreate() {
		System.out.println("GTG CREATED");
		Toast.makeText(this, "GTG created", Toast.LENGTH_SHORT).show();
		myMessageHandler = new MessageHandler(this);

		// Set up GPS listening
		myLocationManger = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		LocationListener myLL = new MyLocationListener();
		myLocationManger.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0,
				myLL);
		
		
		// Create a BroadcastReceiver for internal signals
		final BroadcastReceiver bluetoothReceiver = new BroadcastReceiver() {
		    public void onReceive(Context context, Intent intent) {
		        String action = intent.getAction();
		        // When discovery finds a device
		        
		        
		        if (BluetoothAdapter.ACTION_STATE_CHANGED .equals(action)) {
		        	int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE, 0);
		        	
		        	switch (state)
		        	{
		        		case BluetoothAdapter.STATE_OFF:
		        			myMessageHandler.trigger("bluetooth", "off");
		        			break;
		        		case BluetoothAdapter.STATE_ON:
		        			myMessageHandler.trigger("bluetooth", "on");
		        			break;
		        		default:
		        			break;
		        	}
		        }
		        else if (Intent.ACTION_POWER_CONNECTED.equals(action))
		        {
		        	myMessageHandler.trigger("power", "on");
		        }
		        else if (Intent.ACTION_POWER_DISCONNECTED.equals(action))
		        {
		        	myMessageHandler.trigger("power", "off");
		        }
		        else if (Intent.ACTION_AIRPLANE_MODE_CHANGED .equals(action))
		        {
		        	if (intent.getBooleanExtra("state", true))
		        		myMessageHandler.trigger("airplaneMode", "on");
		        	else
		        		myMessageHandler.trigger("airplaneMode", "off");
		        }
		        else if (Intent.ACTION_ANSWER.equals(action))
		        {
		        	myMessageHandler.trigger("answer", "on");
		        }
		        else if (Intent.ACTION_HEADSET_PLUG.equals(action))
		        {
		        	if (intent.getIntExtra("state", 0) == 0)
		        		myMessageHandler.trigger("headset", "off");
		        	else
		        		myMessageHandler.trigger("headset", "on");
		        }
		        else if (Intent.ACTION_NEW_OUTGOING_CALL.equals(action))
		        {
	        		myMessageHandler.trigger("call", "on");
		        }
		        else if (Intent.ACTION_SCREEN_OFF.equals(action))
		        {
	        		myMessageHandler.trigger("screen", "off");
		        }
		        else if (Intent.ACTION_SCREEN_ON.equals(action))
		        {
	        		myMessageHandler.trigger("screen", "on");
		        }
		    }
		};
		
		// Register the BroadcastReceiver for bluetooth state change
		IntentFilter filter = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED );
		registerReceiver(bluetoothReceiver, filter);
		
		// Register to listen to power on/off
		filter = new IntentFilter(Intent.ACTION_POWER_CONNECTED);
		registerReceiver(bluetoothReceiver, filter);
		
		filter = new IntentFilter(Intent.ACTION_POWER_DISCONNECTED);
		registerReceiver(bluetoothReceiver, filter);
		
		// register to listen to switch to airplane mode
		filter = new IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED );
		registerReceiver(bluetoothReceiver, filter);	
		
		// register to listen to answering a call
		filter = new IntentFilter(Intent.ACTION_ANSWER  );
		registerReceiver(bluetoothReceiver, filter);	
		
		// register to detect headset status  
		filter = new IntentFilter(Intent.ACTION_HEADSET_PLUG);
		registerReceiver(bluetoothReceiver, filter);
		
		// register to detect outgoing calls
		filter = new IntentFilter(Intent.ACTION_NEW_OUTGOING_CALL);
		registerReceiver(bluetoothReceiver, filter);
		
		// register to detect screen off
		filter = new IntentFilter(Intent.ACTION_SCREEN_OFF);
		registerReceiver(bluetoothReceiver, filter);
	
		// register to detect screen on
		filter = new IntentFilter(Intent.ACTION_SCREEN_ON);
		registerReceiver(bluetoothReceiver, filter);
	}

	public class MyLocationListener implements LocationListener
	{
		@Override
		public void onLocationChanged(Location loc) {
			myMessageHandler.trigger("locationChange", "on");
		}

		@Override
		public void onProviderDisabled(String provider) {
			myMessageHandler.trigger("GPS", "off");			
		}

		@Override
		public void onProviderEnabled(String provider) {
			myMessageHandler.trigger("GPS", "on");			
		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			
		}
	}


	
}