package LogAnalysis;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * �α׺м�â (�α׺�)���� ó���� �̺�Ʈ Ŭ����
 * @author Team#1
 */
public class LogViewEvt implements ActionListener, ItemListener {

	private List<LogDataVO> logList;
	private LogView lv;
	private File file;
	private File dir;

	public LogViewEvt(LogView lv) {
		this.lv = lv;
	}// LogViewEvt

	/**
	 * ������ �����ϰ� Choice�� �������� log ������ ��ҷ� �߰����ִ� ����� �ϴ� method.
	 */
	public void selFile() {
		int sel = 0;
		JFileChooser jfc = new JFileChooser();
		// Ȯ���� ���� log�� ���ϸ� ���� �����ϵ��� �Ѵ�.
		jfc.setAcceptAllFileFilterUsed(false);
		jfc.addChoosableFileFilter(new FileNameExtensionFilter("*.log", "log"));
		jfc.setDialogTitle("������ �����ϼ���");
		jfc.setCurrentDirectory(new File(System.getProperty("user.home") + "/Desktop"));
		sel = jfc.showOpenDialog(lv);

		if (sel == JFileChooser.APPROVE_OPTION) {
			dir = jfc.getCurrentDirectory();
			lv.getChLogSelect().removeAll();
			// ������ ������ Choice�� �߰�.
			lv.getChLogSelect().add(jfc.getSelectedFile().getName());
			// VO ����.
			try {
				setLogDataVO();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public void selDir() {
		int sel = 0;
		JFileChooser jfc = new JFileChooser();
		File[] FileArr = null;

		jfc.setDialogTitle("������ �����ϼ���");
		// FileChooser�� �⺻ ��� ����.
		jfc.setCurrentDirectory(new File(System.getProperty("user.home") + "/Desktop"));
		// FileChooser�� �������� �����ϵ��� ����.
		jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		jfc.setAcceptAllFileFilterUsed(false);
		sel = jfc.showOpenDialog(lv);

		// FileChooser�� Open��ư�� ������������ ����ϵ��� ��.
		if (sel == JFileChooser.APPROVE_OPTION) {
			// ������ ������ ��θ� ����.
			dir = jfc.getSelectedFile();
			FileArr = dir.listFiles();
			// Choice�� ��� ��� ����.
			lv.getChLogSelect().removeAll();

			// �����߿��� logȮ���� ���� ���� ���ϸ��� ���̽��� �߰�.
			for (File temp : FileArr) {
				if (temp.getName().contains(".log")) {
					lv.getChLogSelect().add(temp.getName());
				}
			}
			try {
				setLogDataVO();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * LogDataVO�� ���׸����� ���� logList�� �����ϰ� �ʱ�ȭ�ϴ� ����� ���� method.
	 *
	 * @throws IOException
	 */
	public void setLogDataVO() throws IOException {
		logList = new ArrayList<LogDataVO>();
		String str = "";
		String key = "";
		String query = "sist";
		String hour = "";
		// ���̽��� ���õǾ��ִ� �����ۿ� �ش��ϴ� ������ ������ �Ҵ�.
		file = new File(dir + "/" + lv.getChLogSelect().getSelectedItem());
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));

			while ((str = br.readLine()) != null) {

				String[] tempArr = null;
				// �α� ������ �� �ٿ��� []�� �������� ���ڿ��� ������ �迭�� �Ҵ�.
				tempArr = str.substring(1, str.length() - 1).split("\\]\\[");

				// �ð��� ��Ÿ���� ��ҿ��� �� ���� ������ �Ҵ�.
				hour = tempArr[3].substring(11, 13);

				// Ű�� ������ ��Ÿ���� ��ҿ��� Ű���� ������ �Ҵ�.("key="�� �����Ҷ���, �׷��� �������� null)
				if (tempArr[1].contains("key=")) {
					key = tempArr[1].substring(tempArr[1].indexOf("=") + 1, tempArr[1].indexOf("&"));
				} else {
					key = null;
				}

				// ������ ���� ������ �����ϴ� VO�� �����ϰ� ����Ʈ�� �߰�.
				logList.add(new LogDataVO(tempArr[0], key, query, tempArr[2], hour));
				// �α� ������ �� ���� �����ش�.
				lv.getJlbRangeNum().setText("1 ~ " + String.valueOf(logList.size()));
			}
		} finally {
			if (br != null) {
				br.close();
			}
		}
	}

	/**
	 * view ��ư�� �������� ȣ��� method.
	 *
	 * @throws NumberFormatException
	 */
	public void view() throws NumberFormatException {
		// 6�� �䱸������ ���� ������(����,��)�� ����.
		int firstNum = Integer.parseInt(lv.getJtfFirst().getText());
		int secondNum = Integer.parseInt(lv.getJtfSecond().getText());

		// �������� �������� ������ Message Dialog�� ����ش�.
		if ((firstNum < 1) || (firstNum > secondNum) || (secondNum > logList.size())) {
			JOptionPane.showMessageDialog(lv, "������ �ٽ� �Է����ּ���");
			lv.getJtfFirst().setText("");
			lv.getJtfSecond().setText("");
		} else {
			// �䱸������ �ذ��ϱ����� ��ü ���� �� �Ҵ�.
			RequirementsAnalysis ra = new RequirementsAnalysis(logList, lv);
			// �α׸� ������ ��¥ �� �ð��� �����ֱ� ���� �۾�.
			long timeStamp = System.currentTimeMillis();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date = new Date(timeStamp);
			String strTime = sdf.format(date);
			// �α��� ������ append.
			lv.getJtaLog().append("========================================\n");
			lv.getJtaLog().append("  ���ϸ� (" + file.getName().substring(0, file.getName().indexOf("."))
					+ ")   log ������ ��¥ " + strTime + "\n");
			lv.getJtaLog().append("========================================\n");
			lv.getJtaLog().append("  1��) �ִٻ�� Ű�� �̸��� Ƚ�� \n");
			lv.getJtaLog().append("         >> " + ra.getMaxKey() + "\n");
			lv.getJtaLog().append("-------------------------------------------------------------------------\n");
			lv.getJtaLog().append("  2��) �������� ����Ƚ��, ����\n");
			lv.getJtaLog().append("         >> " + ra.getBrowserPer() + "\n");
			lv.getJtaLog().append("-------------------------------------------------------------------------\n");
			lv.getJtaLog().append("  3��) ���񽺸� ���������� ������ Ƚ��, ����(404) Ƚ��\n");
			lv.getJtaLog().append("         >> " + ra.getResNum() + "\n");
			lv.getJtaLog().append("-------------------------------------------------------------------------\n");
			lv.getJtaLog().append("  4��) ��û�� ���� ���� �ð� [�ð� : Ƚ��] => [" + ra.getConnectedTime() + "] \n");
			lv.getJtaLog().append("-------------------------------------------------------------------------\n");
			lv.getJtaLog().append("  5��) ���������� ��û(403)�� �߻��� Ƚ��, ���� ���ϱ�\n");
			lv.getJtaLog().append("         >> " + ra.getResErrorNum() + "\n");
			lv.getJtaLog().append("-------------------------------------------------------------------------\n");
			lv.getJtaLog().append("  6��)�ԷµǴ� ���ο� �ش��ϴ� �������(" + String.valueOf(firstNum) + " ~ "
					+ String.valueOf(secondNum) + ")��° \n");
			lv.getJtaLog().append("        ���ο� �ش��ϴ� ���� �� �ִ� ���Ű�� �̸��� Ƚ��\n");
			lv.getJtaLog().append("         >> " + ra.getRangeMaxKey() + "\n");
		}
	}// end view

	/**
	 * ������� �α׸� ���Ϸ� �����ϴ� ����� �ϴ� method.
	 *
	 * @throws IOException
	 */
	public void report() throws IOException {
		BufferedWriter bw = null;
		String data = lv.getJtaLog().getText();
		long timeStamp = System.currentTimeMillis();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
		Date date = new Date(timeStamp);
		String strTime = sdf.format(date);
		File dir = new File ("C:\\dev\\report");

		// ����ȭ�鿡 report��� ������ �������� ������ ������ �����Ѵ�.
		if (!dir.exists()) {
			dir.mkdirs();
		} // end if

		// ���ϻ���
		File file = new File(dir.getAbsolutePath() + "/report_" + strTime + ".dat");
		try {
			// 1.��Ʈ�� ������ ���Ͽ� �����Ѵ�
			bw = new BufferedWriter(new FileWriter(file));
			// 2.��Ʈ���� ���Ϸ� ����� ������ ����
			bw.write(data);
			// 3.��Ʈ���� ��ϵ� ������ ������ ���Ϸ� ����
			bw.flush();
		} finally {
			// 4.��Ʈ�� ���� ���� (��Ʈ���� ��ϵ� ������ �������� ����ǰ� ������ ��������.)
			if (bw != null) {
				bw.close();
			} // end if
		} // end try~finally

	}// report

	@Override
	public void actionPerformed(ActionEvent ae) {
		if (ae.getSource() == lv.getJbtnSelDir()) {
			selDir();
		}

		if (ae.getSource() == lv.getJbtnSelFile()) {
			selFile();
		}

		if (ae.getSource() == lv.getJbtnView()) {
			lv.getJtaLog().setText("");
			// ���õ� ������ ������ ���������� view Method ȣ��.
			if (dir != null) {
				try {
					view();
				} catch (NumberFormatException e) {
					JOptionPane.showMessageDialog(lv, "���ڸ� �Է����ּ���");
				}
			} else {
				JOptionPane.showMessageDialog(lv, "���� �Ǵ� ������ �������ּ���");
			}
			lv.getJtfFirst().setText("");
			lv.getJtfSecond().setText("");

		} // end if

		if (ae.getSource() == lv.getJbtnReport()) {
			// view method�� ȣ��� ���Ŀ��� report Method ȣ��.
			if (!(lv.getJtaLog().getText().equals(""))) {
				try {
					report();
					JOptionPane.showMessageDialog(lv, "����Ʈ �ۼ��� �Ϸ�Ǿ����ϴ�.");
					lv.getJtaLog().setText("");
				} catch (IOException e) {
					JOptionPane.showMessageDialog(lv, "����Ʈ�ϴ� ���־ ������ �߻��Ͽ����ϴ�.");
					e.printStackTrace();
				} // end try~catch
			} else {
				JOptionPane.showMessageDialog(lv, "View��ư�� ���� �����ּ���");
			}

		} // end if

	}// actionPerformed

	@Override
	public void itemStateChanged(ItemEvent ie) {
		// Choice�� ���°� ����ɶ����� setLogDataVO ȣ��.
		try {
			setLogDataVO();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}// itemStateChanged

}// class
