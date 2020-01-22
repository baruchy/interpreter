package command;

import java.util.List;

import dictionary.Dictionary;
import utils.ExpressionUtil;

public class VarAssigmentCommand implements Command {

	@Override
	public void doCommand(List<String> arg) {
		String varName = new StringBuilder(arg.get(0)).toString();
		arg.remove(0);
		double result = ExpressionUtil.calculate(arg);
		arg.add(0, varName);
		Dictionary.getInstance().setVal(varName, result);
		if (Dictionary.getInstance().getVariable(varName).getBindTo() != null) {
			Dictionary.getInstance().setVal(Dictionary.getInstance().getVariable(varName).getBindTo(), result);
		}
	}

}
