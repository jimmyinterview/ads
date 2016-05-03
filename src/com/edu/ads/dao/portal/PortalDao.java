package com.edu.ads.dao.portal;

import com.edu.ads.bean.order.Order;
import com.edu.ads.bean.portal.OrderRanger;
import com.edu.ads.dao.imp.DaoSupport;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by R on 2016/4/17.
 */
@Repository
public class PortalDao extends DaoSupport<Order> {

    public List<Order> getAdsOrderRankList(){
        String sql = "select t.c_xsry,t.total_price from (\n" +
                "SELECT t1.c_xsry,SUM(t2.n_jg) total_price FROM `t_ggdd` t1,`t_ggp` t2 where t1.c_ggpid=t2.c_id GROUP BY t1.c_xsry,t2.n_jg ) t\n" +
                "order by t.total_price";
        List<Order> list = (List<Order>)getHibernateTemplate().find(sql);
        return list;
    }

    @SuppressWarnings("unchecked")
	public List<OrderRanger> getOrderAnger(final Date kssj,final Date jssj){
       String sql =	bulidSql();
       final SimpleDateFormat simp = new SimpleDateFormat("yyyy-MM-dd");
       return getJdbcTemplate().query(sql, new Object[]{kssj,jssj}, new RowMapper() {

		@Override
		public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
			OrderRanger ranger = new OrderRanger();
			ranger.setCount(String.valueOf(rs.getInt("xse")));
			ranger.setKssj(simp.format(kssj));
			ranger.setJssj(simp.format(jssj));
			ranger.setName(rs.getString("name"));
			return ranger;
		}
	});
    }

	private String bulidSql() {
		StringBuffer sql = new StringBuffer();
    	sql.append("select  ry.c_id,ry.c_name name,sum(dd.n_count) xse");
    	sql.append(" from t_ggdd dd,t_ry ry ");
    	sql.append(" where ry.c_id = dd.c_xsry ");
    	sql.append(" and dd.d_ddsj>=? ");
    	sql.append(" and dd.d_ddsj<=? ");
    	sql.append(" group by dd.c_xsry ");
    	sql.append(" limit 0,5 ");
    	return sql.toString();
	}
    
	
	@SuppressWarnings("unchecked")
	public List<OrderRanger> getWeekRanger(final Date kssj,final Date jssj){
		String sql = bulidWeekSql();
		  return getJdbcTemplate().query(sql, new Object[]{kssj,jssj}, new RowMapper() {
				@Override
				public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
					OrderRanger ranger = new OrderRanger();
					ranger.setCount(String.valueOf(rs.getInt("su")));
					ranger.setDdsj(rs.getDate("ddsj"));
					return ranger;
				}
			});
	}
	
	private String bulidWeekSql (){
		StringBuffer sql = new StringBuffer();
    	sql.append("select d_ddsj ddsj,");
    	sql.append(" SUM(n_count) su ");
    	sql.append(" from t_ggdd where  ");
    	sql.append("  d_ddsj>=? ");
    	sql.append(" and d_ddsj<=? ");
    	sql.append(" group by d_ddsj ");
    	sql.append(" order by d_ddsj desc  ");
    	sql.append(" limit 0,7 ");
    	return sql.toString();
		
	}
	
}
