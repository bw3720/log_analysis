package LogAnalysis;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JOptionPane;

/**
 * 로그인 창에서 수행할 이벤트 처리
 * @author Team#1
 */
public class LoginViewEvt implements ActionListener {
	private LoginView liv; // has a

	public LoginViewEvt(LoginView liv) {
		this.liv = liv;
		// 값할당 : 값은 순서대로 입력되지않는다.
		// 키는 유일하며 값은 중복될수 있다.
	}//LoginViewEvt

	@Override
	public void actionPerformed(ActionEvent ae) {//버튼이 하나인 관계로 따로 구분없이 적는다.

		String ID = liv.getJtfid().getText(); //로그인 창에서 입력된 아이디를 가져온다.
		String PWD = liv.getJtfp().getText(); //로그인 창에서 입력된 비밀번호를 가져온다.

		Map<String, String> map = new HashMap<String, String>();
		map.put("admin", "1234"); //로그인시 미리 지정된 아이디와 비밀번호 (ID : admin, password : 1234)
		map.put("root", "1111");  //로그인시 미리 지정된 아이디와 비밀번호 (ID : root, password : 1111)

		if (map.containsKey(ID)) { //로그인 창에서 입력한 아이디가 map의 키값에 존재하는 경우.
			if (map.get(ID).equals(PWD)) { //아이디와 입력한 비밀번호가 같은경우.
				new LogView(liv); //입력한 아이디와 비밀번호가 옳바른 경우 로그뷰 다이얼로그로 넘어간다.
			} else { //비밀번호만 틀렸을 경우 출력할 메시지 다이얼로그.
				JOptionPane.showMessageDialog(liv, "비밀번호가 잘못 입력되었습니다.\n다시입력해주세요");
			} // end if
		} else { //아이디와 비밀번호 모두 틀렸을 경우 출력할 메시지 다이얼로그.
			JOptionPane.showMessageDialog(liv, "아이디가 잘못 입력되었습니다..\n다시입력해주세요");
		} // end if

	}// actionPerformed

}// class
