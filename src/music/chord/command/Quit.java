package music.chord.command;

public class Quit implements Command {

	@Override
	public void execute() {
		System.exit(0);
	}

}
