package root;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import vo.DeptVO;
import vo.ProfVO;
import vo.SubVO;

public class SubjectDialog extends JDialog {
	private SubVO subject;
	private SqlSessionFactory factory;
	private SetSubject setSubject;

	// JTextField 필드 추가
	private JTextField txtSubNo;
	private JTextField txtSubName;
	private JTextField txtSubTime;
	private JTextField txtSubInfo;
	private JTextField txtSubCnt;
	private JComboBox<String> cbxProf;
	private JComboBox<String> cbxDept;

	public SubjectDialog(JFrame parent, SqlSessionFactory factory, SubVO subject, SetSubject setSubject) {
		super(parent, "과목 관리", true);
		this.subject = subject;
		this.factory = factory;
		this.setSubject = setSubject;
		setLocationRelativeTo(parent);
		initComponents();
	}

	private void initComponents() {
		setSize(400, 300);
		setLayout(new BorderLayout());
		Font buttonFont = new Font("맑은 고딕", Font.BOLD, 12);
		// 과목 정보 입력 필드
		JPanel infoPanel = new JPanel(new GridLayout(7, 2));
		infoPanel.setBackground(Color.white);
		infoPanel.add(new JLabel("과목 번호:"));
		txtSubNo = new JTextField(subject.getSub_no());
		txtSubNo.setEditable(false); // 과목번호는 수정 불가
		infoPanel.add(txtSubNo);

		infoPanel.add(new JLabel("과목 이름:"));
		txtSubName = new JTextField(subject.getSub_name());
		infoPanel.add(txtSubName);

		infoPanel.add(new JLabel("과목 시간:"));
		txtSubTime = new JTextField(subject.getSub_time());
		infoPanel.add(txtSubTime);

		infoPanel.add(new JLabel("과목 정보:"));
		txtSubInfo = new JTextField(subject.getSub_info());
		infoPanel.add(txtSubInfo);

		infoPanel.add(new JLabel("인원 수:"));
		txtSubCnt = new JTextField(subject.getSub_cnt());
		infoPanel.add(txtSubCnt);

		infoPanel.add(new JLabel("교수:"));
		cbxProf = new JComboBox<>();
		cbxProf.setBackground(Color.white);
		loadProfessors();

		cbxProf.setSelectedIndex(Integer.parseInt(subject.getProf_no()) - 1);
		infoPanel.add(cbxProf);
		add(infoPanel, BorderLayout.CENTER);

		infoPanel.add(new JLabel("학과:"));
		cbxDept = new JComboBox<>();
		cbxDept.setBackground(Color.white);
		loadDepartments(); // 학과 정보를 로드하는 메서드 호출
		cbxDept.setSelectedIndex(Integer.parseInt(subject.getDept_no()) - 1);
		infoPanel.add(cbxDept);
		// 수정 및 삭제 버튼
		JPanel buttonPanel = new JPanel();
		buttonPanel.setBackground(Color.white);
		JButton btnUpdate = new JButton("수정");
		JButton btnDelete = new JButton("삭제");

		btnUpdate.setFont(buttonFont);
		btnUpdate.setBackground(new Color(70, 130, 180));
		btnUpdate.setForeground(Color.WHITE);
		btnDelete.setFont(buttonFont);
		btnDelete.setBackground(new Color(255, 69, 58));
		btnDelete.setForeground(Color.WHITE);
		// 수정 버튼 동작
		btnUpdate.addActionListener(e -> {
			subject.setSub_name(txtSubName.getText());
			subject.setSub_time(txtSubTime.getText());
			subject.setSub_info(txtSubInfo.getText());
			subject.setSub_cnt(txtSubCnt.getText());
			subject.setProf_no(String.valueOf(cbxProf.getSelectedIndex() + 1)); // 교수번호로 변환
			subject.setDept_no(String.valueOf(cbxDept.getSelectedIndex() + 1));

			if (changeMy()) {
				JOptionPane.showMessageDialog(this, "수정 완료!");
				setSubject.refreshTable();
				dispose(); // 다이얼로그 종료
			} else {
				JOptionPane.showMessageDialog(this, "수정 실패!");
			}
		});

		// 삭제 버튼 동작
		btnDelete.addActionListener(e -> {
			int confirm = JOptionPane.showConfirmDialog(this, "정말 삭제하시겠습니까?", "삭제 확인", JOptionPane.YES_NO_OPTION);
			if (confirm == JOptionPane.YES_OPTION) {

				if (deleteMy(subject.getSub_no())) {
					JOptionPane.showMessageDialog(this, "삭제 완료!");
					setSubject.refreshTable();
					dispose(); // 다이얼로그 종료
				} else {
					JOptionPane.showMessageDialog(this, "삭제 실패!");
				}
			}
		});

		buttonPanel.add(btnUpdate);
		buttonPanel.add(btnDelete);
		add(buttonPanel, BorderLayout.SOUTH);
	}

	private void loadProfessors() {
		try (SqlSession ss = factory.openSession()) {
			// "professor.getAllProfessors"는 교수 정보를 가져오는 MyBatis 매퍼 SQL
			java.util.List<ProfVO> professorList = ss.selectList("prof.select_prof");

			for (ProfVO professor : professorList) {
				cbxProf.addItem(professor.getProf_no() + " - " + professor.getProf_name()); // 교수 이름과 번호를 콤보박스에 추가
			}
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, "교수 목록을 불러오는 데 실패했습니다.");
		}
	}

	private void loadDepartments() {
		try (SqlSession ss = factory.openSession()) {
			// "department.getAllDepartments"는 학과 정보를 가져오는 MyBatis 매퍼 SQL
			java.util.List<DeptVO> departmentList = ss.selectList("dept.select_dept");

			for (DeptVO department : departmentList) {
				cbxDept.addItem(department.getDept_no() + " - " + department.getDept_name()); // 학과 이름과 번호를 콤보박스에 추가
			}
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, "학과 목록을 불러오는 데 실패했습니다.");
		}
	}

//	private String getProfessorIdByName(String professorName) {
//        // 교수 이름에서 번호를 추출하는 로직 추가
//        try (SqlSession ss = factory.openSession()) {
//        	System.out.println("professorName.trim() : " + professorName.trim());        	
//        	
//        	String str = ss.selectOne("prof.getProfessorIdByName", professorName.trim());
//        	
//        	System.out.println("str : " + str);
//        	
//            return str;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    } 

	private String getProfessorNameById(String professorId) {
		try (SqlSession ss = factory.openSession()) {
			return ss.selectOne("prof.getProfessorNameById", professorId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	private String getDepartmentNameById(String departmentId) {
		try (SqlSession ss = factory.openSession()) {
			return ss.selectOne("dept.getDepartmentNameById", departmentId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public boolean changeMy() {
		ImageIcon image = new ImageIcon("src/images/check-sign.png");
		Image img = image.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
		ImageIcon icon = new ImageIcon(img);
		int confirm = JOptionPane.showConfirmDialog(this, "수정하시겠습니까?", "수정 확인", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, icon);
		if (confirm == JOptionPane.YES_OPTION) {
			try {
				SqlSession ss = factory.openSession();
				int result = ss.update("subject.updateSubject", subject);
				ss.commit(); // 반드시 커밋해야 데이터베이스에 반영됨
				ss.close();
				return true;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return false;

	}

	public boolean deleteMy(String subNo) {
		SqlSession ss = factory.openSession();
		try {
			int result = ss.delete("subject.deleteSubject", subNo);
			ss.commit();
		} catch (Exception e) {
			// TODO: handle exception
		}
		ss.close();
		return false;
	}
}