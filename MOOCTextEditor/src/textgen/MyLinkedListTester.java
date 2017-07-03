/**
 * 
 */
package textgen;

import static org.junit.Assert.*;


import org.junit.Before;
import org.junit.Test;


/**
 * @author UC San Diego MOOC team
 *
 */
public class MyLinkedListTester {

	private static final int LONG_LIST_LENGTH = 10;

	MyLinkedList<String> shortList;
	MyLinkedList<Integer> emptyList;
	MyLinkedList<Integer> longerList;
	MyLinkedList<Integer> list1;

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		// Feel free to use these lists, or add your own
		shortList = new MyLinkedList<String>();
		shortList.add("A");
		shortList.add("B");
		emptyList = new MyLinkedList<Integer>();
		longerList = new MyLinkedList<Integer>();
		for (int i = 0; i < LONG_LIST_LENGTH; i++) {
			longerList.add(i);
		}
		list1 = new MyLinkedList<Integer>();
		list1.add(65);
		list1.add(21);
		list1.add(42);

	}

	/**
	 * Test if the get method is working correctly.
	 */
	/*
	 * You should not need to add much to this method. We provide it as an
	 * example of a thorough test.
	 */
	@Test
	public void testGet() {
		// test empty list, get should throw an exception
		try {
			emptyList.get(0);
			fail("Check out of bounds");
		} catch (IndexOutOfBoundsException e) {

		}

		// test short list, first contents, then out of bounds
		assertEquals("Check first", "A", shortList.get(0));
		assertEquals("Check second", "B", shortList.get(1));

		try {
			shortList.get(-1);
			fail("Check out of bounds");
		} catch (IndexOutOfBoundsException e) {

		}
		try {
			shortList.get(2);
			fail("Check out of bounds");
		} catch (IndexOutOfBoundsException e) {

		}
		// test longer list contents
		for (int i = 0; i < LONG_LIST_LENGTH; i++) {
			assertEquals("Check " + i + " element", (Integer) i, longerList.get(i));
		}

		// test off the end of the longer array
		try {
			longerList.get(-1);
			fail("Check out of bounds");
		} catch (IndexOutOfBoundsException e) {

		}
		try {
			longerList.get(LONG_LIST_LENGTH);
			fail("Check out of bounds");
		} catch (IndexOutOfBoundsException e) {
		}

	}

	/**
	 * Test removing an element from the list. We've included the example from
	 * the concept challenge. You will want to add more tests.
	 */
	@Test
	public void testRemove() {
		int a = list1.remove(0);
		assertEquals("Remove: check a is correct ", 65, a);
		assertEquals("Remove: check element 0 is correct ", (Integer) 21, list1.get(0));
		assertEquals("Remove: check size is correct ", 2, list1.size());

		try {
			a = longerList.remove(longerList.size());
			fail("Check out of bounds");
		} catch (IndexOutOfBoundsException e) {
		}

		a = longerList.remove(longerList.size() - 1);
		assertEquals("Remove: check a is correct ", longerList.size(), a);
		assertEquals("Remove: check last element is correct ", (Integer) 8, longerList.get(longerList.size() - 1));
		assertEquals("Remove: check size is correct ", 9, longerList.size());
	}

	/**
	 * Test adding an element into the end of the list, specifically public
	 * boolean add(E element)
	 */
	@Test
	public void testAddEnd() {
		assertEquals("Check length of shortList", 2, shortList.size);
		assertEquals("Check length of emptyList", 0, emptyList.size);
		assertEquals("Check length of longerList", LONG_LIST_LENGTH, longerList.size);
		assertEquals("Check length of list1", 3, list1.size);

		assertEquals("Check 5th element from longerList", (Integer) 4, longerList.get(4));

		list1.add(666);
		assertEquals("Check new length of list1", 4, list1.size);

		longerList.add(null);
		assertEquals("Check new length of longerList", LONG_LIST_LENGTH + 1, longerList.size);
	}

	/** Test the size of the list */
	@Test
	public void testSize() {
		assertEquals("Check length of shortList", 2, shortList.size());
		assertEquals("Check length of emptyList", 0, emptyList.size());
		assertEquals("Check length of longerList", LONG_LIST_LENGTH, longerList.size());
		assertEquals("Check length of list1", 3, list1.size());

		list1.add(666);
		assertEquals("Check new length of list1", 4, list1.size());

		longerList.add(null);
		assertEquals("Check new length of longerList", LONG_LIST_LENGTH + 1, longerList.size());
	}

	/**
	 * Test adding an element into the list at a specified index, specifically:
	 * public void add(int index, E element)
	 */
	@Test
	public void testAddAtIndex() {
		assertEquals("Check value at index 0, shortList", "A", shortList.get(0));
		shortList.add(0, "C");
		assertEquals("Check value at index 0, shortList", "C", shortList.get(0));

		assertEquals("Check value at index 5, longerList", (Integer) 5, longerList.get(5));
		longerList.add(5, 11);
		assertEquals("Check value at index 5, longerList", (Integer) 11, longerList.get(5));

		try {
			shortList.add(shortList.size + 1, "D");
			fail("Check out of bounds");
		} catch (NullPointerException e) {

		}

		try {
			longerList.add(5, null);
			fail("Check out of bounds");
		} catch (NullPointerException e) {

		}

		try {
			shortList.add(-1, "D");
			fail("Check out of bounds");
		} catch (IndexOutOfBoundsException e) {

		}

		try {
			shortList.add(shortList.size + 2, "D");
			fail("Check out of bounds");
		} catch (IndexOutOfBoundsException e) {

		}
	}

	/** Test setting an element in the list */
	@Test
	public void testSet() {
		int a = list1.set(0, 666);
		assertEquals("Set: check a is correct ", 65, a);
		assertEquals("Set: check element 0 is correct ", (Integer) 666, list1.get(0));
		assertEquals("Set: check size is correct ", 3, list1.size());
		
		try {
			a = longerList.set(longerList.size(), 666);
			fail("Check out of bounds");
		} catch (IndexOutOfBoundsException e) {
		}

		a = longerList.set(longerList.size() - 1, 665);
		assertEquals("Set: check a is correct ", longerList.size()-1, a);
		assertEquals("Set: check last element is correct ", (Integer) 665, longerList.get(longerList.size() - 1));
		assertEquals("Set: check size is correct ", 10, longerList.size());
	}

}
