package de.app.hskafeteria.newsverwaltung;


import java.util.List;



public interface NewsDao {
	public void createNews(News News);
	public void updateNews(News News);
	public void deleteNews(String id);
	public List<News> getAllNews();
	public void shutdown();
}
