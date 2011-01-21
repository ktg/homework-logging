package uk.ac.nott.mrl.homework.logging.serlvets;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import uk.ac.nott.mrl.homework.logging.model.Entry;

@SuppressWarnings("serial")
public class ClearLogs extends HttpServlet
{
	private static final Logger logger = Logger.getLogger(ClearLogs.class.getName());

	@Override
	public void doGet(final HttpServletRequest req, final HttpServletResponse resp) throws IOException
	{
		logger.info("Clear Logs");

		final EntityManager em = EMF.get().createEntityManager();

		final Query query = em.createQuery("SELECT e from Entry e");
		@SuppressWarnings("unchecked")
		final List<Entry> entries = query.getResultList();
		for (final Entry entry : entries)
		{
			em.getTransaction().begin();
			em.remove(entry);
			em.getTransaction().commit();
		}
	}
}
