package self.progetto;

import java.util.Iterator;

public interface BigInt extends Comparable<BigInt>, Iterable<Integer> {
	
	/**
	 * restituisce il valore del BigInt sottoforma di stringa di caratteri
	 * @return rappresentazione del valore come stringa
	 */
    String value();
    
    /**
     * @returns numero di cifre del BigInt this
     */
    default int length() {
    	Iterator<Integer> it = this.iterator();
    	int cont = 0;
    	for (; it.hasNext(); it.next()) cont++;
    	return cont;
    }
    
    /**
     * restituiscce una nuova istanza di BigInt con le stesse cifre di x
     * @param x intero da cui costruire il BigInt
     * @returns nuova istanza di BigInt
     */
    BigInt factory( int x );
	
    /**
     * @returns una nuova istanza di BigInt con valore aumentato di 1 rispetto a this
     */
    BigInt incr();

    /**
     * @returns una nuova istanza di BigInt con valore ridotto di 1 rispetto a this
     * @throws IllegalStateException nel caso in cui il BigInt abbia già il valore minimo 0
     */
    BigInt decr() throws IllegalStateException;
	
    /**
     * @param a secondo termine della somma tra BigInt (this + a)
     * @returns nuovo BigInt risultato della somma
     */
    default BigInt add( BigInt a ) {
    	BigInt result = this;
    	
    	final BigInt bigZero = factory(0);
    	for (int currentState = 1; currentState > 0 ;
    			currentState = a.compareTo(bigZero)) { // start comparing to the end
    		result = result.incr();
    		a = a.decr();
    	}
    	return result;
    }

    /**
     * @param s secondo termine della differenza tra BigInt (this - s)
     * @returns nuovo BigInt risultato della differenza
     * @throws IllegalArgumentException nel caso in cui il secondo termine della differenza sia maggiore del primo (atteso this >= s)
     */
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
	
    /**
     * @param a secondo termine del prodotto tra BigInt (this * a)
     * @returns nuovo BigInt risultato del prodotto
     */
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

    /**
     * @param d secondo termine della divisione intera tra BigInt (this / d)
     * @returns nuovo BigInt risultato della divisione intera
     * @throws IllegalArgumentException nel caso in cui il divisore sia maggiore del primo (atteso this >= d)
     */
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
    
    /**
     * esegue la divisione intera e poi ne calcola il resto eseguendo la differenza tra il dividendo e il quoziente (this - (this / d))
     * @param d secondo termine della divisione intera tra BigInt (this / d)
     * @returns nuovo BigInt resto della divisione intera
     * @throws IllegalArgumentException nel caso in cui il divisore sia maggiore del primo (atteso this >= d), eccezione ereditata da div()
     */
    default BigInt rem( BigInt d ) throws IllegalArgumentException {
    	BigInt quoziente = this.div(d);
    	BigInt result = this.sub(quoziente.mul(d));
    	return result;
    }

    /**
     * @param exponent esponente della potenza (this^exponent)
     * @returns nuovo BigInt risultato della potenza
     */
    default BigInt pow( int exponent ) {
    	BigInt result = factory(1);
    	
    	BigInt bigZero = factory(0);
    	for (BigInt cont = factory(exponent); cont.compareTo(bigZero) > 0; ) {
    		result = result.mul(this);
    		cont = cont.decr();
    	}
    	return result; 	
    }; 
	
}
