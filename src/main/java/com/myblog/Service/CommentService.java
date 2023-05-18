package com.myblog.Service;
import com.myblog.Payload.CommentDto;
import java.util.List;

public interface CommentService {
    CommentDto createComment(long postId, CommentDto commentDto);
    List<CommentDto> getCommentsByPostId(long postId);//Get All Comments By PostId
    CommentDto getCommentById(Long postId, Long commentId);//Get Comment By CommentId
    CommentDto updateComment(Long postId, Long commentId, CommentDto commentDto); //Developing Update Comment Rest API
    void deleteComment(Long postId, Long commentId);//Delete Comment Feature
}
