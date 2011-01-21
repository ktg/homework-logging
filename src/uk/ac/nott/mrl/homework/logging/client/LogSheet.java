package uk.ac.nott.mrl.homework.logging.client;

import java.util.Date;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.Response;
import com.google.gwt.http.client.URL;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;

public class LogSheet implements EntryPoint
{
	interface LoggerUiBinder extends UiBinder<Widget, LogSheet>
	{
	}

	private static LoggerUiBinder uiBinder = GWT.create(LoggerUiBinder.class);

	@UiField
	Button submit;

	@UiField
	DateBox date;

	@UiField
	Label dateError;

	@UiField
	TextBox name;

	@UiField
	Label nameError;

	@UiField
	Label submitDetails;

	@UiField
	TextArea details;

	@Override
	public void onModuleLoad()
	{
		final RootPanel panel = RootPanel.get("form");

		panel.add(uiBinder.createAndBindUi(this));
		date.setFormat(new DateBox.DefaultFormat(DateTimeFormat.getFormat("dd MMM yyyy")));
		date.setValue(new Date());
		date.getTextBox().addChangeHandler(new ChangeHandler()
		{
			@Override
			public void onChange(final ChangeEvent event)
			{
				checkForm();
			}
		});

		dateError.getElement().getParentElement().getStyle().setDisplay(Display.NONE);
		nameError.getElement().getParentElement().getStyle().setDisplay(Display.NONE);
		submitDetails.setVisible(false);
		submit.setEnabled(false);
	}

	@UiHandler("name")
	void nameChanged(final ChangeEvent event)
	{
		checkForm();
	}

	@UiHandler("submit")
	void submit(final ClickEvent event)
	{
		final RequestBuilder builder = new RequestBuilder(RequestBuilder.POST, GWT.getHostPageBaseURL() + "addLog");
		String postData = "date=" + URL.encode(date.getTextBox().getText());
		postData += "&name=" + URL.encode(name.getText());
		postData += "&comment=" + URL.encode(details.getText());
		try
		{
			builder.setHeader("Content-Type", "application/x-www-form-urlencoded");
			submit.setEnabled(false);
			submitDetails.setVisible(true);
			submitDetails.setText("Submitting...");
			builder.sendRequest(postData, new RequestCallback()
			{
				@Override
				public void onError(final Request request, final Throwable exception)
				{
					submit.setEnabled(true);
					submitDetails.setText("Error Submitting.");
					GWT.log("Error", exception);
				}

				@Override
				public void onResponseReceived(final Request request, final Response response)
				{
					submit.setEnabled(true);
					GWT.log(response.getStatusText() + " : " + response.getText());
					submitDetails.setText("Successfully Submitted. Thank you!");
				}
			});
		}
		catch (final Exception e)
		{
			GWT.log(e.getMessage(), e);
		}
	}

	private void checkForm()
	{
		boolean enabled = true;
		if (date.getValue() == null)
		{
			submit.setEnabled(false);
			dateError.getElement().getParentElement().getStyle().setDisplay(Display.INLINE);
			if (date.getTextBox().getText().trim().equals(""))
			{
				dateError.setText("Please enter a date.");
			}
			else
			{
				dateError.setText("The computer cannot understand this date.");
			}
			enabled = false;
			date.addStyleName("error");
		}
		else
		{
			dateError.getElement().getParentElement().getStyle().setDisplay(Display.NONE);
			date.removeStyleName("error");
		}

		if (name.getValue() == null || name.getValue().trim().equals(""))
		{
			nameError.setText("Please enter a name.");
			nameError.getElement().getParentElement().getStyle().setDisplay(Display.INLINE);
			enabled = false;
			name.addStyleName("error");
		}
		else
		{
			nameError.getElement().getParentElement().getStyle().setDisplay(Display.NONE);
			name.removeStyleName("error");
		}

		submit.setEnabled(enabled);
	}
}
