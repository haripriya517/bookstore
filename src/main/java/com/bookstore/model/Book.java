package com.bookstore.model;


import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

@Data
@Entity
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Book {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", columnDefinition = "VARCHAR(255)")
    @Type(type="uuid-char")
    private UUID id;

    private String title;

    private String type;
    private int volumes;

    private String genre;

    private String author;

    public void updateBook(Book book) {
        this.title= book.getTitle();
        this.type=book.getType();
        this.volumes = book.getVolumes();
        this.genre= book.getGenre();
        this.author= book.getAuthor();

    }
}
