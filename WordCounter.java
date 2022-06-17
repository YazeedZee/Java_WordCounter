/*
 * Author: Yazeed Mohammed Alzughaibi
 * ID: 1847186
 * Class Description: this class will take an input .txt file, count the number of words in it, produce a hashmap that holds every word and
 * how many times it occurred and sort it ascending or descending, write the output in an output text file named by the user.
 */

import java.io.FileReader; //Import this class to read the input file
import java.io.FileNotFoundException; //Import this class to handle errors
import java.io.BufferedReader; //Import this class to read the lines coming form FileReader
import java.io.FileWriter; //Import this class to write the output on a text file
import java.nio.file.Path; //Import this class to access the path of the input file
import java.nio.file.Paths; //Import this class to access the path of the input file
import java.util.*; //Import this class in case any util is needed
import java.util.Map.Entry; //Import this class to be able to get a collection view of a map
import java.lang.*; //Import this class to use wrapper classes such as Integer

public class WordCounter
{
	public static boolean Order; //boolean variable initiated here for visibility
	//public static String ordering; //String variable initiated here for visibility
	public static void main(String[]args) throws Exception
	{
		try
		{
			//use the Path class to receive the input file
			Path path = Paths.get(System.getProperty("user.dir")).resolve(args[0]);

			//initiate output file and object for FileWriter
			FileWriter MyWriter = new FileWriter(args[1]);

			//ordering is input from the user as "asc" or "des"
			String ordering = args[2];

			//initiate BufferedReader to read through the input file
			BufferedReader reader = new BufferedReader(new FileReader(path.toFile()));

			//initialize a HashMap to hold the words and their number of ocurences
			Map<String, Integer> WordCount = new HashMap<>();

			//hold the first line in the input file
			String line = reader.readLine();

			 while (line != null) //to stop the loop when the file has ended
		     {
		    	  if(!line.trim().equals("")) //to exclude empty lines
		    	  {
		    		  //cleaning the words from unneeded punctuation and symbols
		    		  String[] words = line.split("\\W|\\_"); //String array to hold the line word by word

		    		  for(String word:words) //iterate through the array
		    		  {
		    			  if(word == null || word.trim().equals("") || word == " ") //skip empty words and such
		    			  {
		    				  continue;
		    			  }

		    			  String NewWord = word.toLowerCase(); //making all words lower case after they were processed

		    			  //start filling the hashmap and incrementing the counters
		    			  if(WordCount.containsKey(NewWord))
		    			  {
		    				  WordCount.put(NewWord, WordCount.get(NewWord) + 1);
		    			  }
		    			  else
		    			  {
		    				  WordCount.put(NewWord, 1);
		    			  }
		    		  }
		    	  }
		    	  //at the end of the loop read the next line for the next loop
		    	  line = reader.readLine();
		    	  //loop only ends when there is no next line
		     }

			 //make a String set for incrementing through the hashmap and count the number of words
			 Set<String> keys = WordCount.keySet();
		   	 int sum = 0;
		   	 for(String key : keys)
		   	 {
		   		 sum += WordCount.get(key);
		   	 }

		   	 //check the type of ordering asked by user
			 if (args[2].equals("asc") == true) //ascending
				 Order = true;
			 else if (args[2].equals("des") == true) //descending
				 Order = false;

			 //print number of words in the output file
			 MyWriter.write("Number of Words in file " + args[0] +" is: " + sum + " words. \n");

			 //start sorting here
			 //first convert the HashMap to a list
			 List<Entry<String, Integer>> list = new LinkedList<Entry<String, Integer>>(WordCount.entrySet());

			 //use collections class to sort
			 Collections.sort(list, new Comparator<Entry<String, Integer>>()
			 {
					public int compare(Entry<String, Integer> o1, Entry<String, Integer> o2)
					{
						//since we can't simply compare two words because that won't work
						//compare two object and return an integer
						if (Order)
						{
							return o1.getValue().compareTo(o2.getValue());
						}
						else
						{
							return o2.getValue().compareTo(o1.getValue());
						}
					}
			 });

//			 if (args[2].equals("asc") == true) //ascending
//				 MyWriter.write("here is the word occurrences is ascending order:\n");
//			 else if (args[2].equals("des") == false) //descending
//				 MyWriter.write("here is the word occurrences is descending order:\n");


			 //start the process of printing the sorted map/list
			 Map<String, Integer> sortedMap = new LinkedHashMap<String, Integer>();

			 //MyWriter.write("word\t:\toccurrences\n");
		     for (Entry<String, Integer> entry : list)
			 {
		    	 sortedMap.put(entry.getKey(), entry.getValue());
			 }

		     int x = 1;
		     for(Entry<String, Integer> entry : sortedMap.entrySet())
		     {
		    	 MyWriter.write(x + " The word ("+entry.getKey() + ") was mentioned ("
		    			 + entry.getValue() + ") times.\n");
		    	 x += 1;
		     }

		   	//print for debugging
		    System.out.println("Output file has been generated with the name: " + args[1]);

		    MyWriter.close();
		 }
		 //catch exception if file is not found
		 catch (FileNotFoundException e)
		 {
			 System.out.println("file was not found.");
			 e.printStackTrace();
		 }
	}

}
