package uk.ac.nott.mrl.homework.logging.serlvets;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.logging.Logger;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import uk.ac.nott.mrl.homework.logging.model.Entry;

@SuppressWarnings("serial")
public class CSVLogs extends HttpServlet
{
	private static final Logger logger = Logger.getLogger(CSVLogs.class.getName());

	private static final DateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
	private static final DateFormat timestampFormat = new SimpleDateFormat("dd MMM yyyy h:mm:ss a");

	@Override
	public void doGet(final HttpServletRequest req, final HttpServletResponse resp) throws IOException
	{
		logger.info("List Logs");

		resp.setContentType("text/csv");

		final EntityManager em = EMF.get().createEntityManager();
		final Query query = em.createQuery("SELECT e from Entry e");
		@SuppressWarnings("unchecked")
		final List<Entry> entries = query.getResultList();
		resp.getWriter().println("\"Name\",\"Date\",\"Timestamp\",\"IP Address\",\"Comment\"");
		for (final Entry entry : entries)
		{
			final String line = "\"" + entry.getName().replaceAll("\"", "\"\"") + "\",\""
					+ dateFormat.format(entry.getDate()) + "\",\"" + timestampFormat.format(entry.getTimestamp())
					+ "\",\"" + entry.getIp() + "\",\"" + entry.getComment().replaceAll("\"", "\"\"") + "\"";

			resp.getWriter().println(line);
		}

		resp.getWriter().flush();
	}
}
