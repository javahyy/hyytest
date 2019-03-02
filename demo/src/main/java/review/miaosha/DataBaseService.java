package review.miaosha;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import review.miaosha.util.DaoUtilJdbcTemplate;

//@Component
public class DataBaseService {
//    @Autowired
//    JdbcTemplate jdbcTemplate;
    JdbcTemplate jdbcTemplate = DaoUtilJdbcTemplate.getSingleJdbcTemplate();
    @Transactional(rollbackFor = Exception.class)
    public boolean buy(String goodsCode, String userId) {
        // 商品数量 减1
        String sql = "update tb_miaosha set goods_num = goods_num -1 where goods_code ='"+goodsCode+"' and goods_num>0";
        int update = jdbcTemplate.update(sql);
        if (update != 1){
            // 秒杀失败
            return false;
        }
        return true;
    }
    /** 获取库存数 */
    public String getCount(String goodsCode) {
        String sql = "SELECT goods_num FROM tb_miaosha WHERE goods_code = '"+goodsCode+"'";
        String count = jdbcTemplate.queryForObject(sql, String.class);
        return count;
    }

    public static void main(String[] args) {
        String bike = new DataBaseService().getCount("bike");
        System.out.println("bike=="+bike);
    }
}
