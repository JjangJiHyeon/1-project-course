
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
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.io.Reader;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import common.LoginFrame;
import vo.StuVO;

public class RootFrame extends JFrame {

	private JPanel west_p, east_p, south_p, north_p;
	private JButton bt_student, bt_dept, bt_subject, bt_professor, bt_add, logout_bt;
	private JScrollPane jScrollPane2;
	private JTable jTable1;
	SqlSessionFactory factory;
	private int bt_option;
	private String[][] arr;

	// 생성자
	public RootFrame() {
		// 관리자 main 생성
		initComponents();
	}

	private void initComponents() {
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image img = toolkit.getImage("src/images/icon_univ.png");
		this.setIconImage(img);
		
		Font buttonFont = new Font("맑은 고딕", Font.BOLD, 16);
		Font titleFont = new Font("맑은 고딕", Font.BOLD, 24);
		setTitle("학생 관리 프로그램 - 관리자");
		west_p = new JPanel();// 좌측 패널
		east_p = new JPanel();
		south_p = new JPanel();
		
		bt_student = createStyledButton("학생", buttonFont);
		bt_dept = createStyledButton("학과", buttonFont);
		bt_subject = createStyledButton("과목", buttonFont);
		bt_professor = createStyledButton("강사", buttonFont);
		bt_add = createStyledButton("추가", buttonFont);
		logout_bt = createStyledButton("로그아웃", buttonFont);
		logout_bt.setBackground(new Color(255, 69, 58));
		logout_bt.setForeground(Color.WHITE);
		jTable1 = new JTable();// 리스트 테이블
		jTable1.getTableHeader().setBackground(new Color(70, 130, 180));
		jTable1.getTableHeader().setForeground(Color.white);
		jTable1.setGridColor(new Color(70, 130, 180));
		jScrollPane2 = new JScrollPane();
		this.setLocation(500, 250);
		bt_add.setEnabled(false);
		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		setPreferredSize(new java.awt.Dimension(700, 500));
		jScrollPane2.setPreferredSize(new Dimension(800, 400));
		dbConnect();
		// 좌측 패널 학과,과목,강사,학생 버튼 형성
		north_p = new JPanel();
		ImageIcon icon = new ImageIcon("src/images/sist.png");
		Image image = icon.getImage().getScaledInstance(70, 60, Image.SCALE_SMOOTH);
		ImageIcon scaledIcon = new ImageIcon(image);
		JLabel imageLabel = new JLabel(scaledIcon);
		north_p.setBackground(Color.white);
		north_p.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, new Color(70, 130, 180))); // 하단 경계선 추가
		north_p.add(imageLabel);
		JLabel titleLabel = new JLabel("관리자");
		titleLabel.setForeground(new Color(70, 130, 180));
		titleLabel.setFont(titleFont);
		north_p.add(titleLabel);
		add(north_p, BorderLayout.NORTH);
		west_p.setLayout(new BoxLayout(west_p, BoxLayout.Y_AXIS));
		west_p.setBackground(Color.white);
		west_p.add(new JLabel());
		west_p.add(addMargin(bt_student));
		west_p.add(addMargin(bt_dept));
		west_p.add(addMargin(bt_subject));
		west_p.add(addMargin(bt_professor));
		south_p.add(logout_bt);
		south_p.setLayout(new FlowLayout(FlowLayout.RIGHT));
		south_p.setBackground(Color.white);
		this.add(west_p, BorderLayout.WEST);

		jScrollPane2.setViewportView(jTable1);
		this.add(jScrollPane2, BorderLayout.CENTER);
		east_p.add(bt_add);
		east_p.setBackground(Color.white);
		this.add(east_p, BorderLayout.EAST);
		this.add(south_p, BorderLayout.SOUTH);
		bt_student.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				removeListner(bt_add);
				SetStudent ss = new SetStudent(RootFrame.this, jTable1, bt_add);
				arr = ss.value;

			}
		});
		bt_professor.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				removeListner(bt_add);
				new SetProfessor(RootFrame.this, jTable1, bt_add);

			}
		});

		bt_subject.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				removeListner(bt_add);
				new SetSubject(RootFrame.this, jTable1, bt_add);
			}
		});
		bt_dept.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				removeListner(bt_add);
				new SetDept(RootFrame.this, jTable1, bt_add);
			}
		});
		logout_bt.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				ImageIcon image = new ImageIcon("src/images/warning-sign.png");
				Image img = image.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
				ImageIcon icon = new ImageIcon(img);
				int yes = JOptionPane.showConfirmDialog(RootFrame.this, "로그아웃 하시겠습니까?", "확인", JOptionPane.OK_OPTION, JOptionPane.WARNING_MESSAGE, icon);
				
				if (yes == JOptionPane.YES_OPTION) {
					new LoginFrame(factory).setVisible(true);

					RootFrame.this.dispose();
				}
			}
		});
		// 팩
		pack();
	}

	private void dbConnect() {
		Reader r;
		try {
			r = Resources.getResourceAsReader("config/config.xml");
			SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
			factory = builder.build(r);
			r.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void setBt_option(int bt_option) {
		this.bt_option = bt_option;
	}

	public void removeListner(JButton bt) {
		if (bt.getActionListeners() != null) {
			ActionListener[] al = bt.getActionListeners();
			for (ActionListener a : al) {
				bt.removeActionListener(a);
			}

		}
	}

	public void removeListner(JTable table) {
		if (table.getMouseListeners() != null) {
			MouseListener[] ml = table.getMouseListeners();
			for (MouseListener a : ml) {
				table.removeMouseListener(a);
			}

		}
	}

	  private JButton createStyledButton(String text, Font font) {
	        JButton button = new JButton(text);
	        button.setFont(font);
	        button.setFocusPainted(false);
	        button.setBackground(new Color(70, 130, 180));
	        button.setForeground(Color.WHITE);
	        button.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
	        return button;
	    }

	private JPanel addMargin(JButton button) {
		JPanel panel = new JPanel(new BorderLayout());
		panel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10)); // 상, 좌, 하, 우 여백
		panel.setBackground(Color.white); // 패널 배경색 (좌측 패널과 통일)
		panel.add(button, BorderLayout.CENTER);
		return panel;
	}

}
