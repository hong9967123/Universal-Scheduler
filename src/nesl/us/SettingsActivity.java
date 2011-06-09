package nesl.us;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class SettingsActivity extends Activity{
	
	//private MessageHandler myMessageHandler;
	private TextView textview;
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        textview = new TextView(this);
        textview.setText(CoreService.rules.toString());
        setContentView(textview);
        //myMessageHandler = new MessageHandler(this);
    }
}
