package model;

import model.Language.Langs;

public class LangFactory {
	public static Language getLang(String lang){
		switch(lang){
		case "English": return new Language(Langs.ENGLISH);
		case "Spanish": return new Language(Langs.SPANISH);
		}
		return null;
	}
}
