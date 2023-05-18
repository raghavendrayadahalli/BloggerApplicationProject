package com.myblog.Payload;
import lombok.Data;
import java.util.List;

@Data
public class PostResponse {
    private List<PostDto> content;
    private int PageNo;
    private int PageSize;
    private int totalPages;
    private int totalElements;
    private boolean isLast;

}
