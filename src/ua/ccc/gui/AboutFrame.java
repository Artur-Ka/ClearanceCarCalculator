package ua.ccc.gui;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.BorderFactory;
import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkEvent.EventType;
import javax.swing.event.HyperlinkListener;

@SuppressWarnings("serial")
public class AboutFrame extends JDialog
{
	public static final int WIDTH = 360;
	public static final int HEIGHT = 240;
	
	public static final String CONTACT_URL = "https://vk.com/indexoutofboundsexception";
	public static final String AUTHOR_INFO = "<html><body>"
			+ "<table width=" + WIDTH + ">"
			+ "<tr>"
			+ "<td>Лицензия:</td>"
			+ "<td>Beerware License</td>"
			+ "</tr"
			+ "<td>Автор:</td>"
			+ "<td>Карпенко Артур</td>"
			+ "</tr"
			+ "<td>Связь:</td>"
			+ "<td><a href=\"\">" + CONTACT_URL + "</a></td>"
			+ "</table>"
			+ "</body></html>";
	
	public static final String INFO_TEXT = "*Здесь могла бы быть ваша реклама :)*";
	
	public AboutFrame()
	{
		super();
		setSize(WIDTH, HEIGHT);
		setModal(true);
		setResizable(false);		
		setTitle("О программе");
		setLayout(new FlowLayout());
		
		// Панель с информацией об авторе
		//==================================================================
		JTextPane infoPane = new JTextPane();
		infoPane.setEditable(false);
		infoPane.setBackground(new Color(238, 238, 238));
		infoPane.setContentType("text/html");
		infoPane.setBorder(BorderFactory.createEtchedBorder());
		infoPane.setText(AUTHOR_INFO);
		infoPane.addHyperlinkListener(new HyperlinkListener()
		{
			@Override
			public void hyperlinkUpdate(HyperlinkEvent arg0)
			{
				if (arg0.getEventType() == EventType.ACTIVATED)
				{
					if (Desktop.isDesktopSupported())
					{
						try
						{
							Desktop.getDesktop().browse(new URI(CONTACT_URL));
						}
						catch (IOException | URISyntaxException e)
						{
							e.printStackTrace();
						}
					}
				}
			}
		});
		//==================================================================
		
		// Текстовая панель
		//==================================================================
		JTextArea textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setText(INFO_TEXT);
		final JScrollPane scrollPane = new JScrollPane(textArea, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setPreferredSize(new Dimension(WIDTH - 10, HEIGHT - infoPane.getHeight() - 130));
		//==================================================================
		
		add(infoPane);
		add(scrollPane);
	}
	
	public void showWindow(int x, int y)
	{
//		if (DataLoader.ICON_IMAGE != null)
//			setIconImage(DataLoader.ICON_IMAGE.getImage());
		
		setLocation(x, y);
		setVisible(true);
	}
}
