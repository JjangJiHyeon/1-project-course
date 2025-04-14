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

import root.RootFrame;
import vo.DeptVO;
import vo.ProfVO;
import vo.StuVO;

public class AddProfDialog extends JDialog {

	RootFrame r;
	SetProfessor s;
	ProfVO pvo;
	
	JTable table;
	JPanel button_p, center_p, p1, p2, p3, p4, p5, p6;
	JTextField tf1, tf2, tf3, tf4, tf5;
	JPasswordField pf1;
	JButton ok_bt, del_bt, cancel_bt;
	JComboBox<DeptVO> dvo;
	
	public AddProfDialog(RootFrame r,SetProfessor s) {
		this.r = r;
		this.s = s;
		initDialog();
		
		p1.setVisible(false);
		del_bt.setVisible(false);
		ok_bt.addActionListener(e -> addProf());
	}
	
	public AddProfDialog(RootFrame r, SetProfessor s, JTable table, ProfVO vo) {
		this.r = r;
		this.s = s;
		this.table = table;
		
		if (vo != null) {
			initDialog();
			
			tf1.setText(vo.getProf_no());
			tf1.setEditable(false);
			
			tf2.setText(vo.getDept_no());
			
			tf3.setText(vo.getProf_name());
			tf4.setText(vo.getProf_email());
			tf5.setText(vo.getProf_id());
			
			pf1.setText(vo.getProf_pw());
		}
		
		ok_bt.addActionListener(e -> modProf());
		del_bt.addActionListener(e -> delProf());
		
	}
	
