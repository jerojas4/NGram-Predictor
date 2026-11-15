// Name: Jayson Rojas
// GithubID: jerojas4
// Version P2.2
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;
/** i predict the future with this class
 */
public class Predictor{
	/**
	 * my static main
	 * this is where the program magic is put together :)
	 */
	public static void main(String[]args){
		if(args.length==0){
			System.out.println("Please enter a file path in the argument.");
			return;
		}


		File folder = new File(args[0]);

		if(!folder.exists()||!folder.isDirectory()){ //read folders section
			System.out.println("Invalid folder path.....");
			return;
		}
		int fileC = 0;
		int totalTok = 0; //check tokens and file count initialize
		NGramModel mod = new NGramModel();
		try{
			File[]files = folder.listFiles(); //create an array of files based on folder
			
			if(files==null){
				System.out.println("CAN'T read folder stuff..");
				return;
			}
			
			for(File file : files){ //for the files in file array
				if(file.isFile()&&file.getName().toLowerCase().endsWith(".txt")){ //if ends with .txt to make sure it's a text
					String txt = TextProcessor.readFile(file.getPath()); //read the file
					List<String>tokens = TextProcessor.tokenize(txt);  //toeknize the text from file 
					List<List<String>>sentences = TextProcessor.splitSentences(tokens); //make sentences of tokens 
					mod.train(sentences); //train it and create bigram/trigrams 
					fileC +=1; //add counter 
					totalTok+=tokens.size(); //add token size counter
				}
				
			}
			if(args.length==2){
				File testFile = new File(args[1]);
				if(testFile.exists()&&testFile.isFile()){
					String txt1 = TextProcessor.readFile(testFile.getPath());
					List<String> tokens1 = TextProcessor.tokenize(txt1);
					List<List<String>> sent = TextProcessor.splitSentences(tokens1);
					int totalPred = 0;
					int correctTri = 0;
					int correctBi = 0;

					for(List<String> sentence : sent){
						for(int i = 1;i<sentence.size();i++){ //start at 1 to skip <s>
							totalPred++;
							String actualWord = sentence.get(i);
							List<String>context = sentence.subList(0, i);
							String triGramPredict = mod.predictNextWord(context);
							String biGramPredict = mod.predictBigramOnly(context);
							if(actualWord.equalsIgnoreCase(triGramPredict)){
								correctTri++;
							}
							if(actualWord.equalsIgnoreCase(biGramPredict)){
								correctBi++;
							}
						}
					}
					double biAccuracy= 0.0;
					double triAccuracy = 0.0;
					if(totalPred>0){
						biAccuracy = ((double)correctBi/totalPred)*100;
						triAccuracy=((double)correctTri/totalPred)*100;
					}
					System.out.println("Evaluating on: "+ args[1]);
					System.out.println("Predictions made: " +totalPred);
					System.out.printf("Accuracy (trigram with fallback): %.1f%%%n" ,triAccuracy);
					System.out.printf("Accuracy (bigram only): %.1f%%%n", biAccuracy);
								
				}
				else{
					System.out.println("Error..... file does NOT exist.");
				}
			}


			
		}
		catch(FileNotFoundException e){
			System.out.println("Error reading the file.. " + e.getMessage());
		}



		if(fileC<5){
			System.out.println("WARNING!! less than 5 txt files detected");
		}
		System.out.println("Files read: " + fileC);
		System.out.println("Total tokens is: " + totalTok); //display counters first
		

		Scanner scnr = new Scanner(System.in);
		String choice = "";
		while(!choice.equalsIgnoreCase("quit")){
			System.out.println("Enter a text to try to predict.. ('quit' to exit): "); 
			choice = scnr.nextLine();
			if(choice.equalsIgnoreCase("quit")){
				break;
			}
			String predictor = mod.predictNextWord(choice); //predict the net word that we're trying to find
			if(predictor==null){
				System.out.println("No prediction available right now..");
			}
			else{
				System.out.println("Predicted word: "+predictor);
			}
		}
		System.out.println("Thank you for using my program! :) ");
		scnr.close();
	}
}