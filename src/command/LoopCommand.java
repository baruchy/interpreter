package command;

import java.util.List;

public class LoopCommand implements Command {

	private List<Command> commandList;

	public List<Command> getCommandList() {
		return commandList;
	}

	public void setCommandList(List<Command> commandList) {
		this.commandList = commandList;
	}

	@Override
	public void doCommand(List<String> arg) {
	}

}
