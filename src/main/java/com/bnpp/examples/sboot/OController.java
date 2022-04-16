package com.bnpp.examples.sboot;

import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/")
public class OController {

    @RequestMapping(value = "/yogurt", method = RequestMethod.GET)
    public String yougurg() throws IOException {
        return "Yogurt welcome. BNPP entertainment";
    }

}
