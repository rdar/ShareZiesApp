package com.webApp.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.webApp.model.User;

@Repository("userDao")
public class UserDaoImpl extends AbstractDao<Integer, User> implements UserDao {

	static final Logger logger = LoggerFactory.getLogger(UserDaoImpl.class);
	
	public User findById(long id) {
		User user = getByKey(id);		
		return user;
	}

	public User findByEmail(String email) {
		logger.info("email : {}", email);
		Criteria crit = createEntityCriteria();
		crit.add(Restrictions.eq("emailAddress", email));
		User user = (User)crit.uniqueResult();
		
		return user;
	}

	@SuppressWarnings("unchecked")
	public List<User> findAllUsers() {
		Criteria criteria = createEntityCriteria().addOrder(Order.asc("fname"));
		criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);//To avoid duplicates.
		List<User> users = (List<User>) criteria.list();		
		
		return users;
	}

	public void save(User user) {
		persist(user);
	}

	public void deleteByEmail(String email) {
		Criteria crit = createEntityCriteria();
		crit.add(Restrictions.eq("emailAddress", email));
		User user = (User)crit.uniqueResult();
		delete(user);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List<User> findUsersByName(String name) {
		Criteria crit = createEntityCriteria().addOrder(Order.asc("fname"));
		crit.add(Restrictions.like("fname", name, MatchMode.ANYWHERE));
		
		List result=crit.list();
		return result;
	}

	
	@SuppressWarnings("unchecked")
	public List<User> getListByOrName(String name){
	    return sessionFactory.getCurrentSession().createCriteria(User.class)
	                .createAlias("fname", "fname")
	                .createAlias("fname.city", "city")
	                .add(Restrictions.eq("city.name", name))
	                .list();
	}
	
	

}
