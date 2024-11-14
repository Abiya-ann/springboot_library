package com.example.library.Controller;

import com.example.library.DTO.BookDTO;
import com.example.library.Service.BookService;
import com.example.library.Service.MemberService;
import com.example.library.entity.Book;
import com.example.library.entity.Member;
import com.example.library.repository.BookRepository;
import com.example.library.repository.MemberRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;
    private final MemberService memberService;
    private final MemberRepository memberRepository;  // Inject memberRepository
    private final BookRepository bookRepository;  // Inject bookRepository

    // Constructor injection for services and repositories
    public BookController(BookService bookService, MemberService memberService,
                          MemberRepository memberRepository, BookRepository bookRepository) {
        this.bookService = bookService;
        this.memberService = memberService;
        this.memberRepository = memberRepository;
        this.bookRepository = bookRepository;
    }


    @PostMapping
    public ResponseEntity<BookDTO> createBook(@RequestBody BookDTO bookDTO) {
        // Find the Member by memberId from the DTO
        Member member = memberRepository.findById(bookDTO.getMemberId())
                .orElseThrow(() -> new RuntimeException("Member not found"));

        // Create a new Book instance
        Book book = new Book();

        // If bookId is provided in the DTO, set it manually
        if (bookDTO.getBookId() != null) {
            book.setBookId(bookDTO.getBookId());  // Set the manually assigned bookId
        }

        // Set other properties
        book.setTitle(bookDTO.getTitle());
        book.setAuthor(bookDTO.getAuthor());
        book.setMember(member); // Associate the member with the book

        // Save the book to the repository
        bookRepository.save(book);

        // Update DTO with the generated bookId (if any)
        bookDTO.setBookId(book.getBookId());

        // Return the saved bookDTO
        return ResponseEntity.status(HttpStatus.CREATED).body(bookDTO);
    }


    // GET: Get all books
    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        List<Book> books = bookService.getAllBooks();
        return ResponseEntity.ok(books);
    }

    // GET: Get book by bookId
   /* @GetMapping("/{bookId}")
    public ResponseEntity<Book> getBookById(@PathVariable Long bookId) {
        Optional<Book> book = bookService.getBookById(bookId);
        return book.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }*/
    @GetMapping("/{bookId}")
    public ResponseEntity<BookDTO> getBookById(@PathVariable Long bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        // Manually map Book entity to BookDTO
        BookDTO bookDTO = new BookDTO();
        bookDTO.setBookId(book.getBookId());
        bookDTO.setTitle(book.getTitle());
        bookDTO.setAuthor(book.getAuthor());
        bookDTO.setMemberId(book.getMember().getMemberId());  // Set the memberId explicitly

        return ResponseEntity.ok(bookDTO);
    }


    // DELETE: Delete book by bookId
    @DeleteMapping("/{bookId}")
    public ResponseEntity<Void> deleteBookById(@PathVariable Long bookId) {
        // Check if book exists before deleting
        if (!bookService.getBookById(bookId).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        bookService.deleteBook(bookId);
        return ResponseEntity.noContent().build();
    }
    @PostMapping("/{bookId}/assign")
    public ResponseEntity<Book> assignBook(@PathVariable Long bookId, @RequestBody Long memberId){
        Book book = bookService.assignBook(bookId, memberId);
        return ResponseEntity.ok(book);
    }

    @PutMapping("/{bookId}")
    public ResponseEntity<Book> updateBook(@PathVariable Long bookId, @RequestBody Book book) {
        Optional<Book> existingBook = bookService.getBookById(bookId);
        if (!existingBook.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        book.setBookId(bookId);
        Book updatedBook = bookService.updateBook(bookId, book);
        return ResponseEntity.ok(updatedBook);
    }
   @GetMapping("/{memberId}/listBooks")
    public ResponseEntity <List<Book>> getByMemberId(@PathVariable Long memberId){
        List <Book> book= bookService.getByMemberId(memberId);
        return ResponseEntity.ok(book);
   }
}
