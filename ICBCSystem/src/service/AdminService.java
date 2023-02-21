package service;

import bean.Admin;
import bean.User;
import dao.AdminDao;
import dao.UserDao;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class AdminService {
    private AdminDao adminDao = new AdminDao();

    public Admin findAdmin(String userName, String password) {
        return adminDao.selectOneAdmin(userName,password);
    }

    public String account(User user) {
        String result = UserService.dataProcessing(user.getBalance());
        if (result.equals("请输入正数金额!")||result.equals("最大精度为小数点后两位!")||result.equals("请输入正确金额!")){
            return result;
        }
        user.setBalance(result);
        User findUser = adminDao.selectByName(user.getUserName());
        if (findUser != null){
            return "该用户名已存在";
        }
        if (user.getCardNo().length() != 16){
            return "银行卡号应该为16位";
        }
        int i = adminDao.insertUser(user);
        if (i == 1){
            return "操作成功!";
        }
        return "操作失败!";
    }

    public String balanceAll() {
        List<User> userList = adminDao.selectAll();
        if (userList!=null){
            Iterator<User> iterator = userList.iterator();
            String balanceAll = "0.00";
            BigDecimal bigDecimal = new BigDecimal(balanceAll);
            while (iterator.hasNext()) {
                User user =  iterator.next();
                BigDecimal userBalance = new BigDecimal(user.getBalance());
                bigDecimal = bigDecimal.add(userBalance);
            }
            return bigDecimal.toString();
        }
        return "无用户";
    }

    public List<User> ranking() {
        List<User> userList = adminDao.selectAll();
        Collections.sort(userList, new Comparator<User>() {
            @Override
            public int compare(User o1, User o2) {
                return new BigDecimal(o1.getBalance()).compareTo(new BigDecimal(o2.getBalance()));
            }
        });
        return userList;
    }
}
