package de.app.hskafeteria.angeboteverwaltung;


import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;

import de.app.hskafeteria.exception.DatabaseException;



public class AngebotDaoImpl implements AngebotDao{
	@Autowired
	private HibernateTemplate hibernateTemplate;

	/**
	 * Insert a new Angebot into the database.
	 * @param Angebot
	 */
	@Override
	public void createAngebot(final Angebot angebot) {
		try {
			HibernateCallback<Object> callback = new HibernateCallback<Object>() {
				@Override
				public Object doInHibernate(Session session)
						throws HibernateException, SQLException {
					String titel = angebot.getTitel();
					Angebot angebotInDB = getAngebotByTitel(titel);
					if (angebotInDB == null)
						session.save(angebot);
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
	 * Find an Angebot by its title.
	 * @param id
	 * @return
	 */
	@Override
	public Angebot getAngebotByTitel(final String titel) {
		Object result = null;
		try {
			HibernateCallback<Angebot> callback = new HibernateCallback<Angebot>() {
				@SuppressWarnings("rawtypes")
				@Override
				public Angebot doInHibernate(Session session)
						throws HibernateException, SQLException {
					Query query = session.createQuery("from Angebot where titel = :titel ");
					query.setParameter("titel", titel);
					List list = query.list();
					if (list.size() != 0)
						return (Angebot) list.get(0);
					else return null;
				}
			};
			result = hibernateTemplate.execute(callback);
			if (result == null)
				return null;
		} catch (HibernateException e) {
			handleException(e);
		}
		if (result instanceof Angebot) {
			return (Angebot) result;
		}
		return null;
	}

	/**
	 * Updates a Angebot.
	 *
	 * @param Angebot
	 */
	@Override
	public void updateAngebot(final Angebot angebot) {
		try {
			HibernateCallback<Angebot> callback = new HibernateCallback<Angebot>() {
				@Override
				public Angebot doInHibernate(Session session)
						throws HibernateException, SQLException {
					session.update(angebot);
					return null;
				}
			};
			hibernateTemplate.execute(callback);
		} catch (HibernateException e) {
			handleException(e);
		}
	}

	/**
	 * Delete an Angebot from the database.
	 * @param id
	 */

	@Override
	public void deleteAngebot(final String id) {
		try {
			HibernateCallback<Object> callback = new HibernateCallback<Object>() {
				@Override
				public Object doInHibernate(Session session)
						throws HibernateException, SQLException {
					Angebot angebot = (Angebot) session.load(Angebot.class, Integer.parseInt(id));
					session.delete(angebot);
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
	 * Finds all Angebote in the database.
	 * @return
	 */  

	@Override
	public List<Angebot> getAllAngebote() {
		List<Angebot> result = null;
		try {
			HibernateCallback<List<Angebot>> callback = new HibernateCallback<List<Angebot>>() {
				@SuppressWarnings("unchecked")
				@Override
				public List<Angebot> doInHibernate(Session session)
						throws HibernateException, SQLException {
					Query query = session.createQuery
							("FROM Angebot");

					return query.list();
				}
			};
			result = (List<Angebot>) hibernateTemplate.execute(callback);
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
