package com.github.jkdv.wr.ui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Iterator;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import javazoom.jl.decoder.JavaLayerException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import com.github.jkdv.wr.util.ExcelUtils;
import com.github.jkdv.wr.util.GradeMaker;
import com.github.jkdv.wts.engines.Naver;
import com.github.jkdv.wts.engines.WebEngine;

@SuppressWarnings("serial")
public class MainFrame extends JFrame implements KeyListener, WindowListener {

	private JPanel contentPane;
	private Iterator<Row> iterator;
	private ExcelUtils utils;
	private JLabel lblFormerWord;
	private JLabel lblFormerMeaning;
	private JLabel lblWord;
	private String meaning;
	private Row currentRow;
	
	private WebEngine voiceEngine = new Naver();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MainFrame() {
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 660, 430);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		lblFormerMeaning = new JLabel("");
		lblFormerMeaning.setVerticalAlignment(SwingConstants.TOP);
		lblFormerMeaning.setHorizontalAlignment(SwingConstants.CENTER);
		lblFormerMeaning.setBounds(6, 34, 648, 72);
		contentPane.add(lblFormerMeaning);

		lblWord = new JLabel("");
		lblWord.setFont(new Font("Lucida Grande", Font.PLAIN, 19));
		lblWord.setHorizontalAlignment(SwingConstants.CENTER);
		lblWord.setBounds(6, 139, 648, 150);
		contentPane.add(lblWord);

		lblFormerWord = new JLabel("");
		lblFormerWord.setHorizontalAlignment(SwingConstants.CENTER);
		lblFormerWord.setBounds(6, 6, 648, 16);
		contentPane.add(lblFormerWord);
		
		this.addKeyListener(this);
		this.addWindowListener(this);
	}

	private void loadWords() {
		utils = ExcelUtils.getInstance();
		try {
			utils.openExcel();
			iterator = utils.iterator();
			nextWord();
		} catch (IOException | URISyntaxException e) {
			e.printStackTrace();
		}
	}

	private void nextWord() {
		lblFormerWord.setText(lblWord.getText());
		lblFormerMeaning.setText(meaning);
		if (iterator.hasNext()) {
			currentRow = iterator.next();
			String word = currentRow.getCell(ExcelUtils.COL_WORD).getStringCellValue();
			lblWord.setText(word);
			meaning = currentRow.getCell(ExcelUtils.COL_MEANING).getStringCellValue();
		} else {
			lblWord.setText("** THE END **");
			meaning = "";
			currentRow = null;
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (currentRow == null)
			return;

		try {
			if (e.getKeyCode() == 10) {
				// Enter
				GradeMaker gm = new GradeMaker();
				Cell cell = currentRow.getCell(ExcelUtils.COL_GRADE);
				int grade = gm.getNext((int) cell.getNumericCellValue());
				utils.updateWord(currentRow, grade);
				nextWord();
				lblFormerWord.setForeground(Color.BLUE);
			} else if (e.getKeyCode() == 16) {
				// Shift
				utils.updateWord(currentRow, 1);
				nextWord();
				lblFormerWord.setForeground(Color.RED);
			} else if (e.getKeyCode() == 47)
				// Slash
				voiceEngine.play(lblWord.getText());
		} catch (IOException | URISyntaxException e1) {
			e1.printStackTrace();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void windowOpened(WindowEvent e) {
		loadWords();
	}

	@Override
	public void windowClosing(WindowEvent e) {
	}

	@Override
	public void windowClosed(WindowEvent e) {
	}

	@Override
	public void windowIconified(WindowEvent e) {
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
	}

	@Override
	public void windowActivated(WindowEvent e) {
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
	}
}
