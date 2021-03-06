//package assignments2018.a2template;

import java.math.BigInteger;

public class Polynomial 
{
	private SLinkedList<Term> polynomial;
	public int size()
	{	
		return polynomial.size();
	}
	private Polynomial(SLinkedList<Term> p)
	{
		polynomial = p;
	}
	
	public Polynomial()
	{
		polynomial = new SLinkedList<Term>();
	}
	
	// Returns a deep copy of the object.
	public Polynomial deepClone()
	{	
		return new Polynomial(polynomial.deepClone());
	}
	
	/* 
	 * TODO: Add new term to the polynomial. Also ensure the polynomial is
	 * in decreasing order of exponent.
	 */
	public void addTerm(Term t)
	{	
		/**** ADD CODE HERE ****/
		
		// Hint: Notice that the function SLinkedList.get(index) method is O(n), 
		// so if this method were to call the get(index) 
		// method n times then the method would be O(n^2).
		// Instead, use a Java enhanced for loop to iterate through 
		// the terms of an SLinkedList.
		/*
		for (Term currentTerm: polynomial)
		{
			// The for loop iterates over each term in the polynomial!!
			// Example: System.out.println(currentTerm.getExponent()) should print the exponents of each term in the polynomial when it is not empty.  
		}
		*/
		int index = 0;
		for(Term currentTerm: polynomial) {
			int exp = currentTerm.getExponent();
			BigInteger coeff = currentTerm.getCoefficient();
			if(exp == t.getExponent()) {
				currentTerm.setCoefficient(coeff.add(t.getCoefficient()));
				if(currentTerm.getCoefficient().intValue() == 0) {
					polynomial.remove(index);
				}
			}
			if(t.getExponent() > exp) {
				polynomial.add(index, t);
			}
			index++;
		}
		polynomial.addLast(t);
		//runtime is O(n)
		
		
	}
	
	public Term getTerm(int index)
	{
		return polynomial.get(index);
	}
	
	//TODO: Add two polynomial without modifying either
	//O(n + n) 
	public static Polynomial add(Polynomial p1, Polynomial p2)
	{
		/**** ADD CODE HERE ****/
		
		Polynomial result = p1.deepClone();
		for(Term t: p2.polynomial) {
			result.addTerm(t);
		}
		return result;
	}
	
	
	//TODO: multiply this polynomial by a given term.
	private void multiplyTerm(Term t)
	{	
		/**** ADD CODE HERE ****/
		
		for(Term term: polynomial) {
			int exp = term.getExponent()+t.getExponent();
			term.setExponent(exp);
			term.setCoefficient(term.getCoefficient().multiply(t.getCoefficient()));
		}
	}
	
	//TODO: multiply two polynomials
	public static Polynomial multiply(Polynomial p1, Polynomial p2)
	{
		/**** ADD CODE HERE ****/
		Polynomial result = p1.deepClone();
		for(Term t : p2.polynomial) {
			result.multiplyTerm(t);
		}
	
		return result;
	}
	
	//TODO: evaluate this polynomial.
	// Hint:  The time complexity of eval() must be order O(m), 
	// where m is the largest degree of the polynomial. Notice 
	// that the function SLinkedList.get(index) method is O(m), 
	// so if your eval() method were to call the get(index) 
	// method m times then your eval method would be O(m^2).
	// Instead, use a Java enhanced for loop to iterate through 
	// the terms of an SLinkedList.

	public BigInteger eval(BigInteger x)
	{
		/**** ADD CODE HERE ****/
		BigInteger result = null;
		for(Term term: polynomial) {
			if(result == null) {
				result = x.pow(term.getExponent()).multiply(term.getCoefficient());
			}
			else {
				result = result.add((x.pow(term.getExponent())).multiply(term.getCoefficient()));
			}
		}
		return result==null? new BigInteger("0"): result;
	}
	
	
	// Checks if this polynomial is same as the polynomial in the argument
	public boolean checkEqual(Polynomial p)
	{	
		if (polynomial == null || p.polynomial == null || size() != p.size())
			return false;
		
		int index = 0;
		for (Term term0 : polynomial)
		{
			Term term1 = p.getTerm(index);
			
			if (term0.getExponent() != term1.getExponent() ||
				term0.getCoefficient().compareTo(term1.getCoefficient()) != 0 || term1 == term0)
					return false;
			
			index++;
		}
		return true;
	}
	
	// This method blindly adds a term to the end of LinkedList polynomial. 
	// Avoid using this method in your implementation as it is only used for testing.
	public void addTermLast(Term t)
	{	
		polynomial.addLast(t);
	}
	
	// This is used for testing multiplyTerm
	public void multiplyTermTest(Term t)
	{
		multiplyTerm(t);
	}
	
	@Override
	public String toString()
	{	
		if (polynomial.size() == 0) return "0";
		return polynomial.toString();
	}
}
