package com.example.mail.send;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
@Service
public class GetExcelFile implements GetExcelInterface {

	@Override
	public void createExcel(List<String> el){
		try {
			FileWriter writer = new FileWriter("/Users/ashwinkashyap/Desktop/Op_excel/EmailList.csv");
			String collect = el.stream().collect(Collectors.joining(", "));
			writer.write(collect);
		    writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
