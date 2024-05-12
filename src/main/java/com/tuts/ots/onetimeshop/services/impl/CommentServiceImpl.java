package com.tuts.ots.onetimeshop.services.impl;

import com.tuts.ots.onetimeshop.entities.Comment;
import com.tuts.ots.onetimeshop.entities.ProductEnitity;
import com.tuts.ots.onetimeshop.entities.User;
import com.tuts.ots.onetimeshop.execptions.ResourceNotFoundException;
import com.tuts.ots.onetimeshop.payloads.CommentDto;
import com.tuts.ots.onetimeshop.payloads.ProductDto;
import com.tuts.ots.onetimeshop.repositires.CommentRepo;
import com.tuts.ots.onetimeshop.repositires.ProductRepo;
import com.tuts.ots.onetimeshop.repositires.UserRepo;
import com.tuts.ots.onetimeshop.services.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private CommentRepo commentRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserRepo userRepo;



    @Override
    public CommentDto createcomment(CommentDto commentDto, Integer productId) {
        ProductEnitity productEnitity=this.productRepo.findById(productId)
                .orElseThrow(()-> new ResourceNotFoundException("ProductEnitity","product id",productId));

        Comment comment=this.modelMapper.map(commentDto,Comment.class);
        comment.setProductEnitity(productEnitity);
        comment.setAddedCommentDate(new Date());
        Comment save = this.commentRepo.save(comment);
        return this.modelMapper.map(save,CommentDto.class);
    }

    @Override
    public void deletcomment(Integer comId) {
        Comment comment=this.commentRepo.findById(comId)
                .orElseThrow(()-> new ResourceNotFoundException("Comment","Comment id",comId));
        this.commentRepo.delete(comment);
    }

    @Override
    public List<CommentDto> getAllCommentsDtosimple() {
        List<Comment> allComments = this.commentRepo.findAll();
        List<CommentDto> commentsDto = allComments.stream().map((comment) -> this.modelMapper.map(comment, CommentDto.class)).collect(Collectors.toList());
        return commentsDto;
    }

    @Override
    public CommentDto getCommentsDtoById(Integer comId) {
        Comment comments=this.commentRepo.findById(comId)
                .orElseThrow(()-> new ResourceNotFoundException("CommentDto","com id",comId));
        return this.modelMapper.map(comments,CommentDto.class);
    }

    @Override
    public List<CommentDto> getCommentByUser(Integer userId) {
        User user = this.userRepo.findById(userId)
                .orElseThrow(()->new ResourceNotFoundException("User","user id",userId));
        List<Comment> Comment =  this.commentRepo.findByUser(user);

        List<CommentDto> commentDtos=Comment.stream().map((comments)-> this.modelMapper.map(comments,CommentDto.class)).collect(Collectors.toList());

        return commentDtos;
    }

    @Override
    public CommentDto createcommentbyuser(CommentDto commentDto,Integer userId,Integer productId) {

        User user=this.userRepo.findById(userId)
                .orElseThrow(()->new ResourceNotFoundException("User","User id",userId));

        ProductEnitity productEnitity=this.productRepo.findById(productId)
                .orElseThrow(()->new ResourceNotFoundException("Product","product id",productId));

        Comment comment = this.modelMapper.map(commentDto,Comment.class);
//        productEnitity.setProdImg("default.png");
//        productEnitity.setAddedDate(new Date());
//        productEnitity.setUser(user);
//        productEnitity.setCategory(category);
        comment.setContent(commentDto.getContent());
        comment.setUser(user);
        comment.setProductEnitity(productEnitity);
        comment.setAddedCommentDate(new Date());

        Comment newComment = this.commentRepo.save(comment);

        return this.modelMapper.map( newComment, CommentDto.class);
    }

}
