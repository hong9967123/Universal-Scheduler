package nesl.us.util;

import nesl.us.CoreService;
import android.content.Intent;
import android.content.Context;


public class MessageHandler {
	private Context master;


	public MessageHandler(Context sender) {
		master = sender;
	}

	public Intent trigger(String triggerName, String triggerValue) {
		Intent i = new Intent(master, CoreService.class);
		i.putExtra("nesl.us.CoreService.triggerName", triggerName);
		i.putExtra("nesl.us.CoreService.triggerValue", triggerValue);
		master.startService(i);
		return i;
	}
}
