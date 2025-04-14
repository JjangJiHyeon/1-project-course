package professor;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import vo.ExFrameVO;
import vo.ExamVO;
import vo.QuestVO;

import java.util.HashMap;
import java.util.List;

public class SetExam extends JPanel {
	
	public ProfFrame p;
	
    public JTable jTable1, jTable2, jTable3; // 테이블
    
    public SqlSessionFactory factory;
    
    public List<ExFrameVO> ef_list; // 시험지 리스트
    public List<ExamVO> e_list; // 시험 리스트

    String[] c_name1 = { "시험지 번호", "시험지 이름", "강사 번호", "과목 번호", "점수" }; // 시험지 테이블 컬럼명
    String[] c_name2 = { "문제 번호", "문제", "정답", "점수", "1번", "2번", "3번", "4번" }; // 문제 테이블 컬럼명
    String[] c_name3 = { "시험지 번호", "문제 번호" }; // 시험 테이블 컬럼명
    
    Font font = new Font("맑은 고딕", Font.BOLD, 12);
    Font headerFont = new Font("맑은 고딕", Font.PLAIN, 12);
    
    // 생성자
    public SetExam(ProfFrame p, SqlSessionFactory factory) {
        this.p = p;
        this.factory = factory;
        
        initComponents();
    }
    
