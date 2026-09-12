package in.nishu.tradex.portfolio_service.entity;


import in.nishu.tradex.portfolio_service.enums.OrderSide;
import in.nishu.tradex.portfolio_service.enums.OrderStatus;
import io.micrometer.core.annotation.Counted;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.query.Order;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "trade_orders")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TradeOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false, length = 32)
    private String symbol;

    @Column(nullable = false,length = 180)
    private String stockName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false,length = 16)
    private OrderSide side;

    @Column(nullable = false,precision = 18,scale = 4)
    private BigDecimal quantity;

    @Column(nullable = false,precision =18,scale = 4)
    private BigDecimal price;

    @Column(nullable = false,precision = 18,scale=2)
    private BigDecimal totalAmount;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    @Column(nullable = false,length = 24)
    private OrderStatus status=OrderStatus.EXECUTED;

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(nullable = false)
    private LocalDateTime updatedAt;

}
