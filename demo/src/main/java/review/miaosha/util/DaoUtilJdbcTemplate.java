package review.miaosha.util;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

public class DaoUtilJdbcTemplate {
    // 四大金刚
    private static final String driverClass= "com.mysql.jdbc.Driver" ;// 驱动名称
    private static final String url = "jdbc:mysql:///test" ;// 连接字符串
    private static final String username = "root" ;// 用户名
    private static final String password = "11203080311hy" ;// 密码
    private static final ThreadLocal<JdbcTemplate> t1 = new ThreadLocal<JdbcTemplate>();

    public static JdbcTemplate getJdbcTemplate(){
        //1 设置数据库相关信息
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(driverClass);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        JdbcTemplate JdbcTemplate = new JdbcTemplate(dataSource);
        return JdbcTemplate;
    }

//    static volatile JdbcTemplate jdbcTemplate = null;
    public static JdbcTemplate getSingleJdbcTemplate(){
        JdbcTemplate jdbcTemplate = null;
        try {
            jdbcTemplate = t1.get();
            if(jdbcTemplate == null){
                jdbcTemplate = getJdbcTemplate();
                t1.set(jdbcTemplate);
                // 测试
//                Integer integer = jdbcTemplate.queryForObject("select count(1) from tb_miaosha", Integer.class);
//                System.out.println("aaa==="+integer);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jdbcTemplate;
    }

}
