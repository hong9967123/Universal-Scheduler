package nesl.us;

import nesl.us.util.MessageHandler;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

public class SettingsActivity extends Activity{
	
	private MessageHandler myMessageHandler;
	private TextView textview;
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        textview = new TextView(this);
        textview.setText(CoreService.rules.toString());
        setContentView(textview);
        myMessageHandler = new MessageHandler(this);
    }
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.rules_menu, menu);
	    return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    // Handle item selection
	    switch (item.getItemId()) {
	    case R.id.add_rule:
	    	myMessageHandler.trigger("locationChange");
	        return true;
	    case R.id.refresh:
	    	textview.setText(CoreService.rules.toString());
	        return true;
	    default:
	        return super.onOptionsItemSelected(item);
	    }
	}
}
