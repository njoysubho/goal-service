

package org.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CategoryController {
   @RequestMapping(path = "/categories",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
   public HttpStatus getCategories(){
        return HttpStatus.OK;
   }
}


