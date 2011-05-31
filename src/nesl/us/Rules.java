package nesl.us;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

public class Rules {
	// Internal representation of rules
	private HashMap<String,List<Rule>> rules;
	private OutputStream out;
	
	// Default constructor, empty for now
	public Rules()
	{
		rules = new HashMap<String,List<Rule>>();
	}
	
	public void setOutFile(OutputStream outFile)
	{
		out = outFile;
	}
	
	// public interface to retrieve rules
	public List<Rule> get(String trigger)
	{
		return rules.get(trigger);
	}
	
	public Set<String> keySet()
	{
		return rules.keySet();
	}
	
	public void removeRule(String trigger, Rule rule)
	{
		List<Rule> tl = rules.get(trigger);
		tl.remove(rule);
		if (tl.isEmpty())
		{
			rules.remove(trigger);
		}
		
	}
	
	public void removeEvent(String trigger, String event)
	{
		for (Rule r : rules.get(trigger))
		{
			r.removeEvent(event);
			if (!r.valid())
			{
				removeRule(trigger,r);
			}
		}
	}
	
	// Add a rule with the given parameters
	public void addRule(String trigger,Set<String> states, String event)
	{
		// Create a new Rule and populate it
		Rule lc = new Rule(trigger);
		
		// Add the event
		lc.addEvent(event);

		
		if(states != null)
		{
			for (String s: states)
			{
				lc.addState(s);
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
	
	// Add the rules corresponding to this element
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
}
