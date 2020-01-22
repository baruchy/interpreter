package dictionary;

import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import command.Command;
import command.ConnectCommand;
import command.OpenDataServerCommand;
import command.PrintCommand;
import command.SleepCommand;
import command.VarCommand;
import command.BindCommand;
import command.DisconnectCommand;
import command.LoopCommand;
import command.ReturnCommand;
import command.VarAssigmentCommand;
import connect.SimulatorClient;

public class Dictionary {
	private static volatile Dictionary instance = null;
	private static Map<String, Variable> variableNameToVariable;
	private static Map<String, Command> commandNameToCommand;

	private Dictionary() {
		variableNameToVariable = new ConcurrentHashMap<>();
		commandNameToCommand = new ConcurrentHashMap<>();

		commandNameToCommand.put("print", new PrintCommand());
		commandNameToCommand.put("connect", new ConnectCommand());
		commandNameToCommand.put("sleep", new SleepCommand());
		commandNameToCommand.put("while", new LoopCommand());
		commandNameToCommand.put("openDataServer", new OpenDataServerCommand());
		commandNameToCommand.put("disconnect", new DisconnectCommand());
		commandNameToCommand.put("return", new ReturnCommand());
		commandNameToCommand.put("bind", new BindCommand());
		commandNameToCommand.put("var", new VarCommand());
		commandNameToCommand.put("=", new VarAssigmentCommand());

		variableNameToVariable.put("simX", new Variable(0.0));
		variableNameToVariable.put("simY", new Variable(0.0));
		variableNameToVariable.put("simZ", new Variable(0.0));
		variableNameToVariable.put("return", new Variable(0.0));

	}

	public static Dictionary getInstance() {
		Dictionary returnTable = instance;
		if (returnTable == null) {
			synchronized (Dictionary.class) {
				if (returnTable == null)
					returnTable = instance = new Dictionary();
			}
		}
		return instance;

	}

	public void setVal(String str, double val) {
		if (!variableNameToVariable.containsKey(str)) {
			variableNameToVariable.put(str, new Variable(val));
		} else {
			variableNameToVariable.get(str).setValue(val);
		}
		variableNameToVariable.get(str)
				.notifyObservers(Arrays.asList(str, String.valueOf(variableNameToVariable.get(str).getValue())));

	}

	public Double getVal(String str) {
		return variableNameToVariable.get(str).getValue();
	}

	public Variable getVariable(String str) {
		return variableNameToVariable.get(str);
	}

	public void bind(String var, String bindTo) {
		if (!variableNameToVariable.containsKey(var)) {
			setVal(var, 0.0);
		}
		if (!variableNameToVariable.containsKey(bindTo)) {
			setVal(bindTo, 0.0);
		}
		variableNameToVariable.replace(var, this.getVariable(bindTo));
		getVariable(var).setBindTo(bindTo);
		getVariable(var).addObserver(SimulatorClient.getInstance());
	}

	public void addSimVariable(String key, double value) {
		if (!variableNameToVariable.containsKey(key)) {
			variableNameToVariable.put(key, new Variable(value));
			getVariable(key).addObserver(SimulatorClient.getInstance());
		}
	}

	public Command getCommand(String commandName) {
		return commandNameToCommand.get(commandName);
	}

	public boolean symbolTableContainsCommand(String commandName) {
		return commandNameToCommand.containsKey(commandName);
	}

	public boolean symbolTableContainsVariable(String variableName) {
		return variableNameToVariable.containsKey(variableName);
	}

	public void restart() {
		variableNameToVariable.clear();
		variableNameToVariable.put("simX", new Variable(0.0));
		variableNameToVariable.put("simY", new Variable(0.0));
		variableNameToVariable.put("simZ", new Variable(0.0));
	}

}
