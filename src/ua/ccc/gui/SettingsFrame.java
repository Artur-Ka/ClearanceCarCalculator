package ua.ccc.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import ua.ccc.Calculator;
import ua.ccc.Config;
import ua.ccc.calc.enums.Currency;

/**
 * 
 * @author A. Karpenko
 * @date 23 янв. 2019 г. 13:48:17
 */
@SuppressWarnings("serial")
public class SettingsFrame extends JDialog
{
	public static final int WIDTH = 200;
	public static final int HEIGHT = 140;
	
	private final JTextField _usd = new JTextField();
	private final JTextField _eur = new JTextField();
	private final JComboBox<Currency> _currency = new JComboBox<>(Currency.values());
	
	private final JButton _ok = new JButton("Ок");
	private final JButton _cancel = new JButton("Отмена");
	
	public SettingsFrame()
	{
		setTitle("Настройки");
		setSize(WIDTH, HEIGHT);
		setModal(true);
		setResizable(false);
		
		final JPanel fieldsPanel = new JPanel();
		fieldsPanel.setLayout(new GridLayout(3, 2));
		fieldsPanel.setBorder(BorderFactory.createEtchedBorder());
		
		_usd.setText(String.valueOf(Config.USD));
		_eur.setText(String.valueOf(Config.EUR));
		_currency.setSelectedItem(Config.CURRENCY);
		
		fieldsPanel.add(new JLabel("USD:"));
		fieldsPanel.add(_usd);
		fieldsPanel.add(new JLabel("EUR:"));
		fieldsPanel.add(_eur);
		fieldsPanel.add(new JLabel("Валюта:"));
		fieldsPanel.add(_currency);
		
		final JPanel buttonsPanel = new JPanel();
		final Dimension buttonsSize = new Dimension(80, 26);
		
		_ok.setPreferredSize(buttonsSize);
		_ok.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				setVisible(false);
				
				Config.USD = Double.parseDouble(_usd.getText().trim());
				Config.EUR = Double.parseDouble(_eur.getText().trim());
				Config.CURRENCY = (Currency) _currency.getSelectedItem();
				
				Calculator.getInstance().refresh();
			}
		});
		
		_cancel.setPreferredSize(buttonsSize);
		_cancel.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				setVisible(false);
			}
		});
		
		buttonsPanel.add(_ok);
		buttonsPanel.add(_cancel);
		
		add(fieldsPanel, BorderLayout.NORTH);
		add(buttonsPanel, BorderLayout.SOUTH);
	}
	
	public void showWindow(int x, int y)
	{
		setLocation(x, y);
		setVisible(true);
	}
}