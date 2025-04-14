package root;
/*
	취소누르면 다이얼로그 dispose() 추가
	로그아웃 버튼
	dialog 중복해서 뜨는 문제 개선
	Frame,dialog 위치 중앙으로 변경

 */
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.Reader;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import vo.StuVO;

public class SetStudent {
	SqlSessionFactory factory;
	List<StuVO> list;
	RootFrame parent;
	String[] c_name = { "학번", "이름", "ID", "PW", "학과", "핸드폰", "e-mail" };
	JTable table;
	String[][] value;

	public SetStudent(RootFrame parent, JTable table, JButton add_bt) {
		this.parent = parent;
		this.table = table;
		this.factory = parent.factory;
		parent.removeListner(table);
		showList();
		table.getColumnModel().getColumn(0).setPreferredWidth(30);
		table.getColumnModel().getColumn(1).setPreferredWidth(60);
		table.getColumnModel().getColumn(4).setPreferredWidth(30);
		table.getColumnModel().getColumn(5).setPreferredWidth(100);
		table.getColumnModel().getColumn(6).setPreferredWidth(150);
		add_bt.setEnabled(true);
		add_bt.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				new AddStuDialog(parent, SetStudent.this);
			}
		});

		table.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				// 클릭한 지점의 행 확인
				int row = table.rowAtPoint(e.getPoint());
				// 선택된 데이터 처리
				StuVO vo = list.get(row);
				System.out.println("선택된 데이터: " + vo);
				new AddStuDialog(SetStudent.this.parent, SetStudent.this, table, vo);

			}

		});

	}

	private void showList() {
		SqlSession ss = factory.openSession();
		list = ss.selectList("student.stu_all");
		String[][] ar = makeArray(list);
		DefaultTableModel model = new DefaultTableModel(ar, c_name);
		table.setModel(model);
		ss.close();

	}

	String[][] makeArray(List<StuVO> list) {
		this.value = null;

		if (list != null && !list.isEmpty()) {
			value = new String[list.size()][c_name.length];
			int i = 0;
			for (StuVO vo : list) { // 2차원 배열에 자원들을 넣기위한 반복문
				value[i][0] = vo.getStu_no();
				value[i][1] = vo.getStu_name();
				value[i][2] = vo.getStu_id();
				value[i][3] = vo.getStu_pw();
				value[i][4] = vo.getDept_no();
				value[i][5] = vo.getStu_phone();
				value[i][6] = vo.getStu_email();
				
				
				
				i++;
			}
		}

		return value;
	}

	public String[][] selectStu() {
		SqlSession ss = parent.factory.openSession();

		list = ss.selectList("student.stu_all");

		ss.close();

		return makeArray(list);
	}
}
