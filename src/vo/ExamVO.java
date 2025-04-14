package vo;

import java.util.List;

public class ExamVO {
private String ex_no,q_no,exf_no,status;


private List<QuestVO> q_list;

public List<QuestVO> getQ_list() {
	return q_list;
}

public void setQ_list(List<QuestVO> q_list) {
	this.q_list = q_list;
}

public String getEx_no() {
	return ex_no;
}

public void setEx_no(String ex_no) {
	this.ex_no = ex_no;
}

public String getQ_no() {
	return q_no;
}

public void setQ_no(String q_no) {
	this.q_no = q_no;
}

public String getExf_no() {
	return exf_no;
}

public void setExf_no(String exf_no) {
	this.exf_no = exf_no;
}

public String getStatus() {
	return status;
}

public void setStatus(String status) {
	this.status = status;
}
}
