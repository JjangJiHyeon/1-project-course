package professor;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import vo.ExFrameVO;
import vo.ExamVO;
import vo.QuestVO;

public class SetExamDialog extends javax.swing.JDialog {

	SetExam parent;
	private SqlSessionFactory factory;
	
	List<ExamVO> e_list;
	List<ExFrameVO> f_list;
	List<QuestVO> q_list;
	
	private int total_score;
	
	JComboBox<String> jComboBoxModEx; // 시험 번호 - 수정, 삭제
	JComboBox<String> jComboBoxModExf, jComboBoxDelExf; // 시험지 번호 - 추가, 수정
	DefaultListModel<JCheckBox> addModel, modModel; // 문제 번호 - 추가, 수정
	JList<JCheckBox> jAddList, jModList; // 문제 목록 - 추가, 수정
	JTextArea jTextAreaModLegacyQuest; // 문제 목록 - 기존 문제 목록(수정)
	JTextArea jTextAreaAddQuest, jTextAreaModQuest; // 문제 목록 - 추가, 삭제
	
	Font font = new Font("맑은 고딕", Font.BOLD, 12);

	public SetExamDialog(SetExam parent, SqlSessionFactory factory) {
		this.parent = parent;
		this.factory = factory;
		this.total_score = 0;
		
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image img = toolkit.getImage("src/images/icon_univ.png");
		this.setIconImage(img);
		this.setTitle("시험 관리");
		
	    setSize(350, 580);
	    setLocationRelativeTo(parent);

	    JTabbedPane tabbedPane = new JTabbedPane();
	    tabbedPane.setBackground(Color.WHITE);

	    JPanel panel1 = new JPanel();
	    panel1.setBackground(Color.WHITE);
	    initAddComponents(panel1);
	    tabbedPane.addTab("추가", panel1);

	    JPanel panel2 = new JPanel();
	    panel2.setBackground(Color.WHITE);
	    initModComponents(panel2);
	    tabbedPane.addTab("수정", panel2);

	    JPanel panel3 = new JPanel();
	    panel3.setBackground(Color.WHITE);
	    initDelComponents(panel3);
	    tabbedPane.addTab("삭제", panel3);

	    // JDialog에 JTabbedPane 추가
        add(tabbedPane);
        
        // JDialog 표시
        setLocationRelativeTo(parent); // 부모 프레임에 상대적으로 위치 설정
        setVisible(true);
	}
    
