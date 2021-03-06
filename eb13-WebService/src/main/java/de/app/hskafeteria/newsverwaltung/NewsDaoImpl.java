package de.app.hskafeteria.newsverwaltung;


import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;

import de.app.hskafeteria.exception.DatabaseException;



public class NewsDaoImpl implements NewsDao{
	@Autowired
	private HibernateTemplate hibernateTemplate;

	/**
	 * Insert a new News into the database.
	 * @param News
	 */
	@Override
	public void createNews(final News news) {
		try {
			HibernateCallback<Object> callback = new HibernateCallback<Object>() {
				@Override
				public Object doInHibernate(Session session)
						throws HibernateException, SQLException {
					String titel = news.getTitel();
					News newsInDB = getNewsByTitel(titel);
					if (newsInDB == null)
						session.save(news);
					return null;
				}
			};
			hibernateTemplate.execute(callback);
		} catch (HibernateException e) {
			handleException(e);
		}
		shutdown();
	}
	
	/**
	 * Find an News by its title.
	 * @param id
	 * @return
	 */
	@Override
	public News getNewsByTitel(final String titel) {
		Object result = null;
		try {
			HibernateCallback<News> callback = new HibernateCallback<News>() {
				@SuppressWarnings("rawtypes")
				@Override
				public News doInHibernate(Session session)
						throws HibernateException, SQLException {
					Query query = session.createQuery("from News where titel = :titel ");
					query.setParameter("titel", titel);
					List list = query.list();
					if (list.size() != 0)
						return (News) list.get(0);
					else return null;
				}
			};
			result = hibernateTemplate.execute(callback);
			if (result == null)
				return null;
		} catch (HibernateException e) {
			handleException(e);
		}
		if (result instanceof News) {
			return (News) result;
		}
		return null;
	}

	/**
	 * Updates a News.
	 *
	 * @param News
	 */
	@Override
	public void updateNews(final News news) {
		try {
			HibernateCallback<News> callback = new HibernateCallback<News>() {
				@Override
				public News doInHibernate(Session session)
						throws HibernateException, SQLException {
					session.update(news);
					return null;
				}
			};
			hibernateTemplate.execute(callback);
		} catch (HibernateException e) {
			handleException(e);
		}
	}

	/**
	 * Delete a News from the database.
	 * @param id
	 */

	@Override
	public void deleteNews(final String id) {
		try {
			HibernateCallback<Object> callback = new HibernateCallback<Object>() {
				@Override
				public Object doInHibernate(Session session)
						throws HibernateException, SQLException {
					News news = (News) session.load(News.class, Integer.parseInt(id));
					session.delete(news);
					return null;
				}
			};
			hibernateTemplate.execute(callback);
		} catch (HibernateException e) {
			handleException(e);
		}
		shutdown();
	}

	protected void handleException(Exception e) {
		throw new DatabaseException("Database connection corrupted, have you turned on VPN?");
	}

	/**
	 * Finds all News in the database.
	 * @return
	 */  

	@Override
	public List<News> getAllNews() {
		List<News> result = null;
		try {
			HibernateCallback<List<News>> callback = new HibernateCallback<List<News>>() {
				@SuppressWarnings("unchecked")
				@Override
				public List<News> doInHibernate(Session session)
						throws HibernateException, SQLException {
					Query query = session.createQuery
							("FROM News ORDER BY Datum DESC");

					return query.list();
				}
			};
			result = (List<News>) hibernateTemplate.execute(callback);
		} catch (Exception e) {
			handleException(e);
		}
		return result;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void shutdown() {
		try {
			HibernateCallback callback = new HibernateCallback() {
				@Override
				public Object doInHibernate(Session session) throws HibernateException, SQLException {
					session.createQuery("SHUTDOWN").executeUpdate();
					return null;
				}
			};
			hibernateTemplate.execute(callback);
		} catch (Exception e) {
		}
	}
}
