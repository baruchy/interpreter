package interprter;

import dictionary.Dictionary;

public class Interpreter {
	public static double interpret(String[] lines) {
		Dictionary.getInstance().restart();
		Parser.parse(Lexer.lexer(lines));
		return Dictionary.getInstance().getVal("return");
   }
}
