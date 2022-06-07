package com.example.controller.service;

import com.example.common.exception.DBException;
import com.example.common.exception.ServiceException;
import com.example.constant.AcGroupNode;
import com.example.controller.service.base.BaseDaoService;
import com.example.entity.acct.AccountGroup;
import com.example.entity.dto.acct.AccountGroupDto;
import com.example.entity.mapper.AccountGroupMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.TypedQuery;
import java.util.List;


@Service
public class AccountGroupService extends BaseDaoService {

    @Transactional
    public List<AccountGroup> getAllGroups() throws Exception {

//     AccountGroup group = null;
        TypedQuery<AccountGroup> findAcGrp = createNamedQuery(AccountGroup.Q_LIST, AccountGroup.class);
        findAcGrp.setParameter("tenantid", getTenantId());
        List<AccountGroup> groups = findAcGrp.getResultList();
        return groups;
    }

    @Transactional
    public AccountGroup savegroup(AccountGroupDto groupDto) throws Exception {      //does exception or serviceexception matter?
        AccountGroup group = null;
        if (groupDto.getId() != null) {
            group = (AccountGroup) find(AccountGroup.class, groupDto.getId());
        } else {
            group = new AccountGroup();
        }
        group = AccountGroupMapper.mapDtoToEntity.apply(groupDto, group);
        group.setTenantid(getTenantId());
        if (groupDto.getId() == null) {
            persist(group);
        } else {
            merge(group);
        }
        return group;
    }



    @Transactional
    public AccountGroup createChildGroup(AccountGroupDto parentGroup, String groupName, long tenantId) {

        AccountGroup accountGroup = new AccountGroup(groupName, parentGroup);
        persist(accountGroup);
        return accountGroup;
    }




    /** ---------------------------------
     *  Save Internal Group Node for new Tenant
     *  ---------------------------------
     */
    @Transactional
    public AccountGroup saveAccountGroupNode_ifNotExists(AcGroupNode groupNode, long tenantId) throws Exception {      //does exception or serviceexception matter?

        // Find group by code
        AccountGroup accountGroup = findGroupByCode(groupNode.getCode(), tenantId);
        if(accountGroup == null) {

            AccountGroup parent = null;
            accountGroup = new AccountGroup(groupNode);

            // Set parent ID
            if(groupNode.equals(AcGroupNode.ROOT)) {
                accountGroup.setParentid(null);
            } else {
                parent = findGroupByCode(groupNode.getParent().getCode(), tenantId);
                if(parent == null) {
                    throw new ServiceException("Unable to find parent for " + groupNode.getDesc());
                } else {
                    accountGroup.setParentid(parent.getId());
                }
            }
            accountGroup.setTenantid(tenantId);
            persist(accountGroup);
        }
        return accountGroup;
    }


    /** --------------------------------------
     *  Find AccountGroup by (code, tenantId)
     *  --------------------------------------
     */
    public AccountGroup findGroupByCode(Integer code, long tenantId) throws ServiceException, DBException {
        TypedQuery<AccountGroup> accountGroupTypedQuery = createNamedQuery(AccountGroup.Q_BY_CODE, AccountGroup.class);
        accountGroupTypedQuery.setParameter("tenantid", tenantId);
        accountGroupTypedQuery.setParameter("code", code);
        List<AccountGroup> groups = accountGroupTypedQuery.getResultList();
        if(groups != null && groups.size() > 0){
            return groups.get(0);
        } else
            return null;

    }

    @Transactional(propagation = Propagation.REQUIRED)
    public AccountGroupDto findByGroupName(String name, long tenantId) {
        AccountGroupDto accountGroupDto = null;
        try {
            TypedQuery<AccountGroup> findName = createNamedQuery(AccountGroup.Q_Find_BY_NAME, AccountGroup.class);
            findName.setParameter("tenantid", tenantId);
            findName.setParameter("name", name);
            AccountGroup group = findName.getSingleResult();
            accountGroupDto = AccountGroupMapper.mapperEntityToDto.apply(group);

        } catch (Exception e) {
            // Log Exception
        }
        return accountGroupDto;
    }


}
