package de.app.hskafeteria.aktionenverwaltung;


import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;

import de.app.hskafeteria.exception.DatabaseException;



public class AktionDaoImpl implements AktionDao{
	@Autowired
	private HibernateTemplate hibernateTemplate;

	/**
	 * Insert a new Aktion into the database.
	 * @param Aktion
	 */
	@Override
	public void createAktion(final Aktion aktion) {

	}

	/**
	 * Updates a Aktion.
	 *
	 * @param Aktion
	 */
	@Override
	public void updateAktion(final Aktion aktion) {
		try {
			HibernateCallback<Aktion> callback = new HibernateCallback<Aktion>() {
				@Override
				public Aktion doInHibernate(Session session)
						throws HibernateException, SQLException {
					session.update(aktion);
					return null;
				}
			};
			hibernateTemplate.execute(callback);
		} catch (HibernateException e) {
			handleException(e);
		}
	}

	/**
	 * Delete a Aktion from the database.
	 * @param id
	 */

	@Override
	public void deleteAktion(final String id) {
		try {
			HibernateCallback<Object> callback = new HibernateCallback<Object>() {
				@Override
				public Object doInHibernate(Session session)
						throws HibernateException, SQLException {
					Aktion aktion = (Aktion) session.load(Aktion.class, Integer.parseInt(id));
					session.delete(aktion);
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
	 * Finds all Aktion in the database.
	 * @return
	 */  

	@Override
	public List<Aktion> getAllAktionen() {
		List<Aktion> result = null;
		try {
			HibernateCallback<List<Aktion>> callback = new HibernateCallback<List<Aktion>>() {
				@SuppressWarnings("unchecked")
				@Override
				public List<Aktion> doInHibernate(Session session)
						throws HibernateException, SQLException {
					Query query = session.createQuery
							("FROM Aktion");

					return query.list();
				}
			};
			result = (List<Aktion>) hibernateTemplate.execute(callback);
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
