package filter;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Document;

public class FilterChain {
	private List<FilterCriterion> filterChain = new ArrayList<>();
	
	public void addFilter(FilterCriterion filterCriterion) {
		filterChain.add(filterCriterion);
	}
	
	public Document filter(Document node) {
		Document currentNode = node;
		for (FilterCriterion filterCriterion : filterChain) {
			currentNode = filterCriterion.execute(currentNode);
		}
		return currentNode;
	}
	
	public Document restore(Document node) {
		Document currentNode = node;
		for (int i = filterChain.size() - 1; i >= 0; i--) {
			currentNode = filterChain.get(i).restore(currentNode);
		}
		return currentNode;
	}
}
