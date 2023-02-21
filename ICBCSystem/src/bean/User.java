package bean;

public class User {

    private int id;
    private String userName;
    private String password;
    private String cardNo;
    private String balance;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public User(int id, String userName, String password, String cardNo, String balance) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.cardNo = cardNo;
        this.balance = balance;
    }

    public User(String userName, String password, String cardNo, String balance) {
    }

    public User() {
    }
}
