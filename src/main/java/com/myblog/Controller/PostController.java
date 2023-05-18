package com.myblog.Controller;
import com.myblog.Payload.PostDto;
import com.myblog.Payload.PostResponse;
import com.myblog.Service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

// @Controller used for building web application(establish communication between view and backend business logic )
@RestController//used for building api,
@RequestMapping("/api/posts")  //By writing URL at this class level,it tells that is common for all methods inside PostController
public class PostController {

    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    //    http://localhost:8080/api/posts
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping //This Annotation used to save record in DB i.e create operation
    public ResponseEntity<?> createPost(@Valid @RequestBody PostDto postDto, BindingResult result){
         //if more than one return statements are there,then make generic to <?>
        //if any spring validation errors present it will throw message

        if(result.hasErrors()){// if true
            return  new ResponseEntity<>(result.getFieldError().getDefaultMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        /*

        if(result.hasErrors()) {
            String errorMessage = "";
            for (FieldError error : result.getFieldErrors()) {
                String fieldName = error.getField();
                //String fieldType = error.getObjectName();
                String message = error.getDefaultMessage();
                errorMessage = String.format("%s: %s", fieldName, message);
            }
            return new ResponseEntity<>(errorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
        }
         */

        // here method is not void because it has to return the status hence void is not used here
        //return type ResponseEntity<> returns the status code back and along with this, it will return dto object content back
        PostDto dto = postService.createPost(postDto);

        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    //  http://localhost:8080/api/posts/?pageNo=0&pageSize=5&sortBy=id&sortDir=asc    // Pagination Concept Implemented here

    @GetMapping // method is used to get all records from DB table    //annotation used to do read operation
    public PostResponse getAllPosts(@RequestParam(value = "pageNo",defaultValue = "0",required = false)int pageNo,
                                    @RequestParam(value ="pageSize",defaultValue="5",required=false)int pageSize,
                                    @RequestParam(value="sortBy",defaultValue="id",required = false)String sortBy,
                                    @RequestParam(value="sortDir",defaultValue = "asc",required = false)String sortDir  //'desc' for descending order
                                    ){
        return postService.getAllPosts(pageNo, pageSize, sortBy, sortDir);
    }

    //  http://localhost:8080/api/posts/1

    @GetMapping("/{id}")   //method used to get particular record using id
    public ResponseEntity<PostDto> getPostById(@PathVariable("id") long id){
        return new ResponseEntity<>(postService.getPostById(id),HttpStatus.OK); //Way-1 HttpStatus response
    }

    //http://localhost:8080/api/posts/1
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}") //annotation & Method used to update Post rest API
    public ResponseEntity<PostDto> updatePostById(@RequestBody PostDto postDto,@PathVariable("id")long id){
        PostDto  postResponse= postService.updatePostById(postDto,id);
        return new ResponseEntity<>(postResponse,HttpStatus.OK); //Way-2  HttpStatus Response

    }

    //http://localhost:8080/api/posts/1
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")    //annotation & Method used to delete Post rest API
    public ResponseEntity<String> deletePost(@PathVariable("id")long id){
        postService.deletePostById(id);
        return new ResponseEntity<>("Post deleted Successfully....!",HttpStatus.OK);
    }

}
