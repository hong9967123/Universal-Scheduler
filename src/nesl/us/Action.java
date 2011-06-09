package nesl.us;

import android.os.Bundle;

public class Action {
	public String name;
	public Bundle bundle;
	
	public Action(String n, Bundle b)
	{
		name = n;
		bundle = b;
	}
	
	public Action(String n)
	{
		name = n;
		bundle = new Bundle();
	}
}
