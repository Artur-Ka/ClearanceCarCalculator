package ua.ccc;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import ua.ccc.calc.Formulas;
import ua.ccc.calc.enums.Currency;
import ua.ccc.calc.enums.EngineType;
import ua.ccc.gui.AboutFrame;
import ua.ccc.gui.SettingsFrame;

public class Calculator extends JFrame
{
	private static final long serialVersionUID = 866001739784294144L;
	
	public static final int WIDTH = 340;
	public static final int HEIGHT = 420;
	
	public static final int FIELD_WIDTH = 100;
	public static final int FIELD_HEIGHT = 25;
	
	private final JMenuBar _menuBar = new JMenuBar();
	private final AboutFrame _aboutFrame = new AboutFrame();
	private final SettingsFrame _settingsFrame = new SettingsFrame();
	
	private final JLabel _carPriceLabel = new JLabel("Цена автомобиля (" + Config.CURRENCY.getEmblem() + "): ");
	private final JLabel _yearOfIssueLabel = new JLabel("Год выпуска:");
	private final JLabel _engineVolumeLabel = new JLabel("Объём двигателя (см³):");
	private final JLabel _engineTypeLabel = new JLabel("Тип двигателя:");
	private final JLabel _gracePerionLabel = new JLabel("Льготный период:");
	
	private final JTextField _carPriceField = new JTextField();
	private final JTextField _yearOfIssueField = new JTextField();
	private final JTextField _engineVolumeField = new JTextField();
	private final JComboBox<EngineType> _engineType = new JComboBox<>(EngineType.values());
	private final JCheckBox _gracePeriod = new JCheckBox();
	
	private final JTextArea _infoArea = new JTextArea();
	private final JButton _calculate = new JButton("Рассчитать");
	
	private Calculator()
	{
		super("CCC ver. 02.14");
		
		// Загрузка
		//============================================================================
//		MouseHolder.getInstance().load(this); // загрузка глобального обработчика мыши
		//============================================================================
		
		// Настройка главного окна
		//============================================================================
		final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		setSize(WIDTH, HEIGHT);
		setLocation(screenSize.width / 2 - WIDTH / 2, screenSize.height / 2 - HEIGHT / 2);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		setAlwaysOnTop(true);
//		if (DataLoader.ICON_IMAGE != null)
//			setIconImage(DataLoader.ICON_IMAGE.getImage());
		//============================================================================
		
		// Меню настроек
		//============================================================================
		final JCheckBoxMenuItem onTop = new JCheckBoxMenuItem("Поверх окон");
		onTop.setMaximumSize(new Dimension(100, 60));
		onTop.setSelected(true);
		onTop.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				setAlwaysOnTop(onTop.isSelected());
			}
		});
		
		final JMenu helpMenu = new JMenu("Настройки");
		final JMenuItem settings = new JMenuItem("Настройки");
		settings.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				final Point buttonLoc = new Point();
				// Узнать расположение кнопки относительно экрана (не компонента)
				SwingUtilities.convertPointFromScreen(buttonLoc, helpMenu);
				final int x = Math.abs(buttonLoc.x);
				final int y = Math.abs(buttonLoc.y) + helpMenu.getHeight();
				
				_settingsFrame.setAlwaysOnTop(isAlwaysOnTop());
				_settingsFrame.showWindow(x, y);
			}
		});
		final JMenuItem about = new JMenuItem("О программе");
		about.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent arg0)
			{
				final Point buttonLoc = new Point();
				// Узнать расположение кнопки относительно экрана (не компонента)
				SwingUtilities.convertPointFromScreen(buttonLoc, helpMenu);
				final int x = Math.abs(buttonLoc.x);
				final int y = Math.abs(buttonLoc.y) + helpMenu.getHeight();
				
				_aboutFrame.setAlwaysOnTop(isAlwaysOnTop());
				_aboutFrame.showWindow(x, y);
			}
		});
