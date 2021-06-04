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
 * 로그분석창 (로그뷰)에서 처리할 이벤트 클래스
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
	 * 폴더를 선택하고 Choice에 폴더안의 log 파일을 요소로 추가해주는 기능을 하는 method.
	 */
	public void selFile() {
		int sel = 0;
		JFileChooser jfc = new JFileChooser();
		// 확장자 명이 log인 파일만 선택 가능하도록 한다.
		jfc.setAcceptAllFileFilterUsed(false);
		jfc.addChoosableFileFilter(new FileNameExtensionFilter("*.log", "log"));
		jfc.setDialogTitle("파일을 선택하세요");
		jfc.setCurrentDirectory(new File(System.getProperty("user.home") + "/Desktop"));
		sel = jfc.showOpenDialog(lv);

		if (sel == JFileChooser.APPROVE_OPTION) {
			dir = jfc.getCurrentDirectory();
			lv.getChLogSelect().removeAll();
			// 선택한 파일을 Choice에 추가.
			lv.getChLogSelect().add(jfc.getSelectedFile().getName());
			// VO 세팅.
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

		jfc.setDialogTitle("폴더를 선택하세요");
		// FileChooser의 기본 경로 설정.
		jfc.setCurrentDirectory(new File(System.getProperty("user.home") + "/Desktop"));
		// FileChooser가 폴더만을 선택하도록 설정.
		jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		jfc.setAcceptAllFileFilterUsed(false);
		sel = jfc.showOpenDialog(lv);

		// FileChooser의 Open버튼이 눌렸을때에만 기능하도록 함.
		if (sel == JFileChooser.APPROVE_OPTION) {
			// 선택한 폴더의 경로를 저장.
			dir = jfc.getSelectedFile();
			FileArr = dir.listFiles();
			// Choice의 모든 요소 제거.
			lv.getChLogSelect().removeAll();

			// 파일중에서 log확장자 명을 가진 파일만을 초이스에 추가.
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
	 * LogDataVO를 제네릭으로 갖는 logList를 생성하고 초기화하는 기능을 갖는 method.
	 *
	 * @throws IOException
	 */
	public void setLogDataVO() throws IOException {
		logList = new ArrayList<LogDataVO>();
		String str = "";
		String key = "";
		String query = "sist";
		String hour = "";
		// 초이스에 선택되어있는 아이템에 해당하는 파일을 변수에 할당.
		file = new File(dir + "/" + lv.getChLogSelect().getSelectedItem());
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));

			while ((str = br.readLine()) != null) {

				String[] tempArr = null;
				// 로그 파일의 한 줄에서 []을 기준으로 문자열을 나누어 배열에 할당.
				tempArr = str.substring(1, str.length() - 1).split("\\]\\[");

				// 시간을 나타내는 요소에서 시 만을 가져와 할당.
				hour = tempArr[3].substring(11, 13);

				// 키와 쿼리를 나타내는 요소에서 키만을 가져와 할당.("key="을 포함할때만, 그렇지 않을때는 null)
				if (tempArr[1].contains("key=")) {
					key = tempArr[1].substring(tempArr[1].indexOf("=") + 1, tempArr[1].indexOf("&"));
				} else {
					key = null;
				}

				// 위에서 얻은 값들을 저장하는 VO를 생성하고 리스트에 추가.
				logList.add(new LogDataVO(tempArr[0], key, query, tempArr[2], hour));
				// 로그 파일의 줄 수를 보여준다.
				lv.getJlbRangeNum().setText("1 ~ " + String.valueOf(logList.size()));
			}
		} finally {
			if (br != null) {
				br.close();
			}
		}
	}

	/**
	 * view 버튼이 눌렸을때 호출될 method.
	 *
	 * @throws NumberFormatException
	 */
	public void view() throws NumberFormatException {
		// 6번 요구사항을 위한 범위값(시작,끝)을 저장.
		int firstNum = Integer.parseInt(lv.getJtfFirst().getText());
		int secondNum = Integer.parseInt(lv.getJtfSecond().getText());

		// 범위값이 적절하지 않을때 Message Dialog를 띄워준다.
		if ((firstNum < 1) || (firstNum > secondNum) || (secondNum > logList.size())) {
			JOptionPane.showMessageDialog(lv, "범위를 다시 입력해주세요");
			lv.getJtfFirst().setText("");
			lv.getJtfSecond().setText("");
		} else {
			// 요구사항을 해결하기위한 객체 생성 및 할당.
			RequirementsAnalysis ra = new RequirementsAnalysis(logList, lv);
			// 로그를 생성한 날짜 및 시간을 보여주기 위한 작업.
			long timeStamp = System.currentTimeMillis();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date = new Date(timeStamp);
			String strTime = sdf.format(date);
			// 로그의 내용을 append.
			lv.getJtaLog().append("========================================\n");
			lv.getJtaLog().append("  파일명 (" + file.getName().substring(0, file.getName().indexOf("."))
					+ ")   log 생성된 날짜 " + strTime + "\n");
			lv.getJtaLog().append("========================================\n");
			lv.getJtaLog().append("  1번) 최다사용 키의 이름과 횟수 \n");
			lv.getJtaLog().append("         >> " + ra.getMaxKey() + "\n");
			lv.getJtaLog().append("-------------------------------------------------------------------------\n");
			lv.getJtaLog().append("  2번) 브라우저별 접속횟수, 비율\n");
			lv.getJtaLog().append("         >> " + ra.getBrowserPer() + "\n");
			lv.getJtaLog().append("-------------------------------------------------------------------------\n");
			lv.getJtaLog().append("  3번) 서비스를 성공적으로 수행한 횟수, 실패(404) 횟수\n");
			lv.getJtaLog().append("         >> " + ra.getResNum() + "\n");
			lv.getJtaLog().append("-------------------------------------------------------------------------\n");
			lv.getJtaLog().append("  4번) 요청이 가장 많은 시간 [시간 : 횟수] => [" + ra.getConnectedTime() + "] \n");
			lv.getJtaLog().append("-------------------------------------------------------------------------\n");
			lv.getJtaLog().append("  5번) 비정상적인 요청(403)이 발생한 횟수, 비율 구하기\n");
			lv.getJtaLog().append("         >> " + ra.getResErrorNum() + "\n");
			lv.getJtaLog().append("-------------------------------------------------------------------------\n");
			lv.getJtaLog().append("  6번)입력되는 라인에 해당하는 정보출력(" + String.valueOf(firstNum) + " ~ "
					+ String.valueOf(secondNum) + ")번째 \n");
			lv.getJtaLog().append("        라인에 해당하는 정보 중 최다 사용키의 이름과 횟수\n");
			lv.getJtaLog().append("         >> " + ra.getRangeMaxKey() + "\n");
		}
	}// end view

	/**
	 * 만들어진 로그를 파일로 저장하는 기능을 하는 method.
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

		// 바탕화면에 report라는 폴더가 존재하지 않으면 폴더를 생성한다.
		if (!dir.exists()) {
			dir.mkdirs();
		} // end if

		// 파일생성
		File file = new File(dir.getAbsolutePath() + "/report_" + strTime + ".dat");
		try {
			// 1.스트림 목적지 파일에 연결한다
			bw = new BufferedWriter(new FileWriter(file));
			// 2.스트림에 파일로 기록할 내용을 쓴다
			bw.write(data);
			// 3.스트림에 기록된 내용을 목적지 파일로 분출
			bw.flush();
		} finally {
			// 4.스트림 연결 끊기 (스트림에 기록된 내용이 목적지로 분출되고 연결이 끊어진다.)
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
			// 선택된 폴더나 파일이 있을때에만 view Method 호출.
			if (dir != null) {
				try {
					view();
				} catch (NumberFormatException e) {
					JOptionPane.showMessageDialog(lv, "숫자만 입력해주세요");
				}
			} else {
				JOptionPane.showMessageDialog(lv, "폴더 또는 파일을 선택해주세요");
			}
			lv.getJtfFirst().setText("");
			lv.getJtfSecond().setText("");

		} // end if

		if (ae.getSource() == lv.getJbtnReport()) {
			// view method가 호출된 이후에만 report Method 호출.
			if (!(lv.getJtaLog().getText().equals(""))) {
				try {
					report();
					JOptionPane.showMessageDialog(lv, "리포트 작성이 완료되었습니다.");
					lv.getJtaLog().setText("");
				} catch (IOException e) {
					JOptionPane.showMessageDialog(lv, "리포트하는 데있어서 오류가 발생하였씁니다.");
					e.printStackTrace();
				} // end try~catch
			} else {
				JOptionPane.showMessageDialog(lv, "View버튼을 먼저 눌러주세요");
			}

		} // end if

	}// actionPerformed

	@Override
	public void itemStateChanged(ItemEvent ie) {
		// Choice의 상태가 변경될때마다 setLogDataVO 호출.
		try {
			setLogDataVO();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}// itemStateChanged

}// class
