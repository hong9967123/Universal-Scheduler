package nesl.us;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;



public class Rule {
	private Set<Event> conditions;
	private Set<Action> actions;
	private Event trigger;
	private int ID;
	private static int num = 0;
	
	public Rule(Event trg)
	{
		ID = num;
		num ++;
		trigger = trg;
		conditions = new LinkedHashSet<Event>();
		actions = new LinkedHashSet<Action>();
	}
	
	public int getID()
	{
		return ID;
	}
	
	// Check if we actually have actions corresponding to this rule
	public boolean valid()
	{
		return actions.size() > 0;
	}
	
	// Add a new action to be performed when this rule is detected
	public void addAction(Action a)
	{
		actions.add(a);
	}
	
	// Add a new condition that needs to be satisfied for the actions to fire
	public void addCondition(Event e)
	{
		conditions.add(e);
	}
	
	// Remove an action
	public void removeAction(Action a)
	{
		actions.remove(a);
	}
	
	// Remove an action by name
	public void removeAction(String actionName)
	{
		Iterator<Action> iter = actions.iterator();
		while (iter.hasNext())
		{
			if (iter.next().name.equals(actionName))
				iter.remove();
		}
	}
	
	// Remove a condition
	public void removeCondition(Event e)
	{
		conditions.remove(e);
	}
	
	// Remove a condition
	public void removeCondition(String conditionName)
	{
		Iterator<Event> iter = conditions.iterator();
		while (iter.hasNext())
		{
			if (iter.next().name.equals(conditionName))
				iter.remove();
		}
	}	
	
	public Set<Event> getConditions()
	{
		return conditions;
	}
	
	public Set<Action> getActions()
	{
		return actions;
	}
	
	public Event getTrigger()
	{
		return trigger;
	}
	
	public String toString()
	{
		String output = "";
		output += "\t<Rule>\n";
	
		output += "\t\t<Trigger>" + trigger + "</Trigger>\n";

		
		output += "\t\t<States>\n";
		for (Event condition : conditions)
		{
			output += "\t\t\t<State>" + condition.name + "</State>\n";
		}
		output += "\t\t</States>\n";
		
		output += "\t\t<Events>\n";
		for (Action action: actions)
		{
			output += "\t\t\t<Event>" + action.name + "</Event>\n";
		}
		output += "\t\t</Events>\n";
		
		output += "\t</Rule>\n";		
		return output;
	}

}
