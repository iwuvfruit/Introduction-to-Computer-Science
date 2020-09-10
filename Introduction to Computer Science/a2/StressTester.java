
import java.math.BigInteger;

//Note that this tester will not check the correctness of your code.
//It only checks the time complexity.
//All tests should terminate in less than 30 seconds on a 10 year old or newer computer.
public class StressTester {
	
	// Creates a long polynomial and clones it.
	public static void testClone()
	{
		int nTerms = 1000000;
		Polynomial p1 = new Polynomial();
		
		// Create a long polynomial
		for (int i = nTerms - 1; i >= 0; i--)
			p1.addTermLast(new Term(i, new BigInteger(Integer.toString(i+1)))); // Append the terms in descending order of exponent.
		
		System.out.print("Starting stress test for deepClone O(n)...");
		
		p1.deepClone();
		
		System.out.println("Passed");
	}
	
	// Creates a long polynomial and then adds 50 terms at the end of polynomial.
	public static void testAddTerm()
	{
		int nTerms = 1000000;
		Polynomial p = new Polynomial();
		
		// Create a long polynomial
		for (int i = nTerms - 1; i >= 0; i--)
			p.addTermLast(new Term(i, new BigInteger(Integer.toString(i+1)))); // Append the terms in descending order of exponent.
		
		System.out.print("Starting stress test for addTerms O(n)...");
		
		// Test: add 50 new terms to long polynomial.
		for (int i = 0; i < 50; i++)
			p.addTerm(new Term(i, new BigInteger("1")));
				
		System.out.println("Passed");
	}
	
	// Creates two long polynomial and adds them.
	public static void testAdd()
	{
		int nTerms = 1000000;
		Polynomial p1 = new Polynomial();
		Polynomial p2 = new Polynomial();
		
		// Create two polynomials of length nTerms.
		for (int i = nTerms - 1; i >= 0; i--)
		{
			p1.addTermLast(new Term(i, new BigInteger(Integer.toString(i+1))));
			p2.addTermLast(new Term(i, new BigInteger(Integer.toString(i+1))));
		}
				
		System.out.print("Starting stress test for add O(n1 + n2)...");
		
		// Add two polynomials of length nTerms
		Polynomial.add(p1, p2);
		
		System.out.println("Passed");
	}
	
	// Creates a long polynomial and multiplies it with a single term.
	public static void testMultiplyTerm()
	{
		int nTerms = 1000000;
		Polynomial p1 = new Polynomial();
		
		// Create a long polynomial
		for (int i = nTerms - 1; i >= 0; i--)
			p1.addTermLast(new Term(i, new BigInteger(Integer.toString(i+1))));
		
		System.out.print("Starting stress test for multiplyTerm O(n)...");
		
		// Multiply a long polynomial with a single term
		p1.multiplyTermTest(new Term(1, new BigInteger("1")));
	
		System.out.println("Passed");
	}
	
	// This will test if the Polynomial.multiply implementation is either O(n1 * n2^2) or O(n1^2 * n2).
	// Create two polynomial and multiply them.
	public static void testMultiply()
	{
		int nTerms = 1500;
		Polynomial p1 = new Polynomial();
		Polynomial p2 = new Polynomial();
		
		// Create a polynomial with nTerms having exponents nTerms-1, nTerms-2, nTerms-3...,2,1,0
		for (int i = nTerms - 1; i >= 0; i--)
			p1.addTermLast(new Term(i, new BigInteger(Integer.toString(i+1))));
		
		// Create a polynomial with nTerms having exponents nTerms-1, nTerms-2, nTerms-3...,2,1,0
		for (int i = nTerms - 1; i >= 0; i--)
			p2.addTermLast(new Term(i, new BigInteger(Integer.toString(i+1))));
				
		System.out.print("Starting stress test for multiply O(n1*n2^2)...");
		
		Polynomial.multiply(p1, p2);
	
		System.out.println("Passed");
	}
	
	// Creates a long polynomial and evaluates it at x=1.
	public static void testEval()
	{
		int nTerms = 1000000;
		Polynomial p1 = new Polynomial();
				
		for (int i = nTerms - 1; i >= 0; i--)
			p1.addTermLast(new Term(i, new BigInteger(Integer.toString(i+1))));
				
		System.out.print("Starting stress test for eval O(n)...");
		p1.eval(new BigInteger("1"));
	
		System.out.println("Passed");
	}
	
	public static void main(String[] args) 
	{
		testClone();
		testAddTerm();
		testAdd();
		testMultiplyTerm();
		testMultiply();
		testEval();
	}
}