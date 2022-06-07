package com.example.controller.service.base;

import com.example.common.exception.DBException;
import com.example.common.exception.ServiceException;
import com.example.entity.dto.AppLog;
import com.example.entity.dto.CRMUserDto;
import com.example.entity.dto.order.InvTransactionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.FlushModeType;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.servlet.http.HttpSession;

public class BaseDaoService  {


    @Autowired
    HttpSession session;

    @Autowired
    @PersistenceContext
    protected EntityManager entityManager;


    public EntityManager getEntityManager() {
        entityManager.setFlushMode(FlushModeType.COMMIT);
        return entityManager;
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    //Update existing record

    @Transactional(propagation=Propagation.REQUIRED)
    public boolean merge(Object obj) {
       getEntityManager().merge(obj);
        getEntityManager().flush();
       return true;
    }



    @Transactional(propagation= Propagation.REQUIRED)
    public boolean persist(Object obj) {
        getEntityManager().persist(obj);
        getEntityManager().flush();
        return true;
    }

    @Transactional(propagation= Propagation.REQUIRED)
    public boolean refresh(Object obj) {
        getEntityManager().refresh(getEntityManager().contains(obj) ? obj : getEntityManager().merge(obj));
        getEntityManager().flush();
        return true;
    }

    @Transactional(propagation= Propagation.REQUIRED)
    public void remove(Object obj) {

        getEntityManager().remove(getEntityManager().contains(obj) ? obj : getEntityManager().merge(obj));

    }


    public Object find(Class entityClass, Object primaryKey){
        Object entity = null;
        try {

            entity =  getEntityManager().find(entityClass, primaryKey);
        }catch (Exception e) {
            System.out.println("Find entity 112 " + e.getMessage());

        }
        return entity;
    }


    public  <T> TypedQuery<T> createNamedQuery(String query, Class<T> type) throws DBException {
        try {
            return getEntityManager().createNamedQuery(query, type);
        } catch (Exception e) {
            throw new DBException("Unable to create Query : " + query);
        }

    }

    public CRMUserDto getUser() throws ServiceException{
        CRMUserDto userDto = (CRMUserDto) session.getAttribute("user");
        if(userDto == null){
            throw new ServiceException("User Not found. Please Login");
        }
        return  userDto;
    }

    public void addToSessionAppLog(String msg) throws ServiceException{
        AppLog appLog = (AppLog) session.getAttribute("applog");
        if(appLog == null){
            appLog =  new AppLog();
        }
        appLog.getLogs().add(msg);
        session.setAttribute("applog", appLog);
    }

    public Long getTenantId () throws ServiceException {
        return getUser().getTenantid();
    }


    public void validateCompany(InvTransactionDto transaction) {

        //if(transaction.getCustCompany().getType() == CompanyType.SUPPLIER.getType() || )

    }

}
