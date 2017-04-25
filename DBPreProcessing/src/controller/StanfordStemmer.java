package controller;

import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import model.FVHashMap;
import edu.stanford.nlp.ling.CoreAnnotations.LemmaAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;

public class StanfordStemmer {

    protected StanfordCoreNLP pipeline;

    public StanfordStemmer() {
        // Create StanfordCoreNLP object properties, with POS tagging
        // (required for lemmatization), and lemmatization
        Properties props;
        props = new Properties();
        props.put("annotators", "tokenize, ssplit, pos, lemma");

        // StanfordCoreNLP loads a lot of models, so you probably
        // only want to do this once per execution
        this.pipeline = new StanfordCoreNLP(props);
    }

    public FVHashMap lemmatize(String documentText)
    {
    	
    	
        List<String> lemmas = new LinkedList<String>();
        
        // create an empty Annotation just with the given text
        Annotation document = new Annotation(documentText);

        // run all Annotators on this text
        this.pipeline.annotate(document);
        
        // Iterate over all of the sentences found
        List<CoreMap> sentences = document.get(SentencesAnnotation.class);
        for(CoreMap sentence: sentences) {
            // Iterate over all tokens in a sentence
            for (CoreLabel token: sentence.get(TokensAnnotation.class)) {
                // Retrieve and add the lemma for each word into the list of lemmas
                lemmas.add(token.get(LemmaAnnotation.class));
            }
        }
        
        FVHashMap fv= new FVHashMap();
        for(String key:lemmas){
        	fv.put(key, 1);
        }
        return fv;
    }
    
    public FVHashMap lemmatize(FVHashMap fv)
    {
    	FVHashMap newfv = new FVHashMap();
    	for(String key:fv.keySet())
    	{
    		int currvalue = fv.get(key);
    		String stem = Stemming(key);
    		newfv.put(stem, currvalue);
    		 
    	}
    	return newfv;
    }
    
    // This function stem single word and return it's stem
    public String Stemming(String documentText)
    {
        // create an empty Annotation just with the given text
        Annotation document = new Annotation(documentText);

        // run all Annotators on this text
        this.pipeline.annotate(document);
        
        // Iterate over all of the sentences found
        CoreMap word = document.get(SentencesAnnotation.class).get(0);
        CoreLabel token= word.get(TokensAnnotation.class).get(0);
                // Retrieve and add the lemma for each word into the list of lemmas
          return token.get(LemmaAnnotation.class);
            
        }
}

