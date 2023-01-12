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

	private BigIntLL(LinkedList<Integer> ll) {
		this.bigInt = ll;
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
		LinkedList<Integer> ret = new LinkedList<>(bigInt);
		ListIterator<Integer> it = ret.listIterator(ret.size());
		boolean riporto = true;
		while(it.hasPrevious() && riporto) {
			Integer current = it.previous();
			if (current != 9) {
				it.set(current + 1);
				riporto = false;
			} else {
				it.set(0);
			}
		}
		
		if (riporto) {
			ret.addFirst(1);
		}
		
		return new BigIntLL(ret);
	}
	
	@Override
	public BigInt decr() throws IllegalStateException { // eccezione se this è zero
		if (this.equals(factory(0))) throw new IllegalStateException("this is already at minimum value possible.");
		
		LinkedList<Integer> ret = new LinkedList<>(bigInt);
		ListIterator<Integer> it = ret.listIterator(bigInt.size());
		boolean riporto = true;
		while(it.hasPrevious() && riporto) {
			Integer current = it.previous();
			if (current != 0) {
				it.set(current - 1);
				riporto = false;
			} else {
				it.set(9);
			}
		}
		
		it = ret.listIterator();
		while(it.hasNext() && it.next() == 0) { 
			it.remove(); // removing trailing zeros
		}
		
		return new BigIntLL(ret);
	}

	@Override
	public BigInt add(BigInt a) {
		LinkedList<Integer> first = new LinkedList<>(bigInt);
		LinkedList<Integer> second = bigIntToLL(a);
		LinkedList<Integer> ret = new LinkedList<>();

		int size1 = this.length();
		int size2 = a.length();
		
		ListIterator<Integer> it1 = null;
		ListIterator<Integer> it2 = null;
		if (this.compareTo(a) >= 0) {
			it1 = first.listIterator(size1);
			it2 = second.listIterator(size2);
		} else {
			it2 = first.listIterator(size1);
			it1 = second.listIterator(size2);
		}
		
		boolean riporto = false;
		while(it1.hasPrevious()) {
			Integer current1 = it1.previous();
			Integer current2 = (it2.hasPrevious()) ? it2.previous() : 0;
			int sum = current1 + current2;
			if (sum >= 10) { // aggiungere riporto al prossimo
				sum -= 10;
				if (riporto) {
					sum++;
				}
				ret.addFirst(sum);
				riporto = true;
			} else {
				if (riporto) {
					sum++;
				}
				ret.addFirst(sum);
				riporto = false;
			}
		}
		
		if (riporto) {
			ret.addFirst(1);
		}
		
		return new BigIntLL(ret);
	}
	
	private LinkedList<Integer> bigIntToLL(BigInt a) {
		LinkedList<Integer> ret = new LinkedList<>();
		String s = a.value();
		for (int i = 0; i < s.length(); i++) {
			ret.addLast(
				Integer.parseInt(
					Character.toString(
						s.charAt(i)
			)));
		}
		return ret;
	}

	@Override
	public int compareTo(BigInt o) {
		if (this.length() > o.length()) return 1;
		if (this.length() < o.length()) return -1;
		
		Iterator<Integer> it1 = this.iterator();
		Iterator<Integer> it2 = o.iterator();
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
