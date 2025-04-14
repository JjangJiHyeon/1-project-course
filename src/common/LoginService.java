package common;

import java.util.HashMap;
import java.util.Map;

import javax.swing.JOptionPane;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;

import professor.ProfFrame;
import root.RootFrame;
import student.StudentFrame;
import vo.LoginVO;
import vo.ProfVO;
import vo.RootVO;
import vo.StuVO;

public class LoginService {
	RootFrame rf;
    private SqlSessionFactory sqlSessionFactory;

    public LoginService(SqlSessionFactory sqlSessionFactory) {
        this.sqlSessionFactory = sqlSessionFactory;
    }

    public void validateLogin(String role, String id, String password,LoginFrame parent) {
        try (SqlSession session = sqlSessionFactory.openSession()) {
        	
            switch (role) {
                case "관리자": // Admin login
                    RootVO adminVO = session.selectOne("login.validateAdminLogin", Map.of("id", id, "password", password));
                    if (adminVO == null) {
                    	JOptionPane.showMessageDialog(null, role + " 로그인 실패");
                    	break;
                    }
                    RootFrame r = new RootFrame();
                    r.setVisible(true);
                    parent.dispose();
                    break;

                case "학생": // Student login
                    StuVO studentVO = session.selectOne("login.validateStudentLogin", Map.of("id", id, "password", password));
                    if (studentVO == null) {
                    	JOptionPane.showMessageDialog(null, role + " 로그인 실패");
                    	break;
                    }
                    System.out.println(studentVO.getStu_name());
                    StudentFrame s = new StudentFrame(studentVO);
                    s.setVisible(true);
                    parent.dispose();
                    break;
                    
                    

                case "강사": // Professor login
                    ProfVO profVO = session.selectOne("login.validateProfessorLogin", Map.of("id", id, "password", password));
                    if (profVO == null) {
                    	JOptionPane.showMessageDialog(null, role + " 로그인 실패");
                    	break;
          
                   }
                    ProfFrame f = new ProfFrame(profVO);
                    f.setVisible(true);
                    parent.dispose();
                  
         
            }
  
          
        }
       
    } 
  
}