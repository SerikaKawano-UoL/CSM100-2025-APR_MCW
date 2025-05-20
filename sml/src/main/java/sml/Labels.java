package sml;

import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

/**
 * An instance contains a list of Strings, 
 * called "labels", in the order in
 * which they were added to the list.
 *
 * @author KLM and xxx
 */
@ToString
public class Labels {
	private final List<String> labels;

	{
		labels = new ArrayList<>();
	}

	/**
	 * Add label lab to this list and return it's number in the list (the first one
	 * added is number 0) Precondition: the list has at most 49 entries
	 *
	 * @param lab the label to add
	 * @return index position of added label
	 */
	@SuppressWarnings("UnusedReturnValue")
	public int addLabel(String lab) {
		labels.add(lab);
		return labels.size() - 1;
	}

	/**
	 * = the number of label lab in the list
	 *
	 * @param lab the label of the instruction
	 * @return the number of the label in the list (-1 if lab is not in the list)
	 */
	public int indexOf(String lab) {
		return IntStream.range(0, labels.size()).filter(i -> lab.equals(labels.get(i))).findFirst().orElse(-1);
	}

	/**
	 * Set the number of elements in the list to 0
	 */
	public void reset() {
		labels.clear();
	}
}
