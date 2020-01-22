package expression;

import dictionary.Dictionary;
import dictionary.Variable;

public class Predicate {

	private double left, right;
	private Variable leftVar, rightVar;
	private String sign;

	public Predicate(String[] arg) {
		setLeft(arg[1]);
		setSign(arg[2]);
		setRight(arg[3]);
	}

	public double getLeft() {
		return left;
	}

	public void setLeft(String left) {
		if (Dictionary.getInstance().symbolTableContainsVariable(left)) {
			leftVar = Dictionary.getInstance().getVariable(left);
			this.left = Dictionary.getInstance().getVal(left);
		} else {
			this.left = Double.parseDouble(left);
		}
	}

	public double getRight() {
		return right;
	}

	public void setRight(String right) {
		if (Dictionary.getInstance().symbolTableContainsVariable(right)) {
			rightVar = Dictionary.getInstance().getVariable(right);
			this.right = Dictionary.getInstance().getVal(right);
		} else {
			this.right = Double.parseDouble(right);
		}
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public boolean calculate() {
		updateBindedValues();
		if (sign.equals(">"))
			return left > right;
		else if (sign.equals("<"))
			return left < right;
		else
			return left == right;

	}

	private void updateBindedValues() {
		if (leftVar != null && leftVar.getBindTo() != null) {
			left = Dictionary.getInstance().getVal(leftVar.getBindTo());
		}
		if (rightVar != null && rightVar.getBindTo() != null) {
			right = Dictionary.getInstance().getVal(rightVar.getBindTo());
		}
	}

}
