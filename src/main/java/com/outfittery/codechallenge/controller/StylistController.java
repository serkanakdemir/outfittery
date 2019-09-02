package com.outfittery.codechallenge.controller;

import com.outfittery.codechallenge.model.Stylist;
import com.outfittery.codechallenge.service.StylistService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("api/v1.0/stylist")
public class StylistController {

    @Autowired
    private StylistService stylistService;

    @ApiOperation(value = "Add stylist", response = Stylist.class)
    @ApiResponses(value = {
        @ApiResponse(code = 201, message = "Successfully created stylist"),
        @ApiResponse(code = 400, message = "Incorrect data entered")
    }
    )
    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public Stylist createStylist(@Valid @RequestBody Stylist stylist) {
        return stylistService.save(stylist);
    }


}
