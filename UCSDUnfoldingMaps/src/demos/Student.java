package demos;

/**
 * A class to store information about a Student Used in module 4 of the UC San
 * Diego MOOC Object Oriented Programming in Java
 * 
 * @author UC San Diego Intermediate Software Development MOOC team
 * 
 */
public class Student extends Person {
	int myId;

	public Student(String name, int id) {
		super(name);
		myId = id;
	}

	public int getId() {
		return myId;
	}

	public boolean isAsleep(int hr) // override
	{
		return 2 < hr && 8 > hr;
	}

	public static void main(String[] args) {
		Person p;
		p = new Student("Sally", 5544);
		p.status(1);
		if (p instanceof Student) {
			System.out.println(((Student) p).getId());
		}
	}
}
