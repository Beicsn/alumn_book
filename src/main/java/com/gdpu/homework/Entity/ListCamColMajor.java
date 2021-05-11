package com.gdpu.homework.Entity;

import lombok.Data;

import java.util.List;

@Data
public class ListCamColMajor {
    private String campus;
    private List<ListColMajor> college;
}
