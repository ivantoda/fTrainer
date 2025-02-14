package com.ftrainer.ftrainer.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ImagePayload {
    private Integer id;

    private Integer user;

    private byte[] image;
}
