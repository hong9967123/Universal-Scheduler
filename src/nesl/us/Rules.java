package nesl.us;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Rules {
	// Internal representation of rules
	private HashMap<Event,List<Rule>> rules;
	//private OutputStream out;
	
	// Default constructor, empty for now
	public Rules()
	{
		rules = new HashMap<Event,List<Rule>>();
	}
	
	/*
	public void setOutFile(OutputStream outFile)
	{
		out = outFile;
	}
	*/
	
	// public interface to retrieve rules
	public List<Rule> get(Event trigger)
	{
		return rules.get(trigger);
	}
	
	public Set<Event> keySet()
	{
		return rules.keySet();
	}
	
	// Remove a rule from the data base
	public void removeRule(Rule rule)
	{
		List<Rule> tl = rules.get(rule.getTrigger());
		tl.remove(rule);
		if (tl.isEmpty())
		{
			rules.remove(rule.getTrigger());
		}
		
	}
	
	// Remove all action under the trigger
	public void removeAction(Event trigger, Action action)
	{
		Iterator<Rule> iter = rules.get(trigger).iterator();
		while (iter.hasNext())
		{
			Rule r = iter.next();
			r.removeAction(action);
			if (!r.valid())
			{
				iter.remove();
				removeRule(r);
			}
		}
	}
	
	public void removeAction(Event trigger, String actionName, int ruleNum)
	{
		Iterator<Rule> iter = rules.get(trigger).iterator();
		while (iter.hasNext())
		{
			Rule r = iter.next();
			if (r.getID() == ruleNum)
			{
				r.removeAction(actionName);
				if (!r.valid())
				{
					iter.remove();
					removeRule(r);
				}		
			}
		}
	}
	
	public void addAction(Event trigger, Action action, int ruleNum)
	{
		for(Rule r : rules.get(trigger))
		{
			if (r.getID() == ruleNum)
			{
				r.addAction(action);
				return;
			}
		}
	}
	
	public void addCondition(Event trigger, Event condition, int ruleNum)
	{
		for(Rule r : rules.get(trigger))
		{
			if (r.getID() == ruleNum)
			{
				r.addCondition(condition);
				return;
			}
		}
	}	
	
	public void removeCondition(Event trigger, String conditionName, int ruleNum)
	{
		Iterator<Rule> iter = rules.get(trigger).iterator();
		while (iter.hasNext())
		{
			Rule r = iter.next();
			if (r.getID() == ruleNum)
			{
				r.removeCondition(conditionName);
				break;
			}
		}		
	}
	
	// Add a rule with the given parameters
	public void addRule(Event trigger,Set<Event> conditions, Action action)
	{
		// Create a new Rule and populate it
		Rule lc = new Rule(trigger);
		
		// Add the event
		lc.addAction(action);
		
		if(conditions != null)
		{
			for (Event cond: conditions)
			{
				lc.addCondition(cond);
			}		
		}
		
		List<Rule> exists = rules.get(trigger);
		
		if (exists == null)
		{
			exists = new LinkedList<Rule>();
			rules.put(trigger, exists);
		}

		exists.add(lc);

	}
	
	/*
	// XML no longer used: Add the rules corresponding to this element
	public void addRule(Element r)
	{
		// Parse Trigger String
		String trigger = getTextByTagName(r, "Trigger");
		
		// Parse States
		Set<String> states = new HashSet<String>();
		NodeList stateList = getElementByTagName(r,"States").getElementsByTagName("State");

		if(stateList != null)
		{
			for(int index = 0 ; index < stateList.getLength();++index) 
			{
				//get the State element
				Element state = (Element)stateList.item(index);
	
				//add the State
				states.add(getText(state));
			}		
		}
		
		// Parse Events
		Set<String> events = new HashSet<String>();
		NodeList eventsList = getElementByTagName(r,"Events").getElementsByTagName("Event");

		if(eventsList != null)
		{
			for(int index = 0 ; index < eventsList.getLength();++index) 
			{
				//get the Event element
				Element event = (Element)eventsList.item(index);
	
				//add the Event
				events.add(getText(event));
			}		
		}		

		// Add the rules
		for (String e: events)
		{
			addRule(trigger,states,e);
		}
	}

	// XML no longer used: parse a file and add it to the database
	public void parseRules(InputStream inFile)
	{
		InputSource is = new InputSource(inFile);
		
		// Parse rules
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		
		try {
			//Using factory get an instance of document builder
			DocumentBuilder db = dbf.newDocumentBuilder();

			Element root = db.parse(is).getDocumentElement();
			
			//parse using builder to get DOM representation of the XML file

			//get a NodeList of  "Rule"
			NodeList xmlRule = root.getElementsByTagName("Rule");
			if(xmlRule == null || xmlRule.getLength() <= 0)
				return;
			
			for(int index = 0 ; index < xmlRule.getLength();++index) 
			{
				//get the Rule element
				Element rule = (Element)xmlRule.item(index);

				//add the Rule
				addRule(rule);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

	// get the text field of a child element by the tag name
	private String getTextByTagName(Element e, String tag) {
		return getText(getElementByTagName(e, tag));
	}
	
	// get a child Element with the given tag name	
	private Element getElementByTagName(Element e, String tag)
	{
		if (e == null || tag == null)
			return null;
		NodeList list = e.getElementsByTagName(tag);
		if(list == null || list.getLength() <= 0)
			return null;	
		return (Element)(list.item(0));
	}
	
	// get the text field of the given Element
	private String getText(Element e)
	{
		if (e == null)
			return null;
		return e.getFirstChild().getNodeValue();		
	}
	
	public String toString()
	{
		String output = "";
		
		output += "<Rules>\n";

		for (List<Rule> curRules : rules.values())
		{
			for (Rule rule : curRules)
			{
				output += rule.toString();
			}
		}
		output +="</Rules>\n";
		
		return output;
	}
	
	public void flush()
	{
		try {
			out.write(toString().getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	*/
}
