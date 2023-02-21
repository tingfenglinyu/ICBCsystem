package dao;

import bean.User;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import utils.DruidUtil;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;

public class UserDao {
    private static final String FRAGMENT = "id,username as userName,password,cardNo,balance";
    private QueryRunner queryRunner = new QueryRunner();

    public User findOneUser(String userName, String password) {
        Connection conn = DruidUtil.getConnection();
        String sql = "select "+FRAGMENT+" from user where username = ? and password = ?";
        try {
            return queryRunner.query(conn, sql, new BeanHandler<User>(User.class), userName, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DruidUtil.close(conn);
        }
        return null;
    }

    public String updateBalance(int i, String balance, User user) {

        Connection conn = DruidUtil.getConnection();
        String sql = "update user set balance = "+balance+" where username="+user.getUserName()+" and password = "+ user.getPassword();
        try {
            queryRunner.update(conn, sql);
            if (i == 1){
                return "存款成功!";
            }else{
                return "取款成功!";
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return "其他错误!在UserDao.updateBalance方法中";
        }finally {
            DruidUtil.close(conn);
        }
    }

    public String selectBalance(User user) {
        Connection conn = DruidUtil.getConnection();
        String sql = "select balance from user where id = "+user.getId();
        try {
            return queryRunner.query(conn, sql, new BeanHandler<User>(User.class)).getBalance().toString();
        } catch (SQLException e) {
            e.printStackTrace();
            return "发生了一个意料之外的错误,在UserDao.selectBalance!";
        }finally {
            DruidUtil.close(conn);
        }
    }

    public User selectCardNo(String cardNo) {
        Connection conn = DruidUtil.getConnection();
        String sql = "select "+FRAGMENT+" from user where cardNo = ?";
        try {
            return queryRunner.query(conn, sql, new BeanHandler<User>(User.class), cardNo);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DruidUtil.close(conn);
        }
        return null;
    }

    public String transfer(String balance, User userByCardNo, User user) {
        Connection conn = DruidUtil.getConnection();
        try {
            conn.setAutoCommit(false);
            //收款人
            String s = userByCardNo.getBalance();
            //打款人
            String d = user.getBalance();

            s = new BigDecimal(s).add(new BigDecimal(balance)).toString();
            d = new BigDecimal(s).subtract(new BigDecimal(balance)).toString();

            String sql = "update user set balance = ? where id = ?";
            queryRunner.update(conn, sql, s, userByCardNo.getId());
            queryRunner.update(conn,sql,d,user.getId());

            conn.commit();
            return "转账成功!";
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();

        }finally {
            DruidUtil.close(conn);
        }
        return "转账失败,位置:UserDao.transfer";


    }

    public String updatePassword(String reNewP, User user) {
        Connection conn = DruidUtil.getConnection();
        String sql = "update user set password = ? where id = ?";
        try {
            queryRunner.update(conn,sql,reNewP,user.getId());
            return "修改成功!请重新登录";
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DruidUtil.close(conn);
        }
        return "修改失败!位置:UserDao.updatePassword";
    }
}
