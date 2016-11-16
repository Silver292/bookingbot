package uk.tlscott.BookingBot;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class DebugInputHandler extends InputHandler{

	
	public DebugInputHandler(Board board, Player player) {
		super(board, player);
		try {
			scan =  new Scanner(new File("G:\\Scott\\Documents\\Projects\\BookingBot\\DebugData"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

}
