package connect;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

import dictionary.Dictionary;

public class SimulatorServer implements Server {

	private int port;
	private int times;
	private volatile boolean stop;
	private static SimulatorServer instance;

	public SimulatorServer(int port, int times) {
		this.port = port;
		this.stop = false;
		this.times = times;		
	}
	
	public void runServer() {
		new Thread(() -> start()).start();
	}

	@Override
	public void start() {
		try (ServerSocket server = new ServerSocket(port, times);) {
			System.out.println("Server srated listening on port: " + port + " at pace: " + times);
			server.setSoTimeout(10000);
			while (!stop) {
				try (Socket client = server.accept();
						BufferedReader inFromClient = new BufferedReader(
								new InputStreamReader(client.getInputStream()));) {

					System.out.println("Server handling a client....");
					String line = null;
					while ((line = inFromClient.readLine()) != null && !stop) {
						try {
							insertValuesIntoDictionary(line);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void close() {
		Thread.currentThread().interrupt();
		stop = true;
	}

	public synchronized static SimulatorServer getInstance(int port, int times) {
		if (instance == null) {
			synchronized (SimulatorServer.class) {
				if (instance == null) {
					instance = new SimulatorServer(port, times);
				}
			}
		}
		return instance;
	}

	public synchronized static SimulatorServer getInstance() {
		return instance;
	}
	
	private void insertValuesIntoDictionaryTest(String line) {
		String[] splited=line.split(",");
		Dictionary.getInstance().setVal("simX",Double.parseDouble(splited[0]));
		Dictionary.getInstance().setVal("simY",Double.parseDouble(splited[1]));
		Dictionary.getInstance().setVal("simZ",Double.parseDouble(splited[2]));
	}
	
	private void insertValuesIntoDictionaryTestFly(String line) {
		String[] splited=line.split(",");
		Dictionary dictionary = Dictionary.getInstance();
		dictionary.setVal("/instrumentation/attitude-indicator/indicated-roll-deg", Double.parseDouble(splited[0]));
		dictionary.setVal("/instrumentation/attitude-indicator/internal-pitch-deg", Double.parseDouble(splited[1]));
        dictionary.setVal("/controls/flight/rudder", Double.parseDouble(splited[2]));
        dictionary.setVal("/controls/flight/aileron", Double.parseDouble(splited[3]));
        dictionary.setVal("/controls/flight/elevator", Double.parseDouble(splited[4]));
        dictionary.setVal("/instrumentation/altimeter/indicated-altitude-ft", Double.parseDouble(splited[5]));
		
	}


	private void insertValuesIntoDictionary(String line) {
		String[] valuesArray = line.split(",");
		Dictionary dictionary = Dictionary.getInstance();
		dictionary.setVal("/instrumentation/airspeed-indicator/indicated-speed-kt", Double.parseDouble(valuesArray[0]));
		dictionary.setVal("/instrumentation/altimeter/indicated-altitude-ft", Double.parseDouble(valuesArray[1]));
		dictionary.setVal("/instrumentation/altimeter/pressure-alt-ft", Double.parseDouble(valuesArray[2]));
		dictionary.setVal("/instrumentation/attitude-indicator/indicated-pitch-deg",
				Double.parseDouble(valuesArray[3]));
		dictionary.setVal("/instrumentation/attitude-indicator/indicated-roll-deg", Double.parseDouble(valuesArray[4]));
		dictionary.setVal("/instrumentation/attitude-indicator/internal-pitch-deg", Double.parseDouble(valuesArray[5]));
		dictionary.setVal("/instrumentation/attitude-indicator/internal-roll-deg", Double.parseDouble(valuesArray[6]));
		dictionary.setVal("/instrumentation/encoder/indicated-altitude-ft", Double.parseDouble(valuesArray[7]));
		dictionary.setVal("/instrumentation/encoder/pressure-alt-ft", Double.parseDouble(valuesArray[8]));
		dictionary.setVal("/instrumentation/gps/indicated-altitude-ft", Double.parseDouble(valuesArray[9]));
		dictionary.setVal("/instrumentation/gps/indicated-ground-speed-kt", Double.parseDouble(valuesArray[10]));
		dictionary.setVal("/instrumentation/gps/indicated-vertical-speed", Double.parseDouble(valuesArray[11]));
		dictionary.setVal("/instrumentation/heading-indicator/indicated-heading-deg",
				Double.parseDouble(valuesArray[12]));
		dictionary.setVal("/instrumentation/magnetic-compass/indicated-heading-deg",
				Double.parseDouble(valuesArray[13]));
		dictionary.setVal("/instrumentation/slip-skid-ball/indicated-slip-skid", Double.parseDouble(valuesArray[14]));
		dictionary.setVal("/instrumentation/turn-indicator/indicated-turn-rate", Double.parseDouble(valuesArray[15]));
		dictionary.setVal("/instrumentation/vertical-speed-indicator/indicated-speed-fpm",
				Double.parseDouble(valuesArray[16]));
		dictionary.setVal("/controls/flight/aileron", Double.parseDouble(valuesArray[17]));
		dictionary.setVal("/controls/flight/elevator", Double.parseDouble(valuesArray[18]));
		dictionary.setVal("/controls/flight/rudder", Double.parseDouble(valuesArray[19]));
		dictionary.setVal("/controls/flight/flaps", Double.parseDouble(valuesArray[20]));
		dictionary.setVal("/controls/engines/current-engine/throttle", Double.parseDouble(valuesArray[21]));
		dictionary.setVal("/engines/engine/rpm", Double.parseDouble(valuesArray[22]));
	}
}
