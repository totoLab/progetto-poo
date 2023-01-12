package self.progetto.math;

import java.util.Iterator;

public interface BigInt extends Comparable<BigInt>, Iterable<Integer> {
	
    String value(); //ritorna il valore del BigInt sottoforma di stringa di caratteri
    
	//ritorna il numero di cifre di questo BigInt
    default int length() {
    	Iterator<Integer> it = this.iterator();
    	int cont = 0;
    	for (; it.hasNext(); it.next()) cont++;
    	return cont;
    }
    
    BigInt factory( int x );
	
    BigInt incr();

    BigInt decr(); // eccezione se this è zero
	
    default BigInt add( BigInt a ) {
    	BigInt result = this;
    	
    	final BigInt bigZero = factory(0);
    	for (int currentState = 1; currentState > 0 ; // safe initializing (BUT IS IT?)
    			currentState = a.compareTo(bigZero)) { // start comparing to the end
    		result = result.incr();
    		a = a.decr();
    	}
    	return result;
    }

	// ritorna un BigInt con la differenza tra this e s; atteso this>=s
    default BigInt sub( BigInt s ) {
    	if (this.compareTo(s) < 0) throw new IllegalArgumentException();

    	final BigInt bigZero = factory(0);
    	BigInt result = this;
    	for (int currentState = 1; currentState > 0 ;
    			currentState = s.compareTo(bigZero)) {
    		result = result.decr();
    		s = s.decr();
    	}
    	return result;
    }
	
    default BigInt mul( BigInt m ) {
    	BigInt result = factory(0);
    	
    	final BigInt bigZero = factory(0);
    	for (int currentState = 1; currentState > 0 ;
    			currentState = m.compareTo(bigZero)) {
    		result = result.add(this);
    		m = m.decr();
    	}
    	return result;
    }

	//ritorna il quoziente della divisione intera tra this e d; atteso this>=d
    default BigInt div( BigInt d ) {
    	if (this.compareTo(d) < 0) throw new IllegalArgumentException();
    	
    	BigInt result = factory(0);
    	BigInt dividendo = this;
    	for (int currentState = 1; currentState > 0 ;
    			currentState = dividendo.compareTo(d)) {
    		dividendo = dividendo.sub(d);
    		result = result.incr();
    	}
    	return result;
    }

	//ritorna il resto della divisione intera tra this e d; atteso this>=d
    default BigInt rem( BigInt d ) {
    	BigInt quoziente = this.div(d); // throws exception if d doesn't meet condition
    	BigInt result = this.sub(quoziente.mul(d));
    	return result;
    }

	//calcola la potenza this^exponent
    default BigInt pow( int exponent ) {
    	BigInt result = factory(1);
    	
    	BigInt bigZero = factory(0);
    	for (BigInt cont = factory(exponent); cont.compareTo(bigZero) < 0; ) {
    		result = result.mul(this);
    		cont = cont.decr();
    	}
    	return result;
    	
    }; 
	
}
