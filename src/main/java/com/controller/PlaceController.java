package com.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.model.Place;
import com.service.PlaceServ;

@RestController
public class PlaceController {
	
	
	@Autowired
	private PlaceServ pser;

	@PostMapping("/saveplace")
	public String meThod(@RequestBody Place place,HttpServletRequest req)
	{
		
		pser.save(place);
		int id = place.getpId();
		System.out.println("id "+id);
		HttpSession sess=req.getSession();
		sess.setAttribute("val", id);
		return "data added successfully";
	}
	//jsp save place
	@RequestMapping("/savplace")
	public ModelAndView savPlac(@ModelAttribute("place") Place place,@RequestParam("file") MultipartFile file,HttpServletRequest req)
	{
		
			String folder="C:/Users/UDHAYA/Desktop/all/udhaysts/samplebms/src/main/resources/static/";
			try {
				byte[] bytes = file.getBytes();
				Path path = Paths.get(folder+file.getOriginalFilename());
				Files.write(path,bytes);
			/*
			 * Place pd = new Place(); pd.setpName(place.getpName());
			 * pd.setImgs(file.getOriginalFilename()); pd.setpId(place.getpId());
			 * pd.setMovie(place.getMovie());
			 */
				place.setImgs(file.getOriginalFilename());
				pser.save(place);
				System.out.println("Image Uploaded SuccessFully");
				
		
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	
		int id = place.getpId();
		System.out.println("id "+id);
		HttpSession sess=req.getSession();
		sess.setAttribute("val", id);
		return new ModelAndView("redirect:/admin");
		}
	
	@GetMapping("/listval")
	public List<Place> getall(HttpServletRequest req)
	{
		List<Place> lis = pser.fetchPlaceAll();
		//HttpSession sess=req.setAttribute("lisplace", o);
		return lis;
	}
	@RequestMapping("/listplace")
	public ModelAndView getplaces(@ModelAttribute("lismovies") Place place,HttpServletRequest req)
	{
		List<Place> lis = pser.fetchPlaceAll();
		/*
		 * HttpSession sesroless = req.getSession(); String name = (String)
		 * sesroless.getAttribute("nameuser");
		 */
		return new ModelAndView("listplace","lspl",lis);
	}
	@RequestMapping("/ediplace")
	public ModelAndView editPlace()
	{
		List<Place> pl = pser.fetchPlaceAll();
		
		return new ModelAndView("editplac","pl",pl);
	}
@RequestMapping("/editplcid/{id}")
public ModelAndView editplid(@ModelAttribute("editplace") Place place,@PathVariable("id") int id)
{
	Place plc = pser.findbyId(id);
	return new ModelAndView("editrowplace","plc",plc);
}
@RequestMapping("/edplid/{id}")
public ModelAndView eplid(@PathVariable("id") int id,@ModelAttribute("editplace") Place place)
{
	Place plx =pser.findbyId(id);
	if(plx.getpId()==id)
	{
		plx.setpName(place.getpName());
		pser.update(plx);
		
	}
	return new ModelAndView("redirect:/ediplace");
	
	
}
@RequestMapping("/deleteplace/{id}")
public ModelAndView deletePlace(@PathVariable int id)
{
	pser.delete(id);
	
	return new ModelAndView("redirect:/ediplace");
}


@RequestMapping("/upimg")
public ModelAndView upImgg()
{
	return new ModelAndView("upimge");
}

//image upload
@RequestMapping("/uploadImage")
public ModelAndView uplImg(@ModelAttribute("uplo") Place plc,@RequestParam("file") MultipartFile file)
{
	String folder="C:/Users/UDHAYA/Desktop/all/udhaysts/samplebms/src/main/resources/static/";
	try {
		byte[] bytes = file.getBytes();
		Path path = Paths.get(folder+file.getOriginalFilename());
		Files.write(path,bytes);
		
		pser.save(plc);
		System.out.println("Image Uploaded SuccessFully");
		
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return null;
}
}
