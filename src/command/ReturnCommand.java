package command;

import java.util.List;

import dictionary.Dictionary;
import utils.ExpressionUtil;

public class ReturnCommand implements Command {

	@Override
	public void doCommand(List<String> arg) {
		double result = ExpressionUtil.calculate(arg);
		Dictionary dictionary = Dictionary.getInstance();
	      if (dictionary.symbolTableContainsVariable(arg.get(0))) {
	    	  dictionary.setVal(arg.get(0), result);
	    	  dictionary.setVal("return", result);
	      } else {
	    	  dictionary.setVal("return", result);
	      }
	}

}
