package spelling;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * An trie data structure that implements the Dictionary and the AutoComplete
 * ADT
 * 
 * @author You
 *
 */
public class AutoCompleteDictionaryTrie implements Dictionary, AutoComplete {

	private TrieNode root;
	private int size;

	public AutoCompleteDictionaryTrie() {
		root = new TrieNode();
	}

	/**
	 * Insert a word into the trie. For the basic part of the assignment (part
	 * 2), you should convert the string to all lower case before you insert it.
	 * 
	 * This method adds a word by creating and linking the necessary trie nodes
	 * into the trie, as described outlined in the videos for this week. It
	 * should appropriately use existing nodes in the trie, only creating new
	 * nodes when necessary. E.g. If the word "no" is already in the trie, then
	 * adding the word "now" would add only one additional node (for the 'w').
	 * 
	 * @return true if the word was successfully added or false if it already
	 *         exists in the dictionary.
	 */
	public boolean addWord(String word) {
		word = word.toLowerCase();
		char[] wordArr = word.toCharArray();
		TrieNode tempNode = root;
		int wordExists = 1;

		for (char c : wordArr) {
			if (tempNode.getChild(c) == null) {
				tempNode = tempNode.insert(c);
				wordExists = 0;
			} else {
				tempNode = tempNode.getChild(c);
			}
		}
		tempNode.setEndsWord(true);

		if (wordExists == 1) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * Return the number of words in the dictionary. This is NOT necessarily the
	 * same as the number of TrieNodes in the trie.
	 */
	public int size() {
		size = checkWords(root);

		return size;
	}

	private int checkWords(TrieNode node) {
		int numWords = 0;

		if (node.endsWord() == true) {
			numWords++;
		}

		for (Character c : node.getValidNextCharacters()) {
			numWords += checkWords(node.getChild(c));
		}

		return numWords;
	}

	/**
	 * Returns whether the string is a word in the trie, using the algorithm
	 * described in the videos for this week.
	 */
	@Override
	public boolean isWord(String s) {
		s = s.toLowerCase();
		char[] wordArr = s.toCharArray();
		TrieNode tempNode = root;

		for (char c : wordArr) {
			if (tempNode.getChild(c) == null) {
				return false;
			} else {
				tempNode = tempNode.getChild(c);
			}
		}

		if (tempNode.endsWord()) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Return a list, in order of increasing (non-decreasing) word length,
	 * containing the numCompletions shortest legal completions of the prefix
	 * string. All legal completions must be valid words in the dictionary. If
	 * the prefix itself is a valid word, it is included in the list of returned
	 * words.
	 * 
	 * The list of completions must contain all of the shortest completions, but
	 * when there are ties, it may break them in any order. For example, if
	 * there the prefix string is "ste" and only the words "step", "stem",
	 * "stew", "steer" and "steep" are in the dictionary, when the user asks for
	 * 4 completions, the list must include "step", "stem" and "stew", but may
	 * include either the word "steer" or "steep".
	 * 
	 * If this string prefix is not in the trie, it returns an empty list.
	 * 
	 * @param prefix
	 *            The text to use at the word stem
	 * @param numCompletions
	 *            The maximum number of predictions desired.
	 * @return A list containing the up to numCompletions best predictions
	 */
	@Override
	public List<String> predictCompletions(String prefix, int numCompletions) {
		prefix = prefix.toLowerCase();
		char[] wordArr = prefix.toCharArray();
		TrieNode tempNode = root;

		List<String> listOfCompletions = new LinkedList<String>();

		for (char c : wordArr) {
			if (tempNode.getChild(c) == null) {
				return listOfCompletions;
			} else {
				tempNode = tempNode.getChild(c);
			}
		}

		Queue<TrieNode> q = new LinkedList<TrieNode>();
		q.add(tempNode);

		while (!q.isEmpty() && numCompletions > 0) {
			TrieNode curr = q.remove();
			if (curr.endsWord()) {
				listOfCompletions.add(curr.getText());
				numCompletions--;
			}
			for (Character c : curr.getValidNextCharacters()) {
				q.add(curr.getChild(c));
			}
		}

		return listOfCompletions;
	}

	// For debugging
	public void printTree() {
		printNode(root);
	}

	/** Do a pre-order traversal from this node down */
	public void printNode(TrieNode curr) {
		if (curr == null)
			return;

		System.out.println(curr.getText());

		TrieNode next = null;
		for (Character c : curr.getValidNextCharacters()) {
			next = curr.getChild(c);
			printNode(next);
		}
	}

}