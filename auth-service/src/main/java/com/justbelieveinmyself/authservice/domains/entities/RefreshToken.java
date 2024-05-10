package com.justbelieveinmyself.authservice.domains.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Entity
@Table(name = "refresh_tokens")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class RefreshToken {
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ref_token_id_seq")
    @SequenceGenerator(name = "ref_token_id_seq", sequenceName = "ref_token_id_seq", allocationSize = 1)
    private Long id;
    @Column(nullable = false)
    private String token;
    @Column(nullable = false)
    private Instant expiration;
    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public boolean isExpired() {
        return this.getExpiration().isBefore(ZonedDateTime.now(ZoneId.systemDefault()).toInstant());
    }

}
