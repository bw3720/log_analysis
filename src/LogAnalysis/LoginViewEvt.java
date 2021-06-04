package LogAnalysis;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JOptionPane;

/**
 * �α��� â���� ������ �̺�Ʈ ó��
 * @author Team#1
 */
public class LoginViewEvt implements ActionListener {
	private LoginView liv; // has a

	public LoginViewEvt(LoginView liv) {
		this.liv = liv;
		// ���Ҵ� : ���� ������� �Էµ����ʴ´�.
		// Ű�� �����ϸ� ���� �ߺ��ɼ� �ִ�.
	}//LoginViewEvt

	@Override
	public void actionPerformed(ActionEvent ae) {//��ư�� �ϳ��� ����� ���� ���о��� ���´�.

		String ID = liv.getJtfid().getText(); //�α��� â���� �Էµ� ���̵� �����´�.
		String PWD = liv.getJtfp().getText(); //�α��� â���� �Էµ� ��й�ȣ�� �����´�.

		Map<String, String> map = new HashMap<String, String>();
		map.put("admin", "1234"); //�α��ν� �̸� ������ ���̵�� ��й�ȣ (ID : admin, password : 1234)
		map.put("root", "1111");  //�α��ν� �̸� ������ ���̵�� ��й�ȣ (ID : root, password : 1111)

		if (map.containsKey(ID)) { //�α��� â���� �Է��� ���̵� map�� Ű���� �����ϴ� ���.
			if (map.get(ID).equals(PWD)) { //���̵�� �Է��� ��й�ȣ�� �������.
				new LogView(liv); //�Է��� ���̵�� ��й�ȣ�� �ǹٸ� ��� �α׺� ���̾�α׷� �Ѿ��.
			} else { //��й�ȣ�� Ʋ���� ��� ����� �޽��� ���̾�α�.
				JOptionPane.showMessageDialog(liv, "��й�ȣ�� �߸� �ԷµǾ����ϴ�.\n�ٽ��Է����ּ���");
			} // end if
		} else { //���̵�� ��й�ȣ ��� Ʋ���� ��� ����� �޽��� ���̾�α�.
			JOptionPane.showMessageDialog(liv, "���̵� �߸� �ԷµǾ����ϴ�..\n�ٽ��Է����ּ���");
		} // end if

	}// actionPerformed

}// class
