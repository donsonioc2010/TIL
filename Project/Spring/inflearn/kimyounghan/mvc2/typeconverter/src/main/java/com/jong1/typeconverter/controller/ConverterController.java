package com.jong1.typeconverter.controller;

import com.jong1.typeconverter.type.IpPort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ConverterController {
    @GetMapping("/converter-view")
    public String converterView(Model model) {
        model.addAttribute("number", 100000);
        model.addAttribute("ipPort", new IpPort("127.0.0.1", 8080));
        return "converter-view";
    }
}
