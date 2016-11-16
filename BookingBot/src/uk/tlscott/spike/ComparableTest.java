package uk.tlscott.spike;

import java.util.ArrayList;
import java.util.Collections;

public class ComparableTest {

	public static void main(String[] args) {
		ArrayList<TestClass> list = new ArrayList<TestClass>();
		
		for (int i = 0; i < 10; i++) {
			list.add(new TestClass(i));
		}
		
		for (int i = 0; i < list.size(); i++) {
			System.out.println(i + "| value : " + list.get(i).value());
		}

		
		System.out.println("\nShuffling list...\n");
		Collections.shuffle(list);
		
		for (int i = 0; i < list.size(); i++) {
			System.out.println(i + "| value : " + list.get(i).value());
		}
		
		System.out.println("\nResult of Collections.min...\n");
		
		System.out.println(Collections.min(list));
		
	}

}


class TestClass implements Comparable<TestClass> {

	private int value;

	public TestClass(int value) {
		this.value = value;
	}
	
	public int  value() {
		return this.value;
	}
	
	@Override
	public int compareTo(TestClass o) {
		return this.value() > o.value() ? 1 : (this.value() == o.value() ? 0 : -1);
	}
	
	@Override
	public String toString() {
		return String.format("TestClass:\nValue = %d",this.value());
	}

	
}