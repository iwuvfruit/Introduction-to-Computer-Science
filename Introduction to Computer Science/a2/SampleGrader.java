
import java.math.BigInteger;
import java.util.Random;

public class SampleGrader {
	
	private static boolean checkStarterCode()
	{
		Polynomial p = new Polynomial();
		
		for (int i = 0; i < 500; i++)
			p.addTermLast(new Term(500 - i - 1, new BigInteger(Integer.toString(i))));
		
		// Check if addTermLast is okay
		if (p.size() != 500)
			return false;
		
		BigInteger result = new BigInteger("0");
		BigInteger one = new BigInteger("1");
		int expectedResult = 0;
		for (int i = 0; i < 500; i++)
		{
			Term currentTerm = p.getTerm(i);
			if (currentTerm.getExponent() != (500 - i - 1))
				return false;
			else if (currentTerm.getCoefficient().compareTo(new BigInteger(Integer.toString(i))) != 0)
				return false;
			result = result.add(currentTerm.eval(one));
			expectedResult += i;
		}
		
		if (result.compareTo(new BigInteger(Integer.toString(expectedResult))) != 0)
			return false;
		
		Polynomial pClone = new Polynomial();
		Polynomial pBadClone = new Polynomial();
		
		for (int i = 0; i < 500; i++) 
		{
			Term term = new Term(500 - i - 1, new BigInteger(Integer.toString(i)));
			pClone.addTermLast(term);
			pBadClone.addTermLast(term);
		}
		
		//Should be equal
		if (!pClone.checkEqual(p))
			return false;
		
		// Shouldn't be equal
		if (pClone.checkEqual(pBadClone))
			return false;
		
		return true;
	}
	
	private static void shuffleArray(int[] array)
	{
	    int index, temp;
	    Random random = new Random(500); // seeding causes the function to always generate same sequence of random number 
	    for (int i = array.length - 1; i > 0; i--)
	    {
	        index = random.nextInt(i + 1);
	        temp = array[index];
	        array[index] = array[i];
	        array[i] = temp;
	    }
	}
	
	private static String gradeString(int score, int maxScore, String comment)
	{
		return Integer.toString(score) + " out of " + Integer.toString(maxScore) + " " + comment;
	}
	
	private static void write(String string)
	{	
		System.out.println(string);
	}
	
	public static void testClone_1()
	{
		String comment = "Test deepClone() with n terms.";
		int maxScore = 8;
						
		try 
		{	
			if (!checkStarterCode()) 
			{
				comment = comment + " Bad starter code!!";
				write(gradeString(0, maxScore, comment));
				return;
			}
			
			Polynomial p = new Polynomial();
			for (int i = 0; i < 100; i++)
			{
				p.addTermLast(new Term(100 - i - 1, new BigInteger("1")));
				Polynomial cloned = p.deepClone();
				if (!cloned.checkEqual(p))
				{
					write(gradeString(0, maxScore, comment));
					return;
				}
			}
			write(gradeString(maxScore, maxScore, comment));
		} catch(Exception e)
		{	
			comment = comment + " Exception caught!!";
			write(gradeString(0, maxScore, comment));
		}
	}
		
