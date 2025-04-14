package vo;

public class SubVO {
    private String sub_no, sub_name, sub_time, sub_info, prof_no, dept_no, sub_cnt;

    public SubVO(String sub_no, String sub_name, String sub_time, String sub_info, String sub_cnt) {
        this.sub_no = sub_no;
        this.sub_name = sub_name;
        this.sub_time = sub_time;
        this.sub_info = sub_info;
        this.sub_cnt = sub_cnt;
        this.prof_no = prof_no;
        this.dept_no = dept_no;
    }

	public SubVO() {
		// TODO Auto-generated constructor stub
	}

	public String getSub_no() {
		return sub_no;
	}

	public void setSub_no(String sub_no) {
		this.sub_no = sub_no;
	}

	public String getSub_name() {
		return sub_name;
	}

	public void setSub_name(String sub_name) {
		this.sub_name = sub_name;
	}

	public String getSub_time() {
		return sub_time;
	}

	public void setSub_time(String sub_time) {
		this.sub_time = sub_time;
	}

	public String getSub_info() {
		return sub_info;
	}

	public void setSub_info(String sub_info) {
		this.sub_info = sub_info;
	}

	public String getProf_no() {
		return prof_no;
	}

	public void setProf_no(String prof_no) {
		this.prof_no = prof_no;
	}

	public String getDept_no() {
		return dept_no;
	}

	public void setDept_no(String dept_no) {
		this.dept_no = dept_no;
	}

	public String getSub_cnt() {
		return sub_cnt;
	}

	public void setSub_cnt(String sub_cnt) {
		this.sub_cnt = sub_cnt;
	}
	
}
