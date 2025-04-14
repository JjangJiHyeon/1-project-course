package student;

import java.awt.Image;
import java.awt.Toolkit;
import java.io.IOException;
import java.io.Reader;
import java.util.List;

import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import vo.CorrectVO;
import vo.ExFrameVO;
import vo.ExamVO;
import vo.QuestVO;
import vo.StuAnsVO;
import vo.StuVO;

public class CorrectExam extends JDialog {
	// 학생 관련 정보를 불러오기 위한 코드
	StuVO s;
	CorrectTable parent;
	private JScrollPane jScrollPane1;
	private JTable jTable1;
	String[][] ar;
	String[] columns = { "시험번호", "문제번호", "정답", "입력한 값", "정답여부", "배점" };
	String exf_no;

	// 생성자에서 StuVO 객체를 받도록 수정
	public CorrectExam(CorrectTable parent, String exf_no) {
		this.parent = parent;
		this.s = parent.parent.s; // s가 null일 수 있으므로 적절히 초기화하는지 확인 필요
		this.exf_no = exf_no;
		
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image img = toolkit.getImage("src/images/icon_univ.png");
		this.setIconImage(img);
		this.setTitle("정오표");
		
		// 학생 관련 정보를 불러오기 위한 코드
		initComponents();
		this.setVisible(true); // Dialog를 보이게 설정
	}

	@SuppressWarnings("unchecked")
	private void initComponents() {
		SqlSessionFactory factory = null;

		ar = null;
		int i = 0;

		jScrollPane1 = new JScrollPane();
		jScrollPane1.setPreferredSize(new java.awt.Dimension(500, 300));
		jTable1 = new JTable();

		setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

		// 테이블 모델 초기화 전에 데이터를 받아와야 한다.
		try {
			Reader r = Resources.getResourceAsReader("config/config.xml");
			SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
			factory = builder.build(r);
			r.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		SqlSession ss = factory.openSession();
		List<StuAnsVO> sa_list = ss.selectList("student.get_stuans_all", s.getStu_no());
		ar = new String[sa_list.size()+1][columns.length];
		int score = 0;
		String exf_score = "0";
		for (StuAnsVO savo : sa_list) {
			
			List<QuestVO> q_list = ss.selectList("student.get_quest_list", exf_no);
			
			if (!sa_list.isEmpty()) {
				for (QuestVO qvo : q_list) {
					if (savo.getQ_no().equals(qvo.getQ_no()) && savo.getExf_no().equals(exf_no)) {
						ar[i][0] = savo.getExf_no(); // 시험 번호
						ar[i][1] = savo.getQ_no(); // 문제번호
						ar[i][2] = qvo.getQ_answer(); // 정답
					
						ar[i][3] = savo.getEs_input(); // 학생이 입력한 값
						if(savo.getEs_correct().equals("1")) {
							ar[i][4] = "O"; // 정답 여부
							score += Integer.parseInt(qvo.getQ_score());
						}
						else
							ar[i][4] = "X"; // 정답 여부
						ar[i][5] = qvo.getQ_score();// 배점
						i++;
						
						exf_score = qvo.getScore();
					}
				}
			}
			
		}
		
		ar[i][columns.length-2]= "총점: ";
		ar[i][columns.length-1]= String.format("%s / %s", String.valueOf(score), exf_score);
	

		// list가 비어 있지 않으면 데이터를 테이블에 삽입

		ss.close();

		// 데이터와 컬럼을 설정한 후에 테이블 모델을 설정
		if (ar != null && ar.length > 0) {
			DefaultTableModel model = new DefaultTableModel(ar, columns);
			jTable1.setModel(model);
		} else {
			System.out.println("테이블 데이터가 비어 있습니다.");
		}
		System.out.println("테이블 데이터:");
		
		// 테이블 비활성화
		jTable1.setDefaultEditor(Object.class, null);
		
		jScrollPane1.setViewportView(jTable1);
		
		this.add(jScrollPane1);
		this.setSize(600, 400); // Dialog 크기 설정
		this.setLocationRelativeTo(null); // 화면 중앙에 위치
	}

}
