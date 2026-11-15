// The method below needs these imports 
// Name: Jayson Rojas
// GithubID: jerojas4
// Version P2.1
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class TextProcessor{

/**
 * basically reading the file
 * making it into one big string with \n
 */
public static String readFile(String fileP) throws FileNotFoundException{
    File file = new File(fileP);
    Scanner scnr = new Scanner(file);
    StringBuilder text = new StringBuilder();
    while(scnr.hasNextLine()){
        text.append(scnr.nextLine()+"\n");

    }
    scnr.close();
    return text.toString();

}
    /**
 * Splits text into lowercase words and sentence-ending punctuation.
 *
 * @param text the raw text to tokenize
 * @return the tokens in order
 */
public static List<String> tokenize(String text) {
    // Lowercase, and replace curly apostrophes (’) with straight ones (')
    text = text.toLowerCase().replace('\u2019', '\'');

    List<String> tokens = new ArrayList<>();
    Matcher matcher = Pattern.compile("[a-z]+(?:'[a-z]+)*|[.!?]").matcher(text);
    while (matcher.find()) {
        tokens.add(matcher.group());
    }
    return tokens; // store returned list of tokens into an ArrayList 
}
/**
 * splits a list of tokens to individual senntences
 * a sentence basically ends with ., ?, or !
 * marks '<s>', empty sentences are ignored
 * if texts ends without sentence ending punctuation then the remaining tokens are treated as complete sentence
 */
public static List<List<String>> splitSentences(List<String> tokens){



    List<List<String>> sent = new ArrayList<>();
    List<String> curr = new ArrayList<>();
    for(String tok : tokens){
        if(curr.isEmpty()&&(tok.equals(".")||tok.equals("!")||tok.equals("?"))){ //ignores punctuation if no words in sentence
            continue;
        }
        if(curr.isEmpty()){ //starts new sentence adds <s> marker
            curr.add("<s>");
        }

        curr.add(tok); //add THIS current token to sentence 
        if(tok.equals(".")||tok.equals("?")||tok.equals("!")){ //check if token marks end of a sentence
            sent.add(curr); //svae
            curr = new ArrayList<>(); //new empty list for next sentence
        }
    }
    if(!curr.isEmpty()){
        sent.add(curr);
    }
    return sent;
}


}
