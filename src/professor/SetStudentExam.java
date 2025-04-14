package professor;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import vo.SubStuExVO;

public class SetStudentExam extends JPanel {

	ProfFrame p;
	
	List<SubStuExVO> s_list;
	List<SubStuExVO> uniqueList;

	public SqlSessionFactory factory;

	private JTable jTable1;
	
	private String[] c_name = { "과목 번호", "과목 이름", "학생 번호", "학생 이름", "시험지 번호", "시험지 이름", "총점", "점수", "응시 여부" };
	
	Font font = new Font("맑은 고딕", Font.BOLD, 12);
	Font headerFont = new Font("맑은 고딕", Font.PLAIN, 12);

	public SetStudentExam(ProfFrame p, SqlSessionFactory factory) {
		this.p = p;
		this.factory = factory;
		
		initComponents();
	}

	public void initComponents() {
		JLabel jLabel1 = new JLabel();
		JComboBox<String> jComboBox1 = new JComboBox<>(new String[]{ "학생 번호", "학생 이름", "시험지 이름", "응시 여부" });
		jComboBox1.setBackground(Color.WHITE);
		jComboBox1.setFont(font);
		JTextField jTextField1 = new JTextField();
		jTextField1.setPreferredSize(new java.awt.Dimension(226, jComboBox1.getPreferredSize().height));
		JButton jButton1 = new JButton("검색");
		JScrollPane jScrollPane1 = new JScrollPane();
		jTable1 = new JTable(new DefaultTableModel(c_name, 0));
		jTable1.getTableHeader().setBackground(Color.WHITE);
		jTable1.getTableHeader().setForeground(Color.BLACK);
		jTable1.getTableHeader().setFont(headerFont);
		jTable1.setGridColor(new Color(70, 130, 180));
		jScrollPane1.setViewportView(jTable1);
		jScrollPane1.setBackground(Color.WHITE);
		jScrollPane1.getViewport().setBackground(Color.WHITE);
		
		// 버튼 배경색 설정
		jButton1.setFont(font);
		jButton1.setBackground(new Color(70, 130, 180));
		jButton1.setForeground(Color.WHITE);
		
		// 테이블 비활성화
		jTable1.setDefaultEditor(Object.class, null);

		// Set properties of the components
		if (p.psvo == null) {
			jLabel1.setText(String.format("시험 정보"));
		} else {
			jLabel1.setText(String.format("%s 시험 정보", p.psvo.getSub_name()));	
		}		
		
		jLabel1.setFont(new Font("맑은 고딕", Font.BOLD, 25));
		jLabel1.setForeground(new Color(70, 130, 180));

		// Set layout and add components
		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
		this.setLayout(layout);
		
		// 배경색 설정
        this.setBackground(Color.WHITE);
		        
        layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        	    .addGroup(layout.createSequentialGroup()
        	        .addContainerGap(65, Short.MAX_VALUE)
        	        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER) // 변경된 부분
        	            .addComponent(jLabel1)
        	            .addGroup(layout.createSequentialGroup()
        	                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 127,
        	                    javax.swing.GroupLayout.PREFERRED_SIZE)
        	                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        	                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 226,
        	                    javax.swing.GroupLayout.PREFERRED_SIZE)
        	                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        	                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 87,
        	                    javax.swing.GroupLayout.PREFERRED_SIZE))
        	            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 700,
        	                javax.swing.GroupLayout.PREFERRED_SIZE))
        	        .addContainerGap(65, Short.MAX_VALUE)));

        	layout.setVerticalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        	    .addGroup(layout.createSequentialGroup()
        	        .addGap(19, 19, 19)
        	        .addComponent(jLabel1) // 상단에서의 위치는 유지
        	        .addGap(18, 18, 18)
        	        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
        	            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE,
        	                javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        	            .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE,
        	                javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        	            .addComponent(jButton1))
        	        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        	        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 520,
        	            javax.swing.GroupLayout.PREFERRED_SIZE)
        	        .addContainerGap(77, Short.MAX_VALUE)));
		
		// 학생 목록
		selectStudent();
		
		// 검색어
		jTextField1.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					if (jComboBox1.getSelectedIndex() == 0) {
						// 학생 번호 검색
						searchStudentByStuNo(jTextField1.getText().trim());
					} else if (jComboBox1.getSelectedIndex() == 1) {
						// 학생 이름 검색
						searchStudentByName(jTextField1.getText().trim());
					} else if (jComboBox1.getSelectedIndex() == 2) {
						// 시험지 이름 검색
						searchStudentByExfName(jTextField1.getText().trim());
					} else {
						// 응시 여부 검색
						searchStudentByStatus(jTextField1.getText().trim());
					}
				}
			}
			
		});
		
		// 검색 버튼
		jButton1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (jComboBox1.getSelectedIndex() == 0) {
					// 학생 번호 검색
					searchStudentByStuNo(jTextField1.getText().trim());
				} else if (jComboBox1.getSelectedIndex() == 1) {
					// 학생 이름 검색
					searchStudentByName(jTextField1.getText().trim());
				} else if (jComboBox1.getSelectedIndex() == 2) {
					// 시험지 이름 검색
					searchStudentByExfName(jTextField1.getText().trim());
				} else {
					// 응시 여부 검색
					searchStudentByStatus(jTextField1.getText().trim());
				}
			}
			
		});
		
		// 테이블 선택
		jTable1.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					int row = jTable1.getSelectedRow();
					
					SubStuExVO evo = uniqueList.get(row);
					
					if (evo.getStatus().equals("미응시")) {
						JOptionPane.showMessageDialog(SetStudentExam.this, "미응시자로 상세 보기가 불가능합니다.");
						return;
					}
					
					String stu_no = evo.getStu_no();
					String exf_no = evo.getExf_no();
					
					new DetailsStuExDialog(SetStudentExam.this, factory, stu_no, exf_no);
				}
			}
		
		});
	}

	// 해당 과목의 학생 목록 가져오기
	private void selectStudent() {
		SqlSession ss = factory.openSession();
		
		if (p.psvo != null) {
			try {
				s_list = ss.selectList("prof.select_stu_by_sub", p.psvo.getSub_no());
				
		        String[][] data = makeStuArray();
				jTable1.setModel(new DefaultTableModel(data, c_name));
			} catch (Exception e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(SetStudentExam.this, "학생 점수 조회에 실패했습니다.");
			} finally {
				ss.close();
			}
		}
	}
	
	// 2차원 배열
	private String[][] makeStuArray() {
		String[][] ar = null;

	    if (s_list.size() > 0) {
	        // 중복된 항목을 제거할 Set
	        HashSet<String> uniqueStrings = new HashSet<>();
	        uniqueList = new ArrayList<>();

	        // 중복을 제거하면서 고유한 항목만 리스트에 추가
	        for (SubStuExVO vo : s_list) {
	            String uniqueKey = vo.getStu_no() + "_" + vo.getExf_no(); // 학생 번호 + 시험지 번호 조합

	            if (!uniqueStrings.contains(uniqueKey)) {
	                uniqueStrings.add(uniqueKey);
	                uniqueList.add(vo);
	            }
	        }

	        // 중복이 제거된 uniqueList로 2차원 배열 생성
	        ar = new String[uniqueList.size()][c_name.length];

	        // 학생별 점수 합산 (es_correct가 true일 때만 합산)
	        HashMap<String, Integer> studentTotalScores = new HashMap<>();

	        for (SubStuExVO score : s_list) {
	            // "응시"인 항목만 점수 합산
	            if ("1".equals(score.getEs_correct()) && "응시".equals(score.getStatus())) {
	                String studentExfKey = score.getStu_no() + "_" + score.getExf_no(); // 학생 번호 + 시험지 번호

	                // 기존 점수에 추가
	                studentTotalScores.put(studentExfKey, studentTotalScores.getOrDefault(studentExfKey, 0) + Integer.parseInt(score.getQ_score()));
	            }
	        }

	        // uniqueList를 이용해 2차원 배열에 데이터 추가
	        int i = 0;
	        for (SubStuExVO vo : uniqueList) {
	            ar[i][0] = vo.getSub_no();
	            ar[i][1] = vo.getSub_name();
	            ar[i][2] = vo.getStu_no();
	            ar[i][3] = vo.getStu_name();
	            ar[i][4] = vo.getExf_no();
	            ar[i][5] = vo.getExf_name();
	            ar[i][6] = vo.getScore();

	            // 학생 총 점수를 추가 (이미 합산한 studentTotalScores에서 가져옴)
	            String studentExfKey = vo.getStu_no() + "_" + vo.getExf_no();
	            ar[i][7] = String.valueOf(studentTotalScores.getOrDefault(studentExfKey, 0)); // 총 점수

	            ar[i][8] = vo.getStatus();

	            i++;
	        }
	    }

	    return ar;
	}
	
	// 학생 번호 검색
	private void searchStudentByStuNo(String str) {
		SqlSession ss = factory.openSession();
		
		try {
			HashMap<String, String> map = new HashMap<>();
			map.put("sub_no", p.psvo.getSub_no());
			map.put("stu_no", str);
	 		
			s_list = ss.selectList("prof.search_stu_by_stu_no", map);
			
			String[][] data = makeStuArray();
			jTable1.setModel(new DefaultTableModel(data, c_name));
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(SetStudentExam.this, "학생 번호 검색에 실패했습니다.");
		} finally {
			ss.close();
		}
	}
	
	// 학생 이름 검색
	private void searchStudentByName(String str) {
		SqlSession ss = factory.openSession();
		
		try {
			HashMap<String, String> map = new HashMap<>();
			map.put("sub_no", p.psvo.getSub_no());
			map.put("stu_name", str);
	 		
			s_list = ss.selectList("prof.search_stu_by_stu_name", map);
			
			String[][] data = makeStuArray();
			jTable1.setModel(new DefaultTableModel(data, c_name));
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(SetStudentExam.this, "학생 이름 검색에 실패했습니다.");
		} finally {
			ss.close();
		}
	}
	
	// 시험지 이름 검색
	private void searchStudentByExfName(String str) {
		SqlSession ss = factory.openSession();
		
		try {
			HashMap<String, String> map = new HashMap<>();
			map.put("sub_no", p.psvo.getSub_no());
			map.put("exf_name", str);
	 		
			s_list = ss.selectList("prof.search_stu_by_exf_name", map);
			
			String[][] data = makeStuArray();
			jTable1.setModel(new DefaultTableModel(data, c_name));
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(SetStudentExam.this, "시험지 이름 검색에 실패했습니다.");
		} finally {
			ss.close();
		}
	}
	
	// 응시 여부 검색
	private void searchStudentByStatus(String str) {
		SqlSession ss = factory.openSession();
		
		try {
			HashMap<String, String> map = new HashMap<>();
			
			map.put("sub_no", p.psvo.getSub_no());
			map.put("status", str);
			
			s_list = ss.selectList("prof.search_stu_by_status", map);
			
			String[][] data = makeStuArray();
			jTable1.setModel(new DefaultTableModel(data, c_name));
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(SetStudentExam.this, "응시 여부 검색에 실패했습니다.");
		} finally {
			ss.close();
		}
	}
	
	// 갱신
	public void refreshStuExam() {
		selectStudent();
	}
	
}