package com.chat.application.service;

import java.util.List;

import com.chat.application.domain.SybilAttack;

public interface ReportService {
	List<SybilAttack> getSybilAttackReport();

	void addReportData(SybilAttack s);

}
