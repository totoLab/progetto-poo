package self.math;

public abstract class AbstractBigInt implements BigInt {
	
	@Override
	public String toString() {
		return this.value();
	}
	
	@Override
	public boolean equals(Object o) {
		if (o == null) return false;
		if (o == this) return true;
		if (!(o instanceof BigInt)) return false;
		BigInt big = (BigInt) o;
	
		return this.toString().equals(big.toString());
	}
	
	@Override
	public int hashCode() {
		return this.toString().hashCode();
	}
	
}
