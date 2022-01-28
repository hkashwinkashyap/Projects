package com.example.mail.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.mail.EmailService.EmailServiceInterface;
import com.example.mail.model.Data;
import com.example.mail.repository.Repo;
import com.example.mail.send.GetExcelInterface;

@RestController
public class MailController {
	@Autowired
	EmailServiceInterface e;

	@Autowired
	GetExcelInterface s;

	@Autowired
	Repo repo;
	private static final Logger logger = LoggerFactory.getLogger(MailController.class);

	@PostMapping("/import")
	public ResponseEntity<String> excelImportAndSendMail(MultipartFile studentsData) throws IOException {
		logger.info("filename " + studentsData.getOriginalFilename());
		List<Data> list = new ArrayList<Data>();
		XSSFWorkbook workbook = new XSSFWorkbook(studentsData.getInputStream());
		XSSFSheet worksheet = workbook.getSheetAt(0);
		for (int i = 2; i <= worksheet.getLastRowNum(); i++) {
			Data tempStudent = new Data();

			XSSFRow row = worksheet.getRow(i);

			tempStudent.setName(row.getCell(0).getStringCellValue());
			tempStudent.setEmail(row.getCell(1).getStringCellValue());
			tempStudent.setPhone((long) row.getCell(2).getNumericCellValue());

			list.add(tempStudent);
		}
		logger.info(list.toString());
		for (int i = 0; i < list.size(); i++) {
			e.sendEmail(list.get(i).getEmail());
		}
		List<String> emailList = new ArrayList<String>();
		for (int i = 0; i < list.size(); i++) {
			emailList.add(list.get(i).getEmail());
		}
		workbook.close();
		repo.saveAll(list);
		return ResponseEntity.ok("Mail Service Started " + emailList.toString());

	}

	@GetMapping("/get")
	public void getExcel() {
		List<String> emails = repo.getEmails();
		logger.info("Get emails" + emails.toString());
		s.createExcel(emails);
	}
}
