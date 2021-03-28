package filter;

import java.io.Serializable;
import java.util.List;
import java.util.function.Predicate;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

public class IfElseFilterCriterion extends LogicalFilterCriterion implements Serializable {

	private static final long serialVersionUID = -4801004952100929760L;
	private Predicate<Document> condition;
	private List<FilterCriterion> thenFilterCriterionList;
	private List<FilterCriterion> elseFilterCriterionList;
	private boolean thenBranchExecuted = true;

	public IfElseFilterCriterion(Predicate<Document> condition, List<FilterCriterion> thenFilterCriterionList,
			List<FilterCriterion> elseFilterCriterionList) {
		this.condition = condition;
		this.thenFilterCriterionList = thenFilterCriterionList;
		this.elseFilterCriterionList = elseFilterCriterionList;
	}

	@Override
	public Document execute(Document node) {
		Document currentNode = node;
		if (condition.test(node)) {
			if (thenFilterCriterionList == null)
				return currentNode;
			for (FilterCriterion thenFilterCriterion : thenFilterCriterionList) {
				currentNode = thenFilterCriterion.execute(currentNode);
			}
			return currentNode;
		} else {
			thenBranchExecuted = false;
			if (elseFilterCriterionList == null)
				return currentNode;
			for (FilterCriterion elseFilterCriterion : elseFilterCriterionList) {
				currentNode = elseFilterCriterion.execute(currentNode);
			}
			return currentNode;
		}
	}
	
	public Document restore(Document node) {
		Document currentNode = node;
		if (thenBranchExecuted) {
			if (thenFilterCriterionList == null)
				return currentNode;
			for (int i = thenFilterCriterionList.size() - 1; i >= 0; i--) {
				currentNode = thenFilterCriterionList.get(i).restore(currentNode);
			}
			return currentNode;
		} else {
			if (elseFilterCriterionList == null)
				return currentNode;
			for (int i = elseFilterCriterionList.size() - 1; i >= 0; i--) {
				currentNode = elseFilterCriterionList.get(i).restore(currentNode);
			}
			return currentNode;
		}
	}

}
