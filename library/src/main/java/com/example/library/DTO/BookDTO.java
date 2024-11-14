

package com.example.library.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookDTO {

    private Long bookId;
    private String title;
    private String author;
    private Long memberId;  // The ID of the Member, not the full object

}
