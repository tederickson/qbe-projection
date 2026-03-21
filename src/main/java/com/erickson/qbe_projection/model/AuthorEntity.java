package com.erickson.qbe_projection.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "author")
@Data
@Builder
@AllArgsConstructor  // For Builder
@NoArgsConstructor   // For JPA
public final class AuthorEntity {
    @Id
    @GeneratedValue
    Long authorId;

    String firstName;
    String lastName;
    String email;
    String userName;

    // Always fetch all related Books - verify the projection does not fetch books
    @OneToMany(mappedBy = "author", fetch = FetchType.EAGER)
    List<BookEntity> books;
}
