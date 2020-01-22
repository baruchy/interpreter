package command;

import java.util.List;

import connect.SimulatorClient;

public class ConnectCommand implements Command {

	@Override
	public void doCommand(List<String> arg) {
		SimulatorClient.getInstance(arg.get(0), Integer.parseInt(arg.get(1))).start();
	}

}