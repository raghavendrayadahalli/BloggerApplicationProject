package com.myblog.Payload;
import lombok.Data;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data   //used to generate getter & setter in class by default
public class PostDto {

     private long  id;

     // title should not be null or empty
     // title should have at least 2 characters
     @NotEmpty
     @Size(min = 2, message = "Post's title should have at least 2 characters")
     private String title;

     //  description should not be null or empty
     //  description should have at least 10 characters
     @NotEmpty
     @Size(min = 10, message = "Post's description should have at least 10 characters")
     private String description;

     //  content should not be null or empty
     @NotEmpty
     private String content;

}
