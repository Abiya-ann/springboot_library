package com.example.library.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Book {

    @Id
    private Long bookId; // Unique ID for each book

    private String title;  // Book title
    private String author; // Book author

    @ManyToOne(fetch = FetchType.EAGER) // Eager loading
    @JoinColumn(name = "m_id", nullable = false)
    @JsonIgnore // Prevent infinite recursion when serializing
    private Member member; // Foreign key to Member
}
