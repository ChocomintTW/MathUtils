package net.chocomint.math;

import java.util.*;
import java.util.function.DoubleUnaryOperator;
import java.util.regex.Pattern;

public class StringCalculator {

	public static final String OPERATOR = "+-*/^!";

	public static final Map<String, DoubleUnaryOperator> SINGLE_PARAMETER_FUNCTIONS = new HashMap<>(Map.ofEntries(
			new AbstractMap.SimpleEntry<>("sin" , Math::sin),
			new AbstractMap.SimpleEntry<>("cos" , Math::cos),
			new AbstractMap.SimpleEntry<>("tan" , Math::tan),
			new AbstractMap.SimpleEntry<>("cot" , t -> 1.0 / Math.tan(t)),
			new AbstractMap.SimpleEntry<>("sec" , t -> 1.0 / Math.cos(t)),
			new AbstractMap.SimpleEntry<>("csc" , t -> 1.0 / Math.sin(t)),
			new AbstractMap.SimpleEntry<>("sinh", Math::sinh),
			new AbstractMap.SimpleEntry<>("cosh", Math::cosh),
			new AbstractMap.SimpleEntry<>("tanh", Math::tanh),
			new AbstractMap.SimpleEntry<>("log" , Math::log10),
			new AbstractMap.SimpleEntry<>("ln"  , Math::log),
			new AbstractMap.SimpleEntry<>("sqrt", Math::sqrt)
	));

	/**
	 *
	 * @param formula
	 *        A math formula like {@code 3^2+sin(4*7+1)}
	 * @return The result of the formula
	 */
	public static double calculate(String formula) {
		if (!isBracketBalanced(formula))
			throw new ArithmeticException("Brackets are not balanced!");
		return calculatePostfix(infixToPostfix(formula));
	}

	private static double calculatePostfix(Stack<String> postfix) {
		Stack<String> post = new Stack<>();
		while (!postfix.isEmpty()) {
			post.push(postfix.pop());
		}

		Stack<Double> tmp = new Stack<>();
		while (!post.isEmpty()) {
			if (SINGLE_PARAMETER_FUNCTIONS.get(post.peek()) != null) {
				tmp.push(SINGLE_PARAMETER_FUNCTIONS.get(post.pop()).applyAsDouble(tmp.pop()));
			} else if (OPERATOR.contains(post.peek())) {
				double second = tmp.pop();
				double first  = tmp.pop();
				tmp.push(basicOperator(first, second, post.pop().charAt(0)));
			} else {
				tmp.push(Double.parseDouble(post.pop()));
			}
		}
		return tmp.pop();
	}

	public static Stack<String> infixToPostfix(String infix) {
		String adj = infix.replaceAll("!", "!0").replaceAll(" ", "");
		Stack<String> res = new Stack<>();
		Stack<Character> operatorStack = new Stack<>();
		StringBuilder numberBuilder = new StringBuilder();

		for (int i = 0; i < adj.length(); i++) {

			for (Map.Entry<String, DoubleUnaryOperator> entry : SINGLE_PARAMETER_FUNCTIONS.entrySet()) {
				final String key = entry.getKey();
				if (adj.startsWith(key, i)) {
					if (adj.charAt(i + key.length()) != '(') throw new ArithmeticException("Didn't find valid bracket");
					Stack<Character> op = new Stack<>();
					op.push('(');
					int j;
					for (j = i + key.length() + 1; !op.isEmpty() && j < adj.length(); j++) {
						if (adj.charAt(j) == '(') op.push('(');
						if (adj.charAt(j) == ')') op.pop();
					}
					infixToPostfix(adj.substring(i + key.length() + 1, j - 1)).forEach(res::push);
					res.push(key);
					i = j;
					break;
				}
			}

			if (i >= adj.length()) break;
			char c = adj.charAt(i);

			if (!isNumberOrPoint(c) && !numberBuilder.isEmpty()) {
				res.push(numberBuilder.toString());
				numberBuilder.setLength(0);
			}
			switch (c) {
				case ')' -> {
					while (operatorStack.peek() != '(') {
						res.push(operatorStack.pop().toString());
					}
					operatorStack.pop();
				}
				case '(' -> operatorStack.push('(');
				case '+', '-', '*', '/', '^', '!' -> {
					if (!operatorStack.isEmpty()) {
						while (priority(operatorStack.peek()) >= priority(c)) {
							res.push(operatorStack.pop().toString());
							if (operatorStack.isEmpty()) break;
						}
					}
					operatorStack.push(c);
				}
				default -> numberBuilder.append(c);
			}
		}
		if (!numberBuilder.isEmpty()) res.push(numberBuilder.toString());
		while (!operatorStack.isEmpty()) {
			res.push(operatorStack.pop().toString());
		}
		return res;
	}

	private static boolean isNumberOrPoint(char c) {
		return (c >= '0' && c <= '9') || ".".contains(c + "");
	}

	private static boolean isBracketBalanced(String formula) {
		Stack<Character> op = new Stack<>();
		for (char c : formula.toCharArray()) {
			switch (c) {
				case '(', '[', '{' -> op.push(c);
				case ')' -> {
					if (op.isEmpty() || op.peek() != '(') return false;
					else op.pop();
				}
				case ']' -> {
					if (op.isEmpty() || op.peek() != '[') return false;
					else op.pop();
				}
				case '}' -> {
					if (op.isEmpty() || op.peek() != '{') return false;
					else op.pop();
				}
			}
		}
		return op.isEmpty();
	}

	private static int priority(char c) {
		return switch (c) {
			case '!' -> 4;
			case '^' -> 3;
			case '*', '/' -> 2;
			case '+', '-' -> 1;
			default -> 0;
		};
	}

	private static double basicOperator(double var1, double var2, char op) {
		return switch (op) {
			case '+' -> var1 + var2;
			case '-' -> var1 - var2;
			case '*' -> var1 * var2;
			case '/' -> var1 / var2;
			case '^' -> Math.pow(var1, var2);
			case '!' -> Functions.gamma(var1 + 1);
			default -> 0;
		};
	}

	public static Stack<String> parseFormula(String formula) {
		String s = Pattern.compile("(\\d+)(x)").matcher(formula).replaceAll("$1*$2");
		return infixToPostfix(s);
	}

	public static void diff(String formula) {
		String[] pieces = formula.split("\\+");
		System.out.println(Arrays.toString(pieces));
	}

	public static DoubleUnaryOperator parse(String formula) {
		String s = Pattern.compile("(\\d+)(x)").matcher(formula).replaceAll("$1*$2");
		Stack<String> postfix = infixToPostfix(s);
		return x -> {
			Stack<?> clonedPostfix = (Stack<?>) postfix.clone();
			Stack<String> post = new Stack<>();
			while (!clonedPostfix.isEmpty()) {
				post.push((String) clonedPostfix.pop());
			}
			Stack<Double> tmp = new Stack<>();
			while (!post.isEmpty()) {
				if (SINGLE_PARAMETER_FUNCTIONS.get(post.peek()) != null) {
					tmp.push(SINGLE_PARAMETER_FUNCTIONS.get(post.pop()).applyAsDouble(tmp.pop()));
				} else if (OPERATOR.contains(post.peek())) {
					double second = tmp.pop();
					double first  = tmp.pop();
					tmp.push(basicOperator(first, second, post.pop().charAt(0)));
				} else {
					if (post.peek().equals("x")) {
						tmp.push(x);
						post.pop();
					}
					else tmp.push(Double.parseDouble(post.pop()));
				}
			}
			return tmp.pop();
		};
	}
}
