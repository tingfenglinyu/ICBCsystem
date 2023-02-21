package service;

import bean.User;
import dao.UserDao;

import java.math.BigDecimal;

public class UserService {
    private UserDao userDao = new UserDao();

    public User findUser(String userName, String password) {
        return userDao.findOneUser(userName,password);
    }

    //存款和取款
    public String deposit(int i, String balance, User user) {
        //对传进来的balance进行处理
        balance = dataProcessing(balance);
        if (balance.equals("请输入正数金额!")||balance.equals("最大精度为小数点后两位!")||balance.equals("请输入正确金额!")){
            return balance;
        }

        BigDecimal newBalance = new BigDecimal(balance);
        //这里需要对user进行更新!
        BigDecimal oldBalance = new BigDecimal(userDao.findOneUser(user.getUserName(),user.getPassword()).getBalance());
        String trueBalance = "";
        if (i == 1){
            trueBalance = oldBalance.add(newBalance).toString();
        }else if (i == 2){
            if (oldBalance.compareTo(newBalance) == -1){
                return "余额不足!";
            }
            trueBalance = oldBalance.subtract(newBalance).toString();
        }


        return userDao.updateBalance(i,trueBalance,user);
    }

    public String findBalance(User user) {
        return userDao.selectBalance(user);
    }

    public String transfer(String balance, String cardNo, User user) {
        BigDecimal oldBalance = new BigDecimal(findBalance(user));
        balance = dataProcessing(balance);
        if (balance.equals("请输入正数金额!")||balance.equals("最大精度为小数点后两位!")||balance.equals("请输入正确金额!")){
            return balance;
        }
        BigDecimal newBalance = new BigDecimal(balance);
        if (oldBalance.compareTo(newBalance) == -1){
            return "您的余额不足!";
        }
        //查找对方账户
        User userByCardNo = userDao.selectCardNo(cardNo);
        if (userByCardNo == null){
            return "对方账户不存在";
        }
        //转账操作
        return userDao.transfer(balance, userByCardNo, userDao.findOneUser(user.getUserName(), user.getPassword()));
    }


    //对数据进行处理
    public static String dataProcessing(String data){
        //如果是整数
        try{
            //判断格式是否正确的同时判断是否小于0.0
            if (Double.valueOf(data) <= 0.0) {
                return "请输入正数金额!";
            }
        }catch (NumberFormatException e){
            e.printStackTrace();
            return "请输入正确金额!";

        }
        if (!data.contains(".")){
            data = data+".00";
        }
        //错误精度
        if (data.length() - data.indexOf(".") > 3){
            return "最大精度为小数点后两位!";
        }
        if (data.length() - data.indexOf(".") == 2){
            data = data+"0";
        }
        if (data.length() - data.indexOf(".") == 1){
            data = data+"00";
        }
        return data;
    }

    public String resivePassword(String oldP, String newP, String reNewP, User user) {
        if (!oldP.equals(newP)){
            return "原密码与新密码一致!";
        }
        if (!oldP.equals(user.getPassword())){
            return "原密码不正确!";
        }
        if (!newP.equals(reNewP)){
            return "两次密码输入不一致!";
        }
        return userDao.updatePassword(reNewP,user);
    }
}
