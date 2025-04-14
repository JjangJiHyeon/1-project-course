package professor;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Reader;

import javax.swing.*;
import javax.swing.border.Border;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import common.LoginFrame;
import vo.ProfSubVO;
import vo.ProfVO;

public class ProfFrame extends JFrame {
	
	SetExam setExamPanel;
	SetStudentExam setStudentExamPanel;
	
	ProfVO pvo;
	ProfSubVO psvo;
	
	public SqlSessionFactory factory;
	
    public JPanel leftPanel, cardPanel,north_p; // 왼쪽 패널과 카드 패널
    private JButton jButton1, jButton2;
    private CardLayout cardLayout; // 카드 레이아웃
    
    Font font = new Font("맑은 고딕", Font.BOLD, 12);
    
    // 생성자
    public ProfFrame(ProfVO pvo) {
    	this.pvo = pvo;
    	north_p = new JPanel();
    	Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image img = toolkit.getImage("src/images/icon_univ.png");
		this.setIconImage(img);
		this.setTitle("학생 관리 프로그램 - 강사");
		
		this.setLocation(500, 250);

		// DB 연결
        dbConnect();
        
        // 과목 정보 가져오기
        selectProfSub();
    	
        leftPanel = new JPanel();
        cardPanel = new JPanel();
        cardLayout = new CardLayout(); // 카드 레이아웃 초기화
        
        // 버튼 생성
        jButton1 = new JButton("시험 관리");
        jButton2 = new JButton("학생별 시험 점수");
        
        // 버튼 및 색상 설정
        jButton1.setFont(font);
        jButton1.setBackground(new Color(70, 130, 180));
        jButton1.setForeground(Color.WHITE);
        jButton2.setFont(font);
        jButton2.setBackground(new Color(70, 130, 180));
        jButton2.setForeground(Color.WHITE);
        
        // 로그아웃
        JButton jButton3 = new JButton("로그아웃");
        jButton3.setFont(font);
        jButton3.setBackground(new Color(255, 69, 58));
        jButton3.setForeground(Color.WHITE);
		        
        // 왼쪽 패널 설정
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.add(jButton1);
        leftPanel.add(Box.createVerticalStrut(10));
        leftPanel.add(jButton2);

        // 아래쪽에 공간을 추가하기 위해 VerticalGlue 사용
        leftPanel.add(Box.createVerticalGlue()); 
        leftPanel.add(jButton3);
        leftPanel.add(Box.createVerticalStrut(10)); // 아래 여백 추가

        // 패널의 외곽 여백 설정
        leftPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // 좌측 패널 배경색 설정
        leftPanel.setBackground(Color.WHITE);
        
        // 좌측 패널 여백과 경계선 설정
        Border matteBorder = BorderFactory.createMatteBorder(0, 0, 0, 2, new Color(70, 130, 180)); // 경계선
        Border emptyBorder = BorderFactory.createEmptyBorder(10, 10, 10, 10); // 오른쪽 여백
        leftPanel.setBorder(BorderFactory.createCompoundBorder(matteBorder, emptyBorder));
         
        // 카드 레이아웃에 사용될 패널 생성
        setExamPanel = new SetExam(this, factory);
        setStudentExamPanel = new SetStudentExam(this, factory);
        
        // 각 카드 패널 배경색 설정
        setExamPanel.setBackground(Color.WHITE);
        setExamPanel.setOpaque(true);

        setStudentExamPanel.setBackground(Color.WHITE);
        setStudentExamPanel.setOpaque(true);
        
        // 카드 레이아웃 설정
        cardPanel.setLayout(cardLayout);
        cardPanel.add(setExamPanel, "Exam"); // 시험 관리 패널
        cardPanel.add(setStudentExamPanel, "StudentExam"); // 학생별 시험 점수 패널
        
        // 버튼 클릭 시 카드 레이아웃 변경
        jButton1.addActionListener(e -> cardLayout.show(cardPanel, "Exam"));
        jButton2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// 학생별 시험 조회 갱신
				setStudentExamPanel.refreshStuExam();
				
				// 레이아웃 변경
				cardLayout.show(cardPanel, "StudentExam");
			}
			
		});
        
        // 메인 프레임 레이아웃 설정
        setLayout(new BorderLayout());
        add(leftPanel, BorderLayout.WEST); // 왼쪽 패널
        add(cardPanel, BorderLayout.CENTER); // 카드 패널
        
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 종료 시 애플리케이션 종료
        this.setPreferredSize(new Dimension(980, 680)); // 프레임 크기 설정
        
        pack(); // 레이아웃을 자동으로 조정
        
        setVisible(true);
        
        // 로그아웃
        jButton3.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ImageIcon image = new ImageIcon("src/images/warning-sign.png");
				Image img = image.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
				ImageIcon icon = new ImageIcon(img);
				int yes = JOptionPane.showConfirmDialog(ProfFrame.this, "로그아웃 하시겠습니까?", "확인", JOptionPane.OK_OPTION, JOptionPane.WARNING_MESSAGE, icon);
				
				if (yes == JOptionPane.YES_OPTION) {
					new LoginFrame(factory).setVisible(true);
					ProfFrame.this.dispose();
				}
			}
			
		});
    }
    
 	// DB 연결
    private void dbConnect() {
        try {
        	Reader r = Resources.getResourceAsReader("config/config.xml");
			SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
			factory = builder.build(r);
			r.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    // 과목 정보 가져오기
    private void selectProfSub() {
    	SqlSession ss = factory.openSession();
    	
    	try {
    		psvo = ss.selectOne("prof.select_prof_sub", pvo.getProf_no());
    		
    		if (psvo == null) {
    			ImageIcon image = new ImageIcon("src/images/warning-sign.png");
				Image img = image.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
				ImageIcon icon = new ImageIcon(img);
				JOptionPane.showMessageDialog(ProfFrame.this, "담당 과목이 없습니다.", "확인", JOptionPane.WARNING_MESSAGE, icon);
    		}
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(ProfFrame.this, "강사 정보 조회에 실패했습니다.");
		} finally {
			ss.close();
		}
    }
    
}
