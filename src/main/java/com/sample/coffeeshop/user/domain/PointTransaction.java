package com.sample.coffeeshop.user.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "point_transaction")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PointTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pointTransactionId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_seq")
    private User user;

    private Long point;

    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;

    private LocalDateTime transactedAt;

    private PointTransaction(User user, Long point) {
        this.user = user;
        this.point = point;
        transactedAt = LocalDateTime.now();
    }

    public static PointTransaction createByPayment(User user, Long point) {
        PointTransaction pointTransaction = new PointTransaction(user, point);
        pointTransaction.transactionType = TransactionType.PAYMENT;
        return pointTransaction;
    }

    public static PointTransaction createByCharge(User user, Long point) {
        PointTransaction pointTransaction = new PointTransaction(user, point);
        pointTransaction.transactionType = TransactionType.CHARGE;
        return pointTransaction;
    }
}
