package com.lulu.demo;

import com.lulu.ioc.annotation.Autowire;
import com.lulu.ioc.annotation.Controller;
import com.lulu.ioc.annotation.RequestMapping;
import com.lulu.ioc.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Controller
@RequestMapping("/web")
public class MyController {

    @Autowire("myService")
    MyService myService;

    @RequestMapping("/test")
    public void test(HttpServletRequest request,
                     HttpServletResponse response,
                     @RequestParam("word") String word) {
        myService.say("hello ioc:"+ word);
        try {
            response.getWriter().print(word);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
