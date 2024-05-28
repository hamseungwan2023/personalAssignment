package com.sparta.personalassignment.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "refresh_token")
public class RefreshToken extends Timestamped {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "hashed_token")
    private String hashedToken;

    public RefreshToken(String hashedToken) {
        this.hashedToken = hashedToken;
    }
}
