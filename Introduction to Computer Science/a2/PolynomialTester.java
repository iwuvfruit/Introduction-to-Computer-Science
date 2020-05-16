//package assignments2018.a2template;

import java.math.BigInteger;

public class PolynomialTester {
	
	private static void testClone()
	{	
		Polynomial poly = new Polynomial();
		
		poly.addTermLast(new Term(0, new BigInteger("1")));
		poly.addTermLast(new Term(1, new BigInteger("2")));
		poly.addTermLast(new Term(2, new BigInteger("3")));
		poly.addTermLast(new Term(3, new BigInteger("4")));
		poly.addTermLast(new Term(4, new BigInteger("5")));
		
		Polynomial clone = poly.deepClone();
		
		if (clone.checkEqual(poly))
		{
			System.out.println("Passed: deepClone()");
			return;
		}
		System.out.println("Failed: deepClone()");
	}
	
	// Tests if the terms are arranged in correct order.
	private static void testAddTerm_1()
	{
		Polynomial p1 = new Polynomial(); // reference polynomial
		Polynomial p2 = new Polynomial();
		
		p1.addTermLast(new Term(4, new BigInteger("4")));
		p1.addTermLast(new Term(3, new BigInteger("3")));
		p1.addTermLast(new Term(2, new BigInteger("2")));
		p1.addTermLast(new Term(1, new BigInteger("1")));
		
		p2.addTerm(new Term(1, new BigInteger("1")));
		p2.addTerm(new Term(2, new BigInteger("2")));
		p2.addTerm(new Term(3, new BigInteger("3")));
		p2.addTerm(new Term(4, new BigInteger("4")));
				
		if (p1.size() != 0 && p2.size() != 0 && p1.checkEqual(p2))
		{
			System.out.println("Passed: addTerm() - Test1");
			return;
		}
		System.out.println("Failed: addTerm() - Test1");
	}
	
	// Check if adding a new term updates/removes an existing term in the polynomial.   
	private static void testAddTerm_2()
	{
		Polynomial p1 = new Polynomial();
		Polynomial p2 = new Polynomial(); // reference polynomial
		
		p1.addTerm(new Term(4, new BigInteger("4")));
		p1.addTerm(new Term(3, new BigInteger("3")));
		p1.addTerm(new Term(2, new BigInteger("2")));
		p1.addTerm(new Term(1, new BigInteger("1")));
		p1.addTerm(new Term(2, new BigInteger("-1")));
		
		p2.addTermLast(new Term(4, new BigInteger("4")));
		p2.addTermLast(new Term(3, new BigInteger("3")));
		p2.addTermLast(new Term(2, new BigInteger("1")));
		p2.addTermLast(new Term(1, new BigInteger("1")));
						
		if (p1.size() != 0 && p2.size() != 0 && p1.checkEqual(p2))
		{
			System.out.println("Passed: addTerm() - Test2");
			return;
		}
		System.out.println("Failed: addTerm() - Test2");
	}
	
	// Test case when there are no terms with same exponent in two polynomial
	private static void testAdd_1()
	{
		Polynomial p1 = new Polynomial();
		Polynomial p2 = new Polynomial();
		
		p1.addTerm(new Term(2, new BigInteger("2")));
		p1.addTerm(new Term(0, new BigInteger("1")));
		
		p2.addTerm(new Term(3, new BigInteger("1")));
		p2.addTerm(new Term(4, new BigInteger("2")));
		
		Polynomial sum = Polynomial.add(p1, p2);
		Polynomial expectedSum = new Polynomial(); // reference
		expectedSum.addTermLast(new Term(4, new BigInteger("2")));
		expectedSum.addTermLast(new Term(3, new BigInteger("1")));
		expectedSum.addTermLast(new Term(2, new BigInteger("2")));
		expectedSum.addTermLast(new Term(0, new BigInteger("1")));
		
		if (sum != null && expectedSum.checkEqual(sum))
		{
			System.out.println("Passed: add() - Test 1");
			return;
		}
		System.out.println("Failed: add() - Test 1");
	}
	
