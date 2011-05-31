package nesl.us;

import java.util.LinkedHashSet;
import java.util.Set;

import android.os.Bundle;

public class Rule {
	private Set<String> states;
	private Set<Event> events;
	private Bundle bundle;
	private String trigger;
	
	public Rule(String trg)
	{
		trigger = trg;
		states = new LinkedHashSet<String>();
		events = new LinkedHashSet<Event>();
		bundle = new Bundle();
	}
	
	public boolean valid()
	{
		return events.size() > 0;
	}
	
	public Bundle getBundle()
	{
		return bundle;
	}
	
	public void addEvent(Event e)
	{
		events.add(e);
	}
	
	public void addEvent(String e)
	{
		events.add(new Event(e));
	}
	
	public void addState(String s)
	{
		states.add(s);
	}
	
	public void removeEvent(String e)
	{
		events.remove(e);
	}
	
	public void removeState(String s)
	{
		events.remove(s);
	}
	
	public Set<String> getStates()
	{
		return states;
	}
	
	public Set<Event> getEvents()
	{
		return events;
	}
	
	public String toString()
	{
		String output = "";
		output += "\t<Rule>\n";
	
		output += "\t\t<Trigger>" + trigger + "</Trigger>\n";

		
		output += "\t\t<States>\n";
		for (String state : states)
		{
			output += "\t\t\t<State>" + state + "</State>\n";
		}
		output += "\t\t</States>\n";
		
		output += "\t\t<Events>\n";
		for (Event event : events)
		{
			output += "\t\t\t<Event>" + event + "</Event>\n";
		}
		output += "\t\t</Events>\n";
		
		output += "\t</Rule>\n";		
		return output;
	}

}
