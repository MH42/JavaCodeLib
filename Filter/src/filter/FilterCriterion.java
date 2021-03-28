package filter;

import org.w3c.dom.Document;

public interface FilterCriterion {
	public Document execute(Document node);
	
	public Document restore(Document node);
}
