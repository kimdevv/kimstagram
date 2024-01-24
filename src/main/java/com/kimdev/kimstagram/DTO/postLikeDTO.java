package com.kimdev.kimstagram.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class postLikeDTO {
    private int principalId;
    private int postId;
}
