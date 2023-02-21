package dao;

import bean.Admin;
import bean.User;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import utils.DruidUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class AdminDao {
    private QueryRunner queryRunner = new QueryRunner();
    private static final String FRAGMENT = "id,username as userName,password,cardNo,balance";

    public Admin selectOneAdmin(String userName, String password) {
        Connection conn = DruidUtil.getConnection();
        String sql = "select username as userName,password from admin where username=? and password = ?";
        try {
            return queryRunner.query(conn,sql,new BeanHandler<Admin>(Admin.class),userName,password);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DruidUtil.close(conn);
        }
        return null;
    }

    public User selectByName(String userName) {
        Connection conn = DruidUtil.getConnection();
        String sql = "select "+FRAGMENT+" from user where username = ?";
        try {
            User query = queryRunner.query(conn, sql, new BeanHandler<>(User.class), userName);
            return query;
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DruidUtil.close(conn);
        }
        return null;
    }

    public int insertUser(User user) {
        Connection conn = DruidUtil.getConnection();
        String sql = "insert into user(username ,password,cardNo,balance) values (?,?,?,?)";
        try {
            int update = queryRunner.update(conn, sql, user.getUserName(), user.getPassword(), user.getCardNo(), user.getBalance());
            return update;
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DruidUtil.close(conn);
        }
        return -1;
    }

    public List<User> selectAll() {
        Connection conn = DruidUtil.getConnection();
        String sql = "select "+FRAGMENT+" from user";
        try {
            List<User> query = queryRunner.query(conn, sql, new BeanListHandler<User>(User.class));
            return query;
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            DruidUtil.close(conn);
        }
        return null;
    }
}
