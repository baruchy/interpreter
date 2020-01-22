package command;

import java.util.List;

public class SleepCommand implements Command {

	@Override
	public void doCommand(List<String> arg) {
		try {
			Thread.sleep(Long.parseLong(arg.get(0)));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
