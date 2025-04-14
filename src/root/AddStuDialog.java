package root;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import org.apache.ibatis.session.SqlSession;

import vo.DeptVO;

import vo.StuVO;

public class AddStuDialog extends JDialog {

	RootFrame r;
	SetStudent s;
	StuVO svo;

	JTable table;
	JPanel button_p, center_p, p1, p2, p3, p4, p5, p6, p7;
	JTextField tf1, tf2, tf3, tf4, tf5, tf6;
	JPasswordField pf1;
	JButton ok_bt, del_bt, cancel_bt, chk_bt;
	JComboBox<DeptVO> dvo;
	List<DeptVO> list;
	boolean alter;

	public AddStuDialog(RootFrame r, SetStudent s) {
		this.r = r;

		this.s = s;
		this.table = s.table;
		initDialog();
		ok_bt.setEnabled(false);
		chk_bt.addActionListener(e -> check_id());
		p1.setVisible(false);
		del_bt.setVisible(false);

		ok_bt.addActionListener(e -> addStu());
	}

	public AddStuDialog(RootFrame r, SetStudent s, JTable table, StuVO vo) {
		this.r = r;
		this.s = s;
		this.table = table;
		alter = false;
		if (vo != null) {
			initDialog();
			chk_bt.setText("수정");
			tf5.setEditable(false);
			tf1.setText(vo.getStu_no());
			tf1.setEditable(false);
			System.out.println(vo.getDept_no());
			dvo.setSelectedIndex(Integer.parseInt(vo.getDept_no())-1);
			tf3.setText(vo.getStu_name());
			tf4.setText(vo.getStu_email());
			tf5.setText(vo.getStu_id());
			tf6.setText(vo.getStu_phone());
			pf1.setText(vo.getStu_pw());
		}

		ok_bt.addActionListener(e -> modStu());
		del_bt.addActionListener(e -> delStu());
		chk_bt.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				tf5.setEditable(true);
				alter = true;
			}
		});

	}

	private void initDialog() {
		Font buttonFont = new Font("맑은 고딕", Font.BOLD, 12);
		
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image img = toolkit.getImage("src/images/icon_univ.png");
		this.setIconImage(img);
		this.setTitle("학생 관리");
		
		center_p = new JPanel(new GridLayout(9, 1));
		center_p.setBackground(Color.white);
		center_p.setForeground(new Color(70, 130, 180));
		dvo = new JComboBox<DeptVO>();
		p1 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		p2 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		p3 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		p4 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		p5 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		p6 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		p7 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		chk_bt = new JButton("check");
		chk_bt.setSize(10, 10);
		chk_bt.setFont(buttonFont);
		chk_bt.setBackground(new Color(70, 130, 180));
		chk_bt.setForeground(Color.WHITE);
		getDeptList();
		p1.add(new JLabel("강사 번호 : "));
		p1.add(tf1 = new JTextField(15));
		p1.setBackground(Color.white);
		p2.setBackground(Color.white);
		p3.setBackground(Color.white);
		p4.setBackground(Color.white);
		p5.setBackground(Color.white);
		p6.setBackground(Color.white);
		p7.setBackground(Color.white);
		p2.add(new JLabel("학과 : "));
		p2.add(dvo);
		p3.add(new JLabel("이름 : "));
		p3.add(tf3 = new JTextField(15));
		p4.add(new JLabel("이메일 : "));
		p4.add(tf4 = new JTextField(15));
		p5.add(chk_bt);
		p5.add(new JLabel("ID : "));
		p5.add(tf5 = new JTextField(15));
		p6.add(new JLabel("PW : "));
		p6.add(pf1 = new JPasswordField(15));
		p7.add(new JLabel("PHONE : "));
		p7.add(tf6 = new JTextField(15));

		center_p.add(p1);
		center_p.add(p2);
		center_p.add(p3);
		center_p.add(p4);
		center_p.add(p5);
		center_p.add(p6);
		center_p.add(p7);
		this.add(center_p);

		button_p = new JPanel();
		button_p.setBackground(Color.white);
		button_p.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
		ok_bt = new JButton("저장");
		ok_bt.setFont(buttonFont);
		ok_bt.setBackground(new Color(70, 130, 180));
		ok_bt.setForeground(Color.WHITE);
		button_p.add(ok_bt);

		del_bt = new JButton("삭제");
		del_bt.setFont(buttonFont);
		del_bt.setBackground(new Color(255, 69, 58));
		del_bt.setForeground(Color.WHITE);
		button_p.add(del_bt);

		cancel_bt = new JButton("취소");
		cancel_bt.setFont(buttonFont);
		cancel_bt.setBackground(new Color(169, 169, 169));
		cancel_bt.setForeground(Color.WHITE);
		button_p.add(cancel_bt);
		this.add(button_p, BorderLayout.SOUTH);

		this.setSize(300, 350);
		setLocationRelativeTo(r);
		this.setVisible(true);
		cancel_bt.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				AddStuDialog.this.dispose();
			}
		});

	}

	private void getText() {
		svo = new StuVO();
		DeptVO vo = (DeptVO) dvo.getSelectedItem();
		// 강사 번호
		if (tf1.getText() != null && tf1.getText().trim().length() > 0) {
			svo.setStu_no(tf1.getText().trim());
		}

		// 학과 번호
		if (vo != null) {
			svo.setDept_no(vo.getDept_no());

		}

		// 이름
		if (tf3.getText() != null && tf3.getText().trim().length() > 0) {
			svo.setStu_name(tf3.getText().trim());
		}

		// 이메일
		if (tf4.getText() != null && tf4.getText().trim().length() > 0) {
			svo.setStu_email(tf4.getText().trim());
		}

		// ID
		if (tf5.getText() != null && tf5.getText().trim().length() > 0) {
			svo.setStu_id(tf5.getText().trim());
		}

		// PW
		if (pf1.getPassword() != null && pf1.getPassword().length > 0) {
			svo.setStu_pw(new String(pf1.getPassword()));
		}
		if (tf6.getText() != null && tf6.getText().trim().length() > 0) {
			svo.setStu_phone(tf6.getText().trim());
		}
	}

	private void addStu() {
		getText();
		insertStu(svo);
		AddStuDialog.this.dispose();
	}

	private void modStu() {
		getText();
		if (alter = true) {
			check_id();
		}
		updateStu(svo);
		refreshStu();
		AddStuDialog.this.dispose();
	}

	// 과목
	private void getDeptList() {
		SqlSession ss = r.factory.openSession();

		list = ss.selectList("student.dept_all");
		for (DeptVO vo : list) {
			dvo.addItem(vo);
		}
		ss.close();
	}

	// 강사 목록 갱신
	private void refreshStu() {
		try {
			String[][] ar = s.selectStu();
			table.setModel(new DefaultTableModel(ar, s.c_name));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 강사 추가
	private void insertStu(StuVO vo) {
		SqlSession ss = r.factory.openSession();

		HashMap<String, String> map = new HashMap<>();

		map.put("dept_no", vo.getDept_no()); // vo.getDept_no());
		map.put("stu_name", vo.getStu_name());
		map.put("stu_email", vo.getStu_email());
		map.put("stu_id", vo.getStu_id());
		map.put("stu_pw", vo.getStu_pw());
		map.put("stu_phone", vo.getStu_phone());

		int cnt = ss.insert("student.insert_stu", map);

		if (cnt > 0) {
			ss.commit();

			JOptionPane.showMessageDialog(this, "저장 완료");
		} else {
			ss.rollback();
		}
		refreshStu();
		ss.close();

	}

	// 강사 수정
	public void updateStu(StuVO vo) {
		SqlSession ss = r.factory.openSession();

		HashMap<String, String> map = new HashMap<>();

		map.put("dept_no", vo.getDept_no());
		map.put("stu_name", vo.getStu_name());
		map.put("stu_email", vo.getStu_email());
		map.put("stu_id", vo.getStu_id());
		map.put("stu_pw", vo.getStu_pw());
		map.put("stu_no", vo.getStu_no());
		map.put("stu_phone", vo.getStu_phone());
		System.out.println(vo.getDept_no());
		int cnt = ss.update("student.update_stu", map);

		if (cnt > 0) {
			ss.commit();

			JOptionPane.showMessageDialog(this, "수정 완료");
		} else {
			ss.rollback();
		}

		ss.close();
	}

	// 강사 삭제
	private void delStu() {
		svo = new StuVO();

		// 강사 번호
		if (tf1.getText() != null && tf1.getText().trim().length() > 0) {
			svo.setStu_no(tf1.getText().trim());

			SqlSession ss = r.factory.openSession();

			// Jtable의 선택된 값
			int cnt = ss.delete("student.delete_stu", svo.getStu_no());

			if (cnt > 0) {
				ss.commit();

				JOptionPane.showMessageDialog(this, "삭제 완료");

				refreshStu();
			} else {
				ss.rollback();
			}

			ss.close();
		}
	}

	private void check_id() {
		if(tf5.getText().trim().length()==0) {
			return;
		}
		SqlSession ss = r.factory.openSession();
		List<StuVO> list = ss.selectList("student.stu_all");
		for (StuVO vo : list) {
			if (vo.getStu_id().equals(tf5.getText().trim())) {
				JOptionPane.showMessageDialog(AddStuDialog.this, "ID중복!");
				return;
			}
		}
		ok_bt.setEnabled(true);
		JOptionPane.showMessageDialog(AddStuDialog.this, "ID사용가능!");
		ss.close();

	}
}
