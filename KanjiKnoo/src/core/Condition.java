package core;

final public class Condition {

	public Object value;
	public Criterias criteria;
	
	public Condition (Criterias criteria, Object value) {
		this.value = value;
		this.criteria = criteria;
	}
}
