package com.example.dao;

import java.util.List;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import com.example.model.Reimbursement;
import com.example.model.ReimbursementStatus;
import com.example.model.Users;
import com.example.util.HibernateUtil;

public class ReimbursementDaoHib implements ReimbursementDao{

	public ReimbursementDaoHib() {
		
	}
	@Override
	public void create(Reimbursement r) {
		Session ses = HibernateUtil.getSession();
		Transaction tx = ses.beginTransaction();
		
		ses.save(r);
		tx.commit();
	}
	@Override
	public Reimbursement updateReim(Reimbursement reimb) {
		
			Session ses = HibernateUtil.getSession();
			Transaction tx = ses.beginTransaction();
			ses.update(reimb);
			tx.commit();
			return ses.get(Reimbursement.class, reimb.getReimbId());
		}
		
	@Override
	public Reimbursement selectByReimbId(int reimbId) {
		Session ses = HibernateUtil.getSession();
		Query q = ses.createQuery("from Reimbursement where id=:reimb_id");
		q.setInteger("id", reimbId);
		Reimbursement reim = (Reimbursement) q.uniqueResult();
		return reim;
	}
	@Override
	public List<Reimbursement> selectAllReimb() {
		Session ses = HibernateUtil.getSession();
		List<Reimbursement> rList = ses.createQuery("from Reimbursement", Reimbursement.class).list();
		return rList;
	}
	@Override
	public List<Reimbursement> selectByPending() {
		Session ses = HibernateUtil.getSession();
		String sql = "SELECT * FROM reimb WHERE status =:status_fk";
		SQLQuery query = HibernateUtil.getSession().createSQLQuery(sql);
		query.addEntity(Reimbursement.class);
		query.setParameter("status", new ReimbursementStatus(1, "PENDING"));
		return query.list();
	}
	@Override
	public List<Reimbursement> selectByResolved() {
		Session ses = HibernateUtil.getSession();
		String sql = "SELECT * FROM reimb WHERE status NOT =:status_fk";
		SQLQuery query = HibernateUtil.getSession().createSQLQuery(sql);
		query.addEntity(Reimbursement.class);
		query.setParameter("status", new ReimbursementStatus(1, "PENDING"));
		return query.list();
	}

	
}
