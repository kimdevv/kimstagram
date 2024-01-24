package com.kimdev.kimstagram.DTO;

import com.kimdev.kimstagram.model.Account;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UploadDataDTO {
    private int userId;
    private String comment;
    private List<MultipartFile> pictures;
}
