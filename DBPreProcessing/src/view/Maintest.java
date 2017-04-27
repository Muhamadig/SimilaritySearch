package view;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import model.FVHashMap;
import model.Language;
import model.Language.Langs;
import utils.Util;
import controller.ReadFile;
import controller.StanfordStemmer;
import controller.StopWordsFiltering;
import edu.stanford.nlp.ling.CoreAnnotations.WordnetSynAnnotation;
public class Maintest {

	public static void main(String[] args) {
             String wordForm = "capacity";
             //  Get the synsets containing the word form=capicity

            File f=new File("WordNet\\2.1\\dict");
            System.setProperty("wordnet.database.dir", f.toString());
            //setting path for the WordNet Directory

            WordNetDatabase database = WordNetDatabase.getFileInstance();
            Synset[] synsets = database.getSynsets(wordForm);
            //  Display the word forms and definitions for synsets retrieved

            if (synsets.length > 0){
               ArrayList<String> al = new ArrayList<String>();
               // add elements to al, including duplicates
               HashSet hs = new HashSet();
               for (int i = 0; i < synsets.length; i++){
                  String[] wordForms = synsets[i].getWordForms();
                    for (int j = 0; j < wordForms.length; j++)
                    {
                      al.add(wordForms[j]);
                    }


               //removing duplicates
                hs.addAll(al);
                al.clear();
                al.addAll(hs);

               //showing all synsets
               for (int i = 0; i < al.size(); i++) {
                     System.out.println(al.get(i));
               }
            }
       }
       }
       else
       {
        System.err.println("No synsets exist that contain the word form '" + wordForm + "'");
       }

}
