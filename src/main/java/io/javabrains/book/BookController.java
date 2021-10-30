package io.javabrains.book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("/book")
public class BookController {

    private final String COVER_IMAGE_ROOT = "http://covers.openlibrary.org/b/id/";
    @Autowired
    private BookRepository bookRepository;

    @GetMapping("/{id}")
    public String getBook(@PathVariable String id, Model model) {
        Optional<Book> optionalBook = bookRepository.findById(id);


        if (optionalBook.isPresent()) {
            Book book = optionalBook.get();

            String coverImageUrl = "/static/images/no-image.png";
            if (book.getCoverIds() != null && book.getCoverIds().size() > 0) {
                coverImageUrl = COVER_IMAGE_ROOT + book.getCoverIds().get(0) + "-M.jpg";
            }
            model.addAttribute("coverImage", coverImageUrl);

            model.addAttribute("book", book);
            return "book";
        }

        return "book-not-found";
    }
}
