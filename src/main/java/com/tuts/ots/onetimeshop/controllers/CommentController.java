package com.tuts.ots.onetimeshop.controllers;

import com.tuts.ots.onetimeshop.entities.Comment;
import com.tuts.ots.onetimeshop.payloads.ApiResponse;
import com.tuts.ots.onetimeshop.payloads.CommentDto;
import com.tuts.ots.onetimeshop.payloads.ProductDto;
import com.tuts.ots.onetimeshop.services.CommentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @PostMapping("/comments/product/{productId}")
    public ResponseEntity<CommentDto> createComment(@Valid @RequestBody CommentDto comment, @PathVariable("productId") Integer productId){
        CommentDto createcomment = this.commentService.createcomment(comment, productId);
        return new ResponseEntity<CommentDto>(createcomment, HttpStatus.CREATED);
    }

    //create Comments api
    @PostMapping("/user/{userId}/comments/product/{productId}")
    public ResponseEntity<CommentDto> createCommentByUser(@Valid @RequestBody CommentDto comment, @PathVariable("productId") Integer productId, @PathVariable Integer userId){

        CommentDto createCommentstDto = this.commentService.createcommentbyuser(comment,userId,productId);

        return new ResponseEntity<CommentDto>(createCommentstDto, HttpStatus.CREATED);
    }

    @GetMapping("/all/Comments")
    public ResponseEntity<List<CommentDto>> getAllCommentsDtosimple(){
        List<CommentDto> commmentsDtos =this.commentService.getAllCommentsDtosimple();
        return new ResponseEntity<List<CommentDto>>(commmentsDtos,HttpStatus.OK);
    }

    @GetMapping("/comments/{comId}")
    public ResponseEntity<CommentDto> getCommentsDtoById(@PathVariable Integer comId){
        CommentDto commentDto =this.commentService.getCommentsDtoById(comId);
        return new ResponseEntity<CommentDto>(commentDto,HttpStatus.OK);
    }



    @DeleteMapping("/comments/{comId}")
    public ResponseEntity<ApiResponse> deletecomment(@PathVariable("comId") Integer comId){
        this.commentService.deletcomment(comId);
        return new ResponseEntity(new ApiResponse("Comment Deleted Successfully",true), HttpStatus.OK);
    }


    @GetMapping("/user/{userId}/comments")
    public ResponseEntity<List<CommentDto>> getCommentByUser(@PathVariable Integer userId){
        List<CommentDto> commentDtos =this.commentService.getCommentByUser(userId);
        return new ResponseEntity<List<CommentDto>>(commentDtos,HttpStatus.OK);
    }






}
