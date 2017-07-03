package textgen;

import java.util.AbstractList;

/**
 * A class that implements a doubly linked list
 * 
 * @author UC San Diego Intermediate Programming MOOC team
 *
 * @param <E>
 *            The type of the elements stored in the list
 */
public class MyLinkedList<E> extends AbstractList<E> {
	LLNode<E> head;
	LLNode<E> tail;
	int size;

	/** Create a new empty LinkedList */
	public MyLinkedList() {
		head = new LLNode<E>(null);
		tail = new LLNode<E>(null);
		size = 0;
	}

	/**
	 * Appends an element to the end of the list
	 * 
	 * @param element
	 *            The element to add
	 */
	public boolean add(E element) {
		LLNode<E> tempNode = new LLNode<E>(element);
		LLNode<E> prevNode = tail.prev;

		if (size == 0) {
			tempNode.prev = head;
			head.next = tempNode;
			tempNode.next = tail;
			tail.prev = tempNode;
		} else {
			tempNode.prev = prevNode;
			prevNode.next = tempNode;
			tempNode.next = tail;
			tail.prev = tempNode;
		}
		size++;

		return true;
	}

	/**
	 * Get the element at position index
	 * 
	 * @throws IndexOutOfBoundsException
	 *             if the index is out of bounds.
	 */
	public E get(int index) {
		if (index < 0 || size <= index) {
			throw new IndexOutOfBoundsException("There is no position " + index + " in this linked list");
		}

		LLNode<E> node = head.next;
		while (index > 0) {
			node = node.next;
			index--;
		}
		return node.data;
	}

	/**
	 * Add an element to the list at the specified index
	 * 
	 * @param index
	 *            The index where the element should be added
	 * @param element
	 *            The element to add
	 */
	public void add(int index, E element) {
		if (element == null) {
			throw new NullPointerException("Null value cannot be added to the list");
		}
		
		if (index < 0 || index > size+1) {
			throw new IndexOutOfBoundsException("There is no position " + index + " in this linked list");
		}
		
		LLNode<E> tempNode = new LLNode<E>(element);
		
		LLNode<E> nextNode = head.next;
		while (index > 0) {
			nextNode = nextNode.next;
			index--;
		}
		
		
		if (size == 0) {
			tempNode.prev = head;
			head.next = tempNode;
			tempNode.next = tail;
			tail.prev = tempNode;
		} else {
			LLNode<E> prevNode = nextNode.prev;
			tempNode.prev = prevNode;
			prevNode.next = tempNode;
			tempNode.next = nextNode;
			nextNode.prev = tempNode;
		}
		
		size++;
	}

	/** Return the size of the list */
	public int size() {
		return size;
	}

	/**
	 * Remove a node at the specified index and return its data element.
	 * 
	 * @param index
	 *            The index of the element to remove
	 * @return The data element removed
	 * @throws IndexOutOfBoundsException
	 *             If index is outside the bounds of the list
	 * 
	 */
	public E remove(int index) {
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException("There is no position " + index + " in this linked list");
		}
		
		LLNode<E> removedNode = head.next;
		while (index > 0) {
			removedNode = removedNode.next;
			index--;
		}
		
		LLNode<E> prevNode = removedNode.prev;
		LLNode<E> nextNode = removedNode.next;
		
		prevNode.next = nextNode;
		nextNode.prev = prevNode;
		
		size--;
		
		return removedNode.data;
	}

	/**
	 * Set an index position in the list to a new element
	 * 
	 * @param index
	 *            The index of the element to change
	 * @param element
	 *            The new element
	 * @return The element that was replaced
	 * @throws IndexOutOfBoundsException
	 *             if the index is out of bounds.
	 */
	public E set(int index, E element) {
		if (element == null) {
			throw new NullPointerException("Null value cannot be added to the list");
		}
		
		if (index < 0 || index >= size) {
			throw new IndexOutOfBoundsException("There is no position " + index + " in this linked list");
		}
		
		LLNode<E> changedNode = head.next;
		while (index > 0) {
			changedNode = changedNode.next;
			index--;
		}
		
		E origData = changedNode.data;
		changedNode.data = element;
		
		return origData;
	}
}

class LLNode<E> {
	LLNode<E> prev;
	LLNode<E> next;
	E data;


	public LLNode(E e) {
		this.data = e;
		this.prev = null;
		this.next = null;
	}

}
