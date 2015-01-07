package com.chat.application.dao;

import java.util.List;

import com.chat.application.domain.SybilAttack;

public interface ReportDao {
	List<SybilAttack> getSybilAttackReport();

	void addReportData(SybilAttack s);
}
