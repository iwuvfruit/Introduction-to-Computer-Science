import java.io.Serializable;
import java.util.ArrayList;
import java.text.*;
import java.lang.Math;

public class DecisionTree implements Serializable {

	DTNode rootDTNode;
	int minSizeDatalist; //minimum number of datapoints that should be present in the dataset so as to initiate a split
	//Mention the serialVersionUID explicitly in order to avoid getting errors while deserializing.
	public static final long serialVersionUID = 343L;
	public DecisionTree(ArrayList<Datum> datalist , int min) {
		minSizeDatalist = min;
		rootDTNode = (new DTNode()).fillDTNode(datalist);
	}

	class DTNode implements Serializable{
		//Mention the serialVersionUID explicitly in order to avoid getting errors while deserializing.
		public static final long serialVersionUID = 438L;
		boolean leaf;
		int label = -1;      // only defined if node is a leaf
		int attribute; // only defined if node is not a leaf
		double threshold;  // only defined if node is not a leaf



		DTNode left, right; //the left and right child of a particular node. (null if leaf)

		DTNode() {
			leaf = true;
			threshold = Double.MAX_VALUE;
		}



		// this method takes in a datalist (ArrayList of type datum) and a minSizeInClassification (int) and returns
		// the calling DTNode object as the root of a decision tree trained using the datapoints present in the
		// datalist variable
		// Also, KEEP IN MIND that the left and right child of the node correspond to "less than" and "greater than or equal to" threshold
		DTNode fillDTNode(ArrayList<Datum> datalist) {

			//YOUR CODE HERE
			boolean sameLabel = true;
			if(datalist.size() > minSizeDatalist ) {
				for(Datum d: datalist) {
					if(!(d.y == datalist.get(0).y)) {
						sameLabel = false;
						break;
					}
				}
				//true
				if(sameLabel) {
					DTNode new_node = new DTNode();
					new_node.label = datalist.get(0).y;
					return new_node;
				}
				//false
				else {
					//we have to find a split 
					//we need the best threshold and the best 
					double[] bestArray = bestSplit(datalist);
					int bestAttribute = (int) bestArray[0];
					double bestThreshold = bestArray[1];
					
					
					DTNode new_Node = new DTNode();
					new_Node.attribute = bestAttribute;
					new_Node.threshold = bestThreshold;
		
					
					ArrayList<Datum> left = new ArrayList<Datum>();
					ArrayList<Datum> right = new ArrayList<Datum>();
					
					for(Datum data : datalist) {
						if(data.x[bestAttribute] < bestThreshold) {
							left.add(data);
						}
						else {
							right.add(data);
						}
					}
					new_Node.left = fillDTNode(left);
					new_Node.right = fillDTNode(right);
					return new_Node;
				}
			}
			else {
				//if dataSet not big enough (k too small), just create a leaf on the spot with the majority of nodes
                DTNode newDTNoder = new DTNode();
                newDTNoder.label = findMajority(datalist);
                return newDTNoder;

			}
		}
		public double[] bestSplit(ArrayList<Datum> datalist) {
			double[] array = new double[2];
			double best_avg_entropy = Double.MAX_VALUE;
			double best_attr = -1;
			double best_threshold = -1;
			for(int i = 0; i< 2; i++) {
				for(int a = 0; a < datalist.size(); a++) {
					//compute split
					ArrayList<Datum> left = new ArrayList<Datum>();
					ArrayList<Datum> right = new ArrayList<Datum>();
					
					for(int b = 0; b <datalist.size(); b++) {
						if(datalist.get(a).x[i] > datalist.get(b).x[i]) {
							left.add(datalist.get(b));
						}
						else {
							right.add(datalist.get(a));
						}
					}
					double left_entropy = calcEntropy(left);
					double right_entropy = calcEntropy(right);
					//H(D,D) = w * H(D) + w *H(D)
					//w = number of datapoints in Di / number of datapoints in D, namely D1 + D2
					double current_avg_entropy = left_entropy * (left.size()/datalist.size()) +
							right_entropy * (right.size()/datalist.size());
					
					if(best_avg_entropy > current_avg_entropy ) {
						best_avg_entropy = current_avg_entropy;
                        best_attr = i;
						best_threshold = datalist.get(a).x[i];


					}
				}
				
			}
			array[0] = best_attr;
			array[1] = best_threshold;
			return array;
			
		}



