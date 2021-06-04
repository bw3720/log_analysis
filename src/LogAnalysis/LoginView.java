package LogAnalysis;

import java.awt.Font;
import java.awt.TextField;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 * ���̵�� ��й�ȣ�� �Է��ؼ� �α����� �ϴ� �α���â�� ������ Ŭ����
 * @author Team#1
 */
@SuppressWarnings("serial")
public class LoginView extends JFrame {
    //�α��� â������ �������
    private JButton jbtnLogin; //�α��� ��ư
    private JTextField jtfID; //���̵� �Է� �ؽ�Ʈ �ʵ�
    private TextField jtfPassword; //��й�ȣ �Է� �ؽ�Ʈ �ʵ�

    public LoginView() {//�α���â ������ Ŭ���� & �α���â������ �̺�Ʈ ����
        //��ư, ��, �ؽ�Ʈ �ʵ� ������ ����
        JLabel jlb = new JLabel("�α��� ������ �Է����ּ���");
        jbtnLogin = new JButton("Ȯ��");
        jtfID = new JTextField();
        jtfPassword = new TextField();

        jbtnLogin.setFont(new Font("���� ���", 5, 20));
        jlb.setFont(new Font("���� ���", 5, 23));

        jlb.setBounds(150, 130, 300, 50);
        jbtnLogin.setBounds(350, 180, 100, 70);
        jtfID.setBounds(150, 180, 180, 35);
        jtfPassword.setBounds(150, 220, 180, 35);
        jtfPassword.selectAll();   //��й�ȣ�� �Էµ� ��� ���ڿ� ���ð���
        jtfPassword.setEchoChar('*');

        //�����ӿ� �߰�
        add(jlb);
        add(jtfPassword);
        add(jtfID);
        add(jbtnLogin);

        //�̺�Ʈ ����
        LoginViewEvt lve = new LoginViewEvt(this);//�α��� �̺�Ʈ �߰�
        jbtnLogin.addActionListener(lve);//�α��� ��ư �̺�Ʈ �߰�

        setTitle("�α���");
        setLayout(null);
        setSize(600, 500);
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }//LoginView

    //�α��� ������ Ȯ���ϱ� ���� getter method//
    public JTextField getJtfid() {
        return jtfID;
    }//getjtfID

    public TextField getJtfp() {
        return jtfPassword;
    }//getjtfp

}//class
