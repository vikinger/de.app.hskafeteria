package de.app.hskafeteria.bewertungsverwaltung;


import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.HibernateTemplate;

import de.app.hskafeteria.angeboteverwaltung.Angebot;
import de.app.hskafeteria.angeboteverwaltung.AngebotDao;
import de.app.hskafeteria.benutzerverwaltung.Benutzer;
import de.app.hskafeteria.benutzerverwaltung.BenutzerDao;
import de.app.hskafeteria.exception.DatabaseException;


public class BewertungDaoImpl implements BewertungDao{
	@Autowired
	private HibernateTemplate hibernateTemplate;
	@Autowired
	private BenutzerDao benutzerDao;
	private AngebotDao angebotDao;

	/**
	 * Insert a new Bewertung into the database.
	 * @param Bewertung
	 */
	@Override
	public void createBewertung(final Bewertung bewertung) {
		Benutzer benutzer;
		Angebot angebot;
		bewertung.setBwId(null);
		if (bewertung.getBenutzer() != null) {
			String email = bewertung.getBenutzer().getEmail();
			benutzer = benutzerDao.getBenutzerByEmail(email);
			bewertung.setBenutzer(benutzer);
		}
		if (bewertung.getAngebot() != null) {
			String titel = bewertung.getAngebot().getTitel();
			angebot = angebotDao.getAngebotByTitel(titel);
			bewertung.setAngebot(angebot);
		}
		try {
			HibernateCallback<Object> callback = new HibernateCallback<Object>() {
				@Override
				public Object doInHibernate(Session session)
						throws HibernateException, SQLException {
					session.save(bewertung);
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
	 * Delete a detached Bewertung from the database.
	 * @param id
	 */

	@Override
	public void deleteBewertung(final String id) {
		try {
			HibernateCallback<Object> callback = new HibernateCallback<Object>() {
				@Override
				public Object doInHibernate(Session session)
						throws HibernateException, SQLException {
					Bewertung bewertung = (Bewertung) session.load(Bewertung.class, Integer.parseInt(id));
					session.delete(bewertung);
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
	 * Finds all Bewertungen in the database.
	 * @return
	 */  

	@Override
	public List<Bewertung> getAllBewertungen() {
		List<Bewertung> result = null;
		try {
			HibernateCallback<List<Bewertung>> callback = new HibernateCallback<List<Bewertung>>() {
				@SuppressWarnings("unchecked")
				@Override
				public List<Bewertung> doInHibernate(Session session)
						throws HibernateException, SQLException {
					Query query = session.createQuery
							("FROM Bewertung");

					return query.list();
				}
			};
			result = (List<Bewertung>) hibernateTemplate.execute(callback);
		} catch (Exception e) {
			handleException(e);
		}
		return result;
	}

	/**
	 * Finds all Bewertungen by Angebot-Titel in the database.
	 * @param titel
	 * @return
	 */  
	// http://localhost/eb13-WebService/rest/Bewertung/Snickers/
	@Override
	public List<Bewertung> getBewertungenByAngebotTitel(final String titel) {

		List<Bewertung> result = null;
		try {
			HibernateCallback<List<Bewertung>> callback = new HibernateCallback<List<Bewertung>>() {
				@SuppressWarnings("unchecked")
				@Override
				public List<Bewertung> doInHibernate(Session session)
						throws HibernateException, SQLException {
					Query queryBewertung = session.createQuery
							("FROM Bewertung b "+
									"where b.angebot.titel = :titel");
					queryBewertung.setParameter("titel", titel);
					return queryBewertung.list();
				}
			};
			result = (List<Bewertung>) hibernateTemplate.execute(callback);
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
