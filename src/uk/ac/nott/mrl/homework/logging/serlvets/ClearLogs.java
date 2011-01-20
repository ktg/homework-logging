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
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException
	{
		logger.info("Clear Logs");
		
		EntityManager em = EMF.get().createEntityManager();

		
		Query query = em.createQuery("SELECT e from Entry e");
		@SuppressWarnings("unchecked")
		List<Entry> entries = query.getResultList();
		for(Entry entry: entries)
		{
			em.getTransaction().begin();			
			em.remove(entry);
			em.getTransaction().commit();			
		}
	}
}
