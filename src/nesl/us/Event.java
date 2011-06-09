package nesl.us;

public class Event {
	public String name;
	public String value;
	
	public Event(String n, String v)
	{
		name = n;
		value = v;
	}
	
	// Two events are equal if they have the same name and value
	public boolean equals(Object other)
	{
		if (other instanceof Event)
				return ((Event) other).name.equals(name) && ((Event) other).value.equals(value);
		return false;
	}
	
	// Naive hashCode function, may consider improving to a better algorithm
	public int hashCode()
	{
		return name.hashCode() + value.hashCode();
	}
	
}