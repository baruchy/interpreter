package dictionary;

import java.util.Arrays;
import java.util.Observable;

public class Variable extends Observable {
	private double value;
	private String bindTo = null;

	public Variable(double value) {
		this.value = value;
	}

	public void setValue(double value) {
		if (value != this.value) {
			this.value = value;
			setChanged();
			notifyObservers(Arrays.asList(bindTo, String.valueOf(value)));
		}
	}

	public double getValue() {
		return value;
	}

	public String getBindTo() {
		return bindTo;
	}

	public void setBindTo(String bindTo) {
		this.bindTo = bindTo;
	}

}