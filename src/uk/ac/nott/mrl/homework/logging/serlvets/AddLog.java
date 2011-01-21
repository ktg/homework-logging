package uk.ac.nott.mrl.homework.logging.serlvets;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import uk.ac.nott.mrl.homework.logging.model.Entry;

@SuppressWarnings("serial")
public class AddLog extends HttpServlet
{
	private static final Logger logger = Logger.getLogger(AddLog.class.getName());

	private static final DateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");

	@Override
	public void doPost(final HttpServletRequest req, final HttpServletResponse resp) throws IOException
	{
		logger.info("Add Log");

		final EntityManager em = EMF.get().createEntityManager();
		final Entry entry = new Entry();

		entry.setComment(req.getParameter("comment"));
		entry.setIp(req.getRemoteAddr());
		entry.setTimestamp(new Date());
		try
		{
			entry.setDate(dateFormat.parse(req.getParameter("date")));
		}
		catch (final ParseException e)
		{
			// TODO Auto-generated catch block
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
		entry.setName(req.getParameter("name"));

		em.getTransaction().begin();
		em.persist(entry);
		em.getTransaction().commit();
		resp.setContentType("text/plain");
		resp.getWriter().println("Success");
		logger.info("Added Entry for " + entry.getName() + ", " + req.getParameter("date") + ": " + entry.getComment());
	}
}
