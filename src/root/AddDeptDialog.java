package root;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import org.apache.ibatis.session.SqlSession;

import com.mysql.cj.xdevapi.Table;


import root.RootFrame;
import vo.DeptVO;
import vo.ProfVO;

public class AddDeptDialog extends JDialog {
	RootFrame r;
	Table table;
	JButton save, delete, cancel, update, dl, insert;
	GridLayout grid;
	JPanel south, center, south2, center2;
	JTextField text, text2;
	Font buttonFont = new Font("맑은 고딕", Font.BOLD, 12);
	
	public AddDeptDialog(RootFrame r) {
		this.r = r;
		
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image img = toolkit.getImage("src/images/icon_univ.png");
		this.setIconImage(img);
		this.setTitle("학과 관리");

		save = new JButton("저장");		
		save.setFont(buttonFont);
		save.setBackground(new Color(70, 130, 180));
		save.setForeground(Color.WHITE);
		//delete = new JButton("삭제");
		cancel = new JButton("취소");
		cancel.setFont(buttonFont);
		cancel.setBackground(new Color(169,169,169));
		cancel.setForeground(Color.WHITE);
		south = new JPanel();
		south.setBackground(Color.white);
		south.add(save);
		south.add(cancel);
		center = new JPanel();
		center.setBackground(Color.white);
		text = new JTextField(10);
		center.add(new JLabel("학과명"));
		center.add(text);
	
		this.add(center,BorderLayout.CENTER);
		this.setSize(300, 150);
		this.setLocationRelativeTo(this.r);
	
		this.add(south, BorderLayout.SOUTH);
		this.setVisible(true);
		
		save.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				adddept();
				
			}
		});
		
		cancel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				
			}
		});
		
	}

	public AddDeptDialog(RootFrame r2, String deptNo, String deptName) {
		this.r =r2;
		
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image img = toolkit.getImage("src/images/icon_univ.png");
		this.setIconImage(img);
		this.setTitle("학과 관리");
		
		dl = new JButton("삭제");
		dl.setFont(buttonFont);
		dl.setBackground(new Color(255, 69, 58));
		dl.setForeground(Color.WHITE);
		update = new JButton("수정");
		update.setFont(buttonFont);
		update.setBackground(new Color(70, 130, 180));
		update.setForeground(Color.WHITE);
		south2 = new JPanel();
		south2.add(update);
		south2.add(dl);
		center2 = new JPanel();
		text = new JTextField(10);
		text.setText(deptNo);
		text.setEditable(false);
		center2.add(text);
		text2 = new JTextField(10);
		text2.setText(deptName);
		center2.add(text2);
		this.add(center2,BorderLayout.CENTER);
		this.add(south2,BorderLayout.SOUTH);
		this.setBounds(300,50,200,200);
		this.setVisible(true);
		
		dl.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				deletedept();
				
			}
		});
		
		update.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				updatedept();
				
			}
		});
			
	}

	private void adddept() {
		DeptVO vo = new DeptVO();
		
		if(text.getText() != null && text.getText().trim().length()>0) {
			vo.setDept_name(text.getText().trim());
		}		
		
		insertdept(vo);
	}
	
	private void insertdept(DeptVO vo) {
		SqlSession ss = r.factory.openSession();
		
		HashMap <String, String> map = new HashMap<>();
		
		map.put("dept_name",vo.getDept_name());
		
		int cnt = ss.insert("dept.insert_dept",map);
		
		if (cnt>0){
			ss.commit();
			
			JOptionPane.showMessageDialog(this, "저장 완료");
		}else {
			ss.rollback();
		}
		ss.close();
		dispose();
	}
	
	public void updatedept() {
	    SqlSession ss = r.factory.openSession();
	    
	    // dept_no와 dept_name을 받아서 전달해야 함
	    String deptNo = text.getText().trim(); // 수정할 dept_no
	    String deptName = text2.getText().trim(); // 수정할 dept_name
	    
	    if (deptNo.isEmpty() || deptName.isEmpty()) {
	        JOptionPane.showMessageDialog(this, "학과번호와 학과명을 모두 입력해주세요.");
	        return;
	    }

	    HashMap<String, String> map = new HashMap<>();
	    map.put("dept_no", deptNo);
	    map.put("dept_name", deptName);

	    // dept_no와 dept_name을 전달하여 update 수행
	    int cnt = ss.update("dept.update_dept", map); // update 쿼리 실행

	    if (cnt > 0) {
	        ss.commit();
	        JOptionPane.showMessageDialog(this, "수정 완료");
	    } else {
	        ss.rollback();
	        JOptionPane.showMessageDialog(this, "수정 실패");
	    }
	    ss.close();
	    dispose();
	}
	
	private void deletedept() {
	    SqlSession ss = r.factory.openSession();

	    // dept_no 값을 전달해야 함
	    String deptNo = text.getText().trim(); // deptNo 값을 text2에서 가져옴
	    
	    if (deptNo.isEmpty()) {
	        JOptionPane.showMessageDialog(this, "학과번호를 입력해주세요.");
	        return;
	    }

	    // deptNo를 매개변수로 전달
	    int cnt = ss.delete("dept.delete_dept", deptNo); // 삭제할 dept_no를 전달

	    if (cnt > 0) {
	        ss.commit();
	        JOptionPane.showMessageDialog(this, "삭제 완료");
	    } else {
	        ss.rollback();
	        JOptionPane.showMessageDialog(this, "삭제 실패");
	    }
	    ss.close();
	    dispose();
		
		
	}
	
	

	
	
	
}