package net.project.journalApp.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;


//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
//@ToString
//@EqualsAndHashCode
@Document(collection="journal_entries")
@Data
@NoArgsConstructor
public class JournalEntry {
    @Id
    private String id;

    @NonNull
    private String title;
    private String content;
    private LocalDateTime date;
}