	private void initDialog() {
		Font buttonFont = new Font("맑은 고딕", Font.BOLD, 12);
		
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image img = toolkit.getImage("src/images/icon_univ.png");
		this.setIconImage(img);
		this.setTitle("강사 관리");
		
		center_p = new JPanel(new GridLayout(9, 1));
		center_p.setBackground(Color.white);
		p1 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		p2 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		p3 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		p4 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		p5 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		p6 = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		
		p1.add(new JLabel("강사 번호 : "));
		p1.add(tf1 = new JTextField(15));
		p1.setBackground(Color.white);
		p2.setBackground(Color.white);
		p3.setBackground(Color.white);
		p4.setBackground(Color.white);
		p5.setBackground(Color.white);
		p6.setBackground(Color.white);
		p2.add(new JLabel("학과 번호 : "));
		p2.add(tf2 = new JTextField(15));
		p3.add(new JLabel("이름 : "));
		p3.add(tf3 = new JTextField(15));
		p4.add(new JLabel("이메일 : "));
		p4.add(tf4 = new JTextField(15));
		p5.add(new JLabel("ID : "));
		p5.add(tf5 = new JTextField(15));
		p6.add(new JLabel("PW : "));
		p6.add(pf1 = new JPasswordField(15));
		
		center_p.add(p1);
		center_p.add(p2);
		center_p.add(p3);
		center_p.add(p4);
		center_p.add(p5);
		center_p.add(p6);
		this.add(center_p);
		
		button_p = new JPanel();
		button_p.setBackground(Color.white);
		button_p.add(ok_bt = new JButton("저장"));
		ok_bt.setFont(buttonFont);
		ok_bt.setBackground(new Color(70, 130, 180));
		ok_bt.setForeground(Color.WHITE);
		button_p.add(del_bt = new JButton("삭제"));
		del_bt.setFont(buttonFont);
		del_bt.setBackground(new Color(255, 69, 58));
		del_bt.setForeground(Color.WHITE);
		button_p.add(cancel_bt = new JButton("취소"));
		cancel_bt.setFont(buttonFont);
		cancel_bt.setBackground(new Color(169, 169, 169));
		cancel_bt.setForeground(Color.WHITE);
		this.add(button_p, BorderLayout.SOUTH);
		this.setSize(300, 350);
		setLocationRelativeTo(r);
		this.setVisible(true);
		
		cancel_bt.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				AddProfDialog.this.dispose();
			}
		});
		
	}
	
	private void getText() {
		pvo = new ProfVO();
		
		// 강사 번호
		if (tf1.getText() != null && tf1.getText().trim().length() > 0) {
			pvo.setProf_no(tf1.getText().trim());
		}
		
		// 학과 번호
		if (tf2.getText() != null && tf2.getText().trim().length() > 0) {
			pvo.setDept_no(tf2.getText().trim());
		}
		
		// 이름
		if (tf3.getText() != null && tf3.getText().trim().length() > 0) {
			pvo.setProf_name(tf3.getText().trim());
		}
		
		// 이메일
		if (tf4.getText() != null && tf4.getText().trim().length() > 0) {
			pvo.setProf_email(tf4.getText().trim());
		}
		
		// ID
		if (tf5.getText() != null && tf5.getText().trim().length() > 0) {
			pvo.setProf_id(tf5.getText().trim());
		}
		
		// PW
		if (pf1.getPassword() != null && pf1.getPassword().length > 0) {
			pvo.setProf_pw(new String(pf1.getPassword()));
		}
	}

	private void addProf() {
		getText();
		
		insertProf(pvo);
	}
	
	private void modProf() {
		getText();
		
		updateProf(pvo);
		
		refreshProf();
	}
	
	// 과목
	private void getDeptList() {
		SqlSession ss = r.factory.openSession();
		
		List<DeptVO> d_list = ss.selectList("");
	}
	
	// 강사 목록 갱신
	private void refreshProf() {
		try {
			String[][] ar = s.selectProf();
			table.setModel(new DefaultTableModel(ar, s.c_name));			
		} catch (Exception e) {
			e.printStackTrace();
		}	
	}
	
	// 강사 추가
	private void insertProf(ProfVO vo) {
		SqlSession ss = r.factory.openSession();
		
		HashMap<String, String> map = new HashMap<>();
		
		map.put("dept_no", "1"); // vo.getDept_no());
		map.put("prof_name", vo.getProf_name());
		map.put("prof_email", vo.getProf_email());
		map.put("prof_id", vo.getProf_id());
		map.put("prof_pw", vo.getProf_pw());
		
		int cnt = ss.insert("prof.insert_prof", map);
		
		if (cnt > 0) {
			ss.commit();
			
			JOptionPane.showMessageDialog(this, "저장 완료");
			dispose();
		} else {
			ss.rollback();
		}
		
		ss.close();
	}
	
	// 강사 수정
	public void updateProf(ProfVO vo) {
		SqlSession ss = r.factory.openSession();
		
		HashMap<String, String> map = new HashMap<>();
		
		map.put("dept_no", vo.getDept_no());
		map.put("prof_name", vo.getProf_name());
		map.put("prof_email", vo.getProf_email());
		map.put("prof_id", vo.getProf_id());
		map.put("prof_pw", vo.getProf_pw());
		map.put("prof_no", vo.getProf_no());
		
		int cnt = ss.update("prof.update_prof", map);
		
		if (cnt > 0) {
			ss.commit();
			
			JOptionPane.showMessageDialog(this, "수정 완료");
		} else {
			ss.rollback();
		}
		
		ss.close();
	}
	
	// 강사 삭제
	private void delProf() {
		pvo = new ProfVO();
		
		// 강사 번호
		if (tf1.getText() != null && tf1.getText().trim().length() > 0) {
			pvo.setProf_no(tf1.getText().trim());
			
			SqlSession ss = r.factory.openSession();
			
			// Jtable의 선택된 값
			int cnt = ss.delete("prof.delete_prof", pvo.getProf_no());
			
			if (cnt > 0) {
				ss.commit();
				
				JOptionPane.showMessageDialog(this, "삭제 완료");
				
				refreshProf();
			} else {
				ss.rollback();
			}
			
			ss.close();
		}
	}
	private void check_id() {
		SqlSession ss = r.factory.openSession();
		List<ProfVO> list = ss.selectList("prof.select_prof");
		for(ProfVO vo : list) {
			if(vo.getProf_id().equals(tf5.getText().trim())) {
				JOptionPane.showMessageDialog(AddProfDialog.this, "ID중복!");
				return;
			}
		}
		ok_bt.setEnabled(true);
		JOptionPane.showMessageDialog(AddProfDialog.this, "ID사용가능!");
		ss.close();
		
	}
}
