package professor;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.List;

import javax.swing.*;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import vo.ExFrameVO;
import vo.QuestVO;

public class SetQuestDialog extends JDialog {
	
	SetExam parent;
	private SqlSessionFactory factory;
	private QuestVO qvo;
	
	private JComboBox<String> jComboBoxMod, jComboBoxDel;
	
	Font font = new Font("맑은 고딕", Font.BOLD, 12);
	
    public SetQuestDialog(SetExam parent, SqlSessionFactory factory) {
    	this.parent = parent;
    	this.factory = factory;
    	
    	Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image img = toolkit.getImage("src/images/icon_univ.png");
		this.setIconImage(img);
		this.setTitle("문제 관리");
    	
        setSize(650, 370);

        // JTabbedPane 생성
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setBackground(Color.WHITE);

        // 첫 번째 탭
        JPanel panel1 = new JPanel();
        panel1.setBackground(Color.WHITE);
        initAddComponents(panel1);
        tabbedPane.addTab("추가", panel1);

        // 두 번째 탭
        JPanel panel2 = new JPanel();
        panel2.setBackground(Color.WHITE);
        initModComponents(panel2);
        tabbedPane.addTab("수정", panel2);

        // 세 번째 탭
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
        // JLabels
        JLabel jLabel1 = new JLabel("문제 쓰기");
        JLabel jLabel2 = new JLabel("선택지");
        JLabel jLabel3 = new JLabel("1");
        JLabel jLabel4 = new JLabel("2");
        JLabel jLabel5 = new JLabel("3");
        JLabel jLabel6 = new JLabel("4");
        JLabel jLabel7 = new JLabel("배점");
        JLabel jLabel8 = new JLabel("정답");

        // JTextFields
        JTextField jTextField2 = new JTextField(10);
        JTextField jTextField3 = new JTextField(10);
        JTextField jTextField4 = new JTextField(10);
        JTextField jTextField5 = new JTextField(10);
        JTextField jTextField6 = new JTextField(10);
        JTextField jTextField7 = new JTextField(10);

        // JTextArea and JScrollPane
        JTextArea jTextArea1 = new JTextArea(5, 20);
        JScrollPane jScrollPane1 = new JScrollPane(jTextArea1);

        // JButtons
        JButton jButton5 = new JButton("추가");
        JButton jButton6 = new JButton("취소");
        
        // 버튼 및 색상 설정
        jButton5.setFont(font);
        jButton5.setBackground(new Color(70, 130, 180));
        jButton5.setForeground(Color.WHITE);
        jButton6.setFont(font);
        jButton6.setBackground(new Color(169,169,169));
        jButton6.setForeground(Color.WHITE);

        // Set up the layout for the panel
        GroupLayout layout = new GroupLayout(panel1);
        panel1.setLayout(layout);
        
        layout.setHorizontalGroup(
    	    layout.createParallelGroup(GroupLayout.Alignment.LEADING)
    	        .addGroup(layout.createSequentialGroup()
    	            .addContainerGap()
    	            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
    	                .addGroup(layout.createSequentialGroup()
    	                    .addComponent(jLabel1)
    	                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
    	                    .addComponent(jScrollPane1))
    	                .addGroup(layout.createSequentialGroup()
    	                    .addComponent(jLabel2)
    	                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
    	                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
    	                        .addComponent(jTextField2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
    	                        .addComponent(jLabel3))
    	                    .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
    	                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
    	                        .addComponent(jTextField3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
    	                        .addComponent(jLabel4))
    	                    .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
    	                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
    	                        .addComponent(jTextField4, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
    	                        .addComponent(jLabel5))
    	                    .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
    	                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
    	                        .addComponent(jTextField5, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
    	                        .addComponent(jLabel6)))
    	                .addGroup(layout.createSequentialGroup()
    	                    .addComponent(jLabel8)
    	                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
    	                    .addComponent(jTextField7, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
    	                .addGroup(layout.createSequentialGroup()
    	                    .addComponent(jLabel7)
    	                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
    	                    .addComponent(jTextField6, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
    	            .addContainerGap(16, Short.MAX_VALUE))
    	        .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
    	            .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
    	            .addComponent(jButton5)
    	            .addGap(20, 20, 20)
    	            .addComponent(jButton6)
    	            .addGap(20, 20, 20))
    	);
    	layout.setVerticalGroup(
    	    layout.createParallelGroup(GroupLayout.Alignment.LEADING)
    	        .addGroup(layout.createSequentialGroup()
    	            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
    	                .addGroup(layout.createSequentialGroup()
    	                    .addGap(11, 11, 11)
    	                    .addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE))
    	                .addGroup(layout.createSequentialGroup()
    	                    .addGap(44, 44, 44)
    	                    .addComponent(jLabel1)))
    	            .addGap(18, 18, 18)
    	            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
    	                .addComponent(jLabel3)
    	                .addComponent(jLabel4)
    	                .addComponent(jLabel5)
    	                .addComponent(jLabel6))
    	            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
    	            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
    	                .addComponent(jLabel2)
    	                .addComponent(jTextField2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
    	                .addComponent(jTextField3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
    	                .addComponent(jTextField4, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
    	                .addComponent(jTextField5, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
    	            .addGap(24, 24, 24)
    	            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
    	                .addComponent(jLabel8)
    	                .addComponent(jTextField7, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
    	            .addGap(18, 18, 18)
    	            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
    	                .addComponent(jLabel7)
    	                .addComponent(jTextField6, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
    	            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
    	            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
    	                .addComponent(jButton5)
    	                .addComponent(jButton6))
    	            .addGap(10, 10, 10))
    	);
        
    	// JTextArea의 텍스트 줄바꿈
    	jTextArea1.setLineWrap(true);
    	jTextArea1.setWrapStyleWord(true);
    	
    	// Placeholder 설정
        String placeholderText = "TXT 파일을 가져오거나 내용을 입력해주세요.";
        jTextArea1.setText(placeholderText);
        jTextArea1.setForeground(Color.GRAY);
        
        jTextArea1.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent e) {
                if (jTextArea1.getText().equals(placeholderText)) {
                	jTextArea1.setText(""); // Placeholder 제거
                	jTextArea1.setForeground(Color.BLACK); // 글자 색상 복원
                }
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                if (jTextArea1.getText().isEmpty()) {
                	jTextArea1.setText(placeholderText); // Placeholder 복원
                	jTextArea1.setForeground(Color.GRAY); // Placeholder 색상
                }
            }
        });

        // Drag and Drop 활성화
        jTextArea1.setTransferHandler(new TransferHandler() {
            @Override
            public boolean canImport(TransferSupport support) {
                // 파일만 허용
                return support.isDataFlavorSupported(DataFlavor.javaFileListFlavor);
            }

            @Override
            public boolean importData(TransferSupport support) {
                if (!canImport(support)) {
                    return false;
                }
                try {
                    // 드롭된 파일 목록 가져오기
                    List<File> files = (List<File>) support.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);

                    // 첫 번째 파일만 처리
                    File file = files.get(0);
                    if (file.getName().endsWith(".txt")) {
                        // 파일 내용을 읽어와 JTextArea에 표시
                        StringBuilder content = new StringBuilder();
                        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                            String line;
                            while ((line = reader.readLine()) != null) {
                                content.append(line).append("\n");
                            }
                        }
                        jTextArea1.setText(content.toString());
                        jTextArea1.setForeground(Color.BLACK); // 텍스트 추가 시 색상 복원
                        return true;
                    } else {
                        JOptionPane.showMessageDialog(SetQuestDialog.this, "Only .txt files are supported!",
                                "Invalid File", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return false;
            }
        });
    	
        // 추가
        jButton5.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ImageIcon image = new ImageIcon("src/images/check-sign.png");
				Image img = image.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
				ImageIcon icon = new ImageIcon(img);
				int cmd = JOptionPane.showConfirmDialog(SetQuestDialog.this, "추가하시겠습니까?", "확인", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, icon);
				
				if (cmd == JOptionPane.YES_OPTION) {
					parseText(jTextArea1, jTextField2, jTextField3, jTextField4, jTextField5, jTextField6, jTextField7);
					
					if (qvo != null) {
						// 추가
						insertQuest();
					}
					
					// 갱신
					refresh();
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
    
    // 수정
    private void initModComponents(JPanel panel2) {
        JLabel jLabel1 = new JLabel("문제 쓰기");
        JLabel jLabel2 = new JLabel("선택지");
        JButton jButton5 = new JButton("수정");
        JButton jButton6 = new JButton("취소");
        JTextField jTextField2 = new JTextField();
        JTextField jTextField3 = new JTextField();
        JTextField jTextField4 = new JTextField();
        JTextField jTextField5 = new JTextField();
        JLabel jLabel3 = new JLabel("1");
        JLabel jLabel4 = new JLabel("2");
        JLabel jLabel5 = new JLabel("3");
        JLabel jLabel6 = new JLabel("4");
        JLabel jLabel7 = new JLabel("배점");
        JTextField jTextField6 = new JTextField();
        JTextField jTextField7 = new JTextField();
        JLabel jLabel8 = new JLabel("정답");
        JScrollPane jScrollPane1 = new JScrollPane();
        JTextArea jTextArea1 = new JTextArea(20, 5);
        JLabel jLabel9 = new JLabel("문제 번호");
        jComboBoxMod = new JComboBox<>();
        jComboBoxMod.setBackground(Color.WHITE);
        jComboBoxMod.addItem("선택하세요");
        
        // 버튼 및 색상 설정
        jButton5.setFont(font);
        jButton5.setBackground(new Color(70, 130, 180));
        jButton5.setForeground(Color.WHITE);
        jButton6.setFont(font);
        jButton6.setBackground(new Color(169,169,169));
        jButton6.setForeground(Color.WHITE);

        jTextField2.setColumns(10);
        jTextField3.setColumns(10);
        jTextField4.setColumns(10);
        jTextField5.setColumns(10);
        jTextField6.setColumns(10);
        jTextField7.setColumns(10);

        jScrollPane1.setViewportView(jTextArea1);
 
        // 레이아웃 설정
        GroupLayout layout = new GroupLayout(panel2);
        panel2.setLayout(layout);
        
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jLabel1)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 400, GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jLabel2)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel3)
                                .addComponent(jTextField2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel4)
                                .addComponent(jTextField3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel5)
                                .addComponent(jTextField4, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel6)
                                .addComponent(jTextField5, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
                    .addContainerGap())
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jLabel9)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED) // 상단 여백 추가
                            .addComponent(jComboBoxMod, GroupLayout.PREFERRED_SIZE, 166, GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jLabel8)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jTextField7, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jLabel7)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jTextField6, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                        .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton5)
                            .addGap(20, 20, 20)
                            .addComponent(jButton6)
                            .addGap(20, 20, 20))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(10) // 상단 여백 추가
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel9)
                        .addComponent(jComboBoxMod, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 80, GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createSequentialGroup()
                            .addGap(33, 33, 33)
                            .addComponent(jLabel1)))
                    .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel3)
                        .addComponent(jLabel4)
                        .addComponent(jLabel5)
                        .addComponent(jLabel6))
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel2)
                        .addComponent(jTextField2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField4, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(jTextField5, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel8)
                        .addComponent(jTextField7, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel7)
                        .addComponent(jTextField6, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton5)
                        .addComponent(jButton6))
                    .addGap(10)) // 하단 여백 추가
        );
        
        // JTextArea의 텍스트 줄바꿈
    	jTextArea1.setLineWrap(true);
    	jTextArea1.setWrapStyleWord(true);
    	
    	// Placeholder 설정
        String placeholderText = "문제 번호를 선택하거나, TXT 파일을 드래그하세요.";
        jTextArea1.setText(placeholderText);
        jTextArea1.setForeground(Color.GRAY);
        
        jTextArea1.addFocusListener(new java.awt.event.FocusAdapter() {
            @Override
            public void focusGained(java.awt.event.FocusEvent e) {
                if (jTextArea1.getText().equals(placeholderText)) {
                	jTextArea1.setText(""); // Placeholder 제거
                	jTextArea1.setForeground(Color.BLACK); // 글자 색상 복원
                }
            }

            @Override
            public void focusLost(java.awt.event.FocusEvent e) {
                if (jTextArea1.getText().isEmpty()) {
                	jTextArea1.setText(placeholderText); // Placeholder 복원
                	jTextArea1.setForeground(Color.GRAY); // Placeholder 색상
                }
            }
        });

        // Drag and Drop 활성화
        jTextArea1.setTransferHandler(new TransferHandler() {
            @Override
            public boolean canImport(TransferSupport support) {
                // 파일만 허용
                return support.isDataFlavorSupported(DataFlavor.javaFileListFlavor);
            }

            @Override
            public boolean importData(TransferSupport support) {
                if (!canImport(support)) {
                    return false;
                }
                try {
                    // 드롭된 파일 목록 가져오기
                    List<File> files = (List<File>) support.getTransferable().getTransferData(DataFlavor.javaFileListFlavor);

                    // 첫 번째 파일만 처리
                    File file = files.get(0);
                    if (file.getName().endsWith(".txt")) {
                        // 파일 내용을 읽어와 JTextArea에 표시
                        StringBuilder content = new StringBuilder();
                        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                            String line;
                            while ((line = reader.readLine()) != null) {
                                content.append(line).append("\n");
                            }
                        }
                        jTextArea1.setText(content.toString());
                        jTextArea1.setForeground(Color.BLACK); // 텍스트 추가 시 색상 복원
                        return true;
                    } else {
                        JOptionPane.showMessageDialog(SetQuestDialog.this, "Only .txt files are supported!",
                                "Invalid File", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return false;
            }
        });
        
        // 문제 목록
        selectQuestList("mod");
        
        // 문제 선택 시
        jComboBoxMod.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (jComboBoxMod.getSelectedIndex() != 0) {
					String idx = jComboBoxMod.getSelectedItem().toString();
					selectQuestById(idx);

					if (qvo != null) {
						jTextArea1.setText(qvo.getQ_text());
						jTextField2.setText(qvo.getFirst());
						jTextField3.setText(qvo.getSecond());
						jTextField4.setText(qvo.getThird());
						jTextField5.setText(qvo.getForth());
						jTextField6.setText(qvo.getQ_score());
						jTextField7.setText(qvo.getQ_answer());
					}
				}
			}
			
		});
        
        // 수정
        jButton5.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (jComboBoxMod.getSelectedIndex() == 0) {
					return;
				}
				
				ImageIcon image = new ImageIcon("src/images/check-sign.png");
				Image img = image.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
				ImageIcon icon = new ImageIcon(img);
				int cmd = JOptionPane.showConfirmDialog(SetQuestDialog.this, "수정하시겠습니까?", "확인", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, icon);
			
				if (cmd == JOptionPane.YES_OPTION) {
					parseText(jTextArea1, jTextField2, jTextField3, jTextField4, jTextField5, jTextField6, jTextField7);
					
					// 수정
					updateQuest(jComboBoxMod.getSelectedItem().toString());
				
					// 갱신
					refresh();
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
    
    // 삭제
    private void initDelComponents(JPanel panel3) {
        JButton jButton5 = new JButton("삭제");
        JButton jButton6 = new JButton("취소");
        JLabel jLabel9 = new JLabel("문제 번호");
        jComboBoxDel = new JComboBox<>();
        jComboBoxDel.setBackground(Color.WHITE);
        jComboBoxDel.addItem("선택하세요");
        
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
                    .addGap(10) // 상단 여백
                    .addComponent(jLabel9)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jComboBoxDel, GroupLayout.PREFERRED_SIZE, 166, GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton5)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED, 20, 20) // 버튼 간 간격
                    .addComponent(jButton6)
                    .addGap(20)) // 오른쪽 여백
        );

        layout.setVerticalGroup(
            layout.createSequentialGroup()
                .addGap(10) // 상단 여백
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jComboBoxDel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton5)
                    .addComponent(jButton6))
                .addGap(10)); // 하단 여백
        
        // 문제 목록
        selectQuestList("del");
        
        // 삭제
        jButton5.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (jComboBoxDel.getSelectedIndex() == 0) {
					return;
				}
				
				ImageIcon image = new ImageIcon("src/images/warning-sign.png");
				Image img = image.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
				ImageIcon icon = new ImageIcon(img);
				int cmd = JOptionPane.showConfirmDialog(SetQuestDialog.this, "삭제하시겠습니까?", "확인", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, icon);
				
				if (cmd == JOptionPane.YES_OPTION) {
					// 삭제
					deleteQuest();
					
					// 갱신
					refresh();
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
    
    // Text
    private void parseText(JTextArea text, JTextField option1, JTextField option2, JTextField option3, JTextField option4, JTextField score, JTextField answer) {
    	String q_text = text.getText().toString().trim();
    	String first = option1.getText().toString().trim();
    	String second = option2.getText().toString().trim();
    	String third = option3.getText().toString().trim();
    	String forth = option4.getText().toString().trim();
    	String q_answer = answer.getText().toString().trim();
    	String q_score = score.getText().toString().trim();
    	
    	if (q_text.length() < 1 || q_answer.length() < 1 || q_score.length() < 1 || first.length() < 1 || second.length() < 1 || third.length() < 1 || forth.length() < 1) {
    		JOptionPane.showMessageDialog(this, "모두 입력하세요.");
    		return;
    	} else {
    		qvo = new QuestVO();
    		qvo.setQ_text(q_text);
    		qvo.setQ_answer(q_answer);
    		qvo.setQ_score(q_score);
    		qvo.setFirst(first);
    		qvo.setSecond(second);
    		qvo.setThird(third);
    		qvo.setForth(forth);
    	}
    }
    
    // 문제 추가
    private void insertQuest() {
    	HashMap<String, String> map = new HashMap<>();
    	
    	map.put("q_text", qvo.getQ_text());
    	map.put("q_answer", qvo.getQ_answer());
    	map.put("q_score", qvo.getQ_score());
    	map.put("first", qvo.getFirst());
    	map.put("second", qvo.getSecond());
    	map.put("third", qvo.getThird());
    	map.put("forth", qvo.getForth());
    	
    	SqlSession ss = factory.openSession();
    	
    	try {
    		int cnt = ss.insert("question.insert_question", map);
        	
        	if (cnt > 0) {
        		ss.commit();
        		
        		JOptionPane.showMessageDialog(this, "문제를 추가하였습니다.");
        	} else {
        		ss.rollback();
        	}
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(SetQuestDialog.this, "문제 추가에 실패했습니다.");
		} finally {
			ss.close();
	    	closed();
		}
    }
    
    // 문제 목록
    private void selectQuestList(String type) {
    	SqlSession ss = factory.openSession();
    	
    	try {
    		List<QuestVO> q_list = ss.selectList("question.select_question");
        	
        	for (QuestVO q : q_list) {
        		if (type == "mod") {
        			jComboBoxMod.addItem(q.getQ_no());
        		} else {
        			jComboBoxDel.addItem(q.getQ_no());
        		}
        	}
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(SetQuestDialog.this, "문제 목록 조회에 실패했습니다.");
		} finally {
	    	ss.close();
		}
    }
    
    // 문제 가져오기
    private QuestVO selectQuestById(String idx) {
    	SqlSession ss = factory.openSession();
    	
    	try {
    		qvo = new QuestVO();
        	qvo = ss.selectOne("question.select_question_by", idx);
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(SetQuestDialog.this, "문제 정보 조회에 실패했습니다.");
		} finally {
			ss.close();
		}
    
    	return qvo;
    }
    
    // 문제 수정
    private void updateQuest(String idx) {
    	HashMap<String, String> map = new HashMap<>();
    	
    	map.put("q_no", idx);
    	map.put("q_text", qvo.getQ_text());
    	map.put("q_answer", qvo.getQ_answer());
    	map.put("q_score", qvo.getQ_score());
    	map.put("first", qvo.getFirst());
    	map.put("second", qvo.getSecond());
    	map.put("third", qvo.getThird());
    	map.put("forth", qvo.getForth());
    	
    	SqlSession ss = factory.openSession();
    	
    	try {
    		int cnt = ss.insert("question.update_question", map);
        	
        	if(cnt > 0) {
        		ss.commit();
        		
        		JOptionPane.showMessageDialog(this, "문제를 수정하였습니다.");
        	} else {
        		ss.rollback();
        	}	
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(SetQuestDialog.this, "문제 수정에 실패했습니다.");
		} finally {
			ss.close();
	    	closed();
		}
    }
    
    // 문제 삭제
    private void deleteQuest() {
    	SqlSession ss = factory.openSession();
    	
    	try {
        	int cnt = ss.delete("question.delete_question", jComboBoxDel.getSelectedItem().toString());
        	
        	if (cnt > 0) {
        		ss.commit();
        		
        		JOptionPane.showMessageDialog(this, "문제를 삭제하였습니다.");
        	} else {
        		ss.rollback();
        	}
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, "시험지에 포함된 문제로 삭제할 수 없습니다.");
		} finally {
			ss.close();
        	closed();
		}
    }
    
    // 갱신
    private void refresh() {
    	int row = parent.jTable1.getSelectedRow();
    	
    	if (row < 0) {
    		return;
    	}
    	
    	if (parent.ef_list != null && parent.ef_list.size() > 0) {
    		ExFrameVO vo = parent.ef_list.get(row);
        	
        	if (vo != null) {
        		String exf_no = vo.getExf_no();
            	parent.selectQuestion(exf_no);
        	}
    	}
    }
    
    // 창 닫기
    private void closed() {
    	SetQuestDialog.this.dispose();
    }

}
