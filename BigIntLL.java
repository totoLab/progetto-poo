package self.progetto;

import java.util.*;

public class BigIntLL extends AbstractBigInt {
	
	/*
	 * il primo elemento della lista è la cifra più significativa dell'intero rappresentato
	 */
	final private List<Integer> bigInt;
	
	/*
	 * regular expression che trova le corrispondenze per caratteri non numerici
	 */
	final static String nonNumericRegex = "\\D";
	
	public BigIntLL(int x) {
		this(Integer.toString(x));
	}
	
	/*
	 * il costruttore tramite una regex controlla che la stringa rappresenti un numero e che questo sia senza segno
	 * successivamente inizializza la lista bigInt ai valori dei singoli caratteri della stringa, dal più al meno significativo
	 */
	public BigIntLL(String s) {
		if (s.matches(nonNumericRegex)) throw new IllegalArgumentException();
		  
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

	/**
	 * migliora le performance sfruttando il metodo della struttura dati
	 */
	@Override
	public int length() {
		return bigInt.size();
	}

	@Override
	public BigInt factory(int x) {
		return new BigIntLL(x);
	}

	/**
	 * incrementa di 1 il valore del bigInt secondo l’algoritmo carta-e-penna procedendo cioè cifra per cifra
	 * (dalla posizione meno significativa a quella più significativa), generando il riporto
	 * e utilizzandolo negli stadi successivi, (infatti il listIterator della LinkedList
	 * parte dall'ultimo elemento e si sposta sfruttando il metodo previous())
	 */
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
	
	/**
	 * riduce di 1 il valore del bigInt secondo l’algoritmo carta-e-penna procedendo cioè cifra per cifra
	 * (dalla posizione meno significativa a quella più significativa), generando il riporto
	 * e utilizzandolo negli stadi successivi, (infatti il listIterator della LinkedList
	 * parte dall'ultimo elemento e si sposta sfruttando il metodo previous())
	 */
	@Override
	public BigInt decr() throws IllegalStateException {
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

	/**
	 * il metodo migliora l'implementazione di add (metodo default), usando ancora l'algoritmo carta e penna
	 * invece di incrementare tante volte quanto è il valore del secondo intero
	 */
	@Override
	public BigInt add(BigInt a) {
		LinkedList<Integer> first = bigIntToLL(this);
		LinkedList<Integer> second = bigIntToLL(a);
		LinkedList<Integer> ret = new LinkedList<>();

		int size1 = first.size();
		int size2 = second.size();
		
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
			
			if (riporto) {
				sum++;
			}
			
			if (sum >= 10) { // aggiungere riporto al prossimo
				sum -= 10;
				riporto = true;
			} else {
				riporto = false;
			}
			ret.addFirst(sum);
		}
		
		if (riporto) {
			ret.addFirst(1);
		}
		
		return new BigIntLL(ret);
	}
	
	/**
	 * dato un BigInt restituisce l'ipotetica LinkedList corrispondente,
	 * utilizzabile solo all'interno dell'implementazione BigIntLL
	 * @param a BigInt da cui ricavare le cifre
	 * @returns LinkedList con le cifre del BigInt come elementi
	 */
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

	/**
	 * implementazione custom di Comparable per i BigInt
	 * verifica prima la lunghezza, a parità di lunghezza verifica il valore delle cifre
	 * dalla più significativa alla meno significativa
	 * @returns intero maggiore di 0 se this > o, intero minore di 0 se this < 0, 0 se this = o
	 */
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
	
}
