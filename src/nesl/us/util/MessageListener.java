package nesl.us.util;

import nesl.us.Action;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class MessageListener extends BroadcastReceiver {
	private EventReceiver myReceiver;
	
	public MessageListener(EventReceiver e)
	{
		super();
		myReceiver = e;
	}
	
	@Override
	public void onReceive(Context context, Intent intent) 
	{
		myReceiver.onAction(new Action(intent.getStringExtra("CoreService.Action"), intent.getBundleExtra("CoreService.Bundle")));
	}

} 