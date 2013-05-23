package de.app.hskafeteria.httpclient.domain;

import java.util.ArrayList;
import java.util.List;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Root(name="newslist")
public class NewsList {
	@ElementList(inline=true)
	private ArrayList<News> newsList;
	
	public NewsList() {
		newsList = new ArrayList<News>();
	}
	
	public NewsList(ArrayList<News> newsList) {
		this.newsList = newsList;
	}
	
	public boolean isEmpty() {
		if (newsList.size() == 0)
			return true;
		else
			return false;
	}

	public int size() {
		return newsList.size();
	}

	public void addNews(News news) {
		newsList.add(news);
	}
	
	public void addNewsList(List<News> newsList) {
		newsList.addAll(newsList);
	}

	public ArrayList<News> getNewsList() {
		return newsList;
	}

	public void setNewsList(ArrayList<News> newsList) {
		this.newsList = newsList;
	}
}
