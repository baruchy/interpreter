package command;

import java.io.IOException;

import java.util.List;

import connect.SimulatorClient;
import connect.SimulatorServer;

public class DisconnectCommand implements Command {

	public void doCommand(List<String> arg) {
		try {
			SimulatorClient.getInstance().close();
			if (SimulatorServer.getInstance() != null)
				SimulatorServer.getInstance().close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
