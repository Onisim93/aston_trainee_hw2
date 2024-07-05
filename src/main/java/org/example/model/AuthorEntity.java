package org.example.model;

import lombok.*;

/**
 * Represents an author entity.
 * This class holds information about an author, including his id, name and biography.
 */
@Getter
@Setter
@RequiredArgsConstructor
@ToString
@EqualsAndHashCode
public class AuthorEntity implements BaseEntity<Integer> {
    /**
     * The unique identifier of the author.
     */
    private Integer id;
    /**
     * The name of the author.
     */
    private String name;
    /**
     * A brief biography of the author. Max length 1024 characters.
     */
    private String bio;


    private AuthorEntity(AuthorEntityBuilder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.bio = builder.bio;
    }

    public static AuthorEntityBuilder builder() {
        return new AuthorEntityBuilder();
    }

    /**
     * Builder
     */
    public static class AuthorEntityBuilder {
        private Integer id;
        private String name;
        private String bio;

        public AuthorEntityBuilder id(Integer id) {
            this.id = id;
            return this;
        }
        public AuthorEntityBuilder name(String name) {
            this.name = name;
            return this;
        }
        public AuthorEntityBuilder bio(String bio) {
            this.bio = bio;
            return this;
        }

        public AuthorEntity build() {
            return new AuthorEntity(this);
        }
    }



}
