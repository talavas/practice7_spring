package shpp.level4.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/hello")
public class HelloController {

    @GetMapping("/{name}")
    public String getGreetings(@PathVariable String name){
        if(name != null){
            return "Hello" + name;
        }else{
            return "Hello World!";
        }
    }

    @PostMapping("/")
    public ResponseEntity<String> setAndGetGreetings(@RequestBody String name){
        if(name != null){
            return new ResponseEntity<>("Hello" + name, HttpStatus.OK);
        }else{
            return new ResponseEntity<>("Hello World!", HttpStatus.OK);
        }
    }
}
