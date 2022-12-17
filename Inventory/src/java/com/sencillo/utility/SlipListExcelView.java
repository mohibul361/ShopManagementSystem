package com.sencillo.utility;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import com.sencillo.model.Slip;

public class SlipListExcelView extends AbstractExcelView {

    @Override
    protected void buildExcelDocument(Map model, HSSFWorkbook workbook,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        CellStyle cellStyle = workbook.createCellStyle();
        CreationHelper createHelper = workbook.getCreationHelper();
        cellStyle.setDataFormat(createHelper.createDataFormat().getFormat("m/d/yy"));

        HSSFSheet excelSheet = workbook.createSheet("Slip List");
        setExcelHeader(excelSheet);

        List<Slip> slipList = (List) model.get("slipList");
        setExcelRows(excelSheet, slipList, cellStyle);

    }

    public void setExcelHeader(HSSFSheet excelSheet) {
        HSSFRow excelHeader = excelSheet.createRow(0);
        excelHeader.createCell(0).setCellValue("Slip Number");
        excelHeader.createCell(1).setCellValue("Slip Type");
        excelHeader.createCell(2).setCellValue("Slip Date");
        excelHeader.createCell(3).setCellValue("Created By");
        excelHeader.createCell(4).setCellValue("Remarks");
    }

    public void setExcelRows(HSSFSheet excelSheet, List<Slip> slipList, CellStyle cellStyle) {

        /*	CellStyle cellStyle = wb.createCellStyle();
		CreationHelper createHelper = wb.getCreationHelper();
		cellStyle.setDataFormat(
		    createHelper.createDataFormat().getFormat("m/d/yy h:mm"));
		cell = row.createCell(1);
		cell.setCellValue(new Date());
		cell.setCellStyle(cellStyle);*/
        int record = 1;

        for (Slip slip : slipList) {
            HSSFRow excelRow = excelSheet.createRow(record++);
            excelRow.createCell(0).setCellValue(slip.getSlipNumber());
            excelRow.createCell(1).setCellValue(slip.getSlipType().name());
            HSSFCell cell = excelRow.createCell(2);
            cell.setCellValue(slip.getSlipDate());
            cell.setCellStyle(cellStyle);
            excelRow.createCell(3).setCellValue(slip.getCreatedBy().getFullName());
            excelRow.createCell(4).setCellValue(slip.getRemarks());
        }
    }
}
