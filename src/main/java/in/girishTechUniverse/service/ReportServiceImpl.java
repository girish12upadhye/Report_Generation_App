package in.girishTechUniverse.service;


import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import java.util.List;


import javax.servlet.http.HttpServletResponse;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;


import in.girishTechUniverse.entity.CitizenPlan;
import in.girishTechUniverse.repo.CitizenPlanRepository;
import in.girishTechUniverse.request.SearchRequest;
import in.girishTechUniverse.util.EmailUtils;
import in.girishTechUniverse.util.ExcelGenerator;
import in.girishTechUniverse.util.PdfGenerator;

@Service
public class ReportServiceImpl implements ReportService {

	@Autowired
	private CitizenPlanRepository planRepo;
	
	@Autowired
	private ExcelGenerator excelGenerator;
	
	@Autowired
	private PdfGenerator pdfGenerator;

	@Autowired
	private EmailUtils emailUtils;
	
	@Override
	public List<String> getPlanName() {
		return planRepo.getPlanNames();
	}

	@Override
	public List<String> getPlanStatus() {
		return planRepo.getPlanStatus();
	}

	@Override
	public List<CitizenPlan> search(SearchRequest request) {
		CitizenPlan entity = new CitizenPlan();
//		BeanUtils.copyProperties(request, entity);
		if (null != request.getPlanName() && !"".equals(request.getPlanName())) {
			entity.setPlanName(request.getPlanName());
		}
		if (null != request.getPlanStatus() && !"".equals(request.getPlanStatus())) {
			entity.setPlanStatus(request.getPlanStatus());
		}
		if (null != request.getGender() && !"".equals(request.getGender())) {
			entity.setGender(request.getGender());
		}
		if (null != request.getStartDate() && !"".equals(request.getStartDate())) {

			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

			String startDate = request.getStartDate();

			LocalDate localDate = LocalDate.parse(startDate, formatter);

			entity.setPlanStartDate(localDate);
		}
		if (null != request.getEndDate() && !"".equals(request.getEndDate())) {

			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

			String endDate = request.getEndDate();

			LocalDate localDate = LocalDate.parse(endDate, formatter);

			entity.setPlanEndDate(localDate);
		}

		return planRepo.findAll(Example.of(entity));
	}

	@Override
	public boolean exportExcel(HttpServletResponse response) throws Exception {

		List<CitizenPlan> plans = planRepo.findAll();
		File f = new File("plans.xls");
		
		excelGenerator.generate(response, plans, f);
		
		String subject = "This is Text Mail Subject";
		String body = "<h1>This is Text Mail Body</h1>";
		String to = "girishupadhye12@gmail.com";
		
		
		emailUtils.sendEmail(subject, body, to, f);
		
		f.delete();

		return true;
	}

	@Override
	public boolean exportPdf(HttpServletResponse response ) throws Exception {

		List<CitizenPlan> plans = planRepo.findAll();
		File f = new File("plans.pdf");
		pdfGenerator.generate(response, plans,f);
		
		String subject = "This is Text Mail Subject";
		String body = "<h1>This is Text Mail Body</h1>";
		String to = "girishupadhye12@gmail.com";
		
		emailUtils.sendEmail(subject, body, to, f);
		f.delete();
		
		return true;
	}

}
