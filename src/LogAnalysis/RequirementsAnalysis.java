package LogAnalysis;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * �α������� �м��ؼ� �䱸������ ���ϴ� ������ ��� Ŭ����.
 * @author Team#1
 */
public class RequirementsAnalysis {
	
	//countMapValue method�� �Ű������� ����� ����
	public final static int DEFAULT_START = -1;
	public final static int DEFAULT_END = -1;
	public final static int KEY_MODE = 0; 
	public final static int BROWSER_MODE = 1;
	public final static int TIME_MODE = 2;

	private List<LogDataVO> logList;
	private LogView lv;

	private Map<String, Integer> keyAllMap;
	private Map<String, Integer> browserMap;
	private Map<String, Integer> timeMap;
	private Map<String, Integer> keyRangeMap;

	public RequirementsAnalysis(List<LogDataVO> logList, LogView lv){
		this.logList = logList;
		this.lv = lv;
		this.keyAllMap = new HashMap<String, Integer>();
		this.browserMap = new HashMap<String, Integer>();
		this.timeMap = new HashMap<String, Integer>();
		this.keyRangeMap = new HashMap<String, Integer>();

		for (int i = 0; i < logList.size(); i++) {
			LogDataVO tempData = logList.get(i);
			
			//VO�� key�� null�� �ƴ϶�� keyAllMap,keyRangeMap�� �߰� value�� 0���� �ξ� key�� ��� ������
			//Map�� key ������ ���� �ȴ�.
			if (tempData.getKey() != null) {
				keyAllMap.put(tempData.getKey(), 0);
				keyRangeMap.put(tempData.getKey(), 0);
			}//end if
			//VO�� hour�� browser�� Map�� �߰��ȴ�.
			timeMap.put(tempData.getHour(), 0);
			browserMap.put(tempData.getBrowser(), 0);
		}//end for
	}//RequirementsAnalysis

	/**
	 * �䱸���� 1�� : �ִٻ��Ű�� �̸��� Ƚ��
	 * @return
	 */
	public String getMaxKey() {
		String answer = "";

		countMapValue(keyAllMap, DEFAULT_START, DEFAULT_END, KEY_MODE);

		answer = "�ִ� ��� Ű,Ƚ�� => " + getMaxEntry(keyAllMap);
		return answer;
	}//getMaxKey

	/**
	 * �䱸���� 2�� : �������� ����Ƚ��, ����
	 * @return
	 */
	public String getBrowserPer() {
		String answer = "";

		Set<String> tempBrowserSet = browserMap.keySet();
		Iterator<String> browserIter = tempBrowserSet.iterator();

		countMapValue(browserMap, DEFAULT_START, DEFAULT_END, BROWSER_MODE);
		
		while (browserIter.hasNext()) {
			String tempBrowser = browserIter.next();
			int tempValue = browserMap.get(tempBrowser);
			answer += (tempBrowser + " : " + tempValue + "ȸ "
					+ (Math.round(((double) tempValue / logList.size() * 100) * 10) / 10.0) + "%\n              ");
		}//end while

		return answer;
	}//getBrowserPer

	/**
	 * �䱸���� 3�� : ���񽺸� ���������� ������ Ƚ�� & ���� (404 = �۵���� �ڵ�) Ƚ��.
	 * @return ���� Ƚ��, ���� Ƚ��
	 */
	public String getResNum() {
		String answer = "";
		String temp = "";
		int sucCnt = 0;
		int failCnt = 0;

		for (LogDataVO tempLdv : logList) {
			temp = tempLdv.getCode();
			if (temp.equals("200")) {
				sucCnt++;
			} else if (temp.equals("404")) {
				failCnt++;
			}//end else if
		}//end for

		answer = "���� Ƚ�� : " + sucCnt + ", ���� Ƚ�� : " + failCnt;

		return answer;
	}//getResNum

	/**
	 * �䱸���� 4�� : ��û�� ���� ���� �ð�
	 * @return ���帹�� ����(��û)�� �ð�
	 */
	public String getConnectedTime() {
		String answer = "";

		countMapValue(timeMap, DEFAULT_START, DEFAULT_END, TIME_MODE);


		answer = getMaxEntry(timeMap);

		return answer;
	}//getConnectedTime