	public static void testAddTerm_1()
	{
		String comment = "Test addTerm() correctness.";
		int maxScore = 10;
		int score = 0;
		int problemSize = 400;
		
		try 
		{	
			if (!checkStarterCode()) 
			{
				comment = comment + " Bad starter code!!";
				write(gradeString(0, maxScore, comment));
				return;
			}
			
			boolean flag = true;
			for (int size = 7; size < problemSize; size++)
			{
				Polynomial p = new Polynomial();
				Polynomial pRef = new Polynomial();
				
				int []shuffle = new int[size];
				for (int i = 0; i < size; i++)
					shuffle[i] = i;
				
				shuffleArray(shuffle); // shuffle the array
				for (int i = 0; i < size; i++)
				{
					int exponent = shuffle[i]; // Add terms in random order
					p.addTerm(new Term(exponent, new BigInteger(Integer.toString(exponent + 1))));
					exponent = size - i - 1; // Add terms descending order.
					pRef.addTermLast(new Term(exponent, new BigInteger(Integer.toString(exponent + 1))));
				}
				
				if (!p.checkEqual(pRef))
				{
					comment = comment + " Failed descending order test.";
					flag = false;
					break;
				}
			}
			
			if (flag)
				score += 3;
			
			flag = true;			
			for (int size = 7; size < problemSize; size++)
			{
				Polynomial p = new Polynomial();
								
				int []shuffle = new int[size];
				for (int i = 0; i < size; i++)
					shuffle[i] = i;
				
				shuffleArray(shuffle); // Randomly shuffle 
				for (int i = 0; i < size; i++)
				{
					int exponent = size - i - 1;
					p.addTermLast(new Term(exponent, new BigInteger(Integer.toString(exponent + 1))));
				}
				
				for (int i = 0; i < size; i++)
				{
					int exponent = shuffle[i]; // Remove a term from polynomial p by adding a term with negative coefficient. 
					p.addTerm(new Term(exponent, new BigInteger(Integer.toString(-(exponent + 1)))));
				}
								
				if (p.size() > 0)
				{	
					comment = comment + " Failed cancellation of terms.";
					flag = false;
					break;
				}
			}
			
			if (flag)
				score += 4;
			
			flag = true;			
			for (int size = 7; size < problemSize; size++)
			{
				Polynomial p = new Polynomial();
				Polynomial pRef = new Polynomial();
				
				int []shuffle = new int[size];
				for (int i = 0; i < size; i++)
					shuffle[i] = i;
				
				shuffleArray(shuffle);
				for (int i = 0; i < size; i++)
				{
					int exponent = size - i - 1;
					p.addTermLast(new Term(exponent, new BigInteger(Integer.toString(exponent + 1))));
					pRef.addTermLast(new Term(exponent, new BigInteger(Integer.toString(2*exponent + 2))));
				}
				
				for (int i = 0; i < size; i++)
				{
					int exponent = shuffle[i];
					p.addTerm(new Term(exponent, new BigInteger(Integer.toString((exponent + 1)))));
				}
								
				if (!p.checkEqual(pRef))
				{	
					comment = comment + " Failed updaing terms.";
					flag = false;
					break;
				}
			}
			
			if (flag)
				score += 3;
			
			write(gradeString(score, maxScore, comment));
			
		} catch(Exception e)
		{	
			comment = comment + " Exception caught!!";
			write(gradeString(score, maxScore, comment));
		}
	}
	
	
	public static void testEval()
	{	
		String comment = "Test eval().";
		int maxScore = 10;
		int score = 0;	
		try 
		{	
			if (!checkStarterCode()) 
			{
				comment = comment + " Bad starter code!!";
				write(gradeString(0, maxScore, comment));
				return;
			}
			
			Polynomial p1 = new Polynomial();
			BigInteger one = new BigInteger("1");
			BigInteger zero = new BigInteger("0");
			if (p1.eval(one).compareTo(zero) == 0)
				score++;
			
			for (int size = 1; size < 30; size++)
			{	
				p1 = new Polynomial();
				int []randArr = new int[size];
				randArr[0] = 1 + (int)(Math.random() * 20);
				for (int i = 1; i < size; i++)
				{
					int random = 1 + (int)(Math.random() * 20);
					randArr[i] +=  randArr[i - 1] + random;
				}
				
				BigInteger expectedResult = new BigInteger("0");
				for (int i = 0; i < size; i++)
				{	
					int random = 1 + (int)(Math.random() * 5);
					Term t = new Term(randArr[size - i - 1], new BigInteger(Integer.toString(random)));
					p1.addTermLast(t);
					expectedResult = expectedResult.add(t.eval(new BigInteger("2")));
				}
				
				if (p1.eval(new BigInteger("2")).compareTo(expectedResult) != 0)
				{	
					comment = comment + " Error: incorrect evaluation!!";
					write(gradeString(score, maxScore, comment));
					return;
				}
			}
			score += 9;			
			write(gradeString(score, maxScore, comment));
		} catch(Exception e)
		{	
			comment = comment + " Exception caught!!";
			write(gradeString(score, maxScore, comment));
		}
	}
	
	public static void main(String[] args) 
	{	
		SampleGrader.testClone_1();
		SampleGrader.testAddTerm_1();
		SampleGrader.testEval();
	}
}