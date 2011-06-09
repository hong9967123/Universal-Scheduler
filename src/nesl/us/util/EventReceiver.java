package nesl.us.util;

import nesl.us.Action;

public interface EventReceiver {
	public abstract void onAction(Action action);
}
