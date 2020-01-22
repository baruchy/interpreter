package command;

import java.util.List;

import connect.SimulatorClient;
import dictionary.Dictionary;
import utils.ExpressionUtil;

public class VarCommand implements Command {

	@Override
	public void doCommand(List<String> arg) {
		Dictionary dictionary = Dictionary.getInstance();
		if (dictionary.symbolTableContainsVariable(arg.get(0))) {
			String bindedVar = Dictionary.getInstance().getVariable(arg.get(0)).getBindTo();
			if (bindedVar != null && SimulatorClient.getInstance() != null) {
				SimulatorClient.getInstance().set(bindedVar, Double.parseDouble((String) arg.get(1)));
			}
		}

		if ((arg.size() == 1)) {
			dictionary.setVal(arg.get(0), 0.0);
		} else {
			String val = new StringBuilder("").append(arg.get(0)).toString();
			arg.remove(0);
			double result = ExpressionUtil.calculate(arg);
			arg.add(0, val);
			dictionary.setVal(val, result);
		}

	}
}
