package professor;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.table.DefaultTableModel;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import vo.SubStuExVO;

public class DetailsStuExDialog extends JDialog {
	
	SetStudentExam parent;
	List<SubStuExVO> e_list;
	
	String stu_no;
	String exf_no;
	
	SqlSessionFactory factory;
	
	private JTable jTable1;
	private JTextField jTextField1; // 학생 번호
	private JTextField jTextField2; // 학생 이름
	private JTextField jTextField3;	// 시험지 이름
	
	private String[] c_name = { "문제 번호", "정답", "입력한 값", "정답 여부", "배점" };
	
	Font font = new Font("맑은 고딕", Font.BOLD, 12);
	
	public DetailsStuExDialog(SetStudentExam parent, SqlSessionFactory factory, String stu_no, String exf_no) {
		this.parent = parent;
		this.factory = factory;
		this.stu_no = stu_no;
		this.exf_no = exf_no;

		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image img = toolkit.getImage("src/images/icon_univ.png");
		this.setIconImage(img);
		this.setTitle("점수 상세보기");
		
		JPanel panel1 = new JPanel();
		panel1.setBackground(Color.WHITE);
		initComponents(panel1);
		this.add(panel1);
		
		this.setSize(480, 530);
        this.setLocationRelativeTo(parent);
		this.setVisible(true);
	}
	
	private void initComponents(JPanel panel1) {
        JLabel jLabel1 = new JLabel("학생 번호");
        JLabel jLabel2 = new JLabel("학생 이름");
        JLabel jLabel3 = new JLabel("시험지 이름");
        jTextField1 = new JTextField();
        jTextField2 = new JTextField();
        jTextField3 = new JTextField();
        JButton jButton1 = new JButton("닫기");
        JScrollPane jScrollPane1 = new JScrollPane();
        jTable1 = new JTable();
        jTable1.getTableHeader().setBackground(new Color(70, 130, 180));
        jTable1.getTableHeader().setForeground(Color.WHITE);
        jTable1.getTableHeader().setFont(font);
        jTable1.setGridColor(new Color(70, 130, 180));
        
        // 테이블 배경색 설정
        jTable1.setBackground(Color.WHITE);
        
        // 버튼 및 색상 설정
        jButton1.setFont(font);
        jButton1.setBackground(new Color(169,169,169));
        jButton1.setForeground(Color.WHITE);

        jTable1.setModel(new DefaultTableModel(c_name, 0));
        jScrollPane1.setViewportView(jTable1);

        GroupLayout layout = new GroupLayout(panel1);
        panel1.setLayout(layout);
        
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(85, 85, 85)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3))
                        .addGap(28, 28, 28)
                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addComponent(jTextField1)
                            .addComponent(jTextField2)
                            .addComponent(jTextField3)))
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.TRAILING)
                        .addComponent(jButton1)
                        .addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 313, GroupLayout.PREFERRED_SIZE)))
                .addGap(83, 83, 83))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(51, 51, 51)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTextField1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTextField2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jTextField3, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 33, Short.MAX_VALUE)
                .addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, 251, GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31)
                .addComponent(jButton1)
                .addGap(31, 31, 31))
        );
        
        // 상세 정보 조회
        searchStudentExam();
        
        // 닫기
        jButton1.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				DetailsStuExDialog.this.dispose();
			}
			
		});
    }
	
	// 상세 정보
	private void searchStudentExam() {
		SqlSession ss = factory.openSession();
		
		try {
			HashMap<String, String> map = new HashMap<>();
			
			map.put("exf_no", exf_no);
			map.put("stu_no", stu_no);
			
			e_list = ss.selectList("prof.search_stu_by_sub_stu", map);
			
			String[][] data = makeArray();
			jTable1.setModel(new DefaultTableModel(data, c_name));
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(DetailsStuExDialog.this, "상세 정보 조회에 실패했습니다.");
		} finally {
			ss.close();
		}
	}
	
	// 2차원 배열
	private String[][] makeArray() {
		String[][] ar = null;
		
		if (e_list != null && e_list.size() > 0) {
			ar = new String[e_list.size()][c_name.length];
			
			int i = 0;
			for (SubStuExVO vo : e_list) {
				if (jTextField1.getText().trim().length() == 0) {
					jTextField1.setText(vo.getStu_no());
					jTextField1.setEditable(false);
				}
				
				if (jTextField2.getText().trim().length() == 0) {
					jTextField2.setText(vo.getStu_name());
					jTextField2.setEditable(false);
				}
				
				if (jTextField3.getText().trim().length() == 0) {
					jTextField3.setText(vo.getExf_name());
					jTextField3.setEditable(false);
				}
				
				ar[i][0] = vo.getQ_no();
				ar[i][1] = vo.getQ_answer();
				ar[i][2] = vo.getEs_input();
								
				if (vo.getEs_correct().equals("0")) {
					ar[i][3] = "X";
				} else {
					ar[i][3] = "O";
				}
				
				ar[i][4] = vo.getQ_score();
				
				i++;
			}
		}
		
		return ar;
	}
}
