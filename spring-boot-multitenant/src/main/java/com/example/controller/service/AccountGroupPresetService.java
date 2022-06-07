//package com.example.controller.service;
//
//import com.example.constant.AcCategory;
//import com.example.constant.AcGroup;
//import com.example.controller.service.base.BaseDaoService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.transaction.annotation.Transactional;
//
//import javax.servlet.http.HttpSession;
//
//public class AccountGroupPresetService extends BaseDaoService {
//    @Autowired
//    HttpSession session;
//
//    @Autowired
//    AccountGroupService accountGroupService;
//
//
//    @Transactional
//    public void createDefaultGroups() throws Exception{
//
//        for(AcCategory acCategory : AcCategory.values()) {
//            accountGroupService.saveCategoryIfNotExist(acCategory);
//        }
//
//        for(AcGroup acGroup : AcGroup.values()) {
//            accountGroupService.saveGroup(acGroup);
//        }
//
//    }
//}
