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
public class Logs extends HttpServlet
{
	private static final Logger logger = Logger.getLogger(Logs.class.getName());

	private static final DateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
	private static final DateFormat timestampFormat = new SimpleDateFormat("dd MMM yyyy h:mm:ss a");

	@Override
	public void doGet(final HttpServletRequest req, final HttpServletResponse resp) throws IOException
	{
		logger.info("List Logs");

		resp.setContentType("text/html");

		final EntityManager em = EMF.get().createEntityManager();
		final Query query = em.createQuery("SELECT e from Entry e");
		@SuppressWarnings("unchecked")
		final List<Entry> entries = query.getResultList();
		resp.getWriter().println("<html><head><title>Logs</title></head><body><table>");
		resp.getWriter()
				.println("<tr><td>Name</td><td>Date</td><td>Timestamp</td><td>IP Address</td><td>Comment</td></tr>");
		for (final Entry entry : entries)
		{
			final String line = "<tr><td>" + entry.getName() + "</td><td>" + dateFormat.format(entry.getDate())
					+ "</td><td>" + timestampFormat.format(entry.getTimestamp()) + "</td><td>" + entry.getIp()
					+ "</td><td>" + entry.getComment() + "</td></tr>";

			resp.getWriter().println(line);
		}

		resp.getWriter().println("</table></body></html>");
		resp.getWriter().flush();
	}
}
