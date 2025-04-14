package student;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.*;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import vo.ExFrameVO;
import vo.ExamVO;
import vo.QuestVO;
import vo.StuAnsVO;

public class TakingExamDialog extends JDialog {
	private SetExam parent;
	private List<QuestVO> q_list;
	private List<JPanel> p_list;
	private List<StuAnsVO> ans_list;
	JLabel title;
	JPanel north_p, south_p;
	JButton submit;
	ExFrameVO exfvo;
	SqlSessionFactory factory;

	public TakingExamDialog(SetExam parent, List<QuestVO> q_list, ExFrameVO exfvo) {
		this.factory = parent.factory;
		this.parent = parent;
		this.q_list = q_list;
		this.p_list = new ArrayList<>();
		this.exfvo = exfvo;
		
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image img = toolkit.getImage("src/images/icon_univ.png");
		this.setIconImage(img);
		
		Font buttonFont = new Font("맑은 고딕", Font.BOLD, 16);
		Font titleFont = new Font("맑은 고딕", Font.BOLD, 24);
		ans_list = new ArrayList<>();
		north_p = new JPanel();
		south_p = new JPanel();
		submit = new JButton("제출");
		title = new JLabel(exfvo.getExf_name());
		north_p.setBackground(Color.white);
		south_p.setBackground(Color.white);
		title.setFont(titleFont);
		title.setForeground(new Color(70, 130, 180));
		north_p.add(title);
		south_p.add(submit);

		submit.setFont(buttonFont);
		submit.setBackground(new Color(70, 130, 180));
		submit.setForeground(Color.WHITE);

		// 다이얼로그 기본 설정
		setTitle("시험 보기");
		this.setBounds(300, 50, 1200, 800);
		setLocationRelativeTo(parent.parent);
		this.add(north_p, BorderLayout.NORTH);
		this.add(south_p, BorderLayout.SOUTH);
		// 스크롤 가능한 메인 패널 설정
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS)); // 질문별 패널
		mainPanel.setBorder(BorderFactory.createLineBorder(new Color(70, 130, 180), 1));
		JScrollPane scrollPane = new JScrollPane(mainPanel);

		add(scrollPane);

		// 각 질문 패널 생성

		int i = 1;
		for (QuestVO vo : q_list) {

			StuAnsVO ansvo = new StuAnsVO();
			ansvo.setEx_no(vo.getExam_frame().getExf_no());
			ansvo.setStu_no(parent.parent.s.getStu_no());
			ansvo.setExf_no(exfvo.getExf_no());
			ansvo.setQ_no(vo.getQ_no());
			ans_list.add(ansvo);
			JPanel questionPanel = createQuestionPanel(vo, i);

			p_list.add(questionPanel);

			mainPanel.add(questionPanel);
			i++;
		}
		
		submit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int result = submit();

				System.out.println(result + " / " + p_list.size());
				if (result == p_list.size())
					dispose();
				parent.showList();
			}

		});
		
		this.setVisible(true);
		
		// pack();
	}

	/**
	 * 개별 질문에 대한 패널을 생성
	 */
	private JPanel createQuestionPanel(QuestVO question, int num) {
		// 고정 크기의 패널 생성
		JPanel panel = new JPanel();
		panel.setBackground(Color.white);
		panel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));

		// 문제 텍스트 추가
		JLabel questionLabel = new JLabel(num + "번: " + question.getQ_text());
		questionLabel.setVerticalAlignment(SwingConstants.TOP);
		questionLabel.setHorizontalAlignment(SwingConstants.LEFT);
		questionLabel.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 2)); // 여백을 적게 추가
		questionLabel.setPreferredSize(new Dimension(400, 20)); // 라벨 크기 고정
		questionLabel.setFont(new Font("맑은 고딕", Font.BOLD, 14));

		// 답변 패널 추가 (라디오 버튼)
		JPanel answerPanel = createAnswerPanel(question);
		answerPanel.setPreferredSize(new Dimension(600, 30)); // 답변 영역 크기 고정

		// 패널에 문제와 답변 배치
		panel.add(questionLabel, BorderLayout.NORTH);
		panel.add(answerPanel, BorderLayout.CENTER);

		// 패널 크기 고정
		panel.setPreferredSize(new Dimension(550, 100)); // 패널의 고정된 크기 설정 (문제 + 답변)
		return panel;
	}

	private JPanel createAnswerPanel(QuestVO q) {
		// 라디오 버튼 그룹 생성
		ButtonGroup group = new ButtonGroup();
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5)); // 답변 정렬
		panel.setBackground(Color.white);

		// 라디오 버튼 생성
		JRadioButton r1 = new JRadioButton(q.getFirst());
		JRadioButton r2 = new JRadioButton(q.getSecond());
		JRadioButton r3 = new JRadioButton(q.getThird());
		JRadioButton r4 = new JRadioButton(q.getForth());
		r1.setBackground(Color.white);
		r2.setBackground(Color.white);
		r3.setBackground(Color.white);
		r4.setBackground(Color.white);
		r1.setActionCommand("1");
		r2.setActionCommand("2");
		r3.setActionCommand("3");
		r4.setActionCommand("4");
		r1.setFont(new Font("맑은 고딕", Font.BOLD, 13));
		r2.setFont(new Font("맑은 고딕", Font.BOLD, 13));
		r3.setFont(new Font("맑은 고딕", Font.BOLD, 13));
		r4.setFont(new Font("맑은 고딕", Font.BOLD, 13));
		group.add(r1);
		panel.add(r1);
		group.add(r2);
		panel.add(r2);
		group.add(r3);
		panel.add(r3);
		group.add(r4);
		panel.add(r4);
		panel.putClientProperty("group", group);
		return panel;
	}

	private int submit() {
		int i = 0;
		int chk = 0;
		SqlSession ss = parent.factory.openSession();

		for (JPanel p : p_list) {
			StuAnsVO ansvo = ans_list.get(i);

			// 패널에서 ButtonGroup 읽기
			JPanel answerPanel = (JPanel) p.getComponent(1); // 두 번째 컴포넌트가 답변 패널
			ButtonGroup group = (ButtonGroup) answerPanel.getClientProperty("group");

			if (group.getSelection() != null) {
				String selectedAnswer = group.getSelection().getActionCommand();
				ansvo.setEs_input(selectedAnswer);

				// 정답 체크
				if (selectedAnswer.equals(q_list.get(i).getQ_answer())) {
					ansvo.setEs_correct("1");

				} else {
					ansvo.setEs_correct("0");
				}

				// 정답 처리 후 데이터베이스 삽입
				Map<String, Object> params = new HashMap<>();
				params.put("ex_no", ansvo.getEx_no());
				params.put("q_no", ansvo.getQ_no());
				params.put("es_input", ansvo.getEs_input());
				params.put("es_correct", ansvo.getEs_correct());
				params.put("stu_no", ansvo.getStu_no());
				params.put("exf_no", ansvo.getExf_no());

				ss.insert("student.insert_stuans", params);
				int a = ss.update("student.update_status", ansvo.getEx_no());
				System.out.println("status update: "+ a);
				chk++;
			}

			i++;
		}

		// 문항 체크 여부에 따라 처리
		if (i != chk) {
			JOptionPane.showMessageDialog(null, "아직 풀지 않은 문항이 있습니다.");
			ss.rollback();
		} else {
			ss.commit();
		}
		ss.close();
		return chk;
	}

}