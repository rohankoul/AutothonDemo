package testserver.server.controllers;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;

import testserver.server.support.GenericResponseObject;
import testserver.server.uiAutomation.UIAutomationInitiator;

@RestController
public class UIAutomationController {
	
	UIAutomationInitiator init;
	
	@RequestMapping( value= "/api/ui/trigger" , method = RequestMethod.GET , params = { "browser", "device" } )
	public GenericResponseObject triggerAutomates(@RequestParam("device") String device, @RequestParam("browser") String browser) {
		
		System.out.println(device+" "+browser);
		
		if(device.equals("") || browser.equals(""))
		{
			return new GenericResponseObject("Paramter values cannot be '' ",400);
			
		}
		
		init = new UIAutomationInitiator(browser,device);
		init.start();
		return new GenericResponseObject("Successfully Triggered",200);
	}
	
	@RequestMapping( value= "/api/ui/trigger" , method = RequestMethod.GET )
	public GenericResponseObject moreDataNeeded() {
			
		return new GenericResponseObject("Please also provide browser and device parameters",400);
	}
	
	@RequestMapping("/status")
	public GenericResponseObject getStatus() {
		
		String status = init.getStatus();
		return new GenericResponseObject("Status : "+status , 200);
		
	}
	
	@RequestMapping(value = "/screenshot/{name}", method = RequestMethod.GET,
            produces = MediaType.IMAGE_PNG_VALUE)
    public void getImage(HttpServletResponse response , @PathVariable("name") String name) throws IOException {

        Resource imgFile = new ClassPathResource("images/"+name+".png");

        response.setContentType(MediaType.IMAGE_PNG_VALUE);
        StreamUtils.copy(imgFile.getInputStream(), response.getOutputStream());
    }
	
}
