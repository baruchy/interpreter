package command;

import java.util.List;

import connect.SimulatorClient;
import dictionary.Dictionary;
import dictionary.Variable;

public class BindCommand implements Command {

	@Override
	public void doCommand(List<String> arg) {
		Dictionary dictionary = Dictionary.getInstance();
		String varName = arg.get(0);
		Variable variable = dictionary.getVariable(varName);
		if (variable == null) {
			dictionary.setVal(varName, 0);
			variable = dictionary.getVariable(varName);
		}
		arg.remove(0);
		String bindToName = concatBindedVariableName(arg);
		variable.setBindTo(bindToName);
		Variable bindVariable = dictionary.getVariable(bindToName);
		if (bindVariable == null) {
			dictionary.setVal(bindToName, 0);
			bindVariable = dictionary.getVariable(bindToName);
		} else {
			variable.setValue(bindVariable.getValue());
		}
		bindVariable.setBindTo(varName);
		variable.addObserver(SimulatorClient.getInstance());
	}

	private String concatBindedVariableName(List<String> arg) {
		StringBuilder bindName = new StringBuilder();
		arg.forEach(s -> bindName.append(s.replaceAll("\\s+", "")));
		return bindName.toString();
	}
}
