package es.bsc.inb.limtox.util;

import java.util.ArrayList;
import java.util.List;

public class ResultSummaryUtil {

	public static List<String> articlesErrors = new ArrayList<String>();
	public static List<String> relevantArticles = new ArrayList<String>();
	public static List<String> nonRelevantArticles = new ArrayList<String>();
	
	public static List<String> getArticlesErrors() {
		return articlesErrors;
	}
	public static void setArticlesErrors(List<String> articlesErrors) {
		ResultSummaryUtil.articlesErrors = articlesErrors;
	}
	public static List<String> getRelevantArticles() {
		return relevantArticles;
	}
	public static void setRelevantArticles(List<String> relevantArticles) {
		ResultSummaryUtil.relevantArticles = relevantArticles;
	}
	public static List<String> getNonRelevantArticles() {
		return nonRelevantArticles;
	}
	public static void setNonRelevantArticles(List<String> nonRelevantArticles) {
		ResultSummaryUtil.nonRelevantArticles = nonRelevantArticles;
	}
	
	public static void addRelevantArticle(String id) {
		relevantArticles.add(id);
	}
	
	public static void addNonRelevantArticle(String id) {
		nonRelevantArticles.add(id);
	}
	
	public static void addArticlesErrors(String id) {
		articlesErrors.add(id);
	}
	
	public static void print() {
		System.out.print("********************************************");
		System.out.print(articlesErrors);
		System.out.print("********************************************");
		System.out.print(relevantArticles);
		System.out.print("********************************************");
		System.out.print(nonRelevantArticles);
	}
}
