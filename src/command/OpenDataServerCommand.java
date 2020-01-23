package command;

import java.util.List;

import connect.SimulatorServer;

public class OpenDataServerCommand implements Command {

	@Override
	public void doCommand(List<String> arg) {
		SimulatorServer.getInstance(Integer.parseInt(arg.get(0)), Integer.parseInt(arg.get(1))).runServer();;
	}

}
