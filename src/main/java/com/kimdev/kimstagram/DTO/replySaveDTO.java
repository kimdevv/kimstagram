package com.kimdev.kimstagram.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class replySaveDTO {
    private String comment;
    private int accountId;
    private int postId;
}
