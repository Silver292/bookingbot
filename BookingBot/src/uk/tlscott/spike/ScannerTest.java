package uk.tlscott.spike;

import java.util.Scanner;

public class ScannerTest {
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		
		for (int i = 1; i < 6; i++) {
			
			System.out.println("TEST " + i);
			
			if (scan.hasNextLine()) {
				String testString = scan.nextLine();
				System.out.println("Scanner: " + testString);
			}
		}
		scan.close();
	}
}