    // 추가
    private void initAddComponents(JPanel panel1) {
    	JLabel jLabel1 = new JLabel("시험지 이름");
        JTextField jTextField1 = new JTextField();
        JLabel jLabel3 = new JLabel("문제 번호 목록");

        // JList에 체크박스를 추가하기 위해 DefaultListModel 사용
        addModel = new DefaultListModel<>();

        jAddList = new JList<>(addModel);
        JScrollPane jScrollPane1 = new JScrollPane(jAddList);
        JLabel jLabel4 = new JLabel("선택한 문제 번호");
        jTextAreaAddQuest = new JTextArea(5, 20);
        jTextAreaAddQuest.setEditable(false);
        JScrollPane jScrollPane2 = new JScrollPane(jTextAreaAddQuest);
        JButton jButton1 = new JButton("초기화");
        JButton jButton2 = new JButton("추가");
        JButton jButton3 = new JButton("취소");
        
        // 버튼 및 색상 설정
        jButton1.setFont(font);
        jButton1.setBackground(new Color(70, 130, 180));
        jButton1.setForeground(Color.WHITE);
        jButton2.setFont(font);
        jButton2.setBackground(new Color(70, 130, 180));
        jButton2.setForeground(Color.WHITE);
        jButton3.setFont(font);
        jButton3.setBackground(new Color(169,169,169));
        jButton3.setForeground(Color.WHITE);
        
        jAddList.setCellRenderer((list, value, index, isSelected, cellHasFocus) -> {
            value.setBackground(isSelected ? list.getSelectionBackground() : list.getBackground());
            value.setForeground(isSelected ? list.getSelectionForeground() : list.getForeground());
            value.setEnabled(list.isEnabled());
            value.setFont(list.getFont());
            value.setFocusPainted(false);
            value.setBorderPainted(true);
            return value;
        });

        GroupLayout layout = new GroupLayout(panel1);
        panel1.setLayout(layout);
        
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addGap(48, 48, 48)
                            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel4)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(jLabel3)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jButton1))
                                .addGroup(layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel1))
                                    .addGap(18, 18, 18)
                                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jTextField1, GroupLayout.DEFAULT_SIZE, 163, Short.MAX_VALUE)))
                                .addComponent(jScrollPane1)
                                .addComponent(jScrollPane2, GroupLayout.DEFAULT_SIZE, 245, Short.MAX_VALUE)))
                        .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(jButton2)
                            .addGap(18, 18, 18)
                            .addComponent(jButton3)))
                    .addContainerGap(57, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(47, 47, 47)
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1)
                        .addComponent(jTextField1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                    .addGap(24, 24, 24)
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel3)
                        .addComponent(jButton1))
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 98, GroupLayout.PREFERRED_SIZE)
                    .addGap(31, 31, 31)
                    .addComponent(jLabel4)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jScrollPane2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 115, Short.MAX_VALUE)
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton2)
                        .addComponent(jButton3))
                    .addGap(23, 23, 23))
        );
        
        // 문제 목록
        selectQuestion("add");
        
        // 문제 선택
        jAddList.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mousePressed(java.awt.event.MouseEvent evt) {
            	// 클릭한 인덱스 확인
            	int index = jAddList.locationToIndex(evt.getPoint());
            	
            	addQuestionArea(index);
            }
        });
        
        // 초기화
        jButton1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {

				// addModel에 있는 모든 JCheckBox의 선택 상태를 false로 설정
			    for (int i = 0; i < addModel.size(); i++) {
			        JCheckBox checkBox = addModel.getElementAt(i); // 모델에서 체크박스 가져오기
			        checkBox.setSelected(false); // 선택 상태를 false로 설정
			    }
			    jAddList.repaint(); // JList를 다시 렌더링하여 UI 갱신
		        
		        jTextAreaAddQuest.setText("");
		        
		        total_score = 0;
			}
			
		});
        
        // 추가
        jButton2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (!(jTextField1.getText().trim().length() > 0)) {
					JOptionPane.showMessageDialog(SetExamDialog.this, "시험지 이름을 입력하세요.");
					return;
				}
				
				if (!(jTextAreaAddQuest.getText().trim().length() > 0)) {
					JOptionPane.showMessageDialog(SetExamDialog.this, "문제를 추가하세요.");
					return;
				}
				
				ImageIcon image = new ImageIcon("src/images/check-sign.png");
				Image img = image.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
				ImageIcon icon = new ImageIcon(img);
				int cmd = JOptionPane.showConfirmDialog(SetExamDialog.this, "추가하시겠습니까?", "확인", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, icon);
				
				if (cmd == JOptionPane.YES_OPTION) {
					String exf_name = jTextField1.getText().trim();
					
					// 시험지 추가
					String exf_no = insertExamFrame(exf_name);
					
					if (exf_no != null) {
						// 시험 추가
						insertExam(exf_no);
						
						// 시험지 갱신
						refreshExFrame();
					}
				}
			}
			
		});
        
        // 취소
        jButton3.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				closed();
			}
			
		});
    }
    
    // 수정
    private void initModComponents(JPanel panel2) {
    	JLabel jLabel1 = new JLabel("시험지 번호");
    	jComboBoxModExf = new JComboBox<>();
    	jComboBoxModExf.setBackground(Color.WHITE);
	    JLabel jLabel2 = new JLabel("시험지 이름");
	    JTextField jTextField1 = new JTextField();
	    JLabel jLabel4 = new JLabel("기존 문제 번호 목록");
	    JScrollPane jScrollPane1 = new JScrollPane();
	    jTextAreaModLegacyQuest = new JTextArea(5, 5);
	    JLabel jLabel5 = new JLabel("변경 문제 번호 목록");
	    JLabel jLabel6 = new JLabel("선택한 문제 번호");
	    JScrollPane jScrollPane3 = new JScrollPane();
	    jTextAreaModQuest = new JTextArea(5, 5);
	    JButton jButton1 = new JButton("초기화");
	    JButton jButton2 = new JButton("수정");
	    JButton jButton3 = new JButton("취소");
	    
	    // 버튼 및 색상 설정
        jButton1.setFont(font);
        jButton1.setBackground(new Color(70, 130, 180));
        jButton1.setForeground(Color.WHITE);
        jButton2.setFont(font);
        jButton2.setBackground(new Color(70, 130, 180));
        jButton2.setForeground(Color.WHITE);
        jButton3.setFont(font);
        jButton3.setBackground(new Color(169,169,169));
        jButton3.setForeground(Color.WHITE);
	    
	    // JList에 체크박스를 추가하기 위해 DefaultListModel 사용
	    modModel = new DefaultListModel<>();
	    
	    jModList = new JList<>(modModel);
        JScrollPane jScrollPane2 = new JScrollPane(jModList);

        jComboBoxModExf.addItem("선택하세요");
	    
        jTextAreaModLegacyQuest.setEditable(false);
	    jScrollPane1.setViewportView(jTextAreaModLegacyQuest);
	    jTextAreaModQuest.setEditable(false);
	    jScrollPane3.setViewportView(jTextAreaModQuest);
	    
	    jModList.setCellRenderer((list, value, index, isSelected, cellHasFocus) -> {
            value.setBackground(isSelected ? list.getSelectionBackground() : list.getBackground());
            value.setForeground(isSelected ? list.getSelectionForeground() : list.getForeground());
            value.setEnabled(list.isEnabled());
            value.setFont(list.getFont());
            value.setFocusPainted(false);
            value.setBorderPainted(true);
            return value;
        });

	    // 레이아웃 설정
	    GroupLayout layout = new GroupLayout(panel2);
	    panel2.setLayout(layout);
	    
	    layout.setHorizontalGroup(
	        layout.createParallelGroup(GroupLayout.Alignment.LEADING)
	            .addGroup(layout.createSequentialGroup()
	                .addGap(40, 40, 40)
	                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
	                    .addComponent(jScrollPane3, GroupLayout.PREFERRED_SIZE, 240, GroupLayout.PREFERRED_SIZE)
	                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
	                        .addComponent(jLabel6)
	                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
	                            .addGroup(layout.createSequentialGroup()
	                                .addComponent(jLabel5)
	                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
	                                .addComponent(jButton1, GroupLayout.PREFERRED_SIZE, 76, GroupLayout.PREFERRED_SIZE))
	                            .addComponent(jLabel4)
	                            .addGroup(layout.createSequentialGroup()
	                                .addComponent(jLabel1)
	                                .addGap(18, 18, 18)
	                                .addComponent(jComboBoxModExf, GroupLayout.PREFERRED_SIZE, 158, GroupLayout.PREFERRED_SIZE))
	                            .addGroup(layout.createSequentialGroup()
	                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
	                                    .addComponent(jLabel2))
	                                .addGap(18, 18, 18)
	                                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
	                                    .addComponent(jTextField1)))
	                            .addComponent(jScrollPane1)
	                            .addComponent(jScrollPane2))
	                        .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
	                            .addGap(76, 76, 76)
	                            .addComponent(jButton2)
	                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
	                            .addComponent(jButton3))))
	                .addContainerGap(70, Short.MAX_VALUE))
	    );
	    layout.setVerticalGroup(
	        layout.createParallelGroup(GroupLayout.Alignment.LEADING)
	            .addGroup(layout.createSequentialGroup()
	                .addGap(22, 22, 22)
	                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
	                    .addComponent(jLabel1)
	                    .addComponent(jComboBoxModExf, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
	                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
	                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
	                    .addComponent(jLabel2)
	                    .addComponent(jTextField1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
	                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
	                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE))
	                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
	                .addComponent(jLabel4)
	                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
	                    .addGroup(layout.createSequentialGroup()
	                        .addGap(75, 75, 75)
	                        .addComponent(jButton1))
	                    .addGroup(layout.createSequentialGroup()
	                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
	                        .addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 57, GroupLayout.PREFERRED_SIZE)
	                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
	                        .addComponent(jLabel5)))
	                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
	                .addComponent(jScrollPane2, GroupLayout.PREFERRED_SIZE, 96, GroupLayout.PREFERRED_SIZE)
	                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
	                .addComponent(jLabel6)
	                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
	                .addComponent(jScrollPane3, GroupLayout.PREFERRED_SIZE, 65, GroupLayout.PREFERRED_SIZE)
	                .addGap(18, 18, 18)
	                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
	                    .addComponent(jButton2)
	                    .addComponent(jButton3))
	                .addContainerGap(89, Short.MAX_VALUE))
	    );
        
        // 시험지 목록
        selectExamFrame("mod");
        
        // 문제 목록
        selectQuestion("mod");
        
        // 시험지 번호 선택
        jComboBoxModExf.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (jComboBoxModExf.getSelectedIndex() != 0) {
					selectQuesionById();
					ExFrameVO fvo = selectExfById(jComboBoxModExf.getSelectedItem().toString());
					
					if (e_list != null) {
				        StringBuffer sb = new StringBuffer();
				        
				        for (int i = 0; i < e_list.size(); i++) {
				            ExamVO evo = e_list.get(i);
				            sb.append(evo.getQ_no());
				            
				            if (i < e_list.size() - 1) {  // 마지막 항목이 아니면 콤마 추가
				                sb.append(",");
				            }
				        }
				        
				        jTextAreaModLegacyQuest.setText(sb.toString());
				    }
					
					if (fvo != null) {
						jTextField1.setText(fvo.getExf_name());
					}
				}
			}
			
		});
        
        // 문제 선택
        jModList.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mousePressed(java.awt.event.MouseEvent evt) {
            	int index = jModList.locationToIndex(evt.getPoint()); // 클릭한 인덱스 확인
            	modQuestionArea(index);
            }
        });
        
        // 초기화
        jButton1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// modModel에 있는 모든 JCheckBox의 선택 상태를 false로 설정
			    for (int i = 0; i < modModel.size(); i++) {
			        JCheckBox checkBox = modModel.getElementAt(i); // 모델에서 체크박스 가져오기
			        checkBox.setSelected(false); // 선택 상태를 false로 설정
			    }
			    jModList.repaint(); // JList를 다시 렌더링하여 UI 갱신
		        
		        jTextAreaModQuest.setText("");
		        
		        total_score = 0;
			}
			
		});
      
        // 수정
        jButton2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (jComboBoxModExf.getSelectedIndex() == 0) {
					JOptionPane.showMessageDialog(SetExamDialog.this, "시험지 번호를 선택하세요.");
					return;
				}
				
				if (!(jTextField1.getText().trim().length() > 0)) {
					JOptionPane.showMessageDialog(SetExamDialog.this, "시험지 이름을 입력하세요");
					return;
				}
				
				ImageIcon image = new ImageIcon("src/images/check-sign.png");
				Image img = image.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
				ImageIcon icon = new ImageIcon(img);
				int cmd = JOptionPane.showConfirmDialog(SetExamDialog.this, "수정하시겠습니까?", "확인", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, icon);
				
				if (cmd == JOptionPane.YES_OPTION) {
					String exf_no = jComboBoxModExf.getSelectedItem().toString();
					String exf_name = jTextField1.getText().trim();
					
					// 시험지 수정
					updateExf(exf_no, exf_name);
					
					// 시험 수정
					updateExam();
					
					// 시험지 갱신
					refreshExFrame();
				}
			}
			
		});
	      
        // 취소
        jButton3.addActionListener(new ActionListener() {
				
			@Override
			public void actionPerformed(ActionEvent e) {
		        closed();
			}
			
		});
    }
    
    // 삭제
    private void initDelComponents(JPanel panel3) {
    	JButton jButton5 = new JButton("삭제");
        JButton jButton6 = new JButton("취소");
        JLabel jLabel9 = new JLabel("시험지 번호");
        jComboBoxDelExf = new JComboBox<>();
        jComboBoxDelExf.setBackground(Color.WHITE);
        jComboBoxDelExf.addItem("선택하세요");
        
        // 버튼 및 색상 설정
        jButton5.setFont(font);
        jButton5.setBackground(new Color(255, 69, 58));
        jButton5.setForeground(Color.WHITE);
        jButton6.setFont(font);
        jButton6.setBackground(new Color(169,169,169));
        jButton6.setForeground(Color.WHITE);
        
        // Panel에 컴포넌트 추가 (GroupLayout에서 panel3으로 수정)
        GroupLayout layout = new GroupLayout(panel3);
        panel3.setLayout(layout);

        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jLabel9)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jComboBoxDelExf, GroupLayout.PREFERRED_SIZE, 166, GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(16, Short.MAX_VALUE))
                .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addContainerGap(215, Short.MAX_VALUE)
                    .addComponent(jButton5)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(jButton6)
                    .addGap(16, 16, 16))
        );

        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(41, 41, 41)
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel9)
                        .addComponent(jComboBoxDelExf, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 202, Short.MAX_VALUE)
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton5)
                        .addComponent(jButton6))
                    .addGap(14, 14, 14))
        );
        
        // 중복 제거된 시험지 목록
        selectExamFrame("del");
        
        // 삭제
        jButton5.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (jComboBoxDelExf.getSelectedIndex() == 0) {
					return;
				}
				
				ImageIcon image = new ImageIcon("src/images/warning-sign.png");
				Image img = image.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
				ImageIcon icon = new ImageIcon(img);
				int cmd = JOptionPane.showConfirmDialog(SetExamDialog.this, "삭제하시겠습니까? ※ 학생의 시험 정보까지 삭제됩니다.", "확인", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, icon);
				
				if (cmd == JOptionPane.YES_OPTION){
					// 시험 삭제
					deleteExam();
					
					// 학생 시험 정보 삭제
					deleteStuAns();
					
					// 시험지 삭제
					deleteExamFrame();
					
					// 갱신
	        		refreshExFrame();
				}
			}
			
		});
        
        // 취소
        jButton6.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				closed();
			}
			
		});
        
    }
    
    // 시험지 목록
    private void selectExamFrame(String type) {
    	SqlSession ss = factory.openSession();
    	
    	try {
    		f_list = ss.selectList("ex_frame.select_ex_frame_sub", parent.p.psvo.getSub_no());
        	
        	for (ExFrameVO fvo : f_list) {
        		if (type == "mod") {
        			jComboBoxModExf.addItem(fvo.getExf_no());
        		} else {
        			jComboBoxDelExf.addItem(fvo.getExf_no());
        		}
        	}
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(SetExamDialog.this, "시험지 목록 조회에 실패했습니다.");
		} finally {
			ss.close();
		}
    }
    
    // 문제 목록
    private void selectQuestion(String type) {
    	SqlSession ss = factory.openSession();
    	
    	try {
    		q_list = ss.selectList("question.select_question");
            
            // 초기화
        	if (type == "add") {
        		addModel.clear();
        	} else {
        		modModel.clear();
        	}
            
            for (QuestVO qvo : q_list) {
                // 체크 박스 추가
            	String questionText = qvo.getQ_no();
                JCheckBox checkBox = new JCheckBox(questionText); // 체크박스 생성
                
                // 모델에 추가
                if (type == "add") {
                	addModel.addElement(checkBox);
                } else {
                	modModel.addElement(checkBox);
                }
            }
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(SetExamDialog.this, "문제 목록 조회에 실패했습니다. (1)");
		} finally {
			ss.close();
		}
    }
    
    // 시험지에 대한 문제 목록
    private void selectQuesionById() {
    	SqlSession ss = factory.openSession();
    	
    	try {
    		e_list = ss.selectList("exam.select_exam_by_exf", jComboBoxModExf.getSelectedItem().toString());
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(SetExamDialog.this, "문제 목록 조회에 실패했습니다.");
		} finally {
			ss.close();
		}
    }
    
    // 시험지 정보
 	private ExFrameVO selectExfById(String idx) {
 		SqlSession ss = factory.openSession();
 		
 		ExFrameVO fvo = new ExFrameVO();
 		
 		try {
 			fvo = ss.selectOne("ex_frame.select_ex_frame_by", idx);
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(SetExamDialog.this, "시험지 정보 조회에 실패했습니다. (2)");
		} finally {
			ss.close();
		}
 		
 		return fvo;
 	}
    
    // 추가 - 문제 선택 시
    private void addQuestionArea(int index) {
        if (index >= 0) {
            JCheckBox checkbox = addModel.getElementAt(index);
            checkbox.setSelected(!checkbox.isSelected()); // 상태 토글

            // 체크박스의 선택 상태가 true이면 텍스트를 jTextAreaAddQuest에 추가
            if (checkbox.isSelected()) {
                String checkboxText = checkbox.getText();
                
                // 총 점수 계산
                QuestVO qvo = q_list.get(index);
                
                if (qvo.getQ_no().equals(checkbox.getText())) {
                	total_score += Integer.parseInt(qvo.getQ_score());
                }
                
                // jTextAreaAddQuest에 기존 텍스트가 있으면 콤마로 구분해서 추가
                String existingText = jTextAreaAddQuest.getText().trim();
                if (!existingText.isEmpty()) {
                	jTextAreaAddQuest.append(", " + checkboxText);
                } else {
                	jTextAreaAddQuest.append(checkboxText);
                }
            } else {
            	// 총 점수 계산
                QuestVO qvo = q_list.get(index);
                
                if (qvo.getQ_no().equals(checkbox.getText())) {
                	total_score -= Integer.parseInt(qvo.getQ_score());
                }
            	
                // 체크박스의 선택 상태가 false이면 해당 텍스트를 jTextAreaAddQuest에서 제거
                String checkboxText = checkbox.getText();
                String existingText = jTextAreaAddQuest.getText().trim();

                if (existingText.contains(checkboxText)) {
                    // 먼저 텍스트에서 체크박스 텍스트를 제거
                    String newText = existingText.replaceAll("(?<=,\\s?)?" + checkboxText + "(?=,\\s?)?", "").trim();

                    // 불필요한 콤마 제거: 여러 개의 콤마가 연속적으로 있는 경우 처리
                    String[] parts = newText.split(",\\s*");
                    StringBuilder cleanedText = new StringBuilder();
                    for (String part : parts) {
                        if (!part.isEmpty()) {
                            if (cleanedText.length() > 0) {
                                cleanedText.append(", ");
                            }
                            cleanedText.append(part);
                        }
                    }

                    jTextAreaAddQuest.setText(cleanedText.toString()); // 텍스트를 재설정
                }
            }
            
            jAddList.repaint(jAddList.getCellBounds(index, index)); // 해당 셀만 다시 렌더링
        }
    }
    
    // 수정 - 문제 선택 시
    private void modQuestionArea(int index) {
    	if (index >= 0) {
            JCheckBox checkbox = modModel.getElementAt(index);
            checkbox.setSelected(!checkbox.isSelected()); // 상태 토글

            // 체크박스의 선택 상태가 true이면 텍스트를 jTextAreaModQuest에 추가
            if (checkbox.isSelected()) {
                String checkboxText = checkbox.getText();
                
                // 총 점수 계산
                QuestVO qvo = q_list.get(index);
                
                if (qvo.getQ_no().equals(checkbox.getText())) {
                	total_score += Integer.parseInt(qvo.getQ_score());
                }
                
                // jTextAreaModQuest에 기존 텍스트가 있으면 콤마로 구분해서 추가
                String existingText = jTextAreaModQuest.getText().trim();
                if (!existingText.isEmpty()) {
                	jTextAreaModQuest.append(", " + checkboxText);
                } else {
                	jTextAreaModQuest.append(checkboxText);
                }
            } else {
            	// 총 점수 계산
                QuestVO qvo = q_list.get(index);
                
                if (qvo.getQ_no().equals(checkbox.getText())) {
                	total_score -= Integer.parseInt(qvo.getQ_score());
                }
            	
                // 체크박스의 선택 상태가 false이면 해당 텍스트를 jTextAreaModQuest에서 제거
                String checkboxText = checkbox.getText();
                String existingText = jTextAreaModQuest.getText().trim();

                if (existingText.contains(checkboxText)) {
                    // 먼저 텍스트에서 체크박스 텍스트를 제거
                    String newText = existingText.replaceAll("(?<=,\\s?)?" + checkboxText + "(?=,\\s?)?", "").trim();

                    // 불필요한 콤마 제거: 여러 개의 콤마가 연속적으로 있는 경우 처리
                    String[] parts = newText.split(",\\s*");
                    StringBuilder cleanedText = new StringBuilder();
                    for (String part : parts) {
                        if (!part.isEmpty()) {
                            if (cleanedText.length() > 0) {
                                cleanedText.append(", ");
                            }
                            cleanedText.append(part);
                        }
                    }

                    jTextAreaModQuest.setText(cleanedText.toString()); // 텍스트를 재설정
                }
            }
            
            jModList.repaint(jModList.getCellBounds(index, index)); // 해당 셀만 다시 렌더링
        }
    }
    
    // 시험지 추가
    private String insertExamFrame(String exf_name) {
    	SqlSession ss = factory.openSession();
    	
    	String exf_no = null;
    	
    	try {
    		ExFrameVO evo = new ExFrameVO();
    		
    		evo.setExf_name(exf_name);
        	evo.setProf_no(parent.p.pvo.getProf_no());
        	evo.setSub_no(parent.p.psvo.getSub_no());
        	evo.setScore(String.valueOf(total_score));
        	
        	int cnt = ss.insert("ex_frame.insert_ex_frame", evo);
        	
        	if (cnt > 0) {
        		ss.commit();
        		
        		exf_no = evo.getExf_no();
        	} else {
        		ss.rollback();
        	}
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(SetExamDialog.this, "시험지 추가에 실패했습니다.");
		} finally {
			ss.close();
		}
    	
    	return exf_no;
    }
    
    // 시험 추가
    private void insertExam(String exf_no) {
    	SqlSession ss = factory.openSession();
    	
    	try {
    		if (jTextAreaAddQuest.getText().trim().length() > 0) {
        		String[] questions = jTextAreaAddQuest.getText().trim().split(",\\s*");
        	    
        	    if (questions.length > 0) {
        	    	ArrayList<ExamVO> e_list = new ArrayList<>();
                    for (String question : questions) {
                    	ExamVO evo = new ExamVO();
                    	
                    	evo.setQ_no(question);
                    	evo.setExf_no(exf_no);
                    	
                    	e_list.add(evo);            	
                    }
                    
                    if (e_list.size() > 0) {
                    	HashMap<String, ArrayList<ExamVO>> map = new HashMap<>();
                        map.put("list", e_list);        	
                    	
                        int cnt = ss.insert("exam.insert_exam_for", map);
                        
                        if (cnt > 0) {
                        	ss.commit();
                        	
                        	JOptionPane.showMessageDialog(SetExamDialog.this, "시험이 추가되었습니다.");
                        } else {
                        	ss.rollback();
                        }
                    }
        	    }
        	}
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(SetExamDialog.this, "시험 추가에 실패했습니다.");
		} finally {
			ss.close();
			closed();
		}
    }
    
    // 시험 수정
    private void updateExam() {
    	SqlSession ss = factory.openSession();
    	
    	try {
    		// 변경 문제 목록 가져오기
    	    String[] questions = null;
    	    
    	    if (jTextAreaModQuest.getText().trim().length() > 0) {
    	    	// 변경 목록에서 문제 목록 가져오기
    	    	questions = jTextAreaModQuest.getText().trim().split(",\\s*");
    	    } else {
    	    	// 변경 문제 목록이 없다면, 기존 문제 목록 가져오기
    	    	if (jTextAreaModLegacyQuest.getText().trim().length() > 0) {
    	    		questions = jTextAreaModLegacyQuest.getText().trim().split(",\\s*");
    	    	}
    	    }
    	    
    	    if (questions != null && questions.length > 0) {
    	    	// del_yn 상태 업데이트
    	    	int cnt = ss.update("exam.update_exam_del", jComboBoxModExf.getSelectedItem().toString());
                
                if (cnt > 0) {
                	ss.commit();       
                } else {
                	ss.rollback();
                	JOptionPane.showMessageDialog(this, "시험 수정에 실패했습니다. (1)");
                	return;
                }
                
                ArrayList<ExamVO> e_list = new ArrayList<>();
                for (int i = 0; i < questions.length; i++) {
            		ExamVO evo = new ExamVO();
            		
                	evo.setQ_no(questions[i]);
                	evo.setExf_no(jComboBoxModExf.getSelectedItem().toString());
                	
                	e_list.add(evo);
    			}
                
                // 추가
                if (e_list.size() > 0) {
                	HashMap<String, ArrayList<ExamVO>> map = new HashMap<>();
                    map.put("list", e_list);
                    
                    cnt = ss.insert("exam.insert_exam_for", map);
                    
                    if (cnt > 0) {
                    	ss.commit();     
                    	 JOptionPane.showMessageDialog(this, "시험이 수정되었습니다.");
                    } else {
                    	ss.rollback();
                    	JOptionPane.showMessageDialog(this, "시험 수정에 실패했습니다. (2)");
                    	ss.close();
                    	return;
                    }   
                }
                
                // 문제 갱신
                refreshQuest();
    	    }
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(SetExamDialog.this, "시험 수정에 실패했습니다. (3)");
		} finally {
			ss.close();
	    	closed();
		}
    }
    
    // 시험지 수정
    private void updateExf(String exf_no, String exf_name) {
    	SqlSession ss = factory.openSession();
    	
    	try {
    		HashMap<String, String> map = new HashMap<>();
        	
        	map.put("exf_name", exf_name);
        	map.put("prof_no", parent.p.pvo.getProf_no());
        	map.put("sub_no", parent.p.psvo.getSub_no());
        	map.put("score", String.valueOf(total_score));
        	map.put("exf_no", exf_no);
        	
        	int cnt = ss.update("ex_frame.update_ex_frame", map);
        	
        	if (cnt > 0) {
        		ss.commit();
        	} else {
        		ss.rollback();
        	}
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(SetExamDialog.this, "시험지 수정에 실패했습니다.");
		} finally {
			ss.close();
		}
    }
    
    // 시험 삭제
    private void deleteExam() {
    	SqlSession ss = factory.openSession();
    	
    	try {
    		// del_yn 상태 업데이트
    		int cnt = ss.update("exam.update_exam_del", jComboBoxDelExf.getSelectedItem().toString());
    		
        	if (cnt > 0) {
        		ss.commit();
        		
        		JOptionPane.showMessageDialog(SetExamDialog.this, "시험을 삭제하였습니다.");
        	} else {
        		ss.rollback();
        	}
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(SetExamDialog.this, "시험 삭제에 실패했습니다.");
		} finally {
			ss.close();
			closed();
		}
    }
    
    // 학생 시험 정보 삭제
    private void deleteStuAns() {
    	SqlSession ss = factory.openSession();
    	
    	try {
			int cnt = ss.delete("prof.delete_stu_ans", jComboBoxDelExf.getSelectedItem().toString());
			
			if (cnt > 0) {
				ss.commit();
			} else {
				ss.rollback();
			}
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(SetExamDialog.this, "학생 시험 정보 삭제에 실패했습니다.");
		} finally {
			ss.close();
		}
    }
    
    // 시험지 삭제
    private void deleteExamFrame() {
    	SqlSession ss = factory.openSession();
    	
    	try {
    		// 시험지 del_yn 업데이트
    		int cnt = ss.update("ex_frame.update_ex_frame_del", jComboBoxDelExf.getSelectedItem().toString());
    		
        	if (cnt > 0) {
        		ss.commit();
        		
        		// 초기화
        		jComboBoxDelExf = new JComboBox<String>();
        		jComboBoxDelExf.addItem("선택하세요");
        		
        		// 갱신
        		selectExamFrame("del");
        	} else {
        		ss.rollback();
        	}
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(SetExamDialog.this, "시험지 삭제에 실패했습니다.");
		} finally {
			ss.close();
	    	closed();
		}
    }
    
    // 시험지 갱신
    private void refreshExFrame() { 
    	parent.selectExFrame();
    }
    
    // 문제 갱신
    private void refreshQuest() {
    	String exf_no = jComboBoxModExf.getSelectedItem().toString();
    	
    	if (exf_no != null) {
    		parent.selectQuestion(exf_no);
    	}
    }
    
    // 창 닫기
    private void closed() {
    	total_score = 0;
    	SetExamDialog.this.dispose();
    }
    
}
