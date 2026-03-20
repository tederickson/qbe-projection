package com.erickson.qbe_projection.rest;

import com.erickson.qbe_projection.dto.AuthorRequest;
import com.erickson.qbe_projection.dto.AuthorResponse;
import com.erickson.qbe_projection.dto.AuthorResponses;
import com.erickson.qbe_projection.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/author")
public class AuthorController {
    private final AuthorService authorService;

    @GetMapping("/id/{id}")
    public AuthorResponse findById(@PathVariable long id) {
        return authorService.findById(id);
    }

    @PostMapping("/")
    public AuthorResponses findBy(@RequestBody AuthorRequest authorRequest) {
        return authorService.findBy(authorRequest);
    }
}
