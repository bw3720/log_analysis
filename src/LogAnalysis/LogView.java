package LogAnalysis;

import java.awt.Choice;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

/**
 * ���������� �α����� ���� �۵��Ǵ� �α׺м�â�� ������ & �̺�Ʈ ó�� Ŭ����.
 * @author Team#1
 */
@SuppressWarnings("serial")
public class LogView extends JDialog {
	private JButton jbtnView, jbtnReport, jbtnSelDir, jbtnSelFile;
	private JTextArea jtaLog;
	private Choice chLogSelect;
	private JTextField jtfFirst, jtfSecond;
	private JLabel jlbRangeNum;

	public LogView(LoginView liv) {
		super(liv, "�α� ���� ����", true);
		setSize(500, 800);
		setLocationRelativeTo(liv);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);

		JPanel jpBtn = null;
		JPanel jpTitle = null;
		JPanel jpSelDir = null;
		JPanel jpCmb = null;
		JPanel jpControl = null;
		JPanel jpRange = null; // ����

		JScrollPane jsp = null;

		JLabel jlbWest = new JLabel("<html><pre>		<pre/><html/>");
		JLabel jlbEest = new JLabel("<html><pre>		<pre/><html/>");
		JLabel jlbTitle = new JLabel("�α� ���� ����");
		JLabel jlbSelCmb = new JLabel("File Select : ",SwingConstants.RIGHT);
		JLabel jlbWhite = new JLabel("");
		JLabel jlbRange = new JLabel("������ �Է��� �ּ��� - �ش� �α��� ���� : ");
		jlbRangeNum = new JLabel();

		JLabel jlbTilde = new JLabel("~");

		jbtnView = new JButton("View");
		jbtnReport = new JButton("Report");
		jbtnSelDir = new JButton("���� ����");
		jbtnSelFile = new JButton("���� ����");
		jtaLog = new JTextArea();
		chLogSelect = new Choice();
		jtfFirst = new JTextField(5);
		jtfSecond = new JTextField(5);

		Font font = new Font("���� ���", Font.PLAIN, 15);
		jtaLog.setFont(font);
		jpBtn = new JPanel();
		jpBtn.add(jbtnView);
		jpBtn.add(jbtnReport);

		jpTitle = new JPanel();
		jpTitle.add(jlbTitle);

		jpSelDir = new JPanel();
		jpSelDir.add(jbtnSelDir);
		jpSelDir.add(jbtnSelFile);

		jpCmb = new JPanel();
		jpCmb.setLayout(new GridLayout(1,2));
		jpCmb.add(jlbSelCmb);
		jpCmb.add(chLogSelect);
		jpCmb.add(jlbWhite);

		jlbRange.setSize(50,10);
		JPanel jpRangeComment = new JPanel();
		jpRangeComment.add(jlbRange);
		jpRangeComment.add(jlbRangeNum);

		jpRange = new JPanel();
		jpRange.add(jtfFirst);
		jpRange.add(jlbTilde);
		jpRange.add(jtfSecond);

		jpControl = new JPanel();
		jpControl.setLayout(new GridLayout(5, 1));
		jpControl.add(jpSelDir);
		jpControl.add(jpCmb);
		jpControl.add(jpRangeComment);
		jpControl.add(jpRange);
		jpControl.add(jpBtn);

		// �̺�Ʈó��
		LogViewEvt lve = new LogViewEvt(this);
		jbtnSelDir.addActionListener(lve);
		jbtnSelFile.addActionListener(lve);
		jbtnReport.addActionListener(lve);
		jbtnView.addActionListener(lve);
		chLogSelect.addItemListener(lve);

		jtaLog.setEditable(false);
		jsp = new JScrollPane(jtaLog);

		add("Center", jsp);
		add("South", jpControl);
		add("East", jlbEest);
		add("West", jlbWest);
		add("North", jpTitle);

		setVisible(true);
	}//LogView

	public JButton getJbtnSelFile() {
		return jbtnSelFile;
	}

	public JButton getJbtnSelDir() {
		return jbtnSelDir;
	}

	public JButton getJbtnView() {
		return jbtnView;
	}

	public JButton getJbtnReport() {
		return jbtnReport;
	}

	public JTextArea getJtaLog() {
		return jtaLog;
	}

	public Choice getChLogSelect() {
		return chLogSelect;
	}

	public JTextField getJtfFirst() {
		return jtfFirst;
	}

	public JTextField getJtfSecond() {
		return jtfSecond;
	}

	public JLabel getJlbRangeNum() {
		return jlbRangeNum;
	}

}//class
