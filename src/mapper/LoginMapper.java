package mapper;



import vo.LoginVO;

public interface LoginMapper {
	boolean validateAdminLogin(LoginVO loginVO);
    boolean validateStudentLogin(LoginVO loginVO);
    boolean validateTeacherLogin(LoginVO loginVO);
}
