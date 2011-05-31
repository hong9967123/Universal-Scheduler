package nesl.us;

import android.os.Bundle;

public class Event {
	private String eventName;
	private Bundle bundle;
	
	public Event(String eName, Bundle bun)
	{
		eventName = eName;
		bundle = bun;
	}
	
	public Event(String eName)
	{
		eventName = eName;
		bundle = new Bundle();
	}
	
	public Bundle getBundle()
	{
		return bundle;
	}
	
	public String getEvent()
	{
		return eventName;
	}
	
	public void setEvent(String event)
	{
		eventName = event;
	}
	
	public String toString()
	{
		return eventName;
	}
}
