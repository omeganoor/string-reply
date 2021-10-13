package com.beta.replyservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReplyController {

	@Autowired
	private ReplyService replyService;

	@GetMapping("/reply")
	public ReplyMessage replying() {
		return new ReplyMessage("Message is empty");
	}

	@GetMapping("/reply/{message}")
	public ResponseEntity replying(@PathVariable String message) {
		if (message.contains("-")){
			try {
				ReplyData replyData = replyService.convertData(message);
				return ResponseEntity.ok(replyData);
			}catch (Exception e){
				return ResponseEntity.status(400).body(e.getMessage());
			}
		}
		return ResponseEntity.ok(new ReplyMessage(message));
	}

}