package interprter;

import java.util.LinkedList;

public class Lexer {

	public static String[][] lexer(String[] lines) {
		LinkedList<String[]> lst = new LinkedList<String[]>();

		for (int i = 0; i < lines.length; i++) {
			if (lines[i].startsWith("while")) {
				StringBuilder inloop = new StringBuilder("");
				while (!lines[i].endsWith("}")) {
					lines[i] = AddSpace(lines[i]);
					inloop.append(lines[i]).append(" ~ ");
					i++;
				}
				lines[i] = AddSpace(lines[i]);
				inloop.append(lines[i]);
				lst.add(inloop.toString().split("[ \t]+"));
			} else {
				lines[i] = AddSpace(lines[i]);
				lst.add(lines[i].split("[ \t]+"));
			}
		}

		String[][] r = new String[lst.size()][];
		lst.toArray(r);
		return r;
	}

	private static String AddSpace(String s) {
		return s.replaceAll("\t", "").replaceAll("\n", "").replaceAll("\\(", " ( ").replaceAll("\\)", " ) ")
				.replaceAll("\\{", " { ").replaceAll("\\}", " } ").replaceAll("\\+", " + ").replaceAll("\\-", " - ")
				.replaceAll("\\*", " * ").replaceAll("\\/", " / ").replaceAll("\\=", " = ").replaceAll(">\\s+=", " >= ")
				.replaceAll("<\\s+=", " <= ").replaceAll("!\\s+=", "!=").replaceAll("=\\s+=", "==");
	}

}
