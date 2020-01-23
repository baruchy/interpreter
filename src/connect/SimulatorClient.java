package connect;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import dictionary.Variable;

public class SimulatorClient implements Client, Observer {

	private int port;
	private String ip;
	private static SimulatorClient instance;
	private Socket interpreter;
	private PrintWriter out;
	private volatile boolean connected;

	private SimulatorClient(String ip, int port) {
		this.port = port;
		this.ip = ip;
		connected = false;
	}

	@Override
	public void start() {
		if (connected) {
			return;
		}
		try {
			interpreter = new Socket(ip, port);
			out = new PrintWriter(interpreter.getOutputStream());
			System.out.println("Client is connected to Simulator Server on port: " + port);
			connected = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void set(String path, Double val) {
		waitToConnection();
		out.println("set " + path + " " + val);
		out.flush();
	}

	@Override
	public void close() throws IOException {
		waitToConnection();
		out.println("bye");
		out.flush();
		out.close();
		interpreter.close();
	}

	public synchronized static SimulatorClient getInstance(String ip, int port) {
		if (instance == null) {
			synchronized (SimulatorServer.class) {
				if (instance == null)
					instance = new SimulatorClient(ip, port);
			}
		}
		return instance;
	}

	public synchronized static SimulatorClient getInstance() {		
		return instance;
	}

	@Override
	public void update(Observable o, Object arg) {
		if (o instanceof Variable) {
			List<String> argList = (List<String>) arg;
			set(argList.get(0), Double.parseDouble(argList.get(1)));
		}
	}
	
	public void waitToConnection() {
		while (!connected) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
