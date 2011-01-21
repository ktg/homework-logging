package uk.ac.nott.mrl.homework.logging.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Entry
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;
	private Date date;
	private Date timestamp;
	private String comment;
	private String ip;

	public String getComment()
	{
		return comment;
	}

	public Date getDate()
	{
		return date;
	}

	public long getId()
	{
		return id;
	}

	public String getIp()
	{
		return ip;
	}

	public String getName()
	{
		return name;
	}

	public Date getTimestamp()
	{
		return timestamp;
	}

	public void setComment(final String comment)
	{
		this.comment = comment;
	}

	public void setDate(final Date date)
	{
		this.date = date;
	}

	public void setIp(final String ip)
	{
		this.ip = ip;
	}

	public void setName(final String name)
	{
		this.name = name;
	}

	public void setTimestamp(final Date timestamp)
	{
		this.timestamp = timestamp;
	}
}