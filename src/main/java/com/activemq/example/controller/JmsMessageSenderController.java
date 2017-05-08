package com.activemq.example.controller;

import com.activemq.example.domain.JmsMessage;
import com.activemq.example.service.JmsMessageSenderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;


/**
 * @author bsnyder
 *
 */
@Controller
@RequestMapping("/send.html")
public class JmsMessageSenderController {
	
	@Autowired
	private JmsMessageSenderService messageSenderService;
	
	@GetMapping
	public String setupForm(ModelMap model) {
        model.addAttribute("jmsMessageBean", new JmsMessage());
	    model.remove("successfulSend");
	    
	    return "send";
	}
	
	@PostMapping
	public String onSubmit(
	        @ModelAttribute("jmsMessageBean") JmsMessage jmsMessageBean,
	        BindingResult result,
	        SessionStatus status,
	        ModelMap model)
			throws Exception {
		messageSenderService.sendMessage(jmsMessageBean);
		model.addAttribute("successfulSend", "The message was sent successfully");
        model.addAttribute("jmsMessageBean", new JmsMessage());
		status.setComplete();
		
		return "send";
	}

}
