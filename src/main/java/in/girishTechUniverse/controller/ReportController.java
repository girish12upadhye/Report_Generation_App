package in.girishTechUniverse.controller;

import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import in.girishTechUniverse.entity.CitizenPlan;
import in.girishTechUniverse.request.SearchRequest;
import in.girishTechUniverse.service.ReportService;

@Controller
public class ReportController {

	@Autowired
	private ReportService service;

	@GetMapping("/excel")
	public void exportExcel(HttpServletResponse response, Model model) throws Exception {
		response.setContentType("application/octet-stream");
		response.addHeader("Content-Disposition", "attachment; filename=plans.xlsx");
		
		boolean status = service.exportExcel(response);
		if (status) {
			model.addAttribute("msg", "Excel Report Sent To Your Mail");
		}
	}
	
	@GetMapping("/pdf")
	public void exportPdf(HttpServletResponse response, Model model) throws Exception {
		response.setContentType("application/pdf");
		response.addHeader("Content-Disposition", "attachment; filename=plans.pdf");
		
		boolean status = service.exportPdf(response);
		
		if (status) {
			model.addAttribute("msg", "PDF Report Sent To Your Mail");
		}
	}
	
	@PostMapping("/search")
	public String handleSearch(@ModelAttribute
					("search")SearchRequest search, Model model) {
		System.out.println(search);
//		model.addAttribute("search", search); if use this exclude @ModelAttribute in parameter 
		List<CitizenPlan> plans = service.search(search);
		model.addAttribute("plans", plans);
		
		init(model);
		return "index";
	}
	@GetMapping("/")
	public String indexPage(Model model) {
		model.addAttribute("search", new SearchRequest());
		init(model);
		
		return "index";
	}
	private void init(Model model) {
//		model.addAttribute("search", new SearchRequest());
		model.addAttribute("names", service.getPlanName());
		model.addAttribute("status", service.getPlanStatus());
	}
}
