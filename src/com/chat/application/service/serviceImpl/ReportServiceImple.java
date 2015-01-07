package com.chat.application.service.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.chat.application.dao.ReportDao;
import com.chat.application.domain.SybilAttack;
import com.chat.application.service.ReportService;

@Service(value="reportService")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class ReportServiceImple implements ReportService {
	@Autowired
	ReportDao reportDao;
	
	public List<SybilAttack> getSybilAttackReport() {
		return reportDao.getSybilAttackReport();
	}

	@Transactional(propagation = Propagation.REQUIRED, readOnly = false)
	public void addReportData(SybilAttack s) {
		reportDao.addReportData(s);
		
	}

}
