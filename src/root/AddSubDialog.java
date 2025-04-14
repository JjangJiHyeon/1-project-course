package root;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.apache.ibatis.session.SqlSession;

import vo.DeptVO;
import vo.ProfVO;
import vo.SubVO;

public class AddSubDialog extends JDialog {
	RootFrame parent;
	SubVO data;
	SetSubject ss;
	JTextField sub_no, sub_name, sub_time, sub_info, sub_cnt; // 외래키는 뺌
	JComboBox<String> cbxProf, cbxDept; 
	JPanel p;
	
	public AddSubDialog(RootFrame p, SetSubject ss) {
		this.parent = p;
		this.ss = ss;
		init();
	}

	public AddSubDialog(RootFrame p, SubVO vo, SetSubject ss) {
		this.data = vo;
		this.parent = p;
		this.ss = ss;
		init();
		sub_no.setText(vo.getSub_no());
		sub_name.setText(vo.getSub_name());
		sub_time.setText(vo.getSub_time());
		sub_info.setText(vo.getSub_info());
		sub_cnt.setText(vo.getSub_cnt());
	    cbxProf.setSelectedItem(getProfessorIdByName(vo.getProf_no()));
	    cbxDept.setSelectedItem(getDepartmentIdByName(vo.getDept_no()));
	}

	public void init() {
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image img = toolkit.getImage("src/images/icon_univ.png");
		this.setIconImage(img);
		this.setTitle("과목 관리");
		
		p = new JPanel();
		p.setLayout(new GridLayout(8, 2));
		p.setBackground(Color.white);
		sub_no = new JTextField(8);// text필드 사이즈 안정해줘서 8로 지정함
		sub_no.setEditable(false);
		sub_name = new JTextField(8);
		sub_time = new JTextField(8);
		sub_info = new JTextField(8);
		sub_cnt = new JTextField(8);
		cbxProf = new JComboBox<>();
		cbxProf.setBackground(Color.white);
		cbxDept = new JComboBox<>();
		cbxDept.setBackground(Color.white);
		
		p.add(new JLabel("과목 번호:"));
		p.add(sub_no);
		
		p.add(new JLabel("과목 이름:"));
		p.add(sub_name);

		p.add(new JLabel("시작 날짜:"));
		p.add(sub_time);

		p.add(new JLabel("과목 정보:"));
		p.add(sub_info);

		p.add(new JLabel("정원 수:"));
		p.add(sub_cnt);
		
		p.add(new JLabel("담당 교수:"));
		p.add(cbxProf);
		
		p.add(new JLabel("학과:"));
		p.add(cbxDept);

		loadProfessors();
		loadDepartments();
		JButton saveButton = new JButton("저장");
		Font buttonFont = new Font("맑은 고딕", Font.BOLD, 12);
		saveButton.setFont(buttonFont);
		saveButton.setBackground(new Color(70, 130, 180));
		saveButton.setForeground(Color.WHITE);
		p.add(saveButton);
		add(p);
		saveButton.addActionListener(new ActionListener() {
	
			@Override
			public void actionPerformed(ActionEvent e) {
				data = new SubVO();
				data.setSub_no(sub_no.getText());
				data.setSub_name(sub_name.getText());
				data.setSub_time(sub_time.getText());
				data.setSub_info(sub_info.getText());
				data.setSub_cnt(sub_cnt.getText());
				data.setProf_no(getProfessorIdByName(cbxProf.getSelectedItem().toString()));
				data.setDept_no(getDepartmentIdByName(cbxDept.getSelectedItem().toString()));
				// 데이터 처리 후 Dialog 종료
				addSubjectData(data); // RootFrame에서 데이터를 업데이트하는 메서드 필요
				ss.refreshTable();
				dispose(); // Dialog 닫기

			}
		});
		setSize(300, 250);
		setLocationRelativeTo(parent); // 부모 창 중앙에 띄우기
		setModal(true); // 모달로 설정 (다른 창을 클릭 못하게)

	}
	private void loadProfessors() {
        SqlSession session = parent.factory.openSession();
        try {
            // 교수 목록 가져오기
            List<ProfVO> professorList = session.selectList("prof.getAllProfessors"); // 쿼리 수정 필요
            for (ProfVO professor : professorList) {
                cbxProf.addItem(professor.getProf_name()); // 교수 이름을 JComboBox에 추가
            }
        } finally {
            session.close();
        }
        
    }
	private void loadDepartments() {
	    SqlSession session = parent.factory.openSession();
	    try {
	        // 학과 목록 가져오기
	        List<DeptVO> departmentList = session.selectList("dept.getAllDepartments"); // 적절한 쿼리 ID 사용
	        for (DeptVO department : departmentList) {
	            cbxDept.addItem(department.getDept_name()); // 학과 이름을 JComboBox에 추가
	        }
	    } finally {
	        session.close();
	    }
	}
	
	private String getProfessorIdByName(String professorName) {
        SqlSession session = parent.factory.openSession();
        try {
            // 교수 이름으로 교수 ID 가져오기
            return session.selectOne("prof.getProfessorIdByName", professorName);
        } finally {
            session.close();
        }
    }
	private String getDepartmentIdByName(String departmentName) {
	    SqlSession session = parent.factory.openSession();
	    try {
	        // 학과 이름으로 학과 ID 가져오기
	        return session.selectOne("dept.getDepartmentIdByName", departmentName);
	    } finally {
	        session.close();
	    }
	}
	private void addSubjectData(SubVO data) {
		SqlSession session = parent.factory.openSession();
		try {
			session.insert("subject.insertSubject", data); // 적절한 쿼리 ID 사용
			session.commit();
		} finally {
			session.close();
		}
	}
}
