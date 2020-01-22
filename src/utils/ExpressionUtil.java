package utils;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

import dictionary.Dictionary;
import expression.Div;
import expression.Expression;
import expression.Minus;
import expression.Mul;
import expression.Plus;
import expression.Number;;

public class ExpressionUtil {

	public static double calculate(List<String> arg) {
		return calculate(StringUtils.convertListToString(arg));
	}

	public static double calculate(String expression) {
		Queue<String> postfixQueue = infixToPostfixQueue(expression);
		return Math.floor((toExpressionStack(postfixQueue).pop().calculate() * 1000)) / 1000;
	}

	public static Queue<String> infixToPostfixQueue(String expression) {
		Queue<String> postfixQueue = new LinkedList<String>();
		Stack<String> stack = new Stack<String>();
		String[] split = expression.split("(?<=[-+*/()])|(?=[-+*/()])");
		for (String s : split) {
			if (Dictionary.getInstance().symbolTableContainsVariable(s))
				if (Dictionary.getInstance().getVariable(s).getBindTo() != null) {
					postfixQueue.add(Dictionary.getInstance()
							.getVal(Dictionary.getInstance().getVariable(s).getBindTo()).toString());
				} else {
					postfixQueue.add(Dictionary.getInstance().getVal(s).toString());
				}
			if (StringUtils.isDouble(s)) {
				postfixQueue.add(s);
			} else {
				switch (s) {
				case "/":
				case "*":
				case "(":
					stack.push(s);
					break;
				case "+":
				case "-":
					while (!stack.empty() && (!stack.peek().equals("("))) {
						postfixQueue.add(stack.pop());
					}
					stack.push(s);
					break;
				case ")":
					while (!stack.peek().equals("(")) {
						postfixQueue.add(stack.pop());
					}
					stack.pop();
					break;
				}
			}
		}
		while (!stack.isEmpty()) {
			postfixQueue.add(stack.pop());
		}
		return postfixQueue;
	}

	public static Stack<Expression> toExpressionStack(Queue<String> postfixQueue) {
		Stack<Expression> expressionStack = new Stack<Expression>();
		for (String postfixExpression : postfixQueue) {
			if (StringUtils.isDouble(postfixExpression)) {
				expressionStack.push(new Number(Double.parseDouble(postfixExpression)));
			} else {
				Expression right = expressionStack.pop();
				Expression left = expressionStack.pop();

				switch (postfixExpression) {
				case "/":
					expressionStack.push(new Div(left, right));
					break;
				case "*":
					expressionStack.push(new Mul(left, right));
					break;
				case "+":
					expressionStack.push(new Plus(left, right));
					break;
				case "-":
					expressionStack.push(new Minus(left, right));
					break;
				}
			}
		}
		return expressionStack;
	}
}
