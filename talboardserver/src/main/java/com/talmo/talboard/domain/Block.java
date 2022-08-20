package com.talmo.talboard.domain;

import com.talmo.talboard.domain.id.BlockId;
import java.time.LocalDateTime;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

@Entity
public class Block {
    @EmbeddedId
    private BlockId blockId;
    private LocalDateTime createDate;
}
