package com.mysite.sbb.answer;

import com.mysite.sbb.question.Question;
import com.mysite.sbb.user.SiteUser;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@NoArgsConstructor
@Getter
public class Answer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(columnDefinition = "TEXT")
    private String content;

    private LocalDateTime createDate;

    private LocalDateTime modifyDate;

    @ManyToOne
    private Question question;

    @ManyToOne
    private SiteUser author;

    @ManyToMany
    private Set<SiteUser> voter;

    public void setContent(String content) {
        this.content = content;
    }

    public void setModifyDate(LocalDateTime localDateTime) {
        this.modifyDate = localDateTime;
    }

    // 초기에 Builder 패턴으로 엔티티 객체를 생성하고자 하였지만 수정 사항 떄문에 @Setter 추가, 권장하지 않음
    @Builder
    public Answer(String content, LocalDateTime createDate, Question question, SiteUser author) {
        this.content = content;
        this.createDate = createDate;
        this.question = question;
        this.author = author;
    }
}
