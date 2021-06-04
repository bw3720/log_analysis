package LogAnalysis;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 로그파일을 분석해서 요구사항인 원하는 정보를 얻는 클래스.
 * @author Team#1
 */
public class RequirementsAnalysis {
	
	//countMapValue method에 매개변수로 사용할 값들
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
			
			//VO의 key가 null이 아니라면 keyAllMap,keyRangeMap에 추가 value는 0으로 두어 key의 모든 종류가
			//Map의 key 값으로 들어가게 된다.
			if (tempData.getKey() != null) {
				keyAllMap.put(tempData.getKey(), 0);
				keyRangeMap.put(tempData.getKey(), 0);
			}//end if
			//VO의 hour와 browser가 Map에 추가된다.
			timeMap.put(tempData.getHour(), 0);
			browserMap.put(tempData.getBrowser(), 0);
		}//end for
	}//RequirementsAnalysis

	/**
	 * 요구사항 1번 : 최다사용키의 이름과 횟수
	 * @return
	 */
	public String getMaxKey() {
		String answer = "";

		countMapValue(keyAllMap, DEFAULT_START, DEFAULT_END, KEY_MODE);

		answer = "최다 사용 키,횟수 => " + getMaxEntry(keyAllMap);
		return answer;
	}//getMaxKey

	/**
	 * 요구사항 2번 : 브라우저별 접속횟수, 비율
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
			answer += (tempBrowser + " : " + tempValue + "회 "
					+ (Math.round(((double) tempValue / logList.size() * 100) * 10) / 10.0) + "%\n              ");
		}//end while

		return answer;
	}//getBrowserPer

	/**
	 * 요구사항 3번 : 서비스를 성공적으로 수행한 횟수 & 실패 (404 = 작동결과 코드) 횟수.
	 * @return 성공 횟수, 실패 횟수
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

		answer = "성공 횟수 : " + sucCnt + ", 실패 횟수 : " + failCnt;

		return answer;
	}//getResNum

	/**
	 * 요구사항 4번 : 요청이 가장 많은 시간
	 * @return 가장많이 실행(요청)된 시간
	 */
	public String getConnectedTime() {
		String answer = "";

		countMapValue(timeMap, DEFAULT_START, DEFAULT_END, TIME_MODE);


		answer = getMaxEntry(timeMap);

		return answer;
	}//getConnectedTime

	/**
	 * 요구사항 5번 : 비정상적인 요청(403=작동결과 코드)이 발생한 횟수 & 비율 구하기
	 * @return 발생횟수, 발생비율
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

		return "발생횟수 : " + cnt + ", 발생비율 : " + (Math.round(((double) cnt / logList.size() * 100) * 100) / 100.0) + "%";
	}//getResErrorNum

	/**
	 * 요구사항 6번 : 입력되는 라인에 해당하는
	 * 				정보 출력(1000~1500)번째 라인에 해당하는 정보 중 최다 사용 키의 이름과 횟수
	 *
	 * @return 최다 사용 키,횟수
	 */
	public String getRangeMaxKey() {
		int firstNum = Integer.parseInt(lv.getJtfFirst().getText()) - 1;
		int secondNum = Integer.parseInt(lv.getJtfSecond().getText());
		String answer = "";

		countMapValue(keyRangeMap, firstNum, secondNum, KEY_MODE);

		answer = "최다 사용 키,횟수 => " + getMaxEntry(keyRangeMap);

		return answer;
	}//getRangeMaxKey

	/**
	 * 발생한 횟수를 세기위한 method
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
		if (mode == KEY_MODE) {//해당 키가 얼마나 실행됐는가
			for (int i = startIdx; i < endIdx; i++) {

				if (logList.get(i).getKey() == null) {
					continue;
				}//end if

				temp = logList.get(i).getKey();
				map.put(temp, map.get(temp) + 1);
			}//end for
		} else if (mode == BROWSER_MODE) {//해당 브라우저가 얼마나 실행됐는가

			for (int i = startIdx; i < endIdx; i++) {
				temp = logList.get(i).getBrowser();
				map.put(temp, map.get(temp) + 1);
			}//end for

		} else if (mode == TIME_MODE) {//해당 시간에 얼마나 요청이 있었는가

			for (int i = startIdx; i < endIdx; i++) {
				temp = logList.get(i).getHour();
				map.put(temp, map.get(temp) + 1);
			}//end for
		}//end else if
	}//countMapValue

	/**
	 * 최다 횟수를 구하는 method
	 *
	 * @param map
	 * @return
	 */
	public String getMaxEntry(Map<String, Integer> map) {
		int maxKeyCnt = 0;
		String maxEntry = "";

		//Map의 value중에 가장 큰 값을 받는다.
		maxKeyCnt = Collections.max(map.values());

		//Map의 entry중에 value가 maxKeyCnt와 같다면 maxEntry 문자열에 해당하는 key와 value의 정보를 담는다.(중복 포함.)
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