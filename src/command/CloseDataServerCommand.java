package command;

import java.util.List;

import connect.SimulatorServer;

public class CloseDataServerCommand implements Command {

	@Override
	public void doCommand(List<String> arg) {
		if (SimulatorServer.getInstance() != null) {
			SimulatorServer.getInstance().close();
		}
	}

}
