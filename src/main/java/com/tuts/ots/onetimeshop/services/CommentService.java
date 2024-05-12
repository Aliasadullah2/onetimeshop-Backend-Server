package com.tuts.ots.onetimeshop.services;

import com.tuts.ots.onetimeshop.payloads.CommentDto;
import com.tuts.ots.onetimeshop.payloads.ProductDto;

import java.util.List;

public interface CommentService {

    CommentDto createcomment (CommentDto commentDto,Integer productId);
    void deletcomment (Integer comId);

    List<CommentDto> getAllCommentsDtosimple();

    CommentDto getCommentsDtoById(Integer comId);

    List<CommentDto> getCommentByUser(Integer userId);

    CommentDto createcommentbyuser(CommentDto commentDto,Integer userId,Integer productId);





}