		//This is a helper method. Given a datalist, this method returns the label that has the most
		// occurences. In case of a tie it returns the label with the smallest value (numerically) involved in the tie.
		int findMajority(ArrayList<Datum> datalist)
		{
			int l = datalist.get(0).x.length;
			int [] votes = new int[l];

			//loop through the data and count the occurrences of datapoints of each label
			for (Datum data : datalist)
			{
				votes[data.y]+=1;
			}
			int max = -1;
			int max_index = -1;
			//find the label with the max occurrences
			for (int i = 0 ; i < l ;i++)
			{
				if (max<votes[i])
				{
					max = votes[i];
					max_index = i;
				}
			}
			return max_index;
		}




		
		int classifyAtNode(double[] xQuery) {
			//once we have a decision tree, you can use it to classify new objects, called the testing phase.
			//We will use the decision tree to choose a label for the object
			//This is done by traversing the decision tree from the root to a leaf
			//YOUR CODE HERE
			
			//xQuery is a datapoint excluding the labels in the forms of an array of type double Datum.x
			//should return corresponding label
			if(this.leaf ) {
				return  this.label;
			}
			else {
				if(xQuery[this.attribute] < this.threshold) {
					return this.left.classifyAtNode(xQuery);
					
				}
				else {
					return this.right.classifyAtNode(xQuery);
				}
			}
		}


		//given another DTNode object, this method checks if the tree rooted at the calling DTNode is equal to the tree rooted
		//at DTNode object passed as the parameter
		public boolean equals(Object dt2)
		{
			//YOUR CODE HERE
			//node - root, left, right 
			//leaf node - the labels should be the same
			//internal node: the thresholds, attributes should be same 
			//traversal (recursion) 
			boolean rootEqual = false;
			boolean leftEqual = false;
			boolean rightEqual = false;
			if(this != null && dt2 != null) {
				if(this.leaf && ((DTNode)dt2).leaf) {
					if(this.label == ((DTNode)dt2).label) {
						return true;
					}
				}
				else { 
					rootEqual = (this.threshold == ((DTNode)dt2).threshold) && (this.attribute == ((DTNode)dt2).attribute);
					leftEqual = this.left.equals(((DTNode)dt2).left);
					rightEqual = this.right.equals(((DTNode)dt2).right);
				}
				return  (rootEqual&&leftEqual&&rightEqual);
			}
			return false;
		

		}
	}



	//Given a dataset, this retuns the entropy of the dataset
	double calcEntropy(ArrayList<Datum> datalist)
	{
		double entropy = 0;
		double px = 0;
		float [] counter= new float[2];
		if (datalist.size()==0)
			return 0;
		double num0 = 0.00000001,num1 = 0.000000001;

		//calculates the number of points belonging to each of the labels
		for (Datum d : datalist)
		{
			counter[d.y]+=1;
		}
		//calculates the entropy using the formula specified in the document
		for (int i = 0 ; i< counter.length ; i++)
		{
			if (counter[i]>0)
			{
				px = counter[i]/datalist.size();
				entropy -= (px*Math.log(px)/Math.log(2));
			}
		}

		return entropy;
	}


	// given a datapoint (without the label) calls the DTNode.classifyAtNode() on the rootnode of the calling DecisionTree object
	int classify(double[] xQuery ) {
		DTNode node = this.rootDTNode;
		return node.classifyAtNode( xQuery );
	}

    // Checks the performance of a DecisionTree on a dataset
    //  This method is provided in case you would like to compare your
    //results with the reference values provided in the PDF in the Data
    //section of the PDF

    String checkPerformance( ArrayList<Datum> datalist)
	{
		DecimalFormat df = new DecimalFormat("0.000");
		float total = datalist.size();
		float count = 0;

		for (int s = 0 ; s < datalist.size() ; s++) {
			double[] x = datalist.get(s).x;
			int result = datalist.get(s).y;
			if (classify(x) != result) {
				count = count + 1;
			}
		}

		return df.format((count/total));
	}


	//Given two DecisionTree objects, this method checks if both the trees are equal by
	//calling onto the DTNode.equals() method
	public static boolean equals(DecisionTree dt1,  DecisionTree dt2)
	{
		boolean flag = true;
		flag = dt1.rootDTNode.equals(dt2.rootDTNode);
		return flag;
	}

}
