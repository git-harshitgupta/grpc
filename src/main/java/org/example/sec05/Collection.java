package org.example.sec05;

import com.harshit.models.sec05.Book;
import com.harshit.models.sec05.Library;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

public class Collection {
    private static final Logger log = LoggerFactory.getLogger(Collection.class);
    public static void main(String[] args) {

        Book book = Book.newBuilder()
                .setAuthor("J K Rowling")
                .setPublicationYear(1998)
                .setTitle("Harry potter part one")
                .build();

        Book book1 = book.toBuilder()
                .setPublicationYear(1999)
                .setTitle("Harry potter part two")
                .build();
        ArrayList<Book> books = new ArrayList<>();
        books.add(book1);
        books.add(book);
        Library library = Library.newBuilder()
                .setName("Library")
//                .addBooks(book1)
//                .addBooks(book)
                .addAllBooks(books)
                .build();
        log.info("Library - {}",library);
    }
}
