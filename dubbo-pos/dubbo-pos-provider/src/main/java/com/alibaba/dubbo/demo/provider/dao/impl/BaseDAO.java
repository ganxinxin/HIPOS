package com.alibaba.dubbo.demo.provider.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.transform.Transformers;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.alibaba.dubbo.demo.bean.PageBean;
/**
 * @author LangXianWei
 * @version 创建时间：May 28, 2016 3:46:13 PM 类说明
*/

@SuppressWarnings("unused")
public class BaseDAO extends HibernateDaoSupport {
	Logger logger = Logger.getLogger(BaseDAO.class);
	
	protected PageBean getPageBean(String hql, String countHql,
			PageBean pageBean) {
		Session session = this.getSession() ;
		try {
			Query query = session.createQuery(hql.toString());
			System.out.println("getPageBean hql="+hql);
			query.setFirstResult((pageBean.getPage() - 1)
					* pageBean.getPageSize());
			query.setMaxResults(pageBean.getPageSize());

			// 获取需要显示的结果和符合条件的总数
			pageBean.setResultList(query.list());
			pageBean.setTotal((Long) getHibernateTemplate().find(
					countHql.toString()).listIterator().next());

			// 计算pageBean的其他信息
			pageBean.compute();
			this.getSession().close();
		} catch (Exception e) {
			logger.error("查询出错：" + hql.toString(),e) ;
			System.out.println("查询出错：" + hql.toString()+" \ne.getMessage()"+e.getMessage());
		} finally {
			session.close();
		}
		return pageBean;
	}
	
	protected PageBean getPageBeanBySql(String sql,PageBean pageBean) {
		Session session = this.getSession() ;
		try {
			SQLQuery query=session.createSQLQuery( sql );
			
			long count = query.list().size();
			query.setFirstResult((pageBean.getPage() - 1)
					* pageBean.getPageSize());
			query.setMaxResults(pageBean.getPageSize());
			
			// 获取需要显示的结果和符合条件的总数
			pageBean.setResultList(query.list());
			pageBean.setTotal(count);

			// 计算pageBean的其他信息
			pageBean.compute();
			this.getSession().close();
		} catch (Exception e) {
			logger.error("查询出错：" + sql,e) ;
			System.out.println("查询出错：" + sql.toString()+" \ne.getMessage()"+e.getMessage());
		} finally {
			session.close();
		}
		return pageBean;
	}
	
	
	protected boolean getQueryPageBean(String hql, String countHql,
			PageBean pageBean) {
		boolean result = false ;
		Session session = this.getSession() ;
		try {
			Query query = session.createQuery(hql.toString());
			query.setFirstResult((pageBean.getPage() - 1)
					* pageBean.getPageSize());
			query.setMaxResults(pageBean.getPageSize());

			// 获取需要显示的结果和符合条件的总数
			pageBean.setResultList(query.list());
			pageBean.setTotal((Long) getHibernateTemplate().find(
					countHql.toString()).listIterator().next());

			// 计算pageBean的其他信息
			pageBean.compute();
			this.getSession().close();
			result = true;
		} catch (Exception e) {
			logger.error("查询出错：" + hql.toString(),e) ;
			System.out.println("查询出错：" + hql.toString()+" \ne.getMessage()"+e.getMessage());
			result = false ;
		} finally {
			session.close();
		}
		return result;
	}
	protected boolean queryBean(String hql, String countSql,
			PageBean pageBean) {
		boolean result = false ;
		Session session = this.getSession() ;
		try {
			Query query = session.createQuery(hql.toString());

			// 获取需要显示的结果和符合条件的总数
			pageBean.setResultList(query.list());
			this.getSession().close();
			result = true;
		} catch (Exception e) {
			logger.error("查询出错：" + hql.toString(),e) ;
			System.out.println("查询出错：" + hql.toString()+" \ne.getMessage()"+e.getMessage());
			result = false ;
		} finally {
			session.close();
		}
		return result;
	}
	
	protected List queryBean(String hql) {
		Session session = this.getSession() ;
		try {
			Query query = session.createQuery(hql.toString());
			// 获取需要显示的结果和符合条件的总数
			return query.list();
		} catch (Exception e) {
			logger.error("查询出错：" + hql.toString(),e) ;
			System.out.println("查询出错：" + hql.toString()+" \ne.getMessage()"+e.getMessage());
		} finally {
			session.close();
		}
		return new ArrayList();
	}
	protected List queryBean(String hql,int max) {
		Session session = this.getSession() ;
		if(max<1)
			max = 1;
		try {
			Query query = session.createQuery(hql.toString());
			query.setMaxResults(max);
			// 获取需要显示的结果和符合条件的总数
			return query.list();
		} catch (Exception e) {
			logger.error("查询出错：" + hql.toString(),e) ;
			System.out.println("查询出错：" + hql.toString()+" \ne.getMessage()"+e.getMessage());
		} finally {
			session.close();
		}
		return new ArrayList();
	}	
	protected List queryBeanBySql(String sql) 
	{
		Session session = this.getSession() ;
		try {
			SQLQuery query=session.createSQLQuery( sql );
			// 获取需要显示的结果和符合条件的总数
			return query.list();
		} catch (Exception e) {
			logger.error("查询出错：" + sql,e) ;
			System.out.println("查询出错：" + sql.toString()+" \ne.getMessage()"+e.getMessage());
		} finally {
			session.close();
		}
		return new ArrayList();
	}
	protected List queryBeanBySql(String sql,int max) 
	{
		Session session = this.getSession() ;
		if(max<1)
			max = 1;
		try {
			SQLQuery query=session.createSQLQuery( sql );
			query.setMaxResults(max);
			// 获取需要显示的结果和符合条件的总数
			return query.list();
		} catch (Exception e) {
			logger.error("查询出错：" + sql,e) ;
			System.out.println("查询出错：" + sql.toString()+" \ne.getMessage()"+e.getMessage());
		} finally {
			session.close();
		}
		return new ArrayList();
	}	
	
	protected boolean deleteBySql(String hql)
	{
		Session session = this.getSession() ;
		
		try {
			Query query=session.createQuery(hql);
			query.executeUpdate();
			session.beginTransaction().commit();
			return true;
		} catch (Exception e) {
			logger.error("删除数据出错：" + hql,e) ;
			System.out.println("查询出错：" + hql.toString()+" \ne.getMessage()"+e.getMessage());
		} finally {
			session.close();
		}
		return false;
	}
	
	protected List getNativeQueryPageBean(String hql) {
		Session session = this.getSession() ;
		Query query = null ;
		List list = new ArrayList();
		try {
			query = session.createSQLQuery(hql.toString()).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			list = query.list();
		} catch (Exception e) {
			logger.debug("查询出错：" + hql,e) ;
			System.out.println("查询出错：" + hql.toString()+" \ne.getMessage()"+e.getMessage());
		} finally {
			session.close();
		}
		return list;
	}
	
	protected boolean delete(String sql) {
		
		Session session = this.getSession();
		try {
			session.createQuery(sql).executeUpdate();
			return true;
		} catch (Exception e) {
			logger.error("删除错误", e);
			System.out.println("查询出错：" + sql.toString()+" \ne.getMessage()"+e.getMessage());
		}finally{
			session.close();
		}
		return false;
	}
	
	protected int excuteBySql(String sql)    
    {    
        int result ;    
        SQLQuery query = this.getSession().createSQLQuery(sql);    
        System.out.println("excuteBySql sql="+sql);
        result = query.executeUpdate();    
        return result;    
    }  
}
