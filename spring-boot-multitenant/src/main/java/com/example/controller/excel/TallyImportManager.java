package com.example.controller.excel;

import com.example.common.exception.ServiceException;
import com.example.constant.AcGroupNode;
import com.example.controller.excel.dto.TallyAccount;
import com.example.controller.excel.dto.TenantData;
import com.example.controller.manager.AccountManager;
import com.example.controller.manager.CompanyManager;
import com.example.controller.service.AccountGroupService;
import com.example.controller.service.CompanyService;
import com.example.controller.service.base.BaseDaoService;
import com.example.entity.acct.AccountGroup;
import com.example.entity.dto.acct.AccountDto;
import com.example.entity.dto.acct.AccountGroupDto;
import com.example.entity.dto.order.CompanyDto;
import com.example.entity.dto.order.ContactDto;
import com.example.entity.dto.order.ProductDto;
import com.example.entity.mapper.AccountGroupMapper;
import com.example.entity.order.Company;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TallyImportManager extends BaseDaoService {

    @Autowired
    private HttpSession session;

    @Autowired
    private ExcelUtil excelUtil;

    @Autowired
    private AccountGroupService accountGroupService;

    @Autowired
    private AccountManager accountManager;

    @Autowired
    private CompanyService companyService;

    @Autowired
    private CompanyManager companyManager;

    private Workbook workbook;
    private TenantData tenantData = new TenantData();
    private List<AccountGroupDto> accountGroups = null;

    public static final String testFile = "master.xlsx";


    // TODO Pass input file from UI
    public List<TallyAccount> importExcel(long tenantId) {
        Sheet productSheet;
        Sheet accountsSheet = null;
        List<TallyAccount> tallyAccounts = null;

        try {
            Workbook workbook = excelUtil.getWorkbook(testFile);
            Iterator<Sheet> sheetIterator = workbook.sheetIterator();

            while (sheetIterator.hasNext()) {
                Sheet sheet = sheetIterator.next();
                if (sheet.getSheetName().contains("Accounts") || sheet.getSheetName().contains("Ledger Report")) {
                    accountsSheet = sheet;
                }
            }

            tallyAccounts = importTallyAccounts(accountsSheet, tenantId);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return tallyAccounts;

    }

    /** ---------------------------------------------
     * Parse accountSheet into List[TallyAccount]
     * Create All
     *  - groups in Tally Account
     *  - Clients with contact
     *  - Accounts
     *  ---------------------------------------------
     */
    @Transactional
    public List<TallyAccount> importTallyAccounts(Sheet accountsSheet, long tenantId) throws Exception {
        int lastRow = accountsSheet.getLastRowNum();
        List<TallyAccount> tallyAccounts = new ArrayList<>();
        accountManager.createInternalAccountForTenant(tenantId);
        accountGroups = accountGroupService.getAllGroups().stream().map(AccountGroupMapper.mapperEntityToDto).collect(Collectors.toList());

        // Create TallyAccount List
        for(int i = 2;  i < accountsSheet.getLastRowNum(); i++) {
            Row row = accountsSheet.getRow(i);

            // Create TallyAccount object from Excel Row
            TallyAccount tallyAccount = new TallyAccount(row);
            tallyAccounts.add(tallyAccount);
        }

        // Find / Create Groups for tally accounts
        // Create Clinet and Account
        for(TallyAccount tallyAccount : tallyAccounts) {
            if(StringUtils.isEmpty(tallyAccount.getName())) {
                continue;
            }

            tallyAccount.setGroupDto(createGroup(tallyAccount.getGroup(), tallyAccount.getSubgroup(), tenantId));
            if(tallyAccount.getGroupDto() != null && tallyAccount.getGroupDto().isClient()) {
                tallyAccount.setClientDto(createClient(tallyAccount, tenantId));
                AccountDto clientAccount = tallyAccount.getClientDto() != null && tallyAccount.getClientDto().getAccount()!= null ?
                        tallyAccount.getClientDto().getAccount() : null;
                tallyAccount.setAccountDto(clientAccount);
            }else {
                tallyAccount.setAccountDto(createAccount(tallyAccount, tenantId));
            }
        }
        return tallyAccounts;
    }

    //------------------------------------------
    // Create Client and Account (TallyAccount)
    //------------------------------------------
    @Transactional
    public CompanyDto createClient(TallyAccount tallyAccount, long tenantId) {
        CompanyDto client = null;
        if(tallyAccount.getGroupDto() == null || !tallyAccount.getGroupDto().isClient() ) {
            return client;
        }

        try {
            // Find Client
            client = companyService.findByCompanyName(tallyAccount.getName());
            if(client != null) {
                client.setTallyAccountInfo(tallyAccount);
            } else {
                client = new CompanyDto(tallyAccount);
            }
            client = companyManager.saveCompany(client);
            tallyAccount.setClientDto(client);
            client = companyManager.saveCompanyContact(client.getId(), new ContactDto(tallyAccount));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return client;

    }

    //------------------------------------------
    // Create Account (TallyAccount)
    //------------------------------------------
    @Transactional
    public AccountDto createAccount(TallyAccount tallyAccount, long tenantId) {
        AccountDto accountDto = null;
        try {
            accountDto =  accountManager.saveAccount_IfNotExist(new AccountDto(tallyAccount), tenantId);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        tallyAccount.setAccountDto(accountDto);
        return accountDto;
    }


    //----------------------------
    // Create Group (TallyAccount)
    //----------------------------
    @Transactional
    public AccountGroupDto createGroup(String group, String parent, long tenantId) throws Exception {

        AccountGroupDto groupDto = null, parentDto = null, rootDto;

        rootDto = accountGroups.stream().filter(GroupDto -> GroupDto.getCode().equals(AcGroupNode.ROOT.getCode())).findFirst().orElse(null);
        if(rootDto == null) {
            throw new ServiceException("Root group not found");
        }

        // Get group
        if(!StringUtils.isEmpty(group)) {
            groupDto = accountGroups.stream().filter(accountGroupDto -> accountGroupDto.getName().equalsIgnoreCase(group.trim())).findFirst().orElse(null);
        }

        // Get parent
        if(!StringUtils.isEmpty(parent)) {
            parentDto = accountGroups.stream().filter(accountGroupDto -> accountGroupDto.getName().equalsIgnoreCase(parent.trim())).findFirst().orElse(null);
        }

        // IF Group found in existing groups
        if(groupDto != null) {
            return groupDto;
        // IF group is absent but Parent exists : Create group under parent
        } else if(parentDto != null) {
            AccountGroup newGroup = accountGroupService.createChildGroup(parentDto, group, tenantId);
            groupDto =  AccountGroupMapper.mapperEntityToDto.apply(newGroup);
            accountGroups = accountGroupService.getAllGroups().stream().map(AccountGroupMapper.mapperEntityToDto).collect(Collectors.toList());
        // Create group under ROOT
        } else {
            AccountGroup newGroup = accountGroupService.createChildGroup(rootDto, group, tenantId);
            groupDto =  AccountGroupMapper.mapperEntityToDto.apply(newGroup);
            accountGroups = accountGroupService.getAllGroups().stream().map(AccountGroupMapper.mapperEntityToDto).collect(Collectors.toList());
        }
        return groupDto;
    }





    @Deprecated
    public void populateProducts(Sheet productSheet) {

        List<ProductDto> productList = new ArrayList<>();
        productSheet.getLastRowNum();
        for (int i = 1; i < productSheet.getLastRowNum(); i++) {
            Row row = productSheet.getRow(i);
            ProductDto productDto = new ProductDto();
            productDto.setTenantid(tenantData.getTenantDto().getId());
            productDto.setName(row.getCell(0).getStringCellValue());
            productDto.setCostPrice(new BigDecimal(row.getCell(1).getNumericCellValue()));
            productDto.setSalesPrice(new BigDecimal(row.getCell(2).getNumericCellValue()));
            productDto.setCategory(row.getCell(3).getStringCellValue());
            productDto.setGroup(row.getCell(4).getStringCellValue());
            productDto.setGstApplicability(row.getCell(5).getStringCellValue());
            productDto.setCgstRate(new BigDecimal(row.getCell(6).getNumericCellValue()));
            productDto.setHsnCode(row.getCell(7).getStringCellValue());
            productDto.setInternalReference(row.getCell(8).getStringCellValue());
            productDto.setService(row.getCell(9).getBooleanCellValue());
            productDto.setDescription(row.getCell(10).getStringCellValue());
            productDto.setBarcode((long) row.getCell(12).getNumericCellValue());
            productDto.setUom(row.getCell(13).getStringCellValue());

        }
    }

}
