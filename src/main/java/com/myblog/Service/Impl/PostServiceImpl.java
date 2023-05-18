package com.myblog.Service.Impl;
import com.myblog.Entity.Post;
import com.myblog.Exception.ResourceNotFoundException;
import com.myblog.Payload.PostDto;
import com.myblog.Payload.PostResponse;
import com.myblog.Repository.PostRepository;
import com.myblog.Service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {

    //this  Repository Layer helps to perform CRUD Operation
    private PostRepository postRepository; //@Autowired is not required here,instead

    private ModelMapper mapper;

    // Constructor based dependency injection,which act as @Autowired
    public PostServiceImpl(PostRepository postRepository,ModelMapper mapper) {
        this.postRepository = postRepository;
        this.mapper=mapper;
    }

    @Override
    public PostDto createPost(PostDto postDto) { //this method is return type because void is not used here
        // convert DTO to entity
        Post post = mapToEntity(postDto);
        Post newpost = postRepository.save(post);//saving entity post ,new post<------post

        // convert entity to DTO
        PostDto dto = mapToDto(newpost);
        return dto;
    }

    //Pagination Implemented here
    @Override  //Method to get All Records From Database
    public PostResponse getAllPosts(int pageNo,int pageSize,String sortBy,String sortDir) {
        //for sorting steps are implemented here
       Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ?  // used ternary operator u can use If
               Sort.by(sortBy).ascending() : Sort.by(sortBy).descending(); //true:false respectively

          /* used if Condition
           Sort sort=null;
          if(sortDir.equalsIgnoreCase("asc")){
             sort= Sort.by(sortBy).ascending();
          }else{
              sort= Sort.by(sortBy).descending();
          }
           */
          //for pagination steps are implemented here
        //Create Pageable instance

        PageRequest pageable = PageRequest.of(pageNo,pageSize,sort);
        Page<Post> content = postRepository.findAll(pageable);//this method will read exactly the No.of Data Depending on pageNo and PageSize
       //Convert Page<Post> to List<Post>
        List<Post> posts = content.getContent();
        //Convert List<Post> Entity to List<PostDto> Dto
        List<PostDto> dto = posts.stream().map(post -> mapToDto(post)).collect(Collectors.toList());

        PostResponse postResponse=new PostResponse();
        postResponse.setContent(dto);

        postResponse.setPageNo(content.getNumber());
        postResponse.setPageSize(content.getSize());
        postResponse.setTotalPages(content.getTotalPages());
        postResponse.setTotalElements((int)content.getTotalElements());
        postResponse.setLast(content.isLast());

        return postResponse;
    }

    @Override //method used to find record by id
    public PostDto getPostById( long id) {
        Post post = postRepository.findById(id).orElseThrow(  //if id not present in DB table it will throw user exception
                () -> new ResourceNotFoundException("Post", "id", id)
        );
        return mapToDto(post);
    }


    @Override //method used to update records in db table
    public PostDto updatePostById(PostDto postDto, long id) {
        // get post by id from the database
        Post post = postRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Post","id",id)
        );
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setDescription(postDto.getDescription());

        Post updatedpost = postRepository.save(post); //save updated post

        return mapToDto(updatedpost);// controller layer need to get response ,so we converted to dto
    }

    @Override
    public void deletePostById(long id) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Post", "id", id)
        );
        postRepository.delete(post);
    }


    Post mapToEntity(PostDto dto){ //method used for converting dto to entity
        Post post = mapper.map(dto, Post.class);
        return post;

              /*  Post post=new Post(); //we can not save dto hence  convert Dto to entity for service layer and save entity
                post.setTitle(dto.getTitle());//coping data from dto to entity
                post.setContent(dto.getContent());
                post.setDescription(dto.getDescription());
                return post;
                */
    }

    PostDto mapToDto(Post post) { //method used for converting entity to dto
       PostDto postDto=mapper.map(post,PostDto.class);
       return postDto;

              /*  PostDto dto=new PostDto();
                dto.setId(post.getId());//coping data from entity to dto
                dto.setTitle(post.getTitle());
                dto.setContent(post.getContent());
                dto.setDescription(post.getDescription());
                return dto;
                */
    }


}
