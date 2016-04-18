package com.edu.ads.dao.order;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.edu.ads.bean.order.Order;
import com.edu.ads.common.page.Page;
import com.edu.ads.dao.imp.DaoSupport;

@Repository
public class OrderDao  extends DaoSupport<Order>{
	
	
	public void updateGgpzt(String ggpid,int ggpzt){
		String sql = "update t_ggp set n_zt =? where c_id=?";
		getJdbcTemplate().update(sql, new Object[]{ggpzt,ggpid});
	}
	
	
	public int getCount(Map<String ,Object> params){
		String sql = bulidCountSql( params);
		return getJdbcTemplate().queryForInt(sql);
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Order> listOrders(Page page,Map<String ,Object> params){
		
		String sql = bulidListSql(page, params);
		return ( List<Order> )getJdbcTemplate().query(sql,  new RowMapper() {
			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				Order order = new Order();
				order.setKhlxr(rs.getString("c_khlxr"));
				order.setKhlxrdh(rs.getString("lxrdh"));
				order.setId(rs.getString("id"));
				order.setCount(rs.getInt("count"));
				order.setKssj(rs.getDate("kssj"));
				order.setJssj(rs.getDate("jssj"));
				order.setDdsj(rs.getDate("ddsj"));
				order.setYsrymc(rs.getString("xsrymc"));
				order.setGgplxmc(rs.getString("lxmc"));
				return order;
			}
		});
		
		
	}
	
	private String bulidCountSql(Map<String ,Object> params){
		StringBuffer sql  = new StringBuffer();
		sql.append(" select count(*) ");
		sql.append(" from t_ggdd dd,t_ggp ggp,t_ry ry");
		sql.append(" where dd.c_ggpid=ggp.c_id and dd.c_xsry=ry.c_id ");
		if(params.containsKey("xsryMc")){
			sql.append(" and ry.c_name like  ");
			sql.append("'%").append(params.get("xsryMc"));
			sql.append("%'");
		}
		if(params.containsKey("khlxr")){
			sql.append(" and dd.c_khlxr like  ");
			sql.append("'%").append(params.get("khlxr"));
			sql.append("%'");
		}
		if(params.containsKey("userid")){
			sql.append(" and dd.c_xsry ='");
			sql.append(params.get("userid")).append("'");
		}
		
		return sql.toString();
	}
	
	
	private String bulidListSql(Page page,Map<String ,Object> params){
		StringBuffer sql  = new StringBuffer();
		sql.append(" select dd.c_khlxr,dd.c_khlxdh lxrdh ,dd.c_id id, dd.n_count count , ");
        sql.append(" dd.d_kssj kssj,dd.d_jssj jssj,dd.d_ddsj ddsj ");
        sql.append(" ,ry.c_name xsrymc,ggp.c_ms lxmc");
		sql.append(" from t_ggdd dd,t_ggp ggp,t_ry ry ");
		sql.append(" where dd.c_ggpid=ggp.c_id and dd.c_xsry=ry.c_id ");
		if(params.containsKey("xsryMc")){
			sql.append(" and ry.c_name like  ");
			sql.append("'%").append(params.get("xsryMc"));
			sql.append("%'");
		}
		if(params.containsKey("khlxr")){
			sql.append(" and dd.c_khlxr like  ");
			sql.append("'%").append(params.get("khlxr"));
			sql.append("%'");
		}
		if(params.containsKey("userid")){
			sql.append(" and dd.c_xsry ='");
			sql.append(params.get("userid")).append("'");
		}
		sql.append(" order by dd.d_ddsj desc ");		
		sql.append("limit ").append(page.getStartIndex());
		sql.append(",").append(page.getPageLength());
		return sql.toString();
		
	}

}
