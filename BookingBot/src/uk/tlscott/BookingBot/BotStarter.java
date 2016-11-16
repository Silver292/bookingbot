package uk.tlscott.BookingBot;

public class BotStarter {

	static final boolean DEBUG = true;

	public static void main(String[] args) {
		Board board = new Board();
		Player player = new Player();
		InputHandler handler = new InputHandler(board, player);
		
		if(DEBUG){
			handler = new DebugInputHandler(board, player);
		}
		
		handler.getInput();
		
	}

}