	/**
	 * �䱸���� 5�� : ���������� ��û(403=�۵���� �ڵ�)�� �߻��� Ƚ�� & ���� ���ϱ�
	 * @return �߻�Ƚ��, �߻�����
	 */
	public String getResErrorNum() {
		String temp = "";
		int cnt = 0;

		for (LogDataVO tempLdv : logList) {
			temp = tempLdv.getCode();
			if (temp.equals("403")) {
				cnt++;
			}//end if
		}//end for

		return "�߻�Ƚ�� : " + cnt + ", �߻����� : " + (Math.round(((double) cnt / logList.size() * 100) * 100) / 100.0) + "%";
	}//getResErrorNum

	/**
	 * �䱸���� 6�� : �ԷµǴ� ���ο� �ش��ϴ�
	 * 				���� ���(1000~1500)��° ���ο� �ش��ϴ� ���� �� �ִ� ��� Ű�� �̸��� Ƚ��
	 *
	 * @return �ִ� ��� Ű,Ƚ��
	 */
	public String getRangeMaxKey() {
		int firstNum = Integer.parseInt(lv.getJtfFirst().getText()) - 1;
		int secondNum = Integer.parseInt(lv.getJtfSecond().getText());
		String answer = "";

		countMapValue(keyRangeMap, firstNum, secondNum, KEY_MODE);

		answer = "�ִ� ��� Ű,Ƚ�� => " + getMaxEntry(keyRangeMap);

		return answer;
	}//getRangeMaxKey

	/**
	 * �߻��� Ƚ���� �������� method
	 * @param map
	 * @param startIdx
	 * @param endIdx
	 * @param mode
	 */
	public void countMapValue(Map<String, Integer> map, int startIdx, int endIdx, int mode) {
		String temp = "";
		if (startIdx == DEFAULT_START) {
			startIdx = 0;
		}//end if
		if (endIdx == DEFAULT_END) {
			endIdx = logList.size();
		}//end if
		if (mode == KEY_MODE) {//�ش� Ű�� �󸶳� ����ƴ°�
			for (int i = startIdx; i < endIdx; i++) {

				if (logList.get(i).getKey() == null) {
					continue;
				}//end if

				temp = logList.get(i).getKey();
				map.put(temp, map.get(temp) + 1);
			}//end for
		} else if (mode == BROWSER_MODE) {//�ش� �������� �󸶳� ����ƴ°�

			for (int i = startIdx; i < endIdx; i++) {
				temp = logList.get(i).getBrowser();
				map.put(temp, map.get(temp) + 1);
			}//end for

		} else if (mode == TIME_MODE) {//�ش� �ð��� �󸶳� ��û�� �־��°�

			for (int i = startIdx; i < endIdx; i++) {
				temp = logList.get(i).getHour();
				map.put(temp, map.get(temp) + 1);
			}//end for
		}//end else if
	}//countMapValue

	/**
	 * �ִ� Ƚ���� ���ϴ� method
	 *
	 * @param map
	 * @return
	 */
	public String getMaxEntry(Map<String, Integer> map) {
		int maxKeyCnt = 0;
		String maxEntry = "";

		//Map�� value�߿� ���� ū ���� �޴´�.
		maxKeyCnt = Collections.max(map.values());

		//Map�� entry�߿� value�� maxKeyCnt�� ���ٸ� maxEntry ���ڿ��� �ش��ϴ� key�� value�� ������ ��´�.(�ߺ� ����.)
		for (Map.Entry<String, Integer> entry : map.entrySet()) {
			if ((entry.getValue()) == (maxKeyCnt)) {
				if (maxEntry.equals("")) {
					maxEntry = entry.getKey() + " : " + entry.getValue();
				} else {
					maxEntry += ", " + entry.getKey() + " : " + entry.getValue();
				}//end else
			}//end if
		}//end for

		return maxEntry;
	}//RequirementsAnalysis
}//class