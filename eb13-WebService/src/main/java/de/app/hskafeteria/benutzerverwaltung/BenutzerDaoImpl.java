package de.app.hskafeteria.benutzerverwaltung;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;



@Transactional
public class BenutzerDaoImpl implements BenutzerDao{
	@Autowired
	private HibernateTemplate hibernateTemplate;
	@Autowired
	TransactionTemplate transactionTemplate;

	/**
	 * Insert a new Benutzer into the database.
	 * @param benutzer
	 */
	public void createBenutzer(final Benutzer benutzer) {
		try {
			HibernateCallback<Object> callback = new HibernateCallback<Object>() {
				@Override
				public Object doInHibernate(Session session)
						throws HibernateException, SQLException {
					String email = benutzer.getEmail();
					Benutzer benutzerInDB = getBenutzerByEmail(email);
					if (benutzerInDB == null)
						session.save(benutzer);
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
	 * Delete a detached Benutzer from the database.
	 * @param benutzer
	 */
	@Override
	public void deleteBenutzer(final String id) {
		try {
			HibernateCallback<Object> callback = new HibernateCallback<Object>() {
				@Override
				public Object doInHibernate(Session session)
						throws HibernateException, SQLException {
					Benutzer benutzer = (Benutzer) session.load(Benutzer.class, Integer.parseInt(id));
					session.delete(benutzer);
					return null;
				}
			};
			hibernateTemplate.execute(callback);
		} catch (HibernateException e) {
			handleException(e);
		}
	}

	/**
	 * Find an Benutzer by its primary key.
	 * @param id
	 * @return
	 */
	@Override
	public Benutzer getBenutzerById(final String id) {
		Object result = null;
		try {
			HibernateCallback<Benutzer> callback = new HibernateCallback<Benutzer>() {
				@Override
				public Benutzer doInHibernate(Session session)
						throws HibernateException, SQLException {
					return (Benutzer) session.get(Benutzer.class, Integer.parseInt(id));
				}
			};
			result = hibernateTemplate.execute(callback);
			if (result == null)
				return null;
		} catch (HibernateException e) {
			handleException(e);
		}
		if (result instanceof Benutzer) {
			return (Benutzer) result;
		}
		return null;
	}

	/**
	 * Find an Benutzer by its email.
	 * @param id
	 * @return
	 */
	@Override
	public Benutzer getBenutzerByEmail(final String email) {
		Object result = null;
		try {
			HibernateCallback<Benutzer> callback = new HibernateCallback<Benutzer>() {
				@SuppressWarnings("rawtypes")
				@Override
				public Benutzer doInHibernate(Session session)
						throws HibernateException, SQLException {
					Query query = session.createQuery("from Benutzer where email = :email ");
					query.setParameter("email", email);
					List list = query.list();
					if (list.size() != 0)
						return (Benutzer) list.get(0);
					else return null;
				}
			};
			result = hibernateTemplate.execute(callback);
			if (result == null)
				//				throw new AccountNotFoundException(String.format("Account with Email = %s not found", email));
				return null;
		} catch (HibernateException e) {
			handleException(e);
		}
		if (result instanceof Benutzer) {
			return (Benutzer) result;
		}
		return null;
	}

	/**
	 * Updates the state of a detached Benutzer.
	 *
	 * @param benutzer
	 */
	@Override
	public void updateBenutzer(final Benutzer benutzer) {
		try {
			HibernateCallback<Benutzer> callback = new HibernateCallback<Benutzer>() {
				@Override
				public Benutzer doInHibernate(Session session)
						throws HibernateException, SQLException {
					session.update(benutzer);
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
	 * Finds all Benutzers in the database.
	 * @return
	 */
	@SuppressWarnings({ "unchecked" })
	@Override
	public Benutzers getAllBenutzers() {
		ArrayList<Benutzer> benutzerList = null;
		try {
			HibernateCallback<List<Benutzer>> callback = new HibernateCallback<List<Benutzer>>() {
				@Override
				public List<Benutzer> doInHibernate(Session session)
						throws HibernateException, SQLException {
					//					Query query = session.createQuery("FROM Benutzer b LEFT JOIN b.angelegte_taetigkeiten LEFTJOIN b.angenommene_taetigkeiten");
					Query query = session.createQuery("from Benutzer");
					return query.list();
				}
			};
			benutzerList = (ArrayList<Benutzer>) hibernateTemplate.execute(callback);
		} catch (Exception e) {
			handleException(e);
		}
		return new Benutzers(benutzerList);
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

	protected void handleException(Exception e) {
		//		throw new DatabaseException("Database connection corrupted, have you turned on VPN?");
		e.printStackTrace();
	}

	@Override
	public void updatePassword(final String email, final String newPassword) {
		try {
			HibernateCallback<List<Benutzer>> callback = new HibernateCallback<List<Benutzer>>() {
				@Override
				public List<Benutzer> doInHibernate(Session session)
						throws HibernateException, SQLException {
					Query queryBenutzer = session.createQuery
							("FROM Benutzer b "+
									"where b.email = :email");
					queryBenutzer.setParameter("email", email);
					Benutzer benutzer = (Benutzer) queryBenutzer.list().get(0);
					benutzer.setPasswort(newPassword);
					session.update(benutzer);
					return null;
				}
			};
			hibernateTemplate.execute(callback);
		} catch (Exception e) {
			handleException(e);
		}
		shutdown();
	}
}
