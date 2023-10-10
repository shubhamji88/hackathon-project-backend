package com.incedo.Hackathon.models;

import jakarta.persistence.*;

@Entity
@Table(
        uniqueConstraints = {
                @UniqueConstraint(columnNames = {"teamId", "isIdeaPhase"})
        }
)
public class Result {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer teamId;
    private Double finalScore;
    private boolean isIdeaPhase;

    public Integer getId() {
        return id;
    }

    public Integer getTeamId() {
        return teamId;
    }

    public void setTeamId(Integer teamId) {
        this.teamId = teamId;
    }

    public Double getFinalScore() {
        return finalScore;
    }

    public void setFinalScore(Double finalScore) {
        this.finalScore = finalScore;
    }

    public boolean isIdeaPhase() {
        return isIdeaPhase;
    }

    public void setIdeaPhase(boolean ideaPhase) {
        isIdeaPhase = ideaPhase;
    }
}
