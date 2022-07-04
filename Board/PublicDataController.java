package kh.board.publicdata;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

@Controller
@RequestMapping(value="/public")
public class PublicDataController {
	@Autowired
	private PublicDataService service;
	
	@RequestMapping(value="/toPublicHome")
	public String toPublicHome() throws Exception{
		return "publicdata/publicHome";
	}
	
	@RequestMapping(value="/toJSON")
	public String toJSON(Model model) throws Exception{
		String rs = service.getJSON();
		// ObjectMapper 클래스를 사용하여 map 형식으로 데이터를 변환
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> map = mapper.readValue(rs, Map.class);
		model.addAttribute("data", map);
		return "publicdata/publicJson";
	}
	@RequestMapping(value="/toXML")
	public String toXML(Model model) throws Exception{
		String rs = service.getXML();
		//XMLMapper 클래스를 사용해 map 형식으로 데이터를 변환
		XmlMapper mapper = new XmlMapper();
		Map<String, Object> map = mapper.readValue(rs, Map.class);
		model.addAttribute("data", map);
		return "publicdata/publicXml";
	}
	@RequestMapping(value="/toAJAX")
	public String toAJAX() throws Exception{
		return "publicdata/publicAjax";
	}
	
	@RequestMapping(value="/getJSON", produces="application/json; charset=UTF-8")
	@ResponseBody
	public String getJSON() throws Exception{
		String data = service.getJSON();
		return data;
	}
	@RequestMapping(value="/getXML", produces="application/xml; charset=UTF-8")
	@ResponseBody
	public String getXML() throws Exception{
		String data = service.getXML();
		return data;
	}
}
