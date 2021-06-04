package LogAnalysis;

import java.awt.Font;
import java.awt.TextField;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 * 아이디와 비밀번호를 입력해서 로그인을 하는 로그인창의 디자인 클래스
 * @author Team#1
 */
@SuppressWarnings("serial")
public class LoginView extends JFrame {
    //로그인 창에서의 구성요소
    private JButton jbtnLogin; //로그인 버튼
    private JTextField jtfID; //아이디 입력 텍스트 필드
    private TextField jtfPassword; //비밀번호 입력 텍스트 필드

    public LoginView() {//로그인창 디자인 클래스 & 로그인창에서의 이벤트 연결
        //버튼, 라벨, 텍스트 필드 디자인 정의
        JLabel jlb = new JLabel("로그인 정보를 입력해주세요");
        jbtnLogin = new JButton("확인");
        jtfID = new JTextField();
        jtfPassword = new TextField();

        jbtnLogin.setFont(new Font("맑은 고딕", 5, 20));
        jlb.setFont(new Font("맑은 고딕", 5, 23));

        jlb.setBounds(150, 130, 300, 50);
        jbtnLogin.setBounds(350, 180, 100, 70);
        jtfID.setBounds(150, 180, 180, 35);
        jtfPassword.setBounds(150, 220, 180, 35);
        jtfPassword.selectAll();   //비밀번호의 입력된 모든 문자열 선택가능
        jtfPassword.setEchoChar('*');

        //프레임에 추가
        add(jlb);
        add(jtfPassword);
        add(jtfID);
        add(jbtnLogin);

        //이벤트 연결
        LoginViewEvt lve = new LoginViewEvt(this);//로그인 이벤트 추가
        jbtnLogin.addActionListener(lve);//로그인 버튼 이벤트 추가

        setTitle("로그인");
        setLayout(null);
        setSize(600, 500);
        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }//LoginView

    //로그인 정보를 확인하기 위한 getter method//
    public JTextField getJtfid() {
        return jtfID;
    }//getjtfID

    public TextField getJtfp() {
        return jtfPassword;
    }//getjtfp

}//class
