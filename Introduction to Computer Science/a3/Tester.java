import java.util.ArrayList;


public class Tester {


    public static String localDir = System.getProperty("user.dir");
    public static String base = localDir+"/src/"; //You might need to change these based on your operating system. (Unless you are working on Ubuntu 16.04)
    public static String basedb = localDir+"/src/db/";  //You might need to change these based on your operating system. (Unless you are working on Ubuntu 16.04)


    public static void testequals(boolean verbose)
    {
        //Test 1
        boolean pass = true;
        String filename1 = "data_high_overlap/thresh4.ser";
        String filename2 = "data_high_overlap/thresh1.ser";
        DecisionTree dt1 = DataReader.readSerializedTree(base+filename1);
        DecisionTree dt2 = DataReader.readSerializedTree(base+filename2);
        if (DecisionTree.equals(dt1,dt2)==false) {
            if (verbose)
                System.out.println("Passed test 1.");
        }
        else {
            if (verbose)
                System.out.println("Failed test 1.");
            pass = false;}

        String filename12 = "data_partial_overlap/thresh1.ser";
        String filename22 = "data_partial_overlap/thresh1.ser";
        DecisionTree dt12 = DataReader.readSerializedTree(base+filename12);
        DecisionTree dt22 = DataReader.readSerializedTree(base+filename22);
        if (DecisionTree.equals(dt12,dt22)==true){
            if (verbose)
                System.out.println("Passed test 2.");}
        else{
            if(verbose)
                System.out.println("Failed test 2.");
            pass = false;}

        String filename13 = "data_minimal_overlap/thresh1.ser";
        String filename23 = "data_minimal_overlap/thresh1.ser";
        DecisionTree dt13 = DataReader.readSerializedTree(base+filename13);
        DecisionTree dt23 = DataReader.readSerializedTree(base+filename23);
        if (DecisionTree.equals(dt13,dt23)==true) {
            if (verbose)
                System.out.println("Passed test 3.");
        }
        else{

            if (verbose)
                System.out.println("Failed test 3.");
            pass = false;}
        String filename14 = "data_high_overlap/thresh1.ser";
        String filename24 = "data_high_overlap/thresh1.ser";
        DecisionTree dt14 = DataReader.readSerializedTree(base+filename14);
        DecisionTree dt24 = DataReader.readSerializedTree(base+filename24);
        if (DecisionTree.equals(dt14,dt24)==true) {
            if (verbose)
                System.out.println("Passed test 4.");
        }
        else{
            if (verbose)
                System.out.println("Failed test 4.");
            pass = false;}
        String filename15 = "data_minimal_overlap/thresh8.ser";
        String filename25 = "data_minimal_overlap/thresh1.ser";
        DecisionTree dt15 = DataReader.readSerializedTree(base+filename15);
        DecisionTree dt25 = DataReader.readSerializedTree(base+filename25);
        if (DecisionTree.equals(dt15,dt25)==false){
            if (verbose)
                System.out.println("Passed test 5.");}
        else{
            if (verbose)
                System.out.println("Failed test 5.");
            pass = false;
        }
        String filename16 = "data_high_overlap/thresh2.ser";
        String filename26 = "data_high_overlap/thresh2.ser";
        DecisionTree dt16 = DataReader.readSerializedTree(base+filename16);
        DecisionTree dt26 = DataReader.readSerializedTree(base+filename26);
        if (DecisionTree.equals(dt16,dt26)==true)
        {
            if (verbose)
                System.out.println("Passed test 6.");}
        else{
            if (verbose)
                System.out.println("Failed test 6.");
            pass = false;}
            String result = "";
         if (pass)
            result = "Passed.";
         else
             result = "Failed.";
         System.out.println("Test result for equals() : "+ result);
    }



    //if you want a more detailed test result change verbose to true

    public static void testDecisionTree(boolean verbose)
    {
        // array of names of the datasets
        String datasets[] = {"data_high_overlap","data_partial_overlap","data_minimal_overlap"};
        //String base = localDir +"/src/";
        //String dbbase = base+"db/";
        boolean testpass = true;
        int counter = 0;
        int total = 0;
        for (int i=0;i<datasets.length;i++)
        {
            //load the datasets
            DataReader dr = new DataReader();
            try {
                dr.read_data(basedb + datasets[i] + ".csv");
            }catch (Exception e)
            {
                e.printStackTrace();
            }
            //split the dataset into train and test set
            //**** DO NOT MAKE ANY CHANGES HERE *****//
            dr.splitTrainTestData(.5);
            for (int j = 1 ; j < dr.trainData.size() ; j = j*2){
                String filename = base+datasets[i]+"/thresh"+j+".ser";
                ArrayList<Datum> trainingData = new ArrayList<Datum>();

                try{
                    total++;
                    //create a decision tree based on the dataset
                    DecisionTree dt = new DecisionTree(dr.trainData , j);
                        //read corresponding serialized tree
                        DecisionTree serdt = DataReader.readSerializedTree(filename);

                        //compare the two trees
                        testpass = DecisionTree.equals(serdt,dt);
                    if (testpass)
                    {
                        if (verbose)
                            System.out.println(datasets[i] + " - thresh" +j+ "  - Result : "+"Passed");
                    }
                    else
                    {
                        if (verbose)
                            System.out.println(datasets[i] + " - thresh" +j+ "  - Result : "+"Failed");
                        counter++;
                    }
                }catch(Exception e)
                {
                    e.printStackTrace();
                }

            }

        }
        System.out.println("Number of tests passed :" + (total-counter) + " out of "+total+" tests." );
    }
     //if you want a more detailed test result change verbose to true
    public static void testClassify(boolean verbose)
    {
        int counter=0;
        int total = 0;
        DataReader dr = new DataReader();
        DecisionTree dt = null;
        try
        {
            //You can change the value of the database in the line below and try for different databases.
            String database = "data_minimal_overlap";
            //DataReader dr = new DataReader();
            dr.read_data(basedb+database+".csv");
            //*** DO NOT MAKE ANY CHANGES HERE ***//
            dr.splitTrainTestData(.5);


            dt = DataReader.readSerializedTree(base+database+"/thresh1.ser");

        }catch (Exception e)
        {
            e.printStackTrace();
        }
        //Go through the
        for (int i = 1 ; i < dr.trainData.size() ; i++)
        {
            //classify and compare with the ground truth
            if (verbose)
                System.out.println( "Ground Truth : " + dr.trainData.get(i).y+". Your classification :" + dt.classify(dr.trainData.get(i).x));
            if (dr.trainData.get(i).y==dt.classify(dr.trainData.get(i).x))
            {
                counter++;
            }
            total++;
        }
        System.out.println("Number of correct outputs : " + counter + " out of " + total);
    }

    public static void main(String args[])
    {

        //Testing the equals function
        testequals(true);

        //if you want a more detailed test result change verbose to 'true'
        testDecisionTree(false);
        testClassify(false);
    }

}
