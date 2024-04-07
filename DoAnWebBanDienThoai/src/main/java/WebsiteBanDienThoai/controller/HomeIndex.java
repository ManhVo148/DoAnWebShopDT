package WebsiteBanDienThoai.controller;

import org.springframework.web.bind.annotation.GetMapping;

public class HomeIndex {
    @GetMapping("/")
    public String home(){
        return "index";
    }


}
