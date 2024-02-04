package com.kimdev.kimstagram.controller.api;

import com.kimdev.kimstagram.DTO.DMMessageDTO;
import com.kimdev.kimstagram.DTO.followDTO;
import com.kimdev.kimstagram.Repository.AccountRepository;
import com.kimdev.kimstagram.Repository.FollowRepository;
import com.kimdev.kimstagram.Repository.PostLikeRepository;
import com.kimdev.kimstagram.Repository.PostRepository;
import com.kimdev.kimstagram.model.Account;
import com.kimdev.kimstagram.model.DMMessage;
import com.kimdev.kimstagram.model.Post;
import com.kimdev.kimstagram.service.DMService;
import com.kimdev.kimstagram.service.HomeService;
import com.kimdev.kimstagram.service.ProfileService;
import com.kimdev.kimstagram.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class DMApiController {

    @Autowired
    DMService dmService;

    @PostMapping("/saveDmMessage")
    public int saveDmMessage(@RequestBody DMMessageDTO dmMessageDTO) {
        dmService.saveDmMessage(dmMessageDTO);

        return 1;
    }
}
