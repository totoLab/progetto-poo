package self.progetto;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;

import javax.swing.plaf.basic.BasicProgressBarUI;

public class BigIntLL extends AbstractBigInt {
	
	final private LinkedList<Integer> bigInt; // first element is most significant digit
	final static String nonNumericRegex = "\\D";
	
	public BigIntLL(int x) {
		this(Integer.toString(x));
	}
	
	public BigIntLL(String s) {
		if (s.matches(nonNumericRegex)) throw new IllegalArgumentException(); // also catches negatives
		  
		bigInt = new LinkedList<>();
		for (int i = 0; i < s.length(); i++) {
			bigInt.add(
				Integer.parseInt(
					Character.toString(
						s.charAt(i)
			)));
		}
	}
	
	@Override
	public String value() {
		Iterator<Integer> it = bigInt.iterator();
		StringBuilder sb = new StringBuilder();
		while (it.hasNext()) {
			sb.append(it.next());
		}
		return sb.toString();
	}

	@Override
	public int length() { // optimization
		return bigInt.size();
	}

	@Override
	public BigInt factory(int x) {
		return new BigIntLL(x);
	}

	@Override
	public BigInt incr() {
		BigIntLL ret = new BigIntLL(this.value());
		ListIterator<Integer> it = ret.listIterator();
		boolean riporto = true;
		while(it.hasNext() && riporto) {
			Integer current = it.next();
			if (current != 9) {
				it.set(current + 1);
				riporto = false;
			} else {
				it.set(0);
			}
		}
		
		if (riporto) {
			it.add(1);
		}
		
		return ret;
	}
	
	@Override
	public BigInt decr() throws IllegalStateException { // eccezione se this è zero
		if (this.equals(factory(0))) throw new IllegalStateException("this is already at minimum value possible.");
		
		BigIntLL ret = new BigIntLL(this.value());
		ListIterator<Integer> it = ret.listIterator();
		boolean riporto = true;
		while(it.hasNext() && riporto) {
			Integer current = it.next();
			if (current != 0) {
				it.set(current - 1);
				riporto = false;
			} else {
				it.set(9);
			}
		}
		
		while(it.hasNext() ) { // removing trailing zeros
			if (it.next() == 0)
				it.remove();
		}
		
		return ret;
	}

	@Override
	public int compareTo(BigInt o) {
		if (this.length() > o.length()) return 1;
		if (this.length() < o.length()) return -1;
		
		Iterator<Integer> it1 = getInvertedObjectValue(this).iterator();
		Iterator<Integer> it2 = getInvertedObjectValue(o).iterator();
		for (; it1.hasNext() && it2.hasNext() ;) {
			Integer i1 = it1.next();
			Integer i2 = it2.next();
			if (i1 != i2) return i1 - i2;
		}
		return 0;
	}

	@Override
	public Iterator<Integer> iterator() {
		return bigInt.iterator();
	}
	
	private ListIterator<Integer> listIterator() {
		return bigInt.listIterator();
	}
	
	private LinkedList<Integer> getInvertedObjectValue(BigInt b) {
		StringBuilder sb = new StringBuilder();
		sb.append(b.value());
		sb.reverse();
		String s = sb.toString();
		LinkedList<Integer> result = new LinkedList<>();
		for (int i = 0; i < s.length(); i++) {
			String c = "" + s.charAt(i);
			result.add(
					Integer.parseInt(c)
			);
		}
		return result;
	}
	
	private BigInt llToBigInt(LinkedList<Integer> ll) { // assuming list first item is less significant digit
		if (ll == null || ll.size() == 0) throw new IllegalArgumentException();
		
		Iterator<Integer> it = ll.descendingIterator();
		StringBuilder sb = new StringBuilder();
		while (it.hasNext()) {
			sb.append(
				Integer.toString(
					it.next()
				)
			);
		}
		return new BigIntLL(sb.toString());
	}
	
}
