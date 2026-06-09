package br.com.livehh.livehh_engine.adapter.out.persistence.entity;

import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.Type;
import org.hibernate.type.SqlTypes;

import java.time.OffsetDateTime;

@Entity
@Table(name = "hand_histories")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HandHistoryJPAEntity {

    @Id
    @Column(name = "hand_id", nullable = false, updatable = false)
    private String handId;

    @Column(name = "game_type", length = 50)
    private String gameType;

    @Column(name = "calculated_ev")
    private Double calculatedEv;

    @Column(name = "processed_at")
    private OffsetDateTime processedAt;

    @Column(name = "bet_category", length = 50)
    private String betCategory;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "raw_payload", columnDefinition = "jsonb")
    private String rawPayload;
}
