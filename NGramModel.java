// Name: Jayson Rojas
// GithubID: jerojas4
// Version P2.2
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;
/**
 * creates and trains bigram/trigram models
 * uses hashmaps to store
 * predicts most likely word based on patterns
 */
public class NGramModel{
	private HashMap<String, List<String>> bigramMap, trigramMap;
	public NGramModel(){
		bigramMap = new HashMap<>();
		trigramMap = new HashMap<>();
	}
	/**
	 * builds ngrams from sentence and stores in a hashmap
	 * contextlength determines how many tokens make up the key
	 * @param sentence the list of tokens in a sentence
	 * @param contextLength the number of tokens used as athe key
	 * @param map the hashmap where ngram is stored
	 */
	private void buildNGram(List<String> sentence, int contextLength, HashMap<String, List<String>> map){
		for(int i = 0;i<sentence.size()-contextLength;i++){//subtracts contLength so theres always a next word
			StringBuilder key = new StringBuilder();
			for(int j = 0;j<contextLength;j++){ //inner loop checks how many tokens go into the key, 2 = 2 token key
				
				if(j>0){ //add spaces between toke but not before first token
					key.append(" ");
				}
				key.append(sentence.get(i+j));//i = starting inex, j = offset of that index
				//for ex.) i = 1, j = 1 means we want the token at index 2
			}
			String keyStr = key.toString();
			String nextWord = sentence.get(i + contextLength);

			if(map.containsKey(keyStr)){
				map.get(keyStr).add(nextWord);
			}
			else{
				List<String>words = new ArrayList<>();
				words.add(nextWord);
				map.put(keyStr,words);
			}
		}
	}
	/**
	 * trains bigram/trigram models using multiple sentences
	 * each sentence processed and prevent ngrams from crossing boundaries
	 * @param sentences where we get the list of tokenizeed sentences
	 */
	public void train(List<List<String>> sentences){
		for(List<String> sentence : sentences){
			buildNGram(sentence, 1, bigramMap); //1 predicted token
			buildNGram(sentence, 2, trigramMap); //2 context tokens + 1 predicted token
		}
	}
	/**
	 * This is my regular predictnextword method but with string input
	 * I removed the original trigram/bigram code 
	 * gets the input of string 
	 * calls its overloaded method
	 * @param List<String> context
	 */
	public String predictNextWord(String inp){
		List<String>tokens = TextProcessor.tokenize(inp); //tokenize
		int start = 0;//tracks where sentence begins
		for(int i = 0;i<tokens.size();i++){ //looks for last sentence ending punctuation
			if(tokens.get(i).equals(".")||tokens.get(i).equals("!")||tokens.get(i).equals("?")){
				start = i+1; //updates repeatedly giving last sentence boundary

			}
		}
		//kee tokens after last punctuation mark
		List<String> cont = new ArrayList<>(tokens.subList(start, tokens.size()));

		if(cont.isEmpty()){
			cont.add("<s>");
		}
		
		return predictNextWord(cont);
	}
	/**
	 * This is my overloaded predictnextword method but with a list of context
	 * logic has the trigram with bigram fallback
	 * this is used to get the most frequent words and then later
	 * called inside of predictnextowrd with string input
	 * @param List<String> context
	 */
	public String predictNextWord(List<String>context){
		if(context.size()>=2){ //TRIGRAM
			String triKey =   context.get(context.size()-2) + " " + context.get(context.size()-1); //get the last two in original order
			if(trigramMap.containsKey(triKey)){
				List<String>possible = trigramMap.get(triKey);
				return mostFrequent(possible);
			}

		}
		if(context.size()>=1){ //BIGRAM
			String biKey = context.get(context.size()-1);
			if(bigramMap.containsKey(biKey)){
				List<String>poss = bigramMap.get(biKey);
				return mostFrequent(poss);
			}
		}
		return null;
	}
	/**
	 * This is my predicting bigram only method
	 * we are only using bigram
	 * no trigram
	 * @param List<String> context
	 */
	public String predictBigramOnly(List<String>context){
		if(!context.isEmpty()){
			String biKey = context.get(context.size()-1);
			if(bigramMap.containsKey(biKey)){
				return mostFrequent(bigramMap.get(biKey));
			}

		}
		return null;
	}
	/**
	 * finds most feq occuring word in list
	 * if two have the same freq, the word comes first alphabetically wins
	 * @param words the list of possible next words
	 * @return the most freq word, or null if empty
	 */
	private String mostFrequent(List<String> words){
		HashMap<String, Integer> counts = new HashMap<>();
		for(String s : words){
			if(counts.containsKey(s)){
				counts.put(s, counts.get(s) + 1);
			}
			else{
				counts.put(s, 1);
			}
		}
		String bestW = null;
		int maxC = 0;
		for(String word:counts.keySet()){
			int count = counts.get(word);
			if(count>maxC|| (count==maxC && word.compareTo(bestW)<0)){
				maxC = count;
				bestW = word;
			}
		}
		return bestW;
	}




}