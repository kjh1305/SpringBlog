package iducs.springboot.blog201712015.domain;

import lombok.Data;

@Data
public class Blog {
    private Long id;
    private String title;
    private String content;
    private String filepath;
    private String b201712015ger;
    private String reg_date_time;
}