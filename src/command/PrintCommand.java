package command;

import java.util.List;

import dictionary.Dictionary;

public class PrintCommand implements Command {

	@Override
	public void doCommand(List<String> arg) {
		Dictionary dictionary = Dictionary.getInstance();
		for (String str : arg) {
			if (dictionary.symbolTableContainsVariable(str)) {
				if (dictionary.getVariable(str).getBindTo() != null) {
					dictionary.setVal(str, dictionary.getVal(dictionary.getVariable(str).getBindTo()));
				}
				System.out.println(dictionary.getVal(str));
			} else {
				System.out.println(str);
			}
		}
	}

}
