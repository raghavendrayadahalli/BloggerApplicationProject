package com.myblog.Service;
import com.myblog.Payload.PostDto;
import com.myblog.Payload.PostResponse;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface PostService { //from controller layer, dto object will come to service layer,
    // after saving dto object it will return(PostDto) back to controller what dto object content is saved
    PostDto createPost(PostDto postDto);

    PostResponse getAllPosts(int pageNo, int pageSize, String sortBy, String sortDir);

    PostDto getPostById(long id);

    PostDto updatePostById(PostDto postDto, long id);

    void deletePostById(long id);
}
