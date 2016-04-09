package com.edu.ads.dao.imp;

import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

import com.edu.ads.bean.user.User;
import com.edu.ads.common.page.PageResult;
import com.edu.ads.dao.BaseDao;

public abstract class DaoSupport<T> extends AdsDaoSupport  implements BaseDao<T>  {

	 
	private Class<T> entityClazz = this.getGenTypeParameter(this.getClass());
	
	@Override
	public T find(String id) {
	 return	getHibernateTemplate().get(entityClazz, id);
	}

	public List<T> findList(String querySql , Object[] params){
		return (List<T>)getHibernateTemplate().find(querySql, params);
	}
	
	
	@Override
	public String save(T entity) {
		 getHibernateTemplate().save(entity);
		return null;
	}

	@Override
	public void update(T entity) {
		 getHibernateTemplate().update(entity);
	}

	@Override
	public void delete(T entity) {
		getHibernateTemplate().delete(entity);
	}

	@Override
	public long getCount() {
		String sql ="select count(" + this.getCountField(this.entityClazz) + ") from "+buildEntityName() ;
		return getHibernateTemplate().find(sql).size();
	}
	
	public PageResult<T> getList(int starIndex,int endIndex,Map<String,Object> params,String orderBy){
		//创建查询sql
		StringBuffer queryString = bulidHql(params, orderBy);
		String entityName = buildEntityName();
		Session session =null;
		PageResult<T> pageResult = new PageResult<T>();
		try{
			session = getHibernateSession();
			Query query = session.createQuery(queryString.toString());
			query.setFirstResult(starIndex);
			query.setMaxResults(endIndex);
			pageResult.setRecords(query.list());
			String countSql ="select count(" + this.getCountField(this.entityClazz) + ") from " + entityName + " o "
					+ bulidWhereSql(params);
			pageResult.setTotalRecords(Integer.valueOf(getHibernateTemplate().find(countSql).get(0).toString()));
			return pageResult;
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(session!=null){
				session.close();
			}
		}
		return pageResult;
	}

	private StringBuffer bulidHql(Map<String, Object> params, String orderBy) {
		StringBuffer queryString = new StringBuffer();
		queryString.append("select o from ");
		queryString.append( buildEntityName()).append(" o ");
		queryString.append(bulidWhereSql(params));
		queryString.append(" ").append(orderBy);
		return queryString;
	}
	
	private String bulidWhereSql(Map<String,Object> params){
		StringBuffer whereSql = new StringBuffer(" where 1=1 ");
		Set<Entry<String, Object>> entrys =  params.entrySet();
		for(Entry<String, Object> entry:entrys){
			whereSql.append(" and ");
			whereSql.append(entry.getKey());
			whereSql.append("=");
			whereSql.append(entry.getValue());
		}
		return whereSql.toString();
	}
	
	/**
	 * 通过泛型技术获取泛型类型参数:比如UserServiceBean extends DAOSupporet<User> 将能得知T代表User
	 * 
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private Class<T> getGenTypeParameter(Class<? extends DaoSupport> clazz) {
		Type type = clazz.getGenericSuperclass();
		if (type instanceof ParameterizedType) {
			ParameterizedType genType = (ParameterizedType) type;
			return (Class<T>) genType.getActualTypeArguments()[0];
		} else {
			throw new RuntimeException(clazz + " 没有继承com.asm.base2.DAOSupport类");
		}
	}
	
	
	private static <E> String getCountField(Class<E> clazz) {
		String out = "o";
		try {
			PropertyDescriptor[] propertyDescriptors = Introspector.getBeanInfo(clazz).getPropertyDescriptors();
			for (PropertyDescriptor propertydesc : propertyDescriptors) {
				Method method = propertydesc.getReadMethod();
				if (method != null && method.isAnnotationPresent(EmbeddedId.class)) {
					PropertyDescriptor[] ps = Introspector.getBeanInfo(propertydesc.getPropertyType())
							.getPropertyDescriptors();
					out = "o." + propertydesc.getName() + "."
							+ (!ps[1].getName().equals("class") ? ps[1].getName() : ps[0].getName());
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return out;
	}
	
	/**
	 * 通过实体clazz对象,构建查询的实体名字
	 * 
	 * @return
	 */
	public String buildEntityName() {
		String name = entityClazz.getSimpleName();
		Entity entity = entityClazz.getAnnotation(Entity.class);
		if (entity.name() != null && !"".equals(entity.name().trim())) {
			name = entity.name();
		}
		return name;
	}
	
	
}