package LogAnalysis;

/**
 * �α����Ͽ��� ���е� �����͸� LogDataVO�� �ִ� Ŭ����.<br>
 * �����ʹ� ����ڵ�, Ű��, ����, ��������, �̿�ð����� ���еȴ�.
 * @author Team#1
 */
public class LogDataVO {
	private String code; //�۵��ڵ�
	private String key; //Ű��
	private String query; //����
	private String browser; //��������
	private String hour; //�̿�ð�

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

	//���� ������������ getter method//
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
