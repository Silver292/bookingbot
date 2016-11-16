package uk.tlscott.BookingBot;

import java.util.Scanner;

public class InputHandler {
	protected Player player;
	protected Board board;
	protected PathFinder pathFinder;
	protected Scanner scan = new Scanner(System.in);
	
	public InputHandler (Board board, Player player) {
		this.board = board;
		this.player = player;
		this.pathFinder = new PathFinder(this.board);
	}
	
	public void getInput() {
		while (scan.hasNextLine()) {
			String line = scan.nextLine();
			
			if (BotStarter.DEBUG) System.out.println(line);
			
			if (line.length() == 0) continue;
			
			String[] parts = line.split(" ");
			switch (parts[0]) {
			case "settings":
				// only at start of the game contains game setting
				// settings [type] [value]
				parseSettings(parts[1], parts[2]);
			break;
			case "update":
				// This is an update of the game state. [player] indicates what bot the update is about, 
				// but could also be game to indicate a general update.
				// update [player] [type] [value]
				parseUpdate(parts);
				break;
			case "action":
				// Indicates a request for an action.
				// action [type] [time]
				// update player location
				
				player.setLocation(board);
				
				if (parts[1].equals("move")) {
					//Don't move if paralysed
					if (player.isParalyzed()) {
						System.out.println("no_moves"); 
						break;
					}
					
					Node nextMove = pathFinder.getNextMove(player);
					
					// don't move if there are no moves
					if (nextMove == null) {
						System.out.println("no_moves"); 
						break;
					}
					
					player.moveTo(nextMove);
				}
				break;
			default:
				System.out.println("no_moves");
				break;
				// error
			}
		}
	}
	
	private void parseSettings(String setting, String value) {
		
		switch (setting) {
			case "your_bot":
			    //The ID number of your bot as used in the field updates (same number as in player name)
			    player.setName(value);
				break;
				
			case "your_botid":
			    //The ID number of your bot as used in the field updates (same number as in player name)
			    player.setIcon(value.charAt(0));
			    break;
	
			case "field_width":
			    //The width of the playing field
			    board.setWidth(Integer.parseInt(value));
			    break;
	
			case "field_height":
			    // The height of the playing field
			    board.setHeight(Integer.parseInt(value));
			    board.setGrid();
			    break;
			default:
				return;
		}
	}

	private void parseUpdate(String[] updateString) {

		if (updateString[1].equals("game")) {
			if (updateString[2].equals("field")) {
				// map update
				board.updateGrid(updateString[3]);
			} // else its a round update
			return;
		}

		if (updateString[1].equals(player.getName())) {
			// Don't send move if paralysed
			if (updateString[2].equals("is_paralyzed")) {
				boolean isParalyzed = updateString[3] == "true" ? true : false;
				player.setParalyzed(isParalyzed);
			}
		}
	}		
}


