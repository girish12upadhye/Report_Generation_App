package in.girishTechUniverse.service;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import in.girishTechUniverse.entity.CitizenPlan;
import in.girishTechUniverse.request.SearchRequest;

public interface ReportService {

	public List<String> getPlanName();
	public List<String> getPlanStatus();
	public List<CitizenPlan> search(SearchRequest request);
	public boolean exportExcel(HttpServletResponse response) throws Exception;
	public boolean exportPdf(HttpServletResponse response) throws Exception;
	
}
