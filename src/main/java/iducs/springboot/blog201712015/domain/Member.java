package iducs.springboot.blog201712015.domain;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Member {
    private Long id;
    private String email;
    private String pw;
    private String name;
    private String phone;
    private String address;
}
