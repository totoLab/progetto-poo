package self.progetto.math;

public abstract class AbstractBigInt implements BigInt {
	
	@Override
	public String toString() {
		return this.value();
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj == null) return false;
		if (obj == this) return true;
		if (!(obj instanceof BigInt)) return false;
		BigInt big = (BigInt) obj;
	
		return this.toString().equals(big.toString());
	}
	
	@Override
	public int hashCode() {
		int prime = 31;
		int h = 0;
		h = h + prime * this.toString().hashCode();
		return h;
	}
	
}
