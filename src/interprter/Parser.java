package interprter;

import java.util.LinkedList;

import dictionary.Dictionary;
import expression.Predicate;

public class Parser {

	public static double parse(String[][] tokenLines) {
		Dictionary dictionary = Dictionary.getInstance();
		for (String[] line : tokenLines) {
			parseLine(line);
		}
		if (!dictionary.symbolTableContainsVariable("return")) {
			dictionary.setVal("return", 0);
		}
		return dictionary.getVal("return");
	}

	public static void parseLine(String[] line) {
		try {
			Thread.sleep(150);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		if (line[0].equals("while")) {
			whileHandler(line);
		}

		LinkedList<String> argList = toTokenList(line);

		if (isBindCommand(line)) {
			Dictionary.getInstance().getCommand("bind").doCommand(argList);
		} else if (Dictionary.getInstance().symbolTableContainsCommand(line[0])) {
			Dictionary.getInstance().getCommand(line[0]).doCommand(argList);
		} else {
			Dictionary.getInstance().getCommand("=").doCommand(argList);
		}
	}

	private static boolean isBindCommand(String[] line) {
		for (String s : line) {
			if (s.equals("bind"))
				return true;
		}
		return false;
	}

	public static LinkedList<String> toTokenList(String[] lineTokens) {
		LinkedList<String> tokensList = new LinkedList<>();
		for (String s : lineTokens) {
			tokensList.add(s);
		}
		if (!lineTokens[0].equals("while")) {
			if (Dictionary.getInstance().symbolTableContainsCommand(tokensList.get(0)))
				tokensList.removeFirst();
			if (tokensList.contains("="))
				tokensList.remove("=");
			if (tokensList.contains("bind"))
				tokensList.remove("bind");
		}
		return tokensList;
	}

	public static void whileHandler(String[] line) {
		Predicate predicate = new Predicate(line);
		LinkedList<String> tokenList = toTokenList(line);

		while (predicate.calculate()) {
			for (int i = 0; i < tokenList.size(); i++) {
				if (tokenList.get(i).equals("~")) {
					StringBuilder temp = new StringBuilder("");
					for (int j = i + 1; j < tokenList.size() && !tokenList.get(j).equals("~")
							&& !tokenList.get(j).equals("}"); j++, i++) {
						temp.append(tokenList.get(j)).append(" ");
					}
					String[] goToParse = temp.toString().split(" ");
					if (goToParse.length != 1) {
						parseLine(goToParse);
					}
				}
			}
			predicate.setLeft(line[1]);
			predicate.setRight(line[3]);
		}
	}

}