//		if (DataLoader.INFO_IMAGE != null)
//			about.setIcon(DataLoader.INFO_IMAGE);
		
		helpMenu.add(settings);
		helpMenu.add(about);
		
		_menuBar.add(onTop);
		_menuBar.add(helpMenu);
		
		// Основное окно
		//============================================================================
		final JPanel mainPanel = new JPanel();
		final JPanel fieldPanel = new JPanel();
		final JPanel boxPanel = new JPanel();
		fieldPanel.setLayout(new GridLayout(4, 2));
		fieldPanel.setPreferredSize(new Dimension(324, 110));
		fieldPanel.setBorder(BorderFactory.createEtchedBorder());
		
		final Dimension fieldsSize = new Dimension(100, 25);
		
		_carPriceField.setPreferredSize(fieldsSize);
		_yearOfIssueField.setPreferredSize(fieldsSize);
		_engineVolumeField.setPreferredSize(fieldsSize);
		_engineType.setPreferredSize(fieldsSize);
		
		_infoArea.setPreferredSize(new Dimension(WIDTH-20, (int)(HEIGHT/2.4)));
		_infoArea.setBorder(BorderFactory.createEtchedBorder());
		_infoArea.setEditable(false);
		
		fieldPanel.add(_carPriceLabel);
		fieldPanel.add(_carPriceField);
		fieldPanel.add(_yearOfIssueLabel);
		fieldPanel.add(_yearOfIssueField);
		fieldPanel.add(_engineVolumeLabel);
		fieldPanel.add(_engineVolumeField);
		fieldPanel.add(_engineTypeLabel);
		fieldPanel.add(_engineType);
		
		boxPanel.add(_gracePerionLabel);
		boxPanel.add(_gracePeriod);
		
		mainPanel.add(fieldPanel);
		mainPanel.add(boxPanel);
		mainPanel.add(new JScrollPane(_infoArea));
		
		_calculate.addActionListener(new CalculateListener());
		//============================================================================
		
		// Добавление компонентов в окно
		//============================================================================
		add(_menuBar, BorderLayout.NORTH);
		add(mainPanel, BorderLayout.CENTER);
		add(_calculate, BorderLayout.SOUTH);
	}
	
	public void refresh()
	{
		_carPriceLabel.setText("Цена автомобиля (" + Config.CURRENCY.getEmblem() + "): ");
	}
	
	private class CalculateListener implements ActionListener
	{
		int _carPrice;
		double _engineVolume;
		int _yearOfIssue;
		EngineType _type;
		
		double _duty;
		double _excise;
		double _VAT;
		double _pensionFund;
		double _fullCustomsFee;
		
		double _firstReg;
		
		@Override
		public void actionPerformed(ActionEvent e)
		{
			try
			{
				_carPrice = Integer.parseInt(_carPriceField.getText());
				_engineVolume = Double.parseDouble(_engineVolumeField.getText());
				_yearOfIssue = Integer.parseInt(_yearOfIssueField.getText());
				_type = EngineType.values()[_engineType.getSelectedIndex()];
				
				if (Config.CURRENCY == Currency.USD)
				{
					_carPrice = Formulas.USDToEUR(_carPrice);
				}
				else if (Config.CURRENCY == Currency.UAH)
				{
					_carPrice = Formulas.UAHToEUR(_carPrice);
				}
				
				_duty = Formulas.getImportDuty(_carPrice, _type);
				_excise = Formulas.getExcise(_engineVolume, _yearOfIssue, _type);
				
				// В льготный период акциз вдвое ниже
				if (_gracePeriod.isSelected())
					_excise /= 2;
				
				_VAT = Formulas.getVAT(_carPrice, _duty, _excise);
				_firstReg = Formulas.getFirstRegistrationFee(_engineVolume, _yearOfIssue);
				_fullCustomsFee = _duty + _excise + _VAT;
				
				if (Config.CURRENCY == Currency.USD)
				{
					_duty = Formulas.EURToUSD(_duty);
					_excise = Formulas.EURToUSD(_excise);
					_VAT = Formulas.EURToUSD(_VAT);
					_pensionFund = Formulas.UAHToUSD(Formulas.getPensionFundFee(Formulas.USDToUAH(_fullCustomsFee)));
					_fullCustomsFee = Formulas.EURToUSD(_fullCustomsFee);
					_firstReg = Formulas.UAHToUSD(_firstReg);
				}
				else if (Config.CURRENCY == Currency.EUR)
				{
					_pensionFund = Formulas.UAHToEUR(Formulas.getPensionFundFee(Formulas.EURToUAH(_fullCustomsFee)));
					_firstReg = Formulas.UAHToEUR(_firstReg);
				}
				else if (Config.CURRENCY == Currency.UAH)
				{
					_duty = Formulas.EURToUAH(_duty);
					_excise = Formulas.EURToUAH(_excise);
					_VAT = Formulas.EURToUAH(_VAT);
					_pensionFund = Formulas.getPensionFundFee(_fullCustomsFee);
					_fullCustomsFee = Formulas.EURToUAH(_fullCustomsFee);
				}
				
				_fullCustomsFee += _pensionFund;
				
				String info = new String("");
				info = info.concat("Ввозная пошлина = " + _duty).concat(Config.CURRENCY.getEmblem()).concat("\n");
				info = info.concat("Акцизный сбор = " + _excise).concat(Config.CURRENCY.getEmblem()).concat("\n");
				info = info.concat("Налог на добавленую стоимость (НДС) = " + _VAT).concat(Config.CURRENCY.getEmblem()).concat("\n");
				info = info.concat("Сбор в пенсионный фонд = " + _pensionFund).concat(Config.CURRENCY.getEmblem()).concat("\n");
				info = info.concat("Сумма таможенных платежей = " + _fullCustomsFee).concat(Config.CURRENCY.getEmblem()).concat("\n");
				info = info.concat("------------------------------------------------------------------------------ \n");
				info = info.concat("Первая регистрация авто = " + _firstReg).concat(Config.CURRENCY.getEmblem()).concat("\n");
				info = info.concat("------------------------------------------------------------------------------ \n");
				info = info.concat("Итоговая цена авто = " + (_fullCustomsFee + _carPrice + _firstReg)).concat(Config.CURRENCY.getEmblem());
				
				_infoArea.setText(info);
			}
			catch (Exception ex)
			{
				_infoArea.setText(ex.toString());
			}
		}
	}
	
	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable()
		{
			@Override
			public void run()
			{
				try
				{
//		            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
//		            UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
//		            UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
					UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
				}
				catch (Throwable thrown)
				{
					thrown.printStackTrace();
				}
				
				Calculator.getInstance().setVisible(true);
			}
		});
	}
	
	public static final Calculator getInstance()
	{
		return SingletonHolder._instance;
	}
	
	private static final class SingletonHolder
	{
		private static final Calculator _instance = new Calculator();
	}
}