	// Test case when there are terms with same exponent in two polynomial
	private static void testAdd_2()
	{
		Polynomial p1 = new Polynomial();
		Polynomial p2 = new Polynomial();
			
		p1.addTerm(new Term(2, new BigInteger("2")));
		p1.addTerm(new Term(0, new BigInteger("1")));
		p1.addTerm(new Term(3, new BigInteger("1")));
		p1.addTerm(new Term(4, new BigInteger("2")));
		
		p2.addTerm(new Term(3, new BigInteger("2")));
		p2.addTerm(new Term(2, new BigInteger("5")));
			
		Polynomial sum = Polynomial.add(p1, p2);
		Polynomial expectedSum = new Polynomial(); //reference
		expectedSum.addTermLast(new Term(4, new BigInteger("2")));
		expectedSum.addTermLast(new Term(3, new BigInteger("3")));
		expectedSum.addTermLast(new Term(2, new BigInteger("7")));
		expectedSum.addTermLast(new Term(0, new BigInteger("1")));
			
		if (sum != null && expectedSum.checkEqual(sum))
		{
			System.out.println("Passed: add() - Test 2");
			return;
		}
		System.out.println("Failed: add() - Test 2");
		}
	
	// Small polynomial test
	private static void testEval_1()
	{
		Polynomial p1 = new Polynomial();
		
		p1.addTermLast(new Term(4, new BigInteger("2")));
		p1.addTermLast(new Term(3, new BigInteger("1")));
		p1.addTermLast(new Term(2, new BigInteger("2")));
		p1.addTermLast(new Term(0, new BigInteger("1")));
				
		if (p1.eval(new BigInteger("2")).compareTo(new BigInteger("49")) == 0)
		{
			System.out.println("Passed: eval() - Test 1");
			return;
		}
		System.out.println("Failed: eval()- Test 1");
	}
	
	// Long polynomial test.    Creates a polynomial with a large number of terms and each term has 
	//  coefficient 1 and we are evaluating at x=1,  so c_i x^i = 1 for each of these terms.
	//  Thus the polynomial would have value equal to the number of terms, i.e.  1 + 1 + .... +  1 = number of terms
	private static void testEval_2()
	{
		Polynomial p1 = new Polynomial();
		
		for (int i = 0; i < 1000000; i++)
			p1.addTermLast(new Term(1000000 - i - 1, new BigInteger("1")));
		
		if (p1.eval(new BigInteger("1")).compareTo(new BigInteger("1000000")) == 0)
		{
			System.out.println("Passed: eval() - Test 2");
			return;
		}
		System.out.println("Failed: eval()- Test 2");
	}
	
	private static void testMultiplyTerm()
	{	
		Polynomial p1 = new Polynomial();
				
		p1.addTermLast(new Term(2, new BigInteger("2")));
		p1.addTermLast(new Term(0, new BigInteger("1")));
		p1.multiplyTermTest(new Term(1, new BigInteger("3")));
		
		Polynomial result = new Polynomial();
		result.addTermLast(new Term(3, new BigInteger("6")));
		result.addTermLast(new Term(1, new BigInteger("3")));
		
		if (p1.size() != 0 && p1.checkEqual(result))
		{
			System.out.println("Passed: multiplyTerm()");
			return;
		}
		System.out.println("Failed: multiplyTerm()");
	}
	
	private static void testMultiply()
	{
		Polynomial p1 = new Polynomial();
		Polynomial p2 = new Polynomial();
		
		p1.addTermLast(new Term(2, new BigInteger("2")));
		p1.addTermLast(new Term(0, new BigInteger("1")));
		
		p2.addTermLast(new Term(2, new BigInteger("2")));
		p2.addTermLast(new Term(0, new BigInteger("1")));
		
		Polynomial product = Polynomial.multiply(p1, p2);
		Polynomial expectedProduct = new Polynomial();
		
		expectedProduct.addTermLast(new Term(4, new BigInteger("4")));
		expectedProduct.addTermLast(new Term(2, new BigInteger("4")));
		expectedProduct.addTermLast(new Term(0, new BigInteger("1")));
		
		if (product != null && product.checkEqual(expectedProduct))
		{
			System.out.println("Passed: multiply()");
			return;
		}
		System.out.println("Failed: multiply()");
	}
	
	public static void main(String[] args) 
	{
		testClone();
		testAddTerm_1();
		testAddTerm_2();
		testAdd_1();
		testAdd_2();
		testEval_1();
		testEval_2();
		testMultiplyTerm();
		testMultiply();
	}
}
