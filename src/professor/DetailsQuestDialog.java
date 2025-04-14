package professor;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;

import vo.QuestVO;

public class DetailsQuestDialog extends JDialog {
	
	SetExam parent; 
	QuestVO qvo;
	
	Font font = new Font("맑은 고딕", Font.BOLD, 12);
	
	public DetailsQuestDialog(SetExam parent, QuestVO qvo) {
		this.parent = parent;
		this.qvo = qvo;
		
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image img = toolkit.getImage("src/images/icon_univ.png");
		this.setIconImage(img);
		this.setTitle("문제 상세보기");
		
		JPanel panel1 = new JPanel();
		panel1.setBackground(Color.WHITE);
		initComponents(panel1);
		this.add(panel1);
		
		this.setSize(630, 300);
        this.setLocationRelativeTo(parent);
		this.setVisible(true);
	}
	
	private void initComponents(JPanel panel1) {
		// JLabels
        JLabel jLabel1 = new JLabel("문제 쓰기");
        JLabel jLabel2 = new JLabel("선택지");
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
        JButton jButton6 = new JButton("닫기");
        
        // 버튼 및 색상 설정
        jButton6.setFont(font);
        jButton6.setBackground(new Color(169,169,169));
        jButton6.setForeground(Color.WHITE);

        // Set up the layout for the panel
        GroupLayout layout = new GroupLayout(panel1);
        panel1.setLayout(layout);
        
        if (qvo != null) {
        	jTextArea1.setText(qvo.getQ_text());
        	jTextField2.setText(qvo.getFirst());
        	jTextField3.setText(qvo.getSecond());
        	jTextField4.setText(qvo.getThird());
        	jTextField5.setText(qvo.getForth());
        	jTextField6.setText(qvo.getQ_score());
        	jTextField7.setText(qvo.getQ_answer());
        }
        
        // 비활성화
        jTextArea1.setEditable(false);
        jTextField2.setEditable(false);
        jTextField3.setEditable(false);
        jTextField4.setEditable(false);
        jTextField5.setEditable(false);
        jTextField6.setEditable(false);
        jTextField7.setEditable(false);
        
        layout.setHorizontalGroup(
    	    layout.createParallelGroup(GroupLayout.Alignment.LEADING)
    	        .addGroup(layout.createSequentialGroup()
    	            .addContainerGap()
    	            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
    	                .addComponent(jLabel1) // jLabel1 추가
    	                .addComponent(jLabel2)
    	                .addComponent(jLabel7)
    	                .addComponent(jLabel8))
    	            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
    	            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
    	                .addComponent(jScrollPane1) // jScrollPane1 추가
    	                .addComponent(jTextField6, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
    	                .addComponent(jTextField7, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
    	                .addGroup(layout.createSequentialGroup()
    	                    .addComponent(jTextField2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
    	                    .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
    	                    .addComponent(jTextField3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
    	                    .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
    	                    .addComponent(jTextField4, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
    	                    .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
    	                    .addComponent(jTextField5, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
    	            .addContainerGap())
    	        .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
    	            .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
    	            .addComponent(jButton6) // jButton6을 우측 정렬
    	            .addGap(20)) // 오른쪽 여백
    	);

    	layout.setVerticalGroup(
    	    layout.createParallelGroup(GroupLayout.Alignment.LEADING)
    	        .addGroup(layout.createSequentialGroup()
    	            .addContainerGap()
    	            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
    	                .addComponent(jLabel1)
    	                .addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
    	            .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
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
    	            .addComponent(jButton6) // jButton6 수직 하단 배치
    	            .addGap(15)) // 하단 여백
    	);
        
        jButton6.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				DetailsQuestDialog.this.dispose();
			}
		});
	}
}
