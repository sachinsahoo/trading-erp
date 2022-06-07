package com.example.controller.excel;

import com.example.controller.service.base.BaseDaoService;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.io.InputStream;

@Service
public class ExcelUtil extends BaseDaoService {

    @Autowired
    private HttpSession session;

    public ExcelUtil() {
    }


    /**
     * *********************
     * Get Excel Workbook
     * *********************
     */
    public Workbook getWorkbook(String  filePath) {

        try (InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(filePath);
             Workbook workbook = WorkbookFactory.create(is);) {

            return workbook;

        } catch (Exception ex ){
            return null;
        }



    }




}
