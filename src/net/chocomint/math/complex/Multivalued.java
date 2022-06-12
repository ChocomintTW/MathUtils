package net.chocomint.math.complex;

public class Multivalued {
	private final MVFunc f;

	public Multivalued(MVFunc func) {
		this.f = func;
	}

	public Complex PV() {
		return f.get(0);
	}

	public Complex value(int k) {
		return f.get(k);
	}

	protected interface MVFunc {
		Complex get(int k);
	}

	@Override
	public String toString() {
		return this.PV().toString();
	}
}
