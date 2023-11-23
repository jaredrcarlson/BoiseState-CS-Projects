import java.util.Random;
/**
 * Finds the first two "Twin-Primes" for a given input range.
 * Twin-Primes are two prime numbers, p1 and p2, such that
 * p1 + 2 = p2.
 * 
 * Fermat's Little Theorem is applied 3 times to a number in order
 * to find that a number is "Almost Certainly" prime.
 * 
 * @author jcarlson
 *
 */
public class TwinPrimesFinder {
	private int rangeMin;
	private int rangeMax;
	private int largerTwin; //p2
	private int smallerTwin; //p1
	private Random rand;
	
	public TwinPrimesFinder(int rangeMin, int rangeMax) {
		this.rangeMin = rangeMin;
		this.rangeMax = rangeMax;
		largerTwin = 5;
		smallerTwin = 3;
		rand = new Random();
		findTwinPrimes();
	}
	
	/**
	 * @return the rangeMin
	 */
	public int getRangeMin() {
		return rangeMin;
	}
	/**
	 * @param rangeMin the rangeMin to set
	 */
	public void setRangeMin(int rangeMin) {
		this.rangeMin = rangeMin;
	}
	/**
	 * @return the rangeMax
	 */
	public int getRangeMax() {
		return rangeMax;
	}
	/**
	 * @param rangeMax the rangeMax to set
	 */
	public void setRangeMax(int rangeMax) {
		this.rangeMax = rangeMax;
	}
	/**
	 * @return the largerTwin
	 */
	public int getLargerTwin() {
		return largerTwin;
	}
	/**
	 * @param largerTwin the largerTwin to set
	 */
	public void setLargerTwin(int largerTwin) {
		this.largerTwin = largerTwin;
	}
	/**
	 * @return the smallerTwin
	 */
	public int getSmallerTwin() {
		return smallerTwin;
	}
	/**
	 * @param smallerTwin the smallerTwin to set
	 */
	public void setSmallerTwin(int smallerTwin) {
		this.smallerTwin = smallerTwin;
	}
	
	/**
	 * Attempts to locate the first set of twin primes within range.
	 */
	private void findTwinPrimes() {
		for(int smaller=rangeMin, larger=smaller+2; larger <= rangeMax; smaller++, larger++) {
			if(isPrime(smaller) && isPrime(larger)) {
				largerTwin = larger;
				smallerTwin = smaller;
				break;
			}
		}
	}
	
	private boolean isPrime(int number) {
		if(passesLittleFermatTest(number)) {
			return true;
		}
		else {
			return false;
		}
	}
	
	private boolean passesLittleFermatTest(int p) {
		boolean pass = false;
		for(int i = 0; i < 3; i++) {
			int a = rand.nextInt(p-1) + 1;
			long result = largePow(a, p-1);
			if((result % p) == 1) {
				pass = true;
			}
			else {
				pass = false;
				break;
			}
		}
		return pass;
	}
	
	private long largePow(int a, int e) {
		String exp = Integer.toString(e, 2);
		long result = a;
		int p = e+1;
		for(int i=1; i < exp.length(); i++) {
			int bit = Character.getNumericValue(exp.charAt(i));
			if(bit == 1) {
				result = ((result * result) * a) % p;
			}
			else {
				result = (result * result) % p;
			}
		}
		return result;
	}
}
