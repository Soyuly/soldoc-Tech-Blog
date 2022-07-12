package com.soldoc.tech.common;

import com.soldoc.tech.common.PostApiResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@RequiredArgsConstructor
public class PostApiResponseHeader {
    private final int code;
    private final String message;

}