    private void initComponents() {
        // Main Layout 설정
        this.setLayout(new BorderLayout());

        // 상단 검색 패널
        JPanel topPanel = new JPanel(new BorderLayout(10, 0)); // 10px의 가로 간격
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // 여백 추가

        JComboBox<String> comboBox = new JComboBox<>(new String[]{ "시험지 번호", "시험지 이름" });
        comboBox.setPreferredSize(new Dimension(150, comboBox.getPreferredSize().height));
        comboBox.setBackground(Color.WHITE);
        comboBox.setFont(font);
        JTextField textField = new JTextField(20);
        JButton searchButton = new JButton("검색");
        
        searchButton.setFont(font);
        searchButton.setBackground(new Color(70, 130, 180));
        searchButton.setForeground(Color.WHITE);

        topPanel.add(comboBox, BorderLayout.WEST);
        topPanel.add(textField, BorderLayout.CENTER);
        topPanel.add(searchButton, BorderLayout.EAST);

        // 테이블 패널 (왼쪽)
        JPanel tablePanel = new JPanel();
        tablePanel.setLayout(new BoxLayout(tablePanel, BoxLayout.Y_AXIS));
        tablePanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // 시험지 테이블
        jTable1 = new JTable(new DefaultTableModel(c_name1, 0));
        jTable1.getTableHeader().setBackground(Color.WHITE);
        jTable1.getTableHeader().setForeground(Color.BLACK);
        jTable1.getTableHeader().setFont(headerFont);
        jTable1.setGridColor(new Color(70, 130, 180));
        JScrollPane jScrollPane1 = new JScrollPane(jTable1);
        jScrollPane1.setBackground(Color.WHITE);
        jScrollPane1.getViewport().setBackground(Color.WHITE);
        JPanel examFramePanel = new JPanel(new BorderLayout());
        TitledBorder border1 = BorderFactory.createTitledBorder("시험지 목록"); // TitledBorder 생성
        border1.setTitleFont(font); // 폰트 설정
        examFramePanel.setBorder(border1); // TitledBorder 적용
        examFramePanel.add(jScrollPane1, BorderLayout.CENTER);
        
        // 문제 테이블
        jTable2 = new JTable(new DefaultTableModel(c_name2, 0));
        jTable2.getTableHeader().setBackground(Color.WHITE);
        jTable2.getTableHeader().setForeground(Color.BLACK);
        jTable2.getTableHeader().setFont(headerFont);
        jTable2.setGridColor(new Color(70, 130, 180));
        JScrollPane jScrollPane2 = new JScrollPane(jTable2);
        jScrollPane2.setBackground(Color.WHITE);
        jScrollPane2.getViewport().setBackground(Color.WHITE);
        JPanel questionPanel = new JPanel(new BorderLayout());
        TitledBorder border2 = BorderFactory.createTitledBorder("문제 목록"); // TitledBorder 생성
        border2.setTitleFont(font); // 폰트 설정
        questionPanel.setBorder(border2); // TitledBorder 적용
        questionPanel.add(jScrollPane2, BorderLayout.CENTER);
        
        // 테이블 배경색 설정
        jTable1.setBackground(Color.WHITE);
        jTable2.setBackground(Color.WHITE);
        
        // 테이블 비활성화
        jTable1.setDefaultEditor(Object.class, null);
        jTable2.setDefaultEditor(Object.class, null);

        // 테이블 패널에 추가
        tablePanel.add(examFramePanel);
        tablePanel.add(questionPanel);

        // 오른쪽 버튼 패널 (고정된 너비)
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));
        rightPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        rightPanel.setPreferredSize(new Dimension(150, 0)); // 고정된 너비 설정

        JButton jButton4 = new JButton("문제");
        JButton jButton5 = new JButton("시험");

        jButton4.setAlignmentX(Component.CENTER_ALIGNMENT); // 버튼 가운데 정렬
        jButton5.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        jButton4.setFont(font);
        jButton4.setBackground(new Color(70, 130, 180));
        jButton4.setForeground(Color.WHITE);
        jButton5.setFont(font);
        jButton5.setBackground(new Color(70, 130, 180));
        jButton5.setForeground(Color.WHITE);        

        rightPanel.add(Box.createVerticalGlue()); // 상단 여백
        rightPanel.add(jButton4);
        rightPanel.add(Box.createVerticalStrut(10)); // 버튼 간 간격
        rightPanel.add(jButton5);
        rightPanel.add(Box.createVerticalGlue()); // 하단 여백
        
        // 배경색 설정
        rightPanel.setBackground(Color.WHITE);
        tablePanel.setBackground(Color.WHITE);
        
        // topPanel에 여백 추가
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // 상단 검색 패널 여백
        tablePanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 5, 0)); // 상단 검색 패널 여백

        // 좌/우 나누기
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, tablePanel, rightPanel);
        splitPane.setDividerLocation(700); // 왼쪽 패널 크기 설정
        splitPane.setOneTouchExpandable(true); // 드래그로 크기 조정 가능
        
        // 배경색 설정
        topPanel.setBackground(Color.WHITE);
        splitPane.setBackground(Color.WHITE);

        // 전체 패널 구성
        this.add(topPanel, BorderLayout.NORTH); // 검색 패널 추가
        this.add(splitPane, BorderLayout.CENTER); // 테이블과 버튼 추가
        
		// 시험지 조회
		this.selectExFrame();
		
		// 문제 Dialog
		jButton4.addActionListener(e -> {
		    new SetQuestDialog(SetExam.this, factory);
		});
		
		// 시험 Dialog
		jButton5.addActionListener(e -> {
		    new SetExamDialog(SetExam.this, factory);
		});
		
		// 검색어
		textField.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					if (comboBox.getSelectedIndex() == 0) {
						// 시험지 번호 검색
						searchExfByExf(textField.getText().trim());
					} else {
						// 시험지 이름 검색
						searchExfByName(textField.getText().trim());
					}
				}
			}			
			
		});
		
		// 검색 버튼
		searchButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (comboBox.getSelectedIndex() == 0) {
					// 시험지 번호 검색
					searchExfByExf(textField.getText().trim());
				} else {
					// 시험지 이름 검색
					searchExfByName(textField.getText().trim());
				}
			}
			
		});
		
		// 테이블 클릭 리스너
		this.jTable1.addMouseListener(new MouseAdapter() {
		    @Override
		    public void mouseClicked(MouseEvent e) {
		        int row = jTable1.getSelectedRow();
		        ExFrameVO vo = ef_list.get(row);
		        String exf_no = vo.getExf_no();
		          
		        selectQuestion(exf_no);
		    }
		});
		
		this.jTable2.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// 더블 클릭
				if (e.getClickCount() == 2) {
					int row = jTable2.getSelectedRow();
					ExamVO evo = e_list.get(row);
					String q_no = evo.getQ_no();
					QuestVO qvo = selectQuestById(q_no);
					
					new DetailsQuestDialog(SetExam.this, qvo);
				}
			}
			
		});
    }
    
    // 시험지 목록 조회
    public void selectExFrame() {
    	SqlSession ss = factory.openSession();
    	
    	try {
    		ef_list = ss.selectList("ex_frame.select_ex_frame_sub", p.psvo.getSub_no());
        	
        	String[][] data = makeExFArray();
        	jTable1.setModel(new DefaultTableModel(data, c_name1));
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(SetExam.this, "시험지 목록 조회에 실패했습니다.");
		} finally {
			ss.close();
		}	
    }

    // 문제 목록 조회
    public void selectQuestion(String exf_no) {
    	SqlSession ss = factory.openSession();
    	
    	try {
    		e_list = ss.selectList("exam.select_exam_by_exf", exf_no);
  		  
        	String[][] data = makeQuestArray();
    		jTable2.setModel(new DefaultTableModel(data, c_name2)); 
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(SetExam.this, "문제 목록 조회에 실패했습니다.");
		} finally {
			ss.close();
		}
    }
    
    // 문제 번호로 문제 정보 조회
    private QuestVO selectQuestById(String q_no) {
    	SqlSession ss = factory.openSession();
    	
    	QuestVO evo = new QuestVO();
    	
    	try {
    		evo = ss.selectOne("question.select_question_by", q_no);
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(SetExam.this, "문제 정보 조회에 실패했습니다.");
		} finally {
			ss.close();
		}
    	
    	return evo;
    }
    
    // 시험지 번호 검색
    private void searchExfByExf(String exf_no) {
    	SqlSession ss = factory.openSession();
    	
    	try {
    		HashMap<String, String> map = new HashMap<>();
    		
    		map.put("sub_no", p.psvo.getSub_no());
    		map.put("exf_no", exf_no);
    		
    		ef_list = ss.selectList("ex_frame.search_ex_frame_by_exf", map);
        	
        	String[][] data = makeExFArray();
        	jTable1.setModel(new DefaultTableModel(data, c_name1));
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(SetExam.this, "시험지 번호 검색에 실패했습니다.");
		} finally {
			ss.close();
		}
    }
    
    // 시험지 이름으로 검색
    private void searchExfByName(String exf_name) {
    	SqlSession ss = factory.openSession();
    	
    	try {
			HashMap<String, String> map = new HashMap<>();
    		
    		map.put("sub_no", p.psvo.getSub_no());
    		map.put("exf_name", exf_name);
    		
    		ef_list = ss.selectList("ex_frame.search_ex_frame_by_name", map);
        	
        	String[][] data = makeExFArray();
        	jTable1.setModel(new DefaultTableModel(data, c_name1));
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(SetExam.this, "시험지 이름 검색에 실패했습니다.");
		} finally {
			ss.close();
		}
    }
    
    // 시험지 데이터 2차원 배열화
    private String[][] makeExFArray() {
    	String[][] ar = null;
    	
    	if (ef_list != null && ef_list.size() > 0) {
    		ar = new String[ef_list.size()][c_name1.length];
    		
    		int i = 0;
    		for (ExFrameVO vo : ef_list) {
    			ar[i][0] = vo.getExf_no();
    			ar[i][1] = vo.getExf_name();
    			ar[i][2] = vo.getProf_no();
    			ar[i][3] = vo.getSub_no();
    			ar[i][4] = vo.getScore();
    			
    			i++;
    		}
    	}
    	
    	return ar;
    }
    
    // 문제 데이터 2차원 배열화
    private String[][] makeQuestArray() {
    	String[][] ar = null;
    	
    	if (e_list != null && e_list.size() > 0) { 
			ar = new String[e_list.size()][c_name2.length];
		  
			int i = 0; 
			for (ExamVO vo : e_list) { 
				List<QuestVO> q_list = vo.getQ_list();
			  
				for (QuestVO qvo : q_list) { 
					ar[i][0] = qvo.getQ_no(); 
					ar[i][1] = qvo.getQ_text();
					ar[i][2] = qvo.getQ_answer(); 
					ar[i][3] = qvo.getQ_score();
					ar[i][4] = qvo.getFirst(); 
					ar[i][5] = qvo.getSecond(); 
					ar[i][6] = qvo.getThird(); 
					ar[i][7] = qvo.getForth();
				  
					i++; 
				} 
			}	 
    	}
    	
    	return ar;
    }
    
}
