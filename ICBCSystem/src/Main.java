import bean.Admin;
import bean.User;
import service.AdminService;
import service.UserService;

import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        while (true) {
            System.out.println("请选择身份:1.管理员 2.用户 3.退出系统");
            Scanner scanner = new Scanner(System.in);
            String s = scanner.next();
            if (s.equals("1")){
                admin();
            }else if(s.equals("2")){
                user();
            }else if(s.equals("3")){
                break;
            }
        }
        System.out.println("爱存不存银行提示:再见了您诶!");
    }

    private static void admin() {
        AdminService adminService = new AdminService();
        while(true){
            System.out.println("输入任意键继续登录,退出请输入1");
            Scanner scanner = new Scanner(System.in);
            String flag = scanner.next();
            if (flag.equals("1")){
                break;
            }
            System.out.println("请输入用户名:");
            scanner = new Scanner(System.in);
            String userName = scanner.next();
            System.out.println("请输入密码:");
            String password = scanner.next();
            Admin admin = adminService.findAdmin(userName,password);
            if (admin != null){
                while(true){
                    System.out.println("1.开户 2.计算总储蓄额 3.储蓄排行 4.退出");
                    String nums = scanner.next();
                    if (nums.equals("1")){
                        System.out.println("请输入开户的用户名:");
                        userName = scanner.next();
                        System.out.println("请输入设置的密码:");
                        password = scanner.next();
                        System.out.println("请输入银行卡卡号:");
                        String cardNo = scanner.next();
                        System.out.println("请输入初始存入金额:");
                        String balance = scanner.next();
                        String result = adminService.account(new User(0,userName,password,cardNo,balance));
                        System.out.println(result);
                    }else if (nums.equals("2")){
                        System.out.println("总储蓄额为:");
                        String balanceAll = adminService.balanceAll();
                        System.out.println(balanceAll);
                    }else if (nums.equals("3")){
                        List<User> userList = adminService.ranking();
                        Iterator<User> iterator = userList.iterator();
                        System.out.println("储蓄排行榜:");
                        int i = 1;
                        while (iterator.hasNext()) {
                            User next = iterator.next();
                            System.out.println(i+". "+next.getUserName());
                            i++;
                        }
                    }else if (nums.equals("4")){
                        break;
                    }
                }
            }else {
                System.out.println("密码或账号不存在,请重新输入`!");
            }
        }
    }

    private static void user() {
        UserService userService = new UserService();
        while(true){
            System.out.println("输入任意键继续登录,退出请输入1");
            Scanner scanner = new Scanner(System.in);
            String flag = scanner.next();
            if (flag.equals("1")){
               break;
            }
            System.out.println("请输入用户名:");
            scanner = new Scanner(System.in);
            String userName = scanner.next();
            System.out.println("请输入密码:");
            String password = scanner.next();
            User user = userService.findUser(userName,password);
            if (user!=null){
                while(true){
                    System.out.println("1.存款 2.取款 3.查询余额 4.转账 5.修改密码 6.退出");
                    String nums = scanner.next();
                    if (nums.equals("1")){
                        System.out.println("请输入存款金额:");
                        String balance = scanner.next();
                        String result = userService.deposit(1,balance,user);
                        System.out.println(result);
                    }else if (nums.equals("2")){
                        System.out.println("请输入取款金额:");
                        String balance = scanner.next();
                        String result = userService.deposit(2,balance,user);
                        System.out.println(result);
                    }else if (nums.equals("3")){
                        System.out.println("您的余额为:");
                        String result = userService.findBalance(user);
                        System.out.println(result);
                    }else if (nums.equals("4")){
                        System.out.println("请输入转账金额:");
                        String balance = scanner.next();
                        System.out.println("请输入对方账户:");
                        String cardNo = scanner.next();
                        String result = userService.transfer(balance,cardNo,user);
                        System.out.println(result);
                    }else if (nums.equals("5")){
                        System.out.println("请输入原密码:");
                        String oldP = scanner.next();
                        System.out.println("请输入新密码:");
                        String newP = scanner.next();
                        System.out.println("请重复新密码:");
                        String reNewP = scanner.next();
                        String result = userService.resivePassword(oldP,newP,reNewP,user);
                        System.out.println(result);
                        if (result.equals("修改成功!请重新登录")){
                            user = null;
                            break;
                        }
                    }else if(nums.equals("6")){
                        break;
                    }
                }
            } else {
                System.out.println("密码或账号不存在!请重新输入`!");
            }
        }
    }
}
