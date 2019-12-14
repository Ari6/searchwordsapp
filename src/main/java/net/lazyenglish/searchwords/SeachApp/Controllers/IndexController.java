package net.lazyenglish.searchwords.SeachApp.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import net.lazyenglish.searchwords.SeachApp.Models.ReceiveForm;
import net.lazyenglish.searchwords.SeachApp.Models.Result;
import net.lazyenglish.searchwords.SeachApp.Utilities.SearchWordUtility;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
@Controller
public final class IndexController {
	@GetMapping("/searchwords")
	public String getIndex(Model model) {
		return "index";
	}
	
	@PostMapping("/searchwords")
	public String searchWords(@ModelAttribute ReceiveForm form, Model model) {
		String[] splitedByLinebreaks = form.getWords().split("\\r?\\n");
		List<String> splited = new ArrayList<>();
		for(String str : splitedByLinebreaks) {
			String[] strArray = str.split("\\s");
			splited.addAll(new ArrayList<>(Arrays.asList(strArray)));
		}
		if(splited.size() == 0) {
			String errorMessage = "Please input at least one word.";
			model.addAttribute("error", errorMessage);
			return "index";
		}
		if(SearchWordUtility.isOverWords(splited)) {
			String errorMessage = "You have exceeded " + SearchWordUtility.MAX_WORDS + " words.";
			model.addAttribute("error", errorMessage);
			return "index";
		} else {
			List<Result> resultArray = new ArrayList<>();
			for(String str : splited) {
				String fromApi = SearchWordUtility.request(str);
				Result result = SearchWordUtility.convertJSONToObject(str, fromApi);
				resultArray.add(result);
			}
			model.addAttribute("results", resultArray);
			return "searchresult";
		}
	}
}
