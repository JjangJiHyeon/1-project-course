package vo;

public class LoginVO {
    private String id;
    private String password;

    // 생성자
    public LoginVO(String id, String password) {
        this.id = id;
        this.password = password;
    }

    // Getter & Setter
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}