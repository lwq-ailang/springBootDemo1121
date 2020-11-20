package com.example.demo.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author: wu
 * @date: 2020/11/20
 * @version: 1.0
 */
@Data
public class User implements Serializable {

    private Long id;
    private String name;
    private int age;

}