package LogAnalysis;

/**
 * 로그파일에서 구분된 데이터를 LogDataVO에 넣는 클래스.<br>
 * 데이터는 결과코드, 키값, 쿼리, 사용브라우저, 이용시간으로 구분된다.
 * @author Team#1
 */
public class LogDataVO {
	private String code; //작동코드
	private String key; //키값
	private String query; //쿼리
	private String browser; //사용브라우저
	private String hour; //이용시간

	public LogDataVO(String code, String key, String query, String browser, String hour) {
		this.code = code;
		this.key = key;
		this.query = query;
		this.browser = browser;
		this.hour = hour;
	}//LogDataVO

	@Override
	public String toString() {
		return "LogDataVO{" + "code='" + code + '\'' + ", key='" + key + '\'' + ", query='" + query + '\''
				+ ", browser='" + browser + '\'' + ", hour='" + hour + '\'' + '}';
	}//toString

	//값을 꺼내쓰기위한 getter method//
	public String getCode() {
		return code;
	}//getCode

	public String getKey() {
		return key;
	}//getKey

	public String getQuery() {
		return query;
	}//getQuery

	public String getBrowser() {
		return browser;
	}//getBrowser

	public String getHour() {
		return hour;
	}//getHour

}//class